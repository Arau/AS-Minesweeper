package domain;


public class ScoreByTime implements ScoreStrategy {
	public static final String TABLE = "SCORE_TIME";

	private long startTime;
	
	public ScoreByTime () {
		startTime = System.currentTimeMillis();
	}
	
	@Override
	public long getScore(int numRolls) {
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		long seconds = totalTime/1000;
		
		if (seconds < 1) seconds = 1;
		return 1000*numRolls/seconds;
	}
}
