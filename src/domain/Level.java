package domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "LEVEL")
public class Level {
	@Id private String name;
	
	@Column(name = "num_box_row")
	private int numBoxRow;
	
	@Column(name = "num_box_column")
	private int numBoxColumn;
	
	@Column(name = "num_mines")
	private int numMines;

	// Delegate ownership of the relationship to Game 
	@OneToMany(mappedBy = "level")
	private Set<Game> games;
	
	public Level() {}
	
	public Level(String name, int numBoxRow, int numBoxCol, int numMines) {
		this.name = name;
		this.numBoxRow = numBoxRow;
		this.numBoxColumn = numBoxCol;
		this.numMines = numMines;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumBoxRow() {
		return numBoxRow;
	}

	public void setNumBoxRow(int numBoxRow) {
		this.numBoxRow = numBoxRow;
	}

	public int getNumBoxColumn() {
		return numBoxColumn;
	}

	public void setNumBoxColumn(int numBoxColumn) {
		this.numBoxColumn = numBoxColumn;
	}

	public int getNumMines() {
		return numMines;
	}

	public void setNumMines(int numMines) {
		this.numMines = numMines;
	}
}
