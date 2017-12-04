package puissance4.controleur;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import puissance4.interfaces.*;
import puissance4.modele.ReglesDuJeu;

import static puissance4.interfaces.Pion.JAUNE;
import static puissance4.interfaces.Pion.ROUGE;

/**
 *  Classe controleur
 *  METHODE1 :  couche de separation entre la vue et le modele
 *  Utilisation d'interfaces ModelePuissance4 et VuePuissance4
 */
public class ControleurPuissance4 implements EventHandler {

    private ModelePuissance4 modele;
    private VuePuissance4 vue;
    private Pion pionActif = JAUNE;
    private ReglesDuJeu regles;

    /**
     * Permet d'avoir le modele sous forme d'interface
     * @return modele
     */
    public ModelePuissance4 getModele() {
        return modele;
    }

    /**
     * Permet de connecter le modele au controleur grace à une interface
     * @param modele
     */
    public void setModele(ModelePuissance4 modele){
        this.modele = modele;
        this.regles= new ReglesDuJeu(modele);
    }

    /**
     * Permet d'avoir la vue connectee sous forme d'interface
     * @return vue
     */
    public VuePuissance4 getVue() {
        return vue;
    }

    /**
     * Permet de connecter la vue au controleur grace à une interface
     * @param vue
     */
    public void setVue(VuePuissance4 vue) {
        this.vue = vue;
        vue.ajouterEcouteurBoutons(this);
    }

    /**
     * Permet de dessiner la representation complete du modele sur la vue
     *
     * @throws ExceptionMauvaisNumeroDeColonne
     */
    public void dessinerModeleSurVue() throws ExceptionColonnePleine {
        for( int col=1; col<= modele.nbColonnes(); col++) {
            for( int lig=1; lig<= modele.nbLignes(); lig++) {
                try {
                    vue.dessineContenu(lig, col, modele.pionEnPosition(lig, col));
                } catch(ExceptionMauvaisNumeroDeLigne exLig) {
                    System.err.println(exLig);
                } catch (ExceptionMauvaisNumeroDeColonne exCol) {
                    System.err.println(exCol);
                }
            }
        }
    }

    /**
     * Permet de controler les evenements en provenance de la vue
     * @param event
     */
    @Override
    public void handle(Event event) {
        // Controle de la validité du coup
        if ( ((Button)event.getSource()).getText().equals("Jouer le coup"))
            controleEtJoueCoup();
        if ( ((Button)event.getSource()).getText().equals("Nouvelle partie"))
            nouvellePartie();

    }

    /**
     * Permet vider le modele puis de le redessiner sur la vue
     */
    private void nouvellePartie() {
        modele.vider();
        vue.activerJouerCoup();
        vue.informe("");
        try {
            dessinerModeleSurVue();
        } catch (ExceptionColonnePleine exceptionColonnePleine) {
            exceptionColonnePleine.printStackTrace();
        }
//        vue.nouveauCoup();
    }

    /**
     *  Permet de jouer le coup dans la colonne pointee par le pion a jouer
     */
    private void controleEtJoueCoup(){
        // recuperation de la colonne jouee
        int colJouee = vue.colonneJouee();
        vue.informe("");
        // lacher du pion actif
        try {
            modele.lacherPionDansColonne(colJouee, pionActif);
            dessinerModeleSurVue();
            //le coup est_il gagnant?
            if(regles.coupGagnant(colJouee)){
                vue.desactiverJouerCoup();
                vue.informe("Partie gagnée par la couleur: "+pionActif.couleur());
            }
            else{
                if(regles.egalite()){
                    vue.desactiverJouerCoup();
                    vue.informe("Partie nulle");
                }
            }

            // preparer un nouveau coup
            if (pionActif == JAUNE) {
                pionActif = ROUGE;
            } else {
                pionActif = JAUNE;
            }

            vue.nouveauCoup();
            // traiter le cas colonne pleine
        } catch (ExceptionMauvaisNumeroDeColonne exNumero) {
            vue.informe("Mauvais choix de colonne (" + colJouee + ") !");
            //exNumero.printStackTrace();
        } catch (ExceptionColonnePleine exPleine) {
            vue.informe("Colonne " + colJouee + " pleine !");
            //exPleine.printStackTrace();
        }

        catch (ExceptionMauvaisNumeroDeLigne exLigne) {

        }



    }


}
