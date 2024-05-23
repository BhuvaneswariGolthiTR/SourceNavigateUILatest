package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.shared;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.HierarchyMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HierarchyNavigationRiskSharedContentSetTests extends TestService
{
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Hierarchy Edit page loads after selecting the 'Navigate' menu option from the Hierarchy menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void navigateToNavigateRiskSharedTest()
    {
    	String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();
		
		homePage().setMyContentSet(iowaContentSet);

		boolean hierarchyEditPageLoaded = hierarchyMenu().goToNavigate();
		Assertions.assertTrue(hierarchyEditPageLoaded, "The Hierarchy Edit page did not load when it should have");
    }
   
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Hierarchy Edit (PUB) page loads after selecting the 'Pub Navigate' menu option from the Hierarchy menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void navigateToPubNavigateRiskSharedTest() 
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);

		boolean hierarchyEditPubPageLoaded = hierarchyMenu().goToPubNavigate();
		Assertions.assertTrue(hierarchyEditPubPageLoaded, "The Hierarchy Edit (PUB) page did not load when it should have");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the New Nodes Report page opens after selecting the 'New Nodes Report' menu option from the Hierarchy -> Reports submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void navigateToNewNodesReportRiskSharedTest() 
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);

		boolean newNodesReportPageOpened = hierarchyMenu().goToHierarchyReportsNewNodesReport();
		Assertions.assertTrue(newNodesReportPageOpened, "The New Nodes Report page did not open when it should have");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Create Virtual Rule Book Report page opens after selecting the 'Virtual Rule Book Report' menu option in the Hierarchy -> Reports submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void navigateToVirtualRuleBookReportRiskSharedTest() 
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);

		boolean virtualRuleBookReportPageOpened = hierarchyMenu().goToHierarchyReportsVirtualRuleBookReport();
		Assertions.assertTrue(virtualRuleBookReportPageOpened, "The Create Virtual Rule Book Report page did not open when it should have");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Related Rule Books page opens after selecting the 'Navigate to Related Rule Book' menu option from the Hierarchy menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void navigateToRelatedRuleBookRiskSharedTest() 
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);

		boolean relatedRuleBooksPageOpened = hierarchyMenu().goToHierarchyRelatedRuleBooks();
		Assertions.assertTrue(relatedRuleBooksPageOpened, "The Related Rule Books page did not open when it should have");
    }

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Source Documents In Hierarchy Report page opens after selecting the 'Source Documents In Hierarchy Report' menu option from the Hierarchy -> Reports submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void navigateToSourceDocumentsInHierarchyReportRiskSharedTest()
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);

		boolean sourceDocumentsInHierarchyReportPageOpened = hierarchyMenu().goToReportsSourceDocumentsInHierarchyReport();
		Assertions.assertTrue(sourceDocumentsInHierarchyReportPageOpened, "The Source Documents In Hierarchy Report page did not open when it should have");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This is a negative test to verify that certain Hierarchy submenu options don't appear for a Risk User under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void negativeNavigateToHierarchyMenuRiskSharedTest()
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);
		
		hierarchyMenu().openMenu();

		boolean recompNavigationOptionAvailable  = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_RECOMP_NAVIGATION);

		hierarchyMenu().openReportsSubMenu();

		boolean keyCiteDatesReportOptionAvailable  = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_KEYCITE_DATES_REPORT);
    	boolean lockedNodesOptionAvailable = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_LOCKED_NODES_REPORT);
		boolean scriptListingReportOptionAvailable  = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_SCRIPT_LISTING_REPORT);
		boolean virtualRuleBookOptionAvailable = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_NAVIGATE_TO_VIRTUAL_RULEBOOK);
		boolean targetTagReportOptionAvailable  = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_TARGET_TAGS_REPORT);

    	Assertions.assertAll
		(
				() -> Assertions.assertFalse(recompNavigationOptionAvailable, "The 'Recomp Navigation' context menu option was displayed when it should not have been"),
				() -> Assertions.assertFalse(keyCiteDatesReportOptionAvailable, "The 'Keycite Dates Report' context menu option was displayed when it should not have been"),
				() -> Assertions.assertFalse(lockedNodesOptionAvailable, "The 'Locked Nodes Report' context menu option was displayed when it should not have been"),
				() -> Assertions.assertFalse(scriptListingReportOptionAvailable, "The 'Script Listing Report' context menu option was displayed when it should not have been"),
				() -> Assertions.assertFalse(virtualRuleBookOptionAvailable, "The 'Virtual Rule Books' context menu option was displayed when it should not have been"),
				() -> Assertions.assertFalse(targetTagReportOptionAvailable, "The 'Target Tags Report' context menu option was displayed when it should not have been")
		);
    }
}
