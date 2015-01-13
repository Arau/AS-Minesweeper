package domaincontrollers;

import javassist.bytecode.stackmap.TypeData.ClassName;

import org.apache.log4j.Logger;

import datainterface.DataControllerFactory;
import datainterface.UserCtrl;
import domain.User;
import exceptions.LoginException;

public class LoginUseCase {
	private static final Logger logger = Logger.getLogger( ClassName.class.getName() );
	
	
	public boolean login (String username, String pwd) {
		UserCtrl uc = DataControllerFactory.getInstance().getUserCtrl();
		boolean result = false;
		
		try {
			User user = uc.get(username);
			result = user.login(pwd);
		} catch (LoginException e) {
			logger.warn(e.getMessage());
			result = false;
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return result;
	}
}
