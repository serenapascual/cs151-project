import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Controller extends JPanel{
	
	public Controller(){
		JButton createButton = new JButton("Create");
		JButton previous = new JButton("<");
		JButton next = new JButton(">");
		JButton quit = new JButton("Quit");
		createButton.setFocusable(false);
		previous.setFocusable(false);
		next.setFocusable(false);
		quit.setFocusable(false);
		add(createButton);
		add(previous);
		add(next);
		setBackground(Color.white);
		add(quit);
	}
}
