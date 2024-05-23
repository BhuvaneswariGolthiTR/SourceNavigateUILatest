package com.thomsonreuters.codes.codesbench.quality.pages.tools.effectivedatemetricsreport;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.effectivedatemetricsreport.EffectiveDateMetricsReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.XlsxFileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.lists.ListUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class EffectiveDateMetricsReportPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public EffectiveDateMetricsReportPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, EffectiveDateMetricsReportPageElements.class);
	}
	
	/**
	 * Generates the effective date metrics report.
	 */
	public void generateReport()
	{
		click(EffectiveDateMetricsReportPageElements.generateReportButton);
		waitForPageLoaded();
	}
	
	/**
	 * Closes the effective date metrics report modal window.
	 */
	public void closeWindow()
	{
		breakOutOfFrame();
		sendEnterToElement(EffectiveDateMetricsReportPageElements.closeEffectiveDateMetricsModalButton);
		waitForPageLoaded();
	}
	
	public XSSFWorkbook generateWorkbook(String filePath)
	{
		XSSFWorkbook workbook = null;
		try 
		{
			workbook = new XSSFWorkbook(new FileInputStream(filePath));
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return workbook;
	}
	
	/**
	 * Verifies the metrics report columns in dashboard laws sheet have the correct headers.
	 *
	 * @param year year of report
	 * @return boolean on whether they are equal
	 */
	public boolean verifyMetricsReportColumsInDashboardLaws(XSSFWorkbook workbook, String year)
	{
		return verifyMetricsReportDashboardColumns(workbook, year, EffectiveDateMetricsReportPageElements.METRICS_REPORT_DASHBOARD_LAWS_SHEET_NAME_EXCEL);
	}
	
	/**
	 * Verifies the metrics report columns in dashboard orders sheet have the correct headers.
	 *
	 * @param year year of report
	 * @return boolean on whether they are equal
	 */
	public boolean verifyMetricsReportColumsInDashboardOrders(XSSFWorkbook workbook, String year)
	{
		return verifyMetricsReportDashboardColumns(workbook, year, EffectiveDateMetricsReportPageElements.METRICS_REPORT_DASHBOARD_ORDERS_SHEET_NAME_EXCEL);
	}
	
	/**
	 * Verifies the metrics report columns in dashboard regulations sheet have the correct headers.
	 *
	 * @param year year of report
	 * @return boolean on whether they are equal
	 */
	public boolean verifyMetricsReportColumsInDashboardRegulations(XSSFWorkbook workbook, String year)
	{
		return verifyMetricsReportDashboardColumns(workbook, year, EffectiveDateMetricsReportPageElements.METRICS_REPORT_DASHBOARD_REGULATIONS_SHEET_NAME_EXCEL);
	}
	
	/**
	 * Verifies the metrics report columns in data sheet have the correct headers.
	 *
	 * @return boolean on whether they are equal
	 */
	public boolean verifyMetricsReportColumnsInData(XSSFWorkbook workbook, List<String> expectedColumnsData)
	{
		return verifyMetricsReportColumns(workbook, expectedColumnsData, EffectiveDateMetricsReportPageElements.METRICS_REPORT_DATA_SHEET_NAME_EXCEL);
	}
	
	/**
	 * Verifies the metrics report columns on content sheets have the correct headers. Verifies the
	 * correct headers are on the sheets for the average metrics.
	 *
	 * @param year year of report
	 * @return boolean on whether they are equal
	 */
	public boolean verifyMetricsReportContentSetSheets(String year, XSSFWorkbook workbook)
	{
		int numSheets = XlsxFileUtils.getNumberOfSheetsFromExcelFileXLSX(workbook);
		
		List<String> expectedColumnsContentSet = Arrays.asList(
						"",
						"Statutes Source Documents Effective",
						"Statutes Source Documents Effective (Regular Effectives Only)",
						"Statutes Deltas Effective",
						"Statutes Deltas Effective (Reg. Eff. Only)",
						"",
						"",
						"");
		List<String> expectedAverageMetrics = Arrays.asList(
						"Average Days to Publish Source Docs",
						"Average Days to Publish Regularly Effective Source Docs",
						"Average Days to Publish Deltas",
						"Average Days to Publish Regularly Effective Deltas");
		List<String> expectedDatesList = generateMetricsReportDateList(Integer.parseInt(year));
		
		for (int i = 4; i < numSheets; i++)
		{
			XSSFSheet sheet = workbook.getSheetAt(i);
			List<String> actualColumnsContentSet = XlsxFileUtils.getAllValuesInRowByIndex(sheet, 1);
			
			if(!ListUtils.areListsEqual(expectedColumnsContentSet, actualColumnsContentSet))
			{
				logger.information(String.format("Column headers are not correct on page %d", i));
				return false;
			}

			XSSFSheet sheet2 = workbook.getSheetAt(i);
			List<String> columnValues = XlsxFileUtils.getAllValuesInColumnByIndex(sheet2, 6);
			List<String> actualAverageMetrics = columnValues.subList(0, columnValues.size() -1);
			actualAverageMetrics.removeAll(Arrays.asList("",null));

			if(!ListUtils.areListsEqual(expectedAverageMetrics, actualAverageMetrics))
			{
				logger.information(String.format("Average metrics headers are not correct on page %d", i));
				return false;
			}

			XSSFSheet sheet3 = workbook.getSheetAt(i);
			List<String> columnValues2 = XlsxFileUtils.getAllValuesInColumnByIndex(sheet3, 0);
			List<String> actualDatesList = columnValues2.subList(1, columnValues2.size() -1);
			actualDatesList.removeAll(Arrays.asList("",null));

			if(!actualDatesList.stream().allMatch(e1 -> expectedDatesList.stream().anyMatch(e2 -> e1.equals(e2))))
			{
				logger.information(String.format("Dates are not correct on page %d", i));
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Helper method for dashboard sheets columns. Makes sure they match the list of headers.
	 *
	 * @param year year of the report
	 * @param sheet sheet to look at
	 * @return boolean of if they match
	 */
	private boolean verifyMetricsReportDashboardColumns(XSSFWorkbook workbook, String year, String sheet)
	{
		List<String> expectedColumnsDashboard = Arrays.asList("",
						"Total Documents",
						"Source Documents with Deltas",
						"Effective Source Documents with Deltas",
						"Effective Source Documents with Deltas effective before " + year);
		
		return verifyMetricsReportColumns(workbook, expectedColumnsDashboard, sheet);
	}
	
	/**
	 * Verifies the expected columns headers are present on the metrics report.
	 *
	 * @param expectedColumns list of the expected columns
	 * @param sheet the name of the sheet
	 * @return boolean of if they match
	 */
	private boolean verifyMetricsReportColumns(XSSFWorkbook workbook, List<String> expectedColumns, String sheet)
	{
		XSSFSheet sheetName = workbook.getSheet(sheet);
		List<String> actualColumnsDashboard = XlsxFileUtils.getAllValuesInRowByIndex(sheetName, 0);
		
		return ListUtils.areListsEqual(expectedColumns, actualColumnsDashboard);
	}
	
	/**
	 * Gets the metrics report excel file path.
	 *
	 * @return the metrics report path
	 */
	public String getMetricsReportPath()
	{
		String metricPath = FileUtils.DOWNLOAD_FOLDER_PATH + File.separator + EffectiveDateMetricsReportPageElements.METRICS_REPORT_FILE_NAME;
		System.out.println(metricPath);
		return metricPath;
	}
	
	/**
	 * Generates the list of possible dates in the metrics report.
	 *
	 * @param year year of the metrics report
	 * @return list of the dates
	 */
	private List<String> generateMetricsReportDateList(int year)
	{
		List<String> dateList = new ArrayList<>();
		LocalDate first = DateAndTimeUtils.getFirstDateOfYear(year);
		LocalDate last = DateAndTimeUtils.getFirstDateOfYear(year + 1);
		
		if(first.getDayOfWeek() != DayOfWeek.SUNDAY & first.getDayOfWeek() != DayOfWeek.SATURDAY & first.getDayOfWeek() != DayOfWeek.MONDAY)
		{
			dateList.add(DateAndTimeUtils.formatLocalDateMMddyyy(first));
		}
		
		for (LocalDate date = first; date.isBefore(last); date = date.plusDays(1))
		{
			if(date.getDayOfWeek() == DayOfWeek.MONDAY)
			{
				dateList.add(DateAndTimeUtils.formatLocalDateMMddyyy(date));
			}
		}
		
		return dateList;
	}

	/**
	 * Deletes the metrics report file.
	 */
	public void deleteMetricsReport()
	{
		FileUtils.deleteFile(getMetricsReportPath());
	}

	/**
	 * Checks all checkboxes on the metrics report modal.
	 */
	public void checkAllCheckboxes()
	{
		enterTheInnerFrame();
		checkCheckbox(EffectiveDateMetricsReportPageElements.contentSetCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.yearCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.renditionStatusIdCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.contentTypeIdCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.classNumberCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.docNumberCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.deltaCountCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.effectiveDateCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.renditionLoadDateTimeCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.prepStartedCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.prepCompletedCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.auditStartedCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.auditCompletedCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.integrationStartedCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.integrationCompletedCheckbox);
		checkCheckbox(EffectiveDateMetricsReportPageElements.westlawLoadCheckbox);
	}
	
	/**
	 * Sets the year for the metrics report.
	 *
	 * @param year the year
	 */
	public void setYear(String year)
	{
		clearAndSendKeysToElement(EffectiveDateMetricsReportPageElements.yearTextBox, year);
	}

	public boolean waitForFileToDownload(String filePath)
	{
		return FileUtils.waitForFileToExist(filePath, DateAndTimeUtils.FIFTEEN_SECONDS);
	}
}
