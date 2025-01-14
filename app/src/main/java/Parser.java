import GeneralTree.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Parser {
    private ArrayList<String[]> token_stack = new ArrayList<>();
    private String[] terminals;
    private String[] non_terminals;
    Map<String, Map<String, String[]>> ll1_table;
    private ArrayList<String> errors_stack = new ArrayList<>();
    private ArrayList<String> grammar_stack = new ArrayList<>();
    private boolean reading_done;
    private Tree tree;
    private GeneralTree complex_AST;
    
    public Parser(ArrayList<String[]> token_stack) {
        this.ll1_table = getLL1table();
        this.token_stack = token_stack;
        this.reading_done = false;
        this.errors_stack = new ArrayList<>();
        this.grammar_stack = new ArrayList<>();
        this.grammar_stack.add("$");
        this.grammar_stack.add("file");
        this.complex_AST = null;
    }

    public Map<String, Map<String, String[]>> getLL1table() {
        Map<String, Map<String, String[]>> table = new HashMap<>();

        String[][] rules = {{}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {}, {}, {}, {}, {}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {"opt_newline", "def_etoile", "stmt_plus", "EOF"}, {}, {}, {"NEWLINE"}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {}, {"ε"}, {"ε"}, {}, {"ε"}, {}, {"ε"}, {}, {"ε"}, {}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {"deft", "def_etoile"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {}, {"ε"}, {"ε"}, {}, {"ε"}, {}, {"ε"}, {}, {"ε"}, {}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {"stmt", "stmt_plus_rest"}, {"stmt", "stmt_plus_rest"}, {}, {}, {}, {}, {}, {"stmt", "stmt_plus_rest"}, {"stmt", "stmt_plus_rest"}, {}, {"stmt", "stmt_plus_rest"}, {}, {"stmt", "stmt_plus_rest"}, {}, {"stmt", "stmt_plus_rest"}, {}, {"stmt", "stmt_plus_rest"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"stmt", "stmt_plus_rest"}, {"stmt", "stmt_plus_rest"}, {"stmt", "stmt_plus_rest"}, {"stmt", "stmt_plus_rest"}, {"stmt", "stmt_plus_rest"}, {}, {"ε"}, {}, {}, {"stmt_plus"}, {"stmt_plus"}, {}, {}, {}, {}, {"ε"}, {"stmt_plus"}, {"stmt_plus"}, {}, {"stmt_plus"}, {}, {"stmt_plus"}, {}, {"stmt_plus"}, {}, {"stmt_plus"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"stmt_plus"}, {"stmt_plus"}, {"stmt_plus"}, {"stmt_plus"}, {"stmt_plus"}, {}, {}, {}, {"def", "ident", "(", "ident_etoile_virgule", ")", ":", "suite"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"ident_plus_virgule"}, {}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"ident", "ident_plus_virgule_rest"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"ε"}, {}, {",", "ident_plus_virgule"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"NEWLINE", "BEGIN", "stmt_plus", "END"}, {}, {"simple_stmt", "NEWLINE"}, {"simple_stmt", "NEWLINE"}, {}, {}, {}, {}, {}, {"simple_stmt", "NEWLINE"}, {"simple_stmt", "NEWLINE"}, {}, {"simple_stmt", "NEWLINE"}, {}, {}, {}, {}, {}, {"simple_stmt", "NEWLINE"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"simple_stmt", "NEWLINE"}, {"simple_stmt", "NEWLINE"}, {"simple_stmt", "NEWLINE"}, {"simple_stmt", "NEWLINE"}, {"simple_stmt", "NEWLINE"}, {}, {}, {}, {}, {"or_expr", "simple_stmt_fact"}, {"or_expr", "simple_stmt_fact"}, {}, {}, {}, {}, {}, {"return", "expr"}, {"print", "(", "expr", ")"}, {}, {"or_expr", "simple_stmt_fact"}, {}, {}, {}, {}, {}, {"or_expr", "simple_stmt_fact"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"or_expr", "simple_stmt_fact"}, {"or_expr", "simple_stmt_fact"}, {"or_expr", "simple_stmt_fact"}, {"or_expr", "simple_stmt_fact"}, {"or_expr", "simple_stmt_fact"}, {}, {}, {"simple_stmt_fact_fact"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"simple_stmt_fact_fact"}, {"[", "expr", "]", "expr_crochet_etoile"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"=", "expr"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"simple_stmt", "NEWLINE"}, {"simple_stmt", "NEWLINE"}, {}, {}, {}, {}, {}, {"simple_stmt", "NEWLINE"}, {"simple_stmt", "NEWLINE"}, {}, {"simple_stmt", "NEWLINE"}, {}, {"for", "ident", "in", "expr", ":", "suite"}, {}, {"if", "expr", ":", "suite", "stmt_else"}, {}, {"simple_stmt", "NEWLINE"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"simple_stmt", "NEWLINE"}, {"simple_stmt", "NEWLINE"}, {"simple_stmt", "NEWLINE"}, {"simple_stmt", "NEWLINE"}, {"simple_stmt", "NEWLINE"}, {}, {"ε"}, {}, {}, {"ε"}, {"ε"}, {}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {}, {"ε"}, {}, {"ε"}, {}, {"ε"}, {"else", ":", "suite"}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {"or_expr", "expr_crochet_etoile"}, {"or_expr", "expr_crochet_etoile"}, {}, {}, {}, {}, {}, {}, {}, {}, {"or_expr", "expr_crochet_etoile"}, {}, {}, {}, {}, {}, {"or_expr", "expr_crochet_etoile"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"or_expr", "expr_crochet_etoile"}, {"or_expr", "expr_crochet_etoile"}, {"or_expr", "expr_crochet_etoile"}, {"or_expr", "expr_crochet_etoile"}, {"or_expr", "expr_crochet_etoile"}, {}, {}, {"ε"}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {}, {"[", "expr", "]", "expr_crochet_etoile"}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"and_expr", "or_expr_rest"}, {"and_expr", "or_expr_rest"}, {}, {}, {}, {}, {}, {}, {}, {}, {"and_expr", "or_expr_rest"}, {}, {}, {}, {}, {}, {"and_expr", "or_expr_rest"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"and_expr", "or_expr_rest"}, {"and_expr", "or_expr_rest"}, {"and_expr", "or_expr_rest"}, {"and_expr", "or_expr_rest"}, {"and_expr", "or_expr_rest"}, {}, {}, {"ε"}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"binop_or", "or_expr"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"not_expr", "and_expr_rest"}, {"not_expr", "and_expr_rest"}, {}, {}, {}, {}, {}, {}, {}, {}, {"not_expr", "and_expr_rest"}, {}, {}, {}, {}, {}, {"not_expr", "and_expr_rest"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"not_expr", "and_expr_rest"}, {"not_expr", "and_expr_rest"}, {"not_expr", "and_expr_rest"}, {"not_expr", "and_expr_rest"}, {"not_expr", "and_expr_rest"}, {}, {}, {"ε"}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"binop_and", "and_expr"}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"comp_expr"}, {"comp_expr"}, {}, {}, {}, {}, {}, {}, {}, {}, {"comp_expr"}, {}, {}, {}, {}, {}, {"not", "comp_expr"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"comp_expr"}, {"comp_expr"}, {"comp_expr"}, {"comp_expr"}, {"comp_expr"}, {}, {}, {}, {}, {"add_expr", "comp_expr_rest"}, {"add_expr", "comp_expr_rest"}, {}, {}, {}, {}, {}, {}, {}, {}, {"add_expr", "comp_expr_rest"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"add_expr", "comp_expr_rest"}, {"add_expr", "comp_expr_rest"}, {"add_expr", "comp_expr_rest"}, {"add_expr", "comp_expr_rest"}, {"add_expr", "comp_expr_rest"}, {}, {}, {"ε"}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"binop_comp", "add_expr"}, {"binop_comp", "add_expr"}, {"binop_comp", "add_expr"}, {"binop_comp", "add_expr"}, {"binop_comp", "add_expr"}, {"binop_comp", "add_expr"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"mut_expr", "add_expr_rest"}, {"mut_expr", "add_expr_rest"}, {}, {}, {}, {}, {}, {}, {}, {}, {"mut_expr", "add_expr_rest"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"mut_expr", "add_expr_rest"}, {"mut_expr", "add_expr_rest"}, {"mut_expr", "add_expr_rest"}, {"mut_expr", "add_expr_rest"}, {"mut_expr", "add_expr_rest"}, {}, {}, {"ε"}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {}, {"binop_add", "add_expr"}, {"binop_add", "add_expr"}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"terminal_expr", "mut_expr_rest"}, {"terminal_expr", "mut_expr_rest"}, {}, {}, {}, {}, {}, {}, {}, {}, {"terminal_expr", "mut_expr_rest"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"terminal_expr", "mut_expr_rest"}, {"terminal_expr", "mut_expr_rest"}, {"terminal_expr", "mut_expr_rest"}, {"terminal_expr", "mut_expr_rest"}, {"terminal_expr", "mut_expr_rest"}, {}, {}, {"ε"}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {}, {"ε"}, {"ε"}, {"binop_mut", "mut_expr"}, {"binop_mut", "mut_expr"}, {"binop_mut", "mut_expr"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"ident", "expr_rest_ident"}, {"(", "expr", ")"}, {}, {}, {}, {}, {}, {}, {}, {}, {"[", "expr_etoile_virgule", "]"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"const"}, {"const"}, {"const"}, {"const"}, {"const"}, {}, {}, {"ε"}, {}, {}, {"(", "expr_etoile_virgule", ")"}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"expr_plus_virgule"}, {"expr_plus_virgule"}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {"expr_plus_virgule"}, {"ε"}, {}, {}, {}, {}, {"expr_plus_virgule"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"expr_plus_virgule"}, {"expr_plus_virgule"}, {"expr_plus_virgule"}, {"expr_plus_virgule"}, {"expr_plus_virgule"}, {}, {}, {}, {}, {"expr", "expr_plus_virgule_rest"}, {"expr", "expr_plus_virgule_rest"}, {}, {}, {}, {}, {}, {}, {}, {}, {"expr", "expr_plus_virgule_rest"}, {}, {}, {}, {}, {}, {"expr", "expr_plus_virgule_rest"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"expr", "expr_plus_virgule_rest"}, {"expr", "expr_plus_virgule_rest"}, {"expr", "expr_plus_virgule_rest"}, {"expr", "expr_plus_virgule_rest"}, {"expr", "expr_plus_virgule_rest"}, {}, {}, {}, {}, {}, {}, {"ε"}, {}, {",", "expr_plus_virgule"}, {}, {}, {}, {}, {}, {}, {"ε"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"+"}, {"-"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"*"}, {"//"}, {"%"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"<="}, {">="}, {">"}, {"<"}, {"!="}, {"=="}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"and"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"or"}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {"integer"}, {"string"}, {"True"}, {"False"}, {"None"},  {}};

        String[] non_terminals = {"file", "opt_newline", "def_etoile", "stmt_plus", "stmt_plus_rest", "deft", "ident_etoile_virgule", "ident_plus_virgule", "ident_plus_virgule_rest", "suite", "simple_stmt", "simple_stmt_fact", "simple_stmt_fact_fact", "stmt", "stmt_else", "expr", "expr_crochet_etoile", "or_expr", "or_expr_rest", "and_expr", "and_expr_rest", "not_expr", "comp_expr", "comp_expr_rest", "add_expr", "add_expr_rest", "mut_expr", "mut_expr_rest", "terminal_expr", "expr_rest_ident", "expr_etoile_virgule", "expr_plus_virgule", "expr_plus_virgule_rest", "binop_add", "binop_mut", "binop_comp", "binop_and", "binop_or", "const"};

        String[] terminals = {"EOF", "NEWLINE", "def", "ident", "(", ")", ":", ",", "BEGIN", "END", "return", "print", "=", "[", "]", "for", "in", "if", "else", "not", "+", "-", "*", "//", "%", "<=", ">=", ">", "<", "!=", "==", "and", "or", "integer", "string", "True", "False", "None", "$"};

        this.non_terminals = non_terminals;
        this.terminals = terminals;

        int ligns = non_terminals.length;
        int columns = terminals.length;

        for (int i = 0; i<ligns; i++) {
            Map<String, String[]> lign = new HashMap<>();
            
            table.put(non_terminals[i], lign);
            for (int j = 0; j<columns; j++) {
                lign.put(terminals[j], rules[i*columns+j]);
            }
        }

        return table;
    }

    public void print_ll1_table() {
        int ligns = this.ll1_table.size();
        int columns = this.ll1_table.get("file").size();

        System.out.print("{");
        for (int i = 0; i<ligns; i++) {
            System.out.print("{" + this.non_terminals[i] + ":\n");
            for (int j = 0; j<columns-1; j++) {
                System.out.print("{" + this.terminals[j] + ":" + Arrays.toString(this.ll1_table.get(this.non_terminals[i]).get(this.terminals[j])) + "}\n");
            }
            System.out.print("{" + this.terminals[columns-1] + ":" + Arrays.toString(this.ll1_table.get(this.non_terminals[i]).get(this.terminals[columns-1])) + "}");
            System.out.print("}\n");
        }
    }

    public String[] getCurrentToken() {
        return this.token_stack.get(0);
    }

    public Tree getTree() {
        return this.tree;
    }
    
    public String getConvertedValue(String[] token) {
        if (token[0].equals("$")) {
            return "$";
        }
        if (token[0].equals("id")) {
            return "ident";
        }
        else if (token[0].equals("keyword")) {
            return token[1];
        }
        else if (token[0].equals("number")) {
            return "integer";
        }
        else if (token[0].equals("string")) {
            return "string";
        }
        else if (token[0].equals("op")) {
            if (token[1].equals("LP")) {
                return "(";
            }
            else if (token[1].equals("RP")) {
                return ")";
            }
            else if (token[1].equals("LB")) {
                return "[";
            }
            else if (token[1].equals("RB")) {
                return "]";
            }
            else if (token[1].equals("MOD")) {
                return "%";
            }
            else if (token[1].equals("DD")) {
                return ":";
            }
            else if (token[1].equals("EQ")) {
                return "=";
            }
            else if (token[1].equals("DIV")) {
                return "//";
            }
            else if (token[1].equals("SUB")) {
                return "-";
            }
            else if (token[1].equals("ADD")) {
                return "+";
            }
            else if (token[1].equals("MULT")) {
                return "*";
            }
            else {
                return ",";
            }
        }
        else if (token[0].equals("relop")) {
            if (token[1].equals("LE")) {
                return "<=";
            }
            else if (token[1].equals("GE")) {
                return ">=";
            }
            else if (token[1].equals("LT")) {
                return "<";
            }
            else if (token[1].equals("GT")) {
                return ">";
            }
            else if (token[1].equals("EQ")) {
                return "==";
            }
            else {
                return "!=";
            }
        }
        else { // case of ws
            return token[1];
        }
    }

    public boolean is_in_array(String[] array, String element) {
        for (int i = 0; i<array.length; i++) {
            if (array[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    public void remove_unecessary_tokens() {
        for (int i = 0; i<this.token_stack.size(); i++) {
            if (this.token_stack.get(i)[0].equals("error") || this.token_stack.get(i)[0].equals("com")) {
                this.token_stack.remove(i);
            }
        }
    }

    public void get_tokens_to_next_lign(String last_line_creator) {
        String current_token = getConvertedValue(this.token_stack.get(0));

        while (current_token != "NEWLINE") {
            this.token_stack.remove(0);
            current_token = getConvertedValue(this.token_stack.get(0));
        }
    }

    public void get_rules_to_previous_line_creator(String last_line_creator) {
        if (!last_line_creator.equals("NEWLINE")){
            String current_rule = this.grammar_stack.get(this.grammar_stack.size() - 1);
            System.out.println(last_line_creator);

            while (!current_rule.equals(last_line_creator)) {
                System.out.println(current_rule);
                System.out.println(last_line_creator);
                this.grammar_stack.remove(this.grammar_stack.size() - 1);
                current_rule = this.grammar_stack.get(this.grammar_stack.size() - 1);
            }
        }
    }
    
    public void top_down_parsing_algorithm() {
        this.remove_unecessary_tokens();

        LinkedList<GeneralTree> treesList = new LinkedList<>();
        GeneralTree parsing_tree = new GeneralTree("file");
        treesList.addLast(parsing_tree);
        int index_where_add_children = 0;

        while (!this.reading_done) {
            String X = this.grammar_stack.get(this.grammar_stack.size() - 1);
            this.grammar_stack.remove(this.grammar_stack.size() - 1);

            String[] a = getCurrentToken();

            //System.out.print(X.toString() + " | " + Arrays.toString(a) + " | ");

            if (is_in_array(this.non_terminals, X)) {
                String[] current_rule;

                if (X.equals("simple_stmt") && getConvertedValue(a).equals("ident")) {
                    if (getConvertedValue(this.token_stack.get(1)).equals("=")) {
                        current_rule = new String[]{"ident", "=", "expr"};
                    } else {
                        current_rule = new String[]{"or_expr", "simple_stmt_fact"};
                    }
                } else {
                    current_rule = this.ll1_table.get(X).get(getConvertedValue(a));
                }


                int terminal_length = current_rule.length;

                if (terminal_length > 0) {
                    if (!current_rule[0].equals("ε")) {
                        GeneralTree parent = treesList.get(index_where_add_children);
                        for (int i = 0; i<terminal_length; i++) {
                            String node = current_rule[terminal_length - 1 - i];
                            this.grammar_stack.add(node);
                            GeneralTree child = new GeneralTree(node);
                            parent.addChild(child);
                            treesList.addLast(child);
                        }
                        treesList.removeFirst();
    
                       // System.out.println(X.toString() + " -> " + Arrays.toString(current_rule));
                    }
                    else {
                        treesList.removeFirst();

                        // System.out.println("On ne fait rien (epsilon)");
                    }
                }
                else {                 
                    treesList.removeFirst();
                    this.errors_stack.add("Expression invalide ligne " + a[2].toString());
                    // System.out.println("On skip ce non terminal: " + X.toString());
                }
            }
            else {
                if (X.equals("$")) {
                    if (!getConvertedValue(a).equals("$")) {
                        this.errors_stack.add("Le token " + getConvertedValue(a).toString() + "est de trop (ligne" + a[2].toString() + ")");
                    }
                    this.reading_done = true;
                }
                else if (!X.equals(getConvertedValue(a))) {
                    treesList.removeFirst();

                    this.errors_stack.add(getConvertedValue(a).toString() + ": ce caractère n'est pas attendu à la ligne" + a[2].toString());
                    //System.out.println("On skip ce token: " + X.toString());
                }
                else {
                    treesList.removeFirst();

                    this.token_stack.remove(0);
                    //System.out.println("On écrit le token: " + Arrays.toString(a));
                }
            }
        }
        this.complex_AST = parsing_tree;
    }

    public void printComplexAST() {
        this.complex_AST.printTreeDot();
    }
}

