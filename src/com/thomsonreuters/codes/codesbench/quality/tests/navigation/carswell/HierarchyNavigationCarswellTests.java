package com.thomsonreuters.codes.codesbench.quality.tests.navigation.carswell;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CARSWELL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class HierarchyNavigationCarswellTests extends TestService
{
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies the Canada user has access to the Locked Nodes Report page after selecting 'Locked Nodes Report' in the Hierarchy -> Reports submenu.<br>
	 * USER - Carswell <br>
     */
	@Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
	public void navigateToHierarchyReportsLockedNodesReportCarswellTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean lockedNodesReportPageOpened = hierarchyMenu().goToLockedNodesReport();
		Assertions.assertTrue(lockedNodesReportPageOpened, "The Locked Nodes Report page did not appear when it should have");
	}
	
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Canada user has access to the Hierarchy Edit page after selecting the 'Navigate' menu option in the Hierarchy menu.<br>
	 * USER - Carswell <br>
     */
	@Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
	public void navigateToHierarchyNavigateCarswellTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean hierarchyEditPageLoaded = hierarchyMenu().goToNavigate();
		Assertions.assertTrue(hierarchyEditPageLoaded, "The Hierarchy Edit page did not load when it should have");
	}
	
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Canada user has access to the Hierarchy Edit (PUB) page after selecting the 'Pub Navigate' menu option from the Hierarchy menu.<br>
	 * USER - Carswell <br>
     */
	@Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
	public void navigateToHierarchyPubNavigateCarswellTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean hierarchyEditPubPageLoaded = hierarchyMenu().goToPubNavigate();
		Assertions.assertTrue(hierarchyEditPubPageLoaded, "The Hierarchy Edit (PUB) page did not load when it should have");
	}
	
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that certain Hierarchy submenu options correctly do and do not appear for Canada users.<br>
	 * USER - Carswell <br>
     */
	@Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
	public void hierarchyMenuOptionsCarswellTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean menuOptionsAreValid = hierarchyMenu().areCarswellHierarchyMenuOptionsCorrectlyDisplayed();
		Assertions.assertTrue(menuOptionsAreValid, "The Hierarchy menu options are not displayed correctly when they should be.");
	}
}
