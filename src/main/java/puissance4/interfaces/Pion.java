package puissance4.interfaces;

public final class Pion implements  Contenu {
    public static final Pion ROUGE = new Pion("ROUGE");
    public static final Pion JAUNE = new Pion("JAUNE");
    private String couleur;
    private Pion(String couleur ) {
        this.couleur = couleur;
    }

    public String couleur() {
        return couleur;
    }

    public boolean equals(Contenu autre) {
        if (autre instanceof CaseVide) return false;
        return this.couleur == ((Pion) autre).couleur;
    }
}
