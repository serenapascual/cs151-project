import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

/**
 * 
 * @author Himanshu Mehta, Serena Pascual and Cherie Sew
 *
 */

public class EventModel {
	private ArrayList<Event> events = new ArrayList<>();
	private Calendar cal;
	private MainView view;

	public EventModel() {
		this.cal = new GregorianCalendar();
	}

	public void setDay(int day) {
		cal.set(Calendar.DAY_OF_MONTH, day);
		view.repaint();
	}

	public void setToday(int day, int month, int year) {
		cal.set(year, month, day);
		view.repaint();
	}

	public void previousDay() {
		cal.add(Calendar.DAY_OF_MONTH, -1);
		view.repaint();
	}

	public void nextDay() {
		cal.add(Calendar.DAY_OF_MONTH, 1);
		view.repaint();
	}

	public void nextMonth() {
		cal.add(Calendar.MONTH, 1);
		view.repaintMonth();
	}

	public void previousMonth() {
		cal.add(Calendar.MONTH, -1);
		view.repaintMonth();
	}

	public Calendar getCal() {
		return cal;
	}

	public void addEvent(Event e) {
		events.add(e);
		Collections.sort(events);
		view.repaint();
	}

	public void setView(MainView view) {
		this.view = view;
	}
	
	public MainView getView() {
		return this.view;
	}

	public ArrayList<Event> getEvents() {
		return events;
	}
	
	public void quit() {
		Load load = new Load();
		load.saveEvent(events);
	}

	public void populateEvents(String values) {
		String[] val = values.split(";");
		String description = val[0];
		int year = Integer.parseInt(val[1]);
		int startMonth = Integer.parseInt(val[2]);
		int endMonth = Integer.parseInt(val[3]);
		String dayOfWeek = val[4];
		String startTime = val[5];
		String endTime = val[6];

		for(int i = startMonth; i <= endMonth; i++) {
			cal.set(year, i-1, 1);
			int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			for(int j = 1; j <= daysInMonth; j++) {
				if(dayOfWeek.contains(Weekday(year, i, j))) {
					Event e = new Event(description, year, i, j, startTime, endTime+"");
					events.add(e);
				}
			}
		}
	}

	public String getWeekDay(int year, int month, int day) {
		return Weekday(year, month, day);
	}

	private String Weekday(int year, int month, int day) {
		cal.set(year, month - 1, day);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		   case 1:
               return "S";
           case 2:
               return "M";
           case 3:
               return "T";
           case 4:
               return "W";
           case 5:
               return "H";
           case 6:
               return "F";
           case 7:
               return "A";
           default:
               return null;
		}
	}
}
