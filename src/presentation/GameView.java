package presentation;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import presentation.components.BoardBox;
import presentation.components.MineBox;
import presentation.components.NumBox;
import utils.Position;

public class GameView extends JPanel {
	
	protected int width, height;
	protected static int xOffset = 100;
	protected static int yOffset = 90;
	protected static int boxSize = 18;
	protected JPanel board;
	protected BoardBox[][] objectBoard;
	
	public GameView() {
		this.setLayout(null);
		Position size = PlayGameController.getInstance().getGameSize();
		width  = size.getCol();
		height = size.getRow();
		
		objectBoard = new BoardBox[width][height];
		
		board = new JPanel();
		board.setLayout(new GridLayout(height, width, 0, 0));
		board.setPreferredSize( new Dimension(boxSize*width+10, boxSize*height+10) );
		board.setBounds(xOffset, yOffset, width*boxSize, height*boxSize);
		
		JLabel rolls = new JLabel("Rolls");
		rolls.setBounds(100, 10, xOffset, yOffset);
		JLabel numMines = new JLabel("mines");
		numMines.setBounds(300, 10, xOffset, yOffset);
		this.add(rolls);
		this.add(numMines);
		
		this.add(board);
		initBoard();
	}
	
	public void discoverBox(List<Position> toDiscover) {
		for (Position p: toDiscover) {
			objectBoard[p.getRow()][p.getCol()].discover();
		}
	}
	
	private void addBox (BoardBox box) {
		Position p = box.getPosition();
		objectBoard[p.getRow()][p.getCol()] = box;
		board.add(box);
	}
	
	private void initBoard() {
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				Position p = new Position(i, j);
				int info = PlayGameController.getInstance().getBoxInfo(p);
				if (info == -1) { // is a Mine
					this.addBox( new MineBox(p) );
				} else {
					this.addBox( new NumBox(p, info) );
				}
			}
		}
	}
}
