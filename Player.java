import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * Created by gmaed on 11/23/2016.
 */
public class Player implements MouseMotionListener, MouseListener{

    Game game = new Game();
    private Rectangle rectangle;
    private int x;
    private int y;
    private int health = 100;
    private int maxHealth = 100;
    private int playerSize = 20;
    private int gameWidth = game.getWindow().getWIDTH();
    private int gameHeight = game.getWindow().getHEIGHT();
    private int middleWidth = (gameWidth / 2) - (playerSize / 2);
    private int middleHeight = (gameHeight / 2) - (playerSize / 2);
    private int middleWidthScreen =  gameWidth / 2;
    private int middleHeightScreen = gameHeight / 2;
    private long second = 1000000000;
    private long coolDown = second / 2;
    private double bulletSpeed = 3.5;
    private int bulletSize = 2;
    private int bulletDamage = 5;

    private int score = 0;

    private boolean clicked = false;
    private boolean canShoot = true;
    private boolean alive = true;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public Player() {
        game.getWindow().getFrame().addMouseMotionListener(this);
        game.getWindow().getFrame().addMouseListener(this);
    }

    long now = System.nanoTime();
    long lastTime = now;
    public synchronized void tick() {

        if(alive) {
            now = System.nanoTime();

            if (game.getKeyInput().isSPACE()) {
                if (canShoot) {
                    canShoot = false;
                    lastTime = now;
                    Bullet b = new Bullet(calcTheta(), bulletSpeed, bulletSize, bulletDamage);
                    bullets.add(b);
                }
            }

            if (!canShoot) {
                if (now - lastTime >= coolDown) {
                    canShoot = true;
                    lastTime = now;
                }
            }
        }

    }

    public double calcTheta() {
        double theta = 0;
        double X = x - middleWidthScreen;
        double Y = y - middleHeightScreen;
        if(X == 0) {
            if(y > 0) {
                return (3/2) * Math.PI;
            }else {
                return Math.PI / 2;
            }
        }

        theta = Math.atan(Y/X);

        if(X < 0) {
            theta += Math.PI;
        }

        return theta;
    }

    public void render(Graphics g) {

        //health bar
        g.setColor(Color.gray);
        g.fillRect(100, gameHeight - 65, maxHealth * 2, 15);
        g.setColor(Color.green);
        g.fillRect(100, gameHeight - 65, health * 2, 15);
        g.setColor(Color.white);
        g.drawRect(100, gameHeight - 65, maxHealth * 2, 15);

        //score bar
        g.drawString("Score: " + game.getPlayer().getScore(), gameWidth - 100, gameHeight - 45);

        g.setColor(Color.cyan);
        if(game.getDrawGraphics().isDebugging()) {
            g.drawOval(middleWidth, middleHeight, playerSize, playerSize);
            g.setColor(Color.magenta);
            g.drawLine(x, y, middleWidth + playerSize / 2, middleHeight + playerSize / 2);
            g.drawRect(middleWidth, middleHeight, playerSize, playerSize);
        }else {
            g.fillOval(middleWidth, middleHeight, playerSize, playerSize);
        }

        if(!alive) {
            g.setColor(Color.red);
            Font font = new Font("Impact", Font.BOLD, 45);
            g.setFont(font);
            g.drawString("GAME OVER", gameWidth / 2 - 108, 200);
        }

        //cursor
        g.setColor(Color.white);
        g.drawLine(x - 5, y, x + 5, y);
        g.drawLine(x, y + 5, x, y - 5);
        rectangle = new Rectangle(middleWidth, middleHeight, playerSize, playerSize);

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isClicked() {
        return clicked;
    }

    public int getHealth() {
        return  health;
    }

    public void setCooldown(int sec) {
        coolDown = sec * second;
    }

    public long getCoolDown() {
        return coolDown / 1000000;
    }

    public boolean canShoot() {
        return canShoot;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setHealth(int i) {
        if(i > maxHealth) {
            health = maxHealth;
        }else {
            health = i;
        }
    }

    public void increaseHealth(int i) {
        health += i;
        if(health > maxHealth) {
            health = maxHealth;
        }
    }

    public void reduceHealth(int i) {
        health -= i;
        if(health <= 0) {
            health = 0;
            alive = false;
        }
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(double d) {
        bulletSpeed = d;
    }

    public int getBulletSize() {
        return bulletSize;
    }

    public void setBulletSize(int i) {
        bulletSize = i;
    }

    public void setBulletDamage(int i) {
        bulletDamage = i;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int i) {
        score += i;
    }

    public void increaseBulletDamage() {
        bulletDamage += 5;
    }

    public void decreaseCoolDown() {
        coolDown -= 50000000;
        if(coolDown <= 100000000) {
            coolDown = 100000000;
        }
    }

    public void increaseBulletSize() {
        bulletSize += 2;
    }

    public void increaseMaxHealth() {
        maxHealth += 50;
    }






    //mouse methods
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        clicked = false;
        game.getUpgradeMenu().setUnClicked(true);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
