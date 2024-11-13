package AST.SimpleStmt.Expr;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.SimpleStmt.Expr.TermExpr.Const.Bool;
import AST.SimpleStmt.Expr.TermExpr.Const.IntegerType;

public class MutExpr extends AddExpr {
    private MutBinop symbole;
    private MutExpr left;
    private MutExpr right;

    public MutExpr() {
        this.symbole = null;
        this.left = null;
        this.right = null;
    }

    public MutBinop getMutSymbole() {
        return symbole;
    }

    public void setSymbole(MutBinop symbole) {
        this.symbole = symbole;
    }

    public MutExpr getLeft() {
        return left;
    }

    public void setLeft(MutExpr left) {
        this.left = left;
    }

    public MutExpr getRight() {
        return right;
    }

    public void setRight(MutExpr right) {
        this.right = right;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\""+ this.symbole.name() + "\"];\n");

        if (this.left != null) {
            String leftNodeName = nodeName + "_left"; 
            writer.write("  " + nodeName + " -- " + leftNodeName + ";\n");
            this.left.vizualisation(writer, leftNodeName);
        }

        if (this.right != null) {
            String rightNodeName = nodeName + "_right"; 
            writer.write("  " + nodeName + " -- " + rightNodeName + ";\n");
            this.right.vizualisation(writer, rightNodeName); 
        }
    }

    

    public MutExpr simplify(){
        this.left = left.simplify();
        this.right = right.simplify();

        if (left instanceof IntegerType && right instanceof IntegerType) {
            int leftValue = ((IntegerType) left).getValue();
            int rightValue = ((IntegerType) right).getValue();
            return new IntegerType(evaluate(leftValue, rightValue, symbole));
        }

        if (left instanceof Bool && right instanceof Bool) {
            int leftBool = ((Bool) left).getBool() == false ? 0 : 1;
            int rightBool = ((Bool) right).getBool() == false ? 0 : 1;
            return new IntegerType(evaluate(leftBool, rightBool, symbole));
        }

        if (left instanceof IntegerType && right instanceof Bool) {
            int leftValue = ((IntegerType) left).getValue();
            int rightBool = ((Bool) right).getBool() == false ? 0 : 1;
            return new IntegerType(evaluate(leftValue, rightBool, symbole));
        }

        if (left instanceof Bool && right instanceof IntegerType) {
            int leftBool = ((Bool) left).getBool() == false ? 0 : 1;
            int rightValue = ((IntegerType) right).getValue();
            return new IntegerType(evaluate(leftBool, rightValue, symbole));
        }

        return this;
    }

    public void leftRotate() {
        
        if (left != null && right != null && right.left != null && right.right != null){

            MutExpr new_left = new MutExpr();

            new_left.setSymbole(symbole);
            symbole = right.getMutSymbole();
            new_left.setLeft(left);
            new_left.setRight(right.left);

            left = new_left;
            right = right.right;
            
            if (right instanceof MutExpr) this.leftRotate();
        }        
    }

    private int evaluate(int leftValue, int rightValue, MutBinop symbole){
        int result = 0;
        switch (symbole) {
            case MULT:
                result = leftValue * rightValue;
                break;
            case DIV:
                result = leftValue / rightValue;
                break;
            case MOD:
                result = leftValue % rightValue;
                break;
        }
        return result;
    }
}
