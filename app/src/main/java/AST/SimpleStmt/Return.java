package AST.SimpleStmt;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.Node;
import AST.SimpleStmt.Expr.Expr;

public class Return implements SimpleStmt {
    private Expr expr;

    public Return() {
        this.expr = null; 
    }

    public Return(Expr expr) {
        this.expr = expr;
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public void vizualisation(BufferedWriter writer,  String nodeName) throws IOException{
        writer.write("  " + nodeName + " [label=\"RETURN\"];\n");

        if (this.expr != null) {
            String exprNodeName = nodeName + "_"; 
            writer.write("  " + nodeName + " -- " + exprNodeName + ";\n");
            ((Node) this.expr).vizualisation(writer, exprNodeName);
        }
    }

    public Return simplify(){
        expr = expr.simplify();
        return this;
    }
}
