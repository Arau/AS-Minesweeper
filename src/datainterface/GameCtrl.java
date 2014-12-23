package datainterface;

import java.util.List;

import domain.Game;

public interface GameCtrl {
	public Game get(Integer idGame) throws Exception;
	public Boolean exists(Integer idGame);
	public List<Game> all();
}
