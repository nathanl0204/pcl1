import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import AST.File;

public class Main {
    
    public static void main(String[] args) throws IOException {
        
        if (args.length != 2 || (!args[0].equals("Lexer") && !args[0].equals("Parser"))){
            System.out.println("Signature : gradle run --args=\"Lexer|Parser FilePath\"");
        }
        else {
            //"src/main/resources/test.py"
            System.out.println("\nExecution du Lexeur\n");;
            Lexeur.execute(args[1]);
            if (args[0].equals("Parser")){
                System.out.println("Execution du Parseur\n");

                Parser parser = new Parser();
                parser.setTokenQueueFromTokenStack(Lexeur.token_stack);
                
                File result = parser.startAnalyse();

                FileWriter fileWriter = new FileWriter("/home/clem/TN/2A/pcl-grp03/app/src/main/resources/output.dot");
            
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("graph G {\n");

                result.simplify();
                result.vizualisation(bufferedWriter);

                bufferedWriter.write("}\n");
                bufferedWriter.close();
                fileWriter.close();
            }
        }
    }
}
