package AST.SimpleStmt;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.Node;
import AST.SimpleStmt.Expr.Expr;

public class AffectTab implements SimpleStmt {
    private Expr expr1;  
    private Expr expr2;  
    private Expr expr3;  


    public AffectTab() {
        this.expr1 = null;  
        this.expr2 = null; 
        this.expr3 = null; 
    }

    public Expr getExpr1() {
        return expr1;
    }

    public void setExpr1(Expr expr) {
        this.expr1 = expr;
    }

    public Expr getExpr2() {
        return expr2;
    }

    public void setExpr2(Expr expr) {
        this.expr2 = expr;
    }

    public Expr getExpr3() {
        return expr3;
    }

    public void setExpr3(Expr expr) {
        this.expr3 = expr;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"AFFECT_TAB\"];\n");

        if (this.expr1 != null) {
            String expr1NodeName = nodeName + "_expr1"; 
            writer.write("  " + nodeName + " -- " + expr1NodeName + ";\n");
            ((Node) this.expr1).vizualisation(writer, expr1NodeName); 
        }

        if (this.expr2 != null) {
            String expr2NodeName = nodeName + "_expr2"; 
            writer.write("  " + nodeName + " -- " + expr2NodeName + ";\n");
            ((Node) this.expr2).vizualisation(writer, expr2NodeName); 
        }

        if (this.expr3 != null) {
            String expr3NodeName = nodeName + "_expr3"; 
            writer.write("  " + nodeName + " -- " + expr3NodeName + ";\n");
            ((Node) this.expr3).vizualisation(writer, expr3NodeName); 
        }
    }

    public AffectTab simplify(){
        expr1 = expr1.simplify();
        expr2 = expr2.simplify();
        expr3 = expr3.simplify();
        return this;
    }
}
