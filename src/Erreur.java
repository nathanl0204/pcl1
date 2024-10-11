
public class Erreur { 
    private String message;
    private int ligne;
    private int colonne;

    public Erreur(String message,int ligne, int colonne){
        this.message = message;
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public String get(){
        return "Erreur : "+this.message+" aux coordonnées ("+this.ligne+","+this.colonne+")";
    }
}