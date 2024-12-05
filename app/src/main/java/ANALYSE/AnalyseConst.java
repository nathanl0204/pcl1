package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseConst{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken1 = new ArrayList<>(Arrays.asList("integer", "string", "True", "False", "None")) ;
    public AnalyseConst(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (validetoken1.contains(currentToken)){
            this.token_stack.remove(0);
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}