package AST.SimpleStmt;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.Node;
import AST.SimpleStmt.Expr.Expr;

public class Print extends SimpleStmt {
    private Expr expr;

    // Constructeur
    public Print() {
        this.expr = null; // Par défaut, on initialise 'expr' à null
    }

    // Getter pour 'expr'
    public Expr getExpr() {
        return expr;
    }

    // Setter pour 'expr'
    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public void vizualisation(BufferedWriter writer,  String nodeName) throws IOException{
        writer.write("  " + nodeName + " [label=\"PRINT\"];\n");

        if (this.expr != null) {
            String exprNodeName = nodeName + "_"; 
            writer.write("  " + nodeName + " -- " + exprNodeName + ";\n");
            ((Node) this.expr).vizualisation(writer, exprNodeName);
        }
    }

    public Print simplify(){
        expr = expr.simplify();
        return this;
    }
    
}
