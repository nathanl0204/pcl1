package AST.SimpleStmt.Expr;

import AST.SimpleStmt.Expr.TermExpr.Ident;
import AST.SimpleStmt.Expr.TermExpr.Const.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompExprTest {

    @Test
    void testSimplifyWithIntegerComparison() {
        CompExpr compExpr = new CompExpr();
        IntegerType left = new IntegerType(5);
        IntegerType right = new IntegerType(10);
        
        compExpr.setLeft(left);
        compExpr.setRight(right);
        compExpr.setCompSymbole(CompBinop.LESS_THAN);
        
        BoolType result = (BoolType) compExpr.simplify();
        assertNotNull(result);
        assertTrue(result.getValue()); 
    }

    @Test
    void testSimplifyWithBooleanComparison() {
        CompExpr compExpr = new CompExpr();
        BoolType left = new BoolType(true);
        BoolType right = new BoolType(false);
        
        compExpr.setLeft(left);
        compExpr.setRight(right);
        compExpr.setCompSymbole(CompBinop.EQUAL);
        
        BoolType result = (BoolType) compExpr.simplify();
        assertNotNull(result);
        assertFalse(result.getValue());
    }

    @Test
    void testSimplifyWithStringComparison() {
        CompExpr compExpr = new CompExpr();
        StringType left = new StringType("apple");
        StringType right = new StringType("banana");
        
        compExpr.setLeft(left);
        compExpr.setRight(right);
        compExpr.setCompSymbole(CompBinop.LESS_THAN);
        
        BoolType result = (BoolType) compExpr.simplify();
        assertNotNull(result);
        assertTrue(result.getValue());
    }

    @Test
    void testSimplifyWithIntegerAndBool() {
        CompExpr compExpr = new CompExpr();
        IntegerType left = new IntegerType(5);
        BoolType right = new BoolType(true);
        
        compExpr.setLeft(left);
        compExpr.setRight(right);
        compExpr.setCompSymbole(CompBinop.GREATER_THAN);
        
        BoolType result = (BoolType) compExpr.simplify();
        assertNotNull(result);
        assertTrue(result.getValue()); 
    }

    @Test
    void testSimplifyWhenNoSimplifiable() {
        CompExpr compExpr = new CompExpr();
        Ident left = new Ident();
        Ident right = new Ident(); 

        compExpr.setLeft(left);
        compExpr.setRight(right);
        
        CompExpr result = (CompExpr) compExpr.simplify();
        assertSame(compExpr, result);
    }
}
