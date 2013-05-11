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

    /**
     * Creates the setup screen for the game.
     * @param args
     */
    public static void main(String[] args) {
        StartingScreen screen = new StartingScreen("Setup");
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setSize(500, 500);
        screen.setVisible(true);
        screen.setResizable(false);
    }

    /**
     * Starts the game if the user started the server.
     * @param server that the user created to play the game on
     */
    public static void startGame(ServerSocket server){
        ServerGame game = new ServerGame("Pong - Server");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
        game.setResizable(false);
        SeverSocketController severSocketController = new SeverSocketController(game.getBoard(), server);
        severSocketController.start();
        //game.runGame();

    }

    /**
     * Starts teh game if the user connected to the server
     * @param socket that the user connected to
     */
    public static void startGame(Socket socket){
        UserGame game = new UserGame("Pong - User");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
        game.setResizable(false);
        UserSocketController userSocketController = new UserSocketController(game.getBoard(), socket);
        userSocketController.start();
    }

}
