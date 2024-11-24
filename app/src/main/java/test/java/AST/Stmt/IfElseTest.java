package AST.Stmt;

import AST.Suite;
import AST.SimpleStmt.Expr.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IfElseTest {

    @Test
    void testIfElseInitialization() {
        IfElse ifElseStmt = new IfElse();
        assertNull(ifElseStmt.getIf(), "Expr if doit être null au départ");
        assertNull(ifElseStmt.getThen(), "Suite then doit être null au départ");
        assertNull(ifElseStmt.getElse(), "Suite else doit être null au départ");
    }

    @Test
    void testSetExpr() {
        IfElse ifElseStmt = new IfElse();
        Expr expr = new OrExpr();
        ifElseStmt.setIf(expr);
        assertEquals(expr, ifElseStmt.getIf(), "L'expression if ne correspond pas après l'appel de setIf");
    }

    @Test
    void testSetThen() {
        IfElse ifElseStmt = new IfElse();
        Suite thenSuite = new Suite();
        ifElseStmt.setThen(thenSuite);
        assertEquals(thenSuite, ifElseStmt.getThen(), "La suite then ne correspond pas après l'appel de setThen");
    }

    @Test
    void testSetElse() {
        IfElse ifElseStmt = new IfElse();
        Suite elseSuite = new Suite();
        ifElseStmt.setElse(elseSuite);
        assertEquals(elseSuite, ifElseStmt.getElse(), "La suite else ne correspond pas après l'appel de setElse");
    }
}