import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Meg Naminski
 * Date: May 6, 2013
 * Time: 3:52:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerGame extends Game implements ActionListener{

    private Timer myTimer;
    private static final int DELAYMAX = 30;
    private static final int DELAYMIN = 5;
    private static final int PADDLEMOV = 7;
    private int balldx;
    private int balldy;
    private Random rgen = new Random();

    public ServerGame(String s){
        super(s);
        this.addKeyListener(this);
        this.runGame();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(myTimer)){
            this.moveBall();
        }
    }

    @Override
    public void runGame(){
        //TODO: Set to send ball and paddle1 locations to user
        //TODO: Set up to receive paddle2 locations from user
        this.startBall();
        this.myTimer = new Timer(30, this);
        this.myTimer.start();
    }

    public void startBall(){
        this.balldx = (int)(-1 + Math.round(rgen.nextDouble()) * 2) * (rgen.nextInt(3) + 1);
        this.balldy = (int)(-1 + Math.round(rgen.nextDouble()) * 2) * (4 - Math.abs(this.balldy));
    }

    public void moveBall(){
        super.board.moveBall(balldx,balldy);
        int ballx = super.board.getBallx();
        int bally = super.board.getBally();
        int balld = super.board.BALLDIAM;
        int paddleZone = super.board.PADDLEWIDTH + super.board.MARGIN;
        if(ballx <= 0 || ballx + balld >= super.WIDTH){
            myTimer.stop();
            //TODO: accumulate point or end game
        }else if(ballx <= paddleZone){
           int paddley = super.board.getPaddle1y();
            if(bally <= paddley + super.board.PADDLEHEIGHT && bally + balld >= paddley){
                this.hitPaddle(paddley, 1);
            }
        }else if(ballx + balld >= super.WIDTH - paddleZone){
            int paddley = super.board.getPaddle2y();
            if(bally <= paddley + super.board.PADDLEHEIGHT && bally + balld >= paddley){
                this.hitPaddle(paddley, -1);
            }
        }
        if(bally <= 0 || bally + super.board.BALLDIAM >= super.HEIGHT){
            this.balldy = -this.balldy;
        }
        //TODO: Send new locations?
    }

    public void hitPaddle(int paddley, int sign){
        int ballc = super.board.getBally() + super.board.BALLDIAM/2;
        int paddleh = super.board.PADDLEHEIGHT;
        double portions = 1.0/7 * paddleh;
        int delayInc = 0;
        if(ballc <= paddley + Math.round(portions)){
            this.balldy = -3;
            this.balldx = 1 * sign;
            delayInc = -5;
        }else if(ballc <= paddley + Math.round(portions*2)){
            this.balldy = -2;
            this.balldx = 2*sign;
            delayInc = -2;
        }else if(ballc <= paddley + Math.round(portions*3)){
            this.balldy = -1;
            this.balldx = 3*sign;
            delayInc = 2;
        }
        else if(ballc < paddley + Math.round(portions*4)){
            this.balldy = 0;
            this.balldx = 4*sign;
            delayInc = 5;
        }
        else if(ballc < paddley + Math.round(portions*5)){
            this.balldy = 1;
            this.balldx = 3*sign;
            delayInc = 2;
        }
        else if(ballc < paddley + Math.round(portions*6)){
            this.balldy = 2;
            this.balldx = 2*sign;
            delayInc = -2;
        }
        else{
            this.balldy = 3;
            this.balldx = 1*sign;
            delayInc = -5;
        }
        int newDelay = this.myTimer.getDelay() + delayInc;
        if(newDelay < this.DELAYMIN){
            this.myTimer.setDelay(this.DELAYMIN);
        }else if(newDelay > this.DELAYMAX){
            this.myTimer.setDelay(this.DELAYMAX);
        }else{
            this.myTimer.setDelay(newDelay);
        }

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

    @Override
    public void keyReleased(KeyEvent e) {
        //nothing
    }
}
