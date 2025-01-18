import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.io.PrintWriter;

public class LexeurTest {
    private Lexeur lexeur;

    @BeforeEach
    public void setup() {
        lexeur = new Lexeur();
        lexeur.token_stack.clear();
        lexeur.indentations_stack.clear();
        lexeur.indentations_stack.add(0);
    }

    @Test
    public void testLexeurExecute() throws IOException {
        File tempFile = File.createTempFile("test", ".py");
        tempFile.deleteOnExit();
        
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("def i(a,b,c) :");
            writer.println("    return --o+-a-y*j*3-j//-u*p");
        }

        lexeur.execute(tempFile.getAbsolutePath());

        ArrayList<String[]> expectedTokens = new ArrayList<>();
        
        expectedTokens.add(new String[]{"keyword", "def", "1"});
        expectedTokens.add(new String[]{"id", "i", "1"});
        expectedTokens.add(new String[]{"op", "LP", "1"});
        expectedTokens.add(new String[]{"id", "a", "1"});
        expectedTokens.add(new String[]{"op", "COM", "1"});
        expectedTokens.add(new String[]{"id", "b", "1"});
        expectedTokens.add(new String[]{"op", "COM", "1"});
        expectedTokens.add(new String[]{"id", "c", "1"});
        expectedTokens.add(new String[]{"op", "RP", "1"});
        expectedTokens.add(new String[]{"op", "DD", "1"});
        expectedTokens.add(new String[]{"ws", "NEWLINE", "1"});
        expectedTokens.add(new String[]{"ws", "BEGIN", "2"});
        expectedTokens.add(new String[]{"keyword", "return", "2"});
        expectedTokens.add(new String[]{"op", "SUB", "2"});
        expectedTokens.add(new String[]{"op", "SUB", "2"});
        expectedTokens.add(new String[]{"id", "o", "2"});
        expectedTokens.add(new String[]{"op", "ADD", "2"});
        expectedTokens.add(new String[]{"op", "SUB", "2"});
        expectedTokens.add(new String[]{"id", "a", "2"});
        expectedTokens.add(new String[]{"op", "SUB", "2"});
        expectedTokens.add(new String[]{"id", "y", "2"});
        expectedTokens.add(new String[]{"op", "MULT", "2"});
        expectedTokens.add(new String[]{"id", "j", "2"});
        expectedTokens.add(new String[]{"op", "MULT", "2"});
        expectedTokens.add(new String[]{"number", "3", "2"});
        expectedTokens.add(new String[]{"op", "SUB", "2"});
        expectedTokens.add(new String[]{"id", "j", "2"});
        expectedTokens.add(new String[]{"op", "DIV", "2"});
        expectedTokens.add(new String[]{"op", "SUB", "2"});
        expectedTokens.add(new String[]{"id", "u", "2"});
        expectedTokens.add(new String[]{"op", "MULT", "2"});
        expectedTokens.add(new String[]{"id", "p", "2"});
        expectedTokens.add(new String[]{"ws", "NEWLINE", "2"});
        expectedTokens.add(new String[]{"EOF", "EOF", "EOF"});
        expectedTokens.add(new String[]{"$", "$", "$"});

        assertEquals(expectedTokens.size(), lexeur.token_stack.size(), "Le nombre de tokens générés n'est pas correct.");

        for (int i = 0; i < expectedTokens.size(); i++) {
            assertEquals(expectedTokens.get(i)[0], lexeur.token_stack.get(i)[0], 
                "Type de token incorrect pour le token " + i + ". Attendu: " + expectedTokens.get(i)[0] + ", Obtenu: " + lexeur.token_stack.get(i)[0]);
            assertEquals(expectedTokens.get(i)[1], lexeur.token_stack.get(i)[1], 
                "Valeur de token incorrecte pour le token " + i + ". Attendu: " + expectedTokens.get(i)[1] + ", Obtenu: " + lexeur.token_stack.get(i)[1]);
            assertEquals(expectedTokens.get(i)[2], lexeur.token_stack.get(i)[2], 
                "Numéro de ligne incorrect pour le token " + i + ". Attendu: " + expectedTokens.get(i)[2] + ", Obtenu: " + lexeur.token_stack.get(i)[2]);
        }
    }

}