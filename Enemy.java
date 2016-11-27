import javax.print.attribute.standard.MediaSize;
import java.awt.*;
import java.util.Map;

/**
 * Created by gmaed on 11/23/2016.
 */
public class Enemy {

    Game game = new Game();
    private Rectangle rectangle;
    private double x;
    private double y;
    private double velX;
    private double velY;
    private double speed;
    private int health;
    private int size;
    private int damage;
    private int value;

    private int centerX = game.getWindow().getWIDTH() / 2;
    private int centerY = game.getWindow().getHEIGHT() / 2;

    public Enemy(double x, double y, double speed, int size, int damage, int health, int value) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        this.damage = damage;
        this.health = health;
        this.value = value;

        calcVelocities();
    }

    public synchronized void tick() {
        x += velX;
        y += velY;
    }


    public void calcVelocities() {
        velX = (centerX - x) / calcDistance();
        velY = (centerY - y) / calcDistance();
    }


    public double calcDistance() {
        double insideX = (x - centerX) * (x - centerX);
        double insideY = (y - centerY) * (y - centerY);

        return Math.sqrt(insideX + insideY);
    }


    public void render(Graphics g) {
        rectangle = new Rectangle((int)x, (int)y, size, size);
        g.setColor(Color.red);
        if(game.getDrawGraphics().isDebugging()) {
            g.drawRect((int)x, (int)y, size, size);
        }else {
            g.fillRect((int) x, (int) y, size, size);
        }

    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isDead() {
        if(health <= 0) {
            return true;
        }else {
            return false;
        }
    }

    public void reduceHealth(int i) {
        health -= i;
    }

    public int getValue() {
        return value;
    }
}
