import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

public class EventModel {
	private ArrayList<event> events = new ArrayList<>();
	private Calendar cal;
	private MainView view;
	
	public EventModel() {
		this.cal = new GregorianCalendar();
	}
	
	public void setDay(int day) {
		cal.set(Calendar.DAY_OF_MONTH, day);
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
	
	public Calendar getCal() {
		return cal;
	}
	
	public void addEvent(event e) {
		events.add(e);
		Collections.sort(events);
		view.repaint();
	}
	
	public void setView(MainView view) {
		this.view = view;
	}
	
	public ArrayList<event> getEvents() {
		return events;
	}
	
}
