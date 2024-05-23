package com.thomsonreuters.codes.codesbench.quality.tests.navigation.carswell;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.ToolsMenuElements;
import com.thomsonreuters.codes.codesbench.quality.tests.navigation.UsersCommonNavigationTests;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CARSWELL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToolsNavigationCarswellTests extends TestService
{
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - Test navigation to Tools -> Stocknote Manager <br>
	 * USER - Carswell <br>
     */
	@Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
	public void navigateToToolsStocknoteManagerCarswellTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean stocknoteManagerWindowAppeared = toolsMenu().goToStockNoteManager();
		Assertions.assertTrue(stocknoteManagerWindowAppeared, "The Tools -> Stocknote Manager window did not appear when it should have");
	}
	
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - Test navigation to Tools -> Workflow Reporting System <br>
	 * USER - Carswell <br>
     */
	@Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
	public void navigateToToolsWorkflowReportingSystemCarswellTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean workflowReportingSystemWindowAppeared = toolsMenu().goToWorkflowReportingSystem();
		Assertions.assertTrue(workflowReportingSystemWindowAppeared, "The Tools -> Workflow Reporting System window did not appear when it should have");
	}
	
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - Negative tests to verify that certain Tools submenu options don't appear
     * 
     * TEST 1: Tests that Tools->Query Note Report is not an option for a Canada user
     * TEST 2: Tests that Tools->Search and Replace is not an option for a Canada user
     * TEST 3: Tests that Tools->Codes Authority is not an option for a Canada user
     * TEST 4: Tests that Tools->Script Maintenance is not an option for a Canada user
     * TEST 5: Tests that Tools->Online Product Maintenance is not an option for a Canada user
     * TEST 6: Tests that Tools->Undo is not an option for a Canada user
     * TEST 7: Tests that Tools->Redlining Compare is not an option for a Canada user <br>
	 * USER - Carswell <br>
     */
	@Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
	public void negativeNavigateToToolsCarswellTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
  		//TEST 1
    	boolean queryNoteReportMenuOptionIsDisplayed = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_QUERY_NOTE_REPORT);
    	//TEST 2
    	boolean searchAndReplaceMenuOptionIsDisplayed = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_SEARCH_AND_REPLACE);
    	//TEST 3
    	boolean codesAuthorityMenuOptionIsDisplayed = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_CODES_AUTHORITY);
    	//TEST 4
    	boolean scriptMaintenanceMenuOptionIsDisplayed = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_SCRIPT_MAINTENANCE);
    	//TEST 5
    	boolean onlineProductMaintenanceMenuOptionIsDisplayed = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_ONLINE_PRODUCT_MAINTENANCE);
    	//TEST 6
    	boolean undoMenuOptionIsDisplayed = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_UNDO);
    	//TEST 7
    	boolean redliningCompareMenuOptionIsDisplayed = toolsMenu().isElementDisplayed(ToolsMenuElements.TOOLS_MENU_REDLINING_COMPARE);
    	
    	Assertions.assertAll
		(
			() -> Assertions.assertFalse(queryNoteReportMenuOptionIsDisplayed, "The Query Note Report menu option is displayed when it should not be"),
			() -> Assertions.assertFalse(searchAndReplaceMenuOptionIsDisplayed, "The Search And Replace menu option is displayed when it should not be"),
			() -> Assertions.assertFalse(codesAuthorityMenuOptionIsDisplayed, "The Codes Authority menu option is displayed when it should not be"),
			() -> Assertions.assertFalse(scriptMaintenanceMenuOptionIsDisplayed, "The Script Maintenance menu option is displayed when it should not be"),
			() -> Assertions.assertFalse(onlineProductMaintenanceMenuOptionIsDisplayed, "The Online Product Maintenance menu option is displayed when it should not be"),
			() -> Assertions.assertFalse(undoMenuOptionIsDisplayed, "The Undo menu option is displayed when it should not be"),
			() -> Assertions.assertFalse(redliningCompareMenuOptionIsDisplayed, "The Redlining Compare menu option is displayed when it should not be")
		);
	}

	/**
	 * STORY/BUG - HALCYONST-13762 <br>
	 * SUMMARY - There is 'OC Extract' option after 'State Feeds' in Tools menu for the Carswell user <br>
	 * USER - Carswell <br>
	 */
	@Test
	@EDGE
	@CARSWELL
	@LOG
	public void navigateToToolsOCExtractCarswellTest()
	{
		new UsersCommonNavigationTests().navigateToToolsOCExtractTest();
	}
}
