import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by gmaed on 11/27/2016.
 */
public class UpgradeMenu {

    Game game = new Game();
    private int width = game.getWindow().getWIDTH();
    private int height = game.getWindow().getHEIGHT();
    private double x;
    private double y;

    private Color bulletDamageColor = Color.white;
    private Color bulletSizeColor = Color.white;
    private Color cannonSpeedColor = Color.white;
    private Color maxHealthColor = Color.white;
    private Color nextLevelColor = Color.white;
    private Rectangle bulletDamage;
    private Rectangle bulletSize;
    private Rectangle cannonSpeed;
    private Rectangle maxHealth;
    private Rectangle nextLevel;

    private boolean unClicked = true;


    long now = System.nanoTime();
    long lastTime = now;
    private ArrayList<Bullet> testBullets = new ArrayList<>();

    public void tick() {
        if(!game.getDrawGraphics().isCustomCursor()) {
            game.getDrawGraphics().changeCursor();
        }

        x = game.getPlayer().getX();
        y = game.getPlayer().getY();

        if(bulletDamage != null) {
            if (x < bulletDamage.getMaxX() && x > bulletDamage.getMinX() && y < bulletDamage.getMaxY() && y > bulletDamage.getMinY()) {
                changeButtonColor("bulletDamage", Color.green);
                if(unClicked && game.getPlayer().isClicked()) {
                    game.getPlayer().increaseBulletDamage();
                    unClicked = false;
                }
            }else {
                changeButtonColor("bulletDamage", Color.white);
            }
        }

        if(cannonSpeed != null) {
            if (x < cannonSpeed.getMaxX() && x > cannonSpeed.getMinX() && y < cannonSpeed.getMaxY() && y > cannonSpeed.getMinY()) {
                changeButtonColor("cannonSpeed", Color.green);
                if(unClicked && game.getPlayer().isClicked()) {
                    game.getPlayer().decreaseCoolDown();
                    unClicked = false;
                }
            }else {
                changeButtonColor("cannonSpeed", Color.white);
            }
        }

        if(bulletSize != null) {
            if (x < bulletSize.getMaxX() && x > bulletSize.getMinX() && y < bulletSize.getMaxY() && y > bulletSize.getMinY()) {
                changeButtonColor("bulletSize", Color.green);
                if(unClicked && game.getPlayer().isClicked()) {
                    game.getPlayer().increaseBulletSize();
                    unClicked = false;
                }
            }else {
                changeButtonColor("bulletSize", Color.white);
            }
        }

        if(maxHealth != null) {
            if (x < maxHealth.getMaxX() && x > maxHealth.getMinX() && y < maxHealth.getMaxY() && y > maxHealth.getMinY()) {
                changeButtonColor("maxHealth", Color.green);
                if(unClicked && game.getPlayer().isClicked()) {
                    game.getPlayer().increaseMaxHealth();
                    unClicked = false;
                }
            }else {
                changeButtonColor("maxHealth", Color.white);
            }
        }

        if(nextLevel != null) {
            if (x < nextLevel.getMaxX() && x > nextLevel.getMinX() && y < nextLevel.getMaxY() && y > nextLevel.getMinY()) {
                changeButtonColor("nextLevel", Color.green);
                if(unClicked && game.getPlayer().isClicked()) {
                    System.out.println("going to next level");
                    game.getLevelHandler().setNewLevel(true);
                    game.getLevelHandler().increaseLevel();
                    game.getGameStateManager().setGameState(game.getGameStateManager().PLAYING_STATE);
                    unClicked = false;
                }
            }else {
                changeButtonColor("nextLevel", Color.white);
            }
        }


        //test bullets
        if(now - lastTime >= game.getPlayer().getCoolDown() * 1000000) {
            Bullet b = new Bullet(-Math.PI / 2, game.getPlayer().getBulletSpeed(), game.getPlayer().getBulletSize(), game.getPlayer().getBulletDamage());
            testBullets.add(b);
            lastTime = now;
        }



        for(int i = 0; i < testBullets.size(); i++) {
            testBullets.get(i).tick();
            if(testBullets.get(i).getY() < 150) {
                testBullets.remove(i);
            }
        }

        now = System.nanoTime();
    }

    public void render(Graphics g) {
        Font impact = new Font("Impact", Font.BOLD, 50);
        Font reg = new Font("Impact", Font.PLAIN, 26);
        g.setColor(Color.WHITE);
        g.setFont(impact);
        g.drawString("Level " + game.getLevelHandler().getLevel() + " Completed", 100, 100);
        g.drawString("Score: " + game.getPlayer().getScore(), width - 350, 100);
        g.drawLine(25, 110, width - 25, 110);

        g.setFont(reg);
        //bullet damage
        g.setColor(bulletDamageColor);
        g.drawString("Upgrade", 100, 200);
        g.drawRect(95, 175, 95, 30);
        g.setColor(Color.white);
        g.drawString("Bullet Damage: " + game.getPlayer().getBulletDamage(), 225, 200);
        bulletDamage = new Rectangle(95, 175, 95, 30);

        //bullet size
        g.setColor(bulletSizeColor);
        g.drawString("Upgrade", 100, 300);
        g.drawRect(95, 275, 95, 30);
        g.setColor(Color.white);
        g.drawString("Bullet Size: " + game.getPlayer().getBulletSize(), 225, 300);
        bulletSize = new Rectangle(95, 275, 95, 30);

        //cannon speed
        g.setColor(cannonSpeedColor);
        g.drawString("Upgrade", 100, 400);
        g.drawRect(95, 375, 95, 30);
        g.setColor(Color.white);
        g.drawString("Cannon Cool Down: " + game.getPlayer().getCoolDown() + "ms", 225, 400);
        cannonSpeed = new Rectangle(95, 375, 95, 30);

        //max health
        g.setColor(maxHealthColor);
        g.drawString("Upgrade", 100, 500);
        g.drawRect(95, 475, 95, 30);
        g.setColor(Color.white);
        g.drawString("Max Health: " + game.getPlayer().getMaxHealth(), 225, 500);
        maxHealth = new Rectangle(95, 475, 95, 30);

        //tower wall


        //sim bullets
        g.setColor(Color.magenta);
        for(int i = 0; i < testBullets.size(); i++) {
            testBullets.get(i).render(g);
        }

        //next level button
        g.setColor(nextLevelColor);
        nextLevel = new Rectangle(width - 205, height - 75, 113, 30);
        g.drawRect(width - 205, height - 75, 113, 30);
        g.drawString("Next Level", width - 200, height - 50);

        //cursor
        g.setColor(Color.white);
        g.drawLine((int)x - 5, (int)y, (int)x + 5, (int)y);
        g.drawLine((int)x, (int)y + 5, (int)x, (int)y - 5);
    }

    public void changeButtonColor(String button, Color color) {
        switch (button) {
            case "bulletDamage":
                bulletDamageColor = color;
                break;
            case "bulletSize":
                bulletSizeColor = color;
                break;
            case "cannonSpeed":
                cannonSpeedColor = color;
                break;
            case "maxHealth":
                maxHealthColor = color;
                break;
            case "nextLevel":
                nextLevelColor = color;
                break;
            default:
                break;
        }
    }

    public void setUnClicked(boolean b) {
        unClicked = b;
    }



}
