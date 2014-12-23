package datainterface;

import java.util.List;

import domain.MinesWeeper;

public interface MinesWeeperCtrl {
	public MinesWeeper get(Integer idGame) throws Exception;
	public Boolean exists(Integer idGame);
	public List<MinesWeeper> all();
}
