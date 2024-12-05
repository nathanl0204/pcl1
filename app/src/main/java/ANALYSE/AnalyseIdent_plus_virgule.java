package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseIdent_plus_virgule{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();

    public AnalyseIdent_plus_virgule(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (currentToken.equals("ident")){
            this.token_stack.remove(0);
            AnalyseIdent_plus_virgule_rest R1 = new AnalyseIdent_plus_virgule_rest(this.token_stack);
            R1.Analyse();
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}
