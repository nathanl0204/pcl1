import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lexeur {
    public static void main(String[] args){

        try{
             FileReader fileReader = new FileReader("src/test.py");
        
            BufferedReader reader = new BufferedReader(fileReader);
            
            String line = reader.readLine();
            
            while (line != null) {

                System.out.println(line);
                
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
       
    }

            
}