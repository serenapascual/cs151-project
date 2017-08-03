import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class Controller extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Controller(final EventModel model){
		JPanel day = new JPanel();
		JPanel views = new JPanel();
		JPanel quitPanel = new JPanel();
		JButton createButton = new JButton("CREATE");
		JButton previous = new JButton("<");
		JButton next = new JButton(">");
		JButton quit = new JButton("QUIT");
		JButton load = new JButton("LOAD EVENTS");
		JButton today = new JButton("TODAY");
		JButton dayView = new JButton("Day");
		JButton monthView = new JButton("Month");
		JButton weekView = new JButton("Week");
		JButton agenda = new JButton("Agenda");
		Calendar cal = Calendar.getInstance();


		today.setFocusable(false);
		today.setBackground(new Color(239, 98, 95));
		today.setForeground(Color.WHITE);
		today.setFont(new Font("Tahoma", Font.BOLD, 14));
		today.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calendar cal = new GregorianCalendar();
				cal.getInstance();
				//				System.out.println(cal.get(Calendar.DATE));
				//				System.out.println(cal.get(Calendar.DAY_OF_MONTH));
				model.setToday(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
			}
		});

		createButton.setFocusable(false);
		createButton.setBackground(new Color(239, 98, 95));
		createButton.setForeground(Color.WHITE);
		createButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateEventView view = new CreateEventView(model);
			}
		});

		previous.setFocusable(false);
		previous.setBackground(new Color(241,241,241));
		previous.setForeground(Color.BLACK);
		previous.setFont(new Font("Tahoma", Font.BOLD, 18));

		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.previousDay();
			}
		});
		next.setFocusable(false);
		next.setBackground(new Color(241,241,241));
		next.setForeground(Color.BLACK);
		next.setFont(new Font("Tahoma", Font.BOLD, 18));

		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.nextDay();
			}
		});

		quit.setFocusable(false);
		quit.setBackground(new Color(68, 133, 244));
		quit.setForeground(Color.WHITE);
		quit.setFont(new Font("Tahoma", Font.BOLD, 14));

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		load.setFocusable(false);
		load.setBackground(new Color(239, 98, 95));
		load.setForeground(Color.WHITE);
		load.setFont(new Font("Tahoma", Font.BOLD, 14));
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				JFileChooser inputFile = new JFileChooser();
				inputFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (inputFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					File file = inputFile.getSelectedFile();

					try{
						Scanner in = new Scanner(file);
						while (in.hasNextLine()){
							model.populateEvents(in.nextLine());
						}
					}
					catch (FileNotFoundException e1){
						e1.printStackTrace();
					}
				}
				//System.out.println(cal.get(Calendar.DATE));
				model.setToday(cal.get(Calendar.DATE),cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));
			}
		});
		
		day.add(today, BorderLayout.EAST);
		day.add(previous, BorderLayout.CENTER);
		day.add(next, BorderLayout.WEST);
		day.add(createButton, BorderLayout.WEST);
		setBackground(Color.white);
		day.setBackground(Color.white);
		add(day, BorderLayout.EAST);
		views.add(dayView, BorderLayout.EAST);
		views.add(weekView, BorderLayout.CENTER);
		views.add(monthView, BorderLayout.CENTER);
		views.add(agenda, BorderLayout.WEST);
		quitPanel.add(load, BorderLayout.CENTER);
		quitPanel.add(quit, BorderLayout.WEST);
		add(views, BorderLayout.CENTER);
		add(load, BorderLayout.WEST);
		add(quitPanel, BorderLayout.WEST);
	}
}

