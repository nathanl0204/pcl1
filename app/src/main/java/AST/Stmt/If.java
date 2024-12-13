package AST.Stmt;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.Node;
import AST.Suite;
import AST.SimpleStmt.Expr.Expr;

public class If implements Stmt {
    private Expr ifExpr; 
    private Suite then;  

    public If() {
        this.ifExpr = null; 
        this.then = null; 
    }

    public If(Expr ifExpr,Suite then) {
        this.ifExpr = ifExpr; 
        this.then = then; 
    }

    public Expr getIf() {
        return ifExpr;
    }

    public void setIf(Expr ifExpr) {
        this.ifExpr = ifExpr;
    }

    public Suite getThen() {
        return then;
    }

    public void setThen(Suite then) {
        this.then = then;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"IF_THEN\"];\n");
        
        if (this.ifExpr != null) {
            String ifNodeName = nodeName + "_if"; 
            writer.write("  " + nodeName + " -- " + ifNodeName + ";\n");
            ((Node) this.ifExpr).vizualisation(writer, ifNodeName);
        }
 
        if (this.then != null) {
            String thenNodeName = nodeName + "_then"; 
            writer.write("  " + nodeName + " -- " + thenNodeName + ";\n");
            ((Node) this.then).vizualisation(writer, thenNodeName); 
        }
    }

    public If simplify(){
        ifExpr = ifExpr.simplify();
        then = then.simplify();
        return this;
    }
}
