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
		
		g.drawRect(col, row, width, height);
		if (this.hidden) {
			this.setForeground( Color.RED );
		} else if (this.flag) {
			this.showFlag();
		} else {
			this.showMine();
		}
		g.drawRect(0, 0, width, height);
	}
	
	public void discover() {
		this.showMine();
	}
	
	private void showMine() {
		this.hidden = false;
		this.flag = false;
		
		this.setForeground(Color.BLUE);
	}
}
