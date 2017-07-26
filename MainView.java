import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainView {
	private EventModel model;
	private final Calendar cal;
	private final JLabel monthLabel = new JLabel();
	private final JPanel monthPanel;
	private final JPanel dayPanel;

	public MainView(final EventModel model) {
		this.model = model;
		this.cal = model.getCal();

		Controller c = new Controller();
		JFrame frame = new JFrame();

		monthPanel = new JPanel();
		monthPanel.setLayout(new GridLayout(0, 7, 8, 6));
		monthPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
		JPanel monthWrap = new JPanel();
		monthWrap.setLayout(new BoxLayout(monthWrap, BoxLayout.Y_AXIS));
		monthWrap.add(monthLabel);
		monthWrap.add(monthPanel);
		drawMonth(monthPanel);

		frame.add(monthWrap, BorderLayout.WEST);

		JScrollPane scroll = new JScrollPane();
		dayPanel = new JPanel();
		dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.PAGE_AXIS));
		dayPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		drawDay(dayPanel);
		scroll.getViewport().add(dayPanel);
		scroll.setPreferredSize(new Dimension(600, 400));
		scroll.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_ALWAYS);

		frame.add(c, BorderLayout.NORTH);
		frame.add(dayPanel, BorderLayout.EAST);
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
		monthLabel.setText(new SimpleDateFormat("MMMM yyyy").format(cal.getTime()));
		monthLabel.setFont(new Font("Tahoma", Font.BOLD, 24));

		//Add Week Labels at top of Month View
		String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		for (int i = 0; i<7; i++) {
			JLabel day = new JLabel(daysOfWeek[i], SwingConstants.CENTER); 
			day.setFont(new Font("Tahoma", Font.BOLD, 18));
			monthPanel.add(day);
		}

		//Add days in month
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

                    //CONTROLLER updates the model on the currently looked day
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int num = Integer.parseInt(day.getText());
                        model.setDay(num);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {}

                    @Override
                    public void mouseReleased(MouseEvent e) {}

                    @Override
                    public void mouseEntered(MouseEvent e) {}

                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
				if (dayNumber == cal.get(Calendar.DAY_OF_MONTH)) {
					day.setBorder(BorderFactory.createLineBorder(Color.black));
				}
				day.setFont(new Font("Tahoma", Font.BOLD, 20));
				monthPanel.add(day);
			}
		}
	}

	private void drawDay(JPanel dayPanel) {
		ArrayList<event> events = model.getEvents();
		for (event e : events) {
			if (e.getStart().get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {

				Date startDate = e.getStart().getTime();
				Date endDate = e.getEnd().getTime();

				SimpleDateFormat sf = new SimpleDateFormat("hh:mm aa");

				dayPanel.add(new JLabel(e.getTitle()));
				dayPanel.add(new JLabel(sf.format(startDate)));
				dayPanel.add(new JLabel(sf.format(endDate)));
			}
		}
	}
}
