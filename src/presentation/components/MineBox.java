package presentation.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.border.LineBorder;

import utils.Position;

public class MineBox extends BoardBox {
	
	public MineBox(Position p) {
		super(p);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// Offset calculus
		int row = this.position.getRow()*height;
		int col = this.position.getCol()*width;
		
		if (this.hidden) {
			this.setOpaque(true);
			this.setForeground(Color.GRAY);
		} else if (this.flag) {
			this.showFlag();
		} else {
			this.setOpaque(false);
			this.showMine();
		}
		g.drawRect(col, row, width, height);
	}
	
	private void showMine() {
		this.setBackground(Color.BLUE);
	}
	
}
