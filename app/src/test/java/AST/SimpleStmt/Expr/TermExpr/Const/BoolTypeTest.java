package AST.SimpleStmt.Expr.TermExpr.Const;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoolTypeTest {

    @Test
    public void testDefaultConstructor() {
        BoolType bool = new BoolType();
        assertFalse(bool.getValue());
    }

    @Test
    public void testParameterizedConstructor() {
        BoolType bool = new BoolType(true);
        assertTrue(bool.getValue());
    }

    @Test
    public void testSetBoolType() {
        BoolType bool = new BoolType();
        bool.setValue(true);
        assertTrue(bool.getValue());
        bool.setValue(false);
        assertFalse(bool.getValue());
    }

    @Test
    public void testSimplify() {
        BoolType bool = new BoolType(true);
        assertSame(bool, bool.simplify());
    }
}