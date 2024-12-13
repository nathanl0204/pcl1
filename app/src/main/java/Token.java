
public class Token {
    
    private String type;
    private String value;
    private String line;


    public Token(String type, String value, String line){
        this.type = type;
        this.value = value;
        this.line = line;
    }

    public String getLine(){
        return line;
    }

    public String getValue(){
        return value;
    }

    public String getSymbole() {
        if (type.equals("$")) {
            return "$";
        }
        if (type.equals("id")) {
            return "ident";
        }
        else if (type.equals("keyword")) {
            return value;
        }
        else if (type.equals("number")) {
            return "integer";
        }
        else if (type.equals("string")) {
            return "string";
        }
        else if (type.equals("op")) {
            if (value.equals("LP")) {
                return "(";
            }
            else if (value.equals("RP")) {
                return ")";
            }
            else if (value.equals("LB")) {
                return "[";
            }
            else if (value.equals("RB")) {
                return "]";
            }
            else if (value.equals("MOD")) {
                return "%";
            }
            else if (value.equals("DD")) {
                return ":";
            }
            else if (value.equals("EQ")) {
                return "=";
            }
            else if (value.equals("DIV")) {
                return "//";
            }
            else if (value.equals("SUB")) {
                return "-";
            }
            else if (value.equals("ADD")) {
                return "+";
            }
            else if (value.equals("MULT")) {
                return "*";
            }
            else {
                return ",";
            }
        }
        else if (type.equals("relop")) {
            if (value.equals("LE")) {
                return "<=";
            }
            else if (value.equals("GE")) {
                return ">=";
            }
            else if (value.equals("LT")) {
                return "<";
            }
            else if (value.equals("GT")) {
                return ">";
            }
            else if (value.equals("EQ")) {
                return "==";
            }
            else {
                return "!=";
            }
        }
        else { // case of ws
            return value;
        }
    }
}
