import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Lexeur {
    private static ArrayList<String[]> token_stack = new ArrayList<>();

    private static ArrayList<Character> current_buffer = new ArrayList<>();
    private static char previous_caracter;

    public static String getAll(ArrayList<Character> list) {
        String res = "";
        for (int i = 0; i<list.size(); i++) {
            res = res.concat(Character.toString(list.get(i)));
        }
        return res;
    }

    public static void Lex(BufferedReader reader, int state, boolean does_read) throws IOException {
        int car;
        boolean readable;

        if (does_read) {
             car = reader.read();
             readable = !(car == (-1));
        }
        else {
            car = -1;
            readable = true;
        }

        if (readable) {
            char curr_car;
            if (car == -1) {
                curr_car = previous_caracter;
            }
            else {
                curr_car = (char) car;
            }
            current_buffer.add(Character.valueOf(curr_car));

            System.out.println(Arrays.toString(current_buffer.toArray()) + "," + curr_car + "|");

            switch (state) {
                case 0:
                    if (Character.isLowerCase(curr_car) || Character.isUpperCase(curr_car) || (Character.valueOf(curr_car) == '_')) {
                        Lex(reader, 1, true);
                    }
                    else if (Character.valueOf(curr_car) == '0') {
                        String[] item = {"number", "0"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else if (Character.isDigit(curr_car)) {
                        Lex(reader, 4, true);
                    }
                    else if ((Character.valueOf(curr_car) == '<') | (Character.valueOf(curr_car) == '>')) {
                        Lex(reader, 8, true);
                    }
                    else if (Character.valueOf(curr_car) == '=') {
                        Lex(reader, 11, true);
                    }
                    else if (Character.valueOf(curr_car) == '!') {
                        Lex(reader, 14, true);
                    }
                    else if (Character.valueOf(curr_car) == '\\') {
                        Lex(reader, 18, true);
                    }
                    else if (Character.valueOf(curr_car) == '(') {
                        String[] item = {"op", "LP"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else if (Character.valueOf(curr_car) == ')') {
                        String[] item = {"op", "RP"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else if (Character.valueOf(curr_car) == '[') {
                        String[] item = {"op", "LB"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else if (Character.valueOf(curr_car) == ']') {
                        String[] item = {"op", "RB"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else if (Character.valueOf(curr_car) == '/') {
                        Lex(reader, 25, true);
                    }
                    else if (Character.valueOf(curr_car) == '+') {
                        String[] item = {"op", "ADD"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else if (Character.valueOf(curr_car) == '*') {
                        String[] item = {"op", "MULT"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else if (Character.valueOf(curr_car) == '%') {
                        String[] item = {"op", "MOD"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else if (Character.valueOf(curr_car) == '-') {
                        String[] item = {"op", "SUB"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else if (Character.valueOf(curr_car) == '\"') {
                        Lex(reader, 33, true);
                    }
                    else if (Character.valueOf(curr_car) == '\n') {
                        String[] item = {"ws", "NEWLINE"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        Lex(reader, 0, true);
                    }
                    break;
                case 1:
                    if (Character.isLowerCase(curr_car) || Character.isUpperCase(curr_car) || (Character.valueOf(curr_car) == '_' ) || Character.isDigit(curr_car)) {
                        Lex(reader, 1, true);
                    }
                    else {
                        previous_caracter = curr_car;
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"word", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false);
                    }
                    break;
                case 4:
                    if (Character.isDigit(curr_car)) {
                        previous_caracter = curr_car;
                        Lex(reader, 4, true);
                    }
                    else {
                        previous_caracter = curr_car;
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"number", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false);
                    }
                    break;
                case 8:
                    if (Character.valueOf(curr_car) == '=') {
                        String[] item = {"relop", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else {
                        previous_caracter = curr_car;
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"relop", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false);
                    }
                    break;
                case 11:
                    if (Character.valueOf(curr_car) == '=') {
                        String[] item = {"relop", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else {
                        previous_caracter = curr_car;
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"op", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false);
                    }
                    break;
                case 14:
                    if (Character.valueOf(curr_car) == '=') {
                        String[] item = {"relop", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else {
                        previous_caracter = curr_car;
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"error", "NR"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false);
                    }
                    break;
                case 18:
                    if (Character.valueOf(curr_car) == '\"') {
                        String[] item = {"string", "\""};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else {
                        previous_caracter = curr_car;
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"error", "NR"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false);
                    }
                    break;
                case 25: // AJOUTER LE NOEUD 20 QUAND TOUT FONCTIONNE
                    if (Character.valueOf(curr_car) == '/') {
                        String[] item = {"op", "DIV"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    else {
                        previous_caracter = curr_car;
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"error", "NR"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false);
                    }
                    break;
                case 33:
                    if (Character.valueOf(curr_car) != '\"') {
                        Lex(reader, 33, true);
                    }
                    else {
                        String[] item = {"string", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true);
                    }
                    break;
            }
        }
    }

    public static void print_tokens(ArrayList<String[]> list) {
        System.out.print("[");
        for (int i = 0; i<list.size(); i++) {
            System.out.print(Arrays.toString(list.get(i)));
            if (i != (list.size() - 1)) {
                System.out.print(",");
            }
        }
        System.out.print("]");
    }


    public static void main(String[] args){

        try{
            FileReader fileReader = new FileReader("src/test.py");
        
            BufferedReader reader = new BufferedReader(fileReader);

            Lex(reader, 0, true);

            print_tokens(token_stack);

            reader.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
       
    }

            
}