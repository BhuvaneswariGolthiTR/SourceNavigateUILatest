package com.thomsonreuters.codes.codesbench.quality.tests.navigation.legal;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.HierarchyMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HierarchyNavigationLegalSharedContentSetTests extends TestService
{
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies the 'Locked Nodes Report' option under the 'Reports' section of the Hierarchy submenu opens the Locked Nodes Report page.<br>
	 * USER - Legal <br
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateLockedNodesReportLegalTest() 
    {
		homePage().goToHomePage();
		loginPage().logIn();

        boolean lockedNodesPageOpened = hierarchyMenu().goToLockedNodesReport();
        Assertions.assertTrue(lockedNodesPageOpened, "Locked Nodes Report page was not opened when it should have been");
    }
	
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies the 'Navigate' option under the Hierarchy menu opens the Hierarchy Edit page.<br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToNavigateLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

	    boolean hierarchyEditPageOpened = hierarchyMenu().goToNavigate();
	    Assertions.assertTrue(hierarchyEditPageOpened, "Hierarchy Edit page was not opened when it should have been");
	}
	
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies the 'Pub Navigate' option under the Hierarchy menu opens the Hierarchy Edit (PUB) page.<br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToPubNavigateLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean hierarchyEditPubPageOpened = hierarchyMenu().goToPubNavigate();
		Assertions.assertTrue(hierarchyEditPubPageOpened, "Hierarchy Edit (PUB) page was not opened when it should have been");
	}
	 
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Recomp page opens after selecting the 'Recomp Navigation' option in the Hierarchy menu<br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToRecompNavigateLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean recompPageOpened  = hierarchyMenu().goToHierarchyRecompNavigation();
		Assertions.assertTrue(recompPageOpened, "The Recomp page was not opened when it should have been");
	}

	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Create Script Listing Report page opens after selecting the 'Script Listing Report' option from the Hierarchy menu<br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToScriptListingReportLegalTest() 
    {
		homePage().goToHomePage();
		loginPage().logIn();

    	boolean createScriptListingReportPageOpened = hierarchyMenu().goToHierarchyReportsScriptListingReport();
        Assertions.assertTrue(createScriptListingReportPageOpened, "The Create Script Listing Report page did not open when it should have");
    }
	
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Target Tags Report page opens after selecting 'Target Tags Report' in the Hierarchy menu<br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToTargetTagsReportLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean targetTagReportPageOpened = hierarchyMenu().goToHierarchyReportsTargetTagsReport();
		Assertions.assertTrue(targetTagReportPageOpened, "The Target Tag Report page did not open when it should have");
	}
	
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the New Nodes Report page opens after selecting 'New Nodes Report' in the Hierarchy menu<br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToNewNodesReportLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

	    boolean newNodesReportPageOpened = hierarchyMenu().goToHierarchyReportsNewNodesReport();
	    Assertions.assertTrue(newNodesReportPageOpened, "The New Nodes Report page did not open when it should have");
	}
	
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - Negative tests to verify that certain Hierarchy submenu options don't appear <br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void negativeNavigateToHierarchyLegalTest()
    {
    	homePage().goToHomePage();
		loginPage().logIn();
		
		hierarchyMenu().openMenu();
		
    	boolean virtualRuleBookReportMenuOptionIsDisplayed = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_VIRTUAL_RULE_BOOK_REPORT);
    	boolean sourceDocumentsInHierarchyReportMenuOptionIsDisplayed = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_SOURCE_DOCUMENTS_IN_HIERARCHY_REPORT);
    	boolean navigateToRelatedRuleBookMenuOptionIsDisplayed = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_NAVIGATE_TO_RELATED_RULE_BOOK);
    	boolean navigateToVirtualRuleBookMenuOptionIsDisplayed = hierarchyMenu().isElementDisplayed(HierarchyMenuElements.HIERARCHY_MENU_NAVIGATE_TO_VIRTUAL_RULEBOOK);
  	
    	Assertions.assertAll
		(
			() -> Assertions.assertFalse(virtualRuleBookReportMenuOptionIsDisplayed, "'Virtual Rule Book Report' context menu option is displayed when it should not be"),
			() -> Assertions.assertFalse(sourceDocumentsInHierarchyReportMenuOptionIsDisplayed, "'Source Documents In Hierarchy Report' context menu option is displayed when it should not be"),
			() -> Assertions.assertFalse(navigateToRelatedRuleBookMenuOptionIsDisplayed, "'Navigate To Related Rule Book' context menu option is displayed when it should not be"),
			() -> Assertions.assertFalse(navigateToVirtualRuleBookMenuOptionIsDisplayed, "'Navigate To Virtual Rule Book' context menu option is displayed when it should not be")
		);
    }
	
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the 'Insert Glossary Link' context menu option doesn't appear when right
     * clicking paragraph text in the Hierarchy editor <br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void hierarchyEditorInsertGlossaryLinkLegalTest()
	{
		String quickSearchValue = "12.28";

		homePage().goToHomePage();
		loginPage().logIn();

		hierarchyMenu().goToNavigate();
		hierarchySearchPage().quickSearch("=", quickSearchValue);

		siblingMetadataPage().selectedSiblingMetadataEditContent();
		editorPage().switchToEditorTextFrame();
		editorTextPage().rightClickFirstTextParagraphText();
		editorTextPage().openMarkupSubMenu();
		boolean isInsertGlossaryDisplayed = editorTextPage().isElementDisplayed(EditorTextContextMenuElements.INSERT_GLOSSARY_LINK_MARKUP);

		editorPage().closeEditorWithDiscardingChangesIfAppeared();
		editorPage().waitForEditorToClose();

		Assertions.assertFalse(isInsertGlossaryDisplayed, "The 'Insert Glossary Link' context menu option was displayed when it should not be");
	}
}
