package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseStmt_plus{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList( "ident", "(", "return", "print", "[", "for","if", "not", "integer", "string", "True", "False", "None")) ;


    public AnalyseStmt_plus(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (validetoken.contains(currentToken)){
            AnalyseStmt R1 = new AnalyseStmt(this.token_stack);
            R1.Analyse();
            AnalyseStmt_plus_rest R2 = new AnalyseStmt_plus_rest(this.token_stack);
            R2.Analyse();

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}
