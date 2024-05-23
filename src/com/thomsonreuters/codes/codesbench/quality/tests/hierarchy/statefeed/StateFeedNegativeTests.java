package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.statefeed;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.DispositionDerivationContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.ParentageGraphContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.TreeContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.XmlExtractStateFeedPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.openqa.selenium.Point;

import java.util.ArrayList;
import java.util.List;

public class StateFeedNegativeTests extends TestService
{

	private final String CONTENT_SET = ContentSets.IOWA_DEVELOPMENT.getName();
	//This node uuid points to a title level document, if this node no longer exists, it should point to another title level node
	private final String NODE_UUID = "I7A35F76014EE11DA8AC5CD53670E6B4E";
	/**
	 * STORY/BUG - HALCYONST-13060
	 * SUMMARY - Ensure XML Extract - State Feed context menu option doesn't display on tree pane, parentage graph pane,
	 * or disposition/derivation pane
	 * USER - Legal
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void xmlExtractStateFeedOptionDoesNotAppearOnTreePaneParentageGraphPaneOrDispositionDerivationPaneTest()
	{
		String errorMessageBase = "XML Extract - State Feed Option should not be available in the context menu for ";

		homePage().goToHomePage();
		loginPage().logIn();
		homePage().setMyContentSet(CONTENT_SET);

		hierarchyMenu().goToNavigate();
		hierarchySearchPage().searchNodeUuid(NODE_UUID);
		hierarchyTreePage().rightClickSelectedNavigationTreeNode();
		boolean isStateFeedOptionPresentOnTreeContextPane = hierarchyTreeContextMenu().doesElementExist(TreeContextMenuElements.XML_EXTRACT_STATE_FEED);

		dispositionDerivationPage().rightClickSelectedNode();
		boolean isStateFeedOptionPresentOnDispositionDerivationPane = dispositionDerivationContextMenu().doesElementExist(DispositionDerivationContextMenuElements.XML_EXTRACT_STATE_FEED);

		parentageGraphPage().rightClickSelectedParentageGraphNodeImage();
		boolean isStateFeedOptionPresentOnParentageGraphPane = parentageGraphContextMenu().doesElementExist(ParentageGraphContextMenuElements.XML_EXTRACT_STATE_FEED);

		Assertions.assertAll
		(
			() -> Assertions.assertFalse(isStateFeedOptionPresentOnTreeContextPane, errorMessageBase + "Tree Pane"),
			() -> Assertions.assertFalse(isStateFeedOptionPresentOnParentageGraphPane, errorMessageBase + "Parentage Graph Pane"),
			() -> Assertions.assertFalse(isStateFeedOptionPresentOnDispositionDerivationPane, errorMessageBase + "Disposition/Derivation Pane")
		);
	}

	/**
	 * STORY/BUG - HALCYONST-13081, 17268
	 * SUMMARY - This test ensures that the default state of the elements in the form are correct (default year
	 * should be current year, placeholder text for group name is "group name") and makes sure that cancel properly
	 * cancels out of the window without creating a workflow.
	 * USER - Legal
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void cancellingXmlExtractStateFeedShouldNotCreateWorkflowTest()
	{
		String expectedGroupNamePlaceholder = "group name";
		String expectedFirstYear = DateAndTimeUtils.getCurrentYearyyyy();
		String expectedEndYear = "2050";
		List<String> expectedYearList = new ArrayList<>();

		for (int i = Integer.parseInt(expectedFirstYear); i <= Integer.parseInt(expectedEndYear); i++)
		{
			expectedYearList.add(i + "");
		}

		homePage().goToHomePage();
		loginPage().logIn();
		homePage().setMyContentSet(CONTENT_SET);

		hierarchyMenu().goToNavigate();

		hierarchySearchPage().searchNodeUuid(NODE_UUID);
		siblingMetadataPage().rightClickSelectedSiblingMetadata();

		boolean doesXmlExtractStateFeedWindowAppear = siblingMetadataContextMenu().xmlExtractStateFeed();
		Assertions.assertTrue(doesXmlExtractStateFeedWindowAppear, "The XML Extract - State Feed window should have appeared, but didn't.");

		String actualGroupNamePlaceholder = xmlExtractStateFeedPage().getPlaceholderOfGroupName();
		String actualDefaultYear = xmlExtractStateFeedPage().getDefaultYearOption();
		List<String> actualYearList = xmlExtractStateFeedPage().getListOfYearOptions();
		Point submitLocation = xmlExtractStateFeedPage().getElementLocation(XmlExtractStateFeedPageElements.submitButton);
		Point cancelLocation = xmlExtractStateFeedPage().getElementLocation(XmlExtractStateFeedPageElements.cancelButton);

		//Click cancel has a built-in "waitForWindowGoneByTitle" call to ensure that the window is gone and will fail if it doesn't go away
		xmlExtractStateFeedPage().clickCancel();

		Assertions.assertAll
		(
			() -> Assertions.assertEquals(expectedGroupNamePlaceholder, actualGroupNamePlaceholder, "The group name placeholder text did not match what is expected."),
			//() -> Assertions.assertEquals(expectedFirstYear, actualDefaultYear, "The default selected year in the dropdown should be the current year."),
			//() -> Assertions.assertLinesMatch(expectedYearList, actualYearList, "The years should go from the current year to the year " + expectedEndYear),
			() -> Assertions.assertEquals(submitLocation.getY(), cancelLocation.getY(), "The height of the cancel and submit buttons should be the same."),
			() -> Assertions.assertTrue(submitLocation.getX() < cancelLocation.getX(), "Submit button should be to the left of the cancel button.")
		);
	}

	/**
	 * STORY/BUG - HALCYONST-13081, 17268
	 * SUMMARY - This test ensures clicking submit while having a null group name creates an alert and doesn't make an extract.
	 * USER - Legal
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void clickingSubmitWithNullGroupNameShouldCreateAlertTest()
	{
		String expectedAlertMessage = "Group name is not set";

		homePage().goToHomePage();
		loginPage().logIn();
		homePage().setMyContentSet(CONTENT_SET);

		hierarchyMenu().goToNavigate();

		hierarchySearchPage().searchNodeUuid(NODE_UUID);
		siblingMetadataPage().rightClickSelectedSiblingMetadata();
		siblingMetadataContextMenu().xmlExtractStateFeed();
		xmlExtractStateFeedPage().clickSubmit();
		boolean submittingWithoutGroupNameCausesAlert = AutoITUtils.verifyAlertTextAndAccept(true, expectedAlertMessage);

		Assertions.assertTrue(submittingWithoutGroupNameCausesAlert, "Submitting without a group name should cause an alert, but didn't.");
	}
}
