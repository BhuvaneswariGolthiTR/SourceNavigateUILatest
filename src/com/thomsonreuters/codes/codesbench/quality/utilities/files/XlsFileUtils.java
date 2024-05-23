package com.thomsonreuters.codes.codesbench.quality.utilities.files;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.lists.ListUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XlsFileUtils
{
    //This class is for HSSF Workbooks and sheets only

    public static boolean compareToExcelFile(String fileName, List<List<String>> stocknoteHotkeyAndNameList)
    {
        HSSFWorkbook workbook = openFileAsHssfWorkbook(FileUtils.DOWNLOAD_FOLDER_PATH + "\\" + fileName);
        HSSFSheet sheet = workbook.getSheet(StocknoteManagerPageElements.HOTKEY_EXCEL_SHEET_NAME);
        List<List<String>> dataFromExcelFile = getAllDataOfHSSFSheet(sheet);
        //dataFromExcelFile.forEach(System.out::println);
        return ListUtils.areListsEqual2D(stocknoteHotkeyAndNameList, dataFromExcelFile);
    }

    public static HSSFWorkbook openFileAsHssfWorkbook(String filePath)
    {
        File excelFile = new File(filePath);
        try
        {
            FileInputStream report = new FileInputStream(excelFile);
            return new HSSFWorkbook(report);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static List<List<String>> getAllDataOfHSSFSheet(HSSFSheet sheet)
    {
        List<List<String>> returnedData = new ArrayList<>();
        Iterator<Row> rowIt = sheet.rowIterator();
        while (rowIt.hasNext()) {
            Row row = rowIt.next();
            int rowIndex = row.getRowNum();
            DataFormatter dataFormatter = new DataFormatter();
            Iterator<Cell> iterator = sheet.getRow(rowIndex).cellIterator();
            List<String> rowValues = new ArrayList<>();

            while (iterator.hasNext()) {
                Cell cell = iterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                rowValues.add(cellValue);
            }
            returnedData.add(rowValues);
        }
        return returnedData;
    }
}
