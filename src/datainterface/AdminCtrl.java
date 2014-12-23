package datainterface;

import java.util.List;

import domain.Admin;

public interface AdminCtrl {
	public Admin get(String username) throws Exception;
	public Boolean exists(String username);
	public List<Admin> all();
}
