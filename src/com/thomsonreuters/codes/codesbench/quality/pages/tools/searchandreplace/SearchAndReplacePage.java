package com.thomsonreuters.codes.codesbench.quality.pages.tools.searchandreplace;

import com.thomsonreuters.codes.codesbench.quality.pageelements.home.MyHomePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.searchandreplace.CopyTableToAnotherContentSetPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.searchandreplace.SearchAndReplacePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.searchandreplace.SearchAndReplaceReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class SearchAndReplacePage extends BasePage
{
    private WebDriver driver;

    @Autowired
    public SearchAndReplacePage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
    }

    @PostConstruct
    public void init()
    {
        PageFactory.initElements(driver, SearchAndReplacePageElements.class);
    }

    public void setSearchAndReplaceTableDropdown(String tableName)
    {
        selectDropdownOption(SearchAndReplacePageElements.searchAndReplaceTableDropdown, tableName);
    }

    public void clickNewEntryButton()
    {
        click(SearchAndReplacePageElements.newEntryTableButton);
        waitForPageLoaded();
    }

    public void setSearchForPhrase(String phrase)
    {
        sendTextToTextbox(SearchAndReplacePageElements.searchPhraseTextbox, phrase);
    }

    public void setReplaceWithPhrase(String phrase)
    {
        sendTextToTextbox(SearchAndReplacePageElements.replacePhraseTextbox, phrase);
    }

    public void clickSaveButton()
    {
        click(SearchAndReplacePageElements.saveButton);
        waitForPageLoaded();
    }



    public boolean clickRefreshButtonUntilViewReportAppears()
    {
        long start = new Date().getTime();
        long millis = DateAndTimeUtils.FIVE_MINUTES;
        while (new Date().getTime() - start <= millis)
        {
            click(SearchAndReplacePageElements.refreshButton);
            if (doesElementExist(SearchAndReplacePageElements.VIEW_REPORT_LINK_XPATH))
            {
                return true;
            }
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        }
        return false;
    }

    public boolean clickRefreshButtonUntilCommittedAppears()
    {
        long start = new Date().getTime();
        long millis = DateAndTimeUtils.FIVE_MINUTES;
        while (new Date().getTime() - start <= millis)
        {
            click(SearchAndReplacePageElements.refreshButton);
            if (doesElementExist(SearchAndReplacePageElements.COMMITTED_LINK_XPATH))
            {
                return true;
            }
            DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        }
        return false;
    }

    public boolean clickViewReport()
    {
        click(SearchAndReplacePageElements.VIEW_REPORT_LINK_XPATH);
        if(waitForWindowByUrl(SearchAndReplaceReportPageElements.SEARCH_AND_REPLACE_REPORT_PAGE_URL))
        {
            waitForPageLoaded();
            enterTheInnerFrame();
            return true;
        }
        return false;
    }

    public boolean acceptDeleteTableAlert()
    {
        boolean alertAccepted = AutoITUtils.verifyAlertTextAndAccept(true, " Modal dialog present: Warning - The selected table may be associated with an existing job(s). If you delete this table all associated jobs will also be deleted. Do you want to continue?");
        waitForPageLoaded();
        return alertAccepted;
    }

    public void clickRunButton()
    {
        click(SearchAndReplacePageElements.runButton);
        waitForPageLoaded();
    }

    public void closeOutOfSARAndSwitchToHomePage()
    {
        closeCurrentWindowIgnoreDialogue();
        switchToWindow(MyHomePageElements.HOME_PAGE_TITLE);
    }

    public void setEntryName(String entryName)
    {
        sendTextToTextbox(SearchAndReplacePageElements.entryNameTextbox, entryName);
    }

    public void selectSearchAndReplaceRadioButton()
    {
       click(SearchAndReplacePageElements.searchAndReplaceRadioButton);
    }

    public void checkTextFilesCheckbox()
    {
        checkCheckbox(SearchAndReplacePageElements.textFilesCheckbox);
    }

    public boolean verifyCommittingChangesAppearsInStatusColumn()
    {
        return isElementDisplayed(SearchAndReplacePageElements.COMMITTING_CHANGES_LINK_XPATH);
    }

	public void goToPage()
	{
		switchToWindow(SearchAndReplacePageElements.SAR_PAGE_TITLE_UPPERCASE);
	}

	public void clickTablesExpandButton()
	{
		click(SearchAndReplacePageElements.tableTabExpander);
		waitForPageLoaded();
		waitForGridRefresh();
	}

	public boolean doesTableAppearInListOfTables(String tableName)
	{
		return isElementDisplayed(String.format(SearchAndReplacePageElements.TABEL_WITH_GIVEN_NAME, tableName));
	}

	public void clickOnTable(String tableName)
	{
		click(String.format(SearchAndReplacePageElements.TABEL_WITH_GIVEN_NAME, tableName));
		waitForPageLoaded();
	}

	public void clickOnTables()
	{
		click(SearchAndReplacePageElements.tableTab);
		waitForPageLoaded();
	}

    public void enterTableName(String tableName)
	{
		sendTextToTextbox(SearchAndReplacePageElements.tableName, tableName);
	}

	public void enterTableDescription(String tableDescription)
	{
		sendTextToTextbox(SearchAndReplacePageElements.tableDescription, tableDescription);
	}

	public void clickCreateButton()
	{
		click(SearchAndReplacePageElements.createTableButton);
		waitForPageLoaded();
        waitForGridRefresh();
	}

	public void clickCopyButton()
	{
		click(SearchAndReplacePageElements.copyTableButton);
	}

	public void clickDeleteButton()
	{
		click(SearchAndReplacePageElements.deleteTableButton);
	}

	public void clickCopyToAnotherContentSetButton()
	{
		click(SearchAndReplacePageElements.copyToAnotherContentSetTableButton);
		waitForPageLoaded();
        switchToInnerIFrameByName(CopyTableToAnotherContentSetPageElements.COPY_TABLE_TO_ANOTHER_CONTENT_SET_IFRAME_NAME);
	}

	public void clickFirstEntryButton()
	{
		click(SearchAndReplacePageElements.firstEntryTableButton);
	}

	public void clickLastEntryButton()
	{
		click(SearchAndReplacePageElements.lastEntryTableButton);
	}

	public void clickForceUnlockButton()
	{
		click(SearchAndReplacePageElements.forceUnlockTableButton);
	}

	//Possible move to table maintence
	public boolean doesForceUnlockButtonExist()
	{
		return isElementDisplayed(SearchAndReplacePageElements.FORCE_UNLOCK_TABLE_BUTTON);
	}
}
