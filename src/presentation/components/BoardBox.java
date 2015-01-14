package presentation.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import presentation.PlayGameController;
import utils.Position;

public class BoardBox extends JComponent implements MouseListener {
	
	protected static int width  = 18;
	protected static int height = 18;
	
	protected Position position;
	protected boolean flag;
	protected boolean hidden;
	protected BufferedImage image;
	protected Graphics painter;
	protected Color color;
	
	
	public BoardBox (Position p) {
		super();
		enableInputMethods(true);   
		addMouseListener(this);
		
		this.position = p;
		this.flag 	= false;
		this.hidden = true;
		this.color = new Color(224,224,224);
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
		painter = g;
	}
	
	public Position getPosition() {
		return position;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			this.hidden = false;
			this.flag = true;
			PlayGameController.getInstance().prDiscoverBox(position);
		} else if (SwingUtilities.isRightMouseButton(e)) {
			PlayGameController.getInstance().prFlagBox(position);
			this.toggleFlag();
		}
	}
	
	public void discover() {}
	
	protected void toggleFlag() {
		if (flag == true) {
			flag = false;
			this.setForeground( Color.BLACK );	
		} else {
			flag = true;
			this.setForeground( Color.RED );
		}
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
