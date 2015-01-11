package hibernate;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
  
    private static final SessionFactory sessionFactory = buildSessionFactory();
  
    private static SessionFactory buildSessionFactory() {
        try {          
            return new AnnotationConfiguration()
            	.configure()
            	.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
  
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Object save(Object o) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
        return o;
    }
 
    public static Object update(Object o) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.merge(o);
        session.getTransaction().commit();
        session.close();
        return o;
    }
    
    public static int updatePkey(String myTable, String field, Object oldK, Object newK) {
    	SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        
    	String hql = "UPDATE " + myTable + " SET " + field + " = :param1" + " WHERE " + field + " = :param2";
    	Query query = session.createQuery(hql);
    	query.setParameter("param1", newK);
    	query.setParameter("param2", oldK);
    	int result = query.executeUpdate();
    	
		session.getTransaction().commit();
		session.close();
    	return result; // num rows afected
    }
    
    public static void delete(Object o) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.close();
    }
    
    public static int emptyTable(String myTable){
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
	    String hql = String.format("delete from %s",myTable);
	    Query query = session.createQuery(hql);
	    int res =  query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return res;
	}
}