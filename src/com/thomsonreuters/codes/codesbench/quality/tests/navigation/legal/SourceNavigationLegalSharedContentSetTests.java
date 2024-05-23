package com.thomsonreuters.codes.codesbench.quality.tests.navigation.legal;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SourceNavigationLegalSharedContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Shared Delta Report page opens after selecting the 'Shared Delta Report' menu option in the Source menu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToSourceSharedDeltaReportLegalTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean sharedDeltaReportPageLoaded = sourceMenu().goToSourceSharedDeltaReport();
		Assertions.assertTrue(sharedDeltaReportPageLoaded, "The Shared Delta Report page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Lock Report page appears after selecting the 'Lock Report' menu option in the Source -> C2012 Reports submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToSourceC2012ReportsLockReportLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean lockReportPageOpened = sourceMenu().goToSourceC2012ReportsLockReport();
		Assertions.assertTrue(lockReportPageOpened, "The Lock Report page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Pending Rendition Navigate page loads after selecting the 'C2012 Navigate' menu option in the Source menu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToSourceC2012NavigateLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean pendingRenditionNavigatePageLoaded = sourceMenu().goToSourceC2012Navigate();
		Assertions.assertTrue(pendingRenditionNavigatePageLoaded, "The Pending Rendition Navigate page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Source Print page opens after selecting the 'C2012 Print Navigate' menu option from the Source menu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToSourceC2012PrintNavigateLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean sourcePrintPageOpened = sourceMenu().goToSourceC2012PrintNavigate();
		Assertions.assertTrue(sourcePrintPageOpened, "The Source Print page did not open when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the BTS Web UI page opens after selecting the 'C2012 BTS' menu option in the Source menu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToSourceC2012BTSLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean btsWebUiPageOpened = sourceMenu().goToSourceC2012BTS();
		Assertions.assertTrue(btsWebUiPageOpened, "The BTS Web UI page did not open when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Lexis Extract of Laws and Orders page opens after selecting the 'Lexis Extracts' menu option from the Source -> Novus Extracts submenu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToSourceNovusExtractsLexisExtractLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean lexisExtractOfLawsAndOrdersPageOpened = sourceMenu().goToSourceNovusExtractsLexisExtract();
		Assertions.assertTrue(lexisExtractOfLawsAndOrdersPageOpened, "The Lexis Extract of Laws and Orders page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Process Historical Extract page opens after selecting the 'Historical Extract' menu option in the Source -> Novus Extracts submenu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToSourceNovusExtractsHistoricalExtractLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean processHistoricalExtractPageOpened = sourceMenu().goToSourceNovusExtractsHistoricalExtract();
		Assertions.assertTrue(processHistoricalExtractPageOpened, "The Process Historical Extract page did not open when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the context menu options for Fiscal Notes are correct for the content type FEDERAL_BILL<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void sourceNavigateFederalBillFiscalNotesContextMenuOptionsLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		sourceMenu().goToSourceC2012Navigate();

		//Set filters for a Fiscal Note
		sourceNavigateFiltersAndSortsPage().setFilterContentSet("USCA(Development)");
		sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
		sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
		sourceNavigateFiltersAndSortsPage().setFilterYear("2015");
		sourceNavigateFiltersAndSortsPage().setFilterContentType("FEDERAL_BILL");
		sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("RDS");
		sourceNavigateFiltersAndSortsPage().setFilterDocNumber("95");

		sourceNavigateFooterToolsPage().refreshTheGrid();
		sourceNavigateGridPage().rightClickFirstRendition();

		//Check that the 'Sync' context menu option is enabled
		boolean syncOptionEnabled = renditionContextMenu().isElementEnabled(RenditionContextMenuElements.sync);

		renditionContextMenu().openModifySubMenu();

		//Check that the 'Modify' -> 'Find Fiscal Notes and Publish' context menu option is enabled
		boolean findFiscalNotesAndPublishOptionEnabled = renditionContextMenu().isElementEnabled(RenditionContextMenuElements.modifyFindFiscalNotesAndPublish);

		Assertions.assertAll
		(
				() -> Assertions.assertTrue(syncOptionEnabled, "The 'Sync' context menu option was not enabled when it should have been"),
				() -> Assertions.assertTrue(findFiscalNotesAndPublishOptionEnabled, "The 'Find Fiscal Notes and Publish' context menu option was not enabled when it should have been")
		);
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the context menu options for multiple Fiscal Notes are correct for the Content Types: BILL and FEDERAL_BILL<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void sourceNavigateFederalBillMultipleFiscalNotesContextMenuOptionsLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		sourceMenu().goToSourceC2012Navigate();

		//Set filters for a Fiscal Note with a 'Multiple' flag associated with it
		sourceNavigateFiltersAndSortsPage().setFilterContentSet("USCA(Development)");
		sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("Multiple");
		sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
		sourceNavigateFiltersAndSortsPage().setFilterYear("2015");
		sourceNavigateFiltersAndSortsPage().setFilterContentType("FEDERAL_BILL");

		sourceNavigateFooterToolsPage().refreshTheGrid();
		sourceNavigateGridPage().rightClickFirstRendition();

		//Check that the 'Sync' context menu option is disabled
		boolean syncContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.sync);

		renditionContextMenu().openModifySubMenu();

		//Check that the 'Modify' -> 'Find Fiscal Notes and Publish' context menu option is disabled
		boolean findFiscalNotesAndPublishContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.modifyFindFiscalNotesAndPublish);

		Assertions.assertAll
		(
				() -> Assertions.assertTrue(syncContextMenuOptionIsDisabled, "The 'Sync' context menu option was not disabled when it should have been"),
				() -> Assertions.assertTrue(findFiscalNotesAndPublishContextMenuOptionIsDisabled, "The 'Find Fiscal Notes and Publish' context menu option was not disabled when it should have been")
		);
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the context menu options for duplicate Fiscal Notes are correct for the Content Types: BILL and FEDERAL_BILL<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void sourceNavigateDuplicateFiscalNotesContextMenuOptionsLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		sourceMenu().goToSourceC2012Navigate();

		//Set filters for a Fiscal Note with a 'Duplicate' flag associated with it
		sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
		sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("Duplicate");
		sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
		sourceNavigateFiltersAndSortsPage().setFilterYear("2015");
		sourceNavigateFiltersAndSortsPage().setFilterContentType("BILL");

		sourceNavigateFooterToolsPage().refreshTheGrid();
		sourceNavigateGridPage().rightClickFirstRendition();

		//Check that the 'Sync' context menu option is disabled
		boolean syncContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.sync);
		renditionContextMenu().openModifySubMenu();

		//Check that the 'Modify' -> 'Find Fiscal Notes and Publish' context menu option is disabled
		boolean findFiscalNotesAndPublishContextMenuOptionIsDisabled = renditionContextMenu().isElementDisabled(RenditionContextMenuElements.modifyFindFiscalNotesAndPublish);

		Assertions.assertAll
		(
				() -> Assertions.assertTrue(syncContextMenuOptionIsDisabled, "The 'Sync' context menu option was not disabled when it should have been"),
				() -> Assertions.assertTrue(findFiscalNotesAndPublishContextMenuOptionIsDisabled, "The 'Find Fiscal Notes and Publish' context menu option was not disabled when it should have been")
		);
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the context menu options for Fiscal Notes are correct for the content type BILL.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void sourceNavigateBillFiscalNotesContextMenuOptionsLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		sourceMenu().goToSourceC2012Navigate();

		//Set filters for a Fiscal Note with content type 'BILL'
		sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
		sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
		sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
		sourceNavigateFiltersAndSortsPage().setFilterYear("2015");
		sourceNavigateFiltersAndSortsPage().setFilterContentType("BILL");

		sourceNavigateFooterToolsPage().refreshTheGrid();
		sourceNavigateGridPage().rightClickFirstRendition();

		//Check that the 'Sync' context menu option is enabled
		boolean syncContextMenuOptionIsEnabled = renditionContextMenu().isElementEnabled(RenditionContextMenuElements.sync);

		renditionContextMenu().openModifySubMenu();

		//Check that the 'Modify' -> 'Find Fiscal Notes and Publish' context menu option is enabled
		boolean findFiscalNotesAndPublishContextMenuOptionIsEnabled = renditionContextMenu().isElementEnabled(RenditionContextMenuElements.modifyFindFiscalNotesAndPublish);

		Assertions.assertAll
		(
				() -> Assertions.assertTrue(syncContextMenuOptionIsEnabled, "The 'Sync' context menu option was not enabled when it should have been"),
				() -> Assertions.assertTrue(findFiscalNotesAndPublishContextMenuOptionIsEnabled, "The 'Find Fiscal Notes and Publish' context menu option was not enabled when it should have been")
		);
	}
}
