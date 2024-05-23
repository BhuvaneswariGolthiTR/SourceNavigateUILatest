package com.thomsonreuters.codes.codesbench.quality.tests.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.searchandreplace.SearchAndReplacePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;

import java.sql.Connection;

public class SearchAndReplaceTests extends TestService
{
	/**
	 * STORY/BUG - CODESITEMS-22 <br>
	 * SUMMARY - This test verifies that the newly created tables appears in the content sets that were selected to copy to.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void searchAndReplaceCopyDataBetweenJurisdictionTest()
	{
		String tableName = "atest"  + DateAndTimeUtils.getCurrentDateMMddyyyy();
		String tableDescription = "atest" + DateAndTimeUtils.getCurrentDateMMddyyyy() + "testing";
		String copyTableName =  "atest"  + DateAndTimeUtils.getCurrentDateMMddyyyy() + "copy";
		String copyTableDescription =  "atest"  + DateAndTimeUtils.getCurrentDateMMddyyyy() + "testing copy";
		String contentSetTexas = "Texas (Development)";
		String contentSetUsca = "USCA(Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		boolean didSARWindowAppear = toolsMenu().goToSearchAndReplace();
		Assertions.assertTrue(didSARWindowAppear, "SAR Window did not appear when it should have");

		searchAndReplacePage().clickOnTables();
		//Do these need to be a table maintenance page?
		searchAndReplacePage().enterTableName(tableName);
		searchAndReplacePage().enterTableDescription(tableDescription);
		searchAndReplacePage().clickCreateButton();

		//check to see if all correct buttons appear
		boolean doesSaveButtonAppear = searchAndReplacePage().isElementDisplayed(SearchAndReplacePageElements.SAVE_TABLE_BUTTON);
		boolean doesNewEntryButtonAppear = searchAndReplacePage().isElementDisplayed(SearchAndReplacePageElements.NEW_ENTRY_TABLE_BUTTON);
		boolean doesCopyButtonAppear = searchAndReplacePage().isElementDisplayed(SearchAndReplacePageElements.COPY_TABLE_BUTTON);
		boolean doesDeleteButtonAppear = searchAndReplacePage().isElementDisplayed(SearchAndReplacePageElements.DELETE_TABLE_BUTTON);
		boolean doesCopyToAnotherContentSetButtonAppear = searchAndReplacePage().isElementDisplayed(SearchAndReplacePageElements.COPY_TO_ANOTHER_CONTENT_SET_TABLE_BUTTON);
		boolean doesFirstEntryButtonAppear = searchAndReplacePage().isElementDisplayed(SearchAndReplacePageElements.FIRST_ENTRY_TABLE_BUTTON);
		boolean doesLastEntryButtonAppear = searchAndReplacePage().isElementDisplayed(SearchAndReplacePageElements.LAST_ENTRY_TABLE_BUTTON);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(doesSaveButtonAppear, "Save button does not appear"),
			() -> Assertions.assertTrue(doesNewEntryButtonAppear, "New Entry button does not appear"),
			() -> Assertions.assertTrue(doesCopyButtonAppear, "Copy button does not appear"),
			() -> Assertions.assertTrue(doesDeleteButtonAppear, "Delete button does not appear"),
			() -> Assertions.assertTrue(doesCopyToAnotherContentSetButtonAppear, "Copy to Another Entry button does not appear"),
			() -> Assertions.assertTrue(doesFirstEntryButtonAppear, "First Entry button does not appear"),
			() -> Assertions.assertTrue(doesLastEntryButtonAppear, "Last Entry button does not appear")
		);

		//copy to another content set
		searchAndReplacePage().clickCopyToAnotherContentSetButton();

		//Verify new content set window appear
		copyTableToAnotherContentSetPage().enterTableName(copyTableName);
		copyTableToAnotherContentSetPage().enterTableDescription(copyTableDescription);
		copyTableToAnotherContentSetPage().multiselectContentSets(contentSetUsca, contentSetTexas);
		copyTableToAnotherContentSetPage().clickOK();

		searchAndReplacePage().closeCurrentWindowIgnoreDialogue();
		homePage().switchToPage();

		//change content set to check for copied table
		homePage().setMyContentSet(contentSetUsca);
		didSARWindowAppear = toolsMenu().goToSearchAndReplace();
		Assertions.assertTrue(didSARWindowAppear, "SAR Window did not appear when it should have");

		//expand the table tab
		searchAndReplacePage().clickTablesExpandButton();
		boolean doesCopiedTableAppearInList = searchAndReplacePage().doesTableAppearInListOfTables(copyTableName);
		Assertions.assertTrue(doesCopiedTableAppearInList, "The copied table does not appear when it should");

		searchAndReplacePage().clickOnTable(copyTableName);
		searchAndReplacePage().clickDeleteButton();
		boolean alertAccepted = AutoITUtils.verifyAlertTextAndAccept(true, "Warning - The selected table may be associated with an existing job(s). If you delete this table all associated jobs will also be deleted. Do you want to continue?");
		Assertions.assertTrue(alertAccepted,"the expected alert didn't appear or wasn't accepted");

		searchAndReplacePage().goToPage();
		searchAndReplacePage().clickTablesExpandButton();
		doesCopiedTableAppearInList = searchAndReplacePage().doesTableAppearInListOfTables(copyTableName);
		Assertions.assertFalse(doesCopiedTableAppearInList, "The copied table appears when it should not");

		searchAndReplacePage().closeCurrentWindowIgnoreDialogue();
		homePage().switchToPage();

		//Repeat steps with new content set
		homePage().setMyContentSet(contentSetTexas);
		didSARWindowAppear = toolsMenu().goToSearchAndReplace();
		Assertions.assertTrue(didSARWindowAppear, "SAR Window did not appear when it should have");

		//expand the table tab
		searchAndReplacePage().clickTablesExpandButton();
		doesCopiedTableAppearInList = searchAndReplacePage().doesTableAppearInListOfTables(copyTableName);
		Assertions.assertTrue(doesCopiedTableAppearInList, "The copied table does not appear when it should");

		searchAndReplacePage().clickOnTable(copyTableName);
		searchAndReplacePage().clickDeleteButton();
		boolean alertAccepted2 = AutoITUtils.verifyAlertTextAndAccept(true, "Warning - The selected table may be associated with an existing job(s). If you delete this table all associated jobs will also be deleted. Do you want to continue?");
		Assertions.assertTrue(alertAccepted2,"the expected alert didn't appear or wasn't accepted");

		searchAndReplacePage().goToPage();
		searchAndReplacePage().clickTablesExpandButton();
		doesCopiedTableAppearInList = searchAndReplacePage().doesTableAppearInListOfTables(copyTableName);
		Assertions.assertFalse(doesCopiedTableAppearInList, "The copied table appears when it should not");
	}

	/**
	 * STORY: <br>
	 * SUMMARY: This test initially mocks up a table. After the table is mocked up, overall Search and Replace functionality is tested.
	 * Finally, the test travels to a publishing UI to verify that the corresponding Full Vols - SAR law tracking status is displayed in
	 * the Law Tracking column for the node we interact with throughout the test<br>
	 * USER: LEGAL <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void searchAndReplacePublishingTest()
	{
		String testTableName = "Test" + DateAndTimeUtils.getCurrentDateMMMddyyyy();
		String testEntryName =  "Jack to Scooby";
		String testText = "Captain Jack Sparrow";
		String nodeUuid = "IC97455C440C4482B9EDFDEBB494ECF79";
		String contentUuid = "I2293C590BE6E11D9BDF79F56AB79CECB";
		String saruser = "saruser";
		String contentSetText = "Texas (Development)";
		String contentSet = ContentSets.TEXAS_DEVELOPMENT.getCode();

		//set node to any status that is NOT not published
		Connection uatHierarchyConnection = BaseDatabaseUtils.connectToDatabaseUAT();
		PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid, contentSet, uatHierarchyConnection);

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(contentSetText);

		hierarchyMenu().goToNavigate();
		hierarchySearchPage().searchNodeUuid(nodeUuid);
		if(siblingMetadataPage().isSelectedNodeErrorStatus())
		{
			siblingMetadataPage().setNotPublishedStatusForSelectedNode();
			PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid, contentSet, uatHierarchyConnection);
		}

		toolsMenu().goToSearchAndReplace();

		searchAndReplacePage().clickOnTables();
		searchAndReplacePage().enterTableName(testTableName);
		searchAndReplacePage().clickCreateButton();

		boolean createdTableAppearsInListOfTables = searchAndReplacePage().doesTableAppearInListOfTables(testTableName);
		Assertions.assertTrue(createdTableAppearsInListOfTables, "Table was not created correctly and does not appear in the list of tables on the SAR page");

		searchAndReplacePage().clickNewEntryButton();
		searchAndReplacePage().setEntryName(testEntryName);
		searchAndReplacePage().setSearchForPhrase(testText);
		searchAndReplacePage().setReplaceWithPhrase(testEntryName);
		searchAndReplacePage().clickSaveButton();
		searchAndReplacePage().closeCurrentWindowIgnoreDialogue();

		hierarchyNavigatePage().switchToHierarchyEditPage();
		hierarchySearchPage().searchNodeUuid(nodeUuid);

		//will use these attributes to filter for the node in the publishing UI
		String nodeValue = siblingMetadataPage().getSelectedNodeValue();
		String nodeVols = siblingMetadataPage().getSelectedNodeVols();

		boolean isSelectedNodeWestlawLoaded = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();
		Assertions.assertTrue(isSelectedNodeWestlawLoaded, "The node should be Westlaw Loaded after running query to database");

		siblingMetadataPage().selectedSiblingMetadataEditContent();
		editorPage().switchDirectlyToTextFrame();
		editorTextPage().insertPhraseIntoFirstTextParagraphWithSpace(testText);
		editorPage().closeAndCheckInChanges();
//		editorPage().closeSpellcheckWindow();
		editorPage().waitForEditorToClose();
		hierarchyNavigatePage().switchToHierarchyEditPage();

		boolean isNodeNotPublishAfterEdit = siblingMetadataPage().isSelectedNodeStatusNotPublished();
		Assertions.assertTrue(isNodeNotPublishAfterEdit, "The node should be not published after we edit a content and check in changes");

		siblingMetadataPage().rightClickSelectedSiblingMetadata();
		siblingMetadataContextMenu().setReadyPublishingStatus();

		boolean isNodeReadyPublishStatus = siblingMetadataPage().isSelectedNodeReadyStatus();
		Assertions.assertTrue(isNodeReadyPublishStatus, "The node should be ready publishing status after setting ready on context menu");

		siblingMetadataPage().rightClickSelectedSiblingMetadata();
		boolean searchAndReplaceWindowOpened = siblingMetadataContextMenu().runSearchAndReplace();
		Assertions.assertTrue(searchAndReplaceWindowOpened, "Search and Replace Maintenance Window did not open");

		searchAndReplacePage().setSearchAndReplaceTableDropdown(testTableName);
		searchAndReplacePage().selectSearchAndReplaceRadioButton();
		searchAndReplacePage().checkTextFilesCheckbox();
		searchAndReplacePage().clickSaveButton();
		searchAndReplacePage().clickRunButton();

		boolean viewReportLinkAppeared = searchAndReplacePage().clickRefreshButtonUntilViewReportAppears();
		Assertions.assertTrue(viewReportLinkAppeared, "View Report link never appeared in the Difference Report column");

		boolean searchAndReplaceReportWindowAppeared = searchAndReplacePage().clickViewReport();
		Assertions.assertTrue(searchAndReplaceReportWindowAppeared, "Search and Replace Report window did not appear");

		boolean nodeUuidMatches = searchAndReplaceReportPage().verifyNodeUuidMatches(nodeUuid);
		boolean captainJackSparrowAppearsHighlightedBeforeChange = searchAndReplaceReportPage().verifyHighlightedPhraseAppearsBeforeChange(testText);
		boolean scoobyDooAppearsHighlightedAfterChange = searchAndReplaceReportPage().verifyHighlightedPhraseAppearsAfterChange("Scooby");
		searchAndReplaceWindowOpened = searchAndReplaceReportPage().clickCommitButton();
		Assertions.assertTrue(searchAndReplaceWindowOpened, "Search and Replace window did not appear");

		boolean committingChangesAppearsInStatusColumn = searchAndReplacePage().verifyCommittingChangesAppearsInStatusColumn();
		boolean committedAppearsInStatusColumn = searchAndReplacePage().clickRefreshButtonUntilCommittedAppears();
		Assertions.assertTrue(committedAppearsInStatusColumn, "Committed or Committed with Exceptions never appears in the status column");

		searchAndReplacePage().closeCurrentWindowIgnoreDialogue();
		hierarchyNavigatePage().switchToHierarchyEditPage();

		siblingMetadataPage().selectNodesByValue(nodeValue);

		siblingMetadataPage().rightClickSelectedSiblingMetadata();
		siblingMetadataContextMenu().refreshSelection();

		boolean selectedNodeIsNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();

		siblingMetadataPage().selectedSiblingMetadataEditContent();

		editorPage().switchDirectlyToTextFrame();
		boolean scoobyAppearsInTextParagraph = editorTextPage().doesFirstParagraphWithParaTextContainGivenText(testEntryName);
		editorTextPage().breakOutOfFrame();

		editorToolbarPage().clickToolbarCloseNoChanges();

		siblingMetadataPage().rightClickSelectedSiblingMetadata();
		siblingMetadataContextMenu().viewModifyPreviousWIPVersion();

		previousWipVersionsPage().clickMostRecentWipVersion();
		String mostRecentWipCreatedByUser = previousWipVersionsPage().getSelectedWipVersionsCreatedByText();
		String mostRecentWipLawTracking = previousWipVersionsPage().getSelectedWipVersionsLawTrackingText();
		previousWipVersionsPage().clickClose();

		publishingMenu().goToPublishReadyTextNodesOnly();
		gridPage().waitForGridLoaded();

		gridHeaderPage().openMenuForModifiedByColumn();
		gridHeaderFiltersPage().setFilterValue(saruser);
		gridHeaderPage().openMenuForVolColumn();
		gridHeaderFiltersPage().setFilterValue(nodeVols);
		gridHeaderPage().openMenuForNodeHierarchyColumn();
		gridHeaderFiltersPage().setFilterValue("Sec. " + nodeValue);

		gridPage().selectFirstSectionNode();
		String selectedNodeLawTrackingStatus = gridPage().getSelectedNodesLawTrackingStatus();

		toolbarPage().closeCurrentWindowIgnoreDialogue();
		hierarchyNavigatePage().switchToHierarchyEditPage();

		toolsMenu().goToSearchAndReplace();

		searchAndReplacePage().clickTablesExpandButton();
		searchAndReplacePage().clickOnTable(testTableName);
		searchAndReplacePage().clickDeleteButton();
		searchAndReplacePage().acceptDeleteTableAlert();
		searchAndReplacePage().clickTablesExpandButton();

		boolean doesTableAppearInListOfTables  = searchAndReplacePage().doesTableAppearInListOfTables(testTableName);

		//two wip versions are created while running this test. to reference the original node again, we delete the two new wip versions and update latest_flag to point to the original node
		HierarchyDatabaseUtils.deleteLatestWipVersion(contentUuid, uatHierarchyConnection);
		HierarchyDatabaseUtils.updateWipVersionToLatest(contentUuid, uatHierarchyConnection);
		HierarchyDatabaseUtils.deleteLatestWipVersion(contentUuid, uatHierarchyConnection);
		HierarchyDatabaseUtils.updateWipVersionToLatest(contentUuid, uatHierarchyConnection);
		BaseDatabaseUtils.disconnect(uatHierarchyConnection);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(nodeUuidMatches, "The node uuid we mocked up is not the node uuid we see on the report"),
			() -> Assertions.assertTrue(captainJackSparrowAppearsHighlightedBeforeChange, "Captain Jack Sparrow does not appear highlighted in the before change grid"),
			() -> Assertions.assertTrue(scoobyDooAppearsHighlightedAfterChange, "Scooby does not appear highlighted in the after change grid"),
			() -> Assertions.assertTrue(committingChangesAppearsInStatusColumn, "Committing changes does not appear in the status column"),
			() -> Assertions.assertTrue(selectedNodeIsNotPublished, "Node is not updated to not published status"),
			() -> Assertions.assertTrue(scoobyAppearsInTextParagraph, "Scooby did not replace Captain Jack Sparrow in text paragraph"),
			() -> Assertions.assertEquals(saruser, mostRecentWipCreatedByUser, "Wip version created by the saruser with a law tracking status of Full-Vols SAR does not appear"),
			() -> Assertions.assertEquals("Full Vols-SAR", mostRecentWipLawTracking, "Wip version created by the saruser with a law tracking status of Full-Vols SAR does not appear"),
			() -> Assertions.assertTrue(selectedNodeLawTrackingStatus.contains("Full Vols-SAR"), "Full Vols-SAR does not appear in the law tracking column of the mocked up node in the ready selection UI"),
			() -> Assertions.assertFalse(doesTableAppearInListOfTables, "Table still appears in the list of tables")
		);
	}
}
