package AST.SimpleStmt.Expr.TermExpr;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import AST.SimpleStmt.Expr.Expr;

public class IdentP extends TermExpr {
    private Ident ident;
    private List<Expr> exprs;

    public IdentP() {
        this.ident = null;
        this.exprs = new ArrayList<Expr>();
    }

    public IdentP(Ident ident, List<Expr> exprs) {
        this.ident = ident;
        this.exprs = exprs;
    }

    public Ident getIdent() {
        return ident;
    }

    public void setIdent(Ident ident) {
        this.ident = ident;
    }

    public List<Expr> getExprs() {
        return exprs;
    }

    public void addExprs(Expr expr) {
        this.exprs.add(expr);
    }
    
    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"FCT\"];\n");

        if (ident != null ){
            String identNodeName = nodeName + "_" + ident.hashCode(); 
            writer.write("  " + nodeName + " -- " + identNodeName + ";\n");
            ident.vizualisation(writer,identNodeName); 
        }

        if (exprs != null && !exprs.isEmpty()){
            String paramNodeName = nodeName + "_PARAM";
            writer.write("  " + nodeName + " -- " + paramNodeName + ";\n");
            writer.write("  " + paramNodeName + " [label=\"PARAM\"];\n");
            for (Expr expr : exprs){
                String exprNodeName = nodeName + "_" + expr.hashCode(); 
                writer.write("  " + paramNodeName + " -- " + exprNodeName + ";\n");
                expr.vizualisation(writer,exprNodeName); 
            }
        }
        
    }

    public TermExpr simplify(){
        if (exprs == null) return null; 
        exprs = exprs.stream()
                     .map(elt -> elt.simplify()) 
                     .collect(Collectors.toList());  

        exprs.removeIf(elt -> elt == null);
        if (exprs == null) return new Ident(ident.getName()); 
        
        return this;
    }
}
