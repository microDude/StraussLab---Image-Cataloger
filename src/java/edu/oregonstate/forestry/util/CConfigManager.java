/**
 * Company:	com.gregorygutshall
 * Author:	gutshall
 *
 *			Copyright (c) com.gregorygutshall
 *			All rights reserved. 
 */
package edu.oregonstate.forestry.util;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import edu.oregonstate.forestry.exceptions.CImageCatalogException;
import edu.oregonstate.forestry.exceptions.IImageCatalogExceptions;

/**
 * Parses the configuration file.
 * 
 * @author gutshall
 */
public class CConfigManager {

	Document configDoc;
	
	public interface CONFIG_ITEMS {
		String pathToCatalogFile = "pathToCatalogFile";
		String directories = "directories";
		String pickUp = "pickUp";
		String dropOff = "dropOff";
		String error = "error";
		String catalogBackup = "catalogBackup";
		String naming = "naming";
		String extension = "extension";
		String dateFormat = "dateFormat";
	}
	
	/**
	 * Constructor
	 * @param file
	 * @throws Exception
	 */
	public CConfigManager(File file) throws Exception {
		SAXBuilder saxBuilder = new SAXBuilder();
		this.configDoc = saxBuilder.build(file);
	}
	
	/**
	 * Get the Excel File.
	 * 
	 * @return
	 * @throws Exception
	 */
	public File getCatalogFile() throws NullPointerException {
		return new File(getRootElement().getChildTextTrim(CONFIG_ITEMS.pathToCatalogFile));
	}
	
	/**
	 * Get Pickup Directory.  Where the {@link WatchService} is looking.
	 * 
	 * @return
	 * @throws InvalidPathException 
	 */
	public Path getPickUpDirectory() throws InvalidPathException {
		return Paths.get(getRootElement().getChild(CONFIG_ITEMS.directories).getChildTextTrim(CONFIG_ITEMS.pickUp));
	}
	
	/**
	 * Where the files will be moved to.
	 * 
	 * @return
	 * @throws InvalidPathException 
	 */
	public Path getDropOffDirectory() throws InvalidPathException {
		return Paths.get(getRootElement().getChild(CONFIG_ITEMS.directories).getChildTextTrim(CONFIG_ITEMS.dropOff));
	}
	
	/**
	 * If an error occurs while processing the file.  The file will be moved here.
	 * 
	 * @return
	 * @throws InvalidPathException 
	 */
	public Path getErrorDirectory() throws InvalidPathException {
		return Paths.get(getRootElement().getChild(CONFIG_ITEMS.directories).getChildTextTrim(CONFIG_ITEMS.error));
	}
	
	/**
	 * Directory where the catalog file will be backed up to before processing.
	 * 
	 * @return
	 * @throws InvalidPathException
	 */
	public Path getCatalogBackupDirectory() throws InvalidPathException {
		return Paths.get(getRootElement().getChild(CONFIG_ITEMS.directories).getChildTextTrim(CONFIG_ITEMS.catalogBackup));
	}
	
	/**
	 * Gets the desired file extension name.
	 * @return
	 */
	public String getFileExtension() {
		return getRootElement().getChild(CONFIG_ITEMS.naming).getChildTextTrim(CONFIG_ITEMS.extension);
	}
	
	/**
	 * Get a date formatted string in the form specified.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getDateFormat() throws CImageCatalogException {
		try {
			String dateFormat = getRootElement().getChild(CONFIG_ITEMS.naming).getChildTextTrim(CONFIG_ITEMS.dateFormat);
			SimpleDateFormat dt = new SimpleDateFormat(dateFormat);
			return dt.format(new Date());
		} catch (Exception e) {
			throw new CImageCatalogException(IImageCatalogExceptions.COULD_NOT_CONVERT_DATE_TIME, "Error while formatting the date. Exception: " + e.getMessage());
		}
	}
	
	/**
	 * Gets the root of the XML configuration file.
	 * 
	 * @return
	 */
	private Element getRootElement() {
		return this.configDoc.getRootElement();
	}
	
}
