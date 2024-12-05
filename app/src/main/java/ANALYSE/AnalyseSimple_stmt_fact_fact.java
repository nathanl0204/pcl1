package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseSimple_stmt_fact_fact{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();

    public AnalyseSimple_stmt_fact_fact(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (currentToken.equals("=")){
            this.token_stack.remove(0);
            AnalyseExpr R1 = new AnalyseExpr(this.token_stack);
            R1.Analyse();
        }
        else if (currentToken.equals("NEWLINE")){

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}
