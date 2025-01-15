package AST.SimpleStmt.Expr.TermExpr.Const;

import java.io.BufferedWriter;
import java.io.IOException;

public class StringType extends Const {
    private String value;

    public StringType() {
        this.value = null;
    }

    public StringType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label="+value+"];\n");
    }

    public StringType simplify(){
        return this;
    }
}
