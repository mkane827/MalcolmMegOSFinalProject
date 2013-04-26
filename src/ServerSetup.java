import java.awt.*;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 4/26/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerSetup extends Frame implements ActionListener, WindowListener{

    private final String START_SERVER = "Start Server";
    private final String EXIT = "Exit";
    private final String CONNECT = "Connect";
    private final String CONNECT_TO_SERVER = "Connect to Server";

    private TextField connectToIPField;
    private TextField connectToPortField;

    private ServerSocket gameServer;
    private Socket gameSocket;

    public ServerSetup(String s) {
        super(s);
        setBackground(Color.yellow);
        setLayout(new BorderLayout());

        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout());
        addButton(START_SERVER, buttonPanel);
        addButton(CONNECT, buttonPanel);
        addButton(EXIT, buttonPanel);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();
        if(action.equals(START_SERVER)) {
            try {
                gameServer = new ServerSocket(1234);
                Label ipaddress = new Label(gameServer.getLocalSocketAddress().toString());
                this.add(ipaddress);
                this.paintAll(this.getGraphics());
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


    public ServerSocket getGameServer() {
        return gameServer;
    }

    public void setGameServer(ServerSocket gameServer) {
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