package puissance4.main;


import com.sun.xml.internal.bind.v2.TODO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import puissance4.controleur.ControleurPuissance4;
import puissance4.interfaces.*;
import puissance4.modele.Modele;
import puissance4.vue.Vue;


public class FenetrePuissance4 extends Application {
    private Vue jeu;

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) {

        // Le jeu se déroule dans une vue : un Group JavaFX
        jeu = new Vue();

        ControleurPuissance4 controleur = new ControleurPuissance4();
        controleur.setVue(jeu);
        controleur.setModele(new Modele());

        // Incorporation de la vue dans une fenetre Java FX
        Scene scene = new Scene( jeu, 500, 500, Color.GRAY);
        // On affiche la fenetre avec la scene et un titre
        primaryStage.setTitle("Puissance 4 FX ");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public VuePuissance4 getVue()
    {
        return jeu;
    }


}
