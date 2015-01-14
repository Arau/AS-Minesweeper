package presentation;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.Position;

public class MinesWeeperFrame extends JFrame {

	private JPanel contentPane;
	private GameView gameView;

	public MinesWeeperFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(300, 20, 450, 300);
		setBounds(300, 20, 600, 600);
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
		gameView = new GameView();
		switchPanel( gameView );
		setSize(630, 670);
	}
	
	public void discoverBox(List<Position> toDiscover) {
		gameView.discoverBox(toDiscover);
	}
	
	private void switchPanel(JPanel panel) {
		remove(contentPane);
		contentPane = panel;
		setContentPane(contentPane);
		validate();
		repaint();
	}
}
