
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author Himanshu Mehta, Serena Pascual and Cherie Sew
 * TimeFormatter - Part of strategy pattern
 * Prints current time in standard format
 *
 */
public class TimeFormatter implements DateFormatter{

	private Date date= new Date();
	
	/**
	 * Returns the formatted date
	 * @return the formatted date
	 */
	public String formatDate() {
	
		SimpleDateFormat formatTime= new SimpleDateFormat("hh:mm:ss a");
		String formattedTime = formatTime.format(new Date()).toString();
		
		return formattedTime;

	}

}