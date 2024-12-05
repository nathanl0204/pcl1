package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseExpr_plus_virgule_rest{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken1 = new ArrayList<>(Arrays.asList(")","]")) ;
    public AnalyseExpr_plus_virgule_rest(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (currentToken.equals(",")){
            this.token_stack.remove(0);
            AnalyseExpr_plus_virgule R1 = new AnalyseExpr_plus_virgule(this.token_stack);
            R1.Analyse();
        }
        else if(validetoken1.contains(currentToken)){

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}