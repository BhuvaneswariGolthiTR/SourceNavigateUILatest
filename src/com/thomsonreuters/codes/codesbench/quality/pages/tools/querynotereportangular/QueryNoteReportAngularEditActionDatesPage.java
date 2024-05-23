
package com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereportangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularEditActionDatesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularEditDeleteResolveCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularEditPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QueryNoteReportAngularEditActionDatesPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public QueryNoteReportAngularEditActionDatesPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, QueryNoteReportAngularEditActionDatesPageElements.class);
	}

	public void clearAndSendKeysToNewActionDateTextbox(String keys)
	{
		clearAndSendKeysToElement(QueryNoteReportAngularEditActionDatesPageElements.newActionDateTextbox, keys);
	}

	public void clickEditAndSaveButton()
	{
		click(QueryNoteReportAngularEditActionDatesPageElements.editAndSaveButton);
		boolean isPageGone = waitForElementGone(QueryNoteReportAngularEditActionDatesPageElements.QUERY_NOTE_EDIT_ACTION_DATES_PAGE_XPATH);
		Assertions.assertTrue(isPageGone, "Clicking edit and save button did not close the 'Edit Action Dates' modal when it should have");
		switchToWindow(QueryNoteReportAngularPageElements.QUERY_NOTE_TITLE);
		waitForGridRefresh();
	}
}