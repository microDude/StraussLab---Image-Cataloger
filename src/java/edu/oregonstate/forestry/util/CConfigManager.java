/**
 * Company:	com.gregorygutshall
 * Author:	gutshall
 *
 *			Copyright (c) com.gregorygutshall
 *			All rights reserved. 
 */
package edu.oregonstate.forestry.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * Parses the configuration file.
 * 
 * @author gutshall
 */
public class CConfigManager {

	Document configDoc;
	
	public interface CONFIG_ITEMS {
		String pathToExcelFile = "pathToExcelFile";
		String directories = "directories";
		String pickUp = "pickUp";
		String dropOff = "dropOff";
		String error = "error";
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
	public File getExcelFile() throws Exception {
		return new File(getRootElement().getChildTextTrim(CONFIG_ITEMS.pathToExcelFile));
	}
	
	/**
	 * Get Pickup Directory.  Where the {@link WatchService} is looking.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Path getPickUpDirectory() throws Exception {
		return Paths.get(getRootElement().getChild(CONFIG_ITEMS.directories).getChildTextTrim(CONFIG_ITEMS.pickUp));
	}
	
	/**
	 * Where the files will be moved to.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Path getDropOffDirectory() throws Exception {
		return Paths.get(getRootElement().getChild(CONFIG_ITEMS.directories).getChildTextTrim(CONFIG_ITEMS.dropOff));
	}
	
	/**
	 * If an error occurs while processing the file.  The file will be moved here.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Path getErrorDirectory() throws Exception {
		return Paths.get(getRootElement().getChild(CONFIG_ITEMS.directories).getChildTextTrim(CONFIG_ITEMS.error));
	}
	
	/**
	 * Gets the desired file extension name.
	 * @return
	 * @throws Exception
	 */
	public String getFileExtension() throws Exception {
		return getRootElement().getChild(CONFIG_ITEMS.naming).getChildTextTrim(CONFIG_ITEMS.extension);
	}
	
	/**
	 * Get a date formatted string in the form specified.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getDateFormat() throws Exception {
		String dateFormat = getRootElement().getChild(CONFIG_ITEMS.naming).getChildTextTrim(CONFIG_ITEMS.dateFormat);
		SimpleDateFormat dt = new SimpleDateFormat(dateFormat);
		return dt.format(new Date());
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
