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
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/output.dot"))) {
            
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
}
