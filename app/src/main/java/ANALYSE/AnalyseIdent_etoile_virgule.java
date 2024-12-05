package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseIdent_etoile_virgule{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();

    public AnalyseIdent_etoile_virgule(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (currentToken.equals("ident")){
            AnalyseIdent_plus_virgule R1 = new AnalyseIdent_plus_virgule(this.token_stack);
            R1.Analyse();
        }
        else if (currentToken.equals(")")){

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}
