import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GamePanel extends Observer {
    private static final int NUM_BRICKS = 5;
    private boolean initial = false;
    private int low, high, left, right;
    private Paddle paddle;
    private Ball ball;
    private Brick[] bricks = new Brick[NUM_BRICKS];

    private BufferedImage image;

    private BufferedImage resize(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public GamePanel() {
        try {
            image = ImageIO.read(new File("./src/water.jpg"));
            image = resize(image, 500, 500);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void ini() {
        low = left = 0;
        high = this.getHeight();
        right = this.getWidth();
        paddle = new Paddle(low, high, left, right / 3 * 2);
        ball = new Ball(500, 500);
        // Paddle logic
        paddle.setPosition(right / 2, high / 2);
        paddle.setSize(10, 80);

        //put bricks on game panel
        for (int i = 0; i < NUM_BRICKS; i++) {
            int y = (int) (Math.random() * this.getHeight() - 20);
            int x = (int) (Math.min(this.getWidth() - 20, (Math.random() / 3 + 2.0 / 3.0) * this.getWidth()));
            bricks[i] = new Brick(x, y);
        }

        this.addKeyListener(paddle.keyMove);
        this.addKeyListener(paddle.keyStop);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (image != null) {
            int x = (getWidth() - image.getWidth()) / 2;
            int y = (getHeight() - image.getHeight()) / 2;
            g.drawImage(image, 0, 0, this);
        }
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
        paddle.update();
        ball.update();
        checkforCollision();
        validate();
        repaint();
    }

    private void checkforCollision() {

        //check for collision with paddle
        float nearestX = Math.max(paddle.x, Math.min(ball.cx, paddle.x + paddle.width));
        float nearestY = Math.max(paddle.y, Math.min(ball.cy, paddle.y + paddle.height));

        float dx = ball.cx - nearestX;
        float dy = ball.cy - nearestY;

        if ((dx * dx + dy * dy) < (Ball.RADIUS * Ball.RADIUS)) {

            if (paddle.speed != 0) {
                if(paddle.direction == 0){
                    ball.cx = ball.cx + 2*paddle.speed;
                }else{
                    ball.cy = ball.cy + 2*paddle.speed;
                }
            }

            float ux = nearestX - ball.cx;
            float uy = -nearestY + ball.cy;
            float vx = ball.dx;
            float vy = -ball.dy;

            double theta = Math.asin((ux * vy - vx * uy) / (Math.sqrt(ux * ux + uy * uy) * Math.sqrt(vx * vx + vy * vy)));

            double nDx = -Math.cos(2 * theta) * ball.dx - Math.sin(2 * theta) * (-ball.dy);
            double nDy = Math.sin(2 * theta) * ball.dx - Math.cos(2 * theta) * (-ball.dy);

            ball.dx = (float) nDx;
            ball.dy = (float) -nDy;
        }

        for (Brick b : bricks) {
            if (!b.isDestroyed()) {
                //check if the brick overlaps with ball
                nearestX = Math.max(b.x, Math.min(ball.cx, b.x + Brick.WIDTH));
                nearestY = Math.max(b.y, Math.min(ball.cy, b.y + Brick.HEIGHT));

                dx = ball.cx - nearestX;
                dy = ball.cy - nearestY;

                if ((dx * dx + dy * dy) < (Ball.RADIUS * Ball.RADIUS)) {
                    b.setDestroyed();
                    float ux = nearestX - ball.cx;
                    float uy = -nearestY + ball.cy;
                    float vx = ball.dx;
                    float vy = -ball.dy;

                    double theta = Math.asin((ux * vy - vx * uy) / (Math.sqrt(ux * ux + uy * uy) * Math.sqrt(vx * vx + vy * vy)));

                    double nDx = -Math.cos(2 * theta) * ball.dx - Math.sin(2 * theta) * (-ball.dy);
                    double nDy = Math.sin(2 * theta) * ball.dx - Math.cos(2 * theta) * (-ball.dy);

                    ball.dx = (float) nDx;
                    ball.dy = (float) -nDy;
                }
            }
        }
    }
}