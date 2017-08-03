import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class event implements Comparable<event>{
	private String title;
	private int year;
	private int month;
	private int day;
	private String startTime;
	private String endTime;
	private Calendar start = new GregorianCalendar();
	private Calendar end = new GregorianCalendar();
	private String dayOfWeek;

	public event(String title, int year, int month, int day, String startTime, String endTime) {
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
	
	public void setTime() {
		SimpleDateFormat sf = new SimpleDateFormat("M/dd/yyyy HH");
		//System.out.println(month+""+day+""+year+""+startTime);
		Date startDate = null;
		try {
			startDate = sf.parse(month+"/"+day+"/"+year+" "+startTime);
		} catch (ParseException ex) {
			Logger.getLogger(CreateEventView.class.getName()).log(Level.SEVERE, null, ex);
		}
		Date endDate = null;
		try {
			endDate = sf.parse(month+"/"+day+"/"+year+" "+endTime);
		} catch (ParseException ex) {
			Logger.getLogger(CreateEventView.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		start.setTime(startDate);
		end.setTime(endDate);
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
		return title +";"+ year +";"+ month +";"+ dayOfWeek +";"+ startTime +";"+ endTime ;
	}

//    public static Comparator<event> createComparatorByName()
//    {
//        return new Comparator<event>()
//        {
//            public int compare(event one, event two)
//            {
//                return one.compareTo(two);
//            }
//        };
//    };
//
//	public int compareTo(event a) {
//		 if (year > a.year)
//         {
//             return 1;
//         }
//         if (year < a.year)
//         {
//             return -1;
//         }
//         if (month > a.month)
//         {
//             return 1;
//         }
//         if (month < a.month)
//         {
//             return -1;
//         }
//         if (day > a.day)
//         {
//             return 1;
//         }
//         if (day < a.day)
//         {
//             return -1;
//         }
//         if (Integer.parseInt(startTime) > Integer.parseInt(a.startTime))
//         {
//             return 1;
//         }
//         if (Integer.parseInt(startTime) < Integer.parseInt(a.startTime))
//         {
//             return -1;
//         }
//         return 0;
//	}
}
