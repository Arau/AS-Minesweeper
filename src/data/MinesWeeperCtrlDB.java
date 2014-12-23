package data;

import hibernate.HibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datainterface.MinesWeeperCtrl;
import domain.Level;
import domain.MinesWeeper;

public class MinesWeeperCtrlDB implements MinesWeeperCtrl {

	private static MinesWeeperCtrlDB instance;		
	public static MinesWeeperCtrlDB getInstance() {
		if (instance == null)
			instance = new MinesWeeperCtrlDB();
		return instance;
	}
	
	public MinesWeeperCtrlDB() {}


	@Override
	public MinesWeeper get(Integer idGame) throws Exception {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		MinesWeeper res = (MinesWeeper) session.get(MinesWeeper.class, idGame);
		session.close();
		if(res == null)
			throw new Exception("MinesWeeperNoExist");
		return res;
	}
	
	@Override
	public Boolean exists(Integer idGame) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		MinesWeeper res = (MinesWeeper) session.get(MinesWeeper.class, idGame);
		session.close();

		return (res != null);	
	}
	
	@Override
	public List<MinesWeeper> all() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		List<MinesWeeper> res = (List<MinesWeeper>)session.createQuery("from "+MinesWeeper.TABLE).list();
		session.close();
		return res;
	}

}
