package presentation;

import domaincontrollers.PlayUseCase;

public class PlayGameController {
	
	private static PlayGameController instance;
	private PlayUseCase playUc;
	private MinesWeeperFrame view;
	
	private PlayGameController() {
		playUc = new PlayUseCase();
		view   = new MinesWeeperFrame();
	}
	
	public static PlayGameController getInstance() {
		if (instance == null)
			instance = new PlayGameController();
		return instance;
	}
	
	public void initUI() {
		view.init();
	}
	
	
}
