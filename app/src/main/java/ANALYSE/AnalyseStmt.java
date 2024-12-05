package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseStmt{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList( "ident", "(","return","print", "[", "not", "integer", "string", "True", "False", "None")) ;
    public AnalyseStmt(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (validetoken.contains(currentToken)){
            AnalyseSimple_stmt R1 = new AnalyseSimple_stmt(this.token_stack);
            R1.Analyse();
            currentTokenArray = convert.getCurrentToken(this.token_stack);
            currentToken = convert.getConvertedValue(currentTokenArray);
            if(currentToken.equals("NEWLINE")){
                this.token_stack.remove(0);
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
            }
            
        }
        else if (currentToken.equals("for")){
            this.token_stack.remove(0);
            currentTokenArray = convert.getCurrentToken(this.token_stack);
            currentToken = convert.getConvertedValue(currentTokenArray);
            if(currentToken.equals("ident")){
                this.token_stack.remove(0);
                currentTokenArray = convert.getCurrentToken(this.token_stack);
                currentToken = convert.getConvertedValue(currentTokenArray);
                if(currentToken.equals("in")){
                    this.token_stack.remove(0);
                    AnalyseExpr R1 = new AnalyseExpr(this.token_stack);
                    R1.Analyse();
                    currentTokenArray = convert.getCurrentToken(this.token_stack);
                    currentToken = convert.getConvertedValue(currentTokenArray);
                    if(currentToken.equals(":")){
                        this.token_stack.remove(0);
                        AnalyseSuite R2 = new AnalyseSuite(this.token_stack);
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
            else{
                 throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
            }
        }
        else if (currentToken.equals("if")){
            this.token_stack.remove(0);
            AnalyseExpr R1 = new AnalyseExpr(this.token_stack);
            R1.Analyse();
            currentTokenArray = convert.getCurrentToken(this.token_stack);
            currentToken = convert.getConvertedValue(currentTokenArray);
            if(currentToken.equals(":")){
                this.token_stack.remove(0);
                AnalyseSuite R2 = new AnalyseSuite(this.token_stack);
                R2.Analyse();
                AnalyseStmt_else R3 = new AnalyseStmt_else(this.token_stack);
                R3.Analyse();
                
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
