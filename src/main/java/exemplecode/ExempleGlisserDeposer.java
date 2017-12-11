package exemplecode;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


// snipset : exemple de code utile pour le projet
// Demo : JavaFX + Dessin + Glisser Deposer


public class ExempleGlisserDeposer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    // Les coordonnées du cercle au début du drag & drop

    @Override
    public void start(Stage primaryStage) {

        // La scene contient le groupe racine
        Scene scene = new Scene(new Jeu(), 500, 500, Color.GRAY);


        // On affiche la fenetre avec la scene et un titre
        primaryStage.setTitle("Drag & Drop Snipset Olivier CHARLES");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        /* On montre ici que l'on recupere un objet par son id avec # devant
         ........ interessant pour les tests IHM automatiques */

        System.out.println(scene.lookup("#cercle-jaune").getClass().toString());
    }

    // Class interne statique réutilisable pour les tests fonctionnels avec TestFX
    public static class Jeu extends Group {
        private double orgSceneX;
        private double orgSceneY;

        public Jeu() {
            super();
            setId("damier");

            //Un cercle jaune avec un id="cercle-jaune"
            Circle circle = new Circle(150, 150, 30, Color.YELLOW);
            circle.setId("cercle-jaune");

            //Un rectangle bleu avec des bords noirs
            Rectangle rectangle = new Rectangle(200, 200, 62, 62);
            rectangle.setId("rectangle-bleu");
            rectangle.setFill(Color.BLUE);
            rectangle.setStroke(Color.BLACK);

            //Un bouton
            Button bouton = new Button("Un bouton");
            bouton.setId("bouton");
            bouton.setLayoutX(20);
            bouton.setLayoutY(20);
            DropShadow shadow = new DropShadow();
            //Ajout de l'ombre en entrant sur le bouton
            bouton.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            bouton.setEffect(shadow);
                        }
                    });
            //Retrait de l'ombre en sortant du bouton
            bouton.addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            bouton.setEffect(null);
                        }
                    });

            // Ajout d'une action sur click souris
            bouton.setOnAction(actionEvent -> bouton.setText("Clicked"));

            // Récupération des coordonnées d'origines lors du clique sur le cercle
            // Un évènement traité avec un lambda. Ici représente un Evenement javaFX correctefff

            circle.setOnMousePressed((t) -> {
                circle.setCursor(Cursor.HAND);
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                Circle c = (Circle) (t.getSource());
                c.toFront();
            });

            // Déplacement du cercle en fonction du déplacement de la souris en mode dragged
            circle.setOnMouseDragged((t) -> {
                double offsetX = t.getSceneX() - orgSceneX;
                double offsetY = t.getSceneY() - orgSceneY;
                Circle c = (Circle) (t.getSource());
                c.setCenterX(c.getCenterX() + offsetX);
                c.setCenterY(c.getCenterY() + offsetY);
                // Ne pas oublier de fixer les nouvelles origines
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                t.consume();
            });

            // Si on lache la souris alors si sa position est dans le rectangle, on centre le cercle dans le rectangle
            circle.setOnMouseReleased((t) -> {
                        if (rectangle.contains(t.getSceneX(), t.getSceneY())) {
                            circle.setCenterX(rectangle.getX() + rectangle.getWidth() / 2);
                            circle.setCenterY(rectangle.getY() + rectangle.getHeight() / 2);
                        }
                        t.consume();
                        circle.setCursor(Cursor.DEFAULT);
                    }

            );

            // On ajoute le cercle et le rectangle dans le groupe racine
            getChildren().addAll(circle, rectangle, bouton);

        }
    }
}
