import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import javax.swing.text.html.parser.Parser;

import AST.*;
import AST.SimpleStmt.*;
import AST.SimpleStmt.Expr.*;
import AST.SimpleStmt.Expr.TermExpr.*;
import AST.SimpleStmt.Expr.TermExpr.Const.*;
import AST.Stmt.*;
import AST.File;

public class ParserTest {
    private Parser parser = new Parser();
    private Lexeur lexeur = new Lexeur();
    private static final String TEST_FILE_PATH = "src/main/resources/test.py";
    File ast;

    @BeforeAll
    public void setup() {
        
        lexeur.token_stack.clear();
        lexeur.indentations_stack.clear();
        lexeur.indentations_stack.add(0);
        
        lexeur.execute(TEST_FILE_PATH);
        
        parser.setTokenQueueFromTokenStack(lexeur.token_stack);
        ast = parser.startAnalyse();
    }

    @Test
    public void testParserAnalyse() {
        try {
            assertNotNull(ast, "L'AST ne devrait pas être null");
            
            assertFalse(ast.getDefs().isEmpty(), "Il devrait y avoir au moins une définition de fonction");
            Def funcI = ast.getDefs().get(0);
            assertEquals("i", funcI.getIdent().getName(), "La première fonction devrait s'appeler 'i'");
            assertEquals(3, funcI.getIdents().size(), "La fonction 'i' devrait avoir 3 paramètres");
            
            assertFalse(ast.getStmts().isEmpty(), "Il devrait y avoir des statements");
            
            boolean foundAffectA = false;
            boolean foundAffectB = false;
            boolean foundAffectC = false;
            
            for (Stmt stmt : ast.getStmts()) {
                if (stmt instanceof Affect) {
                    Affect affect = (Affect) stmt;
                    if (affect.getLeft() instanceof Ident) {
                        Ident ident = (Ident) affect.getLeft();
                        switch (ident.getName()) {
                            case "a":
                                foundAffectA = true;
                                break;
                            case "b":
                                foundAffectB = true;
                                break;
                            case "c":
                                foundAffectC = true;
                                break;
                        }
                    }
                }
            }
            
            assertTrue(foundAffectA, "L'affectation de 'a' devrait être présente");
            assertTrue(foundAffectB, "L'affectation de 'b' devrait être présente");
            assertTrue(foundAffectC, "L'affectation de 'c' devrait être présente");
            
        } catch (ParsingError e) {
            fail("Le parsing ne devrait pas échouer: " + e.getMessage());
        }
    }

    @Test
    public void testIfStatement() {
        boolean foundIfStatement = false;
        
        for (Stmt stmt : ast.getStmts()) {
            if (stmt instanceof If || stmt instanceof IfElse) {
                foundIfStatement = true;
                break;
            }
        }
        
        assertTrue(foundIfStatement, "Au moins une structure If devrait être présente");
    }

    @Test
    public void testForLoop() {
        boolean foundForLoop = false;
        
        for (Stmt stmt : ast.getStmts()) {
            if (stmt instanceof For) {
                foundForLoop = true;
                For forStmt = (For) stmt;
                assertNotNull(forStmt.getIdent(), "La variable d'itération ne devrait pas être null");
                assertNotNull(forStmt.getExpr(), "L'expression du for ne devrait pas être null");
                break;
            }
        }
        
        assertTrue(foundForLoop, "Une boucle For devrait être présente");
    }

    @Test
    public void testListDeclaration() {
        boolean foundListDeclaration = false;
        
        for (Stmt stmt : ast.getStmts()) {
            if (stmt instanceof Affect) {
                Affect affect = (Affect) stmt;
                if (affect.getRight() instanceof ListType) {
                    foundListDeclaration = true;
                    ListType list = (ListType) affect.getRight();
                    assertEquals(5, list.getExprs().size(), "La liste devrait contenir 5 éléments");
                    break;
                }
            }
        }
        
        assertTrue(foundListDeclaration, "Une déclaration de liste devrait être présente");
    }

    @Test
    public void testComplexExpression() {
        boolean foundComplexExpr = false;
        
        for (Stmt stmt : ast.getStmts()) {
            if (stmt instanceof Affect && ((Affect) stmt).getLeft() instanceof Ident) {
                Ident ident = (Ident) ((Affect) stmt).getLeft();
                if (ident.getName().equals("a")) {
                    foundComplexExpr = true;
                    Expr expr = ((Affect) stmt).getRight();
                    assertNotNull(expr, "L'expression complexe ne devrait pas être null");
                    break;
                }
            }
        }
        
        assertTrue(foundComplexExpr, "Une expression complexe avec opérateurs logiques devrait être présente");
    }
}