package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = Game.TABLE)
public class Game {
	public static final String TABLE = "GAME";
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_game")
	private int idGame;
	
	@Column(name = "is_finished")
	private boolean isFinished;
	
	@Column(name = "is_won")
	private boolean isWon;
	
	@Column(name = "num_runs")
	private int numRuns;
	
	@ManyToOne
	@JoinColumn(name = "level_name", referencedColumnName = "name" )
    private Level level;
	
	public Game() {}
	
	public Game(Level level) {
		this.isFinished = Boolean.FALSE;
		this.isWon = Boolean.FALSE;
		this.numRuns = 0;
		
		this.level = level;
	}
	
	public int getId() {
		return idGame;
	}

	public void setId(int idGame) {
		this.idGame = idGame;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public boolean isWon() {
		return isWon;
	}

	public void setWon(boolean isWon) {
		this.isWon = isWon;
	}

	public int getNumRuns() {
		return numRuns;
	}

	public void setNumRuns(int numRuns) {
		this.numRuns = numRuns;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
	
	public String getLevelName() {
		return level.getName();
	}
}	
