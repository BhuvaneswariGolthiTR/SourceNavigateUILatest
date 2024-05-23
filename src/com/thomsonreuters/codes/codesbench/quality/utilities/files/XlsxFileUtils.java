package com.thomsonreuters.codes.codesbench.quality.utilities.files;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class XlsxFileUtils extends TestSetupEdge
{
    //This class is for XSSF Workbooks and sheets only

    public static XSSFWorkbook openFileAsWorkbook(String filePath)
    {
        File excelFile = new File(filePath);
        try
        {
            FileInputStream report = new FileInputStream(excelFile);
            return new XSSFWorkbook(report);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static XSSFWorkbook openFileAsWorkbook(File excelFile)
    {
        try
        {
            FileInputStream report = new FileInputStream(excelFile);
            return new XSSFWorkbook(report);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static int getNumberOfSheetsFromExcelFileXLSX(XSSFWorkbook workbook)
    {
        return workbook.getNumberOfSheets();
    }

    public static List<String> getAllValuesInRowByIndex(XSSFSheet sheet, int rowIndex)
    {
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Cell> iterator = sheet.getRow(rowIndex).cellIterator();
        List<String> rowValues = new ArrayList<>();

        while (iterator.hasNext())
        {
            Cell cell = iterator.next();
            String cellValue = dataFormatter.formatCellValue(cell);
            rowValues.add(cellValue);
        }

        return rowValues;
    }

    public static List<String> getAllValuesInColumnByIndex(XSSFSheet sheet, int columnIndex)
    {
        DataFormatter dataFormatter = new DataFormatter();
        List<String> columnValues = new ArrayList<>();
        Iterator<Row> rowIt = sheet.rowIterator();

        while (rowIt.hasNext())
        {
            Row row = rowIt.next();

            if(row.getCell(columnIndex) != null)
            {
                Cell cell = row.getCell(columnIndex);
                String cellValue = dataFormatter.formatCellValue(cell);
                columnValues.add(cellValue);
            }
        }
        return columnValues;
    }

    public static List<List<String>> getAllDataOfSheet(XSSFSheet sheet)
    {
        Iterator<Row> rowIt = sheet.rowIterator();
        List<List<String>> returnedData = new ArrayList<>();

        while (rowIt.hasNext())
        {
            Row row = rowIt.next();
            int rowIndex = row.getRowNum();
            returnedData.add(getAllValuesInRowByIndex(sheet, rowIndex));
        }

        return returnedData;
    }

    public static List<List<String>> getAllValuesForRowsInRange(XSSFSheet sheet, int firstRow, int lastRow)
    {
        List<List<String>> rowValues = new ArrayList<>();
        for (int i = firstRow; i <= lastRow; i++)
        {
            rowValues.add(getAllValuesInRowByIndex(sheet, i));
        }
        return rowValues;
    }

    public static HashMap<String, List<String>> getColumnHeaderAndValuesByIndex(XSSFSheet sheet, int columnIndex)
    {
        HashMap<String, List<String>> column = new HashMap<>();
        List<String> columnValues = getAllValuesInColumnByIndex(sheet, columnIndex);
        String header = columnValues.get(0);
        columnValues.remove(0);
        column.put(header, columnValues);
        return column;
    }

    public static HashMap<String, List<String>> getAllColumnsValues(XSSFSheet sheet)
    {
        HashMap<String, List<String>> columns = new HashMap<>();
        Iterator<Row> rowIt = sheet.rowIterator();
        Row row = rowIt.next();
        int columnIndex = 0;
        while (row.getCell(columnIndex) != null)
        {
            HashMap<String, List<String>> column = getColumnHeaderAndValuesByIndex(sheet, columnIndex);
            columns.putAll(column);
            columnIndex += 1;
        }
        return columns;
    }

    public static void closeAndDeleteDocument(XSSFWorkbook workBook, String reportFileName)
    {
        if(workBook != null)
        {
            try
            {
                workBook.close();
            }
            catch (IOException e)
            {
                logger.warning("XSSFWorkbook report download was not closed properly");
                e.printStackTrace();
            }
            FileUtils.deleteFilesByFileNameRegex(FileUtils.DOWNLOAD_FOLDER_PATH, reportFileName);
        }
    }
}
