import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Meg Naminski
 * Date: May 6, 2013
 * Time: 12:32:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameBoard extends JPanel {
    private int paddle1x;
    private int paddle1y;
    private int paddle2x;
    private int paddle2y;
    private int ballx;
    private int bally;
    private static final int PADDLEWIDTH = 5;
    private static final int PADDLEHEIGHT = 40;
    private static final int BALLDIAM = 10;

    public GameBoard(int width, int height){
        this.setSize(width,height);
        this.paddle1x = 5;
        this.paddle1y = height/2 - PADDLEHEIGHT/2;
        this.paddle2x = width - PADDLEWIDTH;
        this.paddle2y = height/2 - PADDLEHEIGHT/2;
        this.ballx = width/2-BALLDIAM/2;
        this.bally = height/2 - BALLDIAM/2;
        System.out.println(Integer.toString(width));
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
}
