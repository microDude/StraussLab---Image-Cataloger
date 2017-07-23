/**
 * Company:	com.gregorygutshall
 * Author:	gregory
 * Year:	2017
 *
 * 			Copyright (c) 2017 com.gregorygutshall
 *			All rights reserved.
 */
package edu.oregonstate.forestry.server;

import org.apache.log4j.PropertyConfigurator;

import edu.oregonstate.forestry.app.CImageCatalogApp;
import edu.oregonstate.forestry.util.CLogHelper;

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
		
		// Setup Log4J
		PropertyConfigurator.configure("./resources/log4j.properties");
		
		CLogHelper.logInfo("Starting Application...");
		
		// Get the configuration file
		String pathToConfig = "./cfg/config.xml";
		if (args.length > 0) {
			pathToConfig = args[0];
			CLogHelper.logInfo("Configuration File Set to: " + pathToConfig);
		} else {
			CLogHelper.logInfo("No configuration file set, use default path: " + pathToConfig);
		}
		
		// Start the application 
		CImageCatalogApp catalogApp = null;
		try {
			catalogApp = new CImageCatalogApp(pathToConfig);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		// Intialize the Thread
		Thread thread = new Thread(catalogApp, "ImageCatalogApp");
				
		// Start the Thread
		thread.start();
		
		// Wait till Ctrl+C is trapped
		while (thread.isAlive()) {
			// Wait forever, or until run() is completed
		}
		
		CLogHelper.logInfo("\n" + "Terminating Server");
		
	}

}
