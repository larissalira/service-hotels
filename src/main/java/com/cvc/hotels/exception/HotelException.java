package com.cvc.hotels.exception;

public class HotelException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1713849156834320187L;


	public HotelException() {
		super();
		
	}

	public HotelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public HotelException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public HotelException(String message) {
		super(message);
		
	}

	public HotelException(Throwable cause) {
		super(cause);
		
	}
	
}
