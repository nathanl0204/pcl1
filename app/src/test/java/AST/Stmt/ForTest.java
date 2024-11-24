package AST.Stmt;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import AST.Suite;
import AST.SimpleStmt.Expr.Expr;
import AST.SimpleStmt.Expr.OrExpr;
import AST.SimpleStmt.Expr.TermExpr.Ident;

class ForTest {
    @Test
    void testForInitialization() {
        For for_ = new For();
        assertNull(for_.getExpr(), "Ident ident doit être null au départ");
        assertNull(for_.getIdent(), "Expr expr doit être null au départ");
        assertNull(for_.getSuite(), "Suite suite doit être null au départ");
    }

    @Test
    void testSetExpr() {
        For for_ = new For();
        Expr expr = new OrExpr();
        for_.setExpr(expr);
        assertEquals(expr, for_.getExpr(), "L'expression ne correspond pas après l'appel de setExpr");
    }

    @Test
    void testSetIdent() {
        For for_ = new For();
        Ident ident = new Ident();
        for_.setIdent(ident);
        assertEquals(ident, for_.getIdent(), "L'identifiant ne correspond pas après l'appel de setIdent");
    }

    @Test
    void testSetSuite() {
        For for_ = new For();
        Suite suite = new Suite();
        for_.setSuite(suite);
        assertEquals(suite, for_.getSuite(), "La suite ne correspond pas après l'appel de setSuite");
    }

}
