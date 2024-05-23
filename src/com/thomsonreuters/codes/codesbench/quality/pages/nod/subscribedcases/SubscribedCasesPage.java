package com.thomsonreuters.codes.codesbench.quality.pages.nod.subscribedcases;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases.EnterDateCriteriaForGridFilterPageElements;
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

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nod.subscribedcases.SubscribedCasesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.viewmanagament.ViewManagementPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.pages.table.TableTestingPage;
import com.thomsonreuters.codes.codesbench.quality.pages.table.TableTestingPage.SubscribedCasesColumns;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

@Component
public class SubscribedCasesPage extends BasePage
{
	WebDriver driver;

	@Autowired
	public SubscribedCasesPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SubscribedCasesPageElements.class);
		PageFactory.initElements(driver, ViewManagementPageElements.class);
	}
	
	/**
     * {@inheritDoc}
     */
   // @Override
    public void findCase(final String serialNumber)
    {
        //If the case is not present on the current page, then search for it by serial number
        if (!doesElementExist(String.format(SubscribedCasesPageElements.CLICKABLED_SERIAL_NUMBER, serialNumber)))
        {
            sendKeysToElement(SubscribedCasesPageElements.serialNumberSearchField, serialNumber);
            sendEnterToElement(SubscribedCasesPageElements.serialNumberSearchField);
            waitForGridRefresh();
            waitForPageLoaded();
        }
    }
    
	public ArrayList<String> createListForSubscribedCasesGridContents()
	{
		TableTestingPage tableTestingService = new TableTestingPage(driver());
		String headers = new String("LoadedDate" + "Notes" + "CaseSerialNumber"
				+ "WestlawNumber" + "ReporterCite" + "Court" + "RU" + "Reloaded" + "HN" + "Title"
				+ "SignedOutBy" + "CompletedBy" + "CompletedDate" + "CiteInformation");
		
		ArrayList<String> contents = new ArrayList<String>();
		for (int i = 0; i < getElements(SourceNavigatePageElements.RENDITIONS_XPATH).size(); i++)
		{
			StringBuilder singleLineContent = new StringBuilder();
			singleLineContent.append(tableTestingService.getCellValue(SubscribedCasesColumns.LOADED_DATE, i));
			if (doesElementExist(String.format(SubscribedCasesPageElements.NOTE_IMG_OF_NOTE_WITH_GIVEN_SERIAL_NUMBER, tableTestingService.getCellValue(SubscribedCasesColumns.CASE_SERIAL_NUMBER, i))))
			{
				click(String.format(SubscribedCasesPageElements.NOTE_IMG_OF_NOTE_WITH_GIVEN_SERIAL_NUMBER, tableTestingService.getCellValue(SubscribedCasesColumns.CASE_SERIAL_NUMBER, i)));
				enterTheInnerFrame();
				singleLineContent.append(notesPage().getNoteText());
				click(CommonPageElements.CANCEL_BUTTON);
				switchToWindow(SubscribedCasesPageElements.SUBSCRIBED_CASES_PAGE_TITLE);
			}
			singleLineContent.append(tableTestingService.getCellValue(SubscribedCasesColumns.CASE_SERIAL_NUMBER, i));
			singleLineContent.append(tableTestingService.getCellValue(SubscribedCasesColumns.WESTLAW_NUMBER, i));
			singleLineContent.append(tableTestingService.getCellValue(SubscribedCasesColumns.REPORTER_CITE, i));
			singleLineContent.append(tableTestingService.getCellValue(SubscribedCasesColumns.COURT, i));
			singleLineContent.append(tableTestingService.getCellValue(SubscribedCasesColumns.RU, i));
			singleLineContent.append(tableTestingService.getCellValue(SubscribedCasesColumns.RELOADED, i));
			if (!tableTestingService.getCellValue(SubscribedCasesColumns.HN, i).equals("0"))
			{
				singleLineContent.append(tableTestingService.getCellValue(SubscribedCasesColumns.HN, i));
			}
			singleLineContent.append(tableTestingService.getCellValue(SubscribedCasesColumns.TITLE, i));
			singleLineContent.append(tableTestingService.getCellValue(SubscribedCasesColumns.SIGNED_OUT_BY, i));
			singleLineContent.append(tableTestingService.getCellValue(SubscribedCasesColumns.COMPLETED_BY, i));
			singleLineContent.append(tableTestingService.getCellValue(SubscribedCasesColumns.COMPLETED_DATE, i));
			singleLineContent.append(tableTestingService.getCellValue(SubscribedCasesColumns.CITE_INFORMATION, i));
			contents.add(singleLineContent.toString());
		}
		
		ArrayList<String> gridContents = new ArrayList<String>();
		gridContents.add(headers);
		gridContents.addAll(contents);
		
		return gridContents;
	}

	public void downdloadExcelFileForCurrentGridView()
	{
		// click that button
		if (isElementDisplayed(SourceNavigatePageElements.EXPORT_TO_EXCEL))
		{
			click(SourceNavigatePageElements.EXPORT_TO_EXCEL);
		}
		else
		{
			click("("+SourceNavigatePageElements.EXPORT_TO_EXCEL+")[2]");
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
	
    public boolean switchToSubscribedCasesPage()
	{
		return switchToWindow(SubscribedCasesPageElements.SUBSCRIBED_CASES_PAGE_TITLE);
	}
	
    public String getCurrentViewName()
    {
    	return getElementsText(SubscribedCasesPageElements.currentView);
    }
    
    public void rightClickCase(String serialNumber)
    {
    	 rightClick(String.format(SubscribedCasesPageElements.CLICKABLED_SERIAL_NUMBER, serialNumber));
    }
    
    public void filterForReportedOrUnreportedCase(String reportedOrUnreported)
    {
    	clearAndSendKeysToElement(SubscribedCasesPageElements.reportedUreportedFilter, reportedOrUnreported);
    	sendEnterToElement(SubscribedCasesPageElements.reportedUreportedFilter);
    	waitForGridRefresh();
        waitForPageLoaded();
    }
    
    //Look into base page tweak TODO
    public boolean switchToWindowNoFail(final String wordsInTitle)
    {
		boolean found = false;
	
		waitForWindowByTitle(wordsInTitle, false);
	
		Set<String> windowHandles;
		try
		{
		    windowHandles = driver.getWindowHandles();
		}
		catch (Exception e)
		{
		    return false;
		}
	
		for (String window : windowHandles) {
		    driver.switchTo().window(window);
	
		    String title = driver.getTitle();
	
		    if (title.contains(wordsInTitle)) {
			found = true;
	
			break;
		    }
		}
	
		return found;
    }
    
    public boolean isPropertiesWindowClosed()
    {
    	return switchToWindowNoFail(SubscribedCasesPageElements.PROPERTIES_PAGE_TITLE);
    }
    
    public String getCaseSerialNumberFromFirstUnreportedSubscribedCase()
    {
    	return getElementsText(SubscribedCasesPageElements.unreportedCaseSerialNumber);
    }
    
    public boolean switchToViewManagementWindow()
    {
    	switchToWindow(SubscribedCasesPageElements.SUBSCRIBED_CASES_PAGE_TITLE);
		boolean windowAppeared = switchToWindow(ViewManagementPageElements.VIEW_MANAGEMENT_WINDOW_TITLE);
		enterTheInnerFrame();
		return windowAppeared;
    }
    
    public void filterForSerialNumber(String firstCaseSerialNumber) 
	{
		waitForGridRefresh();
		tableTestingPage().setFilterAndEnter(SubscribedCasesColumns.CASE_SERIAL_NUMBER, firstCaseSerialNumber);
	}
    
    public boolean openViewManagement()  
	{
		waitForElementToBeClickable(SubscribedCasesPageElements.viewManagementButton);
		click(SubscribedCasesPageElements.viewManagementButton);
		boolean viewManagementIsOpened = switchToWindow(ViewManagementPageElements.VIEW_MANAGEMENT_WINDOW_TITLE);
		enterTheInnerFrame();
		return viewManagementIsOpened;
	}
    
    public boolean compareExpectedView(String expectedView)  
	{
		String expectedLabel = "Current view: " + expectedView;
		String actualLabel;

		if (isElementDisplayed(SourceNavigatePageElements.VIEW_LABEL_XPATH))
		{
			actualLabel = getElementsText(SubscribedCasesPageElements.viewManagementButton);
		}
		else
		{
			actualLabel = getElementsText(("("+SourceNavigatePageElements.VIEW_LABEL_XPATH+")[2]"));
		}

		return expectedLabel.equals(actualLabel);
	}

	public void clickOnLoadedDateCalendarButton()
	{
		click(SubscribedCasesPageElements.loadedDateCalendarButton);
		switchToWindow(EnterDateCriteriaForGridFilterPageElements.POPUP_WINDOW_TITLE);
		enterTheInnerFrame();
	}
}
