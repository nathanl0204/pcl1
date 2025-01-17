package AST.SimpleStmt.Expr;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import AST.SimpleStmt.Expr.TermExpr.Const.BoolType;

public class OrExpr implements Expr {
    private List<AndExpr> exprs;

    public OrExpr() {
        this.exprs = new ArrayList<AndExpr>();
    }

    public List<AndExpr> getOrExprs() {
        return exprs;
    }

    public void addOrExpr(AndExpr expr) {
        this.exprs.add(expr);
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"OR\"];\n");
        System.out.println(exprs.size());
        for (AndExpr child : exprs){
            System.out.println(exprs.size());
            String childNodeName = nodeName + "_" + child.hashCode(); 
            writer.write("  " + nodeName + " -- " + childNodeName + ";\n");
            child.vizualisation(writer,childNodeName); 
        }
    }

    public OrExpr simplify(){
        exprs = exprs.stream()
                     .map(elt -> elt.simplify()) 
                     .collect(Collectors.toList());  

        exprs.removeIf(expr -> expr instanceof BoolType && !((BoolType)expr).getValue());
        if (exprs.isEmpty()) return new BoolType(false); 

        for (Expr expr : exprs) {
            if (expr instanceof BoolType && ((BoolType)expr).getValue() ){
                return new BoolType(true); 
            }
        }

        return this;
    }
    
}
