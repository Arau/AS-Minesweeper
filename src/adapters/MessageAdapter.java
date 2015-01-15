package adapters;

import javassist.bytecode.stackmap.TypeData.ClassName;

import org.apache.log4j.Logger;

import service.SendMailClient;
import service.ServiceLocator;

public class MessageAdapter implements IMessageAdapter {
	private static MessageAdapter instance;
	private static final Logger logger = Logger.getLogger( ClassName.class.getName() );
	
	public MessageAdapter() {}
	
	public static MessageAdapter getInstance() {
		if (instance == null)
			instance = new MessageAdapter();
		return instance;
	}
	
	public void sendMessage (String address, String msg) {
		SendMailClient smc = (SendMailClient) ServiceLocator.getInstance().find("MailSv");
		smc.sendMail(address, msg);
		logger.debug(msg);
	}
}