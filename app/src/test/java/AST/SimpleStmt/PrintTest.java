package AST.SimpleStmt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import AST.SimpleStmt.Expr.*;

class PrintTest {

    @Test
    void testPrintInitialization() {
        Print printStmt = new Print();
        assertNull(printStmt.getExpr(), "Expr doit être null au départ");
    }

    @Test
    void testSetExpr() {
        Print printStmt = new Print();
        Expr expr = new OrExpr() {}; // Classe anonyme pour tester
        printStmt.setExpr(expr);
        assertEquals(expr, printStmt.getExpr(), "L'expression ne correspond pas après l'appel de setExpr");
    }
}