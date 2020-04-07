package io.github.laskowski;

public class JSONFormatterException extends RuntimeException {
	private static final long serialVersionUID = -8026193504111446177L;
	
	public JSONFormatterException(String message) {
		super(message);
	}
	
	public JSONFormatterException(Exception exception) {
		super(exception);
	}
}
