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
			this.setBackground( new Color(224,224,224) );
		} else if (this.flag) {
			this.showFlag();
		} else {
			this.showNum();
		}
		g.drawRect(0, 0, width, height);
	}
	
	public void discover() {
		this.showNum();
	}
	
	private void showNum() {
		this.hidden = false;
		this.flag = false;
		
		if (this.num == 0) {
			this.setForeground( new Color(224, 224, 224) );
		} else {
			this.setForeground( Color.GREEN ); // TODO Should show the number
		}
	}
}
