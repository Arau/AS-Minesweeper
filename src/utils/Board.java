package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javassist.bytecode.stackmap.TypeData.ClassName;

import org.apache.log4j.Logger;

import domain.Box;
import domain.Game;
import exceptions.BoxException;

public class Board {
	private static final Logger logger = Logger.getLogger( ClassName.class.getName() );
	
	private Box[][] board;
	private Game game;
	
	public Board (Game g, int width, int height, int numMines) {
		if (width*height <= numMines) {
			throw new IllegalArgumentException("Number of mines bigger than board size");
		}
		
		game = g;
		board = new Box[width][height];
		
		boolean[][] visited = new boolean[width][height];
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				visited[i][j] = false;
			}
		}
		
		List<List<Integer>> minesPos = getPosMines(visited, width, height, numMines);
		
		// Creation bombs
		for (List<Integer> pos: minesPos) {
			Integer row = pos.get(0);
			Integer col = pos.get(1);
			board[row][col] = new Box(game, row, col, true);
		}
		
		// Fill adj bombs
		for (List<Integer> pos: minesPos) {
			Integer row = pos.get(0);
			Integer col = pos.get(1);
			logger.debug("\n");
			logger.debug("Create bomb in: " + row + " " + col);
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
					logger.debug("Assign bomb to:  Row: " + row + " Col:  " + col);
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
					board[i][j] = new Box(game, i, j, false);
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
			board[x][y] = new Box(game, x, y, false);
			logger.debug("Create near bomb box in " + x + " " + y);
		}
		board[x][y].incrementMinesAround();
	}

	private void setUpTopBox (int row, int col, boolean[][] visited) {
		int tr = row - 1; // top row
		setUpBox(tr, col, visited);
	}
	
	private void setUpTopRightBox (int row, int col, boolean[][] visited) {		
		int tr = row - 1; // top row
		int rc = col + 1; // right col
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
	
	public void add(Box b) {
		int row = b.getNumRow();
		int col = b.getNumCol();
		board[row][col] = b;
	}
	
	public void markBox(int row, int col) throws BoxException {
		board[row][col].mark();
	}
	
	public void unMarkBox(int row, int col) throws BoxException {
		board[row][col].unMark();
	}
}
