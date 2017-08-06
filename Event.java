import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author Himanshu Mehta, Serena Pascual and Cherie Sew
 *
 */
public class Event implements Comparable<Event>{
	private String title;
	private int year;
	private int month;
	private int day;
	private String startTime;
	private String endTime;
	private Calendar start = new GregorianCalendar();
	private Calendar end = new GregorianCalendar();
	private String dayOfWeek;

	/**
	 * Creates an event in calendar
	 * @param title - title of event
	 * @param year - year of event
	 * @param month - month of event
	 * @param day - day of event
	 * @param startTime - start time of event
	 * @param endTime - end time of event 
	 */
	public Event(String title, int year, int month, int day, String startTime, String endTime) {
		this.title = title;
		this.year = year;
		this.month = month;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		setTime();
		EventModel model = new EventModel();
		dayOfWeek = model.getWeekDay(year, month, day);
	}
	
	/**
	 * Set the time of an event 
	 */
	public void setTime() {
		@SuppressWarnings("deprecation")
		Date startDate= new Date(year, month - 1, day, Integer.parseInt(startTime.substring(0, 2)),
				Integer.parseInt(startTime.substring(2, 4)));
		@SuppressWarnings("deprecation")
		Date endDate = new Date(year, month - 1, day, Integer.parseInt(endTime.substring(0, 2)),
				Integer.parseInt(endTime.substring(2, 4)));
		start.setTime(startDate);
		end.setTime(endDate);
	}

	/**
	 * Get name of the event
	 * @return title of event
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Get year of the event
	 * @return year of event
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Get month of the event
	 * @return month of event
	 */
	public int getMonth() {
		return month;
	}
	
	/**
	 * Get week of the event
	 * @return week of event
	 */
	public int getWeek() {
		return this.start.get(Calendar.WEEK_OF_MONTH);
	}
	
	/**
	 * Get day of event
	 * @return day of event
	 */
	public int getDay() {
		return day;
	}
	
	/**
	 * Get start time of event
	 * @return start time of event
	 */
	public Calendar getStart() {
		return start;
	}
	
	/**
	 * Get end time of event
	 * @return end time of event
	 */
	public Calendar getEnd() {
		return end;
	}

	/**
	 * Compares event times 
	 * @param e - events to compare to
     * @return -1 Event e is scheduled later
     * @return 1 Event e is scheduler before
     * @return 0 Scheduled at the same time
	 */
	public int compareTo(Event e) {
		if(e.start.before(this.start) && e.end.before(this.start)) {
			return 1;
		}else if(e.start.after(this.end) && e.end.after(this.end)) {
			return -1;
		}else {
			return 0;
		}
	}
	
	/**
	 * Creates String with all event information
	 * @return event in the format of title; year; month; dayOfWeek; startTime; endTime 
	 */
	public String toString() {
		return title +";"+ year +";"+ month +";"+ dayOfWeek +";"+ startTime +";"+ endTime;
	}
}