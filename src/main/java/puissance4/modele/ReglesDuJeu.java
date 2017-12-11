package puissance4.modele;

import puissance4.interfaces.*;

public class ReglesDuJeu {
    private ModelePuissance4 modele;
    public ReglesDuJeu(ModelePuissance4 modele) {
        this.modele = modele;
    }
    public boolean colonneAutorisee(int colonne) throws ExceptionMauvaisNumeroDeColonne {
        return !(modele.colonnePleine(colonne));
    }
    public boolean coupGagnant(int colonne) throws ExceptionMauvaisNumeroDeColonne, ExceptionMauvaisNumeroDeLigne, ExceptionColonnePleine {
        int ligne = modele.nbPionsDansColonne(colonne);
        Contenu joue = modele.pionEnPosition(ligne, colonne);
        for(int d=0;d < Direction.directions.length;d++){
            int nbPionsMemeCoul =1+cherche(ligne, colonne, Direction.directions[d].getDeltaL(),Direction.directions[d].getDeltaC(), joue)
                                  +cherche(ligne, colonne, Direction.directions[d].getOpposeDeltaL(),Direction.directions[d].getOpposeDeltaC(), joue);
            if(nbPionsMemeCoul>=4)return true;
        }
        return false;
    }

    private int cherche(int l, int c, int dL, int dC, Contenu pion) throws ExceptionMauvaisNumeroDeLigne,ExceptionMauvaisNumeroDeColonne, ExceptionColonnePleine {
        l+=dL;
        c+=dC;
        if(l<1)return 0;
        if(c<1)return 0;
        if(l>6)return 0;
        if(c>7)return 0;
        Contenu voisin = modele.pionEnPosition(l, c);
        if(!pion.equals(voisin))return 0;
        return 1+cherche(l,c,dL,dC,pion);

    }

    public boolean egalite(){
        try {
            for (int col = 1; col <= modele.nbColonnes(); col++) {
                if (modele.pionEnPosition(modele.nbLignes(), col) instanceof CaseVide) return false;
            }
        }catch(Exception e){}


        return true; //suppose qu'on ait appelé la méthode coupGagnant() avant
    }
}
