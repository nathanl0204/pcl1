import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private Parser parser;
    private ArrayList<String[]> tokens;

    @BeforeEach
    void setUp() {
        tokens = new ArrayList<>();
        parser = new Parser(tokens);
    }

    @Test
    void testLL1TableInitialization() {
        Map<String, Map<String, String>> ll1Table = parser.getLL1Table();
        assertNotNull(ll1Table, "La table LL(1) ne doit pas être null");
        assertTrue(ll1Table.containsKey("file"), "La table LL(1) doit contenir 'file' comme non-terminal");
        assertTrue(ll1Table.get("file").containsKey("EOF"), "'file' doit avoir une entrée pour EOF");
    }

    @Test
    void testGetCurrentToken() {
        tokens.add(new String[]{"keyword", "def", "1"});
        tokens.add(new String[]{"id", "main", "1"});

        String[] currentToken = parser.getCurrentToken();

        assertNotNull(currentToken, "Le token courant ne doit pas être null");
        assertArrayEquals(new String[]{"keyword", "def", "1"}, currentToken, "Le token courant doit correspondre au premier token dans la pile");
    }

    @Test
    void testGetConvertedValue() {
        ArrayList<String[]> tokens = new ArrayList<>();
        Parser parser = new Parser(tokens);

        String[] tokenKeyword = {"keyword", "def", "1"};
        assertEquals("def", parser.getConvertedValue(tokenKeyword), "Le token 'keyword' devrait être correctement converti");

        String[] tokenId = {"id", "main", "2"};
        assertEquals("ident", parser.getConvertedValue(tokenId), "Le token 'id' devrait être converti en 'ident'");

        String[] tokenNumber = {"number", "42", "3"};
        assertEquals("integer", parser.getConvertedValue(tokenNumber), "Le token 'number' devrait être converti en 'integer'");

        String[] tokenOp = {"op", "LP", "4"};
        assertEquals("(", parser.getConvertedValue(tokenOp), "Le token 'op LP' devrait être converti en '('");

        String[] tokenRelop = {"relop", "LE", "5"};
        assertEquals("<=", parser.getConvertedValue(tokenRelop), "Le token 'relop LE' devrait être converti en '<='");
    }

    @Test
    void testRemoveUnecessaryTokens() {
        tokens.add(new String[]{"error", "invalid", "1"});
        tokens.add(new String[]{"com", "comment", "2"});
        tokens.add(new String[]{"keyword", "def", "3"});

        parser.remove_unecessary_tokens();

        assertEquals(1, parser.token_stack.size(), "Il devrait rester un seul token après suppression.");
        assertArrayEquals(new String[]{"keyword", "def", "3"}, parser.token_stack.get(0), "Le token restant devrait être 'def'");
    }

    @Test
    void testGetTokensToNextLign() {
        tokens.add(new String[]{"id", "main", "1"});
        tokens.add(new String[]{"op", "LP", "1"});
        tokens.add(new String[]{"NEWLINE", "", "1"});
        tokens.add(new String[]{"id", "x", "2"});

        parser.get_tokens_to_next_lign("some_rule");
    }
}