package com.thomsonreuters.codes.codesbench.quality.tests.navigation.legal;

import com.thomsonreuters.codes.codesbench.quality.tests.navigation.UsersCommonNavigationTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToolsNavigationLegalSharedContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Query Note page opens after selecting the 'Query Note Report' menu option in the Tools Menu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToToolsQueryNoteReportLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean queryNotePageOpened = toolsMenu().goToQueryNoteReport();
		Assertions.assertTrue(queryNotePageOpened, "The Query Note page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the SAR page opens after selecting the 'Search and Replace' menu option in the Tools menu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToToolsSearchAndReplaceLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean sarPageOpened = toolsMenu().goToSearchAndReplace();
		Assertions.assertTrue(sarPageOpened, "The SAR page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Stocknote Manager page opens after selecting the 'Stock Note Report' menu option of the Tools menu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToToolsStocknoteManagerLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean stocknoteManagerPageOpened = toolsMenu().goToStockNoteManager();
		Assertions.assertTrue(stocknoteManagerPageOpened, "The Stocknote Manager page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Undo Type Selection page opens after selecting the 'Undo' menu option the Tools menu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToToolsUndoLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean undoTypeSelectionPageOpened = toolsMenu().goToUndo();
		Assertions.assertTrue(undoTypeSelectionPageOpened, "The Undo Type Selection page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Workflow Search page opens after selecting the 'Workflow Reporting System' menu option from the Tools menu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToToolsWorkflowReportingSystemLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean workflowSearchPageOpened = toolsMenu().goToWorkflowReportingSystem();
		Assertions.assertTrue(workflowSearchPageOpened, "The Workflow Search page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Redlining Compare page opens after selecting the 'Redlining Compare' menu option in the Tools menu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToToolsRedliningCompareLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		homePage().setMyContentSet("USCA(Development)");

		boolean redliningComparePageOpened = toolsMenu().goToRedliningCompare();
		Assertions.assertTrue(redliningComparePageOpened, "The Redlining Compare page did not appear when it should have");
	}
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Toolbox page appears after selecting the 'Script Maintenance (New)' menu option from the Tools menu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@EDGE
	@LEGAL
	@LOG
	public void navigateToToolsScriptMaintenanceLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean toolboxPageOpened = toolsMenu().goToScriptMaintenance();
		Assertions.assertTrue(toolboxPageOpened, "The Toolbox page did not appear when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Online Product Maintenance page loads after selecting the 'Online Product Maintenance' menu option from the Tools menu.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToToolsOnlineProductMaintenanceLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean onlineProductMaintenancePageLoaded = toolsMenu().goToOnlineProductMaintenance();
		Assertions.assertTrue(onlineProductMaintenancePageLoaded, "The Online Product Maintenance page did not load when it should have");
	}

	/**
	 * STORY/BUG - HALCYONST-13762 <br>
	 * SUMMARY - There is 'OC Extract' option after 'State Feeds' in Tools menu for the Legal user <br>
	 * USER - Legal <br>
	 */
	@Test
	@EDGE
	@LEGAL
	@LOG
	public void navigateToToolsOCExtractLegalTest()
	{
		new UsersCommonNavigationTests().navigateToToolsOCExtractTest();
	}
}
