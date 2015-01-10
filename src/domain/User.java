package domain;

import hibernate.HibernateUtil;
import javassist.bytecode.stackmap.TypeData.ClassName;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import exceptions.LoginException;

@Entity
@Table(name = User.TABLE)
@Inheritance(strategy=InheritanceType.JOINED)
public class User {
	public static final String TABLE = "USER";
	
	@Transient
	private static final Logger logger = Logger.getLogger( ClassName.class.getName() );
	
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
	
	public boolean login (String pwd) throws LoginException {
		if (!this.pwd.equals(pwd)) {
			logger.debug("Pwd given " + pwd + " vs Pwd stored " + this.pwd);
			throw new LoginException("Wrong password");
		}
		return true;
	}
}
