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
        File tempFile = File.createTempFile("test", ".mpy");
        tempFile.deleteOnExit();
        
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("def i(a,b,c) :");
            writer.println("    return --o+-a-y*j*3-j//-u*p");
            writer.println("def add(a,b):return a+b");
            writer.println("def multiply(a,b):return a*b");
            writer.println("x=10");
            writer.println("y=20");
            writer.println("z=add(x,y)");
            writer.println("if x<y:print(\"x is smaller\")");
            writer.println("else:print(\"x is not smaller\")");
            writer.println("for i in range(5):print(i)");
            writer.println("a=[1,2,3]");
            writer.println("b=a[1]+a[2]");
            writer.println("a[0]=b");
            writer.println("x=5");
            writer.println("y=3");
            writer.println("z=--x+y*2-4//2");
            writer.println("if x==5:print(\"X is 5\")");
            writer.println("else :");
            writer.println("    if y>x:print(\"Y is greater than X\")");
            writer.println("    else:print(\"Neither condition is true\")");
            writer.println("for i in range(10):");
            writer.println(" if i%2==0:print(i)");
            writer.println("result=--x*y//z+x%y");
            writer.println("print(result)");
            writer.println("a=[10,20,30]");
            writer.println("b=a[0]+a[1]");
            writer.println("a[2]=b");
            writer.println("f=3");
            writer.println("g=f*2+1");
            writer.println("h=g//f");
            writer.println("if f>g:print(\"F is greater than G\")");
            writer.println("else :");
            writer.println("    if h==f:print(\"H is equal to F\")");
            writer.println("    else:print(\"No condition matched\")");
            writer.println("i=7");
            writer.println("j=3");
            writer.println("k=--i+j*2");
            writer.println("m=-x");
            writer.println("n=m+y");
            writer.println("a=[1,2,3]");
            writer.println("b=a[1]+a[2]");
            writer.println("a[1]=b");
            writer.println("result=multiply(x,y)");
            writer.println("print(result)");
            writer.println("if x!=y:print(\"X and Y are not equal\")");
            writer.println("result=(x+y)*(z-1)//2");
            writer.println("print(result)");
            writer.println("list_of_numbers=[1,2,3,4,5]");
            writer.println("index=2");
            writer.println("result=list_of_numbers[index]*2");
            writer.println("print(result)");
            writer.println("a=[10,20,30]");
            writer.println("b=a[0]*2+a[1]");
            writer.println("a[2]=b");
            writer.println("a[0][1]=100");
            writer.println("a[1]=b+5");
            writer.println("--x+y//3");
            writer.println("a = -10");
            writer.println("b = 20");
            writer.println("c = a + b");
            writer.println("d = a -b");
            writer.println("e = a * b");
            writer.println("f = a // b");
            writer.println("g = a % b");
            writer.println("if a < b:");
            writer.println("    print(\"test\")");
            writer.println("    return(\"test\")");
            writer.println("else:");
            writer.println("    print(\"test\")");
            writer.println("if e :");
            writer.println("    print(\"test\")");
            writer.println("for x in range(5):");
            writer.println("    print(x)");
            writer.println("resultat = addition(a, b)");
            writer.println("print(\"Le résultat de l'addition est:\")");
            writer.println("print(resultat)");
            writer.println("liste = [1, 2, 3, 4, 5]");
            writer.println("b = ---a");
            writer.println("a[1][1] = 2");
            writer.println("a[1] > a[2][1]");
            writer.println("a or b + 3 and 4 or not 1 or 15 and 400 >= e + r//2 and not 400");
            writer.println("a[1] = 2");
            writer.println("a = 2");
        }

        lexeur.execute(tempFile.getAbsolutePath());

        for (String[] token : lexeur.token_stack) {
            assertEquals(3, token.length, "Chaque token devrait avoir 3 éléments");
            assert token[0] != null && !token[0].isEmpty() : "Le type du token ne devrait pas être null ou vide";
            assert token[1] != null && !token[1].isEmpty() : "La valeur du token ne devrait pas être null ou vide";
            assert token[2] != null && !token[2].isEmpty() : "Le numéro de ligne ne devrait pas être null ou vide";
        }

        String[] validTypes = {"keyword", "id", "op", "ws", "number", "string", "relop", "EOF", "$"};
        for (String[] token : lexeur.token_stack) {
            boolean isValidType = false;
            for (String type : validTypes) {
                if (token[0].equals(type)) {
                    isValidType = true;
                    break;
                }
            }
            assert isValidType : "Type de token invalide trouvé: " + token[0];
        }

        String[] keywords = {"and", "def", "else", "for", "if", "True", "False", "in", "not", "or", "print", "return", "None"};
        for (String[] token : lexeur.token_stack) {
            if (token[0].equals("keyword")) {
                boolean isValidKeyword = false;
                for (String keyword : keywords) {
                    if (token[1].equals(keyword)) {
                        isValidKeyword = true;
                        break;
                    }
                }
                assert isValidKeyword : "Mot-clé invalide trouvé: " + token[1];
            }
        }

        String[] validOps = {"LP", "RP", "LB", "RB", "ADD", "SUB", "MULT", "DIV", "MOD", "DD", "COM", "EQ"};
        for (String[] token : lexeur.token_stack) {
            if (token[0].equals("op")) {
                boolean isValidOp = false;
                for (String op : validOps) {
                    if (token[1].equals(op)) {
                        isValidOp = true;
                        break;
                    }
                }
                assert isValidOp : "Opérateur invalide trouvé: " + token[1];
            }
        }

        String[] validRelops = {"EQ", "NE", "LT", "GT", "LE", "GE"};
        for (String[] token : lexeur.token_stack) {
            if (token[0].equals("relop")) {
                boolean isValidRelop = false;
                for (String relop : validRelops) {
                    if (token[1].equals(relop)) {
                        isValidRelop = true;
                        break;
                    }
                }
                assert isValidRelop : "Opérateur de comparaison invalide trouvé: " + token[1];
            }
        }

        int currentLine = 1;
        for (String[] token : lexeur.token_stack) {
            if (!token[2].equals("EOF") && !token[2].equals("$")) {
                int tokenLine = Integer.parseInt(token[2]);
                assert tokenLine >= currentLine : "Numéro de ligne incohérent: " + tokenLine;
                if (token[1].equals("NEWLINE")) {
                    currentLine++;
                }
            }
        }

        int lastIndex = lexeur.token_stack.size() - 1;
        assertEquals("$", lexeur.token_stack.get(lastIndex)[0], "Dernier token devrait être $");
        assertEquals("$", lexeur.token_stack.get(lastIndex)[1], "Dernier token devrait être $");
        assertEquals("$", lexeur.token_stack.get(lastIndex)[2], "Dernier token devrait être $");
        assertEquals("EOF", lexeur.token_stack.get(lastIndex - 1)[0], "Avant-dernier token devrait être EOF");
        assertEquals("EOF", lexeur.token_stack.get(lastIndex - 1)[1], "Avant-dernier token devrait être EOF");
        assertEquals("EOF", lexeur.token_stack.get(lastIndex - 1)[2], "Avant-dernier token devrait être EOF");
    }
}