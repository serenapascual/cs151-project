import java.awt.*;
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
		setBackground(Color.white);
		setLayout(new BorderLayout());
		
		JPanel upper = new JPanel();
		JPanel lower = new JPanel();
		upper.setBackground(Color.white);
		lower.setBackground(Color.white);
		
		JButton today = new JButton("TODAY");
		JButton create = new JButton("CREATE");
		JButton previous = new JButton("<");
		JButton next = new JButton(">");
		JButton previousMonth = new JButton("<<");
		JButton nextMonth = new JButton(">>");
		JButton quit = new JButton("QUIT");
		JButton load = new JButton("LOAD EVENTS");
		JButton day = new JButton("DAY");
		JButton week = new JButton("WEEK");
		JButton month = new JButton("MONTH");
		JButton agenda = new JButton("AGENDA");

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

		create.setFocusable(false);
		create.setBackground(new Color(239, 98, 95));
		create.setForeground(Color.WHITE);
		create.setFont(new Font("Tahoma", Font.BOLD, 14));
		create.addActionListener(new ActionListener() {
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
		
		
		day.setFocusable(false);
		day.setBackground(new Color(48, 48, 48));
		day.setForeground(Color.WHITE);
		day.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		week.setFocusable(false);
		week.setBackground(new Color(48, 48, 48));
		week.setForeground(Color.WHITE);
		week.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		
		month.setFocusable(false);
		month.setBackground(new Color(48, 48, 48));
		month.setForeground(Color.WHITE);
		month.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		agenda.setFocusable(false);
		agenda.setBackground(new Color(48, 48, 48));
		agenda.setForeground(Color.WHITE);
		agenda.setFont(new Font("Tahoma", Font.BOLD, 14));

		upper.add(today);
		upper.add(create);
		upper.add(previousMonth);
		upper.add(previous);
		upper.add(next);
		upper.add(nextMonth);
		upper.add(load);
		upper.add(create);
		upper.add(quit);
		lower.add(day);
		lower.add(week);
		lower.add(month);
		lower.add(agenda);
		
		add(upper, BorderLayout.NORTH);
		add(lower, BorderLayout.SOUTH);
	}
}

