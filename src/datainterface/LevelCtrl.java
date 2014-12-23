package datainterface;

import java.util.List;

import domain.Level;

public interface LevelCtrl {
	public Level get(String name) throws Exception;
	public Boolean exists(String name);
	public List<Level> all();
}
