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
	private final JScrollPane scrollPane;
	private final JLabel monthLabel = new JLabel();
	private final JPanel monthPanel;
	private final JPanel eventsPanel;
	private final JLabel eventsHeader = new JLabel();

	public static enum DAYS_OF_WEEK {
		Sun, Mon, Tue, Wed, Thu, Fri, Sat
	};
	
	/**
	 * Constructs layout for panel with event information, day, week, month, and agenda view
	 * @param model - MVC model with all the events
	 */
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
		previousMonth.setBackground(new Color(238,238,238));
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
		nextMonth.setBackground(new Color(238,238,238));
		nextMonth.setForeground(Color.BLACK);
		nextMonth.setFont(new Font("Tahoma", Font.BOLD, 16));

		nextMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.nextMonth();
			}
		});

		monthPanel = new JPanel();
        monthPanel.setLayout(new GridLayout(0, 7, 0, 0));
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

		scrollPane = new JScrollPane();
		eventsPanel = new JPanel();
		//dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.PAGE_AXIS));
		eventsPanel.setLayout(new BorderLayout());
		eventsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		eventsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
		eventsPanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(7.0f)));
		eventsHeader.setHorizontalAlignment(SwingConstants.CENTER);
		drawDayEvents();
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.getViewport().add(eventsPanel);
		scrollPane.setPreferredSize(new Dimension(700, 300));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		frame.add(c, BorderLayout.NORTH);
		frame.add(monthWrap, BorderLayout.WEST);
		frame.add(scrollPane, BorderLayout.EAST);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Repaint panel according to the type of view selected
	 */
	public void repaint() {
		monthPanel.removeAll();
		drawMonth(monthPanel);
		monthPanel.revalidate();
		monthPanel.repaint();
		if (model.getViewType().equals(EventModel.ViewTypes.DAY)) drawDayEvents();
		else if (model.getViewType().equals(EventModel.ViewTypes.WEEK)) drawWeekEvents();
		else if (model.getViewType().equals(EventModel.ViewTypes.MONTH)) drawMonthEvents();
		else if (model.getViewType().equals(EventModel.ViewTypes.AGENDA)) drawAgendaEvents(model.getAgendaStart(), model.getAgendaEnd());
	}

	/**
	 * Constructs Calendar View 
	 * @param monthPanel - panel for the calendar
	 */
	private void drawMonth(JPanel monthPanel) {
		//Gets the month and the year and sets it on the top of the month view
		monthLabel.setText(new SimpleDateFormat("MMMM yyyy").format(cal.getTime()));
		monthLabel.setFont(new Font("Tahoma", Font.BOLD, 24));

		//Add Week Labels at top of Month View
		for (int i = 0; i<7; i++) {
			JLabel day = new JLabel("" + DAYS_OF_WEEK.values()[i], SwingConstants.CENTER);
			day.setBorder(new CompoundBorder(day.getBorder(), new EmptyBorder(10, 10, 10, 10)));
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

	/**
	 * Construct Day View - Displays all events happening in a day
	 */
	public void drawDayEvents() {
		eventsPanel.removeAll();
		
		JPanel gridPanel = new JPanel();
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		gridPanel.setLayout(grid);
		
		eventsHeader.setText(new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime()));
		eventsHeader.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		ArrayList<JTextArea> eventTextList = new ArrayList<>();
		ArrayList<JTextArea> timeList = new ArrayList<>();
		
		for(int i = 0; i < 48; i++) {
			JTextArea a = new JTextArea(1, 5);
			a.setBackground(new Color(238, 238, 238));
			a.setBorder(new LineBorder(new Color(184, 207, 229)));
			a.setEditable(false);
			a.setFont(new Font("Tahoma", Font.BOLD, 14));
			String tag = "PM";
			int currentHour = i / 2;
			if(currentHour < 12) tag = "AM";
			if (currentHour == 0) currentHour += 12;
			if (currentHour > 12) currentHour -= 12;
			if (i % 2 == 0) a.setText(currentHour + ":00"+ tag );
			else a.setText(currentHour + ":30" + tag);
			c.gridx = 0;
			c.gridy = i + 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.05;
			gridPanel.add(a, c);
			timeList.add(a);
			
			JTextArea eventText = new JTextArea();
			eventText.setBackground(new Color(238, 238, 238));
			eventText.setBorder(new LineBorder(new Color(184, 207, 229)));
			eventText.setEditable(false);
			eventText.setFont(new Font("Tahoma", Font.BOLD, 14));
			c.gridx = 1;
			c.gridy = i + 1;
			c.weightx = 1.0;
			gridPanel.add(eventText, c);
			eventTextList.add(eventText);
		}
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
				// if the text area is empty, don't add a line break
				if (eventTextList.get(fromPosition).getText().length() == 0) {
					eventTextList.get(fromPosition).append(e.getTitle() + " starts at " + sf.format(startDate));
				}
				// if the text area is not empty, add a line break before the next event
				// and another row to the respective time cell
				else {
					eventTextList.get(fromPosition).append("\n" + e.getTitle() + " starts at " + sf.format(startDate));
					timeList.get(fromPosition).append("\n");
				}
				if (eventTextList.get(toPosition).getText().length() == 0) {
					eventTextList.get(toPosition).append(e.getTitle() + " ends at " + sf.format(endDate));
				}
				else {
					eventTextList.get(toPosition).append("\n" + e.getTitle() + " ends at " + sf.format(endDate));
					timeList.get(fromPosition).append("\n");
				}
			}
		}
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1.0;
		gridPanel.add(eventsHeader, c);
		
		eventsPanel.add(gridPanel, BorderLayout.NORTH);
		eventsPanel.revalidate();
		eventsPanel.repaint();
	}
	
	/**
	 * Constructs Week View - Display all events happening during the week
	 */
	public void drawWeekEvents() {
		eventsPanel.removeAll();
		
		JPanel gridPanel = new JPanel();
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		gridPanel.setLayout(grid);
		
		Calendar firstDateOfWeek = (Calendar) cal.clone();
		int differenceFromFirst = 1 - cal.get(Calendar.DAY_OF_WEEK);
		firstDateOfWeek.add(Calendar.DAY_OF_MONTH, differenceFromFirst);
		eventsHeader.setText(new SimpleDateFormat("dd MMMM yyyy").format(firstDateOfWeek.getTime()));
		
		Calendar lastDateOfWeek = (Calendar) cal.clone();
		int differenceFromLast = 7 - cal.get(Calendar.DAY_OF_WEEK);
		lastDateOfWeek.add(Calendar.DAY_OF_MONTH, differenceFromLast);
		eventsHeader.setText(eventsHeader.getText() + " to " + new SimpleDateFormat("dd MMMM yyyy").format(lastDateOfWeek.getTime()));
		
		ArrayList<JTextArea> eventTextList = new ArrayList<>();
		ArrayList<JTextArea> timeList = new ArrayList<>();

		for(int i = 0; i < 7; i++) {
			JTextArea dayOfWeek = new JTextArea();
			dayOfWeek.setBackground(new Color(238, 238, 238));
			dayOfWeek.setBorder(new LineBorder(new Color(184, 207, 229)));
			dayOfWeek.setEditable(false);
			dayOfWeek.setFont(new Font("Tahoma", Font.BOLD, 14));
			
			Calendar d = (Calendar) firstDateOfWeek.clone();
			firstDateOfWeek.add(Calendar.DAY_OF_MONTH, 1);
			dayOfWeek.setText(new SimpleDateFormat("EEE, MMM d").format(d.getTime()));
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = i + 1;
			c.weightx = 0.05;
			gridPanel.add(dayOfWeek, c);
			timeList.add(dayOfWeek);
			
			JTextArea eventText = new JTextArea();
			eventText.setBackground(new Color(238, 238, 238));
			eventText.setBorder(new LineBorder(new Color(184, 207, 229)));
			eventText.setEditable(false);
			eventText.setFont(new Font("Tahoma", Font.BOLD, 14));
			c.gridx = 1;
			c.gridy = i + 1;
			c.weightx = 1.0;
			gridPanel.add(eventText, c);
			eventTextList.add(eventText);
		}

		ArrayList<Event> events = model.getEvents();
		for (Event e : events) {
			if (e.getWeek() == cal.get(Calendar.WEEK_OF_MONTH)) {
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

				int position = startDate.getDay();
				// if the text area is empty, don't add a line break
				if (eventTextList.get(position).getText().length() == 0) {
					eventTextList.get(position).append(e.getTitle() + " starts at " + sf.format(startDate)
					+ " and ends at " + sf.format(endDate));
				}
				// if the text area is not empty, add a line break before the next event
				// and another row to the respective time cell
				else {
					eventTextList.get(position).append("\n" + e.getTitle() + " starts at " + sf.format(startDate)
					+ " and ends at " + sf.format(endDate));
					timeList.get(position).append("\n");
				}
			}
		}
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1.0;
		gridPanel.add(eventsHeader, c);
		
		eventsPanel.add(gridPanel, BorderLayout.NORTH);
		eventsPanel.revalidate();
		eventsPanel.repaint();
	}
	
	/**
	 * Constructs Month View - Displays Events happening throughout a month 
	 */
	public void drawMonthEvents() {
		eventsPanel.removeAll();
		
		JPanel gridPanel = new JPanel();
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		gridPanel.setLayout(grid);
		
		Calendar firstDateOfMonth = (Calendar) cal.clone();
		int differenceFromFirst = 1 - cal.get(Calendar.DAY_OF_MONTH);
		firstDateOfMonth.add(Calendar.DAY_OF_MONTH, differenceFromFirst);
		eventsHeader.setText(new SimpleDateFormat("dd MMMM yyyy").format(firstDateOfMonth.getTime()));
		
		Calendar lastDateOfMonth = (Calendar) cal.clone();
		int totalDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int differenceFromLast = totalDays - cal.get(Calendar.DAY_OF_MONTH);
		lastDateOfMonth.add(Calendar.DAY_OF_MONTH, differenceFromLast);
		eventsHeader.setText(eventsHeader.getText() + " to " + new SimpleDateFormat("dd MMMM yyyy").format(lastDateOfMonth.getTime()));
		
		ArrayList<JTextArea> eventTextList = new ArrayList<>();
		ArrayList<JTextArea> timeList = new ArrayList<>();

		for(int i = 0; i < totalDays; i++) {
			JTextArea dayOfMonth = new JTextArea();
			Calendar d = (Calendar) firstDateOfMonth.clone();
			d.add(Calendar.DAY_OF_MONTH, i);
			dayOfMonth.setBackground(new Color(238, 238, 238));
			dayOfMonth.setBorder(new LineBorder(new Color(184, 207, 229)));
			dayOfMonth.setText(new SimpleDateFormat("EEE, MMM d").format(d.getTime()));
			dayOfMonth.setFont(new Font("Tahoma", Font.BOLD, 14));
			dayOfMonth.setEditable(false);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = i + 1;
			c.weightx = 0.05;
			gridPanel.add(dayOfMonth, c);
			timeList.add(dayOfMonth);
			
			JTextArea eventText = new JTextArea();
			eventText.setBackground(new Color(238, 238, 238));
			eventText.setBorder(new LineBorder(new Color(184, 207, 229)));
			eventText.setEditable(false);
			eventText.setFont(new Font("Tahoma", Font.BOLD, 14));
			gridPanel.add(eventText);
			c.gridx = 1;
			c.gridy = i + 1;
			c.weightx = 1.0;
			gridPanel.add(eventText, c);
			eventTextList.add(eventText);
		}

		ArrayList<Event> events = model.getEvents();

		for (Event e : events) {
			if ((e.getMonth() == cal.get(Calendar.MONTH) + 1)) {
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

				int position = startDate.getDate();
				// if the text area is empty, don't add a line break
				if (eventTextList.get(position).getText().length() == 0) {
					eventTextList.get(position).append(e.getTitle() + " starts at " + sf.format(startDate)
					+ " and ends at " + sf.format(endDate));
				}
				// if the text area is not empty, add a line break before the next event
				// and another row to the respective time cell
				else {
					eventTextList.get(position).append("\n" + e.getTitle() + " starts at " + sf.format(startDate)
					+ " and ends at " + sf.format(endDate));
					timeList.get(position).append("\n");
				}
			}
		}
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1.0;
		gridPanel.add(eventsHeader, c);
		
		eventsPanel.add(gridPanel, BorderLayout.NORTH);
		eventsPanel.revalidate();
		eventsPanel.repaint();
	}
	
	/**
	 * Constructs Agenda View - Displays list of all events happening between two dates
	 * @param start - Starting Date of Agenda
	 * @param end - Ending Date of Agenda
	 */
	public void drawAgendaEvents(Calendar start, Calendar end) {
		eventsPanel.removeAll();
		
		JPanel gridPanel = new JPanel();
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		gridPanel.setLayout(grid);
		
		Calendar startOfAgenda = (Calendar) start.clone();
		startOfAgenda.add(Calendar.MONTH, -1);
		Date dateOfStart = startOfAgenda.getTime();
		// long timeOfStart = dateOfStart.getTime();
		eventsHeader.setText(new SimpleDateFormat("dd MMMM yyyy").format(dateOfStart));
		
		Calendar endOfAgenda = (Calendar) end.clone();
		endOfAgenda.add(Calendar.MONTH, -1);
		Date dateOfEnd = endOfAgenda.getTime();
		// long timeOfEnd = dateOfEnd.getTime();
		eventsHeader.setText(eventsHeader.getText() + " to " + new SimpleDateFormat("dd MMMM yyyy").format(dateOfEnd));
		
		ArrayList<JTextArea> eventTextList = new ArrayList<>();
		ArrayList<JTextArea> timeList = new ArrayList<>();

		ArrayList<Event> agendaEvents = new ArrayList<>();
		for (Event e : model.getEvents()) {
			if (start.before(e.getStart()) && end.after(e.getEnd())) {
				agendaEvents.add(e);
			}
		}
		
		for(int i = 0; i < agendaEvents.size() - 1; i++) {
			JTextArea dayOfMonth = new JTextArea();
			Calendar d = (Calendar) model.getEvents().get(i).getStart().clone();
			dayOfMonth.setBackground(new Color(238, 238, 238));
			dayOfMonth.setBorder(new LineBorder(new Color(184, 207, 229)));
			dayOfMonth.setText(new SimpleDateFormat("EEE, MMM d").format(d.getTime()));
			dayOfMonth.setFont(new Font("Tahoma", Font.BOLD, 14));
			dayOfMonth.setEditable(false);
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = i + 1;
			c.weightx = 0.05;
			gridPanel.add(dayOfMonth, c);
			timeList.add(dayOfMonth);
			
			JTextArea eventText = new JTextArea();
			eventText.setBackground(new Color(238, 238, 238));
			eventText.setBorder(new LineBorder(new Color(184, 207, 229)));
			eventText.setEditable(false);
			eventText.setFont(new Font("Tahoma", Font.BOLD, 14));
			gridPanel.add(eventText);
			c.gridx = 1;
			c.gridy = i + 1;
			c.weightx = 1.0;
			gridPanel.add(eventText, c);
			eventTextList.add(eventText);
		}

		ArrayList<Event> events = model.getEvents();

		for (Event e : agendaEvents) {
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

			int position = startDate.getDate() - 1;
			// if the text area is empty, don't add a line break
			if (eventTextList.get(position).getText().length() == 0) {
				eventTextList.get(position).append(e.getTitle() + " starts at " + sf.format(startDate)
				+ " and ends at " + sf.format(endDate));
			}
			// if the text area is not empty, add a line break before the next event
			// and another row to the respective time cell
			else {
				eventTextList.get(position).append("\n" + e.getTitle() + " starts at " + sf.format(startDate)
				+ " and ends at " + sf.format(endDate));
				timeList.get(position).append("\n");
			}
		}
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1.0;
		gridPanel.add(eventsHeader, c);
		
		eventsPanel.add(gridPanel, BorderLayout.NORTH);
		eventsPanel.revalidate();
		eventsPanel.repaint();
	}
}