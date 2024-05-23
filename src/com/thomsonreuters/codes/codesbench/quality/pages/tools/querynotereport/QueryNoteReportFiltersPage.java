package com.thomsonreuters.codes.codesbench.quality.pages.tools.querynotereport;

import javax.annotation.PostConstruct;


import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport.QueryNoteReportFiltersPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class QueryNoteReportFiltersPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public QueryNoteReportFiltersPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, QueryNoteReportFiltersPageElements.class);
	}

	/**
	 * Filters by query text.
	 *
	 * @param queryText the query text
	 */
	public void setFilterQueryText(String queryText)
	{
		waitForGridRefresh();
		scrollToElement(QueryNoteReportFiltersPageElements.queryTextTextBox);
		//waitForGridRefresh();
		clearAndSendKeysToElement(QueryNoteReportFiltersPageElements.queryTextTextBox, queryText);
	}

	
	public void setFilterType(String type)
	{
		waitForPageLoaded();
		waitForGridRefresh();
		sendTextToTextBoxAuto(QueryNoteReportFiltersPageElements.typeFilter, type.toUpperCase());
	}

	
	public void setFilterStatus(String status)
	{
		waitForPageLoaded();
		waitForGridRefresh();
		sendTextToTextBoxAuto(QueryNoteReportFiltersPageElements.statusFilter, status);
	}

	public void setFilterActionDate(String actionDate)
	{
		waitForPageLoaded();
		waitForGridRefresh();
		sendTextToTextBoxAuto(QueryNoteReportFiltersPageElements.actionDateFilter, actionDate);
	}

	public void setFilterActionDateToTodaysDate()
	{
		setFilterActionDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
	}

	
	public void setFilterVols(String vols)
	{
		waitForGridRefresh();
		scrollToElement(QueryNoteReportFiltersPageElements.vols);
		clearAndSendKeysToElement(QueryNoteReportFiltersPageElements.vols, vols);
	}

	public void setFilterTargetValue(String targetValue)
	{
		waitForGridRefresh();
		scrollToElement(QueryNoteReportFiltersPageElements.targetValue);
		clearAndSendKeysToElement(QueryNoteReportFiltersPageElements.targetValue, targetValue);
	}


}
