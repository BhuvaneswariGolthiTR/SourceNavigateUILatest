
package com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereport;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport.QueryNoteReportEditPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport.QueryNoteReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class QueryNoteReportEditPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public QueryNoteReportEditPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, QueryNoteReportEditPageElements.class);
	}
	
	/**
	 * Clicks the save button.
	 */
	public void clickSaveButton()
	{
		click(CommonPageElements.SAVE_BUTTON);
		switchToWindow(QueryNoteReportPageElements.QUERY_NOTE_TITLE);
		waitForGridRefresh();
	}

	/**
	 * Clicks the cancel button.
	 */
	public void clickCancelButton()
	{
		click(QueryNoteReportEditPageElements.cancelButton);
		switchToWindow(QueryNoteReportPageElements.QUERY_NOTE_TITLE);
		waitForGridRefresh();
	}
	
	public void addTextToQueryNoteText(String text)
	{
		QueryNoteReportEditPageElements.queryNoteText.sendKeys(text);
	}
}