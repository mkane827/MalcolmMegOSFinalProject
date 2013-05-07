import javax.swing.*;
import java.awt.*;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 4/26/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class StartingScreen extends JFrame implements ActionListener, WindowListener{

    private final String START_SERVER = "Start Server";
    private final String EXIT = "Exit";
    private final String CONNECT = "Connect";
    private final String CONNECT_TO_SERVER = "Connect to Server";

    private TextField connectToIPField;
    private TextField connectToPortField;

    private GameServer gameServer;
    private Socket gameSocket;

    private GameBoard board;
    private JPanel buttonPanel;

    private Timer myTimer;

    public StartingScreen(String s) {
        super(s);
        setLayout(new BorderLayout());

        buttonPanel = new JPanel();
        this.getContentPane().setBackground(Color.YELLOW);
        buttonPanel.setLayout(new FlowLayout());
        addButton(START_SERVER, buttonPanel);
        addButton(CONNECT, buttonPanel);
        addButton(EXIT, buttonPanel);
        addButton("PLAY", buttonPanel);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();
        if(action.equals("PLAY")){
            this.setVisible(false);
            Pong.startGame(true);
        }

        else if(action.equals(START_SERVER)) {
            try {
                gameServer = new GameServer(1234);
                Label instruction = new Label("Us www.whatsmyip.org to get your ip address. Game is on port 1234.");
                this.add(instruction);
                this.paintAll(this.getGraphics());
                Socket clientSocket = gameServer.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String readLine;
                while(!(readLine = in.readLine()).equals("exit")) {
                    System.out.println("read: " + readLine);
                }
                // Testing stuff
                this.removeAll();

                this.setVisible(false);
                Pong.startGame(true);

            }
            catch (IOException e) {
                System.out.println("Could not listen on port: 1234");
                System.exit(-1);
            }
        }

        else if(action.equals(EXIT)) {
            System.exit(0);
        }

        else if(action.equals(CONNECT)) {
            Panel connectPanel = new Panel();
            connectPanel.setLayout(new GridLayout(3, 2, 0, 5));
            Label connectToIPLabel = new Label("IP:");
            connectToIPField = new TextField();
            Label connectToPortLabel = new Label("Port:");
            connectToPortField = new TextField();

            connectPanel.add(connectToIPLabel);
            connectPanel.add(connectToIPField);
            connectPanel.add(connectToPortLabel);
            connectPanel.add(connectToPortField);
            connectPanel.add(new Container()); // Skip cell
            addButton(CONNECT_TO_SERVER, connectPanel);

            this.add(connectPanel, BorderLayout.NORTH);
            this.paintAll(this.getGraphics());
        }
        else if(action.equals(CONNECT_TO_SERVER)) {
            System.out.println(connectToIPField.getText());
            System.out.println(connectToPortField.getText());
            try {
                gameSocket = new Socket(connectToIPField.getText(), Integer.parseInt(connectToPortField.getText()));
                System.out.println("Writing 1");
                PrintWriter out = new PrintWriter(gameSocket.getOutputStream(), true);
                out.println("Hello");
                out.write("exit");
                this.removeAll();

                this.setVisible(false);
                Pong.startGame(false);
            } catch (IOException e) {
                System.out.println("Unable to connect to socket");
                System.exit(-1);
            }
            System.exit(0);
        }
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
}