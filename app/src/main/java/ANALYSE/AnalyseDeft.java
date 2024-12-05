package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseDeft{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();


    public AnalyseDeft(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (currentToken.equals("def")){
            this.token_stack.remove(0);
            currentTokenArray = convert.getCurrentToken(this.token_stack);
            currentToken = convert.getConvertedValue(currentTokenArray);
            if(currentToken.equals("ident")){
                this.token_stack.remove(0);
                currentTokenArray = convert.getCurrentToken(this.token_stack);
                currentToken = convert.getConvertedValue(currentTokenArray);
                if(currentToken.equals("(")){
                    this.token_stack.remove(0);
                    AnalyseIdent_etoile_virgule R1 = new AnalyseIdent_etoile_virgule(this.token_stack);
                    R1.Analyse();
                    currentTokenArray = convert.getCurrentToken(this.token_stack);
                    currentToken = convert.getConvertedValue(currentTokenArray);
                    if(currentToken.equals(")")){
                        this.token_stack.remove(0);
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
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
            }
            

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}
