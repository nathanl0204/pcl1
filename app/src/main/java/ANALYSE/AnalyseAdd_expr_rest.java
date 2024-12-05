package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseAdd_expr_rest{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken1 = new ArrayList<>(Arrays.asList("and","or","NEWLINE",")",":",",","=","[","]","<=",">=",">","<","!=","==")) ;
    private ArrayList<String> validetoken2 = new ArrayList<>(Arrays.asList("-","+")) ;

    public AnalyseAdd_expr_rest(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (validetoken2.contains(currentToken)){
            AnalyseBinop_add R1 = new AnalyseBinop_add(this.token_stack);
            R1.Analyse();
            AnalyseAdd_expr R2 = new AnalyseAdd_expr(this.token_stack);
            R2.Analyse();
        }
        else if(validetoken1.contains(currentToken)){

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}