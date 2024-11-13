package AST.SimpleStmt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import AST.SimpleStmt.Expr.*;

class ReturnTest {

    @Test
    void testReturnInitialization() {
        Return returnStmt = new Return();
        assertNull(returnStmt.getExpr(), "Expr doit être null au départ");
    }

    @Test
    void testSetExpr() {
        Return returnStmt = new Return();
        Expr expr = new OrExpr();
        returnStmt.setExpr(expr);
        assertEquals(expr, returnStmt.getExpr(), "L'expression ne correspond pas après l'appel de setExpr");
    }
}
