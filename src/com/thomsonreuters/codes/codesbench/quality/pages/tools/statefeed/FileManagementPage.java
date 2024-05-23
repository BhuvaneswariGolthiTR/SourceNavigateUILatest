package com.thomsonreuters.codes.codesbench.quality.pages.tools.statefeed;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.statefeed.FileManagementPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.tableAngular.TablePageAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class FileManagementPage extends TablePageAngular
{
	private final WebDriver driver;

	public FileManagementPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, FileManagementPageElements.class);
	}

	public boolean isClearFiltersAndSortsClickable()
	{
		return isElementClickable(FileManagementPageElements.clearFiltersAndSorts);
	}

	public boolean isRefreshCurrentGridClickable()
	{
		return isElementClickable(FileManagementPageElements.refreshCurrentGrid);
	}

	public String getTableHeader()
	{
		return getElementsText(FileManagementPageElements.header);
	}

	public boolean isXmlFilePresent(String fileName)
	{
		return isElementDisplayed(String.format(FileManagementPageElements.XML_FILE_XPATH, fileName));
	}

	public String getRowIdFromFileName(String fileName)
	{
		return getElementsAttribute(String.format(FileManagementPageElements.XML_FILE_XPATH, fileName) + "/../../..", "row-id");
	}

	public boolean verifyExtractDateByFileName(String rowId, String date)
	{
		String text = getElementsText(String.format(FileManagementPageElements.XML_FILE_EXTRACT_DATE_BY_ROW_ID_XPATH, rowId, date));
		return text.equals(date);
	}

	public void collapseAllDataSection()
	{
		click(FileManagementPageElements.ALL_DATA_COLLAPSE_XPATH);
		waitForGridRefresh();
	}

	public void expandAllDataSection()
	{
		click(FileManagementPageElements.ALL_DATA_EXPAND_XPATH);
		waitForGridRefresh();
	}

	public void collapseTextCreditHistoricalSection()
	{
		click(FileManagementPageElements.TEXT_CREDIT_HISTORICAL_COLLAPSE_XPATH);
		waitForGridRefresh();
	}

	public void expandTextCreditHistoricalSection()
	{
		click(FileManagementPageElements.TEXT_CREDIT_HISTORICAL_EXPAND_XPATH);
		waitForGridRefresh();
	}

	public void collapseTextCreditOnly()
	{
		click(FileManagementPageElements.TEXT_CREDIT_ONLY_COLLAPSE_XPATH);
		waitForGridRefresh();
	}

	public void expandTextCreditOnly()
	{
		click(FileManagementPageElements.TEXT_CREDIT_ONLY_EXPAND_XPATH);
		waitForGridRefresh();
	}

	public void selectFile(String fileName)
	{
		click(getElement(String.format(FileManagementPageElements.FILE_CELL_BY_FILE_NAME_XPATH, fileName)));
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
	}

	public void rightClickFile(String fileName)
	{
		rightClick(String.format(FileManagementPageElements.FILE_CELL_BY_FILE_NAME_XPATH, fileName));
	}

	public boolean isTableRowSelected(String fileName)
	{
		return getElementsAttribute(String.format(FileManagementPageElements.FILE_MANAGEMENT_ROW_BY_FILE_NAME_XPATH, fileName), "class").contains("ag-row-selected");
	}

	public void selectMultipleFiles(String... fileNames)
	{
		click(String.format(FileManagementPageElements.FILE_MANAGEMENT_ROW_BY_FILE_NAME_XPATH, fileNames[0]));

		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL);
		for (int i = 1; i < fileNames.length; i++)
		{
			action.click(getElement(String.format(FileManagementPageElements.FILE_MANAGEMENT_ROW_BY_FILE_NAME_XPATH, fileNames[i])));
		}
		action.build().perform();
	}

	public void selectFilesBetween(String firstFile, String lastFile)
	{
		click(String.format(FileManagementPageElements.FILE_MANAGEMENT_ROW_BY_FILE_NAME_XPATH, firstFile));

		Actions action = new Actions(driver);
		action.keyDown(Keys.SHIFT);
		action.click(getElement(String.format(FileManagementPageElements.FILE_MANAGEMENT_ROW_BY_FILE_NAME_XPATH, lastFile)));
		action.build().perform();
	}

	public void checkSelectAllCheckbox()
	{
		checkCheckbox(FileManagementPageElements.SELECT_ALL_HEADER_CHECKBOX_XPATH);
		waitForPageLoaded();
		List<WebElement> allRows = getAllRows();
		for(int i = 0; i < allRows.size(); i++)
		{
			waitForAttributeValueContains(allRows.get(i), "class", "ag-row-selected");
		}
	}

	public void uncheckSelectAllCheckbox()
	{
		uncheckCheckbox(FileManagementPageElements.SELECT_ALL_HEADER_CHECKBOX_XPATH);
		waitForPageLoaded();
	}

	public void clickDeleteButton()
	{
		click(FileManagementPageElements.DELETE_BUTTON_XPATH);
		waitForElementExists(FileManagementPageElements.DELETE_FILE_CONFIRMATION_POPUP_XPATH);
	}

	public void acceptDeleteFileAlert()
	{
		click(FileManagementPageElements.YES_ON_DELETE_POPUP_XPATH);
		waitForElementGone(FileManagementPageElements.DELETE_FILE_CONFIRMATION_POPUP_XPATH);
		waitForPageLoaded();
	}

	public void declineDeleteFileAlert()
	{
		click(FileManagementPageElements.NO_ON_DELETE_POPUP_XPATH);
		waitForElementGone(FileManagementPageElements.DELETE_FILE_CONFIRMATION_POPUP_XPATH);
		waitForPageLoaded();
	}

	public void clickDownloadButton()
	{
		click(FileManagementPageElements.DOWNLOAD_BUTTON_XPATH);
	}

	public List<WebElement> getAllRows()
	{
		return getElements(FileManagementPageElements.ALL_ROWS_XPATH);
	}
}
