package com.thomsonreuters.codes.codesbench.quality.tests.publishing.print;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

public class RunPubtagRefreshByVolsTests extends TestService
{
    Connection uatConnection;

    /**
     * STORY/BUG - HALCYONST-7886 <br>
     * SUMMARY - This test verifies that Pubtag Refresh should only set a Not Published status when a new WIP version is created <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void runPubtagRefreshByVolsTest()
    {
        String parentNodeContentUuid = "I896643401B1311DAB311FB76B2E4F553";
        String childNodeContentUuid = "I896CABE01B1311DAB311FB76B2E4F553";
        String grandchildContentUuid = "I8973D7D01B1311DAB311FB76B2E4F553";
        String contentSetId =  ContentSets.IOWA_DEVELOPMENT.getCode();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.setPublishingNodeToReady(parentNodeContentUuid,contentSetId,uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToReady(childNodeContentUuid,contentSetId,uatConnection);
        PublishingDatabaseUtils.setPublishingNodeToReady(grandchildContentUuid,contentSetId,uatConnection);

        int latestWipVersionInBeginig = HierarchyDatabaseUtils.getLatestWIPVersionOfSelectedContentUuid(uatConnection, grandchildContentUuid);

        homePage().goToHomePage();
        loginPage().logIn();

        //Check both parent and child node have publishing status of 'Ready'
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchContentUuid(parentNodeContentUuid);
        String volValue = siblingMetadataPage().getSelectedNodeVols();
        boolean parentNodeIsSetToReady = siblingMetadataPage().isSelectedNodeReadyStatus();
        Assertions.assertTrue(parentNodeIsSetToReady,"The selected parent node does not have a publishing status of 'Ready' when it should have");

        hierarchySearchPage().searchContentUuid(childNodeContentUuid);
        boolean childNodeIsSetToReady = siblingMetadataPage().isSelectedNodeReadyStatus();
        Assertions.assertTrue(childNodeIsSetToReady,"The selected child node does not have a publishing status of 'Ready' when it should have");

        hierarchySearchPage().searchContentUuid(grandchildContentUuid);
        boolean grandchildNodeIsSetToReady = siblingMetadataPage().isSelectedNodeReadyStatus();
        Assertions.assertTrue(grandchildNodeIsSetToReady,"The selected grandchild node does not have a publishing status of 'Ready' when it should have");

        //Select Vol and click 'Pubtag Refresh' to start workflow
        publishingMenu().goToPublishingPrintRunPubtagRefreshByVOLS();
        runPubtagRefreshByVolsPage().clickOnGivenVolValue(volValue);
        runPubtagRefreshByVolsPage().clickOnSingleArrowGoingRight();
        List<String> listOfSelectedVols = runPubtagRefreshByVolsPage().getListOfAllVolumesUnderSelectedVolsTable();
        boolean isCorrectVolumeInSelectedVolsTable = listOfSelectedVols.contains(volValue);
        Assertions.assertTrue(isCorrectVolumeInSelectedVolsTable,"The selected volume is not in the Selected Vol(s) table when it should be");

        runPubtagRefreshByVolsPage().clickPubtagRefresh();
        String workflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().clickClose();
        runPubtagRefreshByVolsPage().switchToRunPubtagRefreshByVolsPage();

        //Check that workflow finished
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished,"The workflow did not finish");

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        runPubtagRefreshByVolsPage().switchToRunPubtagRefreshByVolsPage();

        //Check that the nodes still have a publishing status of 'Ready'
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchContentUuid(parentNodeContentUuid);
        boolean parentNodeIsSetToReady2 = siblingMetadataPage().isSelectedNodeReadyStatus();

        hierarchySearchPage().searchContentUuid(childNodeContentUuid);
        boolean childNodeIsSetToReady2 = siblingMetadataPage().isSelectedNodeReadyStatus();

        // if the test fails out due to this grand child not being Ready
        hierarchySearchPage().searchContentUuid(grandchildContentUuid);
        boolean grandChildNodeIsSetToReady2 = siblingMetadataPage().isSelectedNodeReadyStatus();

        //go in and create an if statement and verirfy if it is not published instead of ready go in and check that wip version is made by us and num of wip versions updated by 1
        if(!grandChildNodeIsSetToReady2)
        {
            int latestWipVersionAfterTest = HierarchyDatabaseUtils.getLatestWIPVersionOfSelectedContentUuid(uatConnection, grandchildContentUuid);
            Assertions.assertEquals(latestWipVersionInBeginig+1, latestWipVersionAfterTest, "The latest Wip Version before test + 1 was not equal to the Wip Version after the test");
            String latestWipVersionModifiedBy = HierarchyDatabaseUtils.getModifiedByInTocContent(uatConnection, grandchildContentUuid, latestWipVersionAfterTest);
            Assertions.assertEquals(user().getUsername(), latestWipVersionModifiedBy,"The latest Wip Version was not modifed by our user -> look into why grandChild is not published");
            grandChildNodeIsSetToReady2 = true;
        }

        /*test was throwing an error until i added this in for some reason. */
        boolean finalGrandChildNodeIsSetToReady = grandChildNodeIsSetToReady2;

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(parentNodeIsSetToReady2,"The selected parent node does not have a publishing status of 'Ready' when it should have"),
            () -> Assertions.assertTrue(childNodeIsSetToReady2,"The selected child node does not have a publishing status of 'Ready' when it should have"),
            () -> Assertions.assertTrue(finalGrandChildNodeIsSetToReady,"The selected grand child node does not have a publishing status of 'Ready' when it should have")
        );
    }

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
