package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.risk;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.SourceMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SourceNavigationRiskContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the context menu options for Fiscal Notes are correct for the content type FEDERAL_BILL<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
	public void sourceNavigateFederalBillFiscalNotesContextMenuOptionsRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		sourceMenu().goToSourceC2012Navigate();

		//Filter for rendition with content type of FEDERAL_BILL
		// set filters - Fiscal Notes are only available for 'BILL' or 'FEDERAL_BILL'
		// content type documents that are flagged 'None' on the Multiple/Duplicate dropdown
		// documents flagged as 'Multiple' or 'Duplicate' will not have the
		// option to sync or find and publish for fiscal notes
		sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
		sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
		sourceNavigateFiltersAndSortsPage().setFilterContentSet("FINRA");
		sourceNavigateFiltersAndSortsPage().setFilterYear("2015");
		sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APVRS");
		sourceNavigateFiltersAndSortsPage().setFilterContentType("PUBNOT");
		sourceNavigateFooterToolsPage().refreshTheGrid();

		sourceNavigateGridPage().rightClickFirstRendition();
		boolean syncOptionEnabled = renditionContextMenu().isElementEnabled(RenditionContextMenuElements.sync);

		renditionContextMenu().openModifySubMenu();
		boolean findFiscalNotesAndPublishOptionEnabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.modifyFindFiscalNotesAndPublish);

		Assertions.assertAll
		(
				() -> Assertions.assertTrue(syncOptionEnabled, "The 'Sync' context menu option was not enabled when it should have been"),
				() -> Assertions.assertTrue(findFiscalNotesAndPublishOptionEnabled,"The 'Find Fiscal Notes and Publish' context menu option is enabled when it should not have been")
		);
	}
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Lock Report page opens after selecting the 'Lock Report' menu option from the Source -> C2012 Reports submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
	public void navigateToC2012ReportsLockReportRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

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
	public void navigateToC2012NavigateRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean pendingRenditionNavigatePageLoaded = sourceMenu().goToSourceC2012Navigate();
		Assertions.assertTrue(pendingRenditionNavigatePageLoaded, "The Pending Rendition Navigate page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test is a negative test that verifies that certain options under the Source menu do not appear for a Risk user<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
	public void negativeNavigateToSourceRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		sourceMenu().openMenu();

		boolean sharedDeltaReportMenuOptionAppears = sourceMenu().isElementDisplayed(SourceMenuElements.SHARED_DELTA_REPORT_XPATH);
		boolean c2012PrintNavigateMenuOptionAppears = sourceMenu().isElementDisplayed(SourceMenuElements.C2012_PRINT_NAVIGATE_XPATH);
		boolean c2012BtsMenuOptionAppears = sourceMenu().isElementDisplayed(SourceMenuElements.C2012_BTS_XPATH);
		boolean novusExtractsMenuOptionAppears = sourceMenu().isElementDisplayed(SourceMenuElements.NOVUS_EXTRACTS_XPATH);
		boolean legoReportsMenuOptionAppears = sourceMenu().isElementDisplayed(SourceMenuElements.LEGO_REPORTS_XPATH);
		boolean legoAllDeltasWithSharedTargetOptionAppears = sourceMenu().isElementDisplayed(SourceMenuElements.LEGO_ALL_DELTAS_WITH_SHARED_TARGET_XPATH);
		
		Assertions.assertAll
		(
			() -> Assertions.assertFalse(sharedDeltaReportMenuOptionAppears, "The 'Shared Delta Report' menu option appeared when it should not have"),
			() -> Assertions.assertFalse(c2012PrintNavigateMenuOptionAppears, "The 'C2012 Print Navigate' menu option appeared when it should not have"),
			() -> Assertions.assertFalse(c2012BtsMenuOptionAppears, "The 'C2012 BTS' menu option appeared when it should not have"),
			() -> Assertions.assertFalse(novusExtractsMenuOptionAppears, "The 'Novus Extracts' menu option appeared when it should not have"),
			() -> Assertions.assertFalse(legoReportsMenuOptionAppears, "The 'Lego Reports' menu option appeared when it should not have"),
			() -> Assertions.assertFalse(legoAllDeltasWithSharedTargetOptionAppears, "The 'Lego All Deltas with Shared Target' menu option appeared when it should not have")
		);
	}
}
