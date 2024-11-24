package AST.SimpleStmt;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.SimpleStmt.Expr.TermExpr.*;
import AST.SimpleStmt.Expr.Expr;

public class Affect extends SimpleStmt {
    private Ident ident; 
    private Expr expr; 

    public Affect() {
        this.ident = null;  
        this.expr = null; 
    }

    public Ident getIdent() {
        return ident;
    }

    public void setIdent(Ident ident) {
        this.ident = ident;
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"AFFECT\"];\n");

        if (this.ident != null) {
            String identNodeName = nodeName + "_ident"; 
            writer.write("  " + nodeName + " -- " + identNodeName + ";\n");
            this.ident.vizualisation(writer, identNodeName);
        }

        if (this.expr != null) {
            String exprNodeName = nodeName + "_expr"; 
            writer.write("  " + nodeName + " -- " + exprNodeName + ";\n");
            this.expr.vizualisation(writer, exprNodeName); 
        }
    }

    public Affect simplify(){
        expr = expr.simplify();
        return this;
    }
}