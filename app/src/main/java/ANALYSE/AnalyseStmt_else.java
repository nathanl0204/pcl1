package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseStmt_else{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList( "EOF","ident", "(","END", "return", "print", "[", "for","if", "not", "integer", "string", "True", "False", "None")) ;


    public AnalyseStmt_else(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (currentToken.equals("else")){
            this.token_stack.remove(0);
            currentTokenArray = convert.getCurrentToken(this.token_stack);
            currentToken = convert.getConvertedValue(currentTokenArray);
            if (currentToken.equals(":")){
                this.token_stack.remove(0);
                AnalyseSuite R1 = new AnalyseSuite(this.token_stack);
                R1.Analyse();
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne t: " + token_stack.get(0)[2]);
            }

        }
        else if (validetoken.contains(currentToken)){
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}
