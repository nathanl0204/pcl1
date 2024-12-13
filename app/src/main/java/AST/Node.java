package AST;

import java.io.BufferedWriter;
import java.io.IOException;

public interface Node{
    public abstract void vizualisation(BufferedWriter writer,  String nodeName) throws IOException;
    public abstract Node simplify();
}