package domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = Game.TABLE)
public class MinesWeeper {
	public static final String TABLE = "MINESWEEPER";

	public MinesWeeper() {}
	
	private static MinesWeeper instance;
	public static MinesWeeper getInstance() {
		if (instance == null)
			instance = new MinesWeeper();
		return instance;
	}
	
	@OneToOne
    @PrimaryKeyJoinColumn(name = "id_game_fk", referencedColumnName = "id_game")    
	private Game game;
	public void setGame(Game g) {
		game = g;
	}
	
	public Game getGame() {
		return game;
	}	
}
