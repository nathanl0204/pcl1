package AST.SimpleStmt.Expr.TermExpr;

import java.io.BufferedWriter;
import java.io.IOException;

public class Ident extends TermExpr {
    private String name;

    public Ident() {
        this.name = null;
    }

    public Ident(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ident simplify(){
        return this;
    }

    public void vizualisation(BufferedWriter writer,  String nodeName) throws IOException{
        if (name != "") {
            writer.write("  " + nodeName + " [label=\"" + name + "\"];\n");
        }
    }
}
