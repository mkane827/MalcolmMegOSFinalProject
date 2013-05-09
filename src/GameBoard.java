import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by IntelliJ IDEA.
 * User: Meg Naminski
 * Date: May 6, 2013
 * Time: 12:32:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameBoard extends JPanel implements MouseMotionListener {

    private Game game;
    private int paddle1x;
    private int paddle1y;
    private int paddle2x;
    private int paddle2y;
    private int ballx;
    private int bally;
    protected static final int PADDLEWIDTH = 5;
    protected static final int PADDLEHEIGHT = 40;
    protected static final int BALLDIAM = 10;
    protected static final int MARGIN = 1;

    public GameBoard(Game game, int width, int height){
        this.game = game;
        this.setSize(width,height);
        this.paddle1x = this.MARGIN;
        this.paddle1y = height/2 - this.PADDLEHEIGHT/2;
        this.paddle2x = width - this.PADDLEWIDTH - this.MARGIN;
        this.paddle2y = height/2 - this.PADDLEHEIGHT/2;
        this.ballx = width/2-this.BALLDIAM/2;
        this.bally = height/2 - this.BALLDIAM/2;

        this.addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        g.setColor(Color.WHITE);
        g.fillRect(paddle1x, paddle1y, PADDLEWIDTH, PADDLEHEIGHT);
        g.fillOval(ballx, bally, BALLDIAM, BALLDIAM);
        g.fillRect(paddle2x, paddle2y, PADDLEWIDTH, PADDLEHEIGHT);

    }

    public void movePaddle1(int dy){
        this.paddle1y = this.paddle1y + dy;
        this.repaint();
    }

    public void movePaddle2(int dy){
        this.paddle2y = this.paddle2y + dy;
        this.repaint();
    }

    public void moveBall(int dx, int dy){
        this.ballx = this.ballx + dx;
        this.bally = this.bally+dy;
        this.repaint();
    }

    public void setPaddle1(int y){
        this.paddle1y = y;
        this.repaint();
    }

    public void setPaddle2(int y){
        this.paddle2y = y;
        this.repaint();
    }

    public void setBall(int x, int y){
        this.ballx = x;
        this.bally = y;
        this.repaint();
    }

    public int getPaddle1x(){
        return this.paddle1x;
    }

    public int getPaddle1y(){
        return this.paddle1y;
    }

    public int getPaddle2x(){
        return this.paddle2x;
    }

    public int getPaddle2y(){
        return this.paddle2y;
    }

    public int getBallx(){
        return this.ballx;
    }

    public int getBally(){
        return this.bally;
    }

    public void mouseDragged(MouseEvent e) {
        this.game.setPaddle(e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        this.game.setPaddle(e.getY());
    }
}
