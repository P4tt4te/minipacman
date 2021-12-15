import java.awt.Color;
import java.awt.Graphics;
import java.io.*;


public class GrilleDeJeu {
    private char[][] grille = new char[20][20];
    private int nbrpommes;
    private boolean finniveau = false;
    private int numniveau = 1;
    private Score score;

    public GrilleDeJeu(Score score) {
        charger(numniveau);
        this.score = score;
    }

    public void charger(int numniveau) {
        try {
            finniveau = false;
            String p = new File("").getAbsolutePath();
            p = p.concat("/src/niveau/"+numniveau+".txt");
            BufferedReader br = new BufferedReader(new FileReader(p)); // ouverture
            String s;
            int i = 0;
            while ((s = br.readLine()) != null) {
                grille[i] = s.toCharArray();
                i++;       
            }
            br.close();
            }
            catch (IOException erreur) { // Gestion des exceptions
            System.out.println("TexteIO.main (IOException) : " + erreur);
            }
            
    }

    public void changeCase(char newbloc,int y,int x) {
        grille[y][x] = newbloc;
    }

    public char getCase(int x,int y) {
        return grille[y][x];
    }

    public void reset() {
        charger(numniveau);
    }

    public int[] getCoords(char lettre) {
        int[] tab = new int[2];
        for(int y = 0; y < 20; y++){
            for(int x = 0; x < 20; x++){
                if (grille[x][y] == lettre){
                    tab[0] = x;
                    tab[1] = y;
                }
            }
        }
        return tab;
    }

    public void finniveau() {
        numniveau++;
        charger(numniveau);
        score.majnumniveau(numniveau);
    }

    public boolean getfinniveau() {
        return finniveau;
    }

    public void dessiner(Graphics gc){
        nbrpommes = 0;
        for (int i = 0; i < 20; i++) {
            for (int y = 0; y < 20; y++) {
                switch(grille[y][i]) {
                    // mur
                    case 'M': 
                        gc.setColor(Color.BLUE);
                        gc.fillRect(i * 40, y * 40, 40, 40);
                        break;
                    // vide
                    case '.': 
                        gc.setColor(Color.BLACK);
                        gc.fillRect(i * 40, y * 40, 40, 40);
                        break;
                    // spawn fantomes
                    case 'F':
                    case 'G':
                    case 'H':
                    case 'K': 
                        gc.setColor(Color.ORANGE);
                        gc.fillRect(i * 40, y * 40, 40, 40);
                        break;
                    // pac-pommes
                    case 'P': 
                        gc.setColor(Color.BLACK);
                        gc.fillRect(i * 40, y * 40, 40, 40);
                        gc.setColor(Color.YELLOW);
                        gc.fillOval(i*40 + 10, y*40 + 10, 20, 20);
                        nbrpommes++;
                        break;
                    // super pac-pommes
                    case 'S': 
                        gc.setColor(Color.BLACK);
                        gc.fillRect(i * 40, y * 40, 40, 40);
                        gc.setColor(Color.YELLOW);
                        gc.fillOval(i*40 + 5, y*40 + 5, 30, 30);
                        nbrpommes++;
                        break;
                    // fraises
                    case 'R':
                        gc.setColor(Color.BLACK);
                        gc.fillRect(i * 40, y * 40, 40, 40);
                        gc.setColor(Color.RED);
                        gc.fillOval(i*40 + 5, y*40 + 5, 30, 30);
                        gc.setColor(Color.WHITE);
                        gc.drawString("Fraise", i*40 + 1, y*40 + 1);
                        
                        break;
                    // joueur
                    case 'J':
                        gc.setColor(Color.BLACK);
                        gc.fillRect(i*40,y*40,40,40);
                        break;
                    // téléportation
                    case '<':
                        gc.setColor(Color.BLACK);
                        gc.fillRect(i*40, y*40, 40, 40);
                        gc.setColor(Color.WHITE);
                        gc.drawString("<", i*40 + 20, y*40 + 25);
                        break;
                    case '>':
                        gc.setColor(Color.BLACK);
                        gc.fillRect(i*40, y*40, 40, 40);
                        gc.setColor(Color.WHITE);
                        gc.drawString(">", i*40 + 20, y*40 + 25);
                        break;
                }
            }
        }
        if (nbrpommes == 0) {
            finniveau = true;
        }
   }    


}
