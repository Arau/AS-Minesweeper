package domain;


public class Box {
	public static final String TABLE = "BOX";	
	
	
	public static int hashCode(Integer idGame, Integer numRow, Integer numCol) {
		String id = String.format("%10d", idGame) + // Integer size
				String.format("%5d", numRow) + 		// 99999 max rows
				String.format("%5d", numCol);       // 99999 max cols
		return id.hashCode();
	}
}
