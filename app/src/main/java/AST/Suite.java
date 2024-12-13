package AST;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import AST.Stmt.Stmt;

public class Suite implements Node {
    private List<Stmt> stmts;

    public Suite() {
        this.stmts = new ArrayList<>();
    }

    public Suite(List<Stmt> stmts) {
        this.stmts = stmts;
    }

    public List<Stmt> getStmts() {
        return stmts;
    }

    public void addStmt(Stmt stmt) {
        this.stmts.add(stmt);
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"SUITE\"];\n");

        for (Stmt stmt : stmts){
            String childNodeName = nodeName + "_" + stmt.hashCode(); 
            writer.write("  " + nodeName + " -- " + childNodeName + ";\n");
            stmt.vizualisation(writer,childNodeName); 
        }
    }

    public Suite simplify(){
        stmts = stmts.stream()
                     .map(elt -> elt.simplify()) 
                     .collect(Collectors.toList());  
        return this;
    
    }
}
