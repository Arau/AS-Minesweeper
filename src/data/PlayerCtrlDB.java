package data;

import hibernate.HibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datainterface.PlayerCtrl;
import domain.Level;
import domain.Player;

public class PlayerCtrlDB implements PlayerCtrl {

	private static PlayerCtrlDB instance;		
	public static PlayerCtrlDB getInstance() {
		if (instance == null)
			instance = new PlayerCtrlDB();
		return instance;
	}
	
	public PlayerCtrlDB() {}


	@Override
	public Player get(String username) throws Exception {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		Player res = (Player) session.get(Player.class, username);
		session.close();
		if(res == null)
			throw new Exception("PlayerNoExist");
		return res;
	}
	
	@Override
	public Boolean exists(String username) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		Player res = (Player) session.get(Player.class, username);
		session.close();

		return (res != null);	
	}
	
	@Override
	public List<Player> all() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		List<Player> res = (List<Player>)session.createQuery("from "+Player.TABLE).list();
		session.close();
		return res;
	}
}
