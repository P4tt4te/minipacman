import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ZoneDeJeu extends JPanel implements Runnable{
    private GrilleDeJeu grille;
    private PacMan joueur;
    private Fantome speedy;
    private Fantome shadow;
    private Fantome bashful;
    private Fantome pokey;
    private int nbfantome;
    private Score score;

    public ZoneDeJeu() {
        score = new Score();
        grille = new GrilleDeJeu(score);
        nbfantome = 1;
        speedy = new Fantome("Speedy",grille,nbfantome);
        nbfantome++;
        shadow = new Fantome("Shadow",grille,nbfantome);
        nbfantome++;
        bashful = new Fantome("Bashful",grille,nbfantome);
        nbfantome++;
        pokey = new Fantome("Pokey",grille,nbfantome);
        nbfantome++;
        joueur = new PacMan(grille,score,speedy,shadow,bashful,pokey);
        

        setPreferredSize(new Dimension(800,800));
        Thread processus = new Thread(this);
        processus.start();
    }


    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        grille.dessiner(g);
        joueur.dessiner(g);
        speedy.dessiner(g);
        bashful.dessiner(g);
        shadow.dessiner(g);
        pokey.dessiner(g);
        g.setColor(Color.WHITE);
        score.dessiner(g);
    }

    public void changeDirection(String direction) {
        
        switch (direction) {
            case "N":
                joueur.changedir("N");
                break;
            case "S":
                joueur.changedir("S");
                break;
            case "O":
                joueur.changedir("O");
                break;
            case "E":
                joueur.changedir("E");
                break;
                   
        }
    }

    @Override
    public void run() {
        while (true) {
            joueur.bouger();
            speedy.bouger();
            bashful.bouger();
            shadow.bouger();
            pokey.bouger();
            // bouger doit être sans aucun argument
            repaint();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
}
