package AST.SimpleStmt.Expr;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.SimpleStmt.Expr.TermExpr.Ident;
import AST.SimpleStmt.Expr.TermExpr.Const.Bool;

public class NotExpr extends AndExpr {
    private NotExpr expr;

    public NotExpr() {
        this.expr = null;
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
        if (expr == null) return null;
        expr = expr.simplify();  

        if (expr instanceof Ident) return this;
        if (expr instanceof Bool && ((Bool) expr).getBool()) return new Bool(false);
        if (expr instanceof Bool && !((Bool) expr).getBool()) return new Bool(true);
        if (expr instanceof NotExpr) return ((NotExpr) expr).getNotExpr();
        return this;
    }
}
