
public class Main {
    
    public static void main(String[] args) {
        
        if (args.length != 2 || (!args[0].equals("Lexer") && !args[0].equals("Parser"))){

        }
        else {
            //"src/main/resources/test.py"
            System.out.println("Execution du Lexer");;
            Lexeur.execute(args[1]);
            if (args[0].equals("Parser")){
                System.out.println("Execution du Parser");
                Parser parser = new Parser(Lexeur.token_stack);
                parser.top_down_parsing_algorithm();

            }
            

        }
    }
}
