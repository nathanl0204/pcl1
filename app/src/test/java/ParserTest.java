import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import AST.*;
import AST.SimpleStmt.*;
import AST.SimpleStmt.Expr.*;
import AST.SimpleStmt.Expr.TermExpr.*;
import AST.SimpleStmt.Expr.TermExpr.Const.*;
import AST.Stmt.*;
import AST.File;

public class ParserTest {
    private Lexeur lexeur;
    private Parser parser;
    private static final String TEST_FILE_PATH = "src/main/resources/test.mpy";

    @BeforeEach
    public void setup() {
        lexeur = new Lexeur();
        parser = new Parser();
        
        lexeur.token_stack.clear();
        lexeur.indentations_stack.clear();
        lexeur.indentations_stack.add(0);
        
        lexeur.execute(TEST_FILE_PATH);
        
        parser.setTokenQueueFromTokenStack(lexeur.token_stack);
    }

    @Test
    public void testFunctionDefinitions() {
        File ast = parser.startAnalyse();
        assertNotNull(ast, "L'AST ne devrait pas être null");
        
        assertFalse(ast.getDefs().isEmpty(), "Il devrait y avoir des définitions de fonctions");
        
        Def funcI = ast.getDefs().get(0);
        assertEquals("i", funcI.getIdent().getName(), "La première fonction devrait s'appeler 'i'");
        assertEquals(3, funcI.getIdents().size(), "La fonction 'i' devrait avoir 3 paramètres");
        
        Def funcAdd = ast.getDefs().get(1);
        assertEquals("add", funcAdd.getIdent().getName(), "La deuxième fonction devrait s'appeler 'add'");
        assertEquals(2, funcAdd.getIdents().size(), "La fonction 'add' devrait avoir 2 paramètres");
        
        Def funcMultiply = ast.getDefs().get(2);
        assertEquals("multiply", funcMultiply.getIdent().getName(), "La troisième fonction devrait s'appeler 'multiply'");
        assertEquals(2, funcMultiply.getIdents().size(), "La fonction 'multiply' devrait avoir 2 paramètres");
    }

    @Test
    public void testVariableAssignments() {
        File ast = parser.startAnalyse();
        
        boolean foundX = false;
        boolean foundY = false;
        boolean foundZ = false;
        
        for (Stmt stmt : ast.getStmts()) {
            if (stmt instanceof Affect) {
                Affect affect = (Affect) stmt;
                if (affect.getLeft() instanceof Ident) {
                    Ident ident = (Ident) affect.getLeft();
                    switch (ident.getName()) {
                        case "x":
                            foundX = true;
                            if (affect.getRight() instanceof IntegerType) {
                                assertEquals(10, ((IntegerType) affect.getRight()).getValue());
                            }
                            break;
                        case "y":
                            foundY = true;
                            if (affect.getRight() instanceof IntegerType) {
                                assertEquals(20, ((IntegerType) affect.getRight()).getValue());
                            }
                            break;
                        case "z":
                            foundZ = true;
                            assertTrue(affect.getRight() instanceof IdentP, "z devrait être assigné à un appel de fonction");
                            break;
                    }
                }
            }
        }
        
        assertTrue(foundX, "La variable x devrait être déclarée");
        assertTrue(foundY, "La variable y devrait être déclarée");
        assertTrue(foundZ, "La variable z devrait être déclarée");
    }

    @Test
    public void testIfStatements() {
        File ast = parser.startAnalyse();
        boolean foundIfStatement = false;
        boolean foundIfElseStatement = false;
        
        for (Stmt stmt : ast.getStmts()) {
            if (stmt instanceof If) {
                foundIfStatement = true;
            } else if (stmt instanceof IfElse) {
                foundIfElseStatement = true;
                IfElse ifElse = (IfElse) stmt;
                assertNotNull(ifElse.getIf(), "La condition de if-else ne devrait pas être null");
                assertNotNull(ifElse.getThen(), "La suite du if ne devrait pas être null");
                assertNotNull(ifElse.getElse(), "La suite du else ne devrait pas être null");
            }
        }
        
        assertTrue(foundIfStatement, "Au moins une structure If devrait être présente");
        assertTrue(foundIfElseStatement, "Au moins une structure If-Else devrait être présente");
    }

    @Test
    public void testForLoops() {
        File ast = parser.startAnalyse();
        boolean foundForLoop = false;
        
        for (Stmt stmt : ast.getStmts()) {
            if (stmt instanceof For) {
                foundForLoop = true;
                For forStmt = (For) stmt;
                assertNotNull(forStmt.getIdent(), "La variable d'itération ne devrait pas être null");
                assertNotNull(forStmt.getExpr(), "L'expression du for ne devrait pas être null");
                assertNotNull(forStmt.getSuite(), "La suite du for ne devrait pas être null");
            }
        }
        
        assertTrue(foundForLoop, "Au moins une boucle For devrait être présente");
    }

    @Test
    public void testListOperations() {
        File ast = parser.startAnalyse();
        boolean foundListDeclaration = false;
        boolean foundListAccess = false;
        boolean foundListAssignment = false;
        
        for (Stmt stmt : ast.getStmts()) {
            if (stmt instanceof Affect) {
                Affect affect = (Affect) stmt;
                
                if (affect.getRight() instanceof ListType) {
                    foundListDeclaration = true;
                    ListType list = (ListType) affect.getRight();
                    assertFalse(list.getExprs().isEmpty(), "La liste ne devrait pas être vide");
                }
                
                if (affect.getRight() instanceof AddExpr) {
                    if (((AddExpr) affect.getRight()).getLeft() instanceof ExprTab) {
                        foundListAccess = true;
                    }
                }
                
                if (affect.getLeft() instanceof ExprTab) {
                    foundListAssignment = true;
                }
            }
        }
        
        assertTrue(foundListDeclaration, "Une déclaration de liste devrait être présente");
        assertTrue(foundListAccess, "Un accès à un élément de liste devrait être présent");
        assertTrue(foundListAssignment, "Une affectation à un élément de liste devrait être présente");
    }

    @Test
    public void testComplexExpressions() {
        File ast = parser.startAnalyse();
        boolean foundUnaryMinus = false;
        boolean foundArithmeticExpr = false;
        boolean foundLogicalExpr = false;
        
        for (Stmt stmt : ast.getStmts()) {
            if (stmt instanceof Affect) {
                Expr expr = ((Affect) stmt).getRight();
                
                if (expr instanceof MinusExpr) {
                    foundUnaryMinus = true;
                }
                
                if (expr instanceof AddExpr || expr instanceof MutExpr) {
                    foundArithmeticExpr = true;
                }
                
                if (expr instanceof OrExpr || expr instanceof AndExpr) {
                    foundLogicalExpr = true;
                }
            }
        }
        
        assertTrue(foundUnaryMinus, "Une expression avec moins unaire devrait être présente");
        assertTrue(foundArithmeticExpr, "Une expression arithmétique complexe devrait être présente");
        assertTrue(foundLogicalExpr, "Une expression logique devrait être présente");
    }
}