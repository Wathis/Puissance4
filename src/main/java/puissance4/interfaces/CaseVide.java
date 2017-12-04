package puissance4.interfaces;

public class CaseVide implements Contenu {
    public static final CaseVide CASE_VIDE = new CaseVide();
    private CaseVide() {};

    public boolean equals(Contenu autre) {
        return autre instanceof CaseVide;
    }
}
