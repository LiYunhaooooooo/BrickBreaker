import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Panel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class StaticPanel extends Observer {
	private JPanel topPanel, bottomPanel;
	private JLabel tens_clock, units_clock;
	private int counter, tens_time, units_time;
	private JPanel jp;
	
	public StaticPanel() {
		
		setLayout(new BorderLayout());
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
		
		topPanel.add(tens_clock);
		topPanel.add(new Panel());
		topPanel.add(units_clock);
		
	}
	
	public void update() {
		if (counter++ == 99) {
			if (++units_time == 10) {
				units_time = 0;
				++tens_time;
			}
			counter = 0;
		}
		tens_clock.setText(Integer.toString(tens_time));
		units_clock.setText(Integer.toString(units_time));
	}
}
