package com.thomsonreuters.codes.codesbench.quality.tests.tools;

import java.util.Arrays;
import java.util.List;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

public class EffectiveDatesMetricsReportTests extends TestService
{
	/**
	 * STORY/BUG - HALCYONST-1320 <br>
	 * SUMMARY - Runs Effective Dates Metrics Report then downloads it and verifies the column headers
	 * of all sheets and the average metrics headers of content sheets. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void effectiveDatesMetricsReportLegalTest()
	{
		final List<String> expectedColumnsData = Arrays.asList
		(
			"Content Set",
			"Year",
			"Rendition Status ID",
			"Content Type ID",
			"Class Number",
			"Doc Number",
			"Delta Count",
			"Effective Date",
			"Rendition Load Date/Time",
			"Prep Started",
			"Prep Completed",
			"Audit Started",
			"Audit Completed",
			"Integration Started",
			"Integration Completed",
			"Westlaw Load"
		);
		final String currentYear = DateAndTimeUtils.getCurrentYearyyyy();
		final String reportType = "Effective Date Metrics Report";
		final String requestersName = user().getReportCentralRequestersUsername();
		final String metricsReportPath = effectiveDateMetricsReportPage().getMetricsReportPath();

		homePage().goToHomePage();
		loginPage().logIn();

		boolean effectiveDateMetricsReportOpened = toolsMenu().goToEffectiveDateMetricsReport();
		Assertions.assertTrue(effectiveDateMetricsReportOpened, "The Effective Date Metrics Report modal window opened.");

		effectiveDateMetricsReportPage().checkAllCheckboxes();
		effectiveDateMetricsReportPage().setYear(currentYear);
		effectiveDateMetricsReportPage().generateReport();
		effectiveDateMetricsReportPage().closeWindow();
		auditsMenu().goToReportCentral();
		reportCentralFiltersPage().setLegislativeYear(currentYear);
		reportCentralFiltersPage().setReportType(reportType);
		reportCentralFiltersPage().setRequestersName(requestersName);
		reportCentralPage().clickRefresh();
		boolean workflowFinished = reportCentralGridPage().verifyFirstWorkflowFinishes();
		Assertions.assertTrue(workflowFinished, "The workflow finished.");

		reportCentralGridPage().downloadFirstEffectiveDateMetricsReport();
		boolean fileDownloadedIn15Seconds = effectiveDateMetricsReportPage().waitForFileToDownload(metricsReportPath);
		Assertions.assertTrue(fileDownloadedIn15Seconds, "The metrics report file downloaded and took less than 15 seconds.");

		XSSFWorkbook workbook = effectiveDateMetricsReportPage().generateWorkbook(metricsReportPath);
		boolean columnsInDashboardLaws = effectiveDateMetricsReportPage().verifyMetricsReportColumsInDashboardLaws(workbook, currentYear);
		boolean columnsInDashboardOrders = effectiveDateMetricsReportPage().verifyMetricsReportColumsInDashboardOrders(workbook, currentYear);
		boolean columnsInDashboardRegulations = effectiveDateMetricsReportPage().verifyMetricsReportColumsInDashboardRegulations(workbook, currentYear);
		boolean columnsInData = effectiveDateMetricsReportPage().verifyMetricsReportColumnsInData(workbook, expectedColumnsData);
		boolean contentsInContentSets = effectiveDateMetricsReportPage().verifyMetricsReportContentSetSheets(currentYear, workbook);

		effectiveDateMetricsReportPage().deleteMetricsReport();

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(columnsInDashboardLaws, "The columns were set correctly on the dashboard laws page."),
			() -> Assertions.assertTrue(columnsInDashboardOrders, "The columns were set correctly on the dashboard orders page."),
			() -> Assertions.assertTrue(columnsInDashboardRegulations, "The columns were set correctly on the dashboard regulations page."),
			() -> Assertions.assertTrue(columnsInData, "The columns were set correctly on the data page."),
			() -> Assertions.assertTrue(contentsInContentSets, "The cells were set correctly on the content sets page.")
		);
	}
}