package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseStmt_plus_rest{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList( "ident", "(", "return", "print", "[", "for","if", "not", "integer", "string", "True", "False", "None")) ;
    private ArrayList<String> validetoken1 = new ArrayList<>(Arrays.asList( "EOF","END")) ;

    public AnalyseStmt_plus_rest(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (validetoken.contains(currentToken)){
            AnalyseStmt_plus R1 = new AnalyseStmt_plus(this.token_stack);
            R1.Analyse();
        }
        else if (validetoken1.contains(currentToken)){

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}
