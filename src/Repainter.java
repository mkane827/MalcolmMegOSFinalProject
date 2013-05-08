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

    public Repainter(Container container) {
        this.container = container;
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(30);
                container.paintAll(container.getGraphics());
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
