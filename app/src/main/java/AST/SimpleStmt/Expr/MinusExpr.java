package AST.SimpleStmt.Expr;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.SimpleStmt.Expr.TermExpr.TermExpr;

public class MinusExpr extends MutExpr {
    private MinusExpr expr;

    public MinusExpr() {
        this.expr = null;
    }

    public MinusExpr(MinusExpr expr) {
        this.expr = expr;
    }

    public MinusExpr getMinusExpr() {
        return expr;
    }

    public void setMinusExpr(MinusExpr expr) {
        this.expr = expr;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"NEG\"];\n");

        if (expr != null ){
            String exprNodeName = nodeName + "_" + expr.hashCode(); 
            writer.write("  " + nodeName + " -- " + exprNodeName + ";\n");
            expr.vizualisation(writer,exprNodeName); 
        }
    }

    public MinusExpr simplify(){
        if (expr == null) return null;
        expr = expr.simplify();  
        if (expr == null) return this;

        if (expr instanceof TermExpr) return this;
        if (expr instanceof MinusExpr) return this.expr.getMinusExpr();
        
        return this;
    }

}
