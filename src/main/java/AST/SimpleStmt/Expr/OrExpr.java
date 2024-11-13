package AST.SimpleStmt.Expr;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import AST.SimpleStmt.Expr.TermExpr.Const.Bool;

public class OrExpr extends Expr {
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

        for (AndExpr child : exprs){
            String childNodeName = nodeName + "_" + child.hashCode(); 
            writer.write("  " + nodeName + " -- " + childNodeName + ";\n");
            child.vizualisation(writer,childNodeName); 
        }
    }

    public OrExpr simplify(){
        exprs = exprs.stream()
                     .map(elt -> elt.simplify()) 
                     .collect(Collectors.toList());  

        exprs.removeIf(expr -> expr instanceof Bool && !((Bool)expr).getBool());
        if (exprs.isEmpty()) return new Bool(false); 

        for (Expr expr : exprs) {
            if (expr instanceof Bool && ((Bool)expr).getBool() ){
                return new Bool(true); 
            }
        }

        return this;
    }
    
}
