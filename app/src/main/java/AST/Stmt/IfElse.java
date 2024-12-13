package AST.Stmt;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.Node;
import AST.Suite;
import AST.SimpleStmt.Expr.Expr;

public class IfElse implements Stmt {
    private Expr ifExpr; 
    private Suite then;  
    private Suite elseBlock; 

    public IfElse() {
        this.ifExpr = null;   
        this.then = null;    
        this.elseBlock = null;  
    }

    public IfElse(Expr ifExpr,Suite then,Suite elseBlock) {
        this.ifExpr = ifExpr;   
        this.then = then;    
        this.elseBlock = elseBlock;  
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

    public Suite getElse() {
        return elseBlock;
    }

    public void setElse(Suite elseBlock) {
        this.elseBlock = elseBlock;
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

        if (this.elseBlock != null) {
            String elseNodeName = nodeName + "_else"; 
            writer.write("  " + nodeName + " -- " + elseNodeName + ";\n");
            ((Node) this.elseBlock).vizualisation(writer, elseNodeName); 
        }
    }

    public IfElse simplify(){
        ifExpr = ifExpr.simplify();
        then = then.simplify();
        elseBlock = elseBlock.simplify();
        return this;
    }
}