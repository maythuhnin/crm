package com.eniac.projects.bet.exception;

public class MyBatisException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public MyBatisException() {
		super();
	}
	
	public MyBatisException(final String message) {
		super(message);
	}
	
	public MyBatisException(final String message, final Throwable cause) {
		super(message, cause);
		cause.printStackTrace();
	}
	
	public MyBatisException(final Throwable cause) {
		super(cause);
		cause.printStackTrace();
	}

}
