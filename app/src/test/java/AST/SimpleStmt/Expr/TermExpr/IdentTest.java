package AST.SimpleStmt.Expr.TermExpr;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IdentTest {

    @Test
    public void testDefaultConstructor() {
        Ident ident = new Ident();
        assertNull(ident.getName());
    }

    @Test
    public void testConstructorWithName() {
        Ident ident = new Ident("testName");
        assertEquals("testName", ident.getName());
    }

    @Test
    public void testSetName() {
        Ident ident = new Ident();
        ident.setName("newName");
        assertEquals("newName", ident.getName());
    }

    @Test
    public void testSimplify() {
        Ident ident = new Ident("testName");
        Ident simplifiedIdent = ident.simplify();
        assertSame(ident, simplifiedIdent);  
    }
}
