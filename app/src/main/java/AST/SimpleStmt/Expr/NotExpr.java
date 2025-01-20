package AST.SimpleStmt.Expr;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.SimpleStmt.Expr.TermExpr.Const.*;

public class NotExpr extends AndExpr {
    private NotExpr expr;

    public NotExpr() {
        this.expr = null;
    }

    public NotExpr(NotExpr expr) {
        this.expr = expr;
    }

    public NotExpr getNotExpr() {
        return expr;
    }

    public void setNotExpr(NotExpr expr) {
        this.expr = expr;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"NOT\"];\n");

        if (expr != null ){
            String childNodeName = nodeName + "_" + expr.hashCode(); 
            writer.write("  " + nodeName + " -- " + childNodeName + ";\n");
            expr.vizualisation(writer,childNodeName); 
        }
    }

    public NotExpr simplify(){
        expr = expr.simplify();  

        if (expr instanceof BoolType && ((BoolType) expr).getValue()) return new BoolType(false);
        if (expr instanceof BoolType && !((BoolType) expr).getValue()) return new BoolType(true);

        if (expr.getClass().equals(NotExpr.class)) return ((NotExpr) expr).getNotExpr();
        return this;
    }
}
