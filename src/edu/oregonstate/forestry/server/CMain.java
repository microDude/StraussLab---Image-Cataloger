/**
 * Company:	com.gregorygutshall
 * Author:	gregory
 * Year:	2017
 *
 * 			Copyright (c) 2017 com.gregorygutshall
 *			All rights reserved.
 */
package edu.oregonstate.forestry.server;

/**
 * The main entry class.
 * 
 * @author gregory
 */
public class CMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Starting Application...");
		
		// Get the configuration file
		String pathToConfig = "./cfg/config.xml";
		if (args.length > 0) {
			pathToConfig = args[0];
			System.out.println("Configuration File Set to: " + pathToConfig);
		} else {
			System.out.println("No configuration file set, use default path: " + pathToConfig);
		}
		
		// Start the application 
		
		
		
	}

}
