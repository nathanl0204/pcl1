package AST.SimpleStmt.Expr.TermExpr.Const;

import java.io.BufferedWriter;
import java.io.IOException;

public class NoneType extends Const {

    public NoneType(){}

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"NONE\"];\n");
    }

    public NoneType simplify(){
        return this;
    }
}
