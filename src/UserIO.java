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
public class UserIO extends Thread{
    private GameBoard board;
    private Socket socket;

    public UserIO(GameBoard board, Socket socket){
        this.board = board;
        this.socket = socket;
    }

    public void run(){
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(this.board.getPaddle1y());

            String readLine;
            while(!((readLine = in.readLine()).equals("exit"))) {
                String[] boardLayout = readLine.split(",");
                this.board.setBall(Integer.parseInt(boardLayout[0]), Integer.parseInt(boardLayout[1]));
                this.board.setPaddle1(Integer.parseInt(boardLayout[2]));
                out.println(this.board.getPaddle2y());

            }
            out.println("exit");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
