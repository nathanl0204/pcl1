package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseExpr_plus_virgule{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken1 = new ArrayList<>(Arrays.asList("ident","(","[","not", "integer", "string", "True", "False", "None")) ;
    public AnalyseExpr_plus_virgule(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (validetoken1.contains(currentToken)){
            AnalyseExpr R1 = new AnalyseExpr(this.token_stack);
            R1.Analyse();
            AnalyseExpr_plus_virgule_rest R2 = new AnalyseExpr_plus_virgule_rest(this.token_stack);
            R2.Analyse();
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}