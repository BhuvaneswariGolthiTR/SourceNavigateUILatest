package com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereport;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport.QueryNoteReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteManagerFiltersPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

@Component
public class QueryNoteReportPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public QueryNoteReportPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
    {
        PageFactory.initElements(driver, QueryNoteReportPageElements.class);
    }

	/**
	 * Closes the query note report window
	 */
	public void closeWindow()
	{
		driver.close();
	}
	
	public boolean switchToPage()
	{
		return switchToWindow(QueryNoteReportPageElements.QUERY_NOTE_TITLE);
	}
	
	/**
	 * Sets the results per page to the desired number.
	 *
	 * @param resultsPerPage
	 */
	public void setResultsPerPage(String resultsPerPage)
	{
		selectDropdownOption(QueryNoteReportPageElements.resultsPerPageSelect, resultsPerPage);
		waitForGridRefresh();
	}
	
	public void downdloadExcelFileForCurrentGridView()
	{
		// click that button
		if (getElement(QueryNoteReportPageElements.EXPORT_TO_EXCEL).isDisplayed())
		{
			click(QueryNoteReportPageElements.EXPORT_TO_EXCEL);
		}
		else
		{
			click("("+QueryNoteReportPageElements.EXPORT_TO_EXCEL+")[2]");
		}
		
		// click Save IE prompt, cruel workaround
		// AutoIt can not see the popup
		// could not implement GET request for excel download
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
		try
		{
			RobotUtils.getRobot().keyPress(KeyEvent.VK_ALT);
			RobotUtils.getRobot().keyPress(KeyEvent.VK_S);
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_ALT);
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_S);
		}
		finally
		{
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_ALT);
			RobotUtils.getRobot().keyRelease(KeyEvent.VK_S);
		}
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
	}
	
	public void refresh()
	{
		waitForElementToBeClickable(QueryNoteReportPageElements.refreshButton);
		click(QueryNoteReportPageElements.refreshButton);
		waitForGridRefresh();
		waitForPageLoaded();
	}
	
	public ArrayList<String> grabContentsFromSubscribedCasesGridExcel()
	{
		// grab the newest file matching wildcard
		File dir = new File(System.getProperty("user.home") + File.separator + "Downloads");
		File[] files = dir.listFiles((FileFilter)(new WildcardFileFilter("excel-*-subscribedCasesGridBean.xls")));
		if (files.length > 0)
		{
	        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
	    }
		else
		{
			throw new RuntimeException("No file found!");
		}
        File excel = files[0];
        // grab all it's contents
        FileInputStream report = null;
        HSSFWorkbook workbook = null;
        try 
		{
			report = new FileInputStream(excel);
			workbook = new HSSFWorkbook(report);
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
		}
		HSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		ArrayList<String> infoGrabbedFromExcel = new ArrayList<String>();
		while(rowIterator.hasNext())
		{
			StringBuilder singleDeltaInfo = new StringBuilder();
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while(cellIterator.hasNext())
			{
				Cell cell = cellIterator.next();
				singleDeltaInfo.append(cell.getStringCellValue());
			}
			infoGrabbedFromExcel.add(singleDeltaInfo.toString());
		}
		// close opened stuff and remove the excel
		try 
		{
			report.close();
			workbook.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
        excel.delete();
        
        return infoGrabbedFromExcel;
	}
}
