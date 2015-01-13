package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LevelView extends JPanel {

	public LevelView(String[] listLevels) {
		this.setLayout(null);

		JLabel levelLabel = new JLabel("Level");
		levelLabel.setBounds(33, 60, 80, 25);
		this.add(levelLabel);

		JScrollPane scrollPane = new JScrollPane();
		final JList listBox = new JList( listLevels );
		scrollPane.setBounds(131, 12, 186, 115);
		scrollPane.setViewportView(listBox);
		scrollPane.setBounds(131, 12, 186, 115);
		this.add(scrollPane);

		JButton okButton = new JButton("OK");
		okButton.setBounds(131, 215, 80, 25);
		
		okButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				String level = listBox.getSelectedValue().toString();
				PlayGameController.getInstance().prLevelSelected(level);
			}
		});
		this.add(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(223, 215, 103, 25);
		this.add(cancelButton);
	}
}