package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseOpt_newline{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("def", "ident", "(", "return", "print", "[", "for","if", "not", "integer", "string", "True", "False", "None")) ;


    public AnalyseOpt_newline(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (currentToken.equals("NEWLINE")){
            this.token_stack.remove(0);
        }
        else if (validetoken.contains(currentToken)){
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}
