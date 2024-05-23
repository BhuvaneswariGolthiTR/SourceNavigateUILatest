package com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereport;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport.QueryNoteReportDeletePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport.QueryNoteReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class QueryNoteReportDeletePage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public QueryNoteReportDeletePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, QueryNoteReportDeletePageElements.class);
	}
	
	/**
	 * Clicks the delete button.
	 */
	public void clickDeleteButton()
	{
		click(QueryNoteReportDeletePageElements.deleteButton);
		switchToWindow(QueryNoteReportPageElements.QUERY_NOTE_TITLE);
		waitForGridRefresh();
	}

	/**
	 * Clicks the ok button.
	 */
	public void clickOkButton()
	{
		waitForElementToBeClickable(QueryNoteReportDeletePageElements.okButton);
		click(QueryNoteReportDeletePageElements.okButton);
		switchToWindow(QueryNoteReportPageElements.QUERY_NOTE_TITLE);
		waitForGridRefresh();
	}
}
