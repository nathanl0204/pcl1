package AST.SimpleStmt.Expr;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import AST.SimpleStmt.Expr.TermExpr.Ident;
import AST.SimpleStmt.Expr.TermExpr.Const.IntegerType;

class MinusExprTest {

    @Test
    void testMinusExprInitialization() {
        MinusExpr minusExpr = new MinusExpr();
        assertNull(minusExpr.getMinusExpr());
    }

    @Test
    void testSetMinusExpr() {
        MinusExpr minusExpr = new MinusExpr();
        MinusExpr exprToSet = new MinusExpr();
        minusExpr.setMinusExpr(exprToSet);
        assertSame(exprToSet, minusExpr.getMinusExpr());
    }

    @Test
    void testSimplifyDoubleNegation() {
        MinusExpr minusExpr = new MinusExpr();
        MinusExpr innerMinusExpr = new MinusExpr();
        IntegerType x = new IntegerType(); 

        innerMinusExpr.setMinusExpr(x);
        minusExpr.setMinusExpr(innerMinusExpr); 

        MinusExpr simplified = innerMinusExpr.simplify();
        
        assertSame(x, simplified.getMinusExpr());
    }

    @Test
    void testSimplifySingleNegation() {
        MinusExpr minusExpr = new MinusExpr();
        Ident x = new Ident(); 
        minusExpr.setMinusExpr(x);

        MinusExpr simplified = minusExpr.simplify();
        
        assertSame(minusExpr, simplified); 
    }
}
