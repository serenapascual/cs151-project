import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controller extends JPanel{

	public Controller(final EventModel model){
		JButton createButton = new JButton("CREATE");
		JButton previous = new JButton("<");
		JButton next = new JButton(">");
		JButton previousMonth = new JButton("<<");
		JButton nextMonth = new JButton(">>");
		JButton quit = new JButton("QUIT");
		JButton load = new JButton("LOAD EVENTS");
		JButton today = new JButton("TODAY");

		today.setFocusable(false);
		today.setBackground(new Color(239, 98, 95));
		today.setForeground(Color.WHITE);
		today.setFont(new Font("Tahoma", Font.BOLD, 14));
		today.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calendar cal = new GregorianCalendar();
				//				System.out.println(cal.get(Calendar.DATE));
				//				System.out.println(cal.get(Calendar.DAY_OF_MONTH));
				model.setToday(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH));
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

		previousMonth.setFocusable(false);
		previousMonth.setBackground(new Color(241,241,241));
		previousMonth.setForeground(Color.BLACK);
		previousMonth.setFont(new Font("Tahoma", Font.BOLD, 18));

		previousMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.previousMonth();
			}
		});

		nextMonth.setFocusable(false);
		nextMonth.setBackground(new Color(241,241,241));
		nextMonth.setForeground(Color.BLACK);
		nextMonth.setFont(new Font("Tahoma", Font.BOLD, 18));

		nextMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.nextMonth();
			}
		});

		quit.setFocusable(false);
		quit.setBackground(new Color(68, 133, 244));
		quit.setForeground(Color.WHITE);
		quit.setFont(new Font("Tahoma", Font.BOLD, 14));

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.quit();
				System.exit(0);
			}
		});

		load.setFocusable(false);
		load.setBackground(new Color(239, 98, 95));
		load.setForeground(Color.WHITE);
		load.setFont(new Font("Tahoma", Font.BOLD, 14));
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				JFileChooser inputFile = new JFileChooser();
				inputFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (inputFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					File file = inputFile.getSelectedFile();

					try
					{
						Scanner in = new Scanner(file);
						while (in.hasNextLine())
						{
							model.populateEvents(in.nextLine());
						}
					}
					catch (FileNotFoundException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//av.setText("Uploading events from file into calendar...");
				}
			}
		});

		add(today);
		add(createButton);
		add(previousMonth);
		add(previous);
		add(next);
		add(nextMonth);
		setBackground(Color.white);
		add(load);
		add(quit);
	}
}

