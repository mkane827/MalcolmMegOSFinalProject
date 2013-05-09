import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Meg Naminski
 * Date: May 6, 2013
 * Time: 3:52:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerGame extends Game{

    public ServerGame(String s){
        super(s);
    }

    @Override
    public void movePaddle(int y) {
        this.board.setPaddle1(this.getNewPaddlePosition(y + this.board.getPaddle1y()));
    }

    @Override
    void setPaddle(int y) {
        this.board.setPaddle1(this.getNewPaddlePosition(y));
    }
}
