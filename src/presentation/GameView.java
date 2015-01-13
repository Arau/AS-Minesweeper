package presentation;

import javax.swing.JPanel;

import presentation.components.BoardBox;
import presentation.components.MineBox;
import presentation.components.NumBox;
import utils.Position;

public class GameView extends JPanel {
	
	protected int width, height, boxSize, xOffset, yOffset;
	
	public GameView() {
		this.setLayout(null);
		
		xOffset = 45;
		yOffset = 90;
		initBoard();
	}
	
	private void addBox(BoardBox box) {
		Position p = box.getPosition();
		box.setBounds(xOffset, yOffset, 600,600);
		this.add(box);
	}
	
	private void initBoard() {
		Position size = PlayGameController.getInstance().getGameSize();
		int w = size.getCol();
		int h = size.getRow();
		
		boxSize = 10;
		width  = w; 
		height = h;
		
		for (int i = 0; i < h; ++i) {
			for (int j = 0; j < w; ++j) {
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
