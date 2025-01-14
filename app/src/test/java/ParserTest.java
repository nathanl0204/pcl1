import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import ANALYSE.AnalyseException;

public class ParserTest {
    private ParserV2 parser;
    private ArrayList<String[]> tokenStack;

    @BeforeEach
    public void setup() {
        parser = new ParserV2();
        tokenStack = new ArrayList<>();
    }

    @Test
    public void testSimpleAssignment() {
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "0"});
        tokenStack.add(new String[]{"ident", "x", "1"});
        tokenStack.add(new String[]{"=", "=", "1"});
        tokenStack.add(new String[]{"integer", "42", "1"});
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "1"});
        tokenStack.add(new String[]{"EOF", "EOF", "2"});

        parser.setTokenQueueFromTokenStack(tokenStack);
        assertDoesNotThrow(() -> parser.startAnalyse());
    }

    @Test
    public void testSimplePrint() {
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "0"});
        tokenStack.add(new String[]{"print", "print", "1"});
        tokenStack.add(new String[]{"(", "(", "1"});
        tokenStack.add(new String[]{"integer", "42", "1"});
        tokenStack.add(new String[]{")", ")", "1"});
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "1"});
        tokenStack.add(new String[]{"EOF", "EOF", "2"});

        parser.setTokenQueueFromTokenStack(tokenStack);
        assertDoesNotThrow(() -> parser.startAnalyse());
    }

    @Test
    public void testSimpleFunction() {
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "0"});
        tokenStack.add(new String[]{"def", "def", "1"});
        tokenStack.add(new String[]{"ident", "foo", "1"});
        tokenStack.add(new String[]{"(", "(", "1"});
        tokenStack.add(new String[]{")", ")", "1"});
        tokenStack.add(new String[]{":", ":", "1"});
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "1"});
        tokenStack.add(new String[]{"BEGIN", "BEGIN", "2"});
        tokenStack.add(new String[]{"return", "return", "2"});
        tokenStack.add(new String[]{"integer", "42", "2"});
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "2"});
        tokenStack.add(new String[]{"END", "END", "3"});
        tokenStack.add(new String[]{"EOF", "EOF", "4"});

        parser.setTokenQueueFromTokenStack(tokenStack);
        assertDoesNotThrow(() -> parser.startAnalyse());
    }

    @Test
    public void testIfStatement() {
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "0"});
        tokenStack.add(new String[]{"if", "if", "1"});
        tokenStack.add(new String[]{"ident", "x", "1"});
        tokenStack.add(new String[]{">", ">", "1"});
        tokenStack.add(new String[]{"integer", "0", "1"});
        tokenStack.add(new String[]{":", ":", "1"});
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "1"});
        tokenStack.add(new String[]{"BEGIN", "BEGIN", "2"});
        tokenStack.add(new String[]{"print", "print", "2"});
        tokenStack.add(new String[]{"(", "(", "2"});
        tokenStack.add(new String[]{"ident", "x", "2"});
        tokenStack.add(new String[]{")", ")", "2"});
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "2"});
        tokenStack.add(new String[]{"END", "END", "3"});
        tokenStack.add(new String[]{"EOF", "EOF", "4"});

        parser.setTokenQueueFromTokenStack(tokenStack);
        assertDoesNotThrow(() -> parser.startAnalyse());
    }

    @Test
    public void testForLoop() {
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "0"});
        tokenStack.add(new String[]{"for", "for", "1"});
        tokenStack.add(new String[]{"ident", "i", "1"});
        tokenStack.add(new String[]{"in", "in", "1"});
        tokenStack.add(new String[]{"ident", "range", "1"});
        tokenStack.add(new String[]{":", ":", "1"});
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "1"});
        tokenStack.add(new String[]{"BEGIN", "BEGIN", "2"});
        tokenStack.add(new String[]{"print", "print", "2"});
        tokenStack.add(new String[]{"(", "(", "2"});
        tokenStack.add(new String[]{"ident", "i", "2"});
        tokenStack.add(new String[]{")", ")", "2"});
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "2"});
        tokenStack.add(new String[]{"END", "END", "3"});
        tokenStack.add(new String[]{"EOF", "EOF", "4"});

        parser.setTokenQueueFromTokenStack(tokenStack);
        assertDoesNotThrow(() -> parser.startAnalyse());
    }

    @Test
    public void testInvalidSyntax() {
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "0"});
        tokenStack.add(new String[]{"ident", "x", "1"});
        tokenStack.add(new String[]{"=", "=", "1"});
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "1"});
        tokenStack.add(new String[]{"EOF", "EOF", "2"});

        parser.setTokenQueueFromTokenStack(tokenStack);
        assertThrows(AnalyseException.class, () -> parser.startAnalyse());
    }

    @Test
    public void testEmptyTokenStack() {
        parser.setTokenQueueFromTokenStack(tokenStack);
        assertThrows(AnalyseException.class, () -> parser.startAnalyse());
    }

    @Test
    public void testComplexExpression() {
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "0"});
        tokenStack.add(new String[]{"ident", "x", "1"});
        tokenStack.add(new String[]{"=", "=", "1"});
        tokenStack.add(new String[]{"(", "(", "1"});
        tokenStack.add(new String[]{"integer", "42", "1"});
        tokenStack.add(new String[]{"+", "+", "1"});
        tokenStack.add(new String[]{"ident", "y", "1"});
        tokenStack.add(new String[]{")", ")", "1"});
        tokenStack.add(new String[]{"*", "*", "1"});
        tokenStack.add(new String[]{"integer", "2", "1"});
        tokenStack.add(new String[]{"NEWLINE", "NEWLINE", "1"});
        tokenStack.add(new String[]{"EOF", "EOF", "2"});

        parser.setTokenQueueFromTokenStack(tokenStack);
        assertDoesNotThrow(() -> parser.startAnalyse());
    }
}