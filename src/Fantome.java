import java.awt.Color;
import java.awt.Graphics;


public class Fantome {
    private int coorx;
    private int coory;
    private String nom;
    private int id;
    private Boolean bleu = false;
    private final static String NORD = "N";
    private final static String EST = "E";
    private final static String SUD = "S";
    private final static String OUEST = "O";
    private String direction = "N";
    private GrilleDeJeu grille;

    public Fantome(GrilleDeJeu grille,int id) {
        nom = ""+ this; //"Sans nom";
        this.id = id;
        this.grille = grille;
        demander();
    }

    public Fantome(String nom,GrilleDeJeu grille,int id) {
        this.nom = nom;
        this.grille = grille;
        this.id = id;
        demander();
    }

    public void demander() {
        int[] coords = grille.getCoords('F');
        switch (id) {
            case 1: 
                coords = grille.getCoords('F');
                break;
            case 2: 
                coords = grille.getCoords('G');
                break;
            case 3: 
                coords = grille.getCoords('H');
                break;
            case 4: 
                coords = grille.getCoords('K');
                break;
        }
        coorx = coords[1];
        coory = coords[0];
    }

    public void aleadir() {
        int nbrdir = (int) (Math.random()*(5-1)) + 1;
        switch (nbrdir) {
            case 1:
                direction = NORD;
                break;
            case 2:
                direction = EST;
                break;
            case 3:
                direction = OUEST;
                break;
            case 4:
                direction = SUD;
                break;

        }
    }

    public int getx() {
        return coorx;
    }
    public int gety() {
        return coory;
    }

    public void changerBleu(Boolean color) {
        bleu = color;
    }

    public void bouger() {
        // faire changer direction (le thread fait avancer + repaint ) et la direction change ou l'on voit la case du pacman
        int xfront = 0;
        int yfront = 0;
        aleadir();
        switch (direction) {
            case NORD:
                    xfront = coorx;
                    yfront = coory - 1;
                    break;
                case SUD:
                    xfront = coorx;
                    yfront = coory + 1;
                    break;
                case EST:
                    xfront = coorx + 1;
                    yfront = coory;
                    break;
                case OUEST:
                    xfront = coorx - 1;
                    yfront = coory;
                    break;
        }
        char cas = grille.getCase(xfront, yfront);
        if (cas != 'M'  && cas != '>'  && cas != '<'){
            switch (direction) {
                case NORD:
                    coory--;
                    break;
                case SUD:
                    coory++;
                    break;
                case EST:
                    coorx++;
                    break;
                case OUEST:
                    coorx--;
                    break;
            }
        }
    }


    public void dessiner(Graphics gc) {
        if (bleu == false) {
            gc.setColor(Color.PINK);
        } else {
            gc.setColor(Color.BLUE);
        }
        gc.fillOval(coorx*40, coory*40, 40, 40);
        gc.setColor(Color.WHITE);
        gc.drawString(nom, coorx*40, coory*40);
    }
}
