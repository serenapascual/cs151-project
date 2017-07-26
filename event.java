import java.util.Calendar;
import java.util.GregorianCalendar;

public class event implements Comparable<event>{
	private String title;
	private Calendar start;
	private Calendar end;
	
	public event(String title, GregorianCalendar start, GregorianCalendar end) {
		this.title = title;
		this.start = start;
		this.end = end;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Calendar getStart() {
		return start;
	}
	
	public Calendar getEnd() {
		return end;
	}
	
	public int compareTo(event e) {
		if(e.start.before(this.start) && e.end.before(this.start)) {
			return 1;
		}else if(e.start.after(this.end) && e.end.after(this.end)) {
			return -1;
		}else {
			return 0;
		}
	}

	public String toString() {
		return title + start.toString() + end.toString();
	}
}
