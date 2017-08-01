import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	public void setToday(int day, int month) {
		cal.set(cal.get(Calendar.YEAR), month, day);
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
		view.repaint();
	}
	
	public void previousMonth() {
		cal.add(Calendar.MONTH, -1);
		view.repaint();
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
	
	public ArrayList<Event> getEvents() {
		return events;
	}
	
	public void quit() {
		Load load = new Load();
		load.saveEvent(events);
	}

	public void populateEvents(String values) {
		String[] val = values.split("-");
		SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		Date startDate = null;
		try {
			startDate = sf.parse(val[1]);
		} catch (ParseException ex) {
			Logger.getLogger(CreateEventView.class.getName()).log(Level.SEVERE, null, ex);
		}
		Date endDate = null;
		try {
			endDate = sf.parse(val[2]);
		} catch (ParseException ex) {
			Logger.getLogger(CreateEventView.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		Calendar startCal = new GregorianCalendar();
        startCal.setTime(startDate);
        Calendar endCal = new GregorianCalendar();
        endCal.setTime(endDate);
        
        Event e = new Event(val[0], (GregorianCalendar)startCal, (GregorianCalendar) endCal);
		events.add(e);

		//System.out.println(events);
	}
}
