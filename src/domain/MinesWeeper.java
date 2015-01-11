package domain;

import hibernate.HibernateUtil;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = MinesWeeper.TABLE)
public class MinesWeeper {
	public static final String TABLE = "MINESWEEPER";

	@Id
	private int id;
	
	public MinesWeeper() {
		id = 0;
		HibernateUtil.save(this);
	}	
	
	private static MinesWeeper instance;
	public static MinesWeeper getInstance() {
		if (instance == null)
			instance = new MinesWeeper();
		return instance;
	}	
	
	public int getId () {
		return id;
	}
	
	public void setId (int id) {
		HibernateUtil.updatePkey("MinesWeeper", "id", this.id, id);
		this.id = id;
	}
}
