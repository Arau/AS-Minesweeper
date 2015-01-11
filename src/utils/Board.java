package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javassist.bytecode.stackmap.TypeData.ClassName;

import org.apache.log4j.Logger;

import domain.Box;
import domain.Game;
import exceptions.BoxException;

public class Board {
	private static final Logger logger = Logger.getLogger( ClassName.class.getName() );
	
	private Box[][] board;
	private int width, height;
	private boolean[][] visited;
	private Game game;
	
	public Board (Game g, int width, int height, int numMines) {
		if (width*height <= numMines) {
			throw new IllegalArgumentException("Number of mines bigger than board size");
		}
		
		board = new Box[width][height];
		this.width 	= width;
		this.height = height;
		this.game 	= g;
		
		this.visited = new boolean[width][height];
		this.setVisitedToFalse();
		
		List<Position> minesPos = getPosMines(numMines);
		
		// Creation bombs
		for (Position pos: minesPos) {
			int row = pos.getRow();
			int col = pos.getCol();
			board[row][col] = new Box(game, pos.getRow(), pos.getCol(), true);
		}
		
		// Fill boxes that has an adj bomb
		for (Position pos: minesPos) {
			int row = pos.getRow();
			int col = pos.getCol();
			
			logger.debug("\n");
			logger.debug("Created bomb in: " + row + " " + col);
			
			this.fillAdjacentBoxes(pos.getRow(), pos.getCol());
		}
		this.fillNonAdjacentBoxes();
		this.setVisitedToFalse();
	}
	
	public void markBox(Position p) {
		try {
			this.getBox(p).mark();
		} catch (BoxException e) {
			logger.warn(e.getMessage());
		}
	}
	
	public void unMarkBox(Position p) {
		try {
			this.getBox(p).unMark();
		} catch (BoxException e) {
			logger.warn(e.getMessage());
		}
	}
	
	public List<Position> discover (Position p) {
		List<Position> toDiscover = new ArrayList<Position>();
		if (this.hasMine(p)) {
			logger.debug("Mine Box: " + p.toString());
			toDiscover = discoverAll();
		} else if ( this.getBox(p).hasAdjMine() ) {
			toDiscover.add(p);
			this.discoverBox(p);
			visit(p);
		} else {
			toDiscover = discoverAdjBoxesBFS(p);
		}
		
		return toDiscover;
	}
	
	private List<Position> getPosMines (int num) {
		Random randomGenerator = new Random();
		List<Position> pos = new ArrayList<Position>();
		for (int i = 0; i < num; ++i) {				
			boolean mineIsSet = false;
			while (!mineIsSet) {
				int row = randomGenerator.nextInt(width);
				int col = randomGenerator.nextInt(height);
				if (!visited[row][col]) {
					mineIsSet = true;
					visited[row][col] = true;					
					Position p = new Position(row, col);
					pos.add(p);
					logger.debug("Assign bomb to:  Row: " + row + " Col:  " + col);
				}
			}
		}
		return pos;
	}
	
	private void fillNonAdjacentBoxes() {
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				if (!visited[i][j]) {
					visited[i][j] = true;					
					board[i][j] = new Box(game, i, j, false);
				}
			}
		}
	}

	private void fillAdjacentBoxes(int row, int col) {
		if (row == 0) {
			if (col == 0) {
				this.setUpRightBox   (row, col);
				this.setUpBotRightBox(row, col);
				this.setUpBottomBox  (row, col);
			} else if (col == width-1) {
				this.setUpLeftBox	(row, col);
				this.setUpBotLeftBox(row, col);
				this.setUpBottomBox (row, col);
			} else {
				this.setUpRightBox   (row, col);
				this.setUpBotRightBox(row, col);
				this.setUpBottomBox  (row, col);
				this.setUpBotLeftBox (row, col);
				this.setUpLeftBox    (row, col);
			}
		}
		
		else if (row == height-1) {
			if (col == 0) {
				this.setUpTopBox     (row, col);
				this.setUpTopRightBox(row, col);
				this.setUpRightBox   (row, col);
			} else if (col == width-1) {
				this.setUpLeftBox    (row, col);
				this.setUpTopLeftBox (row, col);
				this.setUpTopBox     (row, col);
			} else {
				this.setUpTopBox     (row, col);
				this.setUpTopRightBox(row, col);
				this.setUpRightBox   (row, col);
				this.setUpLeftBox    (row, col);
				this.setUpTopLeftBox (row, col);
			} 
		} else {
			if (col == 0) {
				this.setUpTopBox(row, col);
				this.setUpTopRightBox(row, col);
				this.setUpRightBox(row, col);
				this.setUpBotRightBox(row, col);
				this.setUpBottomBox(row, col);
			} 
			else if (col == width - 1) {
				this.setUpTopBox(row, col);
				this.setUpBottomBox(row, col);
				this.setUpBotLeftBox(row, col);
				this.setUpLeftBox(row, col);
				this.setUpTopLeftBox(row, col);
			} else {
				this.setUpTopBox		 (row, col);
				this.setUpTopRightBox	 (row, col);
				this.setUpRightBox		 (row, col);
				this.setUpBotRightBox	 (row, col);
				this.setUpBottomBox		 (row, col);
				this.setUpBotLeftBox	 (row, col);
				this.setUpLeftBox		 (row, col);
				this.setUpTopLeftBox	 (row, col);
			}
		}
	}
	
	private void setUpBox (int x, int y) {
		if (!visited[x][y]) {
			visited[x][y] = true;
			board[x][y] = new Box(game, x, y, false);
			logger.debug("Create near bomb box in " + x + " " + y);
		}
		board[x][y].incrementMinesAround();
	}

	private void setUpTopBox (int row, int col) {
		int tr = row - 1; // top row
		setUpBox(tr, col);
	}
	
	private void setUpTopRightBox (int row, int col) {		
		int tr = row - 1; // top row
		int rc = col + 1; // right col
		setUpBox(tr, rc);
	}
	
	private void setUpRightBox (int row, int col) {
		int rc = col + 1; // right col
		setUpBox(row, rc);
	}
	
	private void setUpBotRightBox (int row, int col) {
		int br = row + 1; // bottom row
		int rc = col + 1; // right col
		setUpBox(br, rc);
	}
	
	private void setUpBottomBox (int row, int col) {
		int br = row + 1; // bottom row
		setUpBox(br, col);
	}
	
	private void setUpBotLeftBox (int row, int col) {
		int br = row + 1; // bottom row
		int lc = col - 1; // left col
		setUpBox(br, lc);
	}
	
	private void setUpLeftBox (int row, int col) {
		int lc = col - 1; // left col
		setUpBox(row, lc);
	}
	
	private void setUpTopLeftBox (int row, int col) {
		int tr = row - 1; // top row
		int lc = col - 1; // left col
		setUpBox(tr, lc);
	}
	
	private void setVisitedToFalse () {
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				visited[i][j] = false;
			}
		}
	}
	
	private List<Position> discoverAdjBoxesBFS (Position source) {
		List<Position> pos = new ArrayList<Position>();
		Queue<Position> toVisit = new LinkedList<Position>();
		
		toVisit.add(source);
		while(!toVisit.isEmpty()) {
			Position p = toVisit.poll();
			if (!visited(p)) {
				visit(p);
				if ( !this.hasMine(p) ) {
					pos.add(p);
					this.discoverBox(p);
					logger.debug("Discover Box: " + p.toString());
					
					if (!this.getBox(p).hasAdjMine()) {
						addCandidatesToVisit(p, toVisit);
					}
				}
			}
		}
		
		return pos;
	}
	
	private List<Position> discoverAll() {
		List<Position> pos = new ArrayList<Position>();
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				Position p = new Position(i,j);
				if( !visited(p) ) {
					this.discoverBox(p);
					pos.add(p);
				}
			}
		}
		return pos;
	}
	
	private void addCandidatesToVisit(Position p, Queue<Position> toVisit) {
		for (Position adj: adjPos(p)) {
			if ( isValid(adj) && !visited(adj) ) {
				toVisit.add(adj);
				logger.debug("Candidate to discover Box: " + adj.toString());
			}
		}
	}
	
	private Box getBox (Position p) {
		return board[p.getRow()][p.getCol()];
	}
	
	private void discoverBox (Position p) {
		try {
			this.getBox(p).discover();
		} catch (BoxException e) {
			logger.warn(e.getMessage());
		}
	}
	
	private boolean visited (Position p) {
		return this.visited[p.getRow()][p.getCol()];
	}
	
	private void visit (Position p) {
		this.visited[p.getRow()][p.getCol()] = true;
	}
	
	private List<Position> adjPos (Position p) {
		int r = p.getRow();
		int c = p.getCol();
		
		Position top	 	= new Position(r-1 , c);
		Position topRight	= new Position(r-1 , c+1);
		Position right		= new Position(r   , c+1);
		Position botRight	= new Position(r+1 , c+1);
		Position bottom		= new Position(r+1 , c);
		Position botLeft	= new Position(r+1 , c-1);
		Position left		= new Position(r   , c-1);
		Position topLeft	= new Position(r-1 , c-1);
		
		List<Position> adjs = new ArrayList<Position>();
		Collections.addAll(adjs, top, topRight, right, botRight, bottom, botLeft, left, topLeft);
		return adjs;
	}
	
	private boolean isValid (Position p) {
		int r = p.getRow();
		int c = p.getCol();
		
		return (-1 < r && r < height &&
				-1 < c && c < width);
	}
	
	private boolean isHidden (Position p) {
		return this.getBox(p).isHidden();
	}
	
	public boolean hasMine (Position p) {
		return this.getBox(p).hasMine();
	}
	
	public void printSTDOUT (boolean all) {		
		for (int i = 0; i < height; ++i) {
			String s = "";
			for (int j = 0; j < width; ++j) {
				Position p = new Position(i,j);
				
				if ( this.isHidden(p) && !all) {
					s += "  X";
				} else {
					
					if (this.hasMine(p)) {
						s += "  B";
					} else {
						int num = this.getBox(p).getNumMinesAround();
						s += "  " + num;
					}
				}
			}
			System.out.println(s + "\n");
		}
		System.out.println("\n");
	}	
}


