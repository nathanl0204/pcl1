package AST;

import AST.Stmt.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FileTest {

    @Test
    void testFileInitialization() {
        File file = new File();
        assertNotNull(file.getDefs(), "La liste des defs ne doit pas être null");
        assertNotNull(file.getStmts(), "La liste des stmts ne doit pas être null");
        assertTrue(file.getDefs().isEmpty(), "La liste des defs doit être vide au départ");
        assertTrue(file.getStmts().isEmpty(), "La liste des stmts doit être vide au départ");
    }

    @Test
    void testAddDef() {
        File file = new File();
        Def def = new Def();
        file.addDefs(def);
        assertEquals(1, file.getDefs().size(), "La liste des defs devrait contenir un élément après l'ajout");
    }

    @Test
    void testAddStmt() {
        File file = new File();
        Stmt stmt = new If(); // Classe anonyme pour tester
        file.addStmts(stmt);
        assertEquals(1, file.getStmts().size(), "La liste des stmts devrait contenir un élément après l'ajout");
    }
}

