package AST.SimpleStmt.Expr.TermExpr;

import AST.SimpleStmt.Expr.ExprTab;

public abstract class TermExpr extends ExprTab {
    public abstract TermExpr simplify();
}
