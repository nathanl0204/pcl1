import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Lexeur {
    private static ArrayList<String[]> token_stack = new ArrayList<>();
    private static ArrayList<Integer> indentations_stack = new ArrayList<>();
    private static int indentation_counter;

    private static ArrayList<Character> current_buffer = new ArrayList<>();
    private static char previous_caracter;
    private static boolean stop_lexing = false;

    public static String getAll(ArrayList<Character> list) {
        String res = "";
        for (int i = 0; i<list.size(); i++) {
            res = res.concat(Character.toString(list.get(i)));
        }
        return res;
    }

    public static void Lex(BufferedReader reader, int state, boolean does_read, boolean stop) throws IOException {
        if (stop) {
            return;
        }

        int car;
        boolean end_of_file;

        if (does_read) {
             car = reader.read();
             end_of_file = (car == (-1));
        }
        else {
            car = -1;
            end_of_file = false;
        }

        if (!end_of_file) {
            char curr_car;
            if (car == -1) {
                curr_car = previous_caracter;
            }
            else {
                curr_car = (char) car;
            }
            current_buffer.add(Character.valueOf(curr_car));
            previous_caracter = curr_car;

            switch (state) {
                case 0:
                    if (Character.isLowerCase(curr_car) || Character.isUpperCase(curr_car) || (Character.valueOf(curr_car) == '_')) {
                        Lex(reader, 1, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '0') {
                        String[] item = {"number", "0"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.isDigit(curr_car)) {
                        Lex(reader, 4, true, stop_lexing);
                    }
                    else if ((Character.valueOf(curr_car) == '<') | (Character.valueOf(curr_car) == '>')) {
                        Lex(reader, 8, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '=') {
                        Lex(reader, 11, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '!') {
                        Lex(reader, 14, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '(') {
                        String[] item = {"op", "LP"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == ')') {
                        String[] item = {"op", "RP"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '[') {
                        String[] item = {"op", "LB"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == ']') {
                        String[] item = {"op", "RB"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '/') {
                        Lex(reader, 25, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '+') {
                        String[] item = {"op", "ADD"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '*') {
                        String[] item = {"op", "MULT"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '%') {
                        String[] item = {"op", "MOD"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '-') {
                        String[] item = {"op", "SUB"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '\"') {
                        Lex(reader, 33, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '\n') {
                        String[] item = {"ws", "NEWLINE"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 40, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == ' ') {
                        current_buffer.remove(current_buffer.size() - 1);
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '#') {
                        current_buffer.remove(current_buffer.size() - 1);
                        Lex(reader, 39, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == ':') {
                        String[] item = {"op", "DD"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == ',') {
                        String[] item = {"op", "COM"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"error", "NR"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    break;
                case 1:
                    if (Character.isLowerCase(curr_car) || Character.isUpperCase(curr_car) || (Character.valueOf(curr_car) == '_' ) || Character.isDigit(curr_car)) {
                        Lex(reader, 1, true, stop_lexing);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"word", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false, stop_lexing);
                    }
                    break;
                case 4:
                    if (Character.isDigit(curr_car)) {
                        Lex(reader, 4, true, stop_lexing);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"number", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false, stop_lexing);
                    }
                    break;
                case 8:
                    if (Character.valueOf(curr_car) == '=') {
                        String[] item = {"relop", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"relop", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false, stop_lexing);
                    }
                    break;
                case 11:
                    if (Character.valueOf(curr_car) == '=') {
                        String[] item = {"relop", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"op", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false, stop_lexing);
                    }
                    break;
                case 14:
                    if (Character.valueOf(curr_car) == '=') {
                        String[] item = {"relop", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"error", "NR"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false, stop_lexing);
                    }
                    break;
                case 25: // AJOUTER LE NOEUD 20 QUAND TOUT FONCTIONNE
                    if (Character.valueOf(curr_car) == '/') {
                        String[] item = {"op", "DIV"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"error", "NR"};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false, stop_lexing);
                    }
                    break;
                case 33:
                    if (Character.valueOf(curr_car) == '\\') {
                        Lex(reader, 37, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) != '\"' && Character.valueOf(curr_car) != '\n') {
                        Lex(reader, 33, true, stop_lexing);
                    }
                    else {
                        String[] item = {"string", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    break;
                case 37:
                    if (Character.valueOf(curr_car) == '\"') {
                        current_buffer.remove(current_buffer.size() - 2);
                    }
                    Lex(reader, 33, true, stop_lexing);
                    break;
                case 39:
                    if (Character.valueOf(curr_car) != '\n') {
                        Lex(reader, 39, true, stop_lexing);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"com", getAll(current_buffer)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false, stop_lexing);
                    }
                    break;
                case 40:
                    if (Character.valueOf(curr_car) == ' ') {
                        indentation_counter ++;
                        current_buffer.remove(current_buffer.size() - 1);
                        Lex(reader, 40, true, stop_lexing);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        
                        int ind_stack_len = indentations_stack.size();
                        
                        if (indentation_counter > indentations_stack.get(ind_stack_len - 1)) {
                            indentations_stack.add(indentation_counter);

                            String[] item = {"ws", "BEGIN"};
                            token_stack.add(item);
                        }
                        else if (indentation_counter < indentations_stack.get(ind_stack_len - 1)) {
                            while (indentation_counter < indentations_stack.get(ind_stack_len - 1)) {
                                if (indentations_stack.isEmpty()) {
                                    String[] item = {"error", "IE"};
                                    token_stack.add(item);
                                }
                                else {
                                    indentations_stack.remove(ind_stack_len - 1);
                                    String[] item = {"ws", "END"};
                                    token_stack.add(item);
                                }

                                ind_stack_len = indentations_stack.size();
                            }
                        }

                        indentation_counter = 0;
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false, stop_lexing);
                    }
                    break;
                
            }
        }
        else {
            previous_caracter = ' ';
            stop_lexing = true;
            Lex(reader, state, false, false);
        }
    }

    public static void print_tokens(ArrayList<String[]> list) {
        System.out.print("[");
        for (int i = 0; i<list.size(); i++) {
            if (list.get(i)[1] == "NEWLINE") {
                System.out.print("\n");
            }
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

            indentations_stack.add(0);
            indentation_counter = 0;
            Lex(reader, 0, true, stop_lexing);

            print_tokens(token_stack);

            reader.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
       
    }

            
}