import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreatePromptView {
	public CreatePromptView(final EventModel model) {
		final JFrame frame = new JFrame();
		final JPanel panel = new JPanel();
		final Calendar cal = model.getCal();
		final SimpleDateFormat startTime = new SimpleDateFormat("hh:mmaa");
		final Calendar endTime = new GregorianCalendar();
		endTime.setTime(cal.getTime());
		endTime.add(Calendar.MINUTE, 30);

		startTime.format(cal.getTime());
		final JLabel title = new JLabel("Title: ");
		final JTextField titleField = new JTextField("Untitled event",20);
		final JLabel date = new JLabel("Date (MM/DD/YYYY): ");
		final JTextField dateField = new JTextField((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR), 20);
		final JLabel start = new JLabel("Start Time:");
		final JTextField startField = new JTextField(startTime.format(cal.getTime()), 20);
		final JLabel end = new JLabel("End Time:");
		final JTextField endField = new JTextField(startTime.format(endTime.getTime()),20);
		final JButton saveButton = new JButton("Save");
		final JButton cancelButton = new JButton("Cancel");

		cancelButton.setOpaque(true);
		cancelButton.setBorderPainted(false);
		cancelButton.setFocusable(false);
		cancelButton.setBackground(new Color(236, 151, 31));
		cancelButton.setForeground(Color.WHITE);
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		saveButton.setOpaque(true);
		saveButton.setBorderPainted(false);
		saveButton.setFocusable(false);
		saveButton.setBackground(new Color(68, 157, 68));
		saveButton.setForeground(Color.WHITE);
		saveButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = titleField.getText();
				String date = dateField.getText();
				String start = startField.getText();
				String end = endField.getText();
				// used to track whether an error dialog has popped up to prevent multiple
				boolean dialogOpened = false;
				
				int startHour = 0;
				int startMin = 0;
				int endHour = 0;
				int endMin = 0;
				String startAmPm = "";
				String endAmPm = "";
				while (startHour == 0 && startMin == 0 && endHour == 0 && endMin == 0) {
					try {
						String[] startArr = start.split(":");
						startHour = Integer.parseInt(startArr[0]);
						startMin = Integer.parseInt(startArr[1].substring(0, 2));
						startAmPm = startArr[1].substring(2, 4);
						String[] endArr = end.split(":");
						endHour = Integer.parseInt(endArr[0]);
						endMin = Integer.parseInt(endArr[1].substring(0, 2));
						endAmPm = endArr[1].substring(2, 4);
					}
					catch (NumberFormatException e1) {
	                	JOptionPane.showMessageDialog(frame,
	                            "Please enter a time in 12-hour format (HH:MMam/pm).",
	                            "Invalid input",
	                            JOptionPane.WARNING_MESSAGE);
	    				dialogOpened = true;
					}
				}
				
				// format start time and end time to 24-hour time
				if (startHour == 12 && startAmPm.toUpperCase().equals("AM")) startHour = 0;
				if (startHour == 12 && startAmPm.toUpperCase().equals("PM")) startHour = 12;
				else if (startAmPm.toUpperCase().equals("PM")) startHour += 12;
				if (endHour == 12 && endAmPm.toUpperCase().equals("AM")) endHour = 0;
				if (endHour == 12 && endAmPm.toUpperCase().equals("PM")) endHour = 12;
				else if (endAmPm.toUpperCase().equals("PM")) endHour += 12;
				
				String startHourStr = "" + startHour;
				String startMinStr = "" + startMin;
				String endHourStr = "" + endHour;
				String endMinStr = "" + endMin;
				if (startHour < 10) {
					startHourStr = "0" + startHour;
				}
				if (startMin < 10) {
					startMinStr = "0" + startMin;
				}
				if (endHour < 10) {
					endHourStr = "0" + endHour;
				}
				if (endMin < 10) {
					endMinStr = "0" + endMin;
				}
				start = startHourStr + startMinStr;
				end = endHourStr + endMinStr;
				
				String[] dateArr = date.split("/");
				int year = Integer.parseInt(dateArr[2]);
				int day = Integer.parseInt(dateArr[1]);
				int month = Integer.parseInt(dateArr[0]);
                Event eventNew = new Event(title, year, month, day, start, end);
                boolean conflict = false;
                if (((startHour >= 0 && startHour <= 24) == false
                		|| (endHour >= 0 && endHour <= 24) == false
                		|| (startMin >= 0 && startMin <= 59) == false
                		|| (endMin >= 0 && endMin <= 59) == false)
                		&& dialogOpened == false) {
                	JOptionPane.showMessageDialog(frame,
                            "Please enter a time in 12-hour format (HH:MMam/pm).",
                            "Invalid input",
                            JOptionPane.WARNING_MESSAGE);
                	dialogOpened = true;
                }
                else if ((eventNew.getEnd().before(eventNew.getStart())
                		|| eventNew.getStart().equals(eventNew.getEnd()))
                		&& dialogOpened == false) {
                	JOptionPane.showMessageDialog(frame,
                            "The end time cannot come before the start time.",
                            "Time Conflict",
                            JOptionPane.WARNING_MESSAGE);
                        conflict = true;
                        dialogOpened = true;
                }
                for (Event events: model.getEvents()) {
                	if(events.compareTo(eventNew) == 0 && dialogOpened == false) {
                		JOptionPane.showMessageDialog(frame,
                                "Event times cannot overlap. Please check your existing events.",
                                "Time Conflict",
                                JOptionPane.WARNING_MESSAGE);
                            conflict = true;
                            dialogOpened = true;
                            break;
                	}
                }
                if(!conflict) {
                	model.addEvent(eventNew);
                	frame.dispose();
                }
                conflict = false;
                dialogOpened = false;
			}
		});
		
		titleField.selectAll();
		panel.setLayout(new GridLayout(5, 2));
		panel.add(title);
		panel.add(titleField);	
		panel.add(date);
		panel.add(dateField);
		panel.add(start);
		panel.add(startField);
		panel.add(end);
		panel.add(endField);
		panel.add(saveButton);
		panel.add(cancelButton);
		frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
	}
}