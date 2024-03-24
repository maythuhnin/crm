package com.eniac.projects.bet.exception;

public class BuisnessException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BuisnessException() {
		super();
	}
	
	public BuisnessException(final String message) {
		super(message);
	}
	
	public BuisnessException(final String message, final Throwable cause) {
		super(message, cause);
		cause.printStackTrace();
	}
	
	public BuisnessException(final Throwable cause) {
		super(cause);
		cause.printStackTrace();
	}

}
