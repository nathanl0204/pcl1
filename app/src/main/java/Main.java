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
            System.out.println("\n\nExecution du Lexeur\n\n");;
            Lexeur.execute(args[1]);
            if (args[0].equals("Parser")){
                System.out.println("\n\nExecution du Parseur\n\n");
                /*Parser parser = new Parser(Lexeur.token_stack);
                parser.top_down_parsing_algorithm();
                parser.printComplexAST();*/

                ParserV2 parserV2 = new ParserV2();
                parserV2.setTokenQueueFromTokenStack(Lexeur.token_stack);
                
                File result = parserV2.startAnalyse();


                FileWriter fileWriter = new FileWriter("/home/clem/TN/2A/pcl-grp03/app/src/main/resources/output.dot");
            
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("digraph G {\n");
                result.vizualisation(bufferedWriter, "root");
                bufferedWriter.write("}\n");
                bufferedWriter.close();
                fileWriter.close();
            }
        }
    }
}
