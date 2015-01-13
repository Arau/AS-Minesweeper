package presentation;

import java.util.ArrayList;
import java.util.List;

import javassist.bytecode.stackmap.TypeData.ClassName;

import org.apache.log4j.Logger;

import utils.Position;
import domaincontrollers.PlayUseCase;
import exceptions.LevelException;

public class PlayGameController {
	
	private static PlayGameController instance;
	private PlayUseCase playUc;
	private MinesWeeperFrame view;
	private String user;
	private static final Logger logger = Logger.getLogger( ClassName.class.getName() );

	
	private PlayGameController() {
		playUc = new PlayUseCase();
		view   = new MinesWeeperFrame();
		user   = "";
	}
	
	public static PlayGameController getInstance() {
		if (instance == null)
			instance = new PlayGameController();
		return instance;
	}
	
	public void initUI() {
		view.init();
	}
	
	public void prOkLogin(String user, String pwd) {
		if (!playUc.login(user, pwd)) {
			view.showWrongPwd();
		} else {
			List<String> levels = new ArrayList<String>();
			this.user = user;
			try {
				levels = playUc.retrieveLevels();
			} catch (LevelException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			view.showLevels( levels.toArray(new String[levels.size()]) );
		}
	}

	public void prLevelSelected(String level) {
		try {
			playUc.startGame(user, level);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		view.showGame();
	}

	public int getBoxInfo(Position p) {
		return playUc.getBoxInfo(p);
	}

	public Position getGameSize() {
		return playUc.getGameSize();
	}
}
