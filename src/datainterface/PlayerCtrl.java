package datainterface;

import java.util.List;

import domain.Player;

public interface PlayerCtrl {
	public Player get(String username) throws Exception;
	public Boolean exists(String username);
	public List<Player> all();
}
