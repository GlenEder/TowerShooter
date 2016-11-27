import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by gmaed on 11/23/2016.
 */
public class Window implements WindowListener{

    JFrame frame;

    private static int WIDTH = 1440;
    private static int HEIGHT = WIDTH * 9 / 16;

    public void createWindow() {
        frame = new JFrame("Cool Title Here");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(this);
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }








    //window listener methods
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            System.out.println("Quitting game...");
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    /**
     * Created by gmaed on 11/25/2016.
     */
    public static class HighScores {

        private static ArrayList<String> highScores = new ArrayList<>();
        String file = "D:\\IJ_Projects\\TowerShooter";

        public void loadScores() throws IOException {
            BufferedReader br = new BufferedReader(new FileReader(file));
            for(int i = 0; i < 10; i++) {
                String line = br.readLine();
                if(line != null) {
                    highScores.add(line);
                }
            }
        }
    }
}
