package domain;

import hibernate.HibernateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = Box.TABLE)
public class Box {
	public static final String TABLE = "BOX";	
	
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
	
	public Box () {}
	
	public Box (Game game, int numRow, int numCol, boolean hasMine) {		
		this.game	= game;
		this.numRow	= numRow;
		this.numCol = numCol;		
		this.hasMine = hasMine;
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
}
