import java.awt.Color;
import java.awt.Graphics;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class PacMan {
    private int coorx;
    private int coory;
    private final static String NORD = "N";
    private final static String EST = "E";
    private final static String SUD = "S";
    private final static String OUEST = "O";
    private String direction = "N";
    private GrilleDeJeu grille;
    private Score score;
    private Fantome f1;
    private Fantome f2;
    private Fantome f3;
    private Fantome f4;
    // super pouvoir
    private LocalTime current;
    private Duration duree;
    private Boolean pouvoir = false;
    private LocalTime chrono;
    
    public PacMan(GrilleDeJeu grille,Score score,Fantome f1,Fantome f2,Fantome f3,Fantome f4) {
        this.grille = grille;
        this.score = score;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        demander();
    }

    public void demander() {
        int[] coords = grille.getCoords('J');
        coorx = coords[1];
        coory = coords[0];
    }

    public void tp(int[] tab) {
        coorx = tab[1];
        coory = tab[0];
    }

    public int getx() {
        return coorx;
    }

    public int gety() {
        return coory;
    }


    public void changedir(String lettre) {
        int xfront = 0;
        int yfront = 0;
        char cas;
        switch (lettre) {
            case "N":
            xfront = getx();
            yfront = gety() - 1;
            cas = grille.getCase(xfront, yfront);
            if (cas != 'M' && cas != 'F' && cas != 'G' && cas != 'H' && cas != 'K') {
                direction = NORD;
            }
            break;
            case "S":
            xfront = getx();
            yfront = gety() + 1;
            cas = grille.getCase(xfront, yfront);
            if (cas != 'M' && cas != 'F' && cas != 'G' && cas != 'H' && cas != 'K') {
                direction = SUD;
            }
            break;
            case "E":
            xfront = getx() + 1;
            yfront = gety();
            cas = grille.getCase(xfront, yfront);
            if (cas != 'M' && cas != 'F' && cas != 'G' && cas != 'H' && cas != 'K') {
                direction = EST;
            }
            break;
            case "O":
            xfront = getx() - 1;
            yfront = gety();
            cas = grille.getCase(xfront, yfront);
            if (cas != 'M' && cas != 'F' && cas != 'G' && cas != 'H' && cas != 'K') {
                direction = OUEST;
            }
            break;
        }
    }

    public void bouger() {
        // faire changer direction (le thread fait avancer + repaint ) et la direction change ou l'on voit la case du pacman
        int xfront = 0;
        int yfront = 0;
        boolean fantome = false;
        int fantomex = 0;
        int fantomey = 0;
        Fantome fantomedevant = f1;
        switch (direction) {
            case NORD:
                    xfront = getx();
                    yfront = gety() - 1;
                    break;
                case SUD:
                    xfront = getx();
                    yfront = gety() + 1;
                    break;
                case EST:
                    xfront = getx() + 1;
                    yfront = gety();
                    break;
                case OUEST:
                    xfront = getx() - 1;
                    yfront = gety();
                    break;
        }
        char cas = grille.getCase(xfront, yfront);
        // verifier si un fantome ne se situe pas sur la futur case du pacman
        for (int i=0;i<4;i++) {
            switch(i) {
                case 0: 
                fantomex = f1.getx();
                fantomey = f1.gety();
                break;
                case 1: 
                fantomex = f2.getx();
                fantomey = f2.gety();
                break;
                case 2: 
                fantomex = f3.getx();
                fantomey = f3.gety();
                break;
                case 3: 
                fantomex = f4.getx();
                fantomey = f4.gety();
                break;
            }
            if (fantomex == xfront && fantomey == yfront || fantomex == getx() && fantomey == gety()) {
                fantome = true;
                switch (i) {
                    case 0:
                    fantomedevant = f1;
                    break;
                    case 1:
                    fantomedevant = f2;
                    break;
                    case 2:
                    fantomedevant = f3;
                    break;
                    case 3:
                    fantomedevant = f4;
                    break;
                }
            }
        }
        majscore(cas,xfront,yfront);
        // si le superpouvoir est actif
        if (pouvoir == true) {
            current = LocalTime.now();
            duree = Duration.between(chrono,current);
            if (duree.get(ChronoUnit.SECONDS)>3) {
                f1.changerBleu(false);
                f2.changerBleu(false);
                f3.changerBleu(false);
                f4.changerBleu(false);
                pouvoir = false;
            }
        }
        // verifier si une case n'est pas un mur ou un point de spawn
        if (cas != 'M' && cas != 'F' && cas != 'G' && cas != 'H' && cas != 'K'){
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
            // si un fantome est sur la case du pacman
            if (fantome == true) {
                if (pouvoir == true) {
                    score.majscore(250);
                    fantomedevant.demander();
                } else {
                    score.majvie(1);
                    respawn();
                    fantome = false;
                }
                
            }
            
        }
    }

    public void respawn() {
        demander();
        f1.demander();
        f2.demander();
        f3.demander();
        f4.demander();
        grille.reset();
    }

    public void dessiner(Graphics gc) {
        gc.setColor(Color.YELLOW);
        gc.fillOval(coorx*40, coory*40, 40, 40);
    }

    public void superPouvoir() {
        pouvoir = true;
        chrono = LocalTime.now();
        f1.changerBleu(true);
        f2.changerBleu(true);
        f3.changerBleu(true);
        f4.changerBleu(true);
    }

    public void majscore(char bloc,int x,int y) {
        /* pacpomme */
        switch (bloc) {
            case 'P':
            /* pacpomme */
            score.majscore(10);
            grille.changeCase('.', y, x);
            break;
            case 'S':
            /* Super pacpomme */
            score.majscore(50);
            grille.changeCase('.', y, x);
            superPouvoir(); 
            break;
            case 'R':
            /* Fraise */
            score.majscore(300);
            grille.changeCase('.', y, x);
            break;
            case '>':
            /* Tp 1 */
            int[] coords = grille.getCoords('<');
            tp(coords);
            break;
            case '<':
            /* Tp 2 */
            int[] coordss = grille.getCoords('>');
            tp(coordss);
            break;
        }
        if (grille.getfinniveau() == true) {
            grille.finniveau();
            respawn();
        }
    }
}
