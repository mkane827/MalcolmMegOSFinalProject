import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: Meg Naminski
 * Date: May 6, 2013
 * Time: 3:52:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserGame extends Game {

    private static final int PADDLEMOV = 7;
    private Socket socket;

    public UserGame(String s, Socket socket){
        super(s);
        this.socket = socket;
        this.addKeyListener(this);
    }

    @Override
    void runGame() {
        //TODO: Set to receive ball and paddle1 locations from server
        //TODO: Set up to send paddle2 locations to server

        System.out.println("Writing 1");
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            out.println(this.board.getPaddle1y());
            String readLine;
            while(!(readLine = in.readLine()).equals("exit")) {
                String[] boardLayout = readLine.split(",");
                this.board.setBall(Integer.parseInt(boardLayout[0]), Integer.parseInt(boardLayout[1]));
                this.board.setPaddle1(Integer.parseInt(boardLayout[2]));
                out.println(this.board.getPaddle1y());
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void movePaddle(int dy) {
        int paddley = this.board.getPaddle2y();
        if(paddley + dy < 0){
            this.board.setPaddle2(0);
        }else if(paddley + dy + this.board.PADDLEHEIGHT > this.HEIGHT){
            this.board.setPaddle2(this.HEIGHT - this.board.PADDLEHEIGHT);
        }
        else{
            this.board.movePaddle2(dy);
        }
        //TODO: Send new location?
    }

    public int getPaddleDirection(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_KP_UP){
            return -1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_KP_DOWN){
            return 1;
        }
        else{
            return 0;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        this.movePaddle(getPaddleDirection(e)*this.PADDLEMOV);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.movePaddle(getPaddleDirection(e)*this.PADDLEMOV);
    }

    public void keyReleased(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
