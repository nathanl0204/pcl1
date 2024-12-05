package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseOr_expr_rest{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("NEWLINE",")",":",",","=","[","]")) ;


    public AnalyseOr_expr_rest(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (currentToken.equals("or")){
            AnalyseBinop_or R1 = new AnalyseBinop_or(this.token_stack);
            R1.Analyse();
            AnalyseOr_expr R2 = new AnalyseOr_expr(this.token_stack);
            R2.Analyse();
        }
        else if(validetoken.contains(currentToken)){

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}