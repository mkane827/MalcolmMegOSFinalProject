import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Meg Naminski
 * Date: May 8, 2013
 * Time: 8:15:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class SeverSocketController extends Thread{

    private static final int MINWAITTIME = 15;
    private static final int MAXWAITTIME = 75;
    private GameBoard board;
    private ServerSocket gameServer;
    private int balldx;
    private int balldy;
    private Random rgen = new Random();
    private int waitTime = 45;

    /**
     * Controls the server's game
     * @param board that the game is run on
     * @param gameServer that the user connected to
     */
     public SeverSocketController(GameBoard board, ServerSocket gameServer){
        this.board = board;
        this.gameServer = gameServer;

    }

    /**
     * Runs the game for the server.
     * Sends the x and y coordinates or the ball and this user's paddle position.
     * Receives the other player's paddle position
     */
    public void run(){
        try {
            Socket clientSocket = gameServer.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.board.setGameStarted(true);            
            this.startBall();
            String readLine;
            while(!((readLine = in.readLine()).equals("exit")) && !this.board.isGameOver()) {
                this.moveBall();
                this.board.setPaddle2(Integer.parseInt(readLine));
                out.println(this.board.getBallx() + "," + this.board.getBally() + "," + this.board.getPaddle1y());
                Thread.sleep(waitTime);
            }
            if(this.board.isGameOver()){
                if(this.board.isWin()){
                    out.println("exit");
                }
                else{
                    out.println("win");
                }
            }
        } catch (IOException e) {
            this.board.setDisconnected(true);            
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Puts the game ball in motion
     */
    public void startBall(){
        this.balldx = (int)(-1 + Math.round(rgen.nextDouble()) * 2) * (rgen.nextInt(3) + 1);
        this.balldy = (int)(-1 + Math.round(rgen.nextDouble()) * 2) * (4 - Math.abs(this.balldy));
    }

    /**
     * Calculates the balls movement
     */
    public void moveBall(){
        this.board.moveBall(balldx,balldy);
        int ballx = this.board.getBallx();
        int bally = this.board.getBally();
        int balld = this.board.BALLDIAM;
        int paddleZone = this.board.PADDLEWIDTH + this.board.MARGIN;
        if(ballx <= 0){
            this.board.setGameOver(true);
        }else if(ballx + balld >= this.board.getWidth()){
            this.board.setWin(true);
            this.board.setGameOver(true);
        }else if(ballx <= paddleZone){
           int paddley = this.board.getPaddle1y();
            if(bally <= paddley + this.board.PADDLEHEIGHT && bally + balld >= paddley){
                this.hitPaddle(paddley, 1);
            }
        }else if(ballx + balld >= this.board.getWidth() - paddleZone){
            int paddley = this.board.getPaddle2y();
            if(bally <= paddley + this.board.PADDLEHEIGHT && bally + balld >= paddley){
                this.hitPaddle(paddley, -1);
            }
        }
        if(bally <= 0 || bally + this.board.BALLDIAM >= this.board.getHeight()){
            this.balldy = -this.balldy;
        }
    }

    /**
     * Calculates balls change in direction and speed after it hits a paddle.
     * Based on where on the paddle the ball hits, the ball's direction will
     * change at a different angle.
     * @param paddley paddle that the ball hit
     * @param sign direction the ball was moving
     */
    public void hitPaddle(int paddley, int sign){
        int ballc = this.board.getBally() + this.board.BALLDIAM/2;
        int paddleh = this.board.PADDLEHEIGHT;
        double portions = 1.0/7 * paddleh;
        int delayInc = 0;
        if(ballc <= paddley + Math.round(portions)){
            this.balldy = -3;
            this.balldx = 1 * sign;
            delayInc = -10;
        }else if(ballc <= paddley + Math.round(portions*2)){
            this.balldy = -2;
            this.balldx = 2*sign;
            delayInc = -4;
        }else if(ballc <= paddley + Math.round(portions*3)){
            this.balldy = -1;
            this.balldx = 3*sign;
            delayInc = 4;
        }
        else if(ballc < paddley + Math.round(portions*4)){
            this.balldy = 0;
            this.balldx = 3*sign;
            delayInc = 10;
        }
        else if(ballc < paddley + Math.round(portions*5)){
            this.balldy = 1;
            this.balldx = 3*sign;
            delayInc = 4;
        }
        else if(ballc < paddley + Math.round(portions*6)){
            this.balldy = 2;
            this.balldx = 2*sign;
            delayInc = -4;
        }
        else{
            this.balldy = 3;
            this.balldx = 1*sign;
            delayInc = -10;
        }
        int newWaitTime = this.waitTime + delayInc;
        this.waitTime = Math.max(newWaitTime, this.MINWAITTIME);
        this.waitTime = Math.min(newWaitTime, this.MAXWAITTIME);
    }
}
