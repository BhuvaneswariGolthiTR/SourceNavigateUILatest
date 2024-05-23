package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishingstatusreport;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.publishing.toolbox.GridContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.MainHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ErrorStatusesTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    String contentSetIowaText = ContentSets.IOWA_DEVELOPMENT.getName();
    HierarchyDatapodObject datapodObject;

    /**
     * STORY: HALCYONST-7925, HALCYONST-6793, HALCYONST-6629 <br>
     * SUMMARY: After the split up of the 2 Hierarchy Navigate Troubleshooting UIs, this test verifies basic functionality
     * and the display of elements on the Publish Troubleshooting ERROR status UI <br>
     * USER: LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void errorStatusUITest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        HierarchyDatapodConfiguration.getConfig().setSectionCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String contentUuid = datapodObject.getSections().get(0).getContentUUID();
        String value = HierarchyDatabaseUtils.getNodeValue(contentUuid, contentSetIowa, uatConnection);

        PublishingDatabaseUtils.setNodeToPublishingERRORStatus(contentUuid, contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        //mock up node with Error status
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);

        boolean selectedNodeHasErrorStatus = siblingMetadataPage().isSelectedNodeErrorStatus();
        Assertions.assertTrue(selectedNodeHasErrorStatus, "Selected node is not Error status and should be");

        boolean toolboxWindowAppeared = publishingMenu().goToPublishingStatusReportsErrorStatuses();
        Assertions.assertTrue(toolboxWindowAppeared, "Error Status Publishing UI appeared");
        gridPage().waitForGridLoaded();

        boolean contentSetIsDisplayed = toolbarPage().verifyContentSetIsDisplayed(contentSetIowaText);
        boolean nextButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.NEXT);
        boolean refreshCurrentGridButtonIsDisplayed = toolbarPage().isElementDisplayed(ToolbarElements.REFRESH);
        boolean errorStatusHeaderIsDisplayed = mainHeaderPage().isGivenTextDisplayedOnPageHeader(MainHeaderElements.PUBLISHING_STATUS_REPORTS_ERROR_STATUS_PAGE_HEADER);
        boolean nodeHierarchyColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.NODE_HIERARCHY_COLUMN_XPATH);
        boolean rangeColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.RANGE_TYPE_COLUMN_XPATH);
        boolean flagColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.FLAG_COLUMN_XPATH);
        boolean volColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VOL_COLUMN_XPATH);
        boolean statusColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.STATUS_COLUMN_XPATH);
        boolean codeColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.CODE_COLUMN_XPATH);
        boolean keywordColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.KEYWORD_COLUMN_XPATH);
        boolean valueColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VALUE_COLUMN_XPATH);
        boolean startDateColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.START_DATE_COLUMN_XPATH);
        boolean endDateColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.END_DATE_COLUMN_XPATH);
        boolean nodeUuidColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.NODE_UUID_COLUMN_XPATH);

        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishingStatusReportsErrorStatuses();

        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(value);

        //HALCYONST-7925
        gridPage().rightClickByNodeTargetValue(value);
        boolean notPublishedStatusIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.NOT_PUBLISHED_STATUS_XPATH);
        boolean removeNotPublishedStatusIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.REMOVE_NOT_PUBLISHED_STATUS_XPATH);
        boolean findDocumentInPubIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_PUB_XPATH);
        boolean findDocumentInWipIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.FIND_DOCUMENT_IN_WIP_XPATH);
        boolean copyIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.COPY_XPATH);
        boolean copyWithHeadersIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.COPY_WITH_HEADERS_XPATH);
        boolean exportIsDisplayed = gridContextMenu().isElementDisplayed(GridContextMenuElements.EXPORT_XPATH);

        //HALCONST-6629, HALCYONST-7925
        gridContextMenu().findDocumentInWip();
        boolean selectedNodeHasErrorStatusAfterFindDocumentInWip = siblingMetadataPage().isSelectedNodeErrorStatus();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(contentSetIsDisplayed, "Texas (Development) is displayed in the upper right hand corner"),
            () -> Assertions.assertFalse(nextButtonIsDisplayed, "Next button is displayed on this UI and should not be"),
            () -> Assertions.assertTrue(refreshCurrentGridButtonIsDisplayed, "Refresh Current Grid button is not displayed on this UI and should be"),
            () -> Assertions.assertTrue(errorStatusHeaderIsDisplayed, MainHeaderElements.PUBLISHING_STATUS_REPORTS_ERROR_STATUS_PAGE_HEADER + " is not displayed and should be"),
            () -> Assertions.assertTrue(nodeHierarchyColumnIsDisplayed, "Node Hierarchy column is not displayed and should be"),
            () -> Assertions.assertTrue(rangeColumnIsDisplayed, "Range Indicator column is not displayed and should be"),
            () -> Assertions.assertTrue(flagColumnIsDisplayed, "Flag column is not displayed and should be"),
            () -> Assertions.assertTrue(volColumnIsDisplayed, "Vol column is not displayed and should be"),
            () -> Assertions.assertTrue(statusColumnIsDisplayed, "Status column is not displayed and should be"),
            () -> Assertions.assertTrue(codeColumnIsDisplayed, "Code column is not displayed and should be"),
            () -> Assertions.assertTrue(keywordColumnIsDisplayed, "Keyword column is not displayed and should be"),
            () -> Assertions.assertTrue(valueColumnIsDisplayed, "Value column is not displayed and should be"),
            () -> Assertions.assertTrue(startDateColumnIsDisplayed, "Start Date column is not displayed and should be"),
            () -> Assertions.assertTrue(endDateColumnIsDisplayed, "End Date column is not displayed and should be"),
            () -> Assertions.assertTrue(nodeUuidColumnIsDisplayed, "Node UUID column is not displayed and should be"),
            () -> Assertions.assertTrue(notPublishedStatusIsDisplayed, "Not Published Status does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(removeNotPublishedStatusIsDisplayed, "Remove Not Published Status does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(findDocumentInPubIsDisplayed, "Find Document In PUB does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(findDocumentInWipIsDisplayed, "Find Document In WIP does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(copyIsDisplayed, "Copy does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(copyWithHeadersIsDisplayed, "Copy With Headers does not appear as a context menu option and should"),
            () -> Assertions.assertTrue(exportIsDisplayed, "Export does not appear as a context menu option and should "),
            () -> Assertions.assertTrue(selectedNodeHasErrorStatusAfterFindDocumentInWip, "Select node does not have ERROR status and should")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
        if(uatConnection != null)
        {
            BaseDatabaseUtils.disconnect(uatConnection);
        }
    }
}