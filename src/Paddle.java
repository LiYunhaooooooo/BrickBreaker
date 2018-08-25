import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


//paddle moves within 0 to 2/3 of width
class Paddle { 
	public int x,y;
	public int width,height;
	public int speed = 10;
	public KeyAdapter kyeProcessor;
	Paddle(int low,int high,int left,int right){
		kyeProcessor = new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_LEFT) {
					if(x > left) {
						x = Math.max(left,x - speed);
					}
				}
				if(ke.getKeyCode() == KeyEvent.VK_RIGHT) {
					if(x < right- width) {   
						x = Math.min(right- width, x + speed);
					}
				}
				if(ke.getKeyCode() == KeyEvent.VK_UP) {
					if(y > low) {   
						y = Math.max(low, y - speed);
					}
				}
				if(ke.getKeyCode() == KeyEvent.VK_DOWN) {
					if(y < high - height) {   
						y = Math.min(high- height, y + speed);
					}
				}
			}
		};
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
		speed = s;
	}
}
