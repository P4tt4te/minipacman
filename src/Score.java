
import java.awt.Graphics;



public class Score{
    private int score;
    private int vie;
    private int numniveau;

    public Score() {
        vie = 3;
        score = 0;
        numniveau = 1;
    }

    public void majscore(int nombre) {
        score = score + nombre;
        if (score > 10000) {
            majvie(1);
        }
    }

    public void majnumniveau(int nombre) {
        numniveau = nombre;
        if (numniveau == 14) {
            System.exit(1);
        }
    }

    public int getscore() {
        return score;
    }

    public int getvie() {
        return vie;
    }

    public void majvie (int nombre){
        vie = vie - nombre;
        if (vie == 0) {
            System.exit(1);
        }
    }

    public void dessiner(Graphics gc) {
        gc.drawString("Score : "+score+" | Vie restantes : "+vie+" | Niveau : "+numniveau, 5, 20);
        
    }


}
