package adapters;

public class MessageAdapter implements IMessageAdapter {
	private static MessageAdapter instance;

	
	public MessageAdapter() {}
	
	public static MessageAdapter getInstance() {
		if (instance == null)
			instance = new MessageAdapter();
		return instance;
	}
	
	public void sendMessage (String msg) {
		// TODO
	}
}
