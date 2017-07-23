/**
 * Company:	com.gregorygutshall
 * Author:	gutshall
 *
 *			Copyright (c) com.gregorygutshall
 *			All rights reserved. 
 */
package edu.oregonstate.forestry.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.oregonstate.forestry.exceptions.CImageCatalogException;
import edu.oregonstate.forestry.exceptions.IImageCatalogExceptions;

/**
 * Manages the Catalog File.
 * 
 * @author gutshall
 */
public class CCatalogManager {

	// Static EXCEL File config
	private interface COLUMN {
		int directoryCellIndex 			= 2;
	}
	
	// Excel File 
	private final File catalogFile;
	private final Workbook workBook;
	private final Sheet sheet0;
	private final CreationHelper cellCreationHelper;
	private final CellStyle hlinkstyle;
	private final int numRows;
	
	// The current row in the Excel file.
	private int currentRow = 0;
	
	/**
	 * Constructor
	 * @param catalogFile
	 * @throws Exception
	 */
	public CCatalogManager(File catalogFile) throws Exception {
		
		// Keep a reference to the original file for quick writing.
		this.catalogFile = catalogFile;
		
		// Load the Excel File
		FileInputStream inputStream = new FileInputStream(this.catalogFile);
		this.workBook = new XSSFWorkbook(inputStream);
		
		CLogHelper.logInfo("Loaded catalog file: " + catalogFile.getAbsolutePath());
		
		// Close the input stream, so we can write to the file again.
		inputStream.close();
		
		// Load the first sheet.
		this.sheet0 = workBook.getSheetAt(0);
		
		// Keep a reference to the total number of rows, to know when to terminate.
		this.numRows = sheet0.getPhysicalNumberOfRows();
		
		// Set the current row to the first found column of "Image File" that is null.
		setToNextAvailableRow();
		
		// Check that we have the set the row.
		if (getCurrentRow() <= 0) {
			CLogHelper.logInfo("All images are currently defined.  Modify the excel document, by removing a File Link entry.");
			throw new CImageCatalogException(IImageCatalogExceptions.EOF_CATALOG_FILE, "All images are currently defined.");
		}
		
		// Create a final instance of the CreationHelper, used for hyperlinks
		this.cellCreationHelper = workBook.getCreationHelper(); 
		
		// Setup cell style and font (Blue font, underline)
		Font hlinkfont = workBook.createFont();
		hlinkfont.setUnderline(XSSFFont.U_SINGLE);
		hlinkfont.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
		
		this.hlinkstyle = workBook.createCellStyle();
		this.hlinkstyle.setFont(hlinkfont);	
	}
	
	/**
	 * Gets the <i>next</i> image name to be used.
	 * @return
	 */
	public String getNextImageName() {
		return getConcatenatedFileName(getCurrentRow() + 1);
	}
	
	/**
	 * Gets the current image name.
	 * @return
	 */
	public String getCurrentImageName() {
		return getConcatenatedFileName(getCurrentRow());
	}
	
	/**
	 * Sets the hyperlink cell value, and then increments to the next row.
	 * 
	 * @param destinationPath Where the file was moved to.
	 * @throws CImageCatalogException If the End Of File was reached.
	 */
	public void setHyperlinkCellValue(Path destinationPath) throws CImageCatalogException {
		
		// Get a URI file path 
		String uriPath = destinationPath.toUri().toString();
		
		// Create a hyperlink
		XSSFHyperlink link = (XSSFHyperlink) cellCreationHelper.createHyperlink(HyperlinkType.URL);
		link.setAddress(uriPath);
		
		// Create the cell
		Cell directoryCell = sheet0.getRow(getCurrentRow()).createCell(COLUMN.directoryCellIndex);
		
		// Create Hyperlink and insert into sheet
		directoryCell.setCellValue(uriPath);
		directoryCell.setHyperlink(link);
		directoryCell.setCellStyle(hlinkstyle);
		
		// Try to save the file between writes
		saveCatalogFile();
	
		// Set to the next available row.
		setToNextAvailableRow();
	}
	
	/**
	 * @return the currentRow
	 */
	public int getCurrentRow() {
		return currentRow;
	}
	
	/**
	 * Try to save the excel file.
	 */
	public void saveCatalogFile() {
		
		try {
			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(catalogFile);
			workBook.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			CLogHelper.logError("Could not write Excel File, do you have it open in another window?  Exception: " + e.getMessage());
		} catch (IOException e) {
			CLogHelper.logError("Could not write Excel File.  If you are on a VPN network connection, check that you have connection.  Exception: " + e.getMessage());
		}
		
	}
	
	/**
	 * Builds a File Name from the concatenation of several columns.
	 * 
	 * @param rowNum The row number in the excel file (0 based)
	 * @return
	 */
	private String getConcatenatedFileName(int rowNum) {
		
		if (rowNum >= numRows) {
			return "End of File (EOF) reached.";
		}
		
		return "TODO_" + Integer.toString(rowNum);
	}

	/**
	 * Sets the current row.
	 * @param newRowNum the currentRow to set
	 */
	private void setCurrentRow(int newRowNum) throws CImageCatalogException {
		this.currentRow = newRowNum;
		CLogHelper.logInfo("Set current Row to: " + Integer.toString(getCurrentRow() + 1) + ", Image Name: " + getConcatenatedFileName(getCurrentRow()));
	}
	
	/**
	 * increments the current row.
	 * 
	 * @throws CImageCatalogException If the end of file is reached.
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void incrementCurrentRow() throws CImageCatalogException {
		setCurrentRow(getCurrentRow() + 1); 
	}
	
	/**
	 * Tries to set to the next available row.
	 * 
	 * @throws CImageCatalogException If the end of file is reached.
	 */
	private void setToNextAvailableRow() throws CImageCatalogException {
		
		// First check if we are at EOF
		if (getCurrentRow() >= numRows - 1) {
			throw new CImageCatalogException(IImageCatalogExceptions.EOF_CATALOG_FILE, "Reached the End of File."); 
		}
		
		for (int i = getCurrentRow(); i < numRows; i++) {
			
			Cell directoryCell = sheet0.getRow(i).getCell(COLUMN.directoryCellIndex);
			
			// Skip to next row, if there is already a hyperlink
			if (directoryCell != null) {
				continue;
			} else {
				setCurrentRow(i);
				break;
			}
			
		}
		
		
	}
	
}
