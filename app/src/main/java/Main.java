
public class Main {
    
    public static void main(String[] args) {
        
        if (args.length != 2 || (!args[0].equals("Lexer") && !args[0].equals("Parser"))){
            System.out.println("Signature : gradle run --args=\"Lexer|Parser FilePath\"");
        }
        else {
            //"src/main/resources/test.py"
            System.out.println("Execution du Lexeur");;
            Lexeur.execute(args[1]);
            if (args[0].equals("Parser")){
                System.out.println("\nExecution du Parseur");
                /* Parser parser = new Parser(Lexeur.token_stack);
                parser.top_down_parsing_algorithm();
                parser.printComplexAST(); */

                ParserV2 parserV2 = new ParserV2();
                parserV2.setTokenQueueFromTokenStack(Lexeur.token_stack);
                parserV2.startAnalyse();
            }
        }
    }
}
