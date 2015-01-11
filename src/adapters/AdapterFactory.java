package adapters;


public class AdapterFactory {
	private static AdapterFactory instance;
	private static MessageAdapter messageAdapter;
	
	public AdapterFactory() {}
		
	public static AdapterFactory getInstance() {
	    if (instance == null) 
	    	instance = new AdapterFactory() {};
	    return instance;
	}
	
	public static MessageAdapter getMessageAdapter() {
		if (messageAdapter == null) 
			messageAdapter = MessageAdapter.getInstance();
		return messageAdapter;
	}
	
}
