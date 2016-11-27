import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by gmaed on 11/23/2016.
 */
public class KeyInput implements KeyListener{

    Game game = new Game();
    private boolean SPACE = false;
    private boolean ENTER = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_ESCAPE) {
            System.out.println("Force Quiting, remove feature when done debugging");
            System.exit(0);
        }

        if(key == KeyEvent.VK_M) {
            game.getGameStateManager().setGameState(game.getGameStateManager().MENU_STATE);
            game.getDrawGraphics().changeCursor();
        }
        if(key == KeyEvent.VK_B) {
            game.getDrawGraphics().changeDebugging();
        }
        if(key == KeyEvent.VK_SPACE) {
            SPACE = true;
        }

        if(key == KeyEvent.VK_ENTER) {
            ENTER = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_SPACE) {
            SPACE = false;
        }

        if(key == KeyEvent.VK_ENTER) {
            ENTER = false;
        }
    }

    public boolean isSPACE() {
        return SPACE;
    }

    public boolean isENTER() {
        return ENTER;
    }
}
