import javax.swing.*;
import java.net.ServerSocket;
import java.net.Socket;

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
        screen.setResizable(false);
    }


    public static void startGame(ServerSocket server){
        ServerGame game = new ServerGame("Pong - Server");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
        game.setResizable(false);
        ServerIO serverIO = new ServerIO(game.getBoard(), server);
        serverIO.start();
        //game.runGame();

    }

    public static void startGame(Socket socket){
        UserGame game = new UserGame("Pong - User");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
        game.setResizable(false);
        UserIO userIO = new UserIO(game.getBoard(), socket);
        userIO.start();
    }

}
