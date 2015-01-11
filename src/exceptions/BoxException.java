package exceptions;

public class BoxException extends Exception {

	public BoxException() {}

	public BoxException(String message) {
		super(message);
	}

	public BoxException(Throwable cause) {
		super(cause);
	}

	public BoxException(String message, Throwable cause) {
		super(message, cause);
	}
}
