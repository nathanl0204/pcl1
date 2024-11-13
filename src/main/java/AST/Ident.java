package AST;

import java.io.BufferedWriter;
import java.io.IOException;

public class Ident extends Node{
    private String name;

    public void vizualisation(BufferedWriter writer,  String nodeName) throws IOException{
        if (name != "") {
            writer.write("  " + nodeName + " [label=\"" + name + "\"];\n");
        }
    }

    public Ident simplify(){
        return this;
    }
}
