package AST.SimpleStmt.Expr;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.SimpleStmt.Expr.TermExpr.Const.Bool;
import AST.SimpleStmt.Expr.TermExpr.Const.IntegerType;
import AST.SimpleStmt.Expr.TermExpr.Const.StringType;

public class AddExpr extends CompExpr {
    private AddBinop symbole;
    private AddExpr left;
    private AddExpr right;

    public AddExpr() {
        this.symbole = null;
        this.left = null;
        this.right = null;
    }

    public AddBinop getAddSymbole() {
        return symbole;
    }

    public void setAddSymbole(AddBinop symbole) {
        this.symbole = symbole;
    }

    public AddExpr getLeft() {
        return left;
    }

    public void setLeft(AddExpr left) {
        this.left = left;
    }

    public AddExpr getRight() {
        return right;
    }

    public void setRight(AddExpr right) {
        this.right = right;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"" + this.symbole.name() + "\"];\n");

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

    public void leftRotate() {
        if (left != null && right != null && right.left != null && right.right != null){
            if (left instanceof MutExpr) ((MutExpr) left).leftRotate();
            if (right.left instanceof MutExpr) ((MutExpr) right.left).leftRotate();
            AddExpr new_left = new AddExpr();

            new_left.setAddSymbole(symbole);
            symbole = right.getAddSymbole();
            new_left.setLeft(left);
            new_left.setRight(right.left);

            left = new_left;
            right = right.right;

            if (right instanceof AddExpr) this.leftRotate();
            
        }
        else if (right != null && right instanceof MutExpr) ((MutExpr) right).leftRotate();        
    }

    public AddExpr simplify(){
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

        if (left instanceof StringType && right instanceof StringType && symbole == AddBinop.ADD) {
            String leftValue = ((StringType) left).getValue();
            String rightValue = ((StringType) right).getValue();
            return new StringType(leftValue + rightValue);
        }

        return this;
    }

    private int evaluate(int leftValue, int rightValue, AddBinop symbole){
        int result = 0;
        switch (symbole) {
            case ADD:
                result = leftValue + rightValue;
                break;
            case SUB:
                result = leftValue - rightValue;
                break;
        }
        return result;
    }
}
