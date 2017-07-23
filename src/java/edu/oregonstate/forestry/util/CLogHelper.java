/**
 * Company:	com.gregorygutshall
 * Author:	gutshall
 *
 *			Copyright (c) com.gregorygutshall
 *			All rights reserved. 
 */
package edu.oregonstate.forestry.util;

import org.apache.log4j.Logger;

/**
 * Simple Log4J interface.
 * 
 * @author gutshall
 */
public class CLogHelper {
	
	private static Logger log = Logger.getLogger(CLogHelper.class);
	
	/**
	 * Logs a Information message.
	 * 
	 * @param message
	 */
	public static void logInfo(String message) {
		log.info(message);
	}
	
	/**
	 * Logs a Error message.
	 * @param message
	 */
	public static void logError(String message) {
		log.error(message);
	}
	
	/**
	 * Logs a Warning message.
	 * @param message
	 */
	public static void logWarning(String message) {
		log.warn(message);
	}

}
