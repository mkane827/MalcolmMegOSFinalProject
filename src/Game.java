import javax.swing.*;
import java.awt.event.*;

/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 4/26/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Game extends JFrame implements KeyListener{

    protected static final int WIDTH = 500;
    protected static final int HEIGHT = 500;
    protected static final int PADDLEMOV = 10;
    protected GameBoard board;



    public Game(String s) {
        super(s);
        setLayout(null);
        this.setSize(this.WIDTH+100,this.HEIGHT+100);
        board = new GameBoard(this, this.WIDTH, this.HEIGHT);
        add(board);
        this.addKeyListener(this);
    }

    public GameBoard getBoard(){
        return this.board;
    }

    abstract void movePaddle(int y);

    abstract void setPaddle(int y);

    protected int getPaddleDirection(KeyEvent e){
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

    protected int getNewPaddlePosition(int y){
        if(y < 0){
            return 0;
        }else if(y + this.board.PADDLEHEIGHT > this.HEIGHT){
            return this.HEIGHT - this.board.PADDLEHEIGHT;
        }
        else{
            return y;
        }
    }

    public void keyTyped(KeyEvent e) {
        this.movePaddle(getPaddleDirection(e)*this.PADDLEMOV);
    }

    public void keyPressed(KeyEvent e) {
        this.movePaddle(getPaddleDirection(e)*this.PADDLEMOV);
    }

    public void keyReleased(KeyEvent e) {
        //do nothing
    }    
}
