package com.thomsonreuters.codes.codesbench.quality.pages.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.XlsxFileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

public class SubscribedCasesReport extends TestService
{

    public static List<List<String>> getReportContentForGivenNumberOfRows(String reportFilePath, int lastRow)
    {
        // grab the newest file matching wildcard
        XSSFWorkbook workbook = XlsxFileUtils.openFileAsWorkbook(reportFilePath);
        List<List<String>> rowsValues;
        if (workbook != null)
        {
            XSSFSheet sheet = workbook.getSheetAt(0);
            rowsValues = XlsxFileUtils.getAllValuesForRowsInRange(sheet, 0, lastRow);
        }
        else
        {
            rowsValues = new ArrayList<>();
        }
        return rowsValues;
    }

    public static boolean isNotesColumnInReport(List<List<String>> report)
    {
        List<String> headersRow = report.get(0);
        return headersRow.contains("Case note");
    }

    public static int getNotesColumnIndexInReport(List<List<String>> report)
    {
        List<String> headersRow = report.get(0);
        return headersRow.indexOf("Case note");
    }

    //we can't possibly check each note text, so we have to simplify the report: instead of checking notes text,
    //we will check if note exists or not. In order to fdo that we replace any note tet with "Edit note"(as on page) and
    //absent note with "Create note" - also as on page
    public static List<List<String>> returnReportCopyWithEditedNotesColumn(List<List<String>> report, int notesColumnIndex)
    {
        logger.information("started report editing");
        List<List<String>> simplifiedReport = new ArrayList<>();
        for (List<String> row : report)
        {
            List<String> editedRow = new ArrayList<>();
            for (int cellIndex = 0; cellIndex < row.size(); cellIndex++)
            {
                if (cellIndex == notesColumnIndex)
                {
                    if (row.get(cellIndex).equals(""))
                    {
                        editedRow.add("Create note");
                    }
                    else
                    {
                        editedRow.add("Edit note");
                    }
                }
                else
                {
                    editedRow.add(row.get(cellIndex));
                }
            }
            simplifiedReport.add(editedRow);
        }
        logger.information("finished report editing");
        return simplifiedReport;
    }

    public static List<List<String>> parseSubscribedCasesReport(String reportFilePath, int numberOfRowsOnScreen)
    {
        logger.information("started report parsing");
        List<List<String>> uneditedReport = getReportContentForGivenNumberOfRows(reportFilePath, numberOfRowsOnScreen);
        if (isNotesColumnInReport(uneditedReport))
        {
            int notesIndex = getNotesColumnIndexInReport(uneditedReport);
            removeHeadersFromReport(uneditedReport);
            logger.information("report parsing finished");
            return returnReportCopyWithEditedNotesColumn(uneditedReport, notesIndex);
        }
        else
        {
            removeHeadersFromReport(uneditedReport);
            logger.information("report parsing finished");
            return uneditedReport;
        }
    }

    public static List<String> findReportAndTableDataDiscrepancy(List<List<String>> reportFromXlsx, List<List<String>> dataFromPage)
    {
        logger.information("report and data discrepancy is being calculated");
        List<String> discrepancyReport = new ArrayList<>();
        if (reportFromXlsx.size() == dataFromPage.size())
        {
            for (int rowIndex = 0; rowIndex < reportFromXlsx.size(); rowIndex ++)
            {
                if (!reportFromXlsx.get(rowIndex).equals(dataFromPage.get(rowIndex)))
                {
                    String discrepancyInRow = String.format("\nDicrepancy in row %s. \nValues on the page: %s " +
                            "\nReport data: %s", rowIndex, dataFromPage.get(rowIndex), reportFromXlsx.get(rowIndex));
                    discrepancyReport.add(discrepancyInRow);
                }
            }
            logger.information("finished calculating discrepancy");
            return discrepancyReport;
        }
        else
        {
            String discrepancy = String.format("Dicrepancy in report sizes. \nNumber of rows on the page: %s " +
                    "\nNumber of rows in the report: %s", dataFromPage.size(), reportFromXlsx.size());
            discrepancyReport.add(discrepancy);
            logger.information("finished calculating discrepancy");
            return discrepancyReport;
        }
    }

    public static List<List<String>> removeHeadersFromReport(List<List<String>> report)
    {
        report.remove(0);
        return report;
    }
}

