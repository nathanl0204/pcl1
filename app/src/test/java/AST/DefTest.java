package AST;

import AST.SimpleStmt.Expr.TermExpr.Ident;
import AST.Stmt.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DefTest {

    @Test
    void testDefInitialization() {
        Def def = new Def();
        assertNull(def.getIdent(), "Ident doit être null au départ");
        assertTrue(def.getIdents().isEmpty(), "La liste des idents doit être vide au départ");
        assertNull(def.getSuite(), "Suite doit être null au départ");
    }

    @Test
    void testAddIdent() {
        Def def = new Def();
        Ident ident = new Ident();
        def.setIdent(ident);
        assertEquals(1, def.getIdents().size(), "La liste des idents devrait contenir un ident après l'ajout");
    }

    @Test
    void testSetIdent() {
        Def def = new Def();
        Ident ident = new Ident();
        def.setIdent(ident);
        assertEquals(ident, def.getIdent(), "Ident ne correspond pas après l'appel de setIdent");
    }

    @Test
    void testSetSuite() {
        Def def = new Def();
        Suite suite = new Suite();
        suite.addStmt(new If()); // Classe anonyme pour tester
        def.setSuite(suite);
        assertEquals(suite, def.getSuite(), "La suite ne correspond pas après l'appel de setSuite");
    }
}
