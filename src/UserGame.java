import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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

    public UserGame(String s){
        super(s);
    }

    /**
     * Moves paddle 2
     * @param y
     */
    @Override
    public void movePaddle(int y) {
        this.board.setPaddle2(this.getNewPaddlePosition(y + this.board.getPaddle2y()));

    }

    /**
     * Set location of paddle 2
     * @param y
     */
    @Override
    void setPaddle(int y) {
        this.board.setPaddle2(this.getNewPaddlePosition(y));
    }
}
