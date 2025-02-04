package AST.Stmt;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.Suite;
import AST.SimpleStmt.Expr.Expr;
import AST.SimpleStmt.Expr.TermExpr.*;

public class For implements Stmt{
    private Ident ident;
    private Expr expr;
    private Suite suite;

    public For() {
        this.ident = null;
        this.expr = null;
        this.suite = null;
    }

    public For(Ident ident, Expr expr,Suite suite) {
        this.ident = ident;
        this.expr = expr;
        this.suite = suite;
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

    public Suite getSuite() {
        return suite;
    }

    public void setSuite(Suite suite) {
        this.suite = suite;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"FOR\"];\n");
    
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
    
        if (this.suite != null) {
            String suiteNodeName = nodeName + "_suite";
            writer.write("  " + nodeName + " -- " + suiteNodeName + ";\n");
            this.suite.vizualisation(writer, suiteNodeName);
        }
    }
    

    public For simplify(){
        expr = expr.simplify();
        suite = suite.simplify();
        return this;
    }

}
