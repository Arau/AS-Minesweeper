package data;

import hibernate.HibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datainterface.GameCtrl;
import domain.Game;

public class GameCtrlDB implements GameCtrl {

	private static GameCtrlDB instance;		
	public static GameCtrlDB getInstance() {
		if (instance == null)
			instance = new GameCtrlDB();
		return instance;
	}
	
	public GameCtrlDB() {}

	@Override
	public Game get(Integer idGame) throws Exception {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		Game res = (Game) session.get(Game.class, idGame);
		session.close();
		if(res == null)
			throw new Exception("GameNoExist");
		return res;
	}
	
	@Override
	public Boolean exists(Integer idGame) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		Game res = (Game) session.get(Game.class, idGame);
		session.close();

		return (res != null);
	}
	
	@Override
	public List<Game> all() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		List<Game> res = (List<Game>)session.createQuery("from "+Game.TABLE).list();
		session.close();
		return res;
	}
}
