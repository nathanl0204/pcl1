package AST;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import AST.SimpleStmt.Expr.*;
import AST.SimpleStmt.Expr.TermExpr.Const.IntegerType;

public class AST {
    public File root;

    public AST(){
        root = null;
    }

    public void simplify(){
        if (root != null) root = root.simplify();
        else System.out.println("root is null, cannot simplify");
    }

    public void vizualisation(){
        if (root != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.dot"))) {
            
            writer.write("graph AST {\n");
            if (root != null) {
                root.vizualisation(writer, "root");
            }
            writer.write("}\n");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        else System.out.println("root is null, cannot vizualize");
    }

    public static void main(String[] args) {
        MutExpr mut5 = new MutExpr();
        mut5.setRight(new IntegerType(70));
        mut5.setLeft(new IntegerType(60));
        mut5.setMutSymbole(MutBinop.DIV);

        MutExpr mut4 = new MutExpr();
        mut4.setRight(mut5);
        mut4.setLeft(new IntegerType(50));
        mut4.setMutSymbole(MutBinop.MULT);

        MutExpr mut3 = new MutExpr();
        mut3.setRight(mut4);
        mut3.setLeft(new IntegerType(40));
        mut3.setMutSymbole(MutBinop.MULT);




        MutExpr mut2 = new MutExpr();
        mut2.setRight(new IntegerType(40));
        mut2.setLeft(new IntegerType(30));
        mut2.setMutSymbole(MutBinop.DIV);
        
        MutExpr mut1 = new MutExpr();
        mut1.setRight(mut2);
        mut1.setLeft(new IntegerType(20));
        mut1.setMutSymbole(MutBinop.MOD);

        MutExpr mut = new MutExpr();
        mut.setRight(mut1);
        mut.setLeft(new IntegerType(10));
        mut.setMutSymbole(MutBinop.MULT);


        AddExpr expr4 = new AddExpr();
        expr4.setRight(mut3);
        expr4.setLeft(new IntegerType(5));
        expr4.setAddSymbole(AddBinop.ADD);

        AddExpr expr3 = new AddExpr();
        expr3.setRight(expr4);
        expr3.setLeft(new IntegerType(4));
        expr3.setAddSymbole(AddBinop.SUB);

        AddExpr expr2 = new AddExpr();
        expr2.setRight(expr3);
        expr2.setLeft(new IntegerType(3));
        expr2.setAddSymbole(AddBinop.SUB);
        
        AddExpr expr1 = new AddExpr();
        expr1.setRight(expr2);
        expr1.setLeft(new IntegerType(2));
        expr1.setAddSymbole(AddBinop.ADD);

        AddExpr expr = new AddExpr();
        expr.setRight(expr1);
        expr.setLeft(mut);
        expr.setAddSymbole(AddBinop.ADD);

        
        AST ast = new AST();
        ast.root = new File();
        //expr.leftRotate();
        ast.root.addStmts(expr);

        ast.vizualisation();

    }
}
