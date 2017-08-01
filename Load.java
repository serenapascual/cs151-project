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
public class Load{
	private static ArrayList<Event> eventList = new ArrayList<Event>();
	static EventModel model = new EventModel();
	
	public Load(final EventModel model) {
		this.model = model;
	}
	public Load() {
		
	}

	/**
	 * Loads the events from the text file and then add them to the calendar.
	 */
	public static void loadEvent() {
		Scanner s = null;
		File f = new File("events.txt");
		try {
			s = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("This is your first run! The file was not found");
			try {
				f.createNewFile();
				System.out.println("A new file has been created");
				s = new Scanner(f);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
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
	public static void saveEvent(ArrayList<Event> event) {
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

		for (Event s : eventList) {
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
