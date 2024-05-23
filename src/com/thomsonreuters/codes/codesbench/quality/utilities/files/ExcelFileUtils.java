package com.thomsonreuters.codes.codesbench.quality.utilities.files;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class ExcelFileUtils extends TestService
{

	//I discourage you from ever using this class. Just reuse XlsxFileUtils class instead.
	//These methods are left for backwards compatibility with an old test.

	/**
	 * Gets all the data from a given row in an excel file and returns it in a list. The row is done by
	 * index.
	 * 
	 * e.g. If you want the first row you would call the method like this:
	 * getDataFromRowOfSheetInExcelFile(excelFilePath, sheetName, 0)
	 * 
	 * @param excelFilePath
	 * @param sheetName
	 * @param row
	 * @return
	 */
	public static List<String> getDataFromRowOfSheetInExcelFile(String excelFilePath, String sheetName, int row)
	{
		XSSFWorkbook workbook = XlsxFileUtils.openFileAsWorkbook(excelFilePath);
		if (workbook != null)
		{
			XSSFSheet sheet = workbook.getSheet(sheetName);
			return XlsxFileUtils.getAllValuesInRowByIndex(sheet, row);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Gets all the data from a given row in an excel file and returns it in a list. The row is done by
	 * index.
	 * 
	 * e.g. If you want the first row you would call the method like this:
	 * getDataFromRowOfSheetInExcelFile(excelFilePath, sheetName, 0)
	 * @param sheetName
	 * @param row
	 * @return
	 */
	public static List<String> getDataFromRowOfSheetInExcelFileXLSX(XSSFWorkbook workbook, String sheetName, int row)
	{
		XSSFSheet sheet = workbook.getSheet(sheetName);
		return XlsxFileUtils.getAllValuesInRowByIndex(sheet, row);
	}
	
	/**
	 * Gets all the data from the given sheet and returns it as a list of lists of strings. The inner
	 * list is one row. e.g. if you want cell(3, 4), you would do returnedData.get(3).get(4).
	 *
	 * @param excelFilePath
	 * @param sheetName
	 * @return
	 */
	public static List<List<String>> getAllDataOfSheetInExcelFile(String excelFilePath, String sheetName)
	{
		XSSFWorkbook workbook = XlsxFileUtils.openFileAsWorkbook(excelFilePath);
		if (workbook != null)
		{
			XSSFSheet sheet = workbook.getSheet(sheetName);
			return XlsxFileUtils.getAllDataOfSheet(sheet);
		}
		else
		{
			return null;
		}
	}

	
	/**
	 * Gets a list of the information of a row by index in the specified excel file and sheet by index.
	 * 
	 * @see {@link #getDataFromRowOfSheetInExcelFile}
	 *
	 * @param excelFilePath the file path
	 * @param sheetIndex the index of the sheet
	 * @param row the index of the row
	 * @return list of strings
	 */
	public static List<String> getDataFromRowOfSheetByIndexInExcelFileXLSX(String excelFilePath, int sheetIndex, int row)
	{
		XSSFWorkbook workbook = XlsxFileUtils.openFileAsWorkbook(excelFilePath);
		if (workbook != null)
		{
			XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
			return XlsxFileUtils.getAllValuesInRowByIndex(sheet, row);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Gets a column by index in a specified excel file and sheet in the excel file. You must also
	 * specify the start index. The method ignores blank cells.
	 *
	 * @param excelFilePath path to the file
	 * @param sheetIndex index of the sheet
	 * @param column column index
	 * @param startIndex row to start at
	 * @return list of data
	 */
	public static List<String> getDataFromColumnOfSheetByIndexInExcelFileXLSX(String excelFilePath, int sheetIndex, int column, int startIndex)
	{
		XSSFWorkbook workbook = XlsxFileUtils.openFileAsWorkbook(excelFilePath);
		if (workbook != null)
		{
			XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
			List<String> columnValues = XlsxFileUtils.getAllValuesInColumnByIndex(sheet, column);
			return columnValues.subList(startIndex, columnValues.size() -1);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Gets the number of sheets in a XLSX excel file.
	 *
	 * @param excelFilePath
	 * @return
	 */
	public static int getNumberOfSheetsFromExcelFileXLSX(String excelFilePath)
	{
		XSSFWorkbook workbook = XlsxFileUtils.openFileAsWorkbook(excelFilePath);
		if (workbook != null)
		{
			return XlsxFileUtils.getNumberOfSheetsFromExcelFileXLSX(workbook);
		}
		else
		{
			return 0;
		}
	}


}
