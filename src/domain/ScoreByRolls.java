package domain;

public class ScoreByRolls implements ScoreStrategy {

	public ScoreByRolls () {}
	
	@Override
	public long getScore(int numRolls) {
		return numRolls*100;
	}
}
