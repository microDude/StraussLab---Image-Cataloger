/**
 * Company:	com.gregorygutshall
 * Author:	gutshall
 *
 *			Copyright (c) com.gregorygutshall
 *			All rights reserved. 
 */
package edu.oregonstate.forestry.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.oregonstate.forestry.exceptions.CImageCatalogException;
import edu.oregonstate.forestry.exceptions.IImageCatalogExceptions;
import edu.oregonstate.forestry.util.CCatalogManager;
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
	
	// catalog manager
	private final CCatalogManager catalogManager;
	
	// Directory Watching Service
	private final WatchService watcher;
	private final Map<WatchKey,Path> keys;
	
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
		CLogHelper.logInfo("Set pickup Directory to: " + pickUpDir.toString());
		CLogHelper.logInfo("Set drop off Directory to: " + configManager.getDropOffDirectory().toString());
		
		// Backup the current Catalog File
		String catalogFileName = configManager.getCatalogFile().getName();
		Path backupFile = configManager.getCatalogBackupDirectory().resolve(
				catalogFileName.substring(0, catalogFileName.lastIndexOf(".")) 
				+ "_"
				+ new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date())
				+ catalogFileName.substring(catalogFileName.lastIndexOf(".")));
		
		Files.copy(Paths.get(configManager.getCatalogFile().toURI()), backupFile, StandardCopyOption.REPLACE_EXISTING);
		CLogHelper.logInfo("Created Backup Catalog File: " + backupFile.toString());
		
		// Load the Catalog Manager
		this.catalogManager = new CCatalogManager(configManager.getCatalogFile());
	}

	@Override
	public void run() {
		
		// process File Events
		boolean isEndOfFile = false;
		// wait for key to be signaled
		WatchKey key = null;
		WatchEvent<?> event = null;
		Path newFile = null;
		Path destinationFile = null;
		while(!isEndOfFile) {
			
			CLogHelper.logInfo("Next Image --> " + catalogManager.getCurrentImageName());
			
			try {
				
				// Waits for the OS to trigger a StandardWatchEventKinds.ENTRY_CREATE
				key = watcher.take();
				
				CLogHelper.logInfo("New Image arrived!");
				         
	            // Context for directory entry event is the file name of entry
	            event = key.pollEvents().get(0);

	            // Get the full path of the new file
	            newFile = configManager.getPickUpDirectory().resolve((Path) event.context());
	            
	            CLogHelper.logInfo("New Image File: " + newFile.getFileName());

	            // Create path to destination file
	            destinationFile = configManager.getDropOffDirectory().resolve(catalogManager.getCurrentImageName() + "_" + configManager.getDateFormat() + configManager.getFileExtension());
	            
	            // Move the file
	            Files.move(newFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
	            
	            CLogHelper.logInfo("Image File moved to: " + destinationFile.toUri().toString());
	            
	            // Update the excel document
	            catalogManager.setHyperlinkCellValue(destinationFile);
	            
	            CLogHelper.logInfo("Finished Processing, waiting for next Image. \n");
				
			} catch (InterruptedException ex) {
				CLogHelper.logError("Error while getting the new file. Exception: " + ex.getMessage());
			} catch (IOException ex1) {
				// Occurs, if we could not move the file
				
				try {
					CLogHelper.logError("Error while moving the file to the destination path " + configManager.getDropOffDirectory().toUri().toString() + ". Exception: " + ex1.getMessage());
				
					// Try to move the image file to the error directory
					if (newFile != null) {
						try {
							CLogHelper.logInfo("Try to move the file to the Error Directory for future inspection.");
							Files.move(newFile, configManager.getErrorDirectory().resolve(catalogManager.getCurrentImageName() + "_" + configManager.getDateFormat() + configManager.getFileExtension()), StandardCopyOption.REPLACE_EXISTING);
						} catch (Exception e) {
							CLogHelper.logError("Error while trying to move the image file to the error directory. Exception: " + e.getMessage());
						}
					}
					
				
				} catch (InvalidPathException e) {
					// Do nothing.
				}
			} catch (InvalidPathException ex2) {
				CLogHelper.logError("Could not parse or generate the path to: " + ex2.getInput() + ".  Exception: " + ex2.getMessage());
			} catch (CImageCatalogException ex3) {
				
				switch (ex3.getErrorCode()) {
					case IImageCatalogExceptions.COULD_NOT_SAVE_CATALOG_FILE:
						CLogHelper.logError("Could not save the updated catalog file.  Close any other open references to this document, before continuing.");
						break;
					case IImageCatalogExceptions.EOF_CATALOG_FILE:
						CLogHelper.logInfo("Reached End of File!  Self Terminate.");
						isEndOfFile = true;
						break;
					default:
						CLogHelper.logWarning(ex3.getMessage());
				}
				
			} finally {
				// reset key, so we can pick up new events.
				if (key != null) {
					key.reset();
				}
			}

		}
		
		// Try to save the file, one last time.
		catalogManager.saveCatalogFile();
		
	}
	
}
