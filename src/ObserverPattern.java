import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;

// Observable - Timer
class MyTimer implements Runnable {

	ArrayList<Observer> observers = new ArrayList<Observer>();
	int counter = 0;
	boolean isRunning = true;
	@Override
	public void run() {
		while (isRunning) {
			for (Observer obs: observers) {
				obs.update();
			}
			counter += 1;
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void register(Observer o) {
		observers.add(o);
	}
	
	public void unregister(int position) {
		observers.remove(position);
	}
}

class Observer extends JPanel {
	public void update() {}
}

public class ObserverPattern {
	public static void main(String[] args) {
		Border blackline = BorderFactory.createLineBorder(Color.black);
		JFrame frame = new JFrame();

		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		StaticPanel sp = new StaticPanel();
		GamePanel gp = new GamePanel();
		sp.setPreferredSize(new Dimension(150,500));
//		sp.setBorder(BorderFactory.createLineBorder(Color.green));

		gp.setPreferredSize(new Dimension(500,500));
		gp.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		frame.add(sp);
		frame.add(gp);
		frame.setLocationRelativeTo(null);
		frame.setSize(650, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MyTimer t = new MyTimer();
		t.register(sp);
		t.register(gp);
		
		new Thread(t).start();
	}
}