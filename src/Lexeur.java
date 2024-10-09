
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Lexeur {
    private static List<String> mot_cles = Arrays.asList("and","def","else","for","if","True","False",
    "in","not","or","print","return","None");
    private static List<String> symboles = Arrays.asList("<","<=",">",">=","==","!","=","+","-","*","//","%","[","]","(",")"); 

    public static void main(String[] args){
       
        try {
            FileReader fileReader = new FileReader("src/test.py");
            String buffer = new String();
            int caractere;
            FileWriter writer = new FileWriter("output.txt");

            while ((caractere = fileReader.read()) != -1) {
                if ((char)caractere != ' ') {
                    
                
                    if (mot_cles.contains(buffer+(char) caractere)) {
                        writer.write(buffer+"|");
                        buffer = "";
                    }
                    
                    
                    
                }
                if (symboles.contains(""+(char)caractere)){
                    writer.write(buffer+"|"+caractere+"|");
                    buffer = "";
                }
                if ((char)caractere == ' ' && buffer != ""){
                    writer.write(buffer+"|");
                    buffer = "";
                }

                



            }
            if (buffer != ""){
                writer.write(buffer);
                buffer = "";
            }
            writer.close();
            fileReader.close();

        } catch (IOException e) {
			e.printStackTrace();
		}
 
    }            
}