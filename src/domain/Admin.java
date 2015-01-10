package domain;

import hibernate.HibernateUtil;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = Admin.TABLE)
@PrimaryKeyJoinColumn(name="username")
public class Admin extends User {
	public static final String TABLE = "ADMIN";
	
	private String telf;
	
	public Admin (String user, String name, String sName, String pass, String telf) {
		super(user, name, sName, pass);
		this.telf = telf;
		HibernateUtil.update(this);
	}
	
	public Admin () {}
}