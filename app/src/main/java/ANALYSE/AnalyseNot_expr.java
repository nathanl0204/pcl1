package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseNot_expr{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("ident", "(", "[", "integer", "string", "True", "False", "None")) ;


    public AnalyseNot_expr(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (validetoken.contains(currentToken)){
            AnalyseComp_expr R1 = new AnalyseComp_expr(this.token_stack);
            R1.Analyse();
        }
        else if(currentToken.equals("not")){
            this.token_stack.remove(0);
            AnalyseComp_expr R1 = new AnalyseComp_expr(this.token_stack);
            R1.Analyse();
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}