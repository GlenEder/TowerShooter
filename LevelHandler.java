import java.util.ArrayList;
import java.util.Random;

/**
 * Created by gmaed on 11/23/2016.
 */
public class LevelHandler {

    private static ArrayList<Enemy> enemies = new ArrayList<>();
    private Random r = new Random();
    private Game game = new Game();
    private int gameWidth = game.getWindow().getWIDTH();
    private int gameHeight = game.getWindow().getHEIGHT();
    private long now = System.nanoTime();
    private long lastTime = now;
    private long second = 1000000000;
    private long interval = second;
    private double x;
    private double y;
    private int size = 8;
    private int damage = 5;
    private int health = 3;
    private int value = 1;
    private double speed = 2.6;

    public synchronized void tick() {
        //System.out.println("levelHandler");
        if(now - lastTime >= interval) {

            boolean ranX = true;
            if(r.nextInt(10) % 2 == 0) {
                ranX = false;
            }

            boolean top = true;
            if(r.nextInt(10) % 2 == 0) {
                top = false;
            }

            boolean left = true;
            if(r.nextInt(10) % 2 == 0) {
                left = false;
            }

            if(ranX) {
                if(top) {
                    y = -10 - size;
                    if(left) {
                        x = r.nextInt(gameWidth / 2);
                    }else {
                        x = r.nextInt(gameWidth / 2) + (gameWidth / 2);
                    }
                }else {
                    y = gameHeight + 10 + size;
                    if(left) {
                        x = r.nextInt(gameWidth / 2);
                    }else {
                        x = r.nextInt(gameWidth / 2) + (gameWidth / 2);
                    }
                }
            }else {
                if(left) {
                    x = -10 - size;
                    if(top) {
                        y = r.nextInt(gameHeight / 2);
                    }else {
                        y = r.nextInt(gameHeight / 2) + (gameHeight / 2);
                    }
                }else {
                    x = 10 + gameWidth + size;
                    if(top) {
                        y = r.nextInt(gameHeight / 2);
                    }else {
                        y = r.nextInt(gameHeight / 2) + (gameHeight / 2);
                    }
                }
            }

            Enemy e = new Enemy(x, y, speed, size, damage, health, value);
            enemies.add(e);
            lastTime = now;
        }

        now = System.nanoTime();
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
