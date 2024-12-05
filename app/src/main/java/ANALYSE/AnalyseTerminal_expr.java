package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseTerminal_expr{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("integer", "string", "True", "False", "None")) ;

    public AnalyseTerminal_expr(ArrayList<String[]> token_stack){
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
            AnalyseExpr_rest_ident R1 = new AnalyseExpr_rest_ident(this.token_stack);
            R1.Analyse();
        }
        else if(currentToken.equals("(")){
            this.token_stack.remove(0);
            AnalyseExpr R1 = new AnalyseExpr(this.token_stack);
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
        else if(currentToken.equals("[")){
            this.token_stack.remove(0);
            AnalyseExpr_etoile_virgule R1 = new AnalyseExpr_etoile_virgule(this.token_stack);
            R1.Analyse();
            currentTokenArray = convert.getCurrentToken(this.token_stack);
            currentToken = convert.getConvertedValue(currentTokenArray);
            if(currentToken.equals("]")){
                this.token_stack.remove(0);
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
            }
        }
        else if(validetoken.contains(currentToken)){
            AnalyseConst R2 = new AnalyseConst(this.token_stack);
            R2.Analyse();

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}