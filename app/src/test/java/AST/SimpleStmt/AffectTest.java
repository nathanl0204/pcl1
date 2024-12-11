package AST.SimpleStmt;

import AST.SimpleStmt.Expr.*;
import AST.SimpleStmt.Expr.TermExpr.Ident;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AffectTest {

    @Test
    void testAffectInitialization() {
        Affect affectStmt = new Affect();
        assertNull(affectStmt.getIdent(), "Ident doit être null au départ");
        assertNull(affectStmt.getExpr(), "Expr doit être null au départ");
    }

    @Test
    void testSetIdent() {
        Affect affectStmt = new Affect();
        Ident ident = new Ident();
        affectStmt.setIdent(ident);
        assertEquals(ident, affectStmt.getIdent(), "Ident ne correspond pas après l'appel de setIdent");
    }

    @Test
    void testSetExpr() {
        Affect affectStmt = new Affect();
        Expr expr = new OrExpr();
        affectStmt.setExpr(expr);
        assertEquals(expr, affectStmt.getExpr(), "L'expression ne correspond pas après l'appel de setExpr");
    }
}
