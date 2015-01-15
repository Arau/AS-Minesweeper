package domain;

import hibernate.HibernateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = Player.TABLE)
@PrimaryKeyJoinColumn(name="username")
public class Player extends User {
	public static final String TABLE = "PLAYER";
	
	@Column(unique = true)
	private String mail;
	
	public Player (String user, String name, String sName, String pass, String mail) {
		super(user, name, sName, pass);
		this.mail = mail;
		
		HibernateUtil.update(this);
	}
	
	public Player () {}
	
	@OneToOne
	private Game oldGame;
	
	@OneToOne 
	private Game curGame;
	
	public Game getOldGame () {
		return oldGame;
	}
	
	public void setOldGame (Game g) {
		oldGame = g;
		HibernateUtil.update(this);
	}
	
	public Game getCurrentGame () {
		return curGame;
	}
	
	public void setCurrentGame (Game g) {
		curGame = g;
		HibernateUtil.update(this);
	}

	public void finishGame (Game g) {
		this.setOldGame(g);
		this.setCurrentGame(null);
	}
	
	public String getMail () {
		return this.mail;
	}
}
