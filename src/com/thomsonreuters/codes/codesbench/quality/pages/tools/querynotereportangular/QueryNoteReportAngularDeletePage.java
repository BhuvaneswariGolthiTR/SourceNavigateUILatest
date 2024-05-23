package com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereportangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularDeletePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularEditDeleteResolveCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularPageElements;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QueryNoteReportAngularDeletePage extends QueryNoteReportAngularEditDeleteResolveCommonPage
{
	private WebDriver driver;
	
	@Autowired
	public QueryNoteReportAngularDeletePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, QueryNoteReportAngularDeletePageElements.class);
		PageFactory.initElements(driver, QueryNoteReportAngularEditDeleteResolveCommonPageElements.class);
	}

	@Override
	public void clickSubmitButton()
	{
		click(QueryNoteReportAngularEditDeleteResolveCommonPageElements.submitButton);
		boolean isPageGone = waitForElementGone(QueryNoteReportAngularDeletePageElements.QUERY_NOTE_DELETE_PAGE_XPATH);
		Assertions.assertTrue(isPageGone, "Clicking submit button did not close the 'Delete Query Note' modal when it should have");
		switchToWindow(QueryNoteReportAngularPageElements.QUERY_NOTE_TITLE);
		waitForGridRefresh();
	}
	public void clickDeleteButton()
	{
		click(QueryNoteReportAngularEditDeleteResolveCommonPageElements.saveButton);
		boolean isPageGone = waitForElementGone(QueryNoteReportAngularDeletePageElements.QUERY_NOTE_DELETE_PAGE_XPATH);
		Assertions.assertTrue(isPageGone, "Clicking submit button did not close the 'Delete Query Note' modal when it should have");
		switchToWindow(QueryNoteReportAngularPageElements.QUERY_NOTE_TITLE);
		waitForGridRefresh();
	}
}
