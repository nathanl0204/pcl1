package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseExpr{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList( "ident", "(", "[",  "not", "integer", "string", "True", "False", "None")) ;


    public AnalyseExpr(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (validetoken.contains(currentToken)){
            AnalyseOr_expr R1 = new AnalyseOr_expr(this.token_stack);
            R1.Analyse();
            AnalyseExpr_crochet_etoile R2 = new AnalyseExpr_crochet_etoile(this.token_stack);
            R2.Analyse();

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}