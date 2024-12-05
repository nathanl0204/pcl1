package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseExpr_rest_ident{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken1 = new ArrayList<>(Arrays.asList("and","or","NEWLINE",")",":",",","=","[","]","<=",">=",">","<","!=","==","+","-","*","//","%")) ;

    public AnalyseExpr_rest_ident(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (currentToken.equals("(")){
            this.token_stack.remove(0);
            AnalyseExpr_etoile_virgule R1 = new AnalyseExpr_etoile_virgule(this.token_stack);
            R1.Analyse();
            currentTokenArray = convert.getCurrentToken(this.token_stack);
            currentToken = convert.getConvertedValue(currentTokenArray);
            if(currentToken.equals(")")){
                this.token_stack.remove(0);
            }
            else{
                 throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
            }
        }
        else if(validetoken1.contains(currentToken)){

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}