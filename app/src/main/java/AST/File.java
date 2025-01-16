package AST;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import AST.Stmt.Stmt;

public class File implements Node{
    private List<Def> defs;
    private List<Stmt> stmts;

    public File(){
        this.defs = new ArrayList<>();
        this.stmts = new ArrayList<>();
    }

    public File(List<Def> defs,List<Stmt> stmts){
        this.defs = defs;
        this.stmts = stmts;
    }

    public List<Def> getDefs(){
        return this.defs;
    }

    public void addDefs(Def def){
        defs.add(def);
    }

    public List<Stmt> getStmts(){
        return this.stmts;
    }

    public void addStmts(Stmt stmt){
        stmts.add(stmt);
    }

    public void vizualisation(BufferedWriter writer) throws IOException {
        vizualisation(writer,"root");
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\"File\"];\n");

        String defsNodeName = nodeName + "_defs";
        writer.write("  " + nodeName + " -- " + defsNodeName + ";\n");
        writer.write("  " + defsNodeName + " [label=\"DEFS\"];\n");
        
        for (Def def : defs){
            String defNodeName = defsNodeName + "_" + def.hashCode(); 
            writer.write("  " + defsNodeName + " -- " + defNodeName + ";\n");
            def.vizualisation(writer, defNodeName); 
        }

        String stmtsNodeName = nodeName + "_stmts";
        writer.write("  " + nodeName + " -- " + stmtsNodeName + ";\n");
        writer.write("  " + stmtsNodeName + " [label=\"STMTS\"];\n");
        
        for (Stmt stmt : stmts){
            String stmtNodeName = stmtsNodeName + "_" + stmt.hashCode(); 
            writer.write("  " + stmtsNodeName + " -- " + stmtNodeName + ";\n");
            stmt.vizualisation(writer, stmtNodeName); 
        }

    }

    public File simplify(){
        stmts = stmts.stream()
                     .map(elt -> elt.simplify()) 
                     .collect(Collectors.toList());  
        defs = defs.stream()
                    .map(elt -> elt.simplify()) 
                    .collect(Collectors.toList());  
        return this;
    
    }
}
