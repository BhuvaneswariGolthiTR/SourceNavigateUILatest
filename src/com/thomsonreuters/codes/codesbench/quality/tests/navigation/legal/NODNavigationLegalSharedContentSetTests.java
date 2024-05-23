package com.thomsonreuters.codes.codesbench.quality.tests.navigation.legal;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NODNavigationLegalSharedContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Initiate NOD Unmerged Report page opens after selecting 'Initiate NOD Unmerged Report' from the NOD -> Tools submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@EDGE
	@LEGAL
	@LOG
	public void navigateToNODToolsInitiateNODUnmergedReportLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean initiateNodUnmergedReportPageOpened = nodMenu().goToToolsInitiateNodUnmergedReport();
		Assertions.assertTrue(initiateNodUnmergedReportPageOpened, "The Initiate NOD Unmerged Report page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Import Court Team Routing page opens after selecting ''Import Court/Team Routing' from the NOD -> Tools submenu <br>
	 * USER - Legal <br>
	 */
	@Test
	@EDGE
	@LEGAL
	@LOG
	public void navigateToNODToolsImportCourtTeamRoutingLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean importCourtTeamRoutingPageOpened = nodMenu().goToToolsImportCourtTeamRouting();
		Assertions.assertTrue(importCourtTeamRoutingPageOpened, "The Import Court Team Routing page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Initiate NOD Data Validation page opens after selecting 'Initiate NOD Data Validation' from the NOD -> Tools submenu <br>
	 * USER - Legal <br>
	 */
	@Test
	@EDGE
	@LEGAL
	@LOG
	public void navigateToNODToolsInitiateDataValidationLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean initiateNodDataValidationPageOpened = nodMenu().goToToolsInitiateNodDataValidation();
		Assertions.assertTrue(initiateNodDataValidationPageOpened, "The Initiate NOD Data Validation page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Initiate NOD XUSSC Update page is opened after selecting 'Initiate XUSSC Update' from the NOD -> Tools submenu<br>
	 * USER - Legal <br>
	 */
	@Test
	@EDGE
	@LEGAL
	@LOG
	public void navigateToNODToolsInitiateXusscUpdateLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean initiateNodXusscUpdatePageOpened = nodMenu().goToToolsInitiateXusscUpdate();
		Assertions.assertTrue(initiateNodXusscUpdatePageOpened, "The Initiate NOD XUSSC Update page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Initiate NOD Batch Reorder page is opened after selecting 'Initiate NOD Batch Reorder' in the NOD -> Tools submenu <br>
	 * USER - Legal <br>
	 */
	@Test
	@EDGE
	@LEGAL
	@LOG
	public void navigateToNODToolsInitiateNodBatchReorderLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean initiateNodBatchReorderPageOpened = nodMenu().goToToolsInitiateNodBatchReorder();
		Assertions.assertTrue(initiateNodBatchReorderPageOpened, "The Initiate NOD Batch Reorder page did not appear when it should have");
	}
}
