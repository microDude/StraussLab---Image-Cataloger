/**
 * Company:	com.gregorygutshall
 * Author:	gutshall
 *
 *			Copyright (c) com.gregorygutshall
 *			All rights reserved. 
 */
package edu.oregonstate.forestry.exceptions;

/**
 * Error codes for this application.
 * 
 * @author gutshall
 */
public interface IImageCatalogExceptions {

	int EOF_CATALOG_FILE 			= 0;
	int COULD_NOT_SAVE_CATALOG_FILE	= 1;
	int COULD_NOT_CONVERT_DATE_TIME = 2;
}
