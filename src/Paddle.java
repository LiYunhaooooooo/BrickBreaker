import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


//paddle moves within 0 to 2/3 of width
class Paddle {
	public int x,y;
	public int width,height;
	public int speed = 0;
	public int speedConstant = 1;
	public int direction = 0; // 0 for left-right , 1 for up - down
	public KeyAdapter keyMove;
	public KeyAdapter keyStop;
	int low,high,left,right;
	Paddle(int low,int high,int left,int right){
		this.low = low;
		this.high = high;
		this.left = left;
		this.right = right;
		keyMove = new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_LEFT) {
					speed = -speedConstant;
					direction = 0;
				}
				if(ke.getKeyCode() == KeyEvent.VK_RIGHT) {
					speed = speedConstant;
					direction = 0;
				}
				if(ke.getKeyCode() == KeyEvent.VK_UP) {
					speed = -speedConstant;
					direction = 1;
				}
				if(ke.getKeyCode() == KeyEvent.VK_DOWN) {
					speed = speedConstant;
					direction = 1;
				}
			}
		};
		
		keyStop = new KeyAdapter() {
			public void keyReleased(KeyEvent ke){
				speed = 0;
			}
		};
	}
	public void paddleMove() {
		if(direction == 0) {
			x = Math.max(left,x + speed);
			x = Math.min(right - width, x + speed);
		}
		else {
			y = Math.max(low,y + speed);
			y = Math.min(high - height, y + speed);			
		}
	}
	public void update() {
		paddleMove();
	}
	public void setPosition(int px,int py) {
		x = px;
		y = py;
	}
	public void setSize(int w,int h) {
		width = w;
		height = h;
	}
	public void setSpeed(int s) {
		speedConstant = s;
	}
}
