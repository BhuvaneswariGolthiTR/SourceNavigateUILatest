package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.risk;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.ToolsMenuElements;
import com.thomsonreuters.codes.codesbench.quality.tests.navigation.UsersCommonNavigationTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToolsNavigationRiskContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Query Note page opens after selecting the 'Query Note Report' menu option from the Tools menu<br>
	 * USER - Risk <br>
	*/
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void navigateToQueryNoteReportRiskTest()
    {
    	homePage().goToHomePage();
		loginPage().logIn();
    	
    	boolean queryNotePageOpened = toolsMenu().goToQueryNoteReport();
        Assertions.assertTrue(queryNotePageOpened, "The Query Note page did not open when it should have");
    }

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the SAR page opens after selecting the 'Search and Replace' menu option from the Tools menu<br>
	 * USER - Risk <br>
	*/
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void navigateToSearchAndReplaceRiskTest()
    {
    	homePage().goToHomePage();
		loginPage().logIn();
    	
    	boolean sarPageOpened = toolsMenu().goToSearchAndReplace();
        Assertions.assertTrue(sarPageOpened, "The SAR page did not open when it should have");
    }
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Stocknote Manager page opens after selecting the 'Stock Note Manager' menu option from the Tools menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void navigateToStockNoteManagerRiskTest() 
    {
    	homePage().goToHomePage();
		loginPage().logIn();
    	
    	boolean stocknoteManagerPageOpened = toolsMenu().goToStockNoteManager();
    	Assertions.assertTrue(stocknoteManagerPageOpened, "The Stocknote Manager page did not open when it should have");
    }
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Undo Type Selection page opens after selecting the 'Undo' menu option from the Tools menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void navigateToUndoRiskTest()
    {
    	homePage().goToHomePage();
		loginPage().logIn();
    	
    	boolean undoTypeSelectionPageOpened = toolsMenu().goToUndo();
    	Assertions.assertTrue(undoTypeSelectionPageOpened, "The Undo Type Selection page did not open when it should have");
    }
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Workflow Search page opens after selecting the 'Workflow Reporting System' menu option from the Tools menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void navigateToWorkflowReportingSystemRiskTest()
    {
    	homePage().goToHomePage();
		loginPage().logIn();
    	
    	boolean workflowSearchPageOpened = toolsMenu().goToWorkflowReportingSystem();
    	Assertions.assertTrue(workflowSearchPageOpened, "The Workflow Search page did not open when it should have");
    }
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Online Product Maintenance page loads after selecting the 'Online Product Maintenance' menu option from the Tools menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void navigateToOnlineProductMaintenanceRiskTest()
    {
    	homePage().goToHomePage();
		loginPage().logIn();
    	
    	boolean onlineProductMaintenancePageLoaded = toolsMenu().goToOnlineProductMaintenance();
    	Assertions.assertTrue(onlineProductMaintenancePageLoaded, "The Online Product Maintenance page did not load when it should have");
    }
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test is a negative test that verifies that certain Tools submenu options don't appear for a Risk user<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void negativeToolsMenuOptionsRiskTest()
    {
    	homePage().goToHomePage();
		loginPage().logIn();
		
		toolsMenu().openMenu();

    	boolean toolsRedliningCompareOptionAppeared = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_REDLINING_COMPARE);
    	boolean toolsScriptMaintenanceOptionAppeared = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_SCRIPT_MAINTENANCE);
    	boolean toolsCodesAuthoritySearchOptionAppeared = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_CODES_AUTHORITY);

        Assertions.assertAll
		(
			() -> Assertions.assertFalse(toolsRedliningCompareOptionAppeared, "The 'Redlining Compare' menu option appeared when it should not have"),
			() -> Assertions.assertFalse(toolsScriptMaintenanceOptionAppeared, "The 'Script Maintenance' menu option appeared when it should not have"),
			() -> Assertions.assertFalse(toolsCodesAuthoritySearchOptionAppeared, "The 'Codes Authority' menu option appeared when it should not have")
		);
    }

	/**
	 * STORY/BUG - HALCYONST-13762 <br>
	 * SUMMARY - There is 'OC Extract' option after 'State Feeds' in Tools menu for the Risk user <br>
	 * USER - Carswell <br>
	 */
	@Test
	@EDGE
	@RISK
	@LOG
	public void navigateToToolsOCExtractRiskTest()
	{
		new UsersCommonNavigationTests().navigateToToolsOCExtractTest();
	}
}