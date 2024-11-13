package AST.SimpleStmt.Expr;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import AST.SimpleStmt.Expr.TermExpr.Const.*;

class MutExprTest {

    @Test
    void testSetLeft() {
        MutExpr mutExpr = new MutExpr();
        IntegerType left = new IntegerType(5);
        mutExpr.setLeft(left);
        
        assertSame(left, mutExpr.getLeft());
    }

    @Test
    void testSetRight() {
        MutExpr mutExpr = new MutExpr();
        IntegerType right = new IntegerType(3);
        mutExpr.setRight(right);

        assertSame(right, mutExpr.getRight());
    }

    @Test
    void testSetSymbole() {
        MutExpr mutExpr = new MutExpr();
        mutExpr.setMutSymbole(MutBinop.MULT);

        assertEquals(MutBinop.MULT, mutExpr.getMutSymbole());
    }


    @Test
    void testSimplifyWithIntegerTypeMult() {
        MutExpr mutExpr = new MutExpr();
        IntegerType left = new IntegerType(5);
        IntegerType right = new IntegerType(3);
        mutExpr.setLeft(left);
        mutExpr.setRight(right);
        mutExpr.setMutSymbole(MutBinop.MULT);

        IntegerType simplified = (IntegerType) mutExpr.simplify();
        
        assertEquals(15, simplified.getValue()); 
    }

    @Test
    void testSimplifyWithIntegerTypeDiv() {
        MutExpr mutExpr = new MutExpr();
        IntegerType left = new IntegerType(5);
        IntegerType right = new IntegerType(3);
        mutExpr.setLeft(left);
        mutExpr.setRight(right);
        mutExpr.setMutSymbole(MutBinop.DIV);

        IntegerType simplified = (IntegerType) mutExpr.simplify();
        
        assertEquals(1, simplified.getValue()); 
    }

    @Test
    void testSimplifyWithIntegerTypeMod() {
        MutExpr mutExpr = new MutExpr();
        IntegerType left = new IntegerType(5);
        IntegerType right = new IntegerType(3);
        mutExpr.setLeft(left);
        mutExpr.setRight(right);
        mutExpr.setMutSymbole(MutBinop.MOD);

        IntegerType simplified = (IntegerType) mutExpr.simplify();
        
        assertEquals(2, simplified.getValue()); 
    }

    @Test
    void testSimplifyWithBoolAndInteger() {
        MutExpr mutExpr = new MutExpr();
        Bool left = new Bool(true);  // true = 1
        IntegerType right = new IntegerType(2);
        mutExpr.setLeft(left);
        mutExpr.setRight(right);
        mutExpr.setMutSymbole(MutBinop.MULT);

        IntegerType simplified = (IntegerType) mutExpr.simplify();
        
        assertEquals(2, simplified.getValue());  
    }
    
    @Test
    void testSimplifyWithIntegerAndBool() {
        MutExpr mutExpr = new MutExpr();
        IntegerType left = new IntegerType(10);
        Bool right = new Bool(false);  // false = 0
        mutExpr.setLeft(left);
        mutExpr.setRight(right);
        mutExpr.setMutSymbole(MutBinop.MULT);

        IntegerType simplified = (IntegerType) mutExpr.simplify();
        
        assertEquals(0, simplified.getValue()); 
    }
   
}