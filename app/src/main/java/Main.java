import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import AST.File;

public class Main {
    
    public static void main(String[] args) throws IOException {
        
        if (args.length < 2 || (!args[0].equals("Lexer") && !args[0].equals("Parser"))){
            System.out.println("Signature : gradle run --args=\"Lexer|Parser FilePath\" option : --stack | --simplify");
        }
        else {
            //"src/main/resources/test.py"
            System.out.println("\nExecution du Lexeur\n");;
            Lexeur lexeur = new Lexeur();
            lexeur.execute(args[1]);
            if (Arrays.asList(args).contains("--stack"))
                lexeur.print_tokens();

            if (args[0].equals("Parser")){
                System.out.println("Execution du Parseur\n");

                Parser parser = new Parser();
                parser.setTokenQueueFromTokenStack(lexeur.token_stack);
                
                File result = parser.startAnalyse();

                 FileWriter fileWriter = new FileWriter("/home/clem/TN/2A/pcl-grp03/app/src/main/resources/output.dot");
            
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("graph AST {\n");

                if (Arrays.asList(args).contains("--simplify"))
                    result.simplify();
                result.vizualisation(bufferedWriter);

                bufferedWriter.write("}\n");
                bufferedWriter.close();
                fileWriter.close(); 
            }
        }
    }
}
