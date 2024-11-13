package AST.SimpleStmt.Expr;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import AST.SimpleStmt.Expr.TermExpr.Const.Bool;

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
        
        assertTrue(simplified instanceof Bool);
        assertFalse(((Bool) simplified).getBool());
    }

    @Test
    void testSimplifyWithTrue() {
        OrExpr orExpr = new OrExpr();
        orExpr.addOrExpr(new AndExpr());
        orExpr.addOrExpr(new Bool(true));

        OrExpr simplified = orExpr.simplify();

        assertTrue(simplified instanceof Bool);
        assertTrue(((Bool) simplified).getBool());
    }

    @Test
    void testSimplifyWithFalse() {
        OrExpr orExpr = new OrExpr();
        orExpr.addOrExpr(new Bool(false));
        orExpr.addOrExpr(new Bool(false));

        OrExpr simplified = orExpr.simplify();

        assertTrue(simplified instanceof Bool);
        assertFalse(((Bool) simplified).getBool());
    }

    @Test
    void testSimplifyWithTrueAndFalse() {
        OrExpr orExpr = new OrExpr();
        orExpr.addOrExpr(new Bool(true));
        orExpr.addOrExpr(new Bool(false));

        OrExpr simplified = orExpr.simplify();

        assertTrue(simplified instanceof Bool);
        assertTrue(((Bool) simplified).getBool());
    }
}
