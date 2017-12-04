package puissance4.vue;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import puissance4.interfaces.Contenu;
import puissance4.interfaces.VuePuissance4;
import static puissance4.interfaces.CaseVide.CASE_VIDE;
import static puissance4.interfaces.Pion.JAUNE;

/**
 * Classe representant la vue
 */

public class Vue extends Group implements VuePuissance4 {


    private static final double HAUTEUR_CASE = 60;
    private static final double LARGEUR_DAMIER = 7 * HAUTEUR_CASE;
    private static final double HAUTEUR_DAMIER = 6 * HAUTEUR_CASE;
    private static final double X_DAMIER = (500 - LARGEUR_DAMIER) / 2;
    private static final double Y_DAMIER = (500 - HAUTEUR_DAMIER) / 2;
    private static final double DIAMETRE_PION = HAUTEUR_CASE - 10;

    private double orgSceneX;
    private Button boutonJouer;
    private Button boutonPartie;
    private Circle pionCurseur;
    private Label texteInfo;
    private int colonneJouee = 1;

    public Vue() {
        super();
        setId("damier");

        // Dessin du damier puissance 4
        //Un rectangle bleu avec des bords noirs
        Rectangle rectangle = new Rectangle(X_DAMIER, Y_DAMIER, LARGEUR_DAMIER, HAUTEUR_DAMIER);
        rectangle.setId("rectangle-bleu");
        rectangle.setFill(Color.BLUE);
        rectangle.setStroke(Color.YELLOW);
        rectangle.toBack();

        getChildren().add(rectangle);

        // Dessin des 42 trous
        for (int lig = 1; lig <= 6; lig++) {
            for (int col = 1; col <= 7; col++) {
                Circle circle = new Circle(xPionEnColonne(col), yPionEnLigne(lig),
                        DIAMETRE_PION / 2, Color.GRAY);
                //circle.setStroke( Color.BLACK);
                circle.setId("trou-" + lig + "-" + col);
                getChildren().add(circle);

            }
        }

        // Mise en place du Pion curseur
        pionCurseur = new Circle(xPionEnColonne(1), Y_DAMIER - HAUTEUR_CASE / 2,
                DIAMETRE_PION / 2, Color.YELLOW);
        pionCurseur.setId("pion-curseur");
        getChildren().add(pionCurseur);

        pionCurseur.setOnMousePressed((t) -> {
            pionCurseur.setCursor(Cursor.HAND);
            orgSceneX = t.getSceneX();
            Circle c = (Circle) (t.getSource());
            c.toFront();
        });

        // Déplacement du cercle en fonction du déplacement de la souris en mode dragged
        pionCurseur.setOnMouseDragged((t) -> {
            double offsetX = t.getSceneX() - orgSceneX;
            Circle c = (Circle) (t.getSource());
            double newCenterX = c.getCenterX() + offsetX;
            if (newCenterX >= X_DAMIER && newCenterX < X_DAMIER + LARGEUR_DAMIER) {
                c.setCenterX(newCenterX);
            }
            // Ne pas oublier de fixer la nouvelle position en X
            orgSceneX = t.getSceneX();
            t.consume();
        });


        pionCurseur.setOnMouseReleased((t) -> {
                    double x = t.getSceneX();
                    colonneJouee = colAbscisseX(x);
                    pionCurseur.setCenterX(xPionEnColonne( colonneJouee ));
                    t.consume();
                    pionCurseur.setCursor(Cursor.DEFAULT);
                }
        );

        //Bouton jouer
        boutonJouer = new Button("Jouer le coup");
        boutonJouer.setId("btn-jouer-coup");
        boutonJouer.setLayoutX( X_DAMIER );
        boutonJouer.setLayoutY( Y_DAMIER+HAUTEUR_DAMIER+20);
        getChildren().add( boutonJouer );

        //Bouton nouvelle partie
        boutonPartie = new Button("Nouvelle partie");
        boutonPartie.setId("btn-nouvelle-partie");
        boutonPartie.setLayoutX( X_DAMIER + 100);
        boutonPartie.setLayoutY( Y_DAMIER+HAUTEUR_DAMIER+20);
        getChildren().add( boutonPartie );

        //zone d'information
        texteInfo = new Label();
        texteInfo.setId("texte-info");
        texteInfo.setLayoutX( X_DAMIER );
        texteInfo.setLayoutY( Y_DAMIER+HAUTEUR_DAMIER+50);
        getChildren().add( texteInfo );
    }

    public void informe(String texte) {
        texteInfo.setText(texte);
    }

    /**
     * Permet de relier les cliques sur les boutons de la vue au controleur
     * @param eventHandler
     */
    public void ajouterEcouteurBoutons( EventHandler eventHandler)
    {
        boutonJouer.setOnAction( eventHandler );
        boutonPartie.setOnAction( eventHandler);
    }

    /**
     *  Permet de replacer le pion curseur sur la premiere colonne
     */
    @Override
    public void nouveauCoup() {
        pionCurseur.setCenterX( xPionEnColonne(1));
        if (pionCurseur.getFill() == Color.YELLOW)
            pionCurseur.setFill(Color.RED);
        else
            pionCurseur.setFill(Color.YELLOW);
        colonneJouee = 1;
    }

    /**
     * La colonne pointee par le pion curseur
     * @return la colonne pointee
     */
    public int colonneJouee() {
        return colonneJouee;
    }

    /**
     * Permet d'avoir l'ordonnee y du centre du cercle representant les cases
     * de la ligne lig
     * @param lig
     * @return
     */
    protected static double yPionEnLigne(int lig) {
        return Y_DAMIER + (5 - lig + 1) * HAUTEUR_CASE + HAUTEUR_CASE / 2;
    }

    /**
     * Permet d'avoir l'abscisse x du centre du cercle representant les cases
     * de la colonne col
     * @param col
     * @return
     */
    protected static double xPionEnColonne(int col) {
        return X_DAMIER + (col - 1) * HAUTEUR_CASE + HAUTEUR_CASE / 2;
    }

    /**
     * Fonction inverse permettant de retrouver le numero de colonne contenant
     * un point d'abscisse x
     * @param x
     * @return
     */
    protected static int colAbscisseX(double x) {
        int col;
        if (x < X_DAMIER) {
            col = 1;
        } else if (x > X_DAMIER + LARGEUR_DAMIER) {
            col = 7;
        } else {
            col = (int) (1 + ((x - X_DAMIER) / HAUTEUR_CASE));
        }
        return col;
    }

    /**
     * Permet de mettre à jour le contenu de la case de coordonnees lig, col sur la vue
     * en fonction du modele qu'il y ait un pion ou une case vide dans le modele.
     * @param lig
     * @param col
     * @param contenu
     */
    public void dessineContenu( int lig , int col , Contenu contenu) {
        // Existe-t-il un pion en coordonnee lig col ?
        Node pion = this.getScene().lookup("#pion-"+lig+"-"+col);
        if ( contenu != CASE_VIDE ) {
            // On ajoute le pion que si il n est pas deja represente
            if (  pion == null ) {
                Circle circle = new Circle(xPionEnColonne(col), yPionEnLigne(lig),
                        DIAMETRE_PION / 2, contenu == JAUNE ? Color.YELLOW : Color.RED);
                circle.setId("pion-" + lig + "-" + col);
                getChildren().add(circle);
            }
        }
        else
        {   // Si case vide sur modele et pion sur vue : retirer le pion
            if ( pion != null ) {
                getChildren().remove( pion );
            }
        }

    }

    public void desactiverJouerCoup(){
        boutonJouer.setDisable(true);
    }

    public void activerJouerCoup(){
        boutonJouer.setDisable(false);
    }

}
