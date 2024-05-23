package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.shared;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.ToolsMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToolsNavigationRiskSharedContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Query Note page opens after selecting 'Query Note Report' from the Tools menu<br>
	 * USER - Risk <br>
	*/
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void navigateToQueryNoteReportRiskTest()
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);
		
    	boolean queryNotePageOpened = toolsMenu().goToQueryNoteReport();
    	Assertions.assertTrue(queryNotePageOpened, "The Query Note page did not appear when it should have");
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
    public void navigateToWorkflowReportingSystemRiskSharedTest()
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);

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
    public void navigateToOnlineProductMaintenanceRiskSharedTest() 
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);

		boolean onlineProductMaintenancePageLoaded = toolsMenu().goToOnlineProductMaintenance();
		Assertions.assertTrue(onlineProductMaintenancePageLoaded, "The Online Product Maintenance page did not load when it should have");
    }
	
    /**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test is a negative test that verifies that certain Tools submenu options don't appear for a Risk user under a shared content set<br>
	 * USER - Risk <br>
     */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void negativeNavigateToToolsRiskSharedTest()
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);
    	
    	toolsMenu().openMenu();

    	boolean searchAndReplaceMenuOptionAppeared = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_SEARCH_AND_REPLACE);
    	boolean stockNoteManagerMenuOptionAppeared = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_QUERY_STOCK_NOTE_MANAGER);
		boolean codesAuthorityMenuOptionAppeared = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_CODES_AUTHORITY);
		boolean scriptMaintenanceMenuOptionAppeared = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_SCRIPT_MAINTENANCE);
    	boolean undoMenuOptionAppeared = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_UNDO);
		boolean redliningCompareMenuOptionAppeared = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_REDLINING_COMPARE);
    	
    	Assertions.assertAll
    	(
    		() -> Assertions.assertFalse(searchAndReplaceMenuOptionAppeared, "The 'Search and Replace' menu option appeared when it should not have"),
    		() -> Assertions.assertFalse(stockNoteManagerMenuOptionAppeared, "The 'Stock Note Manager' menu option appeared when it should not have"),
    		() -> Assertions.assertFalse(codesAuthorityMenuOptionAppeared, "The 'Codes Authority' menu option appeared when it should not have"),
    		() -> Assertions.assertFalse(scriptMaintenanceMenuOptionAppeared, "The 'Script Maintenance' menu option appeared when it should not have"),
    		() -> Assertions.assertFalse(undoMenuOptionAppeared, "The 'Undo' menu option appeared when it should not have"),
    		() -> Assertions.assertFalse(redliningCompareMenuOptionAppeared, "The 'Redlining Compare' menu option appeared when it should not have")
    	);
    }
}
