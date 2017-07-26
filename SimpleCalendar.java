
public class SimpleCalendar {
	public static void main(String[] args) {
		EventModel model = new EventModel();
		MainView view = new MainView(model);
		model.setView(view);
	}
}
