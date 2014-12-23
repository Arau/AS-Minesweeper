package datainterface;

import java.util.List;

import domain.Box;

public interface BoxCtrl {
	public Box get(Integer idGame, Integer numRow, Integer numCol) throws Exception;
	public Boolean exists(Integer idGame, Integer numRow, Integer numCol);
	public List<Box> all();
}
