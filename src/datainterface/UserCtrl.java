package datainterface;

import java.util.List;

import domain.User;

public interface UserCtrl {
	public User get(String username) throws Exception;
	public Boolean exists(String username);
	public List<User> all();
}
