import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 5/8/13
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Repainter extends Thread {

    public void run(Container container) {
        container.paintAll(container.getGraphics());
    }
}
