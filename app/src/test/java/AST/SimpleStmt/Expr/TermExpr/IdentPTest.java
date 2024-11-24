package AST.SimpleStmt.Expr.TermExpr;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import AST.SimpleStmt.Expr.*;

public class IdentPTest {

    @Test
    public void testDefaultConstructor() {
        IdentP identP = new IdentP();
        assertNull(identP.getIdent());
        assertNotNull(identP.getExprs());
        assertTrue(identP.getExprs().isEmpty());
    }

    @Test
    public void testSetIdent() {
        IdentP identP = new IdentP();
        Ident ident = new Ident();
        identP.setIdent(ident);
        
        assertSame(ident, identP.getIdent());
    }

    @Test
    public void testAddExprs() {
        IdentP identP = new IdentP();
        Expr expr = new OrExpr(); 
        identP.addExprs(expr);
        
        assertEquals(1, identP.getExprs().size());
        assertSame(expr, identP.getExprs().get(0));
    }

    @Test
    public void testSimplify() {
        Expr expr = new Ident("a");  
        IdentP identP = new IdentP();
        identP.addExprs(expr);

        TermExpr simplifiedIdentP = identP.simplify();

        assertSame(identP, simplifiedIdentP);
        assertNotNull(identP.getExprs());
        assertEquals(1, identP.getExprs().size());
    }

}

