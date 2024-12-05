package ANALYSE; 

import java.util.ArrayList;
import java.util.Arrays;

public class ConvertToken{

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
        public String[] getCurrentToken(ArrayList<String[]> token_stack) {
        return token_stack.get(0);
    }

}