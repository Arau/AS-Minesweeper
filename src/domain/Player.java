package domain;

import java.util.Set;

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
	}
	
	public Player () {}
		
	@OneToMany(mappedBy = "player")
	private Set<Game> games;
		
}
