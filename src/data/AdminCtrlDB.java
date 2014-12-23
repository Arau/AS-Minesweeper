package data;

import hibernate.HibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import datainterface.AdminCtrl;
import domain.Admin;

public class AdminCtrlDB implements AdminCtrl {

	private static AdminCtrlDB instance;		
	public static AdminCtrlDB getInstance() {
		if (instance == null)
			instance = new AdminCtrlDB();
		return instance;
	}
	
	public AdminCtrlDB() {}
	
	@Override
	public Admin get(String username) throws Exception {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		Admin res = (Admin) session.get(Admin.class, username);
		session.close();
		if(res == null)
			throw new Exception("AdminNoExist");
		return res;
	}
	
	@Override
	public Boolean exists(String username) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		Admin res = (Admin) session.get(Admin.class, username);
		session.close();

		return (res != null);
	}
	
	@Override
	public List<Admin> all() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		 
		List<Admin> res = (List<Admin>)session.createQuery("from "+Admin.TABLE).list();
		session.close();
		return res;
	}
}
