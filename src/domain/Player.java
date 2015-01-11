package domain;

import hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
		games = new ArrayList<Game>(2);
		Collections.addAll(games, null, null);
		
		HibernateUtil.update(this);
	}
	
	public Player () {}
		
	@OneToMany(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Game> games;
	
	public Game getOldGame () {
		return games.get(0);
	}
	
	public void setOldGame (Game g) {
		games.add(0, g);
	}
	
	public Game getCurrentGame () {
		return games.get(1);
	}
	
	public void setCurrentGame (Game g) {
		if (games.size() == 0) {
			Collections.addAll(games, null, g);
		} 
		else if (games.size() == 1) {
			games.add(g);
		} else {
			Game aged = games.get(1);
		    this.setOldGame(aged);
			games.add(1, g);
		}
	}
}
