import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * @author Himanshu Mehta, Serena Pascual and Cherie Sew
 * Pop up new panel for current time
 *
 */

public class TimePromptView {
	private JPanel panel = new JPanel();
	
	public TimePromptView() {
		
		final JFrame frame = new JFrame();
		
		
		final JButton standardButton = new JButton("STANDARD TIME");
		final JButton militaryButton = new JButton("MILITARY TIME");

		standardButton.setOpaque(true);
		standardButton.setBorderPainted(false);
		standardButton.setFocusable(false);
		standardButton.setBackground(new Color(236, 151, 31));
		standardButton.setForeground(Color.WHITE);
		standardButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		/**
		 * Strategy Pattern
		 * Anonymous class for standard time
		 */
		standardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final DateFormatter format = new TimeFormatter();
				displayStandard(format.formatDate());
			}
		});

		militaryButton.setOpaque(true);
		militaryButton.setBorderPainted(false);
		militaryButton.setFocusable(false);
		militaryButton.setBackground(new Color(68, 157, 68));
		militaryButton.setForeground(Color.WHITE);
		militaryButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		/**
		 * Strategy Pattern
		 * Anonymous class for military time
		 */
		militaryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final DateFormatter format = new MilitaryTimeFormatter();
				displayMilitary(format.formatDate());
			}
		});
		
		panel.add(standardButton);
		panel.add(militaryButton);
		panel.setPreferredSize(new Dimension(350, 250));
		frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        
        
	}
	
	/**
	 * Strategy Pattern for Military Time
	 * @param date
	 */
	 private void displayMilitary(String date)
	    {
	 		JPanel newPanel = new JPanel(new BorderLayout());		
	 		JTextArea area = new JTextArea();
	 		area.setText(date);
	 		
	 		newPanel.add(area, BorderLayout.NORTH);
	 		
	 		panel.add(newPanel, BorderLayout.WEST);
	 		panel.revalidate();
	 		panel.repaint();
	 	}
	    

	 /**
	  * Strategy Pattern for Standard Time	
	  * @param date
	  */
	 private void displayStandard(String date)
	 	{
	 		JPanel newPanel = new JPanel(new BorderLayout());	 		
	 		JTextArea area = new JTextArea(date); 		
	 		
	 		newPanel.add(area, BorderLayout.NORTH);
	 	 		
	 		panel.add(newPanel, BorderLayout.WEST);
	 		panel.revalidate();
	 		panel.repaint();
	 	}

	
}