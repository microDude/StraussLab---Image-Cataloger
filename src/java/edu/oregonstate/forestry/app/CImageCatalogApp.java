/**
 * Company:	com.gregorygutshall
 * Author:	gutshall
 *
 *			Copyright (c) com.gregorygutshall
 *			All rights reserved. 
 */
package edu.oregonstate.forestry.app;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.oregonstate.forestry.util.CConfigManager;
import edu.oregonstate.forestry.util.CLogHelper;

/**
 * Application to handle monitoring the pickup directory, handling the document, and creating the output.
 * 
 * @author gutshall
 */
public class CImageCatalogApp implements Runnable {

	// config manager
	private final CConfigManager configManager;
	
	// Directory Watching Service
	private final WatchService watcher;
	private final Map<WatchKey,Path> keys;
	
	// Excel File 
	private final Workbook excelWorkBook;
	
	/**
	 * Constructor
	 * @param pathToConfig
	 */
	public CImageCatalogApp(String pathToConfig) throws Exception {
		
		// Load the config manager
		this.configManager = new CConfigManager(new File(pathToConfig));
		
		// Create the Watch Service
		this.watcher = FileSystems.getDefault().newWatchService();
		this.keys = new HashMap<>();
		
		// Register the path
		Path pickUpDir = configManager.getPickUpDirectory();
		WatchKey key = pickUpDir.register(this.watcher, StandardWatchEventKinds.ENTRY_CREATE);
		keys.put(key, pickUpDir);
		CLogHelper.log("Set pickup Directory to: " + pickUpDir.toString());
		CLogHelper.log("Set drop off Directory to: " + configManager.getDropOffDirectory().toString());
		
		// Load the Excel File
		FileInputStream inputStream = new FileInputStream(configManager.getExcelFile());
		this.excelWorkBook = new XSSFWorkbook(inputStream);
		CLogHelper.log("Excel reference file: " + configManager.getExcelFile().getAbsolutePath());
		
	}

	@Override
	public void run() {
		
		// process File Events
		
	}
	
}
