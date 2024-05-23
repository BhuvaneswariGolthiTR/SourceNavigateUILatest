package com.thomsonreuters.codes.codesbench.quality.tests.navigation.legal;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HierarchyContextMenuItemsLegalTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the legal user has appropriate context menu items under Hierarchy<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void hierarchyContextMenuOptionsLegalTest()
    {
    	String nodeValue = "12.6";

    	homePage().goToHomePage();
		loginPage().logIn();
        
		hierarchyMenu().goToNavigate();

		hierarchySearchPage().quickSearch("=", nodeValue);
		siblingMetadataPage().rightClickSelectedSiblingMetadata();
		
		boolean onlineProductAssignmentMenuIsDisplayed = siblingMetadataPage().isElementDisplayed(SiblingMetadataContextMenuElements.ONLINE_PRODUCT_ASSIGNMENTS);
		boolean manageGlossaryTermsContextMenuOptionIsDisplayed = siblingMetadataPage().isElementDisplayed(SiblingMetadataContextMenuElements.MANAGE_GLOSSARY_TERMS);

        Assertions.assertAll
		(
			() -> Assertions.assertTrue(onlineProductAssignmentMenuIsDisplayed, "The 'Online Product Assignments' context menu option is not displayed when it should have been"),
			() -> Assertions.assertFalse(manageGlossaryTermsContextMenuOptionIsDisplayed, "The 'Manage Glossary Terms' context menu option is displayed when it should not have been")
		);
    }
}
