package AST.SimpleStmt;

import AST.SimpleStmt.Expr.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AffectTabTest {

    @Test
    void testAffectTabInitialization() {
        AffectTab affectStmt = new AffectTab();
        assertNull(affectStmt.getExpr1(), "Expr1 doit être null au départ");
        assertNull(affectStmt.getExpr2(), "Expr2 doit être null au départ");
        assertNull(affectStmt.getExpr3(), "Expr3 doit être null au départ");
    }

    @Test
    void testSetExpr1() {
        AffectTab affectStmt = new AffectTab();
        Expr expr = new OrExpr();
        affectStmt.setExpr1(expr);
        assertEquals(expr, affectStmt.getExpr1(), "L'expression ne correspond pas après l'appel de setExpr");
    }

    @Test
    void testSetExpr2() {
        AffectTab affectStmt = new AffectTab();
        Expr expr = new OrExpr();
        affectStmt.setExpr2(expr);
        assertEquals(expr, affectStmt.getExpr2(), "L'expression ne correspond pas après l'appel de setExpr");
    }

    @Test
    void testSetExpr3() {
        AffectTab affectStmt = new AffectTab();
        Expr expr = new OrExpr();
        affectStmt.setExpr3(expr);
        assertEquals(expr, affectStmt.getExpr3(), "L'expression ne correspond pas après l'appel de setExpr");
    }
}

