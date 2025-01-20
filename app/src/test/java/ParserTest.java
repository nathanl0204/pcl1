import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class ParserTest {
    private Parser parser = new Parser();

    @Test 
    void testErrorAffectString(){

        ArrayList<String[]> tokens = new ArrayList<>();

        tokens.add(new String[]{"string", "a", "1"});
        tokens.add(new String[]{"op", "EQ", "1"});
        tokens.add(new String[]{"id", "b", "1"});
        tokens.add(new String[]{"ws", "NEWLINE", "1"});
        tokens.add(new String[]{"EOF", "EOF", "EOF"});
        tokens.add(new String[]{"$", "$", "$"});

        parser.setTokenQueueFromTokenStack(tokens);

        assertThrows(ParsingError.class,() -> parser.startAnalyse());

    }

    @Test 
    void testErrorAffectTrue(){
        
        ArrayList<String[]> tokens = new ArrayList<>();

        tokens.add(new String[]{"keyword", "True", "1"});
        tokens.add(new String[]{"op", "EQ", "1"});
        tokens.add(new String[]{"id", "b", "1"});
        tokens.add(new String[]{"ws", "NEWLINE", "1"});
        tokens.add(new String[]{"EOF", "EOF", "EOF"});
        tokens.add(new String[]{"$", "$", "$"});

        parser.setTokenQueueFromTokenStack(tokens);

        assertThrows(ParsingError.class,() -> parser.startAnalyse());

    }

    @Test 
    void testErrorAffectFalse(){
        
        ArrayList<String[]> tokens = new ArrayList<>();

        tokens.add(new String[]{"keyword", "False", "1"});
        tokens.add(new String[]{"op", "EQ", "1"});
        tokens.add(new String[]{"id", "b", "1"});
        tokens.add(new String[]{"ws", "NEWLINE", "1"});
        tokens.add(new String[]{"EOF", "EOF", "EOF"});
        tokens.add(new String[]{"$", "$", "$"});

        parser.setTokenQueueFromTokenStack(tokens);

        assertThrows(ParsingError.class,() -> parser.startAnalyse());

    }

    @Test 
    void testErrorAffectNone(){
        
        ArrayList<String[]> tokens = new ArrayList<>();

        tokens.add(new String[]{"keyword", "None", "1"});
        tokens.add(new String[]{"op", "EQ", "1"});
        tokens.add(new String[]{"id", "b", "1"});
        tokens.add(new String[]{"ws", "NEWLINE", "1"});
        tokens.add(new String[]{"EOF", "EOF", "EOF"});
        tokens.add(new String[]{"$", "$", "$"});

        parser.setTokenQueueFromTokenStack(tokens);

        assertThrows(ParsingError.class,() -> parser.startAnalyse());

    }

    @Test 
    void testErrorAffectParenthese(){
        
        ArrayList<String[]> tokens = new ArrayList<>();

        tokens.add(new String[]{"op", "LP", "1"});
        tokens.add(new String[]{"id", "a", "1"});
        tokens.add(new String[]{"op", "RP", "1"});
        tokens.add(new String[]{"op", "EQ", "1"});
        tokens.add(new String[]{"id", "b", "1"});
        tokens.add(new String[]{"ws", "NEWLINE", "1"});
        tokens.add(new String[]{"EOF", "EOF", "EOF"});
        tokens.add(new String[]{"$", "$", "$"});

        parser.setTokenQueueFromTokenStack(tokens);

        assertThrows(ParsingError.class,() -> parser.startAnalyse());

    }

    @Test 
    void testErrorAffectMinus(){
        
        ArrayList<String[]> tokens = new ArrayList<>();

        tokens.add(new String[]{"op", "SUB", "1"});
        tokens.add(new String[]{"id", "a", "1"});
        tokens.add(new String[]{"op", "EQ", "1"});
        tokens.add(new String[]{"id", "b", "1"});
        tokens.add(new String[]{"ws", "NEWLINE", "1"});
        tokens.add(new String[]{"EOF", "EOF", "EOF"});
        tokens.add(new String[]{"$", "$", "$"});

        parser.setTokenQueueFromTokenStack(tokens);

        assertThrows(ParsingError.class,() -> parser.startAnalyse());

    }

    @Test 
    void testErrorAffectNot(){
        
        ArrayList<String[]> tokens = new ArrayList<>();

        tokens.add(new String[]{"keyword", "not", "1"});
        tokens.add(new String[]{"id", "a", "1"});
        tokens.add(new String[]{"op", "EQ", "1"});
        tokens.add(new String[]{"id", "b", "1"});
        tokens.add(new String[]{"ws", "NEWLINE", "1"});
        tokens.add(new String[]{"EOF", "EOF", "EOF"});
        tokens.add(new String[]{"$", "$", "$"});

        parser.setTokenQueueFromTokenStack(tokens);

        assertThrows(ParsingError.class,() -> parser.startAnalyse());

    }

    @Test 
    void testErrorAffectSum(){
        
        ArrayList<String[]> tokens = new ArrayList<>();

        tokens.add(new String[]{"id", "a", "1"});
        tokens.add(new String[]{"op", "ADD", "1"});
        tokens.add(new String[]{"number", "2", "1"});
        tokens.add(new String[]{"op", "EQ", "1"});
        tokens.add(new String[]{"id", "b", "1"});
        tokens.add(new String[]{"ws", "NEWLINE", "1"});
        tokens.add(new String[]{"EOF", "EOF", "EOF"});
        tokens.add(new String[]{"$", "$", "$"});

        parser.setTokenQueueFromTokenStack(tokens);

        assertThrows(ParsingError.class,() -> parser.startAnalyse());

    }

    @Test 
    void testErrorAffectFct(){
        
        ArrayList<String[]> tokens = new ArrayList<>();

        tokens.add(new String[]{"id", "a", "1"});
        tokens.add(new String[]{"op", "LP", "1"});
        tokens.add(new String[]{"number", "2", "1"});
        tokens.add(new String[]{"op", "RP", "1"});        
        tokens.add(new String[]{"op", "EQ", "1"});
        tokens.add(new String[]{"id", "b", "1"});
        tokens.add(new String[]{"ws", "NEWLINE", "1"});
        tokens.add(new String[]{"EOF", "EOF", "EOF"});
        tokens.add(new String[]{"$", "$", "$"});

        parser.setTokenQueueFromTokenStack(tokens);

        assertThrows(ParsingError.class,() -> parser.startAnalyse());

    }

    @Test 
    void testNotErrorAffectIdent(){
        
        ArrayList<String[]> tokens = new ArrayList<>();

        tokens.add(new String[]{"id", "a", "1"});      
        tokens.add(new String[]{"op", "EQ", "1"});
        tokens.add(new String[]{"id", "b", "1"});
        tokens.add(new String[]{"ws", "NEWLINE", "1"});
        tokens.add(new String[]{"EOF", "EOF", "EOF"});
        tokens.add(new String[]{"$", "$", "$"});

        parser.setTokenQueueFromTokenStack(tokens);

        assertDoesNotThrow(() -> parser.startAnalyse());
    }

    @Test 
    void testNotErrorAffectTab(){
        
        ArrayList<String[]> tokens = new ArrayList<>();

        tokens.add(new String[]{"id", "a", "1"});  
        tokens.add(new String[]{"op", "LB", "1"});
        tokens.add(new String[]{"number", "2", "1"});
        tokens.add(new String[]{"op", "RB", "1"});      
        tokens.add(new String[]{"op", "EQ", "1"});
        tokens.add(new String[]{"id", "b", "1"});
        tokens.add(new String[]{"ws", "NEWLINE", "1"});
        tokens.add(new String[]{"EOF", "EOF", "EOF"});
        tokens.add(new String[]{"$", "$", "$"});

        parser.setTokenQueueFromTokenStack(tokens);

        assertDoesNotThrow(() -> parser.startAnalyse());
    }
}