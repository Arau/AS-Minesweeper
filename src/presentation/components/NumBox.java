package presentation.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.border.LineBorder;

import utils.Position;

public class NumBox extends BoardBox {
	private int num;
	
	public NumBox(Position p, int num) {
		super(p);
		this.num = num;
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
			this.showNum();
		}
		g.drawRect(col, row, width, height);
	}
	
	private void showNum() {
		if (this.num == 0) {
			this.setBackground( new Color(224, 224, 224) );
		} else {
			this.setBackground( Color.black ); // TODO Should show the number
			
		}
	}
}
