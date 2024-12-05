package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseSimple_stmt_fact{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList( "NEWLINE","=")) ;

    public AnalyseSimple_stmt_fact(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if(validetoken.contains(currentToken)){
            AnalyseSimple_stmt_fact_fact R1 = new AnalyseSimple_stmt_fact_fact(this.token_stack);
            R1.Analyse();
        }
        else if(currentToken.equals("[")){
            this.token_stack.remove(0);
            AnalyseExpr R1 = new AnalyseExpr(this.token_stack);
            R1.Analyse();
            currentTokenArray = convert.getCurrentToken(this.token_stack);
            currentToken = convert.getConvertedValue(currentTokenArray);
            if(currentToken.equals("]")){
                this.token_stack.remove(0);
                AnalyseExpr_crochet_etoile R2 = new AnalyseExpr_crochet_etoile(this.token_stack);
                R2.Analyse();
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
            }
            
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}