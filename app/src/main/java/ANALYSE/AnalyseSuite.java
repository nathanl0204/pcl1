package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseSuite{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("ident", "(", "return", "print", "[", "not", "integer", "string", "True", "False", "None")) ;


    public AnalyseSuite(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (currentToken.equals("NEWLINE")){
            this.token_stack.remove(0);
            currentTokenArray = convert.getCurrentToken(this.token_stack);
            currentToken = convert.getConvertedValue(currentTokenArray);
            if(currentToken.equals("BEGIN")){
                this.token_stack.remove(0);
                AnalyseStmt_plus R1=new AnalyseStmt_plus(this.token_stack);
                R1.Analyse();
                if(currentToken.equals("END")){
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
        else if (validetoken.contains(currentToken)){
            AnalyseSimple_stmt R1=new AnalyseSimple_stmt(this.token_stack);
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
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
 }

