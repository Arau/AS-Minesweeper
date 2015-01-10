package domaincontrollers;

import java.util.ArrayList;
import java.util.List;

import datainterface.DataControllerFactory;
import datainterface.LevelCtrl;
import domain.Level;
import exceptions.LevelException;

public class RetrieveLevelsUseCase {

	public List<String> retrieveLevels() throws LevelException {
		LevelCtrl lc = DataControllerFactory.getInstance().getLevelCtrl();
		List<Level> levels = lc.all();
		
		if (levels.size() == 0) {
			throw new LevelException("There are no levels");
		}
		
		List<String> names = new ArrayList<String>();
		for (Level l: levels) {
			names.add(l.getName());
		}
		return names;
	}
}
