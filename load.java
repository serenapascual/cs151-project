import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * This class saves the events to events.txt when program terminates, and also
 * loads the events from the text file.
 * 
 * @author Himanshu Mehta
 *
 */
public class load{
	private static ArrayList<event> eventList = new ArrayList<event>();
	static EventModel model = new EventModel();
	
	public load(final EventModel model) {
		this.model = model;
	}
	public load() {
		
	}

	/**
	 * Loads the events from the text file and then add them to the calendar.
	 */
	public static void loadEvent() {
		Scanner s = null;
		try {
			s = new Scanner(new File("events.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("This is your first run! The file was not found");
			e.printStackTrace();
		}
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNextLine()) {
			list.add(s.nextLine());
		}
		s.close();
		for (String values : list) {
			model.populateEvents(values);
		}
	}

	/**
	 * This method saves events to the events text file
	 * @param event is an event which is saved to the text file
	 *            
	 */
	public static void saveEvent(ArrayList<event> event) {
		for (int i = 0; i < event.size(); i++) {
			eventList.add(event.get(i));
		}
		File f = new File("events.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter(f.getAbsoluteFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);

		for (event s : eventList) {
			try {
				bw.write(s.toString() + System.getProperty("line.separator"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
