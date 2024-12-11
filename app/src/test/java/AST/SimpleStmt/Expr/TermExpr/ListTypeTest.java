package AST.SimpleStmt.Expr.TermExpr;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import AST.SimpleStmt.Expr.*;

public class ListTypeTest {

    @Test
    public void testDefaultConstructor() {
        ListType listType = new ListType();
        assertNotNull(listType.getExprs());
        assertTrue(listType.getExprs().isEmpty());
    }

    @Test
    public void testAddExprs() {
        ListType listType = new ListType();
        Expr expr = new OrExpr();  
        listType.addExprs(expr);
        
        assertEquals(1, listType.getExprs().size());
        assertSame(expr, listType.getExprs().get(0));
    }

    @Test
    public void testSimplify() {
        Expr expr = new OrExpr();  
        ListType listType = new ListType();
        listType.addExprs(expr);

        ListType simplifiedListType = listType.simplify();

        assertSame(listType, simplifiedListType);
        assertNotNull(listType.getExprs());
        assertEquals(1, listType.getExprs().size());
    }

}

