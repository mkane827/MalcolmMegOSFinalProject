import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;

/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 4/28/13
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameServer extends ServerSocket {

    PrintWriter out;
    BufferedReader in;

    public GameServer(int port) throws IOException {
        super(port);
    }
}
