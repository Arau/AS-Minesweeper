package domain;

import hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javassist.bytecode.stackmap.TypeData.ClassName;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import utils.Board;
import exceptions.BoxException;


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
	
	@Column(name = "num_runs")
	private int numRuns;
	
	@ManyToOne
	@JoinColumn(name = "level_name", referencedColumnName = "name" )
    private Level level;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private Player player;

	@Transient
	private Board board;
	
	public Game() {}
	
	public Game(int idGame, Player player, Level level) {
		this.idGame		= idGame;
		this.isFinished = Boolean.FALSE;
		this.isWon 		= Boolean.FALSE;
		this.numRuns 	= 0;
        this.player	= player;
		this.level 	= level;
		player.setCurrentGame(this);
		HibernateUtil.save(this);
		
		this.board = new Board(this, level.getNumBoxColumn(), level.getNumBoxRow(), level.getNumMines());
	}
	
	public void markBox(int row, int col) throws BoxException {
		board.markBox(row, col);
	}
	
	public void unMarkBox(int row, int col) throws BoxException {
		board.unMarkBox(row, col);
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
