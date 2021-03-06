import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: Meg Naminski
 * Date: May 8, 2013
 * Time: 8:51:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserSocketController extends Thread{
    private GameBoard board;
    private Socket socket;

    /**
     * Controls the game for the user
     * @param board that the game is being played on
     * @param socket that the user used to connect to the server
     */
    public UserSocketController(GameBoard board, Socket socket){
        this.board = board;
        this.socket = socket;
    }

    /**
     * Runs the game logic for the user.
     * Sends user's paddle possition.
     * Recieves ball x and y and server player's paddle position
     */
    public void run(){
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.board.setGameStarted(true);
            out.println(this.board.getPaddle1y());

            String readLine;
            while(!((readLine = in.readLine()).equals("exit"))&& !readLine.equals("win")) {
                String[] boardLayout = readLine.split(",");
                this.board.setBall(Integer.parseInt(boardLayout[0]), Integer.parseInt(boardLayout[1]));
                this.board.setPaddle1(Integer.parseInt(boardLayout[2]));
                out.println(this.board.getPaddle2y());

            }
            out.println("exit");
            if(readLine.equals("exit")){
                this.board.setGameOver(true);
            }
            else if(readLine.equals("win")){
                this.board.setWin(true);
                this.board.setGameOver(true);
            }
        } catch (IOException e) {
            this.board.setDisconnected(true);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
