import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Himanshu Mehta, Serena Pascual and Cherie Sew
 * MilitaryTimeFormatter - Part of strategy pattern
 * Prints current time in military format
 *
 */
public class MilitaryTimeFormatter implements DateFormatter {
	
	private Date date= new Date();

  /**
  * Formats the date
  * @return the formatted date
  */
	public String formatDate() {
	
		DateFormat timeFormat = new SimpleDateFormat(" HH:mm:ss");
		Date date = new Date();
	
		return timeFormat.format(date);
		
	}
	
}