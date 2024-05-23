package com.thomsonreuters.codes.codesbench.quality.pages.audits.reportcentral;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SourceNavigateGridPageElements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.audits.reportcentral.ReportCentralGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

@Component
public class ReportCentralGridPage extends BasePage
{
	private WebDriver driver;

	@Autowired
	public ReportCentralGridPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}

	@PostConstruct
	private void init()
	{
		PageFactory.initElements(driver, ReportCentralGridPageElements.class);
	}

	/**
	 * Waits for the first workflow in the grid to finish. Checks if the text equals finished and if
	 * not, sleeps for a second and checks again. If it has been two minutes, the method returns false.
	 *
	 * @return boolean of if the workflow finishes
	 */
	public boolean verifyFirstWorkflowFinishes()
	{
		String currentText = ReportCentralGridPageElements.firstReportWorkflowStatus.getText();
		long startTime = DateAndTimeUtils.getCurrentDateTimeInMilliseconds();

		while (!currentText.equals("Finished") &&
				!DateAndTimeUtils.isDateTimesInMillisecondsEqual(startTime, DateAndTimeUtils.getCurrentDateTimeInMilliseconds(), 120000))
		{
			DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
			reportCentralPage().clickRefresh();
			currentText = getElementsText(ReportCentralGridPageElements.firstReportWorkflowStatus);
		}

		return ReportCentralGridPageElements.firstReportWorkflowStatus.getText().equals("Finished");
	}

	public boolean waitForExistenceOfWorkflow(String reportType, String auditReportIdentifier, String requestersName)
	{
		// Check the grid for a <tr> that contains several <td>s where the <td>s contain the report type, audit report id, and requester's name.
		// This will be in a loop similar to line 45 trough 51.

		String currentText = ReportCentralGridPageElements.firstReportWorkflowStatus.getText();
		long startTime = DateAndTimeUtils.getCurrentDateTimeInMilliseconds();

		while (!ReportCentralGridPageElements.firstRequesterName.equals(requestersName) &&
				!ReportCentralGridPageElements.firstReportIdentifier.equals(auditReportIdentifier) &&
				!ReportCentralGridPageElements.firstReportType.equals(reportType) &&
				!DateAndTimeUtils.isDateTimesInMillisecondsEqual(startTime, DateAndTimeUtils.getCurrentDateTimeInMilliseconds(), 120000))
		{
			DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
			reportCentralPage().clickRefresh();
		}

		return !ReportCentralGridPageElements.firstRequesterName.equals(requestersName) &&
				!ReportCentralGridPageElements.firstReportIdentifier.equals(auditReportIdentifier) &&
				!ReportCentralGridPageElements.firstReportType.equals(reportType);
	}

	public void downloadFirstEffectiveDateMetricsReport()
	{
		click(ReportCentralGridPageElements.firstEffectiveDateMetricsReport);
		AutoITUtils.clickSaveOnIEPopupWithAutoIT();
	}

	public void deleteReport()
	{
		deleteFirstReport();
	}

	public boolean deleteFirstReport()
	{
		int resultsBeforeDelete = getResults();
		rightClick(ReportCentralGridPageElements.firstReportType);
		reportCentralContextMenu().delete();
		waitForGridRefresh();
		waitForPageLoaded();
		int resultsAfterDelete = getResults();
		return resultsAfterDelete < resultsBeforeDelete;
	}

	public int getResults()
	{
		String resultsText = getElementsInnerHTML(SourceNavigateGridPageElements.RENDITION_RESULT);
		String[] resultsSplitText = resultsText.split("\\s+");
		String numberOfResultsString = resultsSplitText[0];
		int numberOfResultsInt = Integer.parseInt(numberOfResultsString);
		return numberOfResultsInt;
	}

	/**
	 *This method takes in a var args String array which allows us to sudo overload it. With no parameter passed in, this method will click on the first report identifier link
	 * With 1 parameter passed in, this method will click on the specified report identifier link if it exists
	 * @param reportLinkIdentifier
	 */
	public void clickReportIdentifierLink(String... reportLinkIdentifier)
	{
		if(reportLinkIdentifier.length == 0)
		{
			click(ReportCentralGridPageElements.firstReportIdentifier);
		}
		else if(doesElementExist(String.format(ReportCentralGridPageElements.REPORT_IDENTIFIER_LINK_WITH_GIVEN_TITLE, reportLinkIdentifier[0])))
		{
			click(String.format(ReportCentralGridPageElements.REPORT_IDENTIFIER_LINK_WITH_GIVEN_TITLE, reportLinkIdentifier[0]));
		}
		else
		{
			logger.information("The given Report Identifier was not present in the Report Central grid");
		}
	}

	public String getHREFFromReportIdentifier()
	{
		return getElementsAttribute(ReportCentralGridPageElements.firstReportIdentifierLink,"href");
	}

	public String getReportUUID()
	{
		return getElementsInnerHTML(ReportCentralGridPageElements.REPORT_UUID);
	}
}