import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 4/24/13
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pong {

    public static void main(String[] args) {
        StartingScreen screen = new StartingScreen("Setup");
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setSize(500, 500);
        screen.setVisible(true);
    }


    public static void startGame(boolean server){
         Game game = new Game("Setup", server);
         game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         game.setVisible(true);
    }

}
