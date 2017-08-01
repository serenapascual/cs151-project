import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreatePromptView {
	public CreatePromptView(final EventModel model) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		Calendar cal = model.getCal();
		SimpleDateFormat startTime = new SimpleDateFormat("hh:mmaa");
		Calendar endTime = new GregorianCalendar();
		endTime.setTime(cal.getTime());
		endTime.add(Calendar.MINUTE, 30);

		startTime.format(cal.getTime());
		JLabel title = new JLabel("Title: ");
		JTextField titleField = new JTextField("Untitled event",20);
		JLabel date = new JLabel("Date (MM/DD/YYYY): ");
		JTextField dateField = new JTextField((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR), 20);
		JLabel start = new JLabel("Start Time:");
		JTextField startField = new JTextField(startTime.format(cal.getTime()), 20);
		JLabel end = new JLabel("End Time:");
		JTextField endField = new JTextField(startTime.format(endTime.getTime()),20);
		JButton saveButton = new JButton("Save");
		JButton cancelButton = new JButton("Cancel");

		cancelButton.setFocusable(false);
		cancelButton.setBackground(new Color(236, 151, 31));
		cancelButton.setForeground(Color.WHITE);
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

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

				SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyyhh:mmaa");
				Date startDate = null;
				try {
					startDate = sf.parse(date+start);
				} catch (ParseException ex) {
					Logger.getLogger(CreatePromptView.class.getName()).log(Level.SEVERE, null, ex);
				}
				Date endDate = null;
				try {
					endDate = sf.parse(date+end);
				} catch (ParseException ex) {
					Logger.getLogger(CreatePromptView.class.getName()).log(Level.SEVERE, null, ex);
				}
				
				Calendar startCal = new GregorianCalendar();
                startCal.setTime(startDate);
                Calendar endCal = new GregorianCalendar();
                endCal.setTime(endDate);
                
                Event eventNew = new Event(title, (GregorianCalendar)startCal, (GregorianCalendar) endCal);
                //System.out.println(eventNew.toString());
                boolean conflict = false;
                if(eventNew.getEnd().before(eventNew.getStart()) || eventNew.getStart().equals(eventNew.getEnd())) {
                	JOptionPane.showMessageDialog(frame,
                            "The end time cannot be before the start time.",
                            "Time Conflict",
                            JOptionPane.WARNING_MESSAGE);
                        conflict = true;
                }
                
                for(Event events: model.getEvents()) {
                	if(events.compareTo(eventNew) == 0) {
                		JOptionPane.showMessageDialog(frame,
                                "Times cannot overlap. Please ensure that start time is different from end time",
                                "Time Conflict",
                                JOptionPane.WARNING_MESSAGE);
                            conflict = true;
                            break;
                	}
                }
                
                if(!conflict) {
                	model.addEvent(eventNew);
                }
                conflict = false;
                frame.dispose();
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
