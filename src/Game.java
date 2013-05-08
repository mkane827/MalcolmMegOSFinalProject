import javax.swing.*;
import java.awt.event.*;

/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 4/26/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Game extends JFrame implements MouseListener {

    protected static final int WIDTH = 500;
    protected static final int HEIGHT = 500;
    protected GameBoard board;



    public Game(String s) {
        super(s);
        setLayout(null);
        this.setSize(this.WIDTH+100,this.HEIGHT+100);
        board = new GameBoard(this.WIDTH, this.HEIGHT);

        add(board);
    }

    abstract void runGame();

    abstract void movePaddle(int dy);

    abstract int getPaddleDirection(KeyEvent e);

    @Override
    public void mouseClicked(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
