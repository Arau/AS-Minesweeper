package presentation.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import utils.Position;

public class NumBox extends BoardBox {
	private int num;
	private Color numColor;
	
	
	public NumBox(Position p, int num) {
		super(p);
		this.num = num;
		this.numColor = new Color(10, 240, 10);
	}

	@Override
	public void paintComponent(Graphics g) {
		this.painter = g;
		
		if (this.hidden) {
			this.setBackground( color );
		} else {
			this.showNum();
		}
		painter.drawRect(1, 1, width-3, height-3);
	}
	
	public void discover() {
		this.showNum();
	}
	
	private void showNum() {
		this.hidden = false;
		this.flag = false;
		
		if (this.num == 0) {
			this.setForeground( color );
		} else {
			this.painter.setColor(numColor);
			this.painter.setFont(new Font("Serif", Font.BOLD, 15)); 
			this.painter.drawString(Integer.toString(num), 5, 15);
			this.painter.setColor(color);
			this.setForeground( color );
		}
	}	
}
