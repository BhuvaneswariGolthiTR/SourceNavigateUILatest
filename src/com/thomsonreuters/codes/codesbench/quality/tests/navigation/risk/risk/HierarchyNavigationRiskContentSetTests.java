package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.risk;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.HierarchyMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HierarchyNavigationRiskContentSetTests extends TestService 
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Locked Nodes Report page opens after selecting 'Locked Nodes Report' in the Hierarchy -> Reports submenu<br>
	 * USER - RISK <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateLockedNodesReportRiskTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean lockedNodesReportPageOpened = hierarchyMenu().goToLockedNodesReport();
		Assertions.assertTrue(lockedNodesReportPageOpened, "The Locked Nodes Report page did not open when it should have");
	}

	/**
	 * STORY/BUG - n/a <br> 
	 * SUMMARY - This test verifies that the Hierarchy Edit page loads after selecting the 'Navigate' menu option from the Hierarchy menu<br>
	 * USER - RISK <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToNavigateRiskTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean hierarchyEditPageLoaded = hierarchyMenu().goToNavigate();
		Assertions.assertTrue(hierarchyEditPageLoaded, "The Hierarchy Edit page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Hierarchy Edit (PUB) page loads after selecting the 'Pub Navigate' menu option from the Hierarchy menu<br>
	 * USER - RISK <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToPubNavigateRiskTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean hierarchyEditPubPageLoaded = hierarchyMenu().goToPubNavigate();
		Assertions.assertTrue(hierarchyEditPubPageLoaded, "The Hierarchy Edit (PUB) page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the New Nodes Report page opens after selecting the 'New Nodes Report' menu option from the Hierarchy -> Reports submenu<br>
	 * USER - RISK <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToNewNodesReportRiskTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean newNodesReportPageOpened = hierarchyMenu().goToHierarchyReportsNewNodesReport();
		Assertions.assertTrue(newNodesReportPageOpened, "The New Nodes Report page did not open when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Create Virtual Rule Book Report page opens after selecting the 'Virtual Rule Book Report' menu option in the Hierarchy -> Reports submenu<br>
	 * USER - RISK <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToVirtualRuleBookReportRiskTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean virtualRuleBookReportPageOpens = hierarchyMenu().goToHierarchyReportsVirtualRuleBookReport();
		Assertions.assertTrue(virtualRuleBookReportPageOpens, "The Create Virtual Rule Book Report page did not open when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Source Documents In Hierarchy Report page opens after selecting the 'Source Documents In Hierarchy Report' menu option from the Hierarchy -> Reports submenu<br>
	 * USER - RISK <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToSourceDocumentsInHierarchyReportRiskTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean sourceDocumentsInHierarchyReportPageOpened = hierarchyMenu().goToReportsSourceDocumentsInHierarchyReport();
		Assertions.assertTrue(sourceDocumentsInHierarchyReportPageOpened, "The Source Documents In Hierarchy Report page did not open when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This is a negative test to verify that certain Hierarchy submenu options don't appear<br>
	 * USER - RISK <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void negativeNavigateToHierarchyRiskTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		hierarchyMenu().openMenu();

		boolean recompNavigationOptionAvailable  = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_RECOMP_NAVIGATION);

		hierarchyMenu().openReportsSubMenu();

		boolean keyciteDatesReportOptionAvailable  = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_KEYCITE_DATES_REPORT);
		boolean scriptListingReportOptionAvailable  = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_SCRIPT_LISTING_REPORT);
		boolean virtualRuleBookOptionAvailable = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_NAVIGATE_TO_VIRTUAL_RULEBOOK);
		boolean targetTagReportOptionAvailable  = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_TARGET_TAGS_REPORT);

		Assertions.assertAll
		(
				() -> Assertions.assertFalse(recompNavigationOptionAvailable, "The 'Recomp Navigation' context menu option was displayed when it should not have been"),
				() -> Assertions.assertFalse(keyciteDatesReportOptionAvailable, "The 'Keycite Dates Report' context menu option was displayed when it should not have been"),
				() -> Assertions.assertFalse(scriptListingReportOptionAvailable, "The 'Script Listing Report' context menu option was displayed when it should not have been"),
				() -> Assertions.assertFalse(virtualRuleBookOptionAvailable, "The 'Virtual Rule Books' context menu option was displayed when it should not have been"),
				() -> Assertions.assertFalse(targetTagReportOptionAvailable, "The 'Target Tags Report' context menu option was displayed when it should not have been")
		);
	}
}
