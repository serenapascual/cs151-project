/**
 * Tester to run the Calendar Application
 * @author Himanshu Mehta
 *
 */
public class SimpleCalendar {
	public static void main(String[] args) {
		EventModel model = new EventModel();
		Load l = new Load(model);
		l.loadEvent();
		MainView view = new MainView(model);
		model.setView(view);
	}
}
