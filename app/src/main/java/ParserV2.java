
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Queue;

import ANALYSE.AnalyseException;
import ANALYSE.ConvertToken;
import AST.Def;
import AST.File;
import AST.Node;
import AST.Suite;
import AST.SimpleStmt.Affect;
import AST.SimpleStmt.SimpleStmt;
import AST.SimpleStmt.Expr.Expr;
import AST.SimpleStmt.Expr.TermExpr.Ident;
import AST.SimpleStmt.Expr.TermExpr.Const.Const;
import AST.Stmt.Stmt;

public class ParserV2 {
    private Queue<Token> tokenQueue = new LinkedList<Token>();

    public ParserV2(Queue<Token> tokenQueue){
        this.tokenQueue = tokenQueue;
    }

    public void startAnalyse(){
        AnalyseFile();
    }
    
    private File AnalyseFile(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList("NEWLINE", "def", "ident", "(", "return", "print", "[", "for", "in", "if", "not", "integer", "string", "True", "False", "None")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }

        Token currentToken = tokenQueue.peek();
        
        if (validetoken.contains(currentToken.getSymbole())){
            

            AnalyseOptNewline();
            
            File file = new File( AnalyseDefEtoile() , AnalyseStmtPlus() );

            currentToken = tokenQueue.poll();
            if(currentToken.getSymbole().equals("EOF")){
                System.out.println("MOT RECONNUE | AUCUN PROBLEME");
                return file;
            }

            
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
        return null;
    }

    private void AnalyseOptNewline(){
        LinkedList<String> validetoken = new LinkedList<>(Arrays.asList("def", "ident", "(", "return", "print", "[", "for","if", "not", "integer", "string", "True", "False", "None")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }

        Token currentToken = tokenQueue.peek();
        if (currentToken.getSymbole().equals("NEWLINE")){
            tokenQueue.poll();
        }
        else if (validetoken.contains(currentToken.getSymbole())){

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private Def AnalyseDeft(){
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.poll();

        if (currentToken.getSymbole().equals("def")){
            Def def = new Def();

            currentToken = tokenQueue.poll();
            if(currentToken.getSymbole().equals("ident")){
                Ident ident = new Ident(currentToken.getValue()); // A revoir 

                currentToken = tokenQueue.poll();
                if(currentToken.getSymbole().equals("(")){


                    LinkedList<Ident> idents = AnalyseIdentEtoileVirgule();

                    currentToken = tokenQueue.poll();
                    if(currentToken.getSymbole().equals(")")){
                        
                        currentToken = tokenQueue.poll();
                        if(currentToken.getSymbole().equals(":")){
                            def.setIdent(ident);
                            def.setIdents(idents);
                            def.setSuite( AnalyseSuite() );

                            return def;
                        }
                        else{
                            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
                        }

                    }
                    else{
                        throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
                    }
                }
                else{
                    throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
                }
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
            }
            

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private LinkedList<Def> AnalyseDefEtoile(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList( "ident", "(", "return", "print", "[", "for","if", "not", "integer", "string", "True", "False", "None")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (currentToken.getSymbole().equals("def")){
            Def def = AnalyseDeft();
            LinkedList<Def> defs = AnalyseDefEtoile();

            if (defs == null) {
                LinkedList<Def> newDefs = new LinkedList<Def>();
                newDefs.add(def);
                
                return newDefs;
            }
            else {
                defs.add(def);
                return defs;
            }


        }
        else if (validetoken.contains(currentToken.getSymbole())){
            return null;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
        
    }

    private LinkedList<Stmt> AnalyseStmtPlus(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList( "ident", "(", "return", "print", "[", "for","if", "not", "integer", "string", "True", "False", "None")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (validetoken.contains(currentToken.getSymbole())){
            Stmt stmt = AnalyseStmt();
            LinkedList<Stmt> stmts = AnalyseStmtPlusRest();

            if (stmts == null){
                LinkedList<Stmt> newStmts = new LinkedList<Stmt>();
                newStmts.add(stmt);
                return newStmts;
            }
            else {
                stmts.add(stmt);
                return stmts;
            }
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private LinkedList<Stmt> AnalyseStmtPlusRest(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList( "ident", "(", "return", "print", "[", "for","if", "not", "integer", "string", "True", "False", "None")) ;
        final LinkedList<String> validetoken1 = new LinkedList<>(Arrays.asList( "EOF","END")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();
        
        if (validetoken.contains(currentToken.getSymbole())){
            return AnalyseStmtPlus();
        }
        else if (validetoken1.contains(currentToken.getSymbole())){
            return null;
        }   
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private LinkedList<Ident> AnalyseIdentEtoileVirgule(){
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (currentToken.getSymbole().equals("ident")){
            return AnalyseIdentPlusVirgule();
        }
        else if (currentToken.getSymbole().equals(")")){
            return null;
        }
        else {
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }

    }

    private LinkedList<Ident> AnalyseIdentPlusVirgule(){
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (currentToken.getSymbole().equals("ident")){
            tokenQueue.poll();
            Ident ident = new Ident();
            LinkedList<Ident> idents = AnalyseIdentPlusVirguleRest();

            if (idents == null){
                LinkedList<Ident> newIdents = new LinkedList<>();
                newIdents.add(ident);
                return newIdents;
            }
            else {
                idents.add(ident);
                return idents;
            }
        }
        else {
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private LinkedList<Ident> AnalyseIdentPlusVirguleRest(){
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (currentToken.getSymbole().equals(")")){
            return null;
        }
        else if (currentToken.getSymbole().equals(",")){
            tokenQueue.poll();
            return AnalyseIdentPlusVirgule();
        }
        else {
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private Suite AnalyseSuite(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList("ident", "(", "return", "print", "[", "not", "integer", "string", "True", "False", "None")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();
        
        if (currentToken.getSymbole().equals("NEWLINE")){
            tokenQueue.poll();
            currentToken = tokenQueue.poll();
            if(currentToken.getSymbole().equals("BEGIN")){

                Suite suite = new Suite( AnalyseStmtPlus() ); 

                currentToken = tokenQueue.poll();
                if(currentToken.getSymbole().equals("END")){
                    return suite;
                }
                else{
                    throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
                }

            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
            }
        }

        else if (validetoken.contains(currentToken.getSymbole())){


            SimpleStmt simpleStmt = AnalyseSimpleStmt();


            currentToken = tokenQueue.poll();
            if(currentToken.getSymbole().equals("NEWLINE")){
                return new Suite( Arrays.asList(simpleStmt) );
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
            }
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }

    }

    private SimpleStmt AnalyseSimpleStmt(){
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList( "ident", "(", "[", "not", "integer", "string", "True", "False", "None")) ;

        Token currentToken = tokenQueue.peek();

        if (currentToken.getSymbole().equals("ident")){
            tokenQueue.poll();
            
            Ident ident = new Ident(currentToken.getValue());

            currentToken = tokenQueue.poll();
            if (currentToken.getSymbole().equals("=")){
                return new Affect( ident , AnalyseExpr() );
            }
            else {
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
            }
        }
        else if(validetoken.contains(currentToken.getSymbole())){

            AnalyseOrExpr();
            AnalyseSimpleStmtFact();
            
        }
        else if(currentToken.getSymbole().equals("return")){
            
            tokenQueue.poll();
            AnalyseExpr();

        }
        else if(currentToken.getSymbole().equals("print")){
            tokenQueue.poll();
            currentToken = tokenQueue.poll();

            if(currentToken.getSymbole().equals("(")){
                
                AnalyseExpr();

                currentToken = tokenQueue.poll();
                if(currentToken.getSymbole().equals(")")){
                    return;
                }
                else{
                    throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
                }
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
            }
            
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }

    }

    private void AnalyseSimpleStmtFact(){
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList( "NEWLINE","=")) ;

        Token currentToken = tokenQueue.poll();

        if(validetoken.contains(currentToken.getSymbole())){
            AnalyseSimpleStmtFactFact();
        }
        else if(currentToken.getSymbole().equals("[")){
            tokenQueue.poll();


            AnalyseExpr();



            currentToken = tokenQueue.poll();
            if(currentToken.getSymbole().equals("]")){
                AnalyseExprCrochetEtoile();
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
            }
            
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseSimpleStmtFactFact(){
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (currentToken.getSymbole().equals("=")){
            tokenQueue.poll();

            AnalyseExpr();
        }
        else if (currentToken.getSymbole().equals("NEWLINE")){
            return ;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private Stmt AnalyseStmt(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList( "ident", "(","return","print", "[", "not", "integer", "string", "True", "False", "None")) ;
    
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (validetoken.contains(currentToken.getSymbole())){



            AnalyseSimpleStmt();


            currentToken = tokenQueue.poll();
            if(currentToken.getSymbole().equals("NEWLINE")){
                return;
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
            }
            
        }
        else if (currentToken.getSymbole().equals("for")){
            tokenQueue.poll();

            currentToken = tokenQueue.poll();
            if(currentToken.getSymbole().equals("ident")){

                currentToken = tokenQueue.poll();
                if(currentToken.getSymbole().equals("in")){
                    
                    AnalyseExpr();

                    currentToken = tokenQueue.poll();
                    if(currentToken.getSymbole().equals(":")){
                        
                        AnalyseSuite();
                    }
                    else{
                        throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
                    }
                }
                else{
                    throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
                }
                
            }
            else{
                 throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
            }
        }
        else if (currentToken.getSymbole().equals("if")){
            tokenQueue.poll();

            AnalyseExpr();

            currentToken = tokenQueue.poll();
            if(currentToken.getSymbole().equals(":")){
                
                AnalyseSuite();
                AnalyseStmtElse();
                
            }
            else{
                 throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
            }
        }

        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseStmtElse(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList( "EOF","ident", "(","END", "return", "print", "[", "for","if", "not", "integer", "string", "True", "False", "None")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (currentToken.getSymbole().equals("else")){
            tokenQueue.poll();

            currentToken = tokenQueue.poll();

            if (currentToken.getSymbole().equals(":")){
                
                AnalyseSuite();
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne t: " + currentToken.getLine());
            }

        }
        else if (validetoken.contains(currentToken.getSymbole())){
            return ;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private Expr AnalyseExpr(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList( "ident", "(", "[",  "not", "integer", "string", "True", "False", "None")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (validetoken.contains(currentToken.getSymbole())){
            AnalyseOrExpr();
            AnalyseExprCrochetEtoile();

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseExprCrochetEtoile(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList("NEWLINE",")",":",",", "ident", "(", "]")) ;
        
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (currentToken.getSymbole().equals("[")){
            tokenQueue.poll();
            AnalyseExpr();

            currentToken = tokenQueue.poll();
            if(currentToken.getSymbole().equals("]")){
                AnalyseExprCrochetEtoile();
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
            }
        }
        else if (validetoken.contains(currentToken.getSymbole())){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseOrExpr(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList("ident", "(", "[", "not", "integer", "string", "True", "False", "None")) ;
        
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (validetoken.contains(currentToken.getSymbole())){
            AnalyseAndExpr();
            AnalyseOrExprRest();
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseOrExprRest(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList("NEWLINE",")",":",",","=","[","]")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (currentToken.getSymbole().equals("or")){
            AnalyseBinopOr();
            AnalyseOrExpr();
        }
        else if(validetoken.contains(currentToken.getSymbole())){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseAndExpr(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList("ident", "(", "[", "not", "integer", "string", "True", "False", "None")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (validetoken.contains(currentToken.getSymbole())){
            AnalyseNotExpr();
            AnalyseAndExprRest();
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseAndExprRest(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList("or","NEWLINE",")",":",",","=","[","]")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (currentToken.getSymbole().equals("and")){
            AnalyseBinopAnd();
            AnalyseAndExpr();
        }
        else if(validetoken.contains(currentToken.getSymbole())){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseNotExpr(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList("ident", "(", "[", "integer", "string", "True", "False", "None")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (validetoken.contains(currentToken.getSymbole())){
            AnalyseCompExpr();
        }
        else if(currentToken.getSymbole().equals("not")){
            tokenQueue.poll();
            AnalyseCompExpr();
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseCompExpr(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList("ident", "(", "[", "integer", "string", "True", "False", "None")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (validetoken.contains(currentToken.getSymbole())){
            AnalyseAddExpr();
            AnalyseCompExprRest();
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseCompExprRest(){
        final LinkedList<String> validetoken1 = new LinkedList<>(Arrays.asList("and","or","NEWLINE",")",":",",","=","[","]")) ;
        final LinkedList<String> validetoken2 = new LinkedList<>(Arrays.asList("<=",">=",">","<","!=","==")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (validetoken2.contains(currentToken.getSymbole())){
            AnalyseBinopComp();
            AnalyseAddExpr();
        }
        else if(validetoken1.contains(currentToken.getSymbole())){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseAddExpr(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList("ident", "(", "[", "integer", "string", "True", "False", "None")) ;
        
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();
        
        if (validetoken.contains(currentToken.getSymbole())){
            AnalyseMutExpr();
            AnalyseAddExprRest();
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }

    }

    private void AnalyseAddExprRest(){
        final LinkedList<String> validetoken1 = new LinkedList<>(Arrays.asList("and","or","NEWLINE",")",":",",","=","[","]","<=",">=",">","<","!=","==")) ;
        final LinkedList<String> validetoken2 = new LinkedList<>(Arrays.asList("-","+")) ;
        
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();
        
        if (validetoken2.contains(currentToken.getSymbole())){
            AnalyseBinopAdd();
            AnalyseAddExpr();
        }
        else if(validetoken1.contains(currentToken.getSymbole())){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseMutExpr(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList("ident", "(", "[", "integer", "string", "True", "False", "None")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();
        
        if (validetoken.contains(currentToken.getSymbole())){
            AnalyseTerminalExpr();
            AnalyseMutExprRest();
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseMutExprRest(){
        final LinkedList<String> validetoken1 = new LinkedList<>(Arrays.asList("and","or","NEWLINE",")",":",",","=","[","]","<=",">=",">","<","!=","==","+","-")) ;
        final LinkedList<String> validetoken2 = new LinkedList<>(Arrays.asList("*","//","%")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (validetoken2.contains(currentToken.getSymbole())){
            AnalyseBinopMut();
            AnalyseMutExpr();
        }
        else if(validetoken1.contains(currentToken.getSymbole())){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseTerminalExpr(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList("integer", "string", "True", "False", "None")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (currentToken.getSymbole().equals("ident")){
            tokenQueue.poll();
            AnalyseExprRestIdent();
        }
        else if(currentToken.getSymbole().equals("(")){
            tokenQueue.poll();

            AnalyseExpr();

            
            currentToken = tokenQueue.poll();
            if(currentToken.getSymbole().equals(")")){
                tokenQueue.poll();
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
            }
        }
        else if(currentToken.getSymbole().equals("[")){
            tokenQueue.poll();
            AnalyseExprEtoileVirgule();


            currentToken = tokenQueue.poll();
            if(currentToken.getSymbole().equals("]")){
                tokenQueue.poll();
            }
            else{
                throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
            }
        }
        else if(validetoken.contains(currentToken.getSymbole())){
            AnalyseConst();

        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " +  currentToken.getSymbole());
        }
    }

    private void AnalyseExprRestIdent(){
        final LinkedList<String> validetoken1 = new LinkedList<>(Arrays.asList("and","or","NEWLINE",")",":",",","=","[","]","<=",">=",">","<","!=","==","+","-","*","//","%")) ;

        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (currentToken.getSymbole().equals("(")){
            tokenQueue.poll();
            AnalyseExprEtoileVirgule();


            currentToken = tokenQueue.poll();
            if(currentToken.getSymbole().equals(")")){
                return;
            }
            else{
                 throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
            }
        }
        else if(validetoken1.contains(currentToken.getSymbole())){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseExprEtoileVirgule(){
        
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (currentToken.getSymbole().equals("ident")){
            AnalyseIdentPlusVirgule();
        }
        else if (currentToken.getSymbole().equals(")")){
            return;
        } 
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseExprPlusVirgule(){
        final LinkedList<String> validetoken = new LinkedList<>(Arrays.asList("ident","(","[","not", "integer", "string", "True", "False", "None")) ;
        
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.peek();

        if (validetoken.contains(currentToken.getSymbole())){
            AnalyseExpr();
            AnalyseExprPlusVirguleRest();
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseExprPlusVirguleRest(){
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }

        Token currentToken = tokenQueue.peek();

        if (currentToken.getSymbole().equals(",")){
            tokenQueue.poll();
            AnalyseExprPlusVirgule();
        }
        else if(currentToken.getSymbole().equals(")")){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseBinopAdd(){
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.poll();

        if (currentToken.getSymbole().equals("+")){
            return;
        }
        else if (currentToken.getSymbole().equals("-")){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseBinopMut(){
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.poll();

        if (currentToken.getSymbole().equals("*")){
            return;
        }
        else if (currentToken.getSymbole().equals("//")){
            return;
        }
        else if (currentToken.getSymbole().equals("%")){
             return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseBinopComp(){
        final LinkedList<String> validetoken1 = new LinkedList<>(Arrays.asList("<=",">=",">","<","!=","==")) ;
        
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.poll();

        if (validetoken1.contains(currentToken.getSymbole())){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }
    }

    private void AnalyseBinopAnd(){
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.poll();

        if (currentToken.getSymbole().equals("and")){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }

    }

    private void AnalyseBinopOr(){
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.poll();

        if (currentToken.getSymbole().equals("or")){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }

    }

    private Const AnalyseConst(){
        final LinkedList<String> validetoken1 = new LinkedList<>(Arrays.asList("integer", "string", "True", "False", "None")) ;
        
        if (tokenQueue.isEmpty()) {
            throw new AnalyseException("Erreur : pile de tokens vide !");
        }
        
        Token currentToken = tokenQueue.poll();
        
    
        if (validetoken1.contains(currentToken.getSymbole())){
            return;
        }
        else{
            throw new AnalyseException("Erreur non reconnue, ligne : " + currentToken.getLine());
        }

    }
}