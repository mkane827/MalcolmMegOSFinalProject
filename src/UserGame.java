import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Meg Naminski
 * Date: May 6, 2013
 * Time: 3:52:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserGame extends Game{
    public UserGame(String s){
        super(s);
    }

    @Override
    void runGame() {
        //TODO: Set to receive ball and paddle1 locations from server
        //TODO: Set up to send paddle2 locations to server
    }

    @Override
    public void movePaddle(int dy) {
        int paddley = this.board.getPaddle1y();
        if(paddley + dy < 0){
            this.board.setPaddle1(0);
        }else if(paddley + dy + this.board.PADDLEHEIGHT > this.HEIGHT){
            this.board.setPaddle1(this.HEIGHT - this.board.PADDLEHEIGHT);
        }
        else{
            this.board.movePaddle1(dy);
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
}
