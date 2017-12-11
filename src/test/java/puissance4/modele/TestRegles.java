package puissance4.modele;

import org.junit.Before;
import org.junit.Test;
import puissance4.interfaces.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static puissance4.interfaces.CaseVide.CASE_VIDE;
import static puissance4.interfaces.Pion.JAUNE;
import static puissance4.interfaces.Pion.ROUGE;

public class TestRegles {


    private Modele modele;
    private ReglesDuJeu regles;

    @Before
    public void creerModele() {
        modele = new Modele();
        regles = new ReglesDuJeu(modele);
    }

    @Test
    public void testCoupGagnantVertical() throws
            ExceptionMauvaisNumeroDeColonne,
            ExceptionMauvaisNumeroDeLigne, ExceptionColonnePleine {

        modele.lacherPionDansColonne(1, ROUGE);
        assertFalse(regles.coupGagnant(1));
        modele.lacherPionDansColonne(1, ROUGE);
        assertFalse(regles.coupGagnant(1));
        modele.lacherPionDansColonne(1, ROUGE);
        assertFalse(regles.coupGagnant(1));
        modele.lacherPionDansColonne(1, ROUGE);
        assertTrue(regles.coupGagnant(1));
    }

    @Test
    public void testCoupGagnantHorizontal() throws
            ExceptionMauvaisNumeroDeColonne,
            ExceptionMauvaisNumeroDeLigne, ExceptionColonnePleine {
        modele.lacherPionDansColonne(2, ROUGE);
        assertFalse(regles.coupGagnant(2));
        modele.lacherPionDansColonne(3, ROUGE);
        assertFalse(regles.coupGagnant(3));
        modele.lacherPionDansColonne(4, ROUGE);
        assertFalse(regles.coupGagnant(4));
        modele.lacherPionDansColonne(5, ROUGE);
        assertTrue(regles.coupGagnant(5));
    }

    @Test
    public void testCoupGagnantDiagonale() throws
            ExceptionMauvaisNumeroDeColonne,
            ExceptionMauvaisNumeroDeLigne, ExceptionColonnePleine {
        modele.lacherPionDansColonne(2, ROUGE);
        assertFalse(regles.coupGagnant(2));

        modele.lacherPionDansColonne(3, JAUNE);
        modele.lacherPionDansColonne(3, ROUGE);
        assertFalse(regles.coupGagnant(3));

        modele.lacherPionDansColonne(4, JAUNE);
        modele.lacherPionDansColonne(4, JAUNE);
        modele.lacherPionDansColonne(4, ROUGE);
        assertFalse(regles.coupGagnant(4));

        modele.lacherPionDansColonne(5, JAUNE);
        modele.lacherPionDansColonne(5, JAUNE);
        modele.lacherPionDansColonne(5, JAUNE);
        modele.lacherPionDansColonne(5, ROUGE);
        assertTrue(regles.coupGagnant(5));
    }
}
