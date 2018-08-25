import java.awt.Color;
import java.awt.Graphics;

public class GamePanel extends Observer {
    private static final int NUM_BRICKS = 1;
    private boolean initial = false;
    private int low, high, left, right;
    private Paddle paddle;
    private Ball ball;
    private Brick[] bricks = new Brick[NUM_BRICKS];

    public void ini() {
        low = left = 0;
        high = this.getHeight();
        right = this.getWidth();
        paddle = new Paddle(low, high, left, right / 3 * 2);
        ball = new Ball(400, 500);
        // Paddle logic
        paddle.setPosition(right / 2, high / 2);
        paddle.setSize(10, 80);

        //put bricks on game panel
        for (int i = 0; i < NUM_BRICKS; i++) {
            int y = (int) (Math.random() * this.getHeight() - 20);
            int x = (int) ((Math.random() / 3 + 2.0 / 3.0) * this.getWidth() - 20);
            bricks[i] = new Brick(x, y);
        }

        this.addKeyListener(paddle.kyeProcessor);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.blue);
        g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
        g.setColor(Color.RED);
        g.fillOval((int) ball.cx - Ball.RADIUS, (int) ball.cy - Ball.RADIUS, Ball.DIAMETER, Ball.DIAMETER);

        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                brick.draw(g);
            }
        }
    }

    public void update() {
        if (!this.initial && this.getWidth() != 0) {
            this.ini();
            this.initial = true;
        }
        ball.update();
        checkforCollision();
        validate();
        repaint();
    }

    private void checkforCollision() {
        for (Brick b : bricks) {
            if (!b.isDestroyed()) {
                //check if the brick overlaps with ball
                float nearestX = Math.max(b.x, Math.min(ball.cx, b.x + Brick.WIDTH));
                float nearestY = Math.max(b.y, Math.min(ball.cy, b.y + Brick.HEIGHT));

                float dx = ball.cx - nearestX;
                float dy = ball.cy - nearestY;
                if ((dx * dx + dy * dy) < (Ball.RADIUS * Ball.RADIUS)) {
                    b.setDestroyed();
                    System.out.println("Overlap");
                }
            }
        }
    }

}