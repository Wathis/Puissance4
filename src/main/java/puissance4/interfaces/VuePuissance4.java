package puissance4.interfaces;

import javafx.event.EventHandler;

public interface VuePuissance4 {
    public void dessineContenu( int lig , int col , Contenu contenu);
    public int colonneJouee();
    public void ajouterEcouteurBoutons( EventHandler eventHandler);
    public void nouveauCoup();
    public void informe(String texte);
    public void desactiverJouerCoup();
    public void activerJouerCoup();
}
