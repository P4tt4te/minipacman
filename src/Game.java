import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;



public class Game extends JFrame implements KeyListener{
    private ZoneDeJeu terrain;


    public Game() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(820,840);
        terrain = new ZoneDeJeu();
        addKeyListener(this);
        getContentPane().add(terrain);
        setTitle("PacMan");
        setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent evt) {

        // Récupérer la touche utilisée
    
       int touche = evt.getKeyCode();
    
        // Comparer la touche avec les codes qui nous intéressent, pour cela utiliser les constantes de la classe KeyEvent
    
       switch (touche) {
    
          case KeyEvent.VK_UP : terrain.changeDirection("N"); break;
    
          case KeyEvent.VK_DOWN : terrain.changeDirection("S"); break;
    
          case KeyEvent.VK_LEFT : terrain.changeDirection("O"); break;
    
          case KeyEvent.VK_RIGHT : terrain.changeDirection("E"); break;
    
       }
    
    }
    public void keyReleased(KeyEvent evt) {
    
    }
    public void keyTyped(KeyEvent evt) {
    
    }
}
