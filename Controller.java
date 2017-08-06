import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * 
 * @author Himanshu Mehta, Serena Pascual and Cherie Sew
 *
 */
public class Controller extends JPanel{

	private static final long serialVersionUID = 1L;

	/**
	 * Controller part of MVC for calendar program 
	 * @param model - MVC model for list of events
	 */
	public Controller(final EventModel model){
		setBackground(Color.white);
		setLayout(new BorderLayout());

		final JPanel upper = new JPanel();
		//	JPanel lower = new JPanel();
		upper.setBackground(Color.white);
		//	lower.setBackground(Color.white);

		final JButton today = new JButton("TODAY");
		final JButton create = new JButton("CREATE");
		final JButton previous = new JButton("<");
		final JButton next = new JButton(">");
		//		JButton previousMonth = new JButton("<<");
		//		JButton nextMonth = new JButton(">>");
		final JButton quit = new JButton("QUIT");
		final JButton load = new JButton("LOAD EVENTS FROM FILE");
		final JButton day = new JButton("DAY");
		final JButton week = new JButton("WEEK");
		final JButton month = new JButton("MONTH");
		final JButton agenda = new JButton("AGENDA");
		final JButton currentTime = new JButton("CURRENT TIME");
		final Calendar cal = Calendar.getInstance();

		today.setOpaque(true);
		today.setBorderPainted(false); // makes color bg show properly on Mac
		today.setFocusable(false);
		today.setBackground(new Color(239, 98, 95));
		today.setForeground(Color.WHITE);
		today.setFont(new Font("Tahoma", Font.BOLD, 14));
		today.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calendar cal = new GregorianCalendar();
				model.setToday(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
			}
		});
		
		create.setOpaque(true);
		create.setBorderPainted(false);
		create.setFocusable(false);
		create.setBackground(new Color(239, 98, 95));
		create.setForeground(Color.WHITE);
		create.setFont(new Font("Tahoma", Font.BOLD, 14));
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreatePromptView view = new CreatePromptView(model);
			}
		});
		
		previous.setOpaque(true);
		previous.setBorderPainted(false);
		previous.setFocusable(false);
		previous.setBackground(new Color(241,241,241));
		previous.setForeground(Color.BLACK);
		previous.setFont(new Font("Tahoma", Font.BOLD, 18));

		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.previousDay();
			}
		});

		next.setOpaque(true);
		next.setBorderPainted(false);
		next.setFocusable(false);
		next.setBackground(new Color(241,241,241));
		next.setForeground(Color.BLACK);
		next.setFont(new Font("Tahoma", Font.BOLD, 18));

		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.nextDay();
			}
		});

		//		previousMonth.setFocusable(false);
		//		previousMonth.setBackground(new Color(241,241,241));
		//		previousMonth.setForeground(Color.BLACK);
		//		previousMonth.setFont(new Font("Tahoma", Font.BOLD, 18));
		//
		//		previousMonth.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent arg0) {
		//				model.previousMonth();
		//			}
		//		});
		//
		//		nextMonth.setFocusable(false);
		//		nextMonth.setBackground(new Color(241,241,241));
		//		nextMonth.setForeground(Color.BLACK);
		//		nextMonth.setFont(new Font("Tahoma", Font.BOLD, 18));
		//
		//		nextMonth.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent arg0) {
		//				model.nextMonth();
		//			}
		//		});

		quit.setOpaque(true);
		quit.setBorderPainted(false);
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

		load.setOpaque(true);
		load.setBorderPainted(false);
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

		day.setOpaque(true);
		day.setBorderPainted(false);
		day.setFocusable(false);
		day.setBackground(new Color(48, 48, 48));
		day.setForeground(Color.WHITE);
		day.setFont(new Font("Tahoma", Font.BOLD, 14));
		day.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setViewType(EventModel.ViewTypes.DAY);
				model.getView().drawDayEvents();
			}
		});

		week.setOpaque(true);
		week.setBorderPainted(false);
		week.setFocusable(false);
		week.setBackground(new Color(48, 48, 48));
		week.setForeground(Color.WHITE);
		week.setFont(new Font("Tahoma", Font.BOLD, 14));
		week.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setViewType(EventModel.ViewTypes.WEEK);
				model.getView().drawWeekEvents();
			}
		});

		month.setOpaque(true);
		month.setBorderPainted(false);
		month.setFocusable(false);
		month.setBackground(new Color(48, 48, 48));
		month.setForeground(Color.WHITE);
		month.setFont(new Font("Tahoma", Font.BOLD, 14));
		month.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setViewType(EventModel.ViewTypes.MONTH);
				model.getView().drawMonthEvents();
			}
		});

		agenda.setOpaque(true);
		agenda.setBorderPainted(false);
		agenda.setFocusable(false);
		agenda.setBackground(new Color(48, 48, 48));
		agenda.setForeground(Color.WHITE);
		agenda.setFont(new Font("Tahoma", Font.BOLD, 14));
		agenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setViewType(EventModel.ViewTypes.AGENDA);
				AgendaPromptView view = new AgendaPromptView(model);
			}
		});
		
		currentTime.setOpaque(true);
		currentTime.setBorderPainted(false);
		currentTime.setFocusable(false);
		currentTime.setBackground(new Color(48, 48, 48));
		currentTime.setForeground(Color.WHITE);
		currentTime.setFont(new Font("Tahoma", Font.BOLD, 14));
		currentTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TimePromptView view = new TimePromptView();
				
			}
		});
		

		upper.add(today);
		//		upper.add(previousMonth);
		upper.add(previous);
		upper.add(next);	
		//	upper.add(Box.createRigidArea(new Dimension(350,0)));

		upper.add(create);
		//		upper.add(nextMonth);
		upper.add(load);
		//	upper.add(create);
		
		upper.add(day);
		upper.add(week);
		upper.add(month);
		upper.add(agenda);
		upper.add(currentTime);
		
		upper.add(quit);
		upper.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		add(upper, BorderLayout.NORTH);
		//	add(lower, BorderLayout.SOUTH);
	}
}
