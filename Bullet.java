import java.awt.*;

/**
 * Created by gmaed on 11/23/2016.
 */
public class Bullet {

    Game game = new Game();
    private Rectangle rectangle;
    private double theta;
    private double speed;
    private int size;
    private double x = game.getWindow().getWIDTH() / 2;
    private double y = game.getWindow().getHEIGHT() / 2;
    private int damage;

    public Bullet(double theta, double speed, int size, int damage) {
        this.theta = theta;
        this.speed = speed;
        this.size = size;
        this.damage = damage;
    }

    public void tick() {
        x += Math.cos(theta) * speed;
        y += Math.sin(theta) * speed;
    }

    public void render(Graphics g) {
        rectangle = new Rectangle((int)x, (int)y, size, size);
        g.setColor(Color.magenta);
        if(game.getDrawGraphics().isDebugging()) {
            g.drawOval((int)x, (int)y, size, size);
        }else {
            g.fillOval((int)x, (int)y, size, size);
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
