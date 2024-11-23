package AST.SimpleStmt.Expr;

import java.io.BufferedWriter;
import java.io.IOException;

public class NotExpr extends AndExpr {
    private NotExpr expr;

    public NotExpr() {
        this.expr = null;
    }

    public NotExpr getNotExpr() {
        return expr;
    }

    public void setExpr(NotExpr expr) {
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
        if (expr instanceof NotExpr) return this.expr.getNotExpr();
        else return this;
    }
}
