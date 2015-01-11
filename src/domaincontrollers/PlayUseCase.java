package domaincontrollers;

import java.util.List;

import javassist.bytecode.stackmap.TypeData.ClassName;

import org.apache.log4j.Logger;

import datainterface.DataControllerFactory;
import datainterface.LevelCtrl;
import datainterface.PlayerCtrl;
import domain.Game;
import domain.Level;
import domain.MinesWeeper;
import domain.Player;
import exceptions.BoxException;

public class PlayUseCase {
	private static final Logger logger = Logger.getLogger( ClassName.class.getName() );
	
	private Game game;
	
	public boolean login(String username, String pwd) throws Exception {
		return (new LoginUseCase()).login(username, pwd);
	}
	
	public List<String> retrieveLevels() throws Exception {
		return (new RetrieveLevelsUseCase()).retrieveLevels();
	}
	
	public void startGame(String username, String levelName) throws Exception {
		MinesWeeper mw = MinesWeeper.getInstance();
		PlayerCtrl  pc = DataControllerFactory.getInstance().getPlayerCtrl();
		LevelCtrl 	lc = DataControllerFactory.getInstance().getLevelCtrl();
		
		Player 	player = pc.get(username);
		Level  	level  = lc.get(levelName);
		int 	idGame = mw.getId();
		
		this.game = new Game(idGame, player, level); 
		mw.setId(idGame + 1);
	}
	
	public void markBox(int row, int col) {
		try {
			this.game.markBox(row, col);
		} catch (BoxException e) {
			logger.warn(e.getMessage());
		}
	}
	
	public void unMarkBox(int row, int col) {
		try {
			this.game.unMarkBox(row, col);
		} catch (BoxException e) {
			logger.warn(e.getMessage());
		}
	}
}
