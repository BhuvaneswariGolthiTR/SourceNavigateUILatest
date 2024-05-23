package com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereportangular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularEditDeleteResolveCommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereportangular.QueryNoteReportAngularResolvePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QueryNoteReportAngularResolvePage extends QueryNoteReportAngularEditDeleteResolveCommonPage
{
	private WebDriver driver;
	
	@Autowired
	public QueryNoteReportAngularResolvePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{ 
		PageFactory.initElements(driver, QueryNoteReportAngularResolvePageElements.class);
	}

	/**
	 * Selects the given query note status.
	 */
	public void selectQueryNoteStatus(String status)
	{
		selectDropdownOption(QueryNoteReportAngularResolvePageElements.queryNoteStatusSelect, status);
	}

	/**
	 * Selects the query note resolution radio button and picks the given dropdown option
	 */
	public void selectQueryNoteResolutionCommentFromDropdown(String comment)
	{
		click(QueryNoteReportAngularResolvePageElements.queryNoteResolutionCommentRadioButton);
		selectDropdownOption(QueryNoteReportAngularResolvePageElements.queryNoteResolutionCommentSelect, comment);
	}

	@Override
	public void clickSubmitButton()
	{
		click(QueryNoteReportAngularEditDeleteResolveCommonPageElements.submitButton);
		boolean isPageGone = waitForElementGone(QueryNoteReportAngularResolvePageElements.QUERY_NOTE_RESOLVE_PAGE_XPATH);
		Assertions.assertTrue(isPageGone, "Clicking submit button did not close the 'Resolve Query Note' modal when it should have");
		switchToWindow(QueryNoteReportAngularPageElements.QUERY_NOTE_TITLE);
		waitForGridRefresh();
	}
}
