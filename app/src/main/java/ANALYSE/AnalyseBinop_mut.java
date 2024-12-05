package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseBinop_mut{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();

    public AnalyseBinop_mut(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (currentToken.equals("*")){
            this.token_stack.remove(0);
        }
        else if (currentToken.equals("//")){
            this.token_stack.remove(0);
        }
        else if (currentToken.equals("%")){
             this.token_stack.remove(0);
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}