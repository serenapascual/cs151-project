import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * 
 * @author Himanshu Mehta, Serena Pascual and Cherie Sew
 *
 */
public class AgendaPromptView {
	
	/**
	 * Pop up new prompt for Agenda between two dates
	 * @param model - MVC model with all the events
	 */
	public AgendaPromptView(final EventModel model) {
		
		final JFrame frame = new JFrame();
		JPanel panel = new JPanel(new BorderLayout());
		JPanel subpanel = new JPanel();
		
		subpanel.setLayout(new GridLayout(3, 2));
		
		JLabel instructions = new JLabel();
		instructions.setText("Enter a range to view all events scheduled during this period.");
		
		JLabel startDate = new JLabel("Start date:");
		
		final JTextField startDateField = new JTextField();
		String firstEventDateString = "";
		try {
			Calendar firstEventDate = model.getEvents().get(0).getStart();
			firstEventDateString = (firstEventDate.get(Calendar.MONTH) + 1) + "/" + firstEventDate.get(Calendar.DAY_OF_MONTH)
			+ "/" + (firstEventDate.get(Calendar.YEAR) - 1900);
		}
		catch (IndexOutOfBoundsException e) {
			firstEventDateString = (model.getCal().get(Calendar.MONTH) + 1) + "/" + model.getCal().get(Calendar.DAY_OF_MONTH)
			+ "/" + model.getCal().get(Calendar.YEAR);
		}
		startDateField.setText(firstEventDateString);

		JLabel endDate = new JLabel("End date:");
		final JTextField endDateField = new JTextField();
		String lastEventDateString = "";
		try{
			Calendar lastEventDate = model.getEvents().get(model.getEvents().size() - 1).getStart();
			lastEventDateString = (lastEventDate.get(Calendar.MONTH) + 1) + "/" + lastEventDate.get(Calendar.DAY_OF_MONTH)
			+ "/" + (lastEventDate.get(Calendar.YEAR) - 1900);
		}
		catch (IndexOutOfBoundsException e) {
			lastEventDateString = (model.getCal().get(Calendar.MONTH) + 1) + "/" + model.getCal().get(Calendar.DAY_OF_MONTH)
			+ "/" + model.getCal().get(Calendar.YEAR);
		}
		endDateField.setText(lastEventDateString);
		
		JButton saveButton = new JButton("Save");
		JButton cancelButton = new JButton("Cancel");

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
			public void actionPerformed(ActionEvent arg0) {
				Calendar start = null;
				Calendar end = null;
				while (start == null) {
					try {
						String[] startDateArr = startDateField.getText().split("/");
						int startYear = Integer.parseInt(startDateArr[2]);
						int startDay = Integer.parseInt(startDateArr[1]);
						int startMonth = Integer.parseInt(startDateArr[0]);
						
						String[] endDateArr = endDateField.getText().split("/");
						int endYear = Integer.parseInt(endDateArr[2]);
						int endDay = Integer.parseInt(endDateArr[1]);
						int endMonth = Integer.parseInt(endDateArr[0]);

						start = new GregorianCalendar(startYear, startMonth, startDay);
						end = new GregorianCalendar(endYear, endMonth, endDay);
					}
					catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(frame,
	                            "Please enter a date in the format MM/DD/YYYY.",
	                            "Invalid input",
	                            JOptionPane.WARNING_MESSAGE);
					}
					catch (ArrayIndexOutOfBoundsException e) {
						JOptionPane.showMessageDialog(frame,
	                            "Please enter a date in the format MM/DD/YYYY.",
	                            "Invalid input",
	                            JOptionPane.WARNING_MESSAGE);
					}
				}
				
				model.setAgenda(start, end);
				model.setViewType(EventModel.ViewTypes.AGENDA);
				model.getView().drawAgendaEvents(model.getAgendaStart(), model.getAgendaEnd());
				frame.dispose();
			}
		});
		
		subpanel.add(startDate);
		subpanel.add(startDateField);
		subpanel.add(endDate);
		subpanel.add(endDateField);
		subpanel.add(saveButton);
		subpanel.add(cancelButton);
		
		panel.add(instructions, BorderLayout.NORTH);
		panel.add(subpanel, BorderLayout.CENTER);
		
		frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
	}
}