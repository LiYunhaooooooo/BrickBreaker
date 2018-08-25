import java.awt.*;

class Brick {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    public int x, y;

    private boolean isDestroyed = false;

    Brick(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed() {
        isDestroyed = true;
    }

    public void draw(Graphics g){
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

}
