package AST.SimpleStmt.Expr.TermExpr;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import AST.SimpleStmt.Expr.Expr;

public class ListType extends TermExpr {
    private List<Expr> exprs;

    public ListType() {
        this.exprs = new ArrayList<Expr>();
    }

    public List<Expr> getExprs() {
        return exprs;
    }

    public void addExprs(Expr expr) {
        this.exprs.add(expr);
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"LIST\"];\n");

        for (Expr expr : exprs){
            String exprNodeName = nodeName + "_" + expr.hashCode(); 
            writer.write("  " + nodeName + " -- " + exprNodeName + ";\n");
            expr.vizualisation(writer,exprNodeName); 
        }
    }

    public ListType simplify() {
        if (exprs == null) return null; 
        exprs = exprs.stream()
                     .map(elt -> elt.simplify()) 
                     .collect(Collectors.toList());  

        exprs.removeIf(elt -> elt == null);
        if (exprs == null) return null; 
        
        return this;
    }

}
