package presentation.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import utils.Position;

public class MineBox extends BoardBox {
	
	public MineBox(Position p) {
		super(p);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		this.painter = g;
		
		// Offset calculus
		int row = this.position.getRow()*height;
		int col = this.position.getCol()*width;
		
		g.drawRect(col, row, width, height);
		if (this.hidden) {
			this.setForeground( Color.RED );
		} else {
			this.showMine();
		}
		painter.drawRect(1, 1, width-3, height-3);
	}
	
	public void discover() {
		this.showMine();
	}
	
	private void showMine() {
		this.hidden = false;
		this.flag = false;
		this.painter.setColor(Color.RED);
		this.painter.fillOval(4, 6, 8, 8);
		this.painter.fillRect(6, 4, 3, 6);
		this.painter.setColor(color);
		this.setForeground( color );
	}
}
