package data;

import hibernate.HibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datainterface.UserCtrl;
import domain.User;

public class UserCtrlDB implements UserCtrl {

	private static UserCtrlDB instance;		
	public static UserCtrlDB getInstance() {
		if (instance == null)
			instance = new UserCtrlDB();
		return instance;
	}
	
	public UserCtrlDB() {}

	@Override
	public User get(String username) throws Exception {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		User res = (User) session.get(User.class, username);
		session.close();
		if(res == null)
			throw new Exception("UserNoExist");
		return res;
	}
	
	@Override
	public Boolean exists(String username) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		User res = (User) session.get(User.class, username);
		session.close();
	
		return (res != null);	
	}
	
	@Override
	public List<User> all() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		List<User> res = (List<User>)session.createQuery("from "+User.TABLE).list();
		session.close();
		return res;
	}
}
