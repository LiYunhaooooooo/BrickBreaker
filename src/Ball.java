

class Ball {
    // Box height and width
    int width;
    int height;

    public Ball(int width, int height) {
        this.width = width;
        this.height = height;
    }

    // Ball Size
    public static final int RADIUS = 10;
    public static final int DIAMETER = RADIUS * 2;

    // Center of Call
    float cx = RADIUS + 50;
    float cy = RADIUS + 20;

    // Direction
    float dx = 1.5f;
    float dy = 1.5f;

    public void update() {

        cx = cx + dx;
        cy = cy + dy;
        if (cx - RADIUS < 0) {
            dx = -dx;
            cx = RADIUS;
        } else if (cx + RADIUS > width) {
            dx = -dx;
            cx = width - RADIUS;
        }

        if (cy - RADIUS < 0) {
            dy = -dy;
            cy = RADIUS;
        } else if (cy + RADIUS > height) {
            dy = -dy;
            cy = height - RADIUS;
        }

    }
}
