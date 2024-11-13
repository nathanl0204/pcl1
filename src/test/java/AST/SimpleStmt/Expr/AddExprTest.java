package AST.SimpleStmt.Expr;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import AST.SimpleStmt.Expr.TermExpr.Const.*;

class AddExprTest {

    @Test
    void testSetLeft() {
        AddExpr addExpr = new AddExpr();
        IntegerType left = new IntegerType(5);
        addExpr.setLeft(left);
        
        assertSame(left, addExpr.getLeft());
    }

    @Test
    void testSetRight() {
        AddExpr addExpr = new AddExpr();
        IntegerType right = new IntegerType(3);
        addExpr.setRight(right);

        assertSame(right, addExpr.getRight());
    }

    @Test
    void testSetSymbole() {
        AddExpr addExpr = new AddExpr();
        addExpr.setAddSymbole(AddBinop.ADD);

        assertEquals(AddBinop.ADD, addExpr.getAddSymbole());
    }


    @Test
    void testSimplifyWithIntegerType() {
        AddExpr addExpr = new AddExpr();
        IntegerType left = new IntegerType(5);
        IntegerType right = new IntegerType(3);
        addExpr.setLeft(left);
        addExpr.setRight(right);
        addExpr.setAddSymbole(AddBinop.ADD);

        IntegerType simplified = (IntegerType) addExpr.simplify();
        
        assertEquals(8, simplified.getValue()); 
    }

    @Test
    void testSimplifyWithBoolAndInteger() {
        AddExpr addExpr = new AddExpr();
        Bool left = new Bool(true);  // true = 1
        IntegerType right = new IntegerType(2);
        addExpr.setLeft(left);
        addExpr.setRight(right);
        addExpr.setAddSymbole(AddBinop.ADD);

        IntegerType simplified = (IntegerType) addExpr.simplify();
        
        assertEquals(3, simplified.getValue());  
    }
    
    @Test
    void testSimplifyWithIntegerAndBool() {
        AddExpr addExpr = new AddExpr();
        IntegerType left = new IntegerType(10);
        Bool right = new Bool(false);  // false = 0
        addExpr.setLeft(left);
        addExpr.setRight(right);
        addExpr.setAddSymbole(AddBinop.SUB);

        IntegerType simplified = (IntegerType) addExpr.simplify();
        
        assertEquals(10, simplified.getValue()); 
    }

    @Test
    void testSimplifyWithStringType() {
        AddExpr addExpr = new AddExpr();
        StringType left = new StringType("Hello");
        StringType right = new StringType(" World");
        addExpr.setLeft(left);
        addExpr.setRight(right);
        addExpr.setAddSymbole(AddBinop.ADD);

        StringType simplified = (StringType) addExpr.simplify();
        
        assertEquals("Hello World", simplified.getValue());  // Concatenation
    }
   
}