import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexeurTest {

    @BeforeEach
    public void setup() {
        Lexeur.token_stack.clear();
        Lexeur.indentations_stack.clear();
        Lexeur.indentations_stack.add(0);
    }

    @Test
    public void testLexeurTokens() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/test.py"))) {
            for (int i = 0; i < 2; i++) {
                String line = reader.readLine();
                if (line == null) break;
                Lexeur.Lex(new BufferedReader(new java.io.StringReader(line)), i + 1, true, false);
            }
        }

        ArrayList<String[]> expectedTokens = new ArrayList<>();

        expectedTokens.add(new String[]{"id", "a", "1"});
        expectedTokens.add(new String[]{"op", "EQ", "1"});
        expectedTokens.add(new String[]{"number", "10", "1"});
        expectedTokens.add(new String[]{"ws", "NEWLINE", "1"});
        expectedTokens.add(new String[]{"id", "b", "2"});
        expectedTokens.add(new String[]{"op", "EQ", "2"});
        expectedTokens.add(new String[]{"number", "20", "2"});
        expectedTokens.add(new String[]{"ws", "NEWLINE", "2"});

        System.out.println("Expected token count: " + expectedTokens.size());
        System.out.println("Actual token count: " + Lexeur.token_stack.size());

        assertEquals(expectedTokens.size(), Lexeur.token_stack.size(), "Le nombre de tokens générés n'est pas correct.");

        for (int i = 0; i < expectedTokens.size(); i++) {
            assertEquals(expectedTokens.get(i)[0], Lexeur.token_stack.get(i)[0], "Type de token incorrect pour le token " + i);
            assertEquals(expectedTokens.get(i)[1], Lexeur.token_stack.get(i)[1], "Valeur de token incorrecte pour le token " + i);
            assertEquals(expectedTokens.get(i)[2], Lexeur.token_stack.get(i)[2], "Numéro de ligne incorrect pour le token " + i);
        }
    }

}