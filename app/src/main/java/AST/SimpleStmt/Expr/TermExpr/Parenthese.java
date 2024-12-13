package AST.SimpleStmt.Expr.TermExpr;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.SimpleStmt.Expr.Expr;

public class Parenthese extends TermExpr {
    private Expr expr;

    public Parenthese() {
        this.expr = null;
    }

    public Parenthese(Expr expr) {
        this.expr = expr;
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"PARENTHESE\"];\n");

        if (expr != null ){
            String exprNodeName = nodeName + "_" + expr.hashCode(); 
            writer.write("  " + nodeName + " -- " + exprNodeName + ";\n");
            expr.vizualisation(writer,exprNodeName); 
        }
    }

    public Parenthese simplify(){
        if (expr == null) return null;
        expr = expr.simplify();
        if (expr == null) return null;
        return this;
    }
}
