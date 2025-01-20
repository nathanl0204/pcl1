package AST.SimpleStmt.Expr;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import AST.SimpleStmt.Expr.TermExpr.Ident;
import AST.SimpleStmt.Expr.TermExpr.Const.*;

class NotExprTest {

    @Test
    void testNotExprInitialization() {
        NotExpr notExpr = new NotExpr();
        assertNull(notExpr.getNotExpr());
    }

    @Test
    void testSetExpr() {
        NotExpr notExpr = new NotExpr();
        NotExpr subExpr = new NotExpr();
        
        notExpr.setNotExpr(subExpr);
        
        assertSame(subExpr, notExpr.getNotExpr());
    }

    @Test
    void testSimplifyDoubleNot() {
        NotExpr notExpr = new NotExpr();
        NotExpr innerNotExpr = new NotExpr();
        Ident innerExpr = new Ident();

        innerNotExpr.setNotExpr(innerExpr);
        notExpr.setNotExpr(innerNotExpr);

        NotExpr simplified = notExpr.simplify();
        
        assertSame(innerExpr, simplified);
    }

    @Test
    void testSimplifySimplesNot() {
        NotExpr notExpr = new NotExpr();
        NotExpr innerNotExpr = new NotExpr();
        Ident innerExpr = new Ident();

        innerNotExpr.setNotExpr(innerExpr);
        notExpr.setNotExpr(innerNotExpr);

        NotExpr simplified = notExpr.simplify();
        
        assertSame(innerExpr, simplified);
    }

    @Test
    void testSimplifyWithTrue() {
        NotExpr notExpr = new NotExpr();
        notExpr.setNotExpr(new BoolType(true));

        NotExpr simplified = notExpr.simplify();
        
        assertTrue(simplified instanceof BoolType);
        assertFalse(((BoolType) simplified).getValue());
    }

    @Test
    void testSimplifyWithFalse() {
        NotExpr notExpr = new NotExpr();
        notExpr.setNotExpr(new BoolType(false));

        NotExpr simplified = notExpr.simplify();
        
        assertTrue(simplified instanceof BoolType);
        assertTrue(((BoolType) simplified).getValue());
    }
}
