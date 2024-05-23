package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishingstatusreport.nodonlypubnavigateevaluation;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class NodOnlyPubNavigateEvaluationWipStatusTests extends TestService
{
    Connection uatConnection;
    String contentSetTexas = ContentSets.TEXAS_DEVELOPMENT.getCode();

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }

    /**
     * STORY: Halcyonst-9277 <br>
     * SUMMARY: This test verifies a publishing workflow finishes and verifies the nodes publishing status of 'WIP published to pub'
     *  through publish now option in NOD-only pub navigate evaluation <br>
     * USER: citeline <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void nodOnlyPubNavigateEvaluationUiPublishNowOptionChangingWIPStatus()
    {
        String contentUuid = "IC30D69800DA311DEAAF5855BC21FDE6B";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        String nodeValue = HierarchyDatabaseUtils.getNodeValue(contentUuid, contentSetTexas, uatConnection);
        String nodeUuid = HierarchyDatabaseUtils.getNodeUuidWithContentUuid(uatConnection, contentUuid);

        PublishingDatabaseUtils.setPublishingNodeToPublishComplete(contentUuid, contentSetTexas, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().setMyContentSet(ContentSets.TEXAS_DEVELOPMENT.getName());

        hierarchyMenu().goToPubNavigate();
        hierarchyPubNavigateSearchPage().searchContentUuid(contentUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().releaseHold();
        siblingMetadataPage().waitForGridRefresh();

        boolean nodOnlyPubNavigateEvaluationPageAppears = publishingMenu().goToPublishingStatusReportsNodOnlyPubNavigateEvaluation();
        Assertions.assertTrue(nodOnlyPubNavigateEvaluationPageAppears, "Nod-Only Pub Navigate Evaluation window did open");
        gridPage().waitForGridLoaded();

        gridHeaderPage().openMenuForNodeUuidColumn();
        gridHeaderFiltersPage().setFilterValue(nodeUuid);
        gridPage().rightClickByNodeTargetValueOnlyDiv(nodeValue);
        gridContextMenu().selectForUpdatedStatus();
        toolbarPage().clickNext();

        toolbarPage().clickPublishNowRadioButton();
        toolbarPage().clickSubmit();
        gridPage().waitForToastMessageToAppear();
        boolean verifyToastMessageAppeared = gridPage().checkUpdatedStatusToastMessage();
        Assertions.assertTrue(verifyToastMessageAppeared,"Toast message appeared");
        toolbarPage().clickBack();
        toolbarPage().closeCurrentWindowIgnoreDialogue();

        hierarchyPubNavigatePage().switchToPubNavigatePage();
        toolsMenu().goToWorkflowReportingSystem();

        workflowSearchPage().filterWorkflowAndCheckGridStatus("WestlawNovusPublishNow", "SOS.TXT", "",  user().getUsername());
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        boolean workflowForWestlawNovusFinished = workflowSearchPage().waitForFirstWorkflowAppearedAndFinishedTenMinutes();
        Assertions.assertTrue(workflowForWestlawNovusFinished, "workflow For WestlawNovusPublishNow should have finsihed: " + workflowId);
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();

        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        hierarchyPubNavigatePage().switchToPubNavigatePage();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        boolean verifyHierachyNavStatusComplete = siblingMetadataPage().isSelectedNodePublishedToPubInHierarchyNavigate();

        hierarchyMenu().goToPubNavigate();
        hierarchyPubNavigateSearchPage().searchNodeUuid(nodeUuid);
        boolean verifyPubNavStatusComplete = siblingMetadataPage().isSelectedNodePublishedToPubInHierarchyPubNavigate();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(verifyHierachyNavStatusComplete, "Status in hierarchy Nav is published to pub"),
            () -> Assertions.assertTrue(verifyPubNavStatusComplete, "Status in Pub nav is published to pub")
        );
    }
}
