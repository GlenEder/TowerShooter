import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by gmaed on 11/23/2016.
 */
public class DrawGraphics extends JPanel implements Runnable{

    Game game = new Game();
    private int width;
    private int height;
    private int frames = 0;
    private int FPS = 0;

    private boolean isDebugging = false;
    private boolean isCustomCursor = false;
    private Thread thread;

    public DrawGraphics() {
        game.getWindow().getFrame().add(this);
        width = game.getWindow().getWIDTH();
        height = game.getWindow().getHEIGHT();
    }

    public void changeCursor() {
        if(isCustomCursor) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            game.getMainMenu().setCustomCursor(false);
            isCustomCursor = false;
        }else {
            BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    cursorImg, new Point(0, 0), "blank cursor");
            setCursor(blankCursor);
            game.getMainMenu().setCustomCursor(true);
            isCustomCursor = true;
        }
    }

    public boolean isCustomCursor() {
        return isCustomCursor;
    }

    public void run() {

        long now = System.nanoTime();
        long lastTime = now;
        long second = 1000000000;

        while(game.isRunning()) {
            repaint();
            frames++;
            if(now - lastTime >= second) {
                FPS = frames;
                frames = 0;
                lastTime = now;
            }
            now = System.nanoTime();
        }
    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        ///////////

            String gameState = game.getGameStateManager().getGameState();
            if(gameState.equals(game.getGameStateManager().PLAYING_STATE)) {
                ArrayList<Bullet> bullets = game.getPlayer().getBullets();
                for (int i = 0; i < bullets.size(); i++) {
                    if (bullets.get(i) != null) {
                        bullets.get(i).render(g);
                    }

                }

                ArrayList<Enemy> enemies = game.getLevelHandler().getEnemies();
                for (int i = 0; i < enemies.size(); i++) {
                    enemies.get(i).render(g);
                }

                game.getPlayer().render(g);
            }else if(gameState.equals(game.getGameStateManager().MENU_STATE)) {
                if(game.getMainMenu() != null) {
                    game.getMainMenu().render(g);
                }
            }else if(gameState.equals(game.getGameStateManager().UPGRADE_STATE)) {
                if(game.getUpgradeMenu() != null) {
                    game.getUpgradeMenu().render(g);
                }
            }

        ///////////
        if(isDebugging) {
            DecimalFormat format = new DecimalFormat("#0.00");
            Font font = new JLabel().getFont();
            g.setFont(font);
            g.setColor(Color.red);
            g.drawLine(width / 2, 0, width / 2, height);    //vert line
            g.drawLine(0, height / 2, width, height / 2);   //hoz line


            g.setColor(Color.green);
            //left side
            g.drawString("FPS: " + FPS, 25, 25);
            g.drawString("TPS: " + game.getTPS(), 25, 45);
            g.drawString("Max Health: " + game.getPlayer().getMaxHealth(), 25, 65);
            g.drawString("Health: " + game.getPlayer().getHealth(), 25, 85);
            g.drawString("Can Shoot: " + game.getPlayer().canShoot(), 25, 105);
            g.drawString("CoolDown: " + format.format(game.getPlayer().getCoolDown()) + "ms", 25, 125);
            g.drawString("#Bullets: " + game.getPlayer().getBullets().size(), 25, 145);
            g.drawString("Bullet Speed: " + game.getPlayer().getBulletSpeed(), 25, 165);
            g.drawString("Bullet Size: " + game.getPlayer().getBulletSize(), 25, 185);
            g.drawString("Bullet Damage: " + game.getPlayer().getBulletDamage(), 25, 205);

            //right side
            g.drawString("Mouse X: " + game.getPlayer().getX(), width - 150, 25);
            g.drawString("Mouse Y: " + game.getPlayer().getY(), width - 150, 45);
            g.drawString("Clicked: " + game.getPlayer().isClicked(), width - 150, 65);
            g.drawString("SPACE: " + game.getKeyInput().isSPACE(), width - 150, 85);
            g.drawString("#Enemies: " + game.getLevelHandler().getEnemies().size(), width -150, 105);
            g.drawString("#Enemies Left: " + game.getLevelHandler().numEnemiesLeft(), width - 150, 125);
            g.drawString("Level: " + game.getLevelHandler().getLevel(), width -150, 145);
        }

        g.dispose();
    }

    public void start() {
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public boolean isDebugging() {
        return isDebugging;
    }

    public void changeDebugging() {
        if(isDebugging) {
            isDebugging = false;
        }else {
            isDebugging = true;
        }
    }
}
