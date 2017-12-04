package exemplecode;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.service.query.impl.NodeQueryUtils.hasText;


public class TestExempleGlisserDeposer extends ApplicationTest {

    private Parent parent;

    @Override
    public void start(Stage stage) {
        Scene scene;
        parent = new ExempleGlisserDeposer.Jeu();
        scene = new Scene(parent
                , 500, 500, Color.GREY);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testChangementTexteSurBouton() {
        clickOn("#bouton");
        verifyThat("#bouton", hasText("Clicked"));

    }

    @Test
    public void testGlisserDeposerDuCercleJaune() {

        drag("#cercle-jaune").dropTo("#rectangle-bleu");


        Circle circle = (Circle) parent.lookup("#cercle-jaune");
        Rectangle rectangle = (Rectangle) parent.lookup("#rectangle-bleu");
        assertTrue(rectangle.getX() + rectangle.getHeight() / 2 == circle.getCenterX());
    }
}
