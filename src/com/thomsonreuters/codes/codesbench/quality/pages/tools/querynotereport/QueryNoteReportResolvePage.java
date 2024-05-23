package com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereport;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport.QueryNoteReportPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport.QueryNoteReportResolvePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class QueryNoteReportResolvePage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public QueryNoteReportResolvePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{ 
		PageFactory.initElements(driver, QueryNoteReportResolvePageElements.class);
	}

	/**
	 * Sets the query note status.
	 *
	 * @param status the status
	 */
	public void setQueryNoteStatus(String status)
	{
		selectDropdownOption(QueryNoteReportResolvePageElements.queryNoteStatusSelect, status);
	}

	/**
	 * Selects the first resolution comment.
	 */
	public void selectFirstQueryNoteResolutionComment()
	{
		click(QueryNoteReportResolvePageElements.queryNoteResolutionCommentFirstOption);
	}

	/**
	 * Sets the first resolution comment.
	 *
	 * @param comment the comment
	 */
	public void setFirstQueryNoteResolutionComment(String comment)
	{
		selectDropdownOption(QueryNoteReportResolvePageElements.queryNoteResolutionCommentFirstOptionSelect, comment);
	}

	/**
	 * Clicks the save button.
	 */
	public void clickSaveButton()
	{
		click(QueryNoteReportResolvePageElements.saveButton);
		switchToWindow(QueryNoteReportPageElements.QUERY_NOTE_TITLE);
		waitForGridRefresh();
	}
	
	public void clickOkButton()
	{
		click(QueryNoteReportResolvePageElements.okButton);
		switchToWindow(QueryNoteReportPageElements.QUERY_NOTE_TITLE);
		waitForGridRefresh();
	}

	/**
	 * Selects the resolved query note status.
	 */
	public void selectResolvedQueryNoteStatus()
	{
		selectDropdownOption(QueryNoteReportResolvePageElements.queryNoteStatusSelect, "RESOLVED");
	}
}
