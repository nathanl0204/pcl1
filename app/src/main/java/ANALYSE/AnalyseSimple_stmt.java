package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseSimple_stmt{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList( "ident", "(", "[", "not", "integer", "string", "True", "False", "None")) ;

    public AnalyseSimple_stmt(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (currentToken.equals("ident") && convert.getConvertedValue(this.token_stack.get(1)).equals("=")){
            this.token_stack.remove(0);
            this.token_stack.remove(0);
            AnalyseExpr R1 = new AnalyseExpr(this.token_stack);
            R1.Analyse();
        }
        else if(validetoken.contains(currentToken)){
            AnalyseOr_expr R1 = new AnalyseOr_expr(this.token_stack);
            R1.Analyse();
            AnalyseSimple_stmt_fact R2 = new AnalyseSimple_stmt_fact(this.token_stack);
            R2.Analyse();
            
        }
        else if(currentToken.equals("return")){
            this.token_stack.remove(0);
            AnalyseExpr R1 = new AnalyseExpr(this.token_stack);
            R1.Analyse();
        }
        else if(currentToken.equals("print")){
            this.token_stack.remove(0);
            currentTokenArray = convert.getCurrentToken(this.token_stack);
            currentToken = convert.getConvertedValue(currentTokenArray);
            if(currentToken.equals("(")){
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
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
            }
            
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}