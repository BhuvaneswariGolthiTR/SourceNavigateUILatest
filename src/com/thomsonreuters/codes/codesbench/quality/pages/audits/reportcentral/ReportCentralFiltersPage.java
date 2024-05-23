package com.thomsonreuters.codes.codesbench.quality.pages.audits.reportcentral;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.reportcentral.ReportCentralFiltersPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class ReportCentralFiltersPage extends BasePage
{

	private WebDriver driver;

	@Autowired
	public ReportCentralFiltersPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, ReportCentralFiltersPageElements.class);
	}

	/**
	 * Sets the legislative year filter to the inputed one.
	 *
	 * @param year The legislative year
	 */
	public void setLegislativeYear(String year)
	{
		setFilterHelper(year, ReportCentralFiltersPageElements.legislativeYearFilter);
	}

	/**
	 * Sets the report type filter to the inputed one.
	 *
	 * @param reportType The report type
	 */
	public void setReportType(String reportType)
	{
		setFilterHelper(reportType, ReportCentralFiltersPageElements.reportTypeFilter);
	}

	/**
	 * Sets the report identifier filter to the inputed one
	 *
	 * @param reportIdentifier The report identifier
	 */
	public void setReportIdentifier(String reportIdentifier)
	{
		setFilterHelper(reportIdentifier, ReportCentralFiltersPageElements.reportIdentifierFilter);
	}

	/**
	 * Sets the requesters name filter to the inputed one.
	 *
	 * @param requestersName The requesters name
	 */
	public void setRequestersName(String requestersName)
	{
		setFilterHelper(requestersName, ReportCentralFiltersPageElements.requestersNameFilter);
	}

	/**
	 * Helper method to set the filters. Takes in the what filter is being applied and the element of the filter.
	 *
	 * @param value What gets inputed in the filter
	 * @param filterBox The element of the filter
	 */
	private void setFilterHelper(String value, WebElement filterBox)
	{
		clearAndSendKeysToElement(filterBox, value);
		sendEnterToElement(filterBox);
		waitForElementGone(CommonPageElements.PROCESSING_PLEASE_WAIT_XPATH);
		waitForPageLoaded();
	}

	public void sendEnterToRequestNameFilter()
	{
		sendEnterToElement(ReportCentralFiltersPageElements.requestersNameFilter);
	}
}
