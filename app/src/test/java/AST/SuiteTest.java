package AST;

import AST.Stmt.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SuiteTest {

    @Test
    void testSuiteInitialization() {
        Suite suite = new Suite();
        assertTrue(suite.getStmts().isEmpty(), "La liste des stmts doit être vide au départ");
    }

    @Test
    void testAddStmt() {
        Suite suite = new Suite();
        Stmt stmt = new If();
        suite.addStmt(stmt);
        assertEquals(1, suite.getStmts().size(), "La liste des stmts devrait contenir un élément après l'ajout");
    }
}

