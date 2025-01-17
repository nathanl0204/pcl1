package AST.SimpleStmt.Expr;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ExprTab implements Expr {
    private Expr expr;
    private List<Expr> exprs;

    public ExprTab(Expr expr, List<Expr> exprs){
        this.expr = expr;
        this.exprs = exprs;
    }

    public ExprTab(List<Expr> exprs){
        this.expr = null;
        this.exprs = exprs;
    }

    public void setLeft(Expr expr){
        this.expr = expr;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"TAB_ACCES\"];\n");
    
        if (expr != null) {
            String leftNodeName = nodeName + "_left";
            writer.write("  " + nodeName + " -- " + leftNodeName + ";\n");
            expr.vizualisation(writer, leftNodeName); 
        }
    
        String indexNodeName = nodeName + "_INDEX";
        writer.write("  " + indexNodeName + " [label=\"INDEX\"];\n");
        writer.write("  " + nodeName + " -- " + indexNodeName + ";\n");
    
        for (int i = 0; i < exprs.size(); i++) {
            String childNodeName = indexNodeName + "_child" + i;
            writer.write("  " + indexNodeName + " -- " + childNodeName + ";\n");
            exprs.get(i).vizualisation(writer, childNodeName);  
        }
    }
    
    public ExprTab simplify() {
        expr = expr.simplify();
        exprs = exprs.stream()
                     .map(elt -> elt.simplify()) 
                     .collect(Collectors.toList());  
        return this;
    }

    
}
