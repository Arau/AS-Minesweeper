package domaincontrollers;

import datainterface.DataControllerFactory;
import datainterface.UserCtrl;
import domain.User;

public class LoginUseCase {
	
	public boolean login (String username, String pwd) throws Exception {
		UserCtrl uc = DataControllerFactory.getInstance().getUserCtrl();
		User user = uc.get(username);
		return user.login(pwd);
	}
}
