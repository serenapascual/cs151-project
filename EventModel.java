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
	private ViewTypes viewType;
	private Calendar agendaStart;
	private Calendar agendaEnd;
	
	public enum ViewTypes {
		DAY, WEEK, MONTH, AGENDA
	}

	/**
	 * MVC model of calendar program 
	 */
	public EventModel() {
		cal = new GregorianCalendar();
		viewType = ViewTypes.DAY;
	}
	
	/**
	 * Set the different view types 
	 * @param t the view type for the events panel
	 */
	public void setViewType(ViewTypes t) {
		viewType = t;
	}
	
	/**
	 * Get the view type
	 * @return the view type for the events panel
	 */
	public ViewTypes getViewType() {
		return viewType;
	}
	
	/**
	 * Get start date for agenda
	 * @return start date for agenda
	 */
	public Calendar getAgendaStart() {
		return agendaStart;
	}
	
	/**
	 * Get end date for agenda
	 * @return end date for agenda
	 */
	public Calendar getAgendaEnd() {
		return agendaEnd;
	}
	
	/**
	 * Set date for Agenda
	 * @param start - start date of agenda
	 * @param end - end date of agenda
	 */
	public void setAgenda(Calendar start, Calendar end) {
		agendaStart = start;
		agendaEnd = end;
	}
	
	/**
	 * Set day in calendar
	 * @param day 
	 */
	public void setDay(int day) {
		cal.set(Calendar.DAY_OF_MONTH, day);
		view.repaint();
	}

	/**
	 * Set current date in calendar
	 * @param day - current day
	 * @param month - current month
	 * @param year - current year
	 */
	public void setToday(int day, int month, int year) {
		cal.set(year, month, day);
		view.repaint();
	}

	/**
	 * Get the previous day in the calendar 
	 */
	public void previousDay() {
		cal.add(Calendar.DAY_OF_MONTH, -1);
		view.repaint();
	}

	/**
	 * Get the next day in the calendar
	 */
	public void nextDay() {
		cal.add(Calendar.DAY_OF_MONTH, 1);
		view.repaint();
	}

	/**
	 * Get the next month in the calendar
	 */
	public void nextMonth() {
		cal.add(Calendar.MONTH, 1);
		view.repaint();
	}

	/**
	 * Get previous month in the calendar
	 */
	public void previousMonth() {
		cal.add(Calendar.MONTH, -1);
		view.repaint();
	}

	public Calendar getCal() {
		return cal;
	}

	/**
	 * Add events into list
	 * @param e the event to be added
	 */
	public void addEvent(Event e) {
		events.add(e);
		Collections.sort(events);
		view.repaint();
	}

	/**
	 * Set layout of events panel
	 * @param view - the layout of the events panel
	 */
	public void setView(MainView view) {
		this.view = view;
	}
	
	/**
	 * Get the layout of events panel
	 * @return main view
	 */
	public MainView getView() {
		return this.view;
	}

	/**
	 * List of events
	 * @return all events in the arraylist 
	 */
	public ArrayList<Event> getEvents() {
		return events;
	}
	
	/**
	 * Quits the program
	 */
	public void quit() {
		Loader load = new Loader();
		load.saveEvent(events);
	}

	/**
	 * Populate the events panel with events
	 * @param values
	 */
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

	/**
	 * Get day of the week
	 * @param year 
	 * @param month
	 * @param day
	 * @return
	 */
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