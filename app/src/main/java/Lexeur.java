import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import AST.*;
import ANALYSE.*;
import AST.SimpleStmt.Expr.AddBinop;
import AST.SimpleStmt.Expr.AddExpr;
import AST.SimpleStmt.Expr.MutBinop;
import AST.SimpleStmt.Expr.MutExpr;
import AST.SimpleStmt.Expr.TermExpr.Const.IntegerType;

public class Lexeur {
    public static ArrayList<String[]> token_stack = new ArrayList<>();
    public static ArrayList<Integer> indentations_stack = new ArrayList<>();
    private static int indentation_counter;
    private static int line_number = 1; // Ajout d'un compteur de ligne

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
            String[] pre_final_terminal = {"EOF", "EOF", "EOF"};
            Lexeur.token_stack.add(pre_final_terminal);
            String[] final_terminal = {"$", "$", "$"};
            Lexeur.token_stack.add(final_terminal);
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
                        String[] item = {"number", "0", String.valueOf(line_number)};
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
                        String[] item = {"op", "LP", String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == ')') {
                        String[] item = {"op", "RP", String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '[') {
                        String[] item = {"op", "LB", String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == ']') {
                        String[] item = {"op", "RB", String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '/') {
                        Lex(reader, 25, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '+') {
                        String[] item = {"op", "ADD", String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '*') {
                        String[] item = {"op", "MULT", String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '%') {
                        String[] item = {"op", "MOD", String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '-') {
                        String[] item = {"op", "SUB", String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '\"') {
                        Lex(reader, 33, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == '\n') {
                        String[] item = {"ws", "NEWLINE", String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        line_number++; // Incrémentation du numéro de ligne
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
                        String[] item = {"op", "DD", String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else if (Character.valueOf(curr_car) == ',') {
                        String[] item = {"op", "COM", String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"error", "NR", String.valueOf(line_number)};
                        token_stack.add(item);
                        System.err.println("Erreur lexicale à la ligne " + line_number + " : caractère non reconnu '" + curr_car + "'");
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
                        String[] item = {"word", getAll(current_buffer), String.valueOf(line_number)};
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
                        String[] item = {"number", getAll(current_buffer), String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false, stop_lexing);
                    }
                    break;
                case 8:
                    if (Character.valueOf(curr_car) == '=') {
                        String[] item = {"relop", getAll(current_buffer), String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"relop", getAll(current_buffer), String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false, stop_lexing);
                    }
                    break;
                case 11:
                    if (Character.valueOf(curr_car) == '=') {
                        String[] item = {"relop", getAll(current_buffer), String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"op", getAll(current_buffer), String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false, stop_lexing);
                    }
                    break;
                case 14:
                    if (Character.valueOf(curr_car) == '=') {
                        String[] item = {"relop", getAll(current_buffer), String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"error", "NR", String.valueOf(line_number)};
                        token_stack.add(item);
                        System.err.println("Erreur lexicale à la ligne " + line_number + " : caractère non reconnu après '!'");
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, false, stop_lexing);
                    }
                    break;
                case 25:
                    if (Character.valueOf(curr_car) == '/') {
                        String[] item = {"op", "DIV", String.valueOf(line_number)};
                        token_stack.add(item);
                        current_buffer = new ArrayList<>();
                        Lex(reader, 0, true, stop_lexing);
                    }
                    else {
                        current_buffer.remove(current_buffer.size() - 1);
                        String[] item = {"error", "NR", String.valueOf(line_number)};
                        token_stack.add(item);
                        System.err.println("Erreur lexicale à la ligne " + line_number + " : caractère non reconnu après '/'");
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
                        String[] item = {"string", getAll(current_buffer), String.valueOf(line_number)};
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
                        String[] item = {"com", getAll(current_buffer), String.valueOf(line_number)};
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

                            String[] item = {"ws", "BEGIN", String.valueOf(line_number)};
                            token_stack.add(item);
                        }
                        else if (indentation_counter < indentations_stack.get(ind_stack_len - 1)) {
                            outerloop:
                            while (indentation_counter < indentations_stack.get(ind_stack_len - 1)) {
                                if (indentations_stack.isEmpty()) {
                                    String[] item = {"error", "IE", String.valueOf(line_number)};
                                    token_stack.add(item);
                                    System.err.println("Erreur lexicale à la ligne " + line_number + " : indentation incorrecte");
                                    break outerloop;
                                }
                                else {
                                    indentations_stack.remove(ind_stack_len - 1);
                                    String[] item = {"ws", "END", String.valueOf(line_number)};
                                    String[] last_item = token_stack.get(token_stack.size() - 1);
                                    token_stack.remove(token_stack.size() - 1);
                                    token_stack.add(item);
                                    token_stack.add(last_item);
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

    public static boolean is_in(String element, String[] array) {
        for (int i = 0; i<array.length; i++) {
            if (element.equals(array[i])){
                return true;
            } 
        }
        return false;
    }


    public static void main(String[] args){

        try{
            FileReader fileReader = new FileReader("src/main/resources/test.py");
        
            BufferedReader reader = new BufferedReader(fileReader);

            String[] keywords = {"and", "def", "else", "for", "if", "True", "False", "in", "not", "or", "print", "return", "None"};

            indentations_stack.add(0);
            indentation_counter = 0;
            Lex(reader, 0, true, stop_lexing);

            int tokens_length = token_stack.size();

            for (int i = 0; i<tokens_length; i++) {
                String[] item = token_stack.get(i);
                
                if (item[0].equals(String.valueOf("word"))) {
                    if (is_in(item[1], keywords)) {
                        String[] new_item = {"keyword", item[1], item[2]};
                        token_stack.set(i, new_item);
                    }
                    else {
                        String[] new_item = {"id", item[1], item[2]};
                        token_stack.set(i, new_item);
                    }
                }
                else if (item[1].equals("==")) {
                    String[] new_item = {item[0], "EQ", item[2]};
                    token_stack.set(i, new_item);
                }
                else if (item[1].equals("=")) {
                    String[] new_item = {item[0], "EQ", item[2]};
                    token_stack.set(i, new_item);
                }
                else if (item[1].equals("!=")) {
                    String[] new_item = {item[0], "NE", item[2]};
                    token_stack.set(i, new_item);
                }
                else if (item[1].equals("<")) {
                    String[] new_item = {item[0], "LT", item[2]};
                    token_stack.set(i, new_item);
                }
                else if (item[1].equals(">")) {
                    String[] new_item = {item[0], "GT", item[2]};
                    token_stack.set(i, new_item);
                }
            }

            print_tokens(token_stack);

            reader.close();
        } catch (IOException e) {
			e.printStackTrace();
		}

        AnalyseFile analyseFile = new AnalyseFile(token_stack);

        try {
            // Lancer l'analyse
            analyseFile.Analyse();
            System.out.println("Analyse terminée avec succès !");
        } catch (AnalyseException e) {
            // Gestion des erreurs
            System.err.println("Erreur lors de l'analyse : " + e.getMessage());
        }


        // test AST
        MutExpr mut5 = new MutExpr();
        mut5.setRight(new IntegerType(70));
        mut5.setLeft(new IntegerType(60));
        mut5.setSymbole(MutBinop.DIV);

        MutExpr mut4 = new MutExpr();
        mut4.setRight(mut5);
        mut4.setLeft(new IntegerType(50));
        mut4.setSymbole(MutBinop.MULT);

        MutExpr mut3 = new MutExpr();
        mut3.setRight(mut4);
        mut3.setLeft(new IntegerType(40));
        mut3.setSymbole(MutBinop.MULT);




        MutExpr mut2 = new MutExpr();
        mut2.setRight(new IntegerType(40));
        mut2.setLeft(new IntegerType(30));
        mut2.setSymbole(MutBinop.DIV);
        
        MutExpr mut1 = new MutExpr();
        mut1.setRight(mut2);
        mut1.setLeft(new IntegerType(20));
        mut1.setSymbole(MutBinop.MOD);

        MutExpr mut = new MutExpr();
        mut.setRight(mut1);
        mut.setLeft(new IntegerType(10));
        mut.setSymbole(MutBinop.MULT);


        AddExpr expr4 = new AddExpr();
        expr4.setRight(mut3);
        expr4.setLeft(new IntegerType(5));
        expr4.setSymbole(AddBinop.ADD);

        AddExpr expr3 = new AddExpr();
        expr3.setRight(expr4);
        expr3.setLeft(new IntegerType(4));
        expr3.setSymbole(AddBinop.SUB);

        AddExpr expr2 = new AddExpr();
        expr2.setRight(expr3);
        expr2.setLeft(new IntegerType(3));
        expr2.setSymbole(AddBinop.SUB);
        
        AddExpr expr1 = new AddExpr();
        expr1.setRight(expr2);
        expr1.setLeft(new IntegerType(2));
        expr1.setSymbole(AddBinop.ADD);

        AddExpr expr = new AddExpr();
        expr.setRight(expr1);
        expr.setLeft(mut);
        expr.setSymbole(AddBinop.ADD);

        
        AST ast = new AST();
        ast.root = new File();
        expr.leftRotate();
        ast.root.addStmts(expr);

        ast.vizualisation();
    }
}