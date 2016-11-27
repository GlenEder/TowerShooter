import javafx.scene.paint.*;

import java.awt.*;
import java.awt.Color;

/**
 * Created by gmaed on 11/23/2016.
 */
public class MainMenu {

    Game game = new Game();
    private boolean customCursor = false;

    public void render(Graphics g) {
        g.setColor(Color.white);
        Font font = new Font("Impact", Font.ITALIC, 72);
        g.setFont(font);
        g.drawString("Tower Shooter", 200, 200);
        font = new Font("Impact", Font.ITALIC, 48);
        g.setFont(font);
        g.drawString("Press Enter to Start.", 350, 250);

        font = new Font("Impact", Font.PLAIN, 22);
        g.setFont(font);
        g.drawString("A game by Glen Michael Austin Eder", game.getWindow().getWIDTH() - 400, game.getWindow().getHEIGHT() - 50);
    }

    public void tick() {
        if(customCursor) {
            game.getDrawGraphics().changeCursor();
        }
        if(game.getKeyInput().isENTER()) {
            game.getDrawGraphics().changeCursor();
            game.getGameStateManager().setGameState(game.getGameStateManager().PLAYING_STATE);
        }
    }

    public void setCustomCursor(boolean b) {
        customCursor = b;
    }
}
