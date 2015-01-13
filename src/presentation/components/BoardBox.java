package presentation.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import utils.Position;

public class BoardBox extends JComponent implements MouseListener {
	
	protected static int width  = 10;
	protected static int height = 10;
	
	protected Position position;
	protected boolean flag;
	protected boolean hidden;
	
	public BoardBox (Position p) {
		super();
		enableInputMethods(true);   
		addMouseListener(this);
		
		this.position = p;
		this.flag 	= false;
		this.hidden = true;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return ( new Dimension(width, height) );
	}
	
	@Override
	public Dimension getMinimumSize() {
		return this.getPreferredSize();
	}
	
	@Override
	public Dimension getMaximumSize() {
		return this.getPreferredSize();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// Offset calculus
		int row = position.getRow()*height;
		int col = position.getCol()*width;
		this.setOpaque(true);
		this.setForeground(Color.GRAY);
		g.drawRect(col, row, width, height);
	}
	
	public Position getPosition() {
		return position;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			this.hidden = false;
		} else if (SwingUtilities.isRightMouseButton(e)) {
			this.flag = true;
		}
	}
	
	protected void showFlag() {
		this.setForeground(Color.RED); // TODO Should show a flag icon
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
}
