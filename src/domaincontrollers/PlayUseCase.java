package domaincontrollers;

import java.util.List;

import utils.Position;
import adapters.AdapterFactory;
import adapters.MessageAdapter;
import datainterface.DataControllerFactory;
import datainterface.LevelCtrl;
import datainterface.PlayerCtrl;
import domain.Game;
import domain.Level;
import domain.MinesWeeper;
import domain.Player;
import exceptions.BoxException;
import exceptions.LevelException;

public class PlayUseCase {
	
	private Game game;
	private Player player;
	
	public boolean login(String username, String pwd) {
		return (new LoginUseCase()).login(username, pwd);
	}
	
	public List<String> retrieveLevels() throws LevelException {
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
		this.player = player;
		mw.setId(idGame + 1);
	}
	
	public void markBox(Position p) {
		this.game.markBox(p);
	}

	public void unMarkBox(Position p) {
		this.game.unMarkBox(p);
	}
	
	public List<Position> discover (Position p) {
		List<Position> discovered = game.discover(p);
		
		if ( game.isFinished() ) {
			player.finishGame(game);
			String msg = player.getUserName();
			if ( game.isWon() ) {
				msg += " You won! "; 
			} else {
				msg += " You lose! ";
			}
			msg += " Score: " + Long.toString( game.getScore() );
			MessageAdapter ma = AdapterFactory.getInstance().getMessageAdapter();
			ma.sendMessage(player.getMail(), msg);
		}
		return discovered;
	}
	
	public boolean isFinished () {
		return game.isFinished();
	}
	
	public boolean isWon () {
		return game.isFinished();
	}
	
	public void printBoard() {
		game.printBoard();
	}
	
	public long getScore() {
		return game.getScore();
	}

	public int getBoxInfo(Position p) {
		return game.getBoxInfo(p);
	}

	public Position getGameSize() {
		return game.getBoardSize();
	}
}
