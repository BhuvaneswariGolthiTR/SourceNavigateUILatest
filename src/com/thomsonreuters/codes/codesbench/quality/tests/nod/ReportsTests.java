package com.thomsonreuters.codes.codesbench.quality.tests.nod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

public class ReportsTests extends TestService
{
	/**
	 * STORY/BUGS - NA &lt;br&gt;
	 * SUMMARY -  Tests various functions of the NOD Reports Page&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
	@Test
	@LEGAL
	@LOG
	public void nodReportsTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean switchedToBrowseReports = nodMenu().goToBrowseReports();
		Assertions.assertTrue(switchedToBrowseReports, "Did not switch to Browse Reports");
		browseReportsPage().closeCurrentWindowIgnoreDialogue();

		boolean homePageWindowAppeared = homePage().switchToPage();
		Assertions.assertTrue(homePageWindowAppeared, "Home Page did not appear");

		boolean switchedToNoTeam = nodMenu().goToReportsNoTeam();
		Assertions.assertTrue(switchedToNoTeam, "The Reports No Team window did not appear");

		noTeamReportsPage().insertDate("11/30/2006");
		noTeamReportsPage().clickOk();
		noTeamReportsPage().closeCurrentWindowIgnoreDialogue();

		homePageWindowAppeared = homePage().switchToPage();
		Assertions.assertTrue(homePageWindowAppeared, "Home Page did not appear");

		boolean switchedToSummary = nodMenu().goToReportsSummary();
		Assertions.assertTrue(switchedToSummary, "Reports summary page did not appear");

		reportsSummaryPage().insertDate("11/30/2006");
		reportsSummaryPage().insertContentSet("SOS.MET");
		reportsSummaryPage().clickOk();
		reportsSummaryPage().closeCurrentWindowIgnoreDialogue();

		homePageWindowAppeared = homePage().switchToPage();
		Assertions.assertTrue(homePageWindowAppeared, "Home Page did not appear");

		boolean switchedToDetail = nodMenu().goToReportsDetail();
		Assertions.assertTrue(switchedToDetail, "The Reports Detail page did not appear");
		
		reportsDetailPage().insertDate("11/30/2006");
		reportsDetailPage().insertContentSet("SOS.MET");
		reportsDetailPage().clickOk();
	}

	/**
	 * STORY/BUGS - HALCYONST-1631  &lt;br&gt;
	 * SUMMARY -  Tests that all NOD Report windows open correctly&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
	@Test
	@LEGAL
	@LOG
	public void nodBrowseReportsReportsMovedToReportCentralTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		nodMenu().goToBrowseReports();

		boolean batchMergeReportsDisplayed = browseReportsPage().isBatchMergeReportsDisplayed();
		if(batchMergeReportsDisplayed)
		{
			Assertions.assertTrue(browseReportsPage().areAllBatchMergeReportDatesLessThen2018(), "There were more recent batch merge reports");
		}

		boolean batchReorderReportsDisplayed = browseReportsPage().isBatchReorderReportsDisplayed();
		if(batchReorderReportsDisplayed)
		{
			Assertions.assertTrue(browseReportsPage().areAllBatchReorderReportDatesLessThen2018(), "There were more recent batch reorder reports");
		}

		boolean updateReportsDisplayed = browseReportsPage().isUpdateReportsDisplayed();
		if(updateReportsDisplayed)
		{
			Assertions.assertTrue(browseReportsPage().areAllUpdateReportDatesLessThen2018(), "There were more recent update reports");
		}

		boolean xusscUpdateReportsDisplayed = browseReportsPage().isXusscUpdateReportDisplayed();
		if(xusscUpdateReportsDisplayed)
		{
			Assertions.assertTrue(browseReportsPage().areAllXusscUpdateReportDatesLessThen2018(), "There were more recent xussc update reports");
		}

		boolean unmergedReportsDisplayed = browseReportsPage().isUnmergedReportsDisplayed();
		if(unmergedReportsDisplayed)
		{
			Assertions.assertTrue(browseReportsPage().areAllUnmergedReportDatesLessThen2018(), "There were more recent unmerged reports");
		}

		boolean dataValidationReportsDisplayed = browseReportsPage().isDataValidationReportsDisplayed();
		if(dataValidationReportsDisplayed)
		{
			Assertions.assertTrue(browseReportsPage().areDataValidationReportDatesLessThen2018(), "There were more recent data validation reports");
		}
	}
}
