
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import ANALYSE.AnalyseException;
import ANALYSE.ConvertToken;

import AST.Node;

public class ParserV2 {
    private Queue<String[]> tokenQueue = new LinkedList<String[]>();
    private ConvertToken convertToken = new ConvertToken();

    public ParserV2(Queue<String[]> tokenQueue){
        this.tokenQueue = tokenQueue;
    }

    public void startAnalyse(Node parent){
        AnalyseFile(parent);
    }
    
    private void AnalyseFile(Node parent){
        AnalyseOptNewline();
        AnalyseDefEtoile(parent);
        AnalyseStmt(parent);
    }

    private void AnalyseOptNewline(){
        String tokenValue = convertToken.getConvertedValue(tokenQueue.peek());
        if (tokenValue.equals("NEWLINE")){
            tokenQueue.poll();
        }
    }

    private void AnalyseDefEtoile(Node parent){
        String tokenValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (tokenValue.equals("def")){
            AnalyseDeft(parent);
            AnalyseDefEtoile(parent);
        }
        
    }

    private void AnalyseStmtPlus(Node parent){
        AnalyseStmt(parent);
        AnalyseStmtPlusRest(parent);
    }

    private void AnalyseStmtPlusRest(Node parent){
        AnalyseStmt(parent);
        AnalyseStmtPlusRest(parent);
    }

    private void AnalyseDeft(Node parent){
        String currentValue = convertToken.getConvertedValue(tokenQueue.poll());

        if (currentValue.equals("def")){
           currentValue = convertToken.getConvertedValue(tokenQueue.poll());
            if(currentValue.equals("ident")){
                currentValue = convertToken.getConvertedValue(tokenQueue.poll());
                if(currentValue.equals("(")){


                    AnalyseIdentEtoileVirgule(parent);

                    currentValue = convertToken.getConvertedValue(tokenQueue.poll());
                    if(currentValue.equals(")")){
                        
                        currentValue = convertToken.getConvertedValue(tokenQueue.poll());
                        if(currentValue.equals(":")){
                            AnalyseSuite(parent);
                        }
                        else{
                            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
                        }

                    }
                    else{
                        throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
                    }
                }
                else{
                    throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
                }
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
            }
            

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseIdentEtoileVirgule(Node parent){
        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (currentValue.equals("ident")){
            AnalyseIdentPlusVirgule(parent);
        }
        else if (currentValue.equals(")")){
            return;
        }
        else {
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }

    }

    private void AnalyseIdentPlusVirgule(Node parent){
        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (currentValue.equals("ident")){
            tokenQueue.poll();
            AnalyseIdentPlusVirguleRest(parent);
        }
        else {
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseIdentPlusVirguleRest(Node parent){
        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (currentValue.equals(")")){
            return;
        }
        else if (currentValue.equals(",")){
            tokenQueue.poll();
            AnalyseIdentPlusVirguleRest(parent);
        }
        else {
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseSuite(Node parent){
        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("ident", "(", "return", "print", "[", "not", "integer", "string", "True", "False", "None")) ;

        if (currentValue.equals("NEWLINE")){
            tokenQueue.poll();
            currentValue = convertToken.getConvertedValue(tokenQueue.poll());
            if(currentValue.equals("BEGIN")){


                AnalyseStmtPlus(parent); 


                currentValue = convertToken.getConvertedValue(tokenQueue.poll());
                if(currentValue.equals("END")){
                    return;
                }
                else{
                    throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
                }

            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
            }
        }

        else if (validetoken.contains(currentValue)){


            AnalyseSimpleStmt(parent);


            currentValue = convertToken.getConvertedValue(tokenQueue.peek());
            if(currentValue.equals("NEWLINE")){
                return;
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
            }
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }

    }

    private void AnalyseSimpleStmt(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList( "ident", "(", "[", "not", "integer", "string", "True", "False", "None")) ;

        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (currentValue.equals("ident")){
            tokenQueue.poll();


            currentValue = convertToken.getConvertedValue(tokenQueue.poll());
            if (currentValue.equals("=")){
                AnalyseExpr(parent);
            }
            else {
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
            }
        }
        else if(validetoken.contains(currentValue)){

            AnalyseOrExpr(parent);
            AnalyseSimpleStmtFact(parent);
            
        }
        else if(currentValue.equals("return")){
            
            tokenQueue.poll();
            AnalyseExpr(parent);

        }
        else if(currentValue.equals("print")){
            tokenQueue.poll();
            currentValue = convertToken.getConvertedValue(tokenQueue.poll());

            if(currentValue.equals("(")){
                
                AnalyseExpr(parent);


                currentValue = convertToken.getConvertedValue(tokenQueue.poll());
                if(currentValue.equals(")")){
                    return;
                }
                else{
                    throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
                }
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
            }
            
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }

    }

    private void AnalyseSimpleStmtFact(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList( "NEWLINE","=")) ;

        String currentValue = convertToken.getConvertedValue(tokenQueue.poll());

        if(validetoken.contains(currentValue)){
            AnalyseSimpleStmtFactFact(parent);
        }
        else if(currentValue.equals("[")){
            tokenQueue.poll();


            AnalyseExpr(parent);



            currentValue = convertToken.getConvertedValue(tokenQueue.poll());
            if(currentValue.equals("]")){
                AnalyseExprCrochetEtoile(parent);
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
            }
            
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseSimpleStmtFactFact(Node parent){
        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (currentValue.equals("=")){
            tokenQueue.poll();

            AnalyseExpr(parent);
        }
        else if (currentValue.equals("NEWLINE")){
            return ;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseStmt(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList( "ident", "(","return","print", "[", "not", "integer", "string", "True", "False", "None")) ;
    
        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (validetoken.contains(currentValue)){



            AnalyseSimpleStmt(parent);


            currentValue = convertToken.getConvertedValue(tokenQueue.poll());
            if(currentValue.equals("NEWLINE")){
                return;
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
            }
            
        }
        else if (currentValue.equals("for")){
            tokenQueue.poll();

            currentValue = convertToken.getConvertedValue(tokenQueue.poll());
            if(currentValue.equals("ident")){

                currentValue = convertToken.getConvertedValue(tokenQueue.poll());
                if(currentValue.equals("in")){
                    
                    AnalyseExpr(parent);

                    currentValue = convertToken.getConvertedValue(tokenQueue.poll());
                    if(currentValue.equals(":")){
                        
                        AnalyseSuite(parent);
                    }
                    else{
                        throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
                    }
                }
                else{
                    throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
                }
                
            }
            else{
                 throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
            }
        }
        else if (currentValue.equals("if")){
            tokenQueue.poll();

            AnalyseExpr(parent);

            currentValue = convertToken.getConvertedValue(tokenQueue.poll());
            if(currentValue.equals(":")){
                
                AnalyseSuite(parent);
                AnalyseStmtElse(parent);
                
            }
            else{
                 throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
            }
        }

        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseStmtElse(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList( "EOF","ident", "(","END", "return", "print", "[", "for","if", "not", "integer", "string", "True", "False", "None")) ;

        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (currentValue.equals("else")){
            tokenQueue.poll();

            currentValue = convertToken.getConvertedValue(tokenQueue.poll());

            if (currentValue.equals(":")){
                
                AnalyseSuite(parent);
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne t: " + currentValue);
            }

        }
        else if (validetoken.contains(currentValue)){
            return ;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseExpr(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList( "ident", "(", "[",  "not", "integer", "string", "True", "False", "None")) ;

        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (validetoken.contains(currentValue)){
            AnalyseOrExpr(parent);
            AnalyseExprCrochetEtoile(parent);

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseExprCrochetEtoile(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("NEWLINE",")",":",",", "ident", "(", "]")) ;
        
        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (currentValue.equals("[")){
            tokenQueue.poll();
            AnalyseExpr(parent);

            currentValue = convertToken.getConvertedValue(tokenQueue.poll());
            if(currentValue.equals("]")){
                AnalyseExprCrochetEtoile(parent);
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
            }
        }
        else if (validetoken.contains(currentValue)){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseOrExpr(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("ident", "(", "[", "not", "integer", "string", "True", "False", "None")) ;
        
        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (validetoken.contains(currentValue)){
            AnalyseAndExpr(parent);
            AnalyseOrExprRest(parent);
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseOrExprRest(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("NEWLINE",")",":",",","=","[","]")) ;

        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (currentValue.equals("or")){
            AnalyseBinopOr(parent);
            AnalyseOrExpr(parent);
        }
        else if(validetoken.contains(currentValue)){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseAndExpr(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("ident", "(", "[", "not", "integer", "string", "True", "False", "None")) ;

        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (validetoken.contains(currentValue)){
            AnalyseNotExpr(parent);
            AnalyseAndExprRest(parent);
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseAndExprRest(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("or","NEWLINE",")",":",",","=","[","]")) ;

        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (currentValue.equals("and")){
            AnalyseBinopAnd(parent);
            AnalyseAndExpr(parent);
        }
        else if(validetoken.contains(currentValue)){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseNotExpr(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("ident", "(", "[", "integer", "string", "True", "False", "None")) ;

        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (validetoken.contains(currentValue)){
            AnalyseCompExpr(parent);
        }
        else if(currentValue.equals("not")){
            tokenQueue.poll();
            AnalyseCompExpr(parent);
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseCompExpr(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("ident", "(", "[", "integer", "string", "True", "False", "None")) ;

        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (validetoken.contains(currentValue)){
            AnalyseAddExpr(parent);
            AnalyseCompExprRest(parent);
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseCompExprRest(Node parent){
        final ArrayList<String> validetoken1 = new ArrayList<>(Arrays.asList("and","or","NEWLINE",")",":",",","=","[","]")) ;
        final ArrayList<String> validetoken2 = new ArrayList<>(Arrays.asList("<=",">=",">","<","!=","==")) ;

        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (validetoken2.contains(currentValue)){
            AnalyseBinopComp(parent);
            AnalyseAddExpr(parent);
        }
        else if(validetoken1.contains(currentValue)){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseAddExpr(Node parent){
        ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("ident", "(", "[", "integer", "string", "True", "False", "None")) ;
        
        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());
        
        if (validetoken.contains(currentValue)){
            AnalyseMutExpr(parent);
            AnalyseAddExprRest(parent);
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }

    }

    private void AnalyseAddExprRest(Node parent){
        final ArrayList<String> validetoken1 = new ArrayList<>(Arrays.asList("and","or","NEWLINE",")",":",",","=","[","]","<=",">=",">","<","!=","==")) ;
        final ArrayList<String> validetoken2 = new ArrayList<>(Arrays.asList("-","+")) ;
        
        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());
        
        if (validetoken2.contains(currentValue)){
            AnalyseBinopAdd(parent);
            AnalyseAddExpr(parent);
        }
        else if(validetoken1.contains(currentValue)){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseMutExpr(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("ident", "(", "[", "integer", "string", "True", "False", "None")) ;

        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());
        
        if (validetoken.contains(currentValue)){
            AnalyseTerminalExpr(parent);
            AnalyseMutExprRest(parent);
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseMutExprRest(Node parent){
        final ArrayList<String> validetoken1 = new ArrayList<>(Arrays.asList("and","or","NEWLINE",")",":",",","=","[","]","<=",">=",">","<","!=","==","+","-")) ;
        final ArrayList<String> validetoken2 = new ArrayList<>(Arrays.asList("*","//","%")) ;

        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (validetoken2.contains(currentValue)){
            AnalyseBinopMut(parent);
            AnalyseMutExpr(parent);
        }
        else if(validetoken1.contains(currentValue)){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseTerminalExpr(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("integer", "string", "True", "False", "None")) ;

        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (currentValue.equals("ident")){
            tokenQueue.poll();
            AnalyseExprRestIdent(parent);
        }
        else if(currentValue.equals("(")){
            tokenQueue.poll();

            AnalyseExpr(parent);

            
            currentValue = convertToken.getConvertedValue(tokenQueue.poll());
            if(currentValue.equals(")")){
                tokenQueue.poll();
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
            }
        }
        else if(currentValue.equals("[")){
            tokenQueue.poll();
            AnalyseExprEtoileVirgule(parent);


            currentValue = convertToken.getConvertedValue(tokenQueue.poll());
            if(currentValue.equals("]")){
                tokenQueue.poll();
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
            }
        }
        else if(validetoken.contains(currentValue)){
            AnalyseConst(parent);

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " +  currentValue);
        }
    }

    private void AnalyseExprRestIdent(Node parent){
        final ArrayList<String> validetoken1 = new ArrayList<>(Arrays.asList("and","or","NEWLINE",")",":",",","=","[","]","<=",">=",">","<","!=","==","+","-","*","//","%")) ;

        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (currentValue.equals("(")){
            tokenQueue.poll();
            AnalyseExprEtoileVirgule(parent);


            currentValue = convertToken.getConvertedValue(tokenQueue.poll());
            if(currentValue.equals(")")){
                return;
            }
            else{
                 throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
            }
        }
        else if(validetoken1.contains(currentValue)){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseExprEtoileVirgule(Node parent){
        
        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (currentValue.equals("ident")){
            AnalyseIdentPlusVirgule(parent);
        }
        else if (currentValue.equals(")")){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseExprPlusVirgule(Node parent){
        final ArrayList<String> validetoken = new ArrayList<>(Arrays.asList("ident","(","[","not", "integer", "string", "True", "False", "None")) ;
        
        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (validetoken.contains(currentValue)){
            AnalyseExpr(parent);
            AnalyseExprPlusVirguleRest(parent);
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseExprPlusVirguleRest(Node parent){
        String currentValue = convertToken.getConvertedValue(tokenQueue.peek());

        if (currentValue.equals(",")){
            tokenQueue.poll();
            AnalyseExprPlusVirgule(parent);
        }
        else if(currentValue.equals(")")){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseBinopAdd(Node parent){

        String currentValue = convertToken.getConvertedValue(tokenQueue.poll());

        if (currentValue.equals("+")){
            return;
        }
        else if (currentValue.equals("-")){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseBinopMut(Node parent){
        String currentValue = convertToken.getConvertedValue(tokenQueue.poll());

        if (currentValue.equals("*")){
            return;
        }
        else if (currentValue.equals("//")){
            return;
        }
        else if (currentValue.equals("%")){
             return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseBinopComp(Node parent){
        ArrayList<String> validetoken1 = new ArrayList<>(Arrays.asList("<=",">=",">","<","!=","==")) ;
   
        String currentValue = convertToken.getConvertedValue(tokenQueue.poll());

        if (validetoken1.contains(currentValue)){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }
    }

    private void AnalyseBinopAnd(Node parent){

        String currentValue = convertToken.getConvertedValue(tokenQueue.poll());

        if (currentValue.equals("and")){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }

    }

    private void AnalyseBinopOr(Node parent){
        String currentValue = convertToken.getConvertedValue(tokenQueue.poll());

        if (currentValue.equals("or")){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }

    }

    private void AnalyseConst(Node parent){
        final ArrayList<String> validetoken1 = new ArrayList<>(Arrays.asList("integer", "string", "True", "False", "None")) ;
        
        String currentValue = convertToken.getConvertedValue(tokenQueue.poll());
        
    
        if (validetoken1.contains(currentValue)){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentValue);
        }

    }
}

