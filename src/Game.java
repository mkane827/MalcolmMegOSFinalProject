import javax.swing.*;
import java.awt.*;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 4/26/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class Game extends JFrame implements ActionListener, WindowListener{

    private static final int SIZE = 500;


    private GameServer gameServer;

    private GameBoard board;

    private Timer myTimer;

    public Game(String s, boolean server) {
        super(s);
        setLayout(new BorderLayout());
        this.setSize(SIZE+20,SIZE+20);
        board = new GameBoard(SIZE, SIZE);
        add(board);
        if(server){
            this.runGameAsServer();
        }
        else{
            this.runGameAsUser();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        
    }


    private void addButton(String text, Container container) {
        Button newButton = new Button(text);
        container.add(newButton);
        newButton.addActionListener(this);
    }


    public GameServer getGameServer() {
        return gameServer;
    }

    public void setGameServer(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void runGameAsServer(){
        System.out.println("here");


        ActionListener timeStepListener = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                moveBall();
            }
        };
        this.myTimer = new Timer(10, timeStepListener);
        this.myTimer.start();

    }

    public void runGameAsUser(){
        board = new GameBoard(SIZE, SIZE);
        repaint();
    }

    public void moveBall(){
        board.moveBall(1,1);
    }
}
