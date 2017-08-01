import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.*;

public class AgendaPromptView {
	public AgendaPromptView(final EventModel model) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new BorderLayout());
		JPanel subpanel = new JPanel();
		
		subpanel.setLayout(new GridLayout(3, 2));
		
		JLabel instructions = new JLabel();
		instructions.setText("Enter a range to view all events scheduled during this period.");
		
		JLabel startDate = new JLabel("Start date:");
		
		JTextField startDateField = new JTextField();
		Calendar firstEventDate = model.getEvents().get(0).getStart();
		String firstEventDateString = firstEventDate.get(Calendar.MONTH) + "/" + firstEventDate.get(Calendar.DAY_OF_MONTH)
			+ "/" + firstEventDate.get(Calendar.YEAR);
		startDateField.setText(firstEventDateString);
		
		JLabel endDate = new JLabel("End date:");
		
		JTextField endDateField = new JTextField();
		Calendar lastEventDate = model.getEvents().get(0).getStart();
		String lastEventDateString = lastEventDate.get(Calendar.MONTH) + "/" + lastEventDate.get(Calendar.DAY_OF_MONTH)
			+ "/" + lastEventDate.get(Calendar.YEAR);
		endDateField.setText(lastEventDateString);
		
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

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
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
