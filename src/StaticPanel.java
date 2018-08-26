import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class StaticPanel extends Observer {
	private JPanel topPanel, bottomPanel;
	private JLabel tens_clock, units_clock;
	private int counter, tens_time, units_time;
	private JPanel jp;
	int highscore;
	private BufferedImage topPanelImage, bottomPanelImage;

    private BufferedImage resize(BufferedImage img, int width, int height, float opacity) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

	public StaticPanel() {
		
		setLayout(new BorderLayout());
		
		try {
			topPanelImage = ImageIO.read(new File("./src/blue_down_face_clock.jpg"));
			topPanelImage = resize(topPanelImage, 150, 150, (float) 1.0);
			
			bottomPanelImage = ImageIO.read(new File("./src/blue_shield.jpg"));
			bottomPanelImage = resize(bottomPanelImage, 150, 350, (float) 1.0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				counter = 0;

		// Define Panels
		topPanel = new JPanel();
		bottomPanel = new JPanel();

		// Panel Settings
		topPanel.setPreferredSize(new Dimension(100, 150));
		bottomPanel.setPreferredSize(new Dimension(100, 350));
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
		
		topPanel.setLayout(new GridBagLayout());
		
		units_time = 0;
		tens_time = 0;
		tens_clock = new JLabel("0");
		tens_clock.setPreferredSize(new Dimension(30, 40));
		tens_clock.setOpaque(true);
		tens_clock.setForeground(Color.WHITE);
		tens_clock.setBackground(Color.GRAY);
		tens_clock.setHorizontalAlignment(SwingConstants.CENTER);
		tens_clock.setFont(new Font("Serif", Font.PLAIN, 24));

		units_clock = new JLabel("0");
		units_clock.setPreferredSize(new Dimension(30, 40));
		units_clock.setOpaque(true);
		units_clock.setForeground(Color.WHITE);
		units_clock.setBackground(Color.GRAY);
		units_clock.setHorizontalAlignment(SwingConstants.CENTER);
		units_clock.setFont(new Font("Serif", Font.PLAIN, 24));
		
//		topPanel.add(tens_clock);
//		topPanel.add(new Panel());
//		topPanel.add(units_clock);
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File("./src/highscore.txt")));
			try {
				String line = reader.readLine();
				highscore = Integer.parseInt(line.trim());
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update() {
		if (counter++ == 199) {
			if (++units_time == 10) {
				units_time = 0;
				++tens_time;
			}
			counter = 0;
		}
		tens_clock.setText(Integer.toString(tens_time));
		units_clock.setText(Integer.toString(units_time));
		repaint();
	}
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(topPanelImage, 0, 0, this);
        
        g.drawImage(bottomPanelImage, 0, 150, this);
        g.setFont(new Font("Serif", Font.BOLD, 30));
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(tens_time), 52, 80);
        g.drawString(Integer.toString(units_time), 82, 80);
        g.setFont(new Font("Serif", Font.BOLD, 20));        
        g.drawString("BEST", 40, 310);
        g.drawString("TIME", 40, 335);
        g.drawString(Integer.toString(highscore), 55, 360);
        
    }	
}
