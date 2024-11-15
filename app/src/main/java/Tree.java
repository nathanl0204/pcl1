import java.util.ArrayList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Tree {
    private String node;
    private ArrayList<Tree> childs;

    public Tree(String node) {
        this.node = node;
        this.childs = new ArrayList<>();;
    }

    public ArrayList<Tree> getChilds() {
        return this.childs;
    }

    public void show(boolean is_first) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/tree.puml"))) {
            if (is_first) {
                writer.write("@startuml\n");
                writer.write("[*] --> " + this.node + "\n");
            }
            ArrayList<Tree> list = this.childs;
            for (int i = 0; i < list.size(); i++) {
                Tree child = list.get(i);
                writer.write(this.node + " --> " + child.node + "\n");
                child.show(false);
            }
            if (is_first) {
                writer.write("@enduml\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }
}
