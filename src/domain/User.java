package domain;

import hibernate.HibernateUtil;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = User.TABLE)
@Inheritance(strategy=InheritanceType.JOINED)
public class User {
	public static final String TABLE = "USER";
	
	@Id	
	private String username;
	
	private String name;
	private String surname;	
	private String pwd;
	
	public User (String user, String n, String sName, String pass) {
		username = user;
		name 	= n;
		surname	= sName;
		pwd		= pass;
		
		HibernateUtil.save(this);
	}
	
	public User () {}
	
	public String getUserName () {
		return username;
	}
}
