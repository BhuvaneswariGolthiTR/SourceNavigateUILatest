
package com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereportangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularEditDeleteResolveCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularEditPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularResolvePageElements;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QueryNoteReportAngularEditPage extends QueryNoteReportAngularEditDeleteResolveCommonPage
{
	private WebDriver driver;
	
	@Autowired
	public QueryNoteReportAngularEditPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, QueryNoteReportAngularEditPageElements.class);
	}

	public void clearAndSendKeysToQueryNoteTextbox(String keys)
	{
		WebElement element = QueryNoteReportAngularEditPageElements.queryNoteText;
		element.clear();
		element.click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript(String.format("arguments[0].value='%s'",keys), element);
		element.click();
		sendKeys(" ");
	}

	@Override
	public void clickSubmitButton()
	{
		click(QueryNoteReportAngularEditDeleteResolveCommonPageElements.submitButton);
		boolean isPageGone = waitForElementGone(QueryNoteReportAngularEditPageElements.QUERY_NOTE_EDIT_PAGE_XPATH);
		Assertions.assertTrue(isPageGone, "Clicking submit button did not close the 'Edit Query Note' modal when it should have");
		switchToWindow(QueryNoteReportAngularPageElements.QUERY_NOTE_TITLE);
		waitForGridRefresh();
	}

	public void clickSaveButton()
	{
		click(QueryNoteReportAngularEditDeleteResolveCommonPageElements.saveButton);
		boolean isPageGone = waitForElementGone(QueryNoteReportAngularResolvePageElements.QUERY_NOTE_RESOLVE_PAGE_XPATH);
		Assertions.assertTrue(isPageGone, "Clicking submit button did not close the 'Resolve Query Note' modal when it should have");
		switchToWindow(QueryNoteReportAngularPageElements.QUERY_NOTE_TITLE);
		waitForGridRefresh();
	}
}