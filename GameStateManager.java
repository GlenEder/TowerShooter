/**
 * Created by gmaed on 11/23/2016.
 */
public class GameStateManager {

    private static String currentGameState;


    //constants
    public static String MENU_STATE = "menu";
    public static String PLAYING_STATE = "playing";
    public static String UPGRADE_STATE = "upgrade";
    public static String OPTIONS_STATE = "options";
    public static String PAUSE_STATE = "pause";
    public static String HIGHSCORE_STATE = "highScores";

    public void setGameState(String gameState) {
        currentGameState = gameState;
    }

    public String getGameState() {
        return currentGameState;
    }
}
