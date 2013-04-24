import acm.graphics.GOval;
import acm.program.GraphicsProgram;

import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 4/24/13
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pong extends GraphicsProgram {

    private int screenWidth = 500;
    private int screenHeight = 500;

    public MouseEvent mouseEvent;

    public void run() {

    }

    public void init() {
        setSize(screenWidth, screenHeight);
        super.init();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
