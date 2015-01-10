package data;

import hibernate.HibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datainterface.BoxCtrl;
import domain.Box;

public class BoxCtrlDB implements BoxCtrl {

	private static BoxCtrlDB instance;		
	public static BoxCtrlDB getInstance() {
		if (instance == null)
			instance = new BoxCtrlDB();
		return instance;
	}
	
	public BoxCtrlDB() {}

	@Override
	public Box get(Integer idGame, Integer numRow, Integer numCol) throws Exception {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		Box res = (Box) session.get(Box.class, Box.hashCode(idGame, numRow, numCol));
		session.close();
		if(res == null)
			throw new Exception("BoxNoExist");
		return res;
	}
	
	@Override
	public Boolean exists(Integer idGame, Integer numRow, Integer numCol) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		Box res = (Box) session.get(Box.class, Box.hashCode(idGame, numRow, numCol));
		session.close();

		return (res != null);
	}
	
	@Override
	public List<Box> all() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		List<Box> res = (List<Box>) session.createQuery("from Box").list();
		session.close();
		return res;
	}
}
