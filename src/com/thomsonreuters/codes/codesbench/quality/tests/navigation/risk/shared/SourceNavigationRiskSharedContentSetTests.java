package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.shared;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.SourceMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SourceNavigationRiskSharedContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Lock Report page opens after selecting the 'Lock Report' menu option from the Source -> C2012 Reports submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
	public void navigateToLockReportRiskSharedTest()
	{
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);

		boolean lockReportPageLoaded = sourceMenu().goToSourceC2012ReportsLockReport();
		Assertions.assertTrue(lockReportPageLoaded, "The Lock Report page did not open when it should have");
	}
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Pending Rendition Navigate page loads after selecting the 'C2012 Navigate' menu option from the Source menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
	public void navigateToC2012NavigateRiskSharedTest()
	{
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);

		boolean pendingRenditionNavigatePageLoaded = sourceMenu().goToSourceC2012Navigate();
		Assertions.assertTrue(pendingRenditionNavigatePageLoaded, "The Pending Rendition Navigate page did not load when it should have");
	}
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test is a negative test that verifies that certain options under the Source menu do not appear for a Risk user under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
	public void negativeNavigateToSourceRiskSharedTest()
	{
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);

		sourceMenu().openMenu();

		boolean printNavigateOptionAppeared = sourceMenu().isElementDisplayed(SourceMenuElements.C2012_PRINT_NAVIGATE_XPATH);
		boolean sourceBTSOptionAppeared = sourceMenu().isElementDisplayed(SourceMenuElements.C2012_BTS_XPATH);
		boolean allDeltasWithSharedTargetAppeared = sourceMenu().isElementDisplayed(SourceMenuElements.SHARED_DELTA_REPORT_XPATH);
		boolean novusExtractsOptionAppeared = sourceMenu().isElementDisplayed(SourceMenuElements.NOVUS_EXTRACTS_XPATH);
		boolean legoReportsOptionAppeared = sourceMenu().isElementDisplayed(SourceMenuElements.LEGO_REPORTS_XPATH);
		boolean legoAllDeltasOptionAppeared = sourceMenu().isElementDisplayed(SourceMenuElements.LEGO_ALL_DELTAS_WITH_SHARED_TARGET_XPATH);

		Assertions.assertAll
		(
				() -> Assertions.assertFalse(printNavigateOptionAppeared, "Source print navigate option appeared"),
				() -> Assertions.assertFalse(sourceBTSOptionAppeared, "Source BTS option appeared"),
				() -> Assertions.assertFalse(allDeltasWithSharedTargetAppeared, "All Deltas with Shared Target option appeared"),
				() -> Assertions.assertFalse(novusExtractsOptionAppeared, "Source novus extracts option appeared"),
				() -> Assertions.assertFalse(legoReportsOptionAppeared, "Source Lego Reports option appeared"),
				() -> Assertions.assertFalse(legoAllDeltasOptionAppeared, "Source Lego All Deltas option appeared")
		);
	}
}
