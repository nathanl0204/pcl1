package AST.SimpleStmt.Expr;

import java.io.BufferedWriter;
import java.io.IOException;

import AST.SimpleStmt.Expr.TermExpr.Const.Bool;
import AST.SimpleStmt.Expr.TermExpr.Const.IntegerType;
import AST.SimpleStmt.Expr.TermExpr.Const.StringType;

public class CompExpr extends NotExpr {
    private CompBinop symbole;
    private AddExpr left;
    private AddExpr right;

    public CompExpr() {
        this.symbole = null;
        this.left = null;
        this.right = null;
    }

    public CompBinop getCompSymbole() {
        return symbole;
    }

    public void setSymbole(CompBinop symbole) {
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
    
    public CompExpr simplify() {
        if (left instanceof AddExpr) left.leftRotate();
        if (right instanceof AddExpr) right.leftRotate();
        if (left instanceof MutExpr) ((MutExpr) left).leftRotate();
        if (right instanceof MutExpr) ((MutExpr) right).leftRotate();

        this.left = left.simplify();
        this.right = right.simplify();
 
        if (left instanceof IntegerType && right instanceof IntegerType) {
            int leftValue = ((IntegerType) left).getValue();
            int rightValue = ((IntegerType) right).getValue();
            return new Bool(arithmeticComp(leftValue, rightValue, symbole));
        }

        if (left instanceof Bool && right instanceof Bool) {
            int leftBool = ((Bool) left).getBool() == false ? 0 : 1;
            int rightBool = ((Bool) right).getBool() == false ? 0 : 1;
            return new Bool(arithmeticComp(leftBool, rightBool, symbole));
        }

        if (left instanceof IntegerType && right instanceof Bool) {
            int leftValue = ((IntegerType) left).getValue();
            int rightBool = ((Bool) right).getBool() == false ? 0 : 1;
            return new Bool(arithmeticComp(leftValue, rightBool, symbole));
        }

        if (left instanceof Bool && right instanceof IntegerType) {
            int leftBool = ((Bool) left).getBool() == false ? 0 : 1;
            int rightValue = ((IntegerType) right).getValue();
            return new Bool(arithmeticComp(leftBool, rightValue, symbole));
        }

        if (left instanceof StringType && right instanceof StringType) {
            String leftValue = ((StringType) left).getValue();
            String rightValue = ((StringType) right).getValue();
    
            int comparisonResult = leftValue.compareTo(rightValue);
            boolean result = false;
    
            switch (symbole) {
                case EQUAL:
                    result = (comparisonResult == 0);
                    break;
                case NOT_EQUAL:
                    result = (comparisonResult != 0);
                    break;
                case LESS_THAN:
                    result = (comparisonResult < 0);
                    break;
                case LESS_EQUAL:
                    result = (comparisonResult <= 0);
                    break;
                case GREATER_THAN:
                    result = (comparisonResult > 0);
                    break;
                case GREATER_EQUAL:
                    result = (comparisonResult >= 0);
                    break;
            }
    
            return new Bool(result);
        }

        return this;
    }

    private boolean arithmeticComp(int leftValue, int rightValue, CompBinop symbole){
        boolean result = false;
        switch (symbole) {
            case LESS_THAN:
                result = leftValue < rightValue;
                break;
            case LESS_EQUAL:
                result = leftValue <= rightValue;
                break;
            case GREATER_THAN:
                result = leftValue > rightValue;
                break;
            case GREATER_EQUAL:
                result = leftValue >= rightValue;
                break;
            case EQUAL:
                result = leftValue == rightValue;
                break;
            case NOT_EQUAL:
                result = leftValue != rightValue;
                break;
        }
        return result;
    }
}
