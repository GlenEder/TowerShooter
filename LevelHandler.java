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

    private int[] numEnemies = new int[5];
    private int level = 1;
    private boolean changed = false;
    private boolean newLevel = true;

    public synchronized void tick() {
        //System.out.println("levelHandler");

        if (newLevel) {
            System.out.println("init enemies");
            changed = false;
            initEnemies();
            newLevel = false;
        }

        if (numEnemiesLeft() == 0 && enemies.size() == 0) {
            game.getGameStateManager().setGameState(game.getGameStateManager().UPGRADE_STATE);
        }

        //spawn enemies on timer
        if (now - lastTime >= interval) {

            if (enemiesLeft()) {

                boolean ranX = true;
                if (r.nextInt(10) % 2 == 0) {
                    ranX = false;
                }

                boolean top = true;
                if (r.nextInt(10) % 2 == 0) {
                    top = false;
                }

                boolean left = true;
                if (r.nextInt(10) % 2 == 0) {
                    left = false;
                }

                if (ranX) {
                    if (top) {
                        y = -10 - size;
                        if (left) {
                            x = r.nextInt(gameWidth / 2);
                        } else {
                            x = r.nextInt(gameWidth / 2) + (gameWidth / 2);
                        }
                    } else {
                        y = gameHeight + 10 + size;
                        if (left) {
                            x = r.nextInt(gameWidth / 2);
                        } else {
                            x = r.nextInt(gameWidth / 2) + (gameWidth / 2);
                        }
                    }
                } else {
                    if (left) {
                        x = -10 - size;
                        if (top) {
                            y = r.nextInt(gameHeight / 2);
                        } else {
                            y = r.nextInt(gameHeight / 2) + (gameHeight / 2);
                        }
                    } else {
                        x = 10 + gameWidth + size;
                        if (top) {
                            y = r.nextInt(gameHeight / 2);
                        } else {
                            y = r.nextInt(gameHeight / 2) + (gameHeight / 2);
                        }
                    }
                }

                int eType = r.nextInt(numEnemies.length);

                if (eType == 1) {
                    if (numEnemies[1] != 0) {
                        size = 16;
                        damage = 10;
                        health = 6;
                        value = 2;
                        speed = 1.3;
                        --numEnemies[1];
                    } else {
                        eType = 2;
                    }
                }

                if (eType == 2) {
                    if (numEnemies[2] != 0) {
                        size = 20;
                        damage = 20;
                        health = 20;
                        value = 10;
                        speed = 2;
                        --numEnemies[2];
                    } else {
                        eType = 3;
                    }
                }

                if (eType == 3) {
                    if (numEnemies[3] != 0) {
                        size = 16;
                        damage = 10;
                        health = 6;
                        value = 2;
                        speed = 1.3;
                        --numEnemies[3];
                    } else {
                        eType = 4;
                    }
                }

                if (eType == 4) {
                    if (numEnemies[4] != 0) {
                        size = 16;
                        damage = 10;
                        health = 6;
                        value = 2;
                        speed = 1.3;
                        --numEnemies[4];
                    } else {
                        if (numEnemies[0] != 0) {
                            size = 8;
                            damage = 5;
                            health = 3;
                            value = 1;
                            speed = 2.6;
                            --numEnemies[0];
                        }
                    }

                    Enemy e = new Enemy(x, y, speed, size, damage, health, value);
                    enemies.add(e);
                    lastTime = now;
                }


            }

        }
        now = System.nanoTime();
    }

    private void initEnemies() {
        if (!changed) {
            if (level == 1) {
                numEnemies[0] = 5;
                numEnemies[1] = 0;
                numEnemies[2] = 0;
                numEnemies[3] = 0;
                numEnemies[4] = 0;
            } else {
                numEnemies[0] = 1 + 5 * level;
                numEnemies[1] = 1 + 3 * level;
                numEnemies[2] = 2 + 2 * level;
                numEnemies[3] = 3 * level;
                numEnemies[4] = level;
                System.out.println("#enemies: " + numEnemiesLeft());
            }
        }
        changed = true;
    }


    private boolean enemiesLeft() {
        for(int i = 0; i < numEnemies.length; i++) {
            if(numEnemies[i] != 0) {
                return true;
            }
        }

        return false;
    }

    public int numEnemiesLeft() {
        int enemies = 0;
        for(int i = 0; i < numEnemies.length; i++) {
            enemies += numEnemies[i];
        }

        return enemies;
    }


    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public int getLevel() {
        return level;
    }

    public void increaseLevel() {
        level++;
    }

    public void setNewLevel(boolean b) {
        newLevel = b;
    }
}
