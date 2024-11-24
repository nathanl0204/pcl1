package AST.SimpleStmt.Expr.TermExpr;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import AST.SimpleStmt.Expr.*;

public class ParentheseTest {

    @Test
    public void testDefaultConstructor() {
        Parenthese parenthese = new Parenthese();
        assertNull(parenthese.getExpr());
    }

    @Test
    public void testSetExpr() {
        Parenthese parenthese = new Parenthese();
        Expr mockExpr = new OrExpr(); 
        parenthese.setExpr(mockExpr);
        assertSame(mockExpr, parenthese.getExpr());
    }

    @Test
    public void testSimplifyWithNullExpr() {
        Parenthese parenthese = new Parenthese();
        assertNull(parenthese.simplify());
    }

    @Test
    public void testSimplifyWithNonNullExpr() {
        Expr expr = new OrExpr();  
        Parenthese parenthese = new Parenthese();
        parenthese.setExpr(expr);

        assertSame(parenthese, parenthese.simplify());
    }
}
