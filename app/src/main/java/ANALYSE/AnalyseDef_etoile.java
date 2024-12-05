package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseDef_etoile{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList( "ident", "(", "return", "print", "[", "for","if", "not", "integer", "string", "True", "False", "None")) ;


    public AnalyseDef_etoile(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (currentToken.equals("def")){
            AnalyseDeft R1 = new AnalyseDeft(this.token_stack);
            R1.Analyse();
            AnalyseDef_etoile R2 = new AnalyseDef_etoile(this.token_stack);
            R2.Analyse();

        }
        else if (validetoken.contains(currentToken)){
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}
