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

    @Override
    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'vizualisation'");
    }

    @Override
    public ExprTab simplify() {
        expr = expr.simplify();
        exprs = exprs.stream()
                     .map(elt -> elt.simplify()) 
                     .collect(Collectors.toList());  
        return this;
    }

    
}
