package test.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LexeurTest {

    public void testLexeur() {
        try {
            try (BufferedReader reader = new BufferedReader(new FileReader("src/test.py"))) {
                Lexeur lexeur = new Lexeur();
                lexeur.Lex(reader, 0, true, false);
                assertFalse(lexeur.token_stack.isEmpty(), "La pile de tokens ne doit pas être vide après lexing.");
                assertTokenType(lexeur, "a", "IDENTIFIER");
                assertTokenType(lexeur, "=", "ASSIGN");
                assertTokenType(lexeur, "10", "NUMBER");
                assertTokenType(lexeur, "b", "IDENTIFIER");
                assertTokenType(lexeur, "20", "NUMBER");
                assertTokenType(lexeur, "+", "OP");
                assertTokenType(lexeur, "-", "OP");
                assertTokenType(lexeur, "*", "OP");
                assertTokenType(lexeur, "/", "OP");
                assertTokenType(lexeur, "%", "OP");
                assertTokenType(lexeur, "<", "OP");
                assertTokenType(lexeur, "if", "IF");
                assertTokenType(lexeur, "elif", "ELIF");
                assertTokenType(lexeur, "else", "ELSE");
                assertTokenType(lexeur, "for", "FOR");
                assertTokenType(lexeur, "while", "WHILE");
                assertTokenType(lexeur, "def", "DEF");
                assertTokenType(lexeur, "print", "IDENTIFIER");
                assertTokenType(lexeur, "range", "IDENTIFIER");
                assertTokenType(lexeur, "[", "LBRACK");
                assertTokenType(lexeur, "]", "RBRACK");
                assertTokenType(lexeur, ":", "COLON");
                assertTokenType(lexeur, ",", "COMMA");
                assertTrue(lexeur.hasErrors(), "Le lexeur ne devrait pas être exempt d'erreurs.");
                assertFalse(lexeur.getErrorMessages().isEmpty(), "Le lexeur devrait avoir des messages d'erreur.");
                System.out.println("Messages d'erreur : " + lexeur.getErrorMessages());
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail("Une erreur d'I/O a été rencontrée lors du test du Lexeur.");
        }
    }
    
    private void assertTokenType(Lexeur lexeur, String expectedValue, String expectedType) {
        assertFalse(lexeur.token_stack.isEmpty(), "La pile de tokens est vide lors de la vérification de " + expectedValue);
        Token actualToken = lexeur.token_stack.pop();
        assertEquals(expectedValue, actualToken.getValue(), "Le token attendu " + expectedValue + " n'a pas été trouvé.");
        assertEquals(expectedType, actualToken.getType(), "Le type de token attendu " + expectedType + " n'a pas été trouvé.");
    }
}