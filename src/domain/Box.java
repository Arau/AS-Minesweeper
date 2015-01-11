package domain;

import hibernate.HibernateUtil;
import javassist.bytecode.stackmap.TypeData.ClassName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import exceptions.BoxException;

@Entity
@Table(name = Box.TABLE)
public class Box {
	public static final String TABLE = "BOX";
	
	@Transient
	private static final Logger logger = Logger.getLogger( ClassName.class.getName() );
	
	
	@Id
	private int id;
		
	@ManyToOne
	@JoinColumn(name = "id_game")
	private Game game;
	
	@Column(name = "num_row")
	private int numRow;
	
	@Column(name = "num_col")
	private int numCol;
	
	@Column(name = "num_mines_around")
	private int numMinesAround;
	
	@Column(name = "has_mine") 
	private boolean hasMine;
	
	@Column(name = "is_hidden") 
	private boolean isHidden;
	
	@Column(name = "is_marked") 
	private boolean isMarked;
	
	
	public Box () {}
	
	public Box (Game game, int numRow, int numCol, boolean hasMine) {		
		this.game		= game;
		this.numRow		= numRow;
		this.numCol 	= numCol;		
		this.hasMine 	= hasMine;
		this.isHidden 	= true;
		this.isMarked 	= false;
		this.id = hashCode();
		this.numMinesAround = 0;
		HibernateUtil.save(this);
	}
	
	public static int hashCode (int idGame, int numRow, int numCol) {
		String id = String.format("%10d", idGame) + // Integer size
				String.format("%5d", numRow) + 		// 99999 max rows
				String.format("%5d", numCol);       // 99999 max cols
		return id.hashCode();
	}
	
	public int hashCode () {
		return hashCode(game.getId(), numRow, numCol);
	}
	
	public int getNumMinesAround() {
		return numMinesAround;
	}

	public void incrementMinesAround() {
		numMinesAround++;
		HibernateUtil.update(this);
	}
	
	public void mark () throws BoxException {
		if (this.isMarked) {
			throw new BoxException("Mark Box: " + numRow + " " + numCol + " Has already marked");
		}
		
		if (!this.isHidden) {
			throw new BoxException("Mark Box: " + numRow + " " + numCol + " has already been discovered");
		}
		
		this.isMarked = true;
		HibernateUtil.update(this);
		logger.debug("Box " + numRow + " " + numCol + " has been marked");
	}
	
	public void unMark () throws BoxException {
		if (!this.isMarked) {
			throw new BoxException("Unmark Box: " + numRow + " " + numCol + " Has not been marked");
		}
		
		if (!this.isHidden) {
			throw new BoxException("Unmark Box: " + numRow + " " + numCol + " Has already been discovered");
		}
		
		this.isMarked = false;		
		HibernateUtil.update(this);
		logger.debug("Box " + numRow + " " + numCol + " has been unmarked");
	}
	
	public int getNumRow() {
		return numRow;
	}
	
	public int getNumCol() {
		return numCol;
	}
	
	public boolean hasMine () {
		return hasMine;
	}
	
	public boolean hasAdjMine () {
		return (numMinesAround > 0);
	}
	
	public boolean isHidden() {
		return this.isHidden;
	}
	
	public void discover () throws BoxException {
		if (!this.isHidden) {
			throw new BoxException("Discover Box: " + numRow + " " + numCol + " Has already been shown");
		}

		this.isHidden = false;
		HibernateUtil.update(this);
		logger.debug("Box " + numRow + " " + numCol + " has been discovered");
	}
}
