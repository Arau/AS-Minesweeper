package data;

import hibernate.HibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datainterface.LevelCtrl;
import domain.Level;

public class LevelCtrlDB implements LevelCtrl {

	private static LevelCtrlDB instance;		
	public static LevelCtrlDB getInstance() {
		if (instance == null)
			instance = new LevelCtrlDB();
		return instance;
	}
	
	public LevelCtrlDB() {}

	@Override
	public Level get(String name) throws Exception {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		Level res = (Level) session.get(Level.class, name);
		session.close();
		if(res == null)
			throw new Exception("LevelNoExist");
		return res;
	}
	
	@Override
	public Boolean exists(String name) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		Level res = (Level) session.get(Level.class, name);
		session.close();

		return (res != null);	
	}
	
	@Override
	public List<Level> all() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		List<Level> res = (List<Level>)session.createQuery("from "+Level.TABLE).list();
		session.close();
		return res;
	}
}
