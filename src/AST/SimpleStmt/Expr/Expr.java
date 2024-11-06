package AST.SimpleStmt.Expr;

import AST.SimpleStmt.SimpleStmt;

public abstract class Expr extends SimpleStmt {
    public abstract Expr simplify();
}
