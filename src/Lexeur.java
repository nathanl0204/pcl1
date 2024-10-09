
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Lexeur {
    private static List<String> mot_cles = Arrays.asList("and","def","else","for","if","True","False",
                                                        "in","not","or","print","return","None");
    private static List<String> symboles = Arrays.asList("<","<=",">",">=","==","!","=","+","-","*",
                                                        "//","%","[","]","(",")",":"); 
    private static List<Character> accepted_charactere = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 
    'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 
    'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', 
    '1', '2', '3', '4', '5', '6', '7', '8', '9', '_', ' ','\n','\t',':','"','<','>','!','=','+','-','*',
    '/','%','[',']','(',')');

    public static void main(String[] args){
       
        try (FileReader fileReader = new FileReader("test.mpy")){
            String buffer = new String();
            char caractere;
            char previous_caractere = ' ';
            FileWriter output = new FileWriter("output.txt");
            boolean word_is_identif = true;
            boolean word_is_0 = true;
            boolean error = false;

            while ((caractere = (char) fileReader.read()) != (char)-1) {
                if(!accepted_charactere.contains(caractere) && !error){
                    error = true;
                    System.err.println("caractere non accepté utilisé ("+caractere+")");
                }

                //Si le caractere est un espace ou un retour a la ligne, le buffer est vider
                if (caractere == ' ' || caractere == '\n'){
                    if (buffer!=""){
                        output.write(buffer+"|");
                        buffer = "";
                        error = false;
                    }
                }
                
                //Si on lit un caractere qui est un symbole on a besoin de connaitre le prochain caractere 
                //pour savoir si il est de longueur unitaire ou non
                if (symboles.contains(""+caractere)){
                    if (buffer!=""){
                        output.write(buffer+"|");
                        buffer = "";
                        error = false;
                    }
                   
                    if (symboles.contains(""+previous_caractere+caractere)){
                        output.write(""+previous_caractere+caractere+"|");
                        previous_caractere = ' ';
                        
                    }
                    else {
                        
                        if (previous_caractere != ' '){
                            output.write(previous_caractere+"|");
                        
                        }
                        previous_caractere = caractere;
                    }
                        
                }
                else if (caractere != ' ' && caractere != '\n') {
                    if (buffer.length() > 100 && !error){
                        error = true;System.err.println("l'identificateur trop long");
                    }

                    boolean char_is_int = 48<=(int) caractere && (int) caractere<=57;
                    

                    if (previous_caractere != ' '){
                        output.write(previous_caractere+"|");
                        previous_caractere = ' ';
                    }

                    if (buffer == ""){
                        word_is_identif = !char_is_int;
                        word_is_0 = caractere == '0';
                    }
                    else if (!char_is_int && !word_is_identif && !error){
                        error = true;
                        System.err.println("un identificateur doit commencer par une lettre de l'alphabet");
                    }
                    else if (word_is_0 && char_is_int && !error){
                        error = true;
                        System.err.println("un nombre ne peut commencer par 0");                  
                    }

                    buffer += caractere;
                }
            }


            if (buffer != "" || previous_caractere != ' '){
                output.write(buffer+previous_caractere);
                buffer = "";
            }
            output.write("\n");

            output.close();
            fileReader.close();

        } catch (IOException e) {
			e.printStackTrace();
		}
 
    }            
}