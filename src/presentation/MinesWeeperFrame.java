package presentation;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MinesWeeperFrame extends JFrame {

	private JPanel contentPane;

	public MinesWeeperFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	public void init () {
		contentPane = new LoginView();
		setContentPane(contentPane);
		setSize(270, 123);
		showView("MinesWeeper");
	}
	
	public void showView (String title) {
		setTitle(title);
		setVisible(true);
		setResizable(false);
	}
	
	private void switchPanel(JPanel panel) {
		remove(contentPane);
		contentPane = panel;
		setContentPane(contentPane);
		validate();
		repaint();
	}
}
