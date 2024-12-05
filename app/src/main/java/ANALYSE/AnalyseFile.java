package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class AnalyseFile{
    private ArrayList<String[]> token_stack ; 
    private ConvertToken convert = new ConvertToken();
    private ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("NEWLINE", "def", "ident", "(", "return", "print", "[", "for", "in", "if", "not", "integer", "string", "True", "False", "None")) ;


    public AnalyseFile(ArrayList<String[]> token_stack){
        this.token_stack=token_stack;
    }
    
    public void Analyse(){

        if (token_stack.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
    }

        String[] currentTokenArray = convert.getCurrentToken(this.token_stack);
        String currentToken = convert.getConvertedValue(currentTokenArray);
        if (validetoken.contains(currentToken)){
            AnalyseOpt_newline R1 = new AnalyseOpt_newline(this.token_stack);
            R1.Analyse();
            AnalyseDef_etoile R2 = new AnalyseDef_etoile(this.token_stack);
            R2.Analyse();
            AnalyseStmt_plus R3 = new AnalyseStmt_plus(this.token_stack);
            R3.Analyse();
            if(currentToken.equals("EOF")){
                System.out.println("mot reconnue");
                return;
            }
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + token_stack.get(0)[2]);
        }

    }
}


