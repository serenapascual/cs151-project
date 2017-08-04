import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.*;
import javax.swing.border.*;

/**
 * 
 * @author Himanshu Mehta, Serena Pascual and Cherie Sew
 *
 */
public class MainView {
	private EventModel model;
	private final Calendar cal;
	private final JLabel monthLabel = new JLabel();
	private final JPanel monthPanel;
	private final JPanel eventsPanel;
	private final JLabel eventsLabel = new JLabel();

	public static enum DAYS_OF_WEEK {
		Sun, Mon, Tue, Wed, Thu, Fri, Sat
	};
	
	public MainView(final EventModel model) {
		this.model = model;
		this.cal = model.getCal();
		Controller c = new Controller(model);
		c.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
		JFrame frame = new JFrame();
		JPanel month = new JPanel();
		JButton previousMonth = new JButton("<<");
		JButton nextMonth = new JButton(">>");
		
		previousMonth.setOpaque(true);
		previousMonth.setBorderPainted(false); // makes color bg show properly on Mac
		previousMonth.setFocusable(false);
		previousMonth.setBackground(new Color(241,241,241));
		previousMonth.setForeground(Color.BLACK);
		previousMonth.setFont(new Font("Tahoma", Font.BOLD, 16));

		previousMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.previousMonth();
			}
		});

		nextMonth.setOpaque(true);
		nextMonth.setBorderPainted(false);
		nextMonth.setFocusable(false);
		nextMonth.setBackground(new Color(241,241,241));
		nextMonth.setForeground(Color.BLACK);
		nextMonth.setFont(new Font("Tahoma", Font.BOLD, 16));

		nextMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.nextMonth();
			}
		});

		monthPanel = new JPanel();
        monthPanel.setLayout(new GridLayout(0, 7, 10, 10));
		JPanel monthWrap = new JPanel();
		monthWrap.setLayout(new BorderLayout());
		monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		month.add(previousMonth, BorderLayout.EAST);
		month.add(monthLabel, BorderLayout.CENTER);
		month.add(nextMonth, BorderLayout.WEST);
		monthWrap.add(month, BorderLayout.NORTH);
		monthWrap.add(monthPanel, BorderLayout.CENTER);
		monthWrap.setSize(new Dimension(400,400));
		monthWrap.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); 
		drawMonth(monthPanel);

		JScrollPane scroll = new JScrollPane();
		eventsPanel = new JPanel();
		//dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.PAGE_AXIS));
		eventsPanel.setLayout(new BorderLayout());
		eventsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		eventsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
		eventsPanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(7.0f)));
		eventsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		drawDayEvents();
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.getViewport().add(eventsPanel);
		scroll.setPreferredSize(new Dimension(500, 300));
		scroll.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		frame.add(c, BorderLayout.NORTH);
		frame.add(monthWrap, BorderLayout.WEST);
		frame.add(scroll, BorderLayout.EAST);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public void repaint() {
		monthPanel.removeAll();
		drawMonth(monthPanel);
		monthPanel.revalidate();
		monthPanel.repaint();
		eventsPanel.removeAll();
		if (model.getViewType().equals(EventModel.ViewTypes.DAY)) drawDayEvents();
		else if (model.getViewType().equals(EventModel.ViewTypes.WEEK)) drawWeekEvents();
		else if (model.getViewType().equals(EventModel.ViewTypes.MONTH)) drawMonthEvents();
		else if (model.getViewType().equals(EventModel.ViewTypes.AGENDA)) drawAgendaEvents();
		eventsPanel.revalidate();
		eventsPanel.repaint();
	}
	
	public void repaintMonth() {
		monthPanel.removeAll();
		drawMonth(monthPanel);
		monthPanel.revalidate();
		monthPanel.repaint();
	}

	private void drawMonth(JPanel monthPanel) {
		//Gets the month and the year and sets it on the top of the month view
		monthLabel.setText(new SimpleDateFormat("MMMM yyyy").format(cal.getTime()));
		monthLabel.setFont(new Font("Tahoma", Font.BOLD, 24));

		//Add Week Labels at top of Month View
		for (int i = 0; i<7; i++) {
			JLabel day = new JLabel("" + DAYS_OF_WEEK.values()[i], SwingConstants.CENTER);
			day.setBorder(new CompoundBorder(day.getBorder(), new EmptyBorder(5, 5, 5, 5)));
			day.setFont(new Font("Tahoma", Font.BOLD, 18));
			if(i == 0 || i == 6) {
				day.setForeground(Color.RED);
			}
			monthPanel.add(day);
		}

		//Adds the days in the month to the JLabel day 
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		Calendar getStart = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		int startDay = getStart.get(Calendar.DAY_OF_WEEK);
		for (int i = 1; i<daysInMonth+startDay; i++) {
			if (i < startDay) {
				final JLabel day = new JLabel("");
				monthPanel.add(day);
			} else {
				int dayNumber = i-startDay+1;
				final JLabel day = new JLabel(dayNumber + "", SwingConstants.CENTER);
				day.addMouseListener(new MouseListener() {
					public void mouseClicked(MouseEvent e) {
						int num = Integer.parseInt(day.getText());
						model.setDay(num);
					}
					public void mousePressed(MouseEvent e) {}
					public void mouseReleased(MouseEvent e) {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
				});
				if (dayNumber == cal.get(Calendar.DAY_OF_MONTH)) {
					day.setBorder(BorderFactory.createLineBorder(Color.black));
				}
				day.setFont(new Font("Tahoma", Font.BOLD, 16));
				//Adds the JLabel to the JPanel
				monthPanel.add(day);
			}
		}
	}

	public void drawDayEvents() {
		eventsPanel.removeAll();

		eventsLabel.setText(new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime()));
		eventsLabel.setBackground(Color.white);
		eventsLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		ArrayList<JTextField> fieldList = new ArrayList<>();
		JPanel dayHolder = new JPanel(); 
		dayHolder.setLayout(new BoxLayout(dayHolder, BoxLayout.PAGE_AXIS));

		for(int i = 0 ; i < 48; i++) {
			JTextField eventField = new JTextField();
			eventField.setEditable(false);
			eventField.setFont(new Font("Tahoma", Font.BOLD, 14));
			dayHolder.add(eventField);
			fieldList.add(eventField);
		}
		
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.PAGE_AXIS));
		for(int i = 0 ; i < 24; i++) {
			JPanel t = new JPanel();
			t.setLayout(new GridLayout(2,1));
			JTextField a = new JTextField(5);
			JTextField b = new JTextField(5);
			String tag = "PM";
			int currentHour = i;
			if(currentHour < 12) tag = "AM";
			if (currentHour == 0) currentHour += 12;
			if (currentHour > 12) currentHour -= 12;
			a.setText(currentHour + ":00"+ tag );
			a.setEditable(false);
			b.setText(currentHour + ":30"+ tag );
			b.setEditable(false);
			a.setFont(new Font("Tahoma", Font.BOLD, 14));
			b.setFont(new Font("Tahoma", Font.BOLD, 14));
			t.add(a);
			t.add(b);
			timePanel.add(t);
		}
		eventsPanel.add(eventsLabel, BorderLayout.NORTH);
		eventsPanel.add(timePanel, BorderLayout.WEST);
		eventsPanel.add(dayHolder, BorderLayout.CENTER);
		ArrayList<Event> events = model.getEvents();
		for (Event e : events) {
			if (e.getDay() == cal.get(Calendar.DAY_OF_MONTH)
					&& (e.getMonth() == cal.get(Calendar.MONTH) + 1) 
					&& (e.getYear() == cal.get(Calendar.YEAR))) {
				Date startDate = e.getStart().getTime();
				Date endDate = e.getEnd().getTime();

				SimpleDateFormat sf = new SimpleDateFormat("hh:mm aa");
				SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
				SimpleDateFormat minFormat = new SimpleDateFormat("mm");
				int startHour, startMin, endHour, endMin;
				startHour = Integer.parseInt(hourFormat.format(startDate));
				startMin = Integer.parseInt(minFormat.format(startDate));
				endHour = Integer.parseInt(hourFormat.format(endDate));
				endMin = Integer.parseInt(minFormat.format(endDate));

				int fromPosition = startHour * 2;
				if (startMin >= 30) fromPosition++;
				int toPosition = endHour * 2;
				if (endMin >= 30) toPosition++;

				fieldList.get(fromPosition).setText(e.getTitle() + " starts at " + sf.format(startDate));
				fieldList.get(toPosition).setText(e.getTitle() + " ends at " + sf.format(endDate));
			}
		}
		eventsPanel.revalidate();
		eventsPanel.repaint();
	}
	
	public void drawWeekEvents() {
		eventsPanel.removeAll();
		
		Calendar firstDateOfWeek = (Calendar) cal.clone();
		int differenceFromFirst = 1 - cal.get(Calendar.DAY_OF_WEEK);
		firstDateOfWeek.add(Calendar.DAY_OF_MONTH, differenceFromFirst);
		eventsLabel.setText(new SimpleDateFormat("dd MMMM yyyy").format(firstDateOfWeek.getTime()));
		
		Calendar lastDateOfWeek = (Calendar) cal.clone();
		int differenceFromLast = 7 - cal.get(Calendar.DAY_OF_WEEK);
		lastDateOfWeek.add(Calendar.DAY_OF_MONTH, differenceFromLast);
		eventsLabel.setText(eventsLabel.getText() + " to " + new SimpleDateFormat("dd MMMM yyyy").format(lastDateOfWeek.getTime()));
		
		ArrayList<JTextField> fieldList = new ArrayList<>();
		JPanel weekHolder = new JPanel();
		weekHolder.setLayout(new BoxLayout(weekHolder, BoxLayout.PAGE_AXIS));

		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.PAGE_AXIS));
		
		for(int i = 0 ; i < 7; i++) {
			JTextField eventField = new JTextField(40);
			eventField.setEditable(false);
			eventField.setFont(new Font("Tahoma", Font.BOLD, 14));
			weekHolder.add(eventField);
			fieldList.add(eventField);

			JPanel t = new JPanel();
			t.setLayout(new GridLayout(1,1));
			JTextField dayOfWeek = new JTextField();
			
			Calendar d = (Calendar) firstDateOfWeek.clone();
			firstDateOfWeek.add(Calendar.DAY_OF_MONTH, 1);
			
			dayOfWeek.setText(new SimpleDateFormat("EEE, MMM d").format(d.getTime()));
			dayOfWeek.setFont(new Font("Tahoma", Font.BOLD, 14));
			dayOfWeek.setEditable(false);
			t.add(dayOfWeek);
			timePanel.add(t);
		}
		
		eventsPanel.add(eventsLabel, BorderLayout.NORTH);
		eventsPanel.add(timePanel, BorderLayout.WEST);
		eventsPanel.add(weekHolder, BorderLayout.CENTER);
		
		eventsPanel.revalidate();
		eventsPanel.repaint();
	}
	
	public void drawMonthEvents() {
		eventsPanel.removeAll();
		
		Calendar firstDateOfMonth = (Calendar) cal.clone();
		int differenceFromFirst = 1 - cal.get(Calendar.DAY_OF_MONTH);
		firstDateOfMonth.add(Calendar.DAY_OF_MONTH, differenceFromFirst);
		eventsLabel.setText(new SimpleDateFormat("dd MMMM yyyy").format(firstDateOfMonth.getTime()));
		
		Calendar lastDateOfMonth = (Calendar) cal.clone();
		int totalDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int differenceFromLast = totalDays - cal.get(Calendar.DAY_OF_MONTH);
		lastDateOfMonth.add(Calendar.DAY_OF_MONTH, differenceFromLast);
		eventsLabel.setText(eventsLabel.getText() + " to " + new SimpleDateFormat("dd MMMM yyyy").format(lastDateOfMonth.getTime()));
		eventsPanel.add(eventsLabel, BorderLayout.NORTH);
		
		ArrayList<JTextField> fieldList = new ArrayList<>();
		JPanel monthHolder = new JPanel();
		monthHolder.setLayout(new BoxLayout(monthHolder, BoxLayout.PAGE_AXIS));

		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.PAGE_AXIS));
		
		for(int i = 0; i < totalDays; i++) {
			JTextField eventField = new JTextField(40);
			eventField.setEditable(false);
			eventField.setFont(new Font("Tahoma", Font.BOLD, 14));
			monthHolder.add(eventField);
			fieldList.add(eventField);
			
			JPanel t = new JPanel();
			t.setLayout(new GridLayout(1,1));
			JTextField dayOfMonth = new JTextField();
			
			Calendar d = (Calendar) firstDateOfMonth.clone();
			d.add(Calendar.DAY_OF_MONTH, i);
			
			dayOfMonth.setText(new SimpleDateFormat("EEE, MMM d").format(d.getTime()));
			dayOfMonth.setFont(new Font("Tahoma", Font.BOLD, 14));
			dayOfMonth.setEditable(false);
			t.add(dayOfMonth);
			timePanel.add(t);
		}
		
		eventsPanel.add(eventsLabel, BorderLayout.NORTH);
		eventsPanel.add(timePanel, BorderLayout.WEST);
		eventsPanel.add(monthHolder, BorderLayout.CENTER);
		
		eventsPanel.revalidate();
		eventsPanel.repaint();
	}
	
	public void drawAgendaEvents() {
		eventsPanel.removeAll();
		
		Calendar firstDateOfMonth = (Calendar) cal.clone();
		int differenceFromFirst = 1 - cal.get(Calendar.DAY_OF_MONTH);
		firstDateOfMonth.add(Calendar.DAY_OF_MONTH, differenceFromFirst);
		eventsLabel.setText(new SimpleDateFormat("dd MMMM yyyy").format(firstDateOfMonth.getTime()));
		
		Calendar lastDateOfMonth = (Calendar) cal.clone();
		int totalDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int differenceFromLast = totalDays - cal.get(Calendar.DAY_OF_MONTH);
		lastDateOfMonth.add(Calendar.DAY_OF_MONTH, differenceFromLast);
		eventsLabel.setText(eventsLabel.getText() + " to " + new SimpleDateFormat("dd MMMM yyyy").format(lastDateOfMonth.getTime()));
		eventsPanel.add(eventsLabel, BorderLayout.NORTH);
		
		ArrayList<JTextField> fieldList = new ArrayList<>();
		JPanel monthHolder = new JPanel();
		monthHolder.setLayout(new BoxLayout(monthHolder, BoxLayout.PAGE_AXIS));

		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.PAGE_AXIS));
		
		for(int i = 0; i < totalDays; i++) {
			JTextField eventField = new JTextField(40);
			eventField.setEditable(false);
			eventField.setFont(new Font("Tahoma", Font.BOLD, 14));
			monthHolder.add(eventField);
			fieldList.add(eventField);
			
			JPanel t = new JPanel();
			t.setLayout(new GridLayout(1,1));
			JTextField dayOfMonth = new JTextField();
			
			Calendar d = (Calendar) firstDateOfMonth.clone();
			d.add(Calendar.DAY_OF_MONTH, i);
			
			dayOfMonth.setText(new SimpleDateFormat("EEE, MMM d").format(d.getTime()));
			dayOfMonth.setFont(new Font("Tahoma", Font.BOLD, 14));
			dayOfMonth.setEditable(false);
			t.add(dayOfMonth);
			timePanel.add(t);
		}
		
		eventsPanel.add(eventsLabel, BorderLayout.NORTH);
		eventsPanel.add(timePanel, BorderLayout.WEST);
		eventsPanel.add(monthHolder, BorderLayout.CENTER);
		
		eventsPanel.revalidate();
		eventsPanel.repaint();
	}
}