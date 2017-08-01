import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.*;
import javax.swing.border.*;

public class MainView {
	private EventModel model;
	private final Calendar cal;
	private final JLabel monthLabel = new JLabel();
	private final JPanel monthPanel;
	private final JPanel dayPanel;
	private final JLabel dayLabel = new JLabel();

	public MainView(final EventModel model) {
		this.model = model;
		this.cal = model.getCal();
		Controller c = new Controller(model);
		c.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
		JFrame frame = new JFrame();

		monthPanel = new JPanel();
        monthPanel.setLayout(new GridLayout(0, 7, 0, 0));
		JPanel monthWrap = new JPanel();
		monthWrap.setLayout(new BorderLayout());
		monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		monthWrap.add(monthLabel, BorderLayout.NORTH);
		monthWrap.add(monthPanel, BorderLayout.CENTER);
		monthWrap.setSize(new Dimension(400,400));
		monthWrap.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); 
		drawMonth(monthPanel);

		JScrollPane scroll = new JScrollPane();
		dayPanel = new JPanel();
		//dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.PAGE_AXIS));
		dayPanel.setLayout(new BorderLayout());
		dayPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		dayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
		dayPanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(7.0f)));
		dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		drawDay(dayPanel);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.getViewport().add(dayPanel);
		scroll.setPreferredSize(new Dimension(500, 300));
		scroll.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_ALWAYS);

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
		dayPanel.removeAll();
		drawDay(dayPanel);
		dayPanel.revalidate();
		dayPanel.repaint();
	}

	private void drawMonth(JPanel monthPanel) {
		//Gets the month and the year and sets it on the top of the month view
		monthLabel.setText(new SimpleDateFormat("MMMM yyyy").format(cal.getTime()));
		monthLabel.setFont(new Font("Tahoma", Font.BOLD, 24));

		//Add Week Labels at top of Month View
		String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		for (int i = 0; i<7; i++) {
			JLabel day = new JLabel(daysOfWeek[i], SwingConstants.CENTER);
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
			if (i<startDay) {
				final JLabel day = new JLabel("");
				monthPanel.add(day);
			} else {
				int dayNumber = i-startDay+1;
				final JLabel day = new JLabel(dayNumber+"",SwingConstants.CENTER);
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

	private void drawDay(JPanel dayPanel) {
		dayLabel.setText(new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime()));
		dayLabel.setBackground(Color.white);
		dayLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		ArrayList<JTextField> fieldList = new ArrayList<>();
		JPanel dayHolder = new JPanel(); 
		dayHolder.setLayout(new BoxLayout(dayHolder, BoxLayout.PAGE_AXIS));

		for(int i = 0 ; i<48; i++) {
			JTextField eventField = new JTextField(40);
			eventField.setEditable(false);
			eventField.setFont(new Font("Tahoma", Font.BOLD, 14));
			dayHolder.add(eventField);
			fieldList.add(eventField);
		}
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.PAGE_AXIS));
		for(int i = 0 ; i<24;i++) {
			JPanel t = new JPanel();
			t.setLayout(new GridLayout(2,1));
			JTextField a = new JTextField(5);
			JTextField b = new JTextField(5);
			String tag = "PM";
			int currentHour = i;
			if(currentHour<12) {
				tag = "AM";
			}
			if (currentHour == 0) {
				currentHour += 12;
			}
			if (currentHour > 12) {
				currentHour -= 12;
			}
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
		dayPanel.add(timePanel, BorderLayout.WEST);
		dayPanel.add(dayHolder, BorderLayout.CENTER);
		dayPanel.add(dayLabel, BorderLayout.NORTH);
		ArrayList<Event> events = model.getEvents();
		for (Event e : events) {
			if (e.getStart().get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH) && (e.getStart().get(Calendar.MONTH) == cal.get(Calendar.MONTH)) 
										&& (e.getStart().get(Calendar.YEAR) == cal.get(Calendar.YEAR)) ) {

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

				int formPosition = startHour*2;
				if(startMin >= 30) {
					formPosition++;
				}
				
				int toPosition = endHour*2;
				if(endMin >= 30) {
					toPosition++;
				}
				for(int i = formPosition; i<= toPosition; i++) {
					fieldList.get(i).setText(e.getTitle() + " starts at "  + sf.format(startDate) + " and will end at " + sf.format(endDate));;
				}
//				dayPanel.add(new JLabel(e.getTitle()));
//				dayPanel.add(new JLabel(sf.format(startDate)));
//				dayPanel.add(new JLabel(sf.format(endDate)));
			}
		}
	}
}
