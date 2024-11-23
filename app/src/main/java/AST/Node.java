package AST;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Node{
    public abstract void vizualisation(BufferedWriter writer,  String nodeName) throws IOException;
    public abstract Node simplify();
}