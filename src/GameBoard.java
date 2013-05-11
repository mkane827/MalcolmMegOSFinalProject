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
    private int width;
    private int height;
    private int paddle1x; // server paddle
    private int paddle1y; // server paddle
    private int paddle2x; // user paddle
    private int paddle2y; // user paddle
    private int ballx;
    private int bally;
    private boolean gameOver = false;
    private boolean gameStarted = false;
    private boolean disconnected = false;
    private boolean win = false;
    private static final String DISCONNECTED = "Opponent Disconnected";
    private static final String WIN = "You Won!";
    private static final String GAMEOVER = "Game Over";
    private static final String NOTSTARTED = "Waiting for Opponent...";    
    protected static final int PADDLEWIDTH = 5;
    protected static final int PADDLEHEIGHT = 40;
    protected static final int BALLDIAM = 10;
    protected static final int MARGIN = 1;

    public GameBoard(Game game, int width, int height){
        this.game = game;
        this.width = width;
        this.height = height;
        this.setSize(this.width,this.height);
        this.paddle1x = this.MARGIN;
        this.paddle1y = height/2 - this.PADDLEHEIGHT/2;
        this.paddle2x = width - this.PADDLEWIDTH - this.MARGIN;
        this.paddle2y = height/2 - this.PADDLEHEIGHT/2;
        this.ballx = width/2-this.BALLDIAM/2;
        this.bally = height/2 - this.BALLDIAM/2;

        this.addMouseMotionListener(this);
    }

    /**
     * Paints jpanel
     * @param g
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        g.setColor(Color.WHITE);
        g.fillRect(paddle1x, paddle1y, PADDLEWIDTH, PADDLEHEIGHT);
        if(gameStarted){
            // draw ball
            g.fillOval(ballx, bally, BALLDIAM, BALLDIAM);
        }
        g.fillRect(paddle2x, paddle2y, PADDLEWIDTH, PADDLEHEIGHT);

        g.setColor(Color.YELLOW);
        Font f = new Font("Arial", 0, 20);
        g.setFont(f);
        if(!gameStarted){
            // wait for connection
            int sWidth = g.getFontMetrics(g.getFont()).stringWidth(this.NOTSTARTED);
            g.drawString(this.NOTSTARTED, this.width/2 - sWidth/2, this.height/2);
        }
        else if(gameOver){
            if(win){
                // notify win
                int sWidth = g.getFontMetrics(g.getFont()).stringWidth(this.WIN);
                g.drawString(this.WIN, this.width/2 - sWidth/2, this.height/2);

            }
            else{
                // notify over
                int sWidth = g.getFontMetrics(g.getFont()).stringWidth(this.GAMEOVER);
                g.drawString(this.GAMEOVER, this.width/2 - sWidth/2, this.height/2);

            }
        }
        else if(disconnected){
            //notify disconnect
            int sWidth = g.getFontMetrics(g.getFont()).stringWidth(this.DISCONNECTED);
            g.drawString(this.DISCONNECTED, this.width/2 - sWidth/2, this.height/2);
        }

    }

    /**
     *  Moves paddle 1 by distance dy
     * @param dy
     */
    public void movePaddle1(int dy){
        this.paddle1y = this.paddle1y + dy;
        this.repaint();
    }

    /**
     * Moves paddle 2 by distance dy
     * @param dy
     */
    public void movePaddle2(int dy){
        this.paddle2y = this.paddle2y + dy;
        this.repaint();
    }

    /**
     * Move by by distance dx and dy
     * @param dx
     * @param dy
     */
    public void moveBall(int dx, int dy){
        this.ballx = this.ballx + dx;
        this.bally = this.bally+dy;
        this.repaint();
    }

    /**
     * Set paddle 1 at location y
     * @param y
     */
    public void setPaddle1(int y){
        this.paddle1y = y;
        this.repaint();
    }

    /**
     * Set paddle 2 at location y
     * @param y
     */
    public void setPaddle2(int y){
        this.paddle2y = y;
        this.repaint();
    }

    /**
     * Set ball at location x and y
     * @param x
     * @param y
     */
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

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Set game over and remove listeners
     * @param gameOver
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        this.repaint();
        this.removeMouseMotionListener(this);
        this.game.removeKeyListener(this.game);
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
        this.repaint();
    }

    public boolean isDisconnected() {
        return disconnected;
    }

    /**
     * Set disconnected and remove listeners
     * @param disconnected
     */
    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
        this.repaint();
        this.removeMouseMotionListener(this);      
        this.game.removeKeyListener(this.game);
        
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public void mouseDragged(MouseEvent e) {
        this.game.setPaddle(e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        this.game.setPaddle(e.getY());
    }
}
