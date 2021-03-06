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
import java.net.ServerSocket;
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

    private ServerSocket gameServer;
    private Socket gameSocket;

    private GameBoard board;
    private JPanel buttonPanel;

    /**
     * Creates the setup screen
     * @param s
     */
    public StartingScreen(String s) {
        super(s);
        setLayout(new BorderLayout());

        buttonPanel = new JPanel();
        this.getContentPane().setBackground(Color.YELLOW);
        buttonPanel.setLayout(new FlowLayout());
        addButton(START_SERVER, buttonPanel);
        addButton(CONNECT, buttonPanel);
        addButton(EXIT, buttonPanel);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Reacts to button being clicked on.
     * START_SERVER: Creates server on port 1234 for user to connect to
     * EXIT: Exits the game
     * CONNECT: Creates fields for user to input IP Address to connect to
     * CONNECT_TO_SERVER: After user puts in IP Address to connect to, connects
     *                      to the server.
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();

        if(action.equals(START_SERVER)) {
            try {
                gameServer = new ServerSocket(1234);
                Label instruction = new Label("Use www.whatsmyip.org to get your ip address");
                this.add(instruction);
                this.paintAll(this.getGraphics());

                this.setVisible(false);
                Pong.startGame(gameServer);

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
            connectPanel.setLayout(new GridLayout(2, 2, 0, 5));
            Label connectToIPLabel = new Label("IP:");
            connectToIPField = new TextField();

            connectPanel.add(connectToIPLabel);
            connectPanel.add(connectToIPField);
            connectPanel.add(new Container()); // Skip cell
            addButton(CONNECT_TO_SERVER, connectPanel);

            this.add(connectPanel, BorderLayout.NORTH);
            this.paintAll(this.getGraphics());
        }
        else if(action.equals(CONNECT_TO_SERVER)) {
            try {
                gameSocket = new Socket(connectToIPField.getText(), 1234);

                this.setVisible(false);
                Pong.startGame(gameSocket);
            } catch (IOException e) {
                System.out.println("Unable to connect to socket");
                System.exit(-1);
            }
        }
    }

    /**
     * Adds button to container
     * @param text to put in the button
     * @param container to add the button to
     */
    private void addButton(String text, Container container) {
        Button newButton = new Button(text);
        container.add(newButton);
        newButton.addActionListener(this);
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