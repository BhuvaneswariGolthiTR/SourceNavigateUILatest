package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishingstatusreport.nodonlywiptopubuploadissues;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.MainHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

public class NodOnlyWipToPubUploadIssuesUITests extends TestService
{
    Connection connection;
    HierarchyDatapodObject datapodObject;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    /**
     * STORY/BUG - HALCYONST-15383 <br>
     * SUMMARY - This test verifies that the NOD-Only Wip to Pub Upload Issues ui contains the proper content as according
     * to the Story mentioned above.<br>
     * USER - LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void nodOnlyWipToPubUploadIssuesUITest()
    {
        //Create BL node datapod and set it to 'Approved' status, add the user as the approver
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        HierarchyDatapodConfiguration.getConfig().setInsertInPubNav(true);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String blNodeUuid = datapodObject.getBluelines().get(0).getNodeUUID();
        String blNodeContentUuid = datapodObject.getBluelines().get(0).getContentUUID();
        PublishingDatabaseUtils.setPublishingNodeToApprove(blNodeContentUuid, contentSetIowa, connection);
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, connection);
        BaseDatabaseUtils.disconnect(connection);

        //Verify the node is set to 'Approved' status in hierarchy navigate
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchContentUuid(blNodeContentUuid);
        boolean nodeHasApprovedPublishingStatus = siblingMetadataPage().isSelectedNodeApprovedStatus();
        Assertions.assertTrue(nodeHasApprovedPublishingStatus, "Node should have 'Approved' publishing status");
        String value = siblingMetadataPage().getSelectedNodeValue();

        //Navigate to Publishing Status Reports > Nod-Only Wip to Pub Upload issues page and filter for mocked BL node
        publishingMenu().goToPublishingStatusReportsNodOnlyWipToPubUploadIssues();
        gridPage().waitForGridLoaded();
        gridHeaderPage().openMenuForNodeUuidColumn();
        gridHeaderFiltersPage().setFilterValue(blNodeUuid);

        //Verify the page and its elements appear as we expect them too.
        boolean nodWipToPubHeaderIsDisplayed = mainHeaderPage().isGivenTextDisplayedOnPageHeader(MainHeaderElements.PUBLISHING_STATUS_REPORTS_NOD_WIP_TO_PUB_UPLOAD_ISSUES_PAGE_HEADER);
        boolean contentSetIsDisplayed = toolbarPage().verifyContentSetIsDisplayed(ContentSets.IOWA_DEVELOPMENT.getName());
        boolean requeryAndReloadButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.RELOAD);
        boolean clearFiltersAndSortsButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.CLEAR_FILTERS);
        boolean refreshCurrentGridButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.REFRESH);
        boolean nextButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.NEXT);
        boolean nodeHierarchyColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.NODE_HIERARCHY_COLUMN_XPATH);
        boolean rangeColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.RANGE_TYPE_COLUMN_XPATH);
        boolean flagColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.FLAG_COLUMN_XPATH);
        boolean volColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VOL_COLUMN_XPATH);
        boolean statusColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.STATUS_COLUMN_XPATH);
        boolean codeColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.CODE_COLUMN_XPATH);
        boolean keywordColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.KEYWORD_COLUMN_XPATH);
        boolean valueColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VALUE_COLUMN_XPATH);
        boolean nodeUuidColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.NODE_UUID_COLUMN_XPATH);

        //HALCYONST-7925: Verify context menu options appear as expected in NOD-Only WIP To PUB page
        gridPage().rightClickByNodeTargetValue(value);
        boolean selectForUpdatedStatusIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.SELECT_FOR_UPDATED_STATUS_XPATH);
        boolean removeSelectionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.REMOVE_SELECTION_XPATH);
        boolean massSelectionIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.MASS_SELECTION_XPATH);
        boolean findDocumentInPubIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_PUB_XPATH);
        boolean findDocumentInWipIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_WIP_XPATH);
        boolean copyIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.COPY_XPATH);
        boolean copyWithHeadersIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.COPY_WITH_HEADERS_XPATH);
        boolean exportIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.EXPORT_XPATH);

        //Verify 'Find Document in WIP' option navigates to the correct node in hierarchy navigate
        gridContextMenu().findDocumentInWip();
        hierarchyNavigatePage().waitForHierarchyEditPageToLoad(value);
        boolean findDocumentInWipBroughtToNodeInHierarchy = siblingMetadataPage().getSelectedNodeUuid().equals(blNodeUuid);
        Assertions.assertTrue(findDocumentInWipBroughtToNodeInHierarchy, "Should bring to mocked BL node in hierarchy navigate after Find Document in WIP");
        mainHeaderPage().switchToPublishingStatusReportsNodOnlyWipToPubUploadIssues();

        //verify 'Find Document in PUB' option navigates to the correct node in pub navigate
        gridPage().rightClickByNodeTargetValue(value);
        gridContextMenu().findDocumentInPub();
        hierarchyPubNavigatePage().waitForHierarchyPubPageToLoad(value);
        boolean findDocumentInPubBroughtToNodeInPubNavigate = siblingMetadataPage().getSelectedNodeUuid().equals(blNodeUuid);
        Assertions.assertTrue(findDocumentInPubBroughtToNodeInPubNavigate, "Should bring to mocked BL node in pub navigate after Find Document in Pub");
        hierarchyPubNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishingStatusReportsNodOnlyWipToPubUploadIssues();

        //Select bl node for not published status, verify it is highlighted and submit update. Verify toast
        gridPage().rightClickByNodeTargetValue(value);
        gridContextMenu().selectForUpdatedStatus();
        boolean isNodeHighlightedGreen = gridPage().isBackgroundGreenForSelectedRow();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(contentSetIsDisplayed, "Texas (Development) is displayed in the upper right hand corner"),
            () -> Assertions.assertTrue(nextButtonIsDisplayed, "Next button is not displayed and should be"),
            () -> Assertions.assertTrue(requeryAndReloadButtonIsDisplayed, "Requery and Reload from Database button is not displayed on this UI and should be"),
            () -> Assertions.assertTrue(clearFiltersAndSortsButtonIsDisplayed, "Clear Filters and Sorts button is not displayed on this UI and should be"),
            () -> Assertions.assertTrue(refreshCurrentGridButtonIsDisplayed, "Refresh Current Grid button is not displayed on this UI and should be"),
            () -> Assertions.assertTrue(nodWipToPubHeaderIsDisplayed, MainHeaderElements.PUBLISHING_STATUS_REPORTS_NOD_WIP_TO_PUB_UPLOAD_ISSUES_PAGE_HEADER + " is not displayed and should be"),
            () -> Assertions.assertTrue(nodeHierarchyColumnIsDisplayed, "Node Hierarchy column is not displayed and should be"),
            () -> Assertions.assertTrue(rangeColumnIsDisplayed, "Range Indicator column is not displayed and should be"),
            () -> Assertions.assertTrue(flagColumnIsDisplayed, "Flag column is not displayed and should be"),
            () -> Assertions.assertTrue(volColumnIsDisplayed, "Vol column is not displayed and should be"),
            () -> Assertions.assertTrue(statusColumnIsDisplayed, "Status column is not displayed and should be"),
            () -> Assertions.assertTrue(codeColumnIsDisplayed, "Code column is not displayed and should be"),
            () -> Assertions.assertTrue(keywordColumnIsDisplayed, "Keyword column is not displayed and should be"),
            () -> Assertions.assertTrue(valueColumnIsDisplayed, "Value column is not displayed and should be"),
            () -> Assertions.assertTrue(nodeUuidColumnIsDisplayed, "Node UUID column is not displayed and should be"),

            () -> Assertions.assertTrue(selectForUpdatedStatusIsDisplayed, "Select for Updated Status does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(removeSelectionIsDisplayed, "Remove Selection does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(massSelectionIsDisplayed, "Mass Selection does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(findDocumentInPubIsDisplayed, "Find Document In PUB does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(findDocumentInWipIsDisplayed, "Find Document In WIP does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(copyIsDisplayed, "Copy does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(copyWithHeadersIsDisplayed, "Copy With Headers does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(exportIsDisplayed, "Export does not appear as a context menu option and should "),

            () -> Assertions.assertTrue(isNodeHighlightedGreen, "Selected node should be highlighted green after selecting for not approved status")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
        if (connection != null)
        {
            BaseDatabaseUtils.disconnect(connection);
        }
    }
}
