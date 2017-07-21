/**
 * Company:	com.gregorygutshall
 * Author:	gutshall
 *
 *			Copyright (c) com.gregorygutshall
 *			All rights reserved. 
 */
package edu.oregonstate.forestry.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;

import junit.framework.TestCase;

/**
 * A test class for excel manipulation.
 * 
 * @author gutshall
 */
public class CApachePOITest extends TestCase {

	/**
	 * Test opening and reading values from Excel.
	 * @throws Exception
	 */
	public void testOpenReadExcelDocument() throws Exception {
		
		// Load the Excel File
		InputStream inp = new FileInputStream("./test/excelFiles/example_exceldoc_v1.xlsx");
		Workbook excelWorkBook = WorkbookFactory.create(inp);
		inp.close();
		
		// Get Sheet 0
		Sheet sheet0 = excelWorkBook.getSheetAt(0);
		
		// Read all rows in column0
		int numRows = sheet0.getPhysicalNumberOfRows();
		
		DataFormatter formatter = new DataFormatter();
		for (int i = 0; i < numRows; i++) {
			
			Row eachRow = sheet0.getRow(i);
			
			Cell cell = eachRow.getCell(0);
			
			//System.out.println(cell.getStringCellValue());
			
			//CellValue cellValue = evaluator.evaluate(cell);
			
			String text = formatter.formatCellValue(cell);
            System.out.println("Raw Cell Value: " + text);
            
            switch (cell.getCellTypeEnum()) {
	            case STRING:
	                System.out.println(cell.getRichStringCellValue().getString());
	                break;
	            case NUMERIC:
	                if (DateUtil.isCellDateFormatted(cell)) {
	                    System.out.println(cell.getDateCellValue());
	                } else {
	                    System.out.println(cell.getNumericCellValue());
	                }
	                break;
	            case BOOLEAN:
	                System.out.println(cell.getBooleanCellValue());
	                break;
	            case FORMULA:
	                System.out.println("Cell Formula: " + cell.getCellFormula());
	                switch(cell.getCachedFormulaResultTypeEnum()) {
		                case STRING:
		                    System.out.println("Last evaluated as: " + cell.getRichStringCellValue());
		                    break;
		                case NUMERIC:
		                    System.out.println("Last evaluated as: " + cell.getNumericCellValue());
		                    break;
						default:
							// Suppor other types...
							break;
	                }
	                break;
	            case BLANK:
	                System.out.println();
	                break;
	            default:
	                System.out.println();
            }
			
		}

		// Close the file stream
		excelWorkBook.close();
		
	}
	
	/**
	 * Test opening, editing, then updating the Excel file.
	 * 
	 * @throws Exception
	 */
	public void testUpdateHyperlinkToExcel() throws Exception {
		
		// Load the Excel File
		InputStream inp = new FileInputStream("./test/excelFiles/example_exceldoc_v1.xlsx");
		Workbook excelWorkBook = WorkbookFactory.create(inp);
		inp.close();
				
		// Get Sheet 0
		Sheet sheet0 = excelWorkBook.getSheetAt(0);
		
		// Create the HyperLink
		CreationHelper createHelper = excelWorkBook.getCreationHelper();
		
		
		// Setup cell style
		CellStyle hlinkstyle = excelWorkBook.createCellStyle();
		Font hlinkfont = excelWorkBook.createFont();
		hlinkfont.setUnderline(XSSFFont.U_SINGLE);
		hlinkfont.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
		hlinkstyle.setFont(hlinkfont);
		
		for (Row eachRow : sheet0) {
			
			// Skip over the Column Header
			if (eachRow.getRowNum() == 0) {
				continue;
			}
			
			Cell directoryCell = eachRow.getCell(2);
			
			// Skip to next row, if there is already a hyperlink
			if (directoryCell != null) {
				continue;
			}
			
			// Create a hyperlink
			XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.URL);
			link.setAddress(new File("./test/dropOffDir/XYZ_1500664031907.txt").toURI().toString());
			
			// Create the cell
			directoryCell = eachRow.createCell(2);
			
			// Create Hyperlink and insert into sheet
			directoryCell.setCellValue("File Link");
			directoryCell.setHyperlink(link);
			directoryCell.setCellStyle(hlinkstyle);
					
		}
		
		
		// Write the output to a file
	    FileOutputStream fileOut = new FileOutputStream("./test/excelFiles/example_exceldoc_v1.xlsx");
	    excelWorkBook.write(fileOut);
	    fileOut.close();
		
	}
	
}
