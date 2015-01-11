package utils;

public class Position {
	int row, col;

	public Position (int r, int c) {
		row = r;
		col = c;
	}		
	
	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}
