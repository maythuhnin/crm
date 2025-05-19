package com.eniac.projects.crm.exception;

public class DuplicatedEntryException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DuplicatedEntryException() {
		super();
	}
	
	public DuplicatedEntryException(final String message) {
		super(message);
	}
	
	public DuplicatedEntryException(final String message, final Throwable cause) {
		super(message, cause);
		cause.printStackTrace();
	}
	
	public DuplicatedEntryException(final Throwable cause) {
		super(cause);
		cause.printStackTrace();
	}

}
