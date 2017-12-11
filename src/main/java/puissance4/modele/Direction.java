package puissance4.modele;

public class Direction {
    private int deltaL;
    private int deltaC;
    public static final Direction HAUT = new Direction(1, 0);
    public static final Direction DROITE = new Direction(0, 1);
    public static final Direction HAUTGAUCHE = new Direction(1, -1);
    public static final Direction HAUTDROITE = new Direction(1, 1);
    public static final Direction directions[] = {HAUT, DROITE, HAUTGAUCHE, HAUTDROITE };

    public Direction(int dL, int dC) {
        this.deltaL = dL;
        this.deltaC = dC;

    }

    public int getDeltaL() {
        return deltaL;
    }

    public int getDeltaC() {
        return deltaC;
    }
    public int getOpposeDeltaL() {
        return -deltaL;
    }

    public int getOpposeDeltaC() {
        return -deltaC;
    }


}
