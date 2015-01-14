package domain;

import hibernate.HibernateUtil;

import java.util.List;
import java.util.Random;

import javassist.bytecode.stackmap.TypeData.ClassName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import utils.Position;



@Entity
@Table(name = Game.TABLE)
public class Game {
	@Transient
	private static final Logger logger = Logger.getLogger( ClassName.class.getName() );

	public static final String TABLE = "GAME";
	
	@Id    
	@Column(name = "id_game")
	private int idGame;
	
	@Column(name = "is_finished")
	private boolean isFinished;
	
	@Column(name = "is_won")
	private boolean isWon;
	
	@Column(name = "num_rolls")
	private int numRolls;
	
	@Transient
	private ScoreStrategy scoreStrategy;
	
	private long score;
	
	@ManyToOne
	@JoinColumn(name = "level_name", referencedColumnName = "name" )
    private Level level;
	
	@Transient
	private Board board;
	
	public Game() {}
	
	public Game(int idGame, Player player, Level level) {
		this.idGame		= idGame;
		this.isFinished = Boolean.FALSE;
		this.isWon 		= Boolean.FALSE;
		this.numRolls 	= 1;
		this.level 	= level;
		HibernateUtil.save(this);

		player.setCurrentGame(this);
		this.board = new Board(this, level.getNumBoxColumn(), level.getNumBoxRow(), level.getNumMines());
		this.assignScoreStrategy();
	}
	
	public void markBox(Position p) {
		board.markBox(p);
	}
	
	public void unMarkBox(Position p) {
		board.unMarkBox(p);
	}
	
	public List<Position> discover (Position p) {
		List<Position> discovered = board.discover(p);
		if (board.hasMine(p)) {
			// Lost game
			this.setFinished(true);
			this.updateScore();
			logger.debug("Lost game");
		} else if (board.getNumRemainBoxes() == 0) {
			this.setWon(true);
			this.setFinished(true);
			this.updateScore();
			logger.debug("Won game");
		}
		
		this.incrementNumRolls();
		return discovered; 
	}
	
	private void assignScoreStrategy() {
		Random rg = new Random();
		int strategy = rg.nextInt(2);
		if(strategy == 0) {
			scoreStrategy = new ScoreByTime();
			logger.debug("Score strategy: Time");
		} else {
			scoreStrategy = new ScoreByRolls();
			logger.debug("Score strategy: Rolls");
		}
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
		HibernateUtil.update(this);
	}

	public boolean isWon() {
		return isWon;
	}

	public void setWon(boolean isWon) {
		this.isWon = isWon;
		HibernateUtil.update(this);
	}

	public int getNumRolls() {
		return numRolls;
	}

	public void incrementNumRolls () {
		numRolls++;
		HibernateUtil.update(this);
	}
	
	public void updateScore () {
		this.score = scoreStrategy.getScore(this.numRolls);
		HibernateUtil.update(this);
	}
	
	public long getScore () {
		return this.score;
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
	
	public void printBoard() {
		board.printSTDOUT(false);
		board.printSTDOUT(true);
	}

	public int getBoxInfo(Position p) {
		return board.getBoxInfo(p);
	}

	public Position getBoardSize() {
		return board.getSize();
	}
}	
