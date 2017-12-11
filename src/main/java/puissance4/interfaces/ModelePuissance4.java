package puissance4.interfaces;

public interface ModelePuissance4 {
    public void lacherPionDansColonne(int col, Pion pion) throws
            ExceptionMauvaisNumeroDeColonne,  ExceptionColonnePleine;

    public void retirerPionDeLaColonne(int col) throws ExceptionMauvaisNumeroDeColonne;

    public Contenu pionEnPosition(int lig, int col) throws
            ExceptionMauvaisNumeroDeColonne, ExceptionMauvaisNumeroDeLigne, ExceptionColonnePleine;

    public boolean colonnePleine(int col) throws ExceptionMauvaisNumeroDeColonne;

    public int nbPionsJoues();

    public void vider();

    public int nbColonnes();
    public int nbLignes();
    public int nbPionsDansColonne(int col) throws
            ExceptionMauvaisNumeroDeColonne ;
}
