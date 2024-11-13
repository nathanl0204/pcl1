package AST.SimpleStmt.Expr.TermExpr;

public class Ident extends TermExpr {
    private String name;

    public Ident() {
        this.name = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ident simplify(){
        return this;
    }
}
