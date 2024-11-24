package AST.Stmt;

import AST.Suite;
import AST.SimpleStmt.Expr.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IfTest {

    @Test
    void testIfInitialization() {
        If ifStmt = new If();
        assertNull(ifStmt.getIf(), "Expr if doit être null au départ");
        assertNull(ifStmt.getThen(), "Suite then doit être null au départ");
    }

    @Test
    void testSetExpr() {
        If ifStmt = new If();
        Expr expr = new OrExpr();
        ifStmt.setIf(expr);
        assertEquals(expr, ifStmt.getIf(), "L'expression if ne correspond pas après l'appel de setIf");
    }

    @Test
    void testSetThen() {
        If ifStmt = new If();
        Suite thenSuite = new Suite();
        ifStmt.setThen(thenSuite);
        assertEquals(thenSuite, ifStmt.getThen(), "La suite then ne correspond pas après l'appel de setThen");
    }
}