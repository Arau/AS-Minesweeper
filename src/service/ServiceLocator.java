package service;

public class ServiceLocator {
	
	private static ServiceLocator instance;
    
	public static ServiceLocator getInstance() {
        if (instance == null) 
        	instance = new ServiceLocator() {};
        return instance;
    }
	
	public ServiceLocator() {}

	public Object find(String svc) {
		Object res = null;
		if (svc.equals("MailSv") ) {
			res = new SendMailClient();
		}
		return res;
	}
}
