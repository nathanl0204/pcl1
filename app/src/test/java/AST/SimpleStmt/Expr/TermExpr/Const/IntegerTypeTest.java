package AST.SimpleStmt.Expr.TermExpr.Const;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IntegerTypeTest {

    @Test
    public void testDefaultConstructor() {
        IntegerType integerType = new IntegerType();
        assertEquals(0, integerType.getValue());
    }

    @Test
    public void testParameterizedConstructor() {
        IntegerType integerType = new IntegerType(42);
        assertEquals(42, integerType.getValue());
    }

    @Test
    public void testSetValue() {
        IntegerType integerType = new IntegerType();
        integerType.setValue(100);
        assertEquals(100, integerType.getValue());
        integerType.setValue(-50);
        assertEquals(-50, integerType.getValue());
    }

    @Test
    public void testSimplify() {
        IntegerType integerType = new IntegerType(42);
        assertSame(integerType, integerType.simplify());
    }
}