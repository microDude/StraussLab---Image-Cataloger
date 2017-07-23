/**
 * Company:	com.gregorygutshall
 * Author:	gutshall
 *
 *			Copyright (c) com.gregorygutshall
 *			All rights reserved. 
 */
package edu.oregonstate.forestry.exceptions;

/**
 * Exception class for this application
 * 
 * @author gutshall
 */
public class CImageCatalogException extends Exception implements IImageCatalogExceptions {

	private static final long serialVersionUID = 5294715304626423277L;
	
	private int ERROR_CODE;
	
	public CImageCatalogException(int ecd, String message) {
		this.initCause(new Throwable(message));
		this.ERROR_CODE = ecd;
	}
	
	public int getErrorCode() {
		return ERROR_CODE;
	}

}
