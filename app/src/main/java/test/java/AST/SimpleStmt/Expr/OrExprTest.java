package AST.SimpleStmt.Expr;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import AST.SimpleStmt.Expr.TermExpr.Const.BoolType;

class OrExprTest {

    @Test
    void testOrExprInitialization() {
        OrExpr orExpr = new OrExpr();
        assertNotNull(orExpr.getOrExprs());
        assertTrue(orExpr.getOrExprs().isEmpty());
    }

    @Test
    void testAddOrExpr() {
        OrExpr orExpr = new OrExpr();
        AndExpr andExpr = new AndExpr();
        
        orExpr.addOrExpr(andExpr);
        
        List<AndExpr> exprs = orExpr.getOrExprs();
        assertEquals(1, exprs.size());
        assertSame(andExpr, exprs.get(0));
    }

    @Test
    void testSimplifyEmptyExprs() {
        OrExpr orExpr = new OrExpr();
        OrExpr simplified = orExpr.simplify();
        
        assertTrue(simplified instanceof BoolType);
        assertFalse(((BoolType) simplified).getValue());
    }

    @Test
    void testSimplifyWithTrue() {
        OrExpr orExpr = new OrExpr();
        orExpr.addOrExpr(new AndExpr());
        orExpr.addOrExpr(new BoolType(true));

        OrExpr simplified = orExpr.simplify();

        assertTrue(simplified instanceof BoolType);
        assertTrue(((BoolType) simplified).getValue());
    }

    @Test
    void testSimplifyWithFalse() {
        OrExpr orExpr = new OrExpr();
        orExpr.addOrExpr(new BoolType(false));
        orExpr.addOrExpr(new BoolType(false));

        OrExpr simplified = orExpr.simplify();

        assertTrue(simplified instanceof BoolType);
        assertFalse(((BoolType) simplified).getValue());
    }

    @Test
    void testSimplifyWithTrueAndFalse() {
        OrExpr orExpr = new OrExpr();
        orExpr.addOrExpr(new BoolType(true));
        orExpr.addOrExpr(new BoolType(false));

        OrExpr simplified = orExpr.simplify();

        assertTrue(simplified instanceof BoolType);
        assertTrue(((BoolType) simplified).getValue());
    }
}
