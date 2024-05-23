package com.thomsonreuters.codes.codesbench.quality.pages.source.bts;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridHeaderFiltersElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.robot.RobotUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Quotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.BtsWebUiPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.CreateNewBtsRecordPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.FindRecordPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.GenerateLegislativeServiceTablesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.GeneratePocketPartTablesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;

@Component
public class BtsWebUiPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public BtsWebUiPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, BtsWebUiPageElements.class);
	}

	public boolean clickSortBills()
	{
		click(BtsWebUiPageElements.sortBillsButton);
		
		return AutoITUtils.verifyAlertTextAndAccept(true, BtsWebUiPageElements.TOOLS_MENU_SORT_BILLS_POPUP_MESSAGE);
	}
	
	public void goToTasksTablesGenerateLegislativeServiceTables()
	{
		openTasksTablesSubmenu();
		click(BtsWebUiPageElements.tasksTablesMenuGenerateLegislativeServiceTable);
		switchToWindow(GenerateLegislativeServiceTablesPageElements.GENERATE_LEGISLATIVE_SERVICE_TABLES_PAGE_TITLE); 
		btsWebUiPage().waitForPageLoaded();

		enterTheInnerFrame();
	}
		
	public void goToTasksTablesGeneratePocketPartTables()
	{
		openTasksTablesSubmenu();
		click(BtsWebUiPageElements.tasksTablesMenuGeneratePocketPartTables);
		switchToWindow(GeneratePocketPartTablesPageElements.GENERATE_POCKET_PART_TABLES_TITLE_PAGE);
		btsWebUiPage().waitForPageLoaded();

		enterTheInnerFrame();
	}
	
	public boolean goToFileAddNewRecord()
	{
		sendEnterToElement(BtsWebUiPageElements.fileMenu);
		click(BtsWebUiPageElements.fileMenuAddNewRecord);
		boolean createNewBtsRecordPageTitle = switchToWindow(CreateNewBtsRecordPageElements.CREATE_NEW_BTS_RECORD_PAGE_TITLE);
		waitForPageLoaded();

		enterTheInnerFrame();
		return createNewBtsRecordPageTitle;
	}
	
	public boolean goToEditFind()
	{
		sendEnterToElement(BtsWebUiPageElements.editMenu);
		sendKeyToElement(BtsWebUiPageElements.editMenu, Keys.ARROW_DOWN);
		click(BtsWebUiPageElements.editFindMenuFind);
		boolean findRecordPageTitle = switchToWindow(FindRecordPageElements.FIND_RECORD_PAGE_TITLE);
		waitForPageLoaded();
		return findRecordPageTitle;
	}

	public void setJurisdictionDropdown(String jurisdiction)
	{
		//Original Code
		//selectDropdownOption(BtsWebUiPageElements.jurisdictionDropdown, jurisdiction);
		//waitForPageLoaded();
		//New Code
		selectDropdownOptionUsingJavascript("selectJurisdiction", jurisdiction);
		BtsWebUiPageElements.jurisdictionDropdown.sendKeys(Keys.DOWN);
		BtsWebUiPageElements.jurisdictionDropdown.sendKeys(Keys.UP);
		waitForPageLoaded();
	}
    
    public void goToTasksTableListServiceTables()
    {
    	click(BtsWebUiPageElements.tasksMenu);
    	Actions action = new Actions(driver());
    	action.moveToElement(BtsWebUiPageElements.tasksMenuTablesMenu)
    	.sendKeys(Keys.ARROW_RIGHT)
    	.build().perform();
    	click(BtsWebUiPageElements.tasksMenuTablesMenuListLegislativeServicesTable);
    }
    
    public void setLegislativeYear(String year)
	{
		//Original Code
    	//selectDropdownOption(BtsWebUiPageElements.yearDropdown, year);
		//waitForPageLoaded();
		//New Code
		selectDropdownOptionUsingJavascript("selectYear", year);
		BtsWebUiPageElements.yearDropdown.sendKeys(Keys.DOWN);
		BtsWebUiPageElements.yearDropdown.sendKeys(Keys.UP);
		waitForPageLoaded();
	}
    
    public void clickCancelButtonOnForm()
	{
		getElement(CommonPageElements.CANCEL_BUTTON).click();
	}
    
    public void openTasksTablesSubmenu()
    {
    	click(BtsWebUiPageElements.tasksMenu);
    	Actions action = new Actions(driver());
    	action.sendKeys(Keys.ARROW_DOWN)
        	.moveToElement(BtsWebUiPageElements.tasksMenuTablesMenu)
        	.sendKeys(Keys.ARROW_RIGHT)
        	.build().perform();
    }

	public void selectSpecificTable(String tableTitle) 
	{
		//Old Code
		//selectDropdownOption(BtsWebUiPageElements.specificOptionDropdown, tableTitle);
		//waitForPageLoaded();

		//New Code
		selectDropdownOptionUsingJavascript("selectSpecificOption", tableTitle);
		BtsWebUiPageElements.specificOptionDropdown.sendKeys(Keys.DOWN);
		BtsWebUiPageElements.specificOptionDropdown.sendKeys(Keys.UP);
		waitForPageLoaded();
	}
	
	public void LastPageIsDisplayed()
	{
		if(isElementDisplayed(BtsWebUiPageElements.LAST_BUTTON_XPATH))
		{
		    click(BtsWebUiPageElements.lastPageButton);
		}
	}
	
	public boolean  dataLoadedWithoutErrors()
	{
		return isElementDisplayed(BtsWebUiPageElements.GENERIC_TABLE_ROW);
	}
	
	public boolean isLastPageButtonDisabled() 
	{
		return isElementDisplayed(BtsWebUiPageElements.LAST_BUTTON_XPATH);
	}
	
	public boolean lastPageLoadedWithoutError()
	{
		return isElementDisplayed(BtsWebUiPageElements.GENERIC_TABLE_ROW);
	}
	
	public boolean listAndGeneratePocketTablesIsDisplayed() 
	{
		return isElementDisplayed(BtsWebUiPageElements.TASKS_TABLES_GENERATE_POCKET_PART_TABLES_MENU_XPATH) && isElementDisplayed(BtsWebUiPageElements.TASKS_TABLES_LIST_POCKET_PART_TABLES_MENU_XPATH);
	}

	public void rightClickRecord(String year, String jurisdiction, String documentType)
	{
		RobotUtils.moveMouse(getScreenMiddleHeight(), getScreenMiddleWidth());
		Actions action = new Actions(driver());
    	action.moveToElement(getElement(String.format(BtsWebUiPageElements.GENERIC_RECORD_XPATH, jurisdiction, year, documentType)))
        	.contextClick()
        	.build().perform();		
    	
	}
	
	public boolean doesRecordExist(String year, String jurisdiction, String documentType)
	{
		return isElementDisplayed(String.format(BtsWebUiPageElements.GENERIC_RECORD_XPATH, jurisdiction, year, documentType));
	}
}
