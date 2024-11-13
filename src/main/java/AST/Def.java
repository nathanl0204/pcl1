package AST;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import AST.Stmt.Stmt;

public class Def extends Node {
    private Ident ident;
    private List<Ident> idents;
    private Stmt suite;

    public Def() {
        this.idents = new ArrayList<>();
    }

    public Ident getIdent() {
        return ident;
    }

    public void setIdent(Ident new_ident) {
        this.ident = new_ident;
    }

    public List<Ident> getIdents() {
        return idents;
    }

    public void addIdent(Ident new_ident) {
        this.idents.add(new_ident);
    }

    public Stmt getSuite() {
        return suite;
    }

    public void setSuite(Stmt suite) {
        this.suite = suite;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"DEF\"];\n");

        
        if (this.ident != null) {
            String identNodeName = nodeName + "_ident"; 
            writer.write("  " + nodeName + " -- " + identNodeName + ";\n");
            ((Node) this.ident).vizualisation(writer, identNodeName);
        }

        for (Ident ident_ : idents){
            String childNodeName = nodeName + "_" + ident_.hashCode(); 
            writer.write("  " + nodeName + " -- " + childNodeName + ";\n");
            ((Node) ident_).vizualisation(writer,childNodeName); 
        }

        if (this.suite != null) {
            String suiteNodeName = nodeName + "_suite"; 
            writer.write("  " + nodeName + " -- " + suiteNodeName + ";\n");
            ((Node) this.suite).vizualisation(writer, suiteNodeName); 
        }
    }

    public Def simplify(){
        suite = suite.simplify();
        idents = idents.stream()
                    .map(elt -> elt.simplify()) 
                    .collect(Collectors.toList());  
        return this;    
    }
}
