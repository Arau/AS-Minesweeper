package domaincontrollers;

import java.util.List;

import utils.Position;
import datainterface.DataControllerFactory;
import datainterface.LevelCtrl;
import datainterface.PlayerCtrl;
import domain.Game;
import domain.Level;
import domain.MinesWeeper;
import domain.Player;
import exceptions.BoxException;

public class PlayUseCase {
	
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
	
	public void markBox(Position p) {
		this.game.markBox(p);
	}

	public void unMarkBox(Position p) {
		this.game.unMarkBox(p);
	}
	
	public List<Position> discover (Position p) throws BoxException {
		List<Position> discovered = game.discover(p);
		return discovered;
	}
	
	public void printBoard() {
		game.printBoard();
	}
}
