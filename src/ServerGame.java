import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Meg Naminski
 * Date: May 6, 2013
 * Time: 3:52:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerGame extends Game {

    private GameServer gameServer;
    private static final int DELAYMAX = 30;
    private static final int DELAYMIN = 5;
    private static final int PADDLEMOV = 7;
    private int balldx;
    private int balldy;
    private Random rgen = new Random();

    public ServerGame(String s, GameServer gameServer){
        super(s);
        this.gameServer = gameServer;
        this.addMouseListener(this);
        this.paintAll(this.getGraphics());
    }

    @Override
    public void runGame(){
        //TODO: Set to send ball and paddle1 locations to user
        //TODO: Set up to receive paddle2 locations from user

        try {
            Socket clientSocket = gameServer.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            this.startBall();

            Repainter repainter = new Repainter();
            repainter.run(this);

            String readLine;
            while(!((readLine = in.readLine()).equals("exit"))) {
                this.board.setPaddle2(Integer.parseInt(readLine));
                this.moveBall();
                try {
                    this.board.setPaddle1(this.getMousePosition().y);
                } catch (NullPointerException e) {
                    // Doesn't have focus
                }
                out.println(this.board.getBallx() + "," + this.board.getBally() + "," + this.board.getPaddle2y());
                repainter.run(this);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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

    public GameServer getGameServer() {
        return gameServer;
    }

    public void setGameServer(GameServer gameServer) {
        this.gameServer = gameServer;
    }
}
