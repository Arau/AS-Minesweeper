package presentation;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JPanel {
	
	public LoginView () {
		this.setLayout(null);
	
		JLabel userLabel = new JLabel("User");
		userLabel.setBounds(10, 10, 80, 25);
		this.add(userLabel);
	
		JTextField userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		this.add(userText);
	
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 80, 25);
		this.add(passwordLabel);
	
		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		this.add(passwordText);
	
		JButton loginButton = new JButton("OK");
		loginButton.setBounds(80, 80, 80, 25);
		this.add(loginButton);
	
		JButton registerButton = new JButton("Cancel");
		registerButton.setBounds(169, 80, 90, 25);
		this.add(registerButton);
	}

}
