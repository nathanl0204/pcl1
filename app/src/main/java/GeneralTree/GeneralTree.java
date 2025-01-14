package GeneralTree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeneralTree {
    String value;
    List<GeneralTree> children;

    public GeneralTree(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public void addChild(GeneralTree child) {
        this.children.add(child);
    }

    public void printTreeDot() {
        String fileName = "src/main/resources/tree.dot";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("digraph G {\n");
            writeTreeToDot(this, writer, 0);
            writer.write("}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTreeToDot(GeneralTree node, BufferedWriter writer, int i) throws IOException {
        if (node == null || node.value == null || node.value.trim().isEmpty()) {
            return;
        }

        writer.write("\"" + node.value + ", " + i + "\";\n");

        for (GeneralTree child : node.children) {
            int j = 0;
            if (child != null && child.value != null && !child.value.trim().isEmpty()) {
                j+=1;
                writer.write("\"" + node.value + ", " + i + "\" -> \"" + child.value + ", " + (i + j) + "\";\n");
                writeTreeToDot(child, writer, i+j);
            }
        }
    }
}
