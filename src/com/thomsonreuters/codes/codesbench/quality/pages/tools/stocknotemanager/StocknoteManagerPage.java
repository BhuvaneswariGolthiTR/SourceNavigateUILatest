package com.thomsonreuters.codes.codesbench.quality.pages.tools.stocknotemanager;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.lists.ListUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.regex.RegexUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StocknoteManagerPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public StocknoteManagerPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, StocknoteManagerPageElements.class);
	}
	
	/**
	 * Closes out of the Stocknote Manager by using the driver.close() function. There is not a close
	 * button for the Stocknote Manager.
	 * 
	 * @throws 
	 */
	public void close()  
	{
		driver.close();
		waitForWindowGoneByTitle(StocknoteManagerPageElements.PAGE_TITLE);
	}
	
	/**
	 * Clicks the export hotkeys button and returns the approximate download time.
	 *
	 * @return Approximate download time.
	 */
	public String exportHotkeysToExcel()
	{
		sendEnterToElement(StocknoteManagerPageElements.exportHotkeysToExcel);
		String time = DateAndTimeUtils.getCurrentTimeHHmmss();
		DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
		return time;
	}
	
	/**
	 * Clicks the save button on the bottom ribbon for exporting the hotkeys as excel using an AutoIT
	 * script. exportHotkeysToExcel method must be called prior to this one.
	 */
	public void saveExcelFile() 
	{
		AutoITUtils.clickSaveOnIERibbonWithAutoIT();
	}
	
	/**
	 * Gets all the file names in the download folder then filters it for ones only matching the
	 * filename format. Then loops through to figure out which one has the most recent time. That
	 * filename is then returned.
	 *
	 * @return Most recent filename.
	 */
	public String getMostRecentDownloadedHotkeyFileName()
	{
		// Gets all the file names in the downloads folder and gets todays date.
		List<String> fileNames = FileUtils.getFileNamesInFolder(FileUtils.DOWNLOAD_FOLDER_PATH);
		String mostRecentTime = "000000";
		
		// Finds all the filenames matching the given regex.
		fileNames = filterFileNamesForHotkeyFiles(fileNames);
		
		// Finds the file from the ones that match with the most recent time.
		for (String fileName : fileNames)
		{
			// Gets the time from the file name and turns it to an int.
			String timeAsString = getTimeFromHotkeyFile(fileName);
			int timeAsInt = Integer.parseInt(timeAsString);
			
			// Sets the most recent time variable to current time if it is more recent.
			mostRecentTime = timeAsInt > Integer.parseInt(mostRecentTime) ? timeAsString : mostRecentTime;
		}
		
		return String.format(StocknoteManagerPageElements.HOTKEY_EXPORT_FILE_NAME, DateAndTimeUtils.getCurrentdateYYYYMMdd(), mostRecentTime);
	}
	
	/**
	 * Takes in the file name and approximate download time. This method first verifies the filename has
	 * the correct format then it verifies it was downloaded in the last five minutes.
	 *
	 * @param fileName
	 * @param approximateDownloadTime
	 * @return
	 */
	public boolean wasFileDownloaded(String fileName, String approximateDownloadTime)
	{
		String fileNameRegex = String.format(StocknoteManagerPageElements.HOTKEY_EXPORT_FILE_NAME_REGEX, DateAndTimeUtils.getCurrentdateYYYYMMdd());
		
		if(!RegexUtils.matchesRegex(fileName, fileNameRegex))
		{
			logger.information(fileName + " does not match the correct format. Must match " + fileNameRegex + ".");
			return false;
		}
		
		
		return DateAndTimeUtils.isDateTimesInMillisecondsEqual(DateAndTimeUtils.getHHmmssTimeInMilliseconds(approximateDownloadTime),
						DateAndTimeUtils.getHHmmssTimeInMilliseconds(getTimeFromHotkeyFile(fileName)), DateAndTimeUtils.FIVE_MINUTES);
	}
	
	private String getTimeFromHotkeyFile(String fileName)
	{
		return fileName.split("-")[StocknoteManagerPageElements.HOT_KEY_FILENAME_TIME_INDEX];
	}
	
	private List<String> filterFileNamesForHotkeyFiles(List<String> fileNames)
	{
		String fileNameRegex = String.format(StocknoteManagerPageElements.HOTKEY_EXPORT_FILE_NAME_REGEX, DateAndTimeUtils.getCurrentdateYYYYMMdd());
		
		return fileNames.stream().filter(fileName -> RegexUtils.matchesRegex(fileName, fileNameRegex)).collect(Collectors.toList());
	}
	
	/**
	 * Returns the number of pages in the stocknote manager.
	 *
	 * @return Number of pages
	 */
	public int getNumberOfPages()
	{
		return (int) Math.ceil((double) getNumberOfStocknotes() / stocknoteManagerFiltersPage().getNumberOfResultsPerPage());
	}
	
	/**
	 * Gets the total number of stocknotes in the stocknote manager.
	 *
	 * @return Total number of stocknotes
	 */
	public int getNumberOfStocknotes()
	{
		return Integer.parseInt(getElementsText(StocknoteManagerPageElements.totalNumberOfStocknotes).split(" ")[0].trim());
	}
	
	/**
	 * Gets the current page number.
	 *
	 * @return Current page number.
	 */
	public int getCurrentPageNumber()
	{
		return Integer.parseInt(StocknoteManagerPageElements.currentPageNumber.getText());
	}
	
	/**
	 * Clicks the refresh button on the stocknote manager and then waits for the grid to refresh.
	 */
	public void refresh()
	{
		click(StocknoteManagerPageElements.refreshButton);
		waitForGridRefresh();
		waitForPageLoaded();
	}
}
