package exceptions;

public class LevelException extends Exception {
	public LevelException() {}

	public LevelException(String message) {
		super(message);
	}

	public LevelException(Throwable cause) {
		super(cause);
	}

	public LevelException(String message, Throwable cause) {
		super(message, cause);
	}
}
