package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javassist.bytecode.stackmap.TypeData.ClassName;

import org.apache.log4j.Logger;

import utils.Position;
import exceptions.BoxException;

public class Board {
	private static final Logger logger = Logger.getLogger( ClassName.class.getName() );
	
	private Box[][] board;
	private int width, height;
	private boolean[][] visited;
	private int numRemainBoxes;
	private Game game;
	
	public Board (Game g, int width, int height, int numMines) {
		if (width*height <= numMines) {
			throw new IllegalArgumentException("Number of mines bigger than board size");
		}
		
		board = new Box[width][height];
		this.width 	= width;
		this.height = height;		
		this.game 	= g;
		this.numRemainBoxes = (width*height) - numMines;
		
		this.visited = new boolean[width][height];
		this.setVisitedToFalse();
		
		List<Position> minesPos = getPosMines(numMines);
		
		// Creation bombs
		for (Position pos: minesPos) {
			this.addBox( new Box(game, pos.getRow(), pos.getCol(), true) );
		}
		
		// Fill boxes that has an adj bomb
		for (Position p: minesPos) {
			logger.debug("\n");
			logger.debug("Created bomb in: " + p.toString());
			
			this.fillAdjacentBoxes(p);
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

		this.numRemainBoxes -= toDiscover.size();
		return toDiscover;
	}
	
	private List<Position> getPosMines (int num) {
		Random rg = new Random(); // Random generator
		List<Position> pos = new ArrayList<Position>();
		for (int i = 0; i < num; ++i) {				
			boolean mineIsSet = false;
			while (!mineIsSet) {
				Position p = new Position(rg.nextInt(height), rg.nextInt(width));
				if ( !visited(p) ) {
					mineIsSet = true;
					visit(p);					
					pos.add(p);
					logger.debug("Assign bomb to: " + p.toString());
				}
			}
		}
		return pos;
	}
	
	private void fillNonAdjacentBoxes() {
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				Position p = new Position(i,j);
				if ( !visited(p) ) {
					visit(p);
					this.addBox( new Box(game, p.getRow(), p.getCol(), false) );
				}
			}
		}
	}

	private void fillAdjacentBoxes(Position source) {
		for (Position adj: adjPos(source)) {
			if ( isValid(adj) ) {
				if ( !visited(adj) ) {
					visit(adj);
					Box b = new Box(game, adj.getRow(), adj.getCol(), false);
					b.incrementMinesAround();
					this.addBox(b);
					logger.debug("Create near bomb box in " + adj.toString());
				} else {
					this.getBox(adj).incrementMinesAround();		
				}
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
	
	private void setVisitedToFalse () {
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				visited[i][j] = false;
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
	
	private void addBox (Box b) {
		board[b.getNumRow()][b.getNumCol()] = b;
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
	

	public int getNumRemainBoxes () {
		return numRemainBoxes;
	}
	
	public int getBoxInfo(Position p) {
		Box b = this.getBox(p);
		if (b.hasMine()) {
			return -1;
		} else {
			return b.getNumMinesAround(); 
		}
	}
	
	public Position getSize() {
		return new Position(height, width);
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