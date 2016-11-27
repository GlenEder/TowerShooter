import java.util.ArrayList;

/**
 * Created by gmaed on 11/23/2016.
 */
public class Game implements Runnable{

    Thread gameThread;
    private static Window window;
    private static DrawGraphics drawGraphics;
    private static Player player;
    private static KeyInput keyInput;
    private static Collisions collisions;
    private static MainMenu mainMenu;
    private static LevelHandler levelHandler;
    private static GameStateManager gameStateManager;
    private static boolean running = false;

    private static int TPS = 0;


    public void init() {

        window = new Window();
        window.createWindow();
        gameStateManager = new GameStateManager();
        gameStateManager.setGameState(gameStateManager.MENU_STATE);
        drawGraphics = new DrawGraphics();
        drawGraphics.start();
        keyInput = new KeyInput();
        window.getFrame().addKeyListener(keyInput);
        levelHandler = new LevelHandler();
        collisions = new Collisions();
        mainMenu = new MainMenu();

        player = new Player();
    }
    public void run() {

        init();

        long now = System.nanoTime();
        long lastTime = now;
        long second = 1000000000;
        long timer = second / 60;
        long longTime = now;
        int ticks = 0;

        while(running) {
            if(now - lastTime >= timer) {
                tick();
                ticks++;
                lastTime = now;
            }

            if(now - longTime >= second) {
                TPS = ticks;
                ticks = 0;
                longTime = now;
            }

            now = System.nanoTime();

        }
    }

    public synchronized void tick() {
        String gameState = gameStateManager.getGameState();
        if(gameState.equals(gameStateManager.PLAYING_STATE)) {
            player.tick();
            ArrayList<Bullet> bullets = player.getBullets();
            for (int i = 0; i < bullets.size(); i++) {
                Bullet b = bullets.get(i);
                b.tick();
                int size = player.getBulletSize();
                if (b.getX() > window.getWIDTH() + size || b.getX() < 0 - size) {
                    player.getBullets().remove(i);
                }

                if (b.getY() > window.getHEIGHT() + size || b.getY() < 0 - size) {
                    player.getBullets().remove(i);
                }
            }

            levelHandler.tick();
            ArrayList<Enemy> enemies = levelHandler.getEnemies();
            for(int j = 0; j < enemies.size(); j++) {
                enemies.get(j).tick();
            }
            collisions.tick();
        }else if(gameState.equals(gameStateManager.MENU_STATE)) {
            mainMenu.tick();
        }


    }

    public void start() {
        if(gameThread == null) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public Window getWindow() {
        return window;
    }

    public int getTPS() {
        return TPS;
    }

    public Player getPlayer() {
        return player;
    }

    public KeyInput getKeyInput() {
        return keyInput;
    }

    public DrawGraphics getDrawGraphics() {
        return drawGraphics;
    }

    public LevelHandler getLevelHandler() {
        return levelHandler;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }
}

