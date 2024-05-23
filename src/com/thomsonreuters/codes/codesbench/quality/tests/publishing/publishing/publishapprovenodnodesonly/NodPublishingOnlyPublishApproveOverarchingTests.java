package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishing.publishapprovenodnodesonly;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.GridHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.MainHeaderElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class NodPublishingOnlyPublishApproveOverarchingTests extends TestService
{
    Connection uatConnection;

    String contentSetTexas = ContentSets.TEXAS_DEVELOPMENT.getCode();
    String contentSetTexasText = ContentSets.TEXAS_DEVELOPMENT.getName();

    //Note: Test should remain Texas for now. Not able to find ARL node type in Iowa.
    /**
     *  STORY: HALCYONST-7144, HALCYONST-7043, HALCYONST-7816 <br>
     *  SUMMARY: Verifies overall content, functionality and display of the NOD Publishing Only UI  <br>
     *  USER: LEGAL <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void nodPublishingOnlyUITest()
    {
        String blContentUuid = "IF9393570186D11EB9024D02F3FF85330"; //Filtering for this node in the UI to verify correct value in Publishing Status column, also verifying keyword appears in the filter box when filtering on the Keyword column
        String blAnalysisContentUuid = "IF92D9CB0186D11EB9024D02F3FF85330"; //Verifying keyword appears in the filter box when filtering on the Keyword column
        String xndContentUuid = "IF928E1C1186D11EB9024D02F3FF85330"; //Verifying keyword appears in the filter box when filtering on the Keyword column
        String arlContentUuid = "IB0E4A5006D8211E7A222BE59957724A6"; //Filtering for this node in the UI to verify correct value in Publishing Status column, also verifying keyword appears in the filter box when filtering on the Keyword column
        String ndrsContentUuid = "IB11F64106D8211E7A222BE59957724A6"; //Verifying keyword appears in the filter box when filtering on the Keyword column
        List<String> expectedNodKeywordFilterValues = Arrays.asList("(Select All)", "ARL", "BL", "BL Analysis", "NDRS", "XND");

        //setting all possible NOD node types to not published or ready status so that we can verify each appears when we try to filter by keyword
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        HierarchyDatabaseUtils.setNodeKeywordToARL(arlContentUuid, contentSetTexas, uatConnection);
        HierarchyDatabaseUtils.setNodeKeywordToNDRS(ndrsContentUuid, contentSetTexas, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(blContentUuid, contentSetTexas, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToReady(arlContentUuid, contentSetTexas, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(blAnalysisContentUuid, contentSetTexas, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(xndContentUuid, contentSetTexas, uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToNotPublish(ndrsContentUuid, contentSetTexas, uatConnection);
        String blNodeValue = HierarchyDatabaseUtils.getNodeValue(blContentUuid, contentSetTexas, uatConnection);
        String arlNodeValue = HierarchyDatabaseUtils.getNodeValue(arlContentUuid, contentSetTexas, uatConnection);
        String codeName = HierarchyDatabaseUtils.getNodeCodeName(blContentUuid, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().setMyContentSet(contentSetTexasText);

//        hierarchyMenu().goToNavigate();
//        //This is here cause when iowa data is rest the status sets set to ERROR which interfers with our db changes
//        hierarchySearchPage().searchContentUuid(blContentUuid);
//        siblingMetadataPage().setNotPublishedStatusForSelectedNode();
//
//        hierarchySearchPage().searchContentUuid(blAnalysisContentUuid);
//        siblingMetadataPage().setNotPublishedStatusForSelectedNode();
//
//        hierarchySearchPage().searchContentUuid(xndContentUuid);
//        siblingMetadataPage().setNotPublishedStatusForSelectedNode();
//
//        hierarchySearchPage().searchContentUuid(xndContentUuid);
//        siblingMetadataPage().setNotPublishedStatusForSelectedNode();
//
//        hierarchySearchPage().searchContentUuid(arlContentUuid);
//        siblingMetadataPage().setNotPublishedStatusForSelectedNode();
//
//        hierarchySearchPage().searchContentUuid(ndrsContentUuid);
//        siblingMetadataPage().setNotPublishedStatusForSelectedNode();

        boolean toolboxWindowOpened = publishingMenu().goToPublishApproveNodNodesOnly();
        Assertions.assertTrue(toolboxWindowOpened, "Toolbox window opened");
        gridPage().waitForGridLoaded();

        boolean contentSetIsDisplayed = toolbarPage().verifyContentSetIsDisplayed(contentSetTexasText);
        boolean nodPublishingOnlyIsDisplayed = mainHeaderPage().isGivenTextDisplayedOnPageHeader(MainHeaderElements.PUBLISH_APPROVE_NOD_NODES_ONLY_PAGE_HEADER);
        boolean nodeHierarchyColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.NODE_HIERARCHY_COLUMN_XPATH);
        boolean rangeColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.RANGE_TYPE_COLUMN_XPATH);
        boolean flagColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.FLAG_COLUMN_XPATH);
        boolean volColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VOL_COLUMN_XPATH);
        boolean statusColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.STATUS_COLUMN_XPATH);
        boolean codeColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.CODE_COLUMN_XPATH);
        boolean keywordColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.KEYWORD_COLUMN_XPATH);
        boolean valueColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.VALUE_COLUMN_XPATH);
        boolean modifiedByColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.MODIFIED_BY_COLUMN_XPATH);
        boolean modifiedyColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.MODIFIED_DATE_COLUMN_XPATH);
        boolean publishingStatusColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.PUBLISHING_STATUS_COLUMN_XPATH);
        boolean readyUserColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.READY_USER_COLUMN_XPATH);
        boolean readyColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.READY_DATE_COLUMN_XPATH);
        boolean lawTrackingColumnIsDisplayed = gridHeaderPage().isElementDisplayed(GridHeaderElements.LAW_TRACKING_COLUMN_XPATH);

        gridHeaderPage().openMenuForKeywordColumn();
        gridHeaderFiltersPage().clickFilterIcon();

        List<String> nodKeywordFilterValues = gridHeaderFiltersPage().getFilterValues();
        boolean nodKeywordFilterValuesMatchExpected = expectedNodKeywordFilterValues.equals(nodKeywordFilterValues);

        gridHeaderPage().openMenuForCodeColumn();
        gridHeaderFiltersPage().setFilterValue(codeName);
        gridHeaderPage().openMenuForKeywordColumn();
        gridHeaderFiltersPage().setFilterValue(expectedNodKeywordFilterValues.get(2));
        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(blNodeValue);

        gridPage().selectByNodeTargetValue(blNodeValue);

        String blNodePublishingStatusColumn = gridPage().getSelectedNodesPublishingStatusValue();
        Assertions.assertEquals("0", blNodePublishingStatusColumn, "Blue line we mocked up to have a publishing status of not published displays a 0 in the publishing status");

        gridPage().rightClickByNodeTargetValue(blNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        siblingMetadataPage().selectNodesByValue(blNodeValue);
        boolean broughtToCorrectNodeAfterFindDocumentInHierarchy = siblingMetadataPage().getSelectedNodeValue().equals(blNodeValue);
        Assertions.assertTrue(broughtToCorrectNodeAfterFindDocumentInHierarchy, "Successfully brought to " + blNodeValue);
        boolean blNodeIsNotPublishedStatusAfterFindDocumentInHierarchy = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveNodNodesOnlyWindow();

        toolbarPage().clickClearFiltersAndSorts();

        gridHeaderPage().openMenuForKeywordColumn();
        gridHeaderFiltersPage().setFilterValue(expectedNodKeywordFilterValues.get(1));
        gridHeaderPage().openMenuForValueColumn();
        gridHeaderFiltersPage().setFilterValue(arlNodeValue);

        gridPage().selectByNodeTargetValue(arlNodeValue);

        String arlNodePublishingStatusColumn = gridPage().getSelectedNodesPublishingStatusValue();
        Assertions.assertEquals("1", arlNodePublishingStatusColumn, "ARL node we mocked up to have a publishing status of Ready displays a 1 in the publishing status column");

        gridPage().rightClickByNodeTargetValue(arlNodeValue);
        gridContextMenu().findDocumentInHierarchy();

        boolean broughtToCorrectNodeAfterFindDocumentInHierarchy2 = siblingMetadataPage().getSelectedNodeValue().equals(arlNodeValue);
        Assertions.assertTrue(broughtToCorrectNodeAfterFindDocumentInHierarchy2, "Successfully brought to " + arlNodeValue);
        boolean arlNodeIsReadyStatusAfterFindDocumentInHierarchy = siblingMetadataPage().isSelectedNodeReadyStatus();

        hierarchyNavigatePage().closeCurrentWindowIgnoreDialogue();
        mainHeaderPage().switchToPublishApproveNodNodesOnlyWindow();

        gridPage().rightClickByNodeTargetValue(arlNodeValue);
        gridContextMenu().approvedStatus();
        toolbarPage().clickNext();

        boolean publishNowRadioButtonAppears = toolbarPage().isElementDisplayed(ToolbarElements.PUBLISH_NOW_RADIO_BUTTON_XPATH);
        boolean publishTonightRadioButtonAppears = toolbarPage().isElementDisplayed(ToolbarElements.SCHEDULED_PUBLISH_RADIO_BUTTON_XPATH);

        //Return nodes to their original keywords
        HierarchyDatabaseUtils.setNodeKeywordToBlueLine(arlContentUuid, contentSetTexas, uatConnection);
        HierarchyDatabaseUtils.setNodeKeywordToBLAnalysis(ndrsContentUuid, contentSetTexas, uatConnection);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(contentSetIsDisplayed, "Texas (Development) is displayed in the upper right hand corner"),
            () -> Assertions.assertTrue(nodPublishingOnlyIsDisplayed, MainHeaderElements.PUBLISH_APPROVE_NOD_NODES_ONLY_PAGE_HEADER + " is not displayed on the header and should be"),
            () -> Assertions.assertTrue(nodeHierarchyColumnIsDisplayed, "Node Hierarchy column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(rangeColumnIsDisplayed, "Range column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(flagColumnIsDisplayed, "Flag column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(volColumnIsDisplayed, "Vol column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(statusColumnIsDisplayed, "Status column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(codeColumnIsDisplayed, "Code column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(keywordColumnIsDisplayed, "Keyword column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(valueColumnIsDisplayed, "Value column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(modifiedByColumnIsDisplayed, "Modified by column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(modifiedyColumnIsDisplayed, "Modified column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(publishingStatusColumnIsDisplayed, "Publihsing status column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(readyUserColumnIsDisplayed, "Ready User column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(readyColumnIsDisplayed, "Ready column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(lawTrackingColumnIsDisplayed, "Law tracking column is not displayed on the header and should be"),
            () -> Assertions.assertTrue(nodKeywordFilterValuesMatchExpected, "There are unexpected keyword filter values when there should not be"),
            () -> Assertions.assertTrue(blNodeIsNotPublishedStatusAfterFindDocumentInHierarchy, "BL node is not not published status after finding it in hierarchy and should be"),
            () -> Assertions.assertTrue(arlNodeIsReadyStatusAfterFindDocumentInHierarchy, "BL node is not not published status after finding it in hierarchy and should be"),
            () -> Assertions.assertTrue(publishNowRadioButtonAppears, "Publish Now does appear on the NOD Publishing Only submission page"),
            () -> Assertions.assertTrue(publishTonightRadioButtonAppears, "Publish Tonight does appear on the NOD Publishing Only submission page")
        );
    }


    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
