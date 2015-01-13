package presentation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JPanel {
	
	public LoginView (boolean wrongPwd) {
		this.setLayout(null);
	
		JLabel userLabel = new JLabel("User");
		userLabel.setBounds(10, 10, 80, 25);
		this.add(userLabel);
	
		final JTextField userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		this.add(userText);
	
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 80, 25);
		this.add(passwordLabel);
	
		final JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		this.add(passwordText);

		int row = 80;
		if (wrongPwd) {
			JLabel wrongLabel = new JLabel("Wrong password");
			wrongLabel.setBounds(10, 70, 160, 25);
			wrongLabel.setForeground (Color.red);
			this.add(wrongLabel);
			row = 100;
		}
		
		JButton loginButton = new JButton("OK");
		loginButton.setBounds(80, row, 80, 25);
		
		loginButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				String usr = userText.getText().toString(); 
				String pwd = new String(passwordText.getPassword());
				PlayGameController.getInstance().prOkLogin(usr, pwd);
			}
		});
		
		this.add(loginButton);
	
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(169, row, 90, 25);
		this.add(cancelButton);
	}

}
