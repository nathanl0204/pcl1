package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseAnd_expr_rest{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("or","NEWLINE",")",":",",","=","[","]")) ;


    public AnalyseAnd_expr_rest(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (currentToken.equals("and")){
            AnalyseBinop_and R1 = new AnalyseBinop_and(this.token_stack);
            R1.Analyse();
            AnalyseAnd_expr R2 = new AnalyseAnd_expr(this.token_stack);
            R2.Analyse();
        }
        else if(validetoken.contains(currentToken)){

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}