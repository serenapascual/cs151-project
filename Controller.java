import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Controller extends JPanel{
	
	public Controller(){
		JButton createButton = new JButton("CREATE");
		JButton previous = new JButton("<");
		JButton next = new JButton(">");
		JButton quit = new JButton("QUIT");
		createButton.setFocusable(false);
		createButton.setBackground(new Color(239, 98, 95));
		createButton.setForeground(Color.WHITE);
		createButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		previous.setFocusable(false);
		previous.setBackground(new Color(241,241,241));
		previous.setForeground(Color.BLACK);
		previous.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		next.setFocusable(false);
		next.setBackground(new Color(241,241,241));
		next.setForeground(Color.BLACK);
		next.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		quit.setFocusable(false);
		quit.setBackground(new Color(68, 133, 244));
		quit.setForeground(Color.WHITE);
		quit.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		add(createButton);
		add(previous);
		add(next);
		setBackground(Color.white);
		add(quit);
	}
}
