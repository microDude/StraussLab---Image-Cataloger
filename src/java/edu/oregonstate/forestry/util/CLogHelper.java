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
 * @author gutshall
 *
 */
public class CLogHelper {
	
	private static Logger log = Logger.getLogger(CLogHelper.class);
	
	public static void log(String message) {
		log.info(message);
	}

}
