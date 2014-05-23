package io;

public class InOutException extends RuntimeException {
	public InOutException() {
		super();
	}

	public InOutException(String message) {
		super(message);
	}

	public InOutException(String message, Throwable cause) {
		super(message, cause);
	}

	public InOutException(Throwable cause) {
		super(cause);
	}
}
