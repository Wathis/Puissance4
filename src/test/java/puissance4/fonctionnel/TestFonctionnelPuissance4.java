package puissance4.fonctionnel;

import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.paint.Color;
        import javafx.scene.shape.Circle;
        import javafx.scene.shape.Rectangle;
        import javafx.stage.Stage;

        import org.junit.*;
        import org.testfx.framework.junit.ApplicationTest;
        import static org.junit.Assert.*;
        import static org.testfx.api.FxAssert.verifyThat;
        import static org.testfx.service.query.impl.NodeQueryUtils.hasText;
        import org.junit.FixMethodOrder;
        import org.junit.runners.MethodSorters;
        import puissance4.controleur.ControleurPuissance4;
        import puissance4.modele.Modele;
        import puissance4.vue.Vue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestFonctionnelPuissance4 extends ApplicationTest {
    private Vue vue;
    private Scene scene;

    @BeforeClass
    public static void miseEnPlace() {
    }

    @Override
    public void start(Stage stage) {
        vue = new Vue();
        ControleurPuissance4 controleur = new ControleurPuissance4();
        controleur.setVue(vue);
        controleur.setModele(new Modele());
        scene = new Scene(vue, 500, 500, Color.GRAY);
        stage.setTitle("Puissance 4 FX");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Test
    public void test01Lacher2PionsColonne1() {
        assertEquals(Color.YELLOW, ((Circle)vue.lookup("#pion-curseur")).getFill());
        clickOn("#btn-jouer-coup");
        assertEquals(Color.YELLOW, ((Circle)vue.lookup("#pion-1-1")).getFill());
        assertEquals(Color.RED, ((Circle)vue.lookup("#pion-curseur")).getFill());
        clickOn("#btn-jouer-coup");
        assertEquals(Color.RED, ((Circle)vue.lookup("#pion-2-1")).getFill());
    }

    @Test
    public void test02Lacher1PionColonne4() {
        assertEquals(Color.YELLOW, ((Circle)vue.lookup("#pion-curseur")).getFill());
        drag("#pion-curseur").dropTo("#trou-6-4");
        clickOn("#btn-jouer-coup");
        assertEquals(Color.YELLOW, ((Circle)vue.lookup("#pion-1-4")).getFill());
        assertEquals(Color.RED, ((Circle)vue.lookup("#pion-curseur")).getFill());
    }

    @Test
    public void test03DeborderColonne() {
        for (int i = 1; i <= 7; i++) {
            drag("#pion-curseur").dropTo("#trou-6-7");
            clickOn("#btn-jouer-coup");
        }
        verifyThat("#texte-info", hasText("Colonne 7 pleine !"));
    }

    @Test
    public void test04NouvellePartie() {
        clickOn("#btn-nouvelle-partie");
        for(int col = 1; col <= 7; col++) {
            assertEquals(Color.GRAY, ((Circle)vue.lookup("#trou-1-" + col)).getFill());
        }
        assertEquals(Color.YELLOW, ((Circle)vue.lookup("#pion-curseur")).getFill());
    }
    @Test
    public void test04Victoire(){
        drag("#pion-curseur").dropTo("#trou-6-1");
        clickOn("#btn-jouer-coup");
        drag("#pion-curseur").dropTo("#trou-6-2");
        clickOn("#btn-jouer-coup");
        drag("#pion-curseur").dropTo("#trou-6-1");
        clickOn("#btn-jouer-coup");
        drag("#pion-curseur").dropTo("#trou-6-2");
        clickOn("#btn-jouer-coup");
        drag("#pion-curseur").dropTo("#trou-6-1");
        clickOn("#btn-jouer-coup");
        drag("#pion-curseur").dropTo("#trou-6-2");
        clickOn("#btn-jouer-coup");
        drag("#pion-curseur").dropTo("#trou-6-1");
        clickOn("#btn-jouer-coup");
        verifyThat("#texte-info", hasText("Partie gagnée par la couleur: JAUNE"));
    }

    @Test
    public void tet06Egalite() {
        clickOn("#btn-nouvelle-partie");
        for (int lig = 1 ; lig <= 3; lig++) {
            for (int col = 1 ; col <= 6 ; col++) {
                drag("#pion-curseur").dropTo("#trou-6-" + col);
                clickOn("#btn-jouer-coup");
            }
        }
        drag("#pion-curseur").dropTo("#trou-6-7");
        clickOn("#btn-jouer-coup");
        for (int lig = 4 ; lig <= 6 ; lig++) {
            for (int col = 1 ; col <= 6 ; col++) {
                drag("#pion-curseur").dropTo("#trou-6-" + col);
                clickOn("#btn-jouer-coup");
            }
        }
        for (int lig = 2 ; lig <= 6 ; lig++) {
            drag("#pion-curseur").dropTo("#trou-6-7");
            clickOn("#btn-jouer-coup");
        }
        verifyThat("#texte-info",hasText("Partie nulle"));
    }
}
