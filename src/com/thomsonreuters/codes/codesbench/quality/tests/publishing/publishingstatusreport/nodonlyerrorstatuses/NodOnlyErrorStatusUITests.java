package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishingstatusreport.nodonlyerrorstatuses;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.MainHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class NodOnlyErrorStatusUITests extends TestService
{
    Connection connection;
    String contentSetIowaText = ContentSets.IOWA_DEVELOPMENT.getName();
    HierarchyDatapodObject datapodObject;

    /**
     * STORY: Halcyonst-15383<br>
     * SUMMARY: This test verifies the base functionality and display of elements on the new UI created in the story above NOD-only Error Statuses <br>
     * USER: LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void nodOnlyErrorStatusUITest()
    {
        //Create BL node datapod and set it to ERROR publishing status
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String blNodeUuid = datapodObject.getBluelines().get(0).getNodeUUID();
        String blNodeContentUuid = datapodObject.getBluelines().get(0).getContentUUID();
        String value = HierarchyDatabaseUtils.getNodeValue(blNodeContentUuid, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        PublishingDatabaseUtils.setNodeToPublishingERRORStatus(blNodeContentUuid, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);

        //navigate to hierarchy, search for mocked up node and verify it has an ERROR publishing status
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchContentUuid(blNodeContentUuid);
        boolean nodeHasErrorPublishingStatus = siblingMetadataPage().isSelectedNodeErrorStatus();
        Assertions.assertTrue(nodeHasErrorPublishingStatus, "Node should have had ERROR publishing status");

        //Go to Nod-Error Statuses page
        publishingMenu().goToPublishingStatusReportsNodOnlyErrorStatus();
        gridPage().waitForGridLoaded();

        //Verify UI elements in NOD-Only Error Statuses page are displayed correctly
        boolean nodErrorStatusesHeaderIsDisplayed = mainHeaderPage().isGivenTextDisplayedOnPageHeader(MainHeaderElements.PUBLISHING_STATUS_REPORTS_NOD_ONLY_ERROR_STATUSES_HEADER);
        boolean contentSetIsDisplayed = toolbarPage().verifyContentSetIsDisplayed(contentSetIowaText);
        boolean requeryAndReloadButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.RELOAD);
        boolean clearFiltersAndSortsButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.CLEAR_FILTERS);
        boolean refreshCurrentGridButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.REFRESH);
        boolean nextButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.NEXT);
        boolean submitButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.SUBMIT);
        boolean nodeHierarchyColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.NODE_HIERARCHY_COLUMN_XPATH);
        boolean rangeColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.RANGE_TYPE_COLUMN_XPATH);
        boolean flagColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.FLAG_COLUMN_XPATH);
        boolean volColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VOL_COLUMN_XPATH);
        boolean statusColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.STATUS_COLUMN_XPATH);
        boolean codeColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.CODE_COLUMN_XPATH);
        boolean keywordColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.KEYWORD_COLUMN_XPATH);
        boolean valueColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VALUE_COLUMN_XPATH);
        boolean nodeUuidColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.NODE_UUID_COLUMN_XPATH);

        //filter for mocked BL node
        gridHeaderPage().openMenuForNodeUuidColumn();
        gridHeaderFiltersPage().setFilterValue(blNodeUuid);

        //HALCYONST-7925: Verify context menu options appear as expected in NOD-Only Error Statuses page
        gridPage().rightClickByNodeTargetValue(value);
        boolean notPublishedStatusIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.NOT_PUBLISHED_STATUS_XPATH);
        boolean removeNotPublishedStatusIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.REMOVE_NOT_PUBLISHED_STATUS_XPATH);
        boolean findDocumentInPubIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_PUB_XPATH);
        boolean findDocumentInWipIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_WIP_XPATH);
        boolean copyIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.COPY_XPATH);
        boolean copyWithHeadersIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.COPY_WITH_HEADERS_XPATH);
        boolean exportIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.EXPORT_XPATH);

        //HALCONST-6629, HALCYONST-7925: Verify 'Find Document in WIP' navigates to the correct node.
        gridContextMenu().findDocumentInWip();
        boolean selectedNodeHasErrorStatusAfterFindDocumentInWip = siblingMetadataPage().getSelectedNodeValue().equals(value);
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishingStatusReportsNodOnlyErrorStatuses();

        //Set node to not published in the NOD-Only Error Statuses page and verify the 'updated status' toast message appears
        gridPage().rightClickByNodeTargetValue(value);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        gridContextMenu().notPublishedStatus();
        toolbarPage().clickSubmit();
        boolean updatedStatusToastMessageIsDisplayed = gridPage().checkUpdatedStatusToastMessage();

        //Navigate back to hierarchy page and verify the publishing status of the node is updated to 'Not Published'
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyPubNavigateSearchPage().searchContentUuid(blNodeContentUuid);
        boolean isNodeStatusIsNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(contentSetIsDisplayed, "Texas (Development) is displayed in the upper right hand corner"),
            () -> Assertions.assertFalse(nextButtonIsDisplayed, "Next button is displayed on this UI and should not be"),
            () -> Assertions.assertTrue(submitButtonIsDisplayed, "Submit button should be displayed on this UI"),
            () -> Assertions.assertTrue(requeryAndReloadButtonIsDisplayed, "Requery and Reload from Database button is not displayed on this UI and should be"),
            () -> Assertions.assertTrue(clearFiltersAndSortsButtonIsDisplayed, "Clear Filters and Sorts button is not displayed on this UI and should be"),
            () -> Assertions.assertTrue(refreshCurrentGridButtonIsDisplayed, "Refresh Current Grid button is not displayed on this UI and should be"),
            () -> Assertions.assertTrue(nodErrorStatusesHeaderIsDisplayed, MainHeaderElements.PUBLISHING_STATUS_REPORTS_ERROR_STATUS_PAGE_HEADER + " is not displayed and should be"),
            () -> Assertions.assertTrue(nodeHierarchyColumnIsDisplayed, "Node Hierarchy column is not displayed and should be"),
            () -> Assertions.assertTrue(rangeColumnIsDisplayed, "Range Indicator column is not displayed and should be"),
            () -> Assertions.assertTrue(flagColumnIsDisplayed, "Flag column is not displayed and should be"),
            () -> Assertions.assertTrue(volColumnIsDisplayed, "Vol column is not displayed and should be"),
            () -> Assertions.assertTrue(statusColumnIsDisplayed, "Status column is not displayed and should be"),
            () -> Assertions.assertTrue(codeColumnIsDisplayed, "Code column is not displayed and should be"),
            () -> Assertions.assertTrue(keywordColumnIsDisplayed, "Keyword column is not displayed and should be"),
            () -> Assertions.assertTrue(valueColumnIsDisplayed, "Value column is not displayed and should be"),
            () -> Assertions.assertTrue(nodeUuidColumnIsDisplayed, "Node UUID column is not displayed and should be"),
            () -> Assertions.assertTrue(notPublishedStatusIsDisplayed, "Not Published Status does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(removeNotPublishedStatusIsDisplayed, "Remove Not Published Status does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(findDocumentInPubIsDisplayed, "Find Document In PUB does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(findDocumentInWipIsDisplayed, "Find Document In WIP does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(copyIsDisplayed, "Copy does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(copyWithHeadersIsDisplayed, "Copy With Headers does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(exportIsDisplayed, "Export does not appear as a context menu option and should "),
            () -> Assertions.assertTrue(selectedNodeHasErrorStatusAfterFindDocumentInWip, "Select node does not have ERROR status and should"),
            () -> Assertions.assertTrue(updatedStatusToastMessageIsDisplayed, "Toast message was expected to appear"),
            () -> Assertions.assertTrue(isNodeStatusIsNotPublished, "Node status should be not published After clicking submit")
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