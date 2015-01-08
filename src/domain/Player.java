package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	}
	
	public Player () {}
		
	@OneToMany(mappedBy = "player")
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
		games.add(1, g);
	}
	
		
}
