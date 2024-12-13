package AST.SimpleStmt.Expr;

import AST.SimpleStmt.SimpleStmt;

public interface Expr extends SimpleStmt {
    public Expr simplify();
}
