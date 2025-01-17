package AST.SimpleStmt;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.SimpleStmt.Expr.Expr;

public class Affect implements SimpleStmt {
    private Expr leftExpr; 
    private Expr rightExpr; 

    public Affect() {
        this.leftExpr = null;  
        this.rightExpr = null; 
    }

    public Affect( Expr rightExpr) {
        this.leftExpr = null;  
        this.rightExpr = rightExpr; 
    }

    public Affect(Expr leftExpr, Expr rightExpr) {
        this.leftExpr = leftExpr;
        this.rightExpr = rightExpr;
    }

    public Expr getLeft() {
        return leftExpr;
    }

    public void setLeft(Expr leftExpr) {
        this.leftExpr = leftExpr;
    }

    public Expr getRight() {
        return rightExpr;
    }

    public void setRight(Expr rightExpr) {
        this.rightExpr = rightExpr;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"AFFECT\"];\n");

        if (this.leftExpr != null) {
            String leftExprNodeName = nodeName + "_leftExpr";             
            writer.write("  " + nodeName + " -- " + leftExprNodeName + ";\n");
            this.leftExpr.vizualisation(writer, leftExprNodeName);
        }

        if (this.rightExpr != null) {
            String rightExprNodeName = nodeName + "_rightExpr";
            writer.write("  " + nodeName + " -- " + rightExprNodeName + ";\n");
            rightExpr.vizualisation(writer, rightExprNodeName);  
            System.out.println(rightExpr.getClass());
        }
    }

    public Affect simplify(){
        rightExpr = rightExpr.simplify();
        return this;
    }
}