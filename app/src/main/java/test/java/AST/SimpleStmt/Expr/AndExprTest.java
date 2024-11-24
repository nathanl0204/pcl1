package AST.SimpleStmt.Expr;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import AST.SimpleStmt.Expr.TermExpr.Const.BoolType;

class AndExprTest {

    @Test
    void testAndExprInitialization() {
        AndExpr andExpr = new AndExpr();
        assertNotNull(andExpr.getAndExprs());
        assertTrue(andExpr.getAndExprs().isEmpty());
    }

    @Test
    void testAddAndExpr() {
        AndExpr andExpr = new AndExpr();
        NotExpr notExpr = new NotExpr();
        
        andExpr.addAndExpr(notExpr);
        
        List<NotExpr> exprs = andExpr.getAndExprs();
        assertEquals(1, exprs.size());
        assertSame(notExpr, exprs.get(0));
    }

    @Test
    void testSimplifyEmptyExprs() {
        AndExpr andExpr = new AndExpr();
        AndExpr simplified = andExpr.simplify();
        
        assertTrue(simplified instanceof BoolType);
        assertTrue(((BoolType) simplified).getValue());
    }

    @Test
    void testSimplifyWithFalse() {
        AndExpr andExpr = new AndExpr();
        andExpr.addAndExpr(new BoolType(false));
        andExpr.addAndExpr(new BoolType(true));

        AndExpr simplified = andExpr.simplify();

        assertTrue(simplified instanceof BoolType);
        assertFalse(((BoolType) simplified).getValue());
    }

    @Test
    void testSimplifyWithTrue() {
        AndExpr andExpr = new AndExpr();
        andExpr.addAndExpr(new BoolType(true));
        andExpr.addAndExpr(new BoolType(true));

        AndExpr simplified = andExpr.simplify();

        assertTrue(simplified instanceof AndExpr);
    }

    @Test
    void testSimplifyWithTrueAndFalse() {
        AndExpr andExpr = new AndExpr();
        andExpr.addAndExpr(new BoolType(true));
        andExpr.addAndExpr(new BoolType(false));

        AndExpr simplified = andExpr.simplify();

        assertTrue(simplified instanceof BoolType);
        assertFalse(((BoolType) simplified).getValue());
    }
}