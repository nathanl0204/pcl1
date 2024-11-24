package AST.SimpleStmt.Expr.TermExpr.Const;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringTypeTest {

    @Test
    public void testDefaultConstructor() {
        StringType stringType = new StringType();
        assertNull(stringType.getValue());
    }

    @Test
    public void testParameterizedConstructor() {
        StringType stringType = new StringType("Hello");
        assertEquals("Hello", stringType.getValue());
    }

    @Test
    public void testSetValue() {
        StringType stringType = new StringType();
        stringType.setValue("World");
        assertEquals("World", stringType.getValue());
        stringType.setValue(null);
        assertNull(stringType.getValue());
    }

    @Test
    public void testSimplify() {
        StringType stringType = new StringType("Simplified");
        assertSame(stringType, stringType.simplify());
    }
}