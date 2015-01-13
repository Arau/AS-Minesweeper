package presentation;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MinesWeeperFrame extends JFrame {

	private JPanel contentPane;

	public MinesWeeperFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 20, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	public void init () {
		contentPane = new LoginView(false);
		setContentPane(contentPane);
		setSize(270, 139);
		showView("MinesWeeper");
	}
	
	public void showView (String title) {
		setTitle(title);
		setVisible(true);
		setResizable(false);
	}
	
	public void showLevels(String[] levels) {
		switchPanel( new LevelView(levels) );
		setSize(380, 290);
	}
	
	public void showWrongPwd() {
		switchPanel( new LoginView(true) );
		setSize(270, 160);
	}
	
	public void showGame() {
		switchPanel( new GameView() );
		setSize(600, 620);
	}
	
	private void switchPanel(JPanel panel) {
		remove(contentPane);
		contentPane = panel;
		setContentPane(contentPane);
		validate();
		repaint();
	}
}
