import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 5/8/13
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Repainter extends Thread {

    Container container;

    public Repainter(Container container){
        super();
        this.container = container;
    }

    public void run() {
        this.container.paintAll(container.getGraphics());
    }
}
