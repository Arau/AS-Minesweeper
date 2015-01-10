package domain;

import hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = Game.TABLE)
public class Game {
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
	private Box[][] board;
	
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
		
		createBoard(level.getNumBoxRow(), level.getNumBoxColumn(), level.getNumMines());
	}
	
	private void createBoard (int width, int height, int numMines) {
		if (width*height <= numMines) {
			throw new IllegalArgumentException("Number of mines bigger than board size");
		}
		
		boolean[][] visited = new boolean[width][height];
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				visited[i][j] = false;
			}
		}
		
		board = new Box[width][height];
		List<List<Integer>> minesPos = getPosMines(visited, width, height, numMines);
		
		// Creation bombs
		for (List<Integer> pos: minesPos) {
			Integer row = pos.get(0);
			Integer col = pos.get(1);
			board[row][col] = new Box(this, row, col, true);
		}
		
		// Fill adj bombs
		for (List<Integer> pos: minesPos) {
			Integer row = pos.get(0);
			Integer col = pos.get(1);
			System.out.println("Bomb in " + row + " " + col);
			this.fillAdjacentBoxes(width, height, row, col, visited);
		}
		this.fillNonAdjacentBoxes(width, height, visited);
	}
	
	private List<List <Integer>> getPosMines (boolean[][] visited, int w, int h, int num) {
		Random randomGenerator = new Random();
		List<List<Integer>> pos = new ArrayList<List<Integer>>();
		for (int i = 0; i < num; ++i) {				
			boolean mineIsSet = false;
			while (!mineIsSet) {
				int row = randomGenerator.nextInt(w);
				int col = randomGenerator.nextInt(h);
				if (!visited[row][col]) {
					mineIsSet = true;
					visited[row][col] = true;
					System.out.println("Assign bomb in:  Row: " + row + " Col:  " + col);
					List tmp = new ArrayList<Integer> (2);
					tmp.add(row);
					tmp.add(col);
					pos.add(tmp);
				}
			}
		}
		return pos;
	}
	
	private void fillNonAdjacentBoxes(int width, int height, boolean[][] visited) {
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				if (!visited[i][j]) {
					visited[i][j] = true;					
					board[i][j] = new Box(this, i, j, false);
				}
			}
		}
	}

	private void fillAdjacentBoxes(int width, int height, int row, int col, boolean visited[][]) {
		if (row == 0) {
			if (col == 0) {
				this.setUpRightBox   (row, col, visited);
				this.setUpBotRightBox(row, col, visited);
				this.setUpBottomBox  (row, col, visited);
			} else if (col == width-1) {
				this.setUpLeftBox	(row, col, visited);
				this.setUpBotLeftBox(row, col, visited);
				this.setUpBottomBox (row, col, visited);
			} else {
				this.setUpRightBox   (row, col, visited);
				this.setUpBotRightBox(row, col, visited);
				this.setUpBottomBox  (row, col, visited);
				this.setUpBotLeftBox (row, col, visited);
				this.setUpLeftBox    (row, col, visited);
			}
		}
		
		else if (row == height-1) {
			if (col == 0) {
				this.setUpTopBox     (row, col, visited);
				this.setUpTopRightBox(row, col, visited);
				this.setUpRightBox   (row, col, visited);
			} else if (col == width-1) {
				this.setUpLeftBox    (row, col, visited);
				this.setUpTopLeftBox (row, col, visited);
				this.setUpTopBox     (row, col, visited);
			} else {
				this.setUpTopBox     (row, col, visited);
				this.setUpTopRightBox(row, col, visited);
				this.setUpRightBox   (row, col, visited);
				this.setUpLeftBox    (row, col, visited);
				this.setUpTopLeftBox (row, col, visited);
			} 
		} else {
			if (col == 0) {
				this.setUpTopBox(row, col, visited);
				this.setUpTopRightBox(row, col, visited);
				this.setUpRightBox(row, col, visited);
				this.setUpBotRightBox(row, col, visited);
				this.setUpBottomBox(row, col, visited);
			} 
			else if (col == width - 1) {
				this.setUpTopBox(row, col, visited);
				this.setUpBottomBox(row, col, visited);
				this.setUpBotLeftBox(row, col, visited);
				this.setUpLeftBox(row, col, visited);
				this.setUpTopLeftBox(row, col, visited);
			} else {
				this.setUpTopBox		 (row, col, visited);
				this.setUpTopRightBox	 (row, col, visited);
				this.setUpRightBox		 (row, col, visited);
				this.setUpBotRightBox	 (row, col, visited);
				this.setUpBottomBox		 (row, col, visited);
				this.setUpBotLeftBox	 (row, col, visited);
				this.setUpLeftBox		 (row, col, visited);
				this.setUpTopLeftBox	 (row, col, visited);
			}
		}
	}
	
	private void setUpBox (int x, int y, boolean[][] visited) {
		if (!visited[x][y]) {
			visited[x][y] = true;
			board[x][y] = new Box(this, x, y, false);
			System.out.println("Create box in " + x + " " + y);
			board[x][y].incrementMinesAround();
		} else {
			System.out.println("Box already created in " + x + " " + y);
			board[x][y].incrementMinesAround();	
		}
		if (board[x][y] == null) System.out.println("null in " + x + " " + y);		
		
		//board[x][y].incrementMinesAround();					
	}

	private void setUpTopBox (int row, int col, boolean[][] visited) {
		int tr = row - 1; // top row
		System.out.println("Row: " + row + " Col:  " + col + " Top Row: " + tr );
		setUpBox(tr, col, visited);
	}
	
	private void setUpTopRightBox (int row, int col, boolean[][] visited) {		
		int tr = row - 1; // top row
		int rc = col + 1; // right col
		System.out.println("Row: " + row + " Col:  " + col + " Top Row: " + tr + "  Right col " + rc );
		setUpBox(tr, rc, visited);
	}
	
	private void setUpRightBox (int row, int col, boolean[][] visited) {
		int rc = col + 1; // right col
		setUpBox(row, rc, visited);
	}
	
	private void setUpBotRightBox (int row, int col, boolean[][] visited) {
		int br = row + 1; // bottom row
		int rc = col + 1; // right col
		setUpBox(br, rc, visited);
	}
	
	private void setUpBottomBox (int row, int col, boolean[][] visited) {
		int br = row + 1; // bottom row
		setUpBox(br, col, visited);
	}
	
	private void setUpBotLeftBox (int row, int col, boolean[][] visited) {
		int br = row + 1; // bottom row
		int lc = col - 1; // left col
		setUpBox(br, lc, visited);
	}
	
	private void setUpLeftBox (int row, int col, boolean[][] visited) {
		int lc = col - 1; // left col
		setUpBox(row, lc, visited);
	}
	
	private void setUpTopLeftBox (int row, int col, boolean[][] visited) {
		int tr = row - 1; // top row
		int lc = col - 1; // left col
		setUpBox(tr, lc, visited);
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
