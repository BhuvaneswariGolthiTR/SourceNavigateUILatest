package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
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

public class HierarchyScriptsTests extends TestService
{

    HierarchyDatapodObject datapodObject;
    Connection connection;
    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that a workflow is created after adding and removing an assigned script. <br>
     * It also verifies that the assigned script appears when it should in the 'Remove Assigned Scripts' page <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void hierarchyAddScriptLegalTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String contentUuid = datapodObject.getSections().get(0).getContentUUID();
        String scriptPubtagValue = "CIV";
        String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Add assigned script
        hierarchySearchPage().searchNodeUuid(nodeUuid);

        if(!siblingMetadataPage().isSelectedNodeNotPublished())
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid, contentSetIowa, connection);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();

        boolean isNodeWestLawInitially = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();
        Assertions.assertTrue(isNodeWestLawInitially, "The node should be mocked up to WestLaw Loaded");

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsAddToScript();
        addAssignedScriptsPage().rightClickScriptWithGivenPubTagValue(scriptPubtagValue);
        addAssignedScriptsContextMenu().assignSingle();

        //Check that workflow has started and finished for script assignment
        boolean workflowStarted = yourWorkflowHasBeenCreatedPage().workflowHasStarted();
        Assertions.assertTrue(workflowStarted, "The workflow didn't started when it should have");

        String workflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().clickClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished,String.format("The workflow with id: %s did not finish",workflowId));

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check the selected node is set to 'Not Published' and then set it to 'Ready'
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();
        boolean isNodeNotPublishAfterScriptAssign = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setReadyPublishingStatus();

        boolean isNodeStatusReady = siblingMetadataPage().isSelectedNodeReadyStatus();
        Assertions.assertTrue(isNodeStatusReady, "The Status of the node did not change to Ready after setting it to Ready");

        //Remove assigned script
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsViewRemoveAssignedScripts();
        boolean scriptAppears = removeAssignedScriptsPage().isScriptWithGivenPubTagDisplayed(scriptPubtagValue);
        Assertions.assertTrue(scriptAppears, "The script with the given pubtag value does not appear");

        removeAssignedScriptsPage().rightClickScriptWithGivenPubTagValue(scriptPubtagValue);
        removeAssignedScriptsContextMenu().removeSingle();

        //Check that workflow has started and finished for script assignment
        boolean workflowStarted2 = yourWorkflowHasBeenCreatedPage().workflowHasStarted();
        Assertions.assertTrue(workflowStarted2, "The workflow didn't start when it should have");

        String workflowId2 = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().clickClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId2);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished2 = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished2,String.format("The workflow with id: %s did not finish",workflowId2));

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that assigned script was removed and node is set to 'Not Published'
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();
        boolean isNodeNotPublishAfterScriptRemoved = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsViewRemoveAssignedScripts();
        boolean scriptIsDisplayed = removeAssignedScriptsPage().isScriptWithGivenPubTagDisplayed(scriptPubtagValue);

        Assertions.assertAll
        (
                () -> Assertions.assertFalse(scriptIsDisplayed, "The script with the given pubtag value does appear when it should not"),
                () -> Assertions.assertTrue(isNodeNotPublishAfterScriptAssign, "The selected node should have a publishing status of 'Not Published' but did not"),
                () -> Assertions.assertTrue(isNodeNotPublishAfterScriptRemoved, "The selected node should have a publishing status of 'Not Published' but did not")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that a workflow is created after selecting a wip or pub view for the script and clicking ok <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void scriptsListingReportLegalTest()
    {
        String scriptValue = "1-0033 | ANNOTATED PRINT";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToHierarchyReportsScriptListingReport();

        //Select Wip view option and script to create workflow
        scriptListingReportPage().selectWipViewRadioButton();
        scriptListingReportPage().selectScriptWithGivenValue(scriptValue);
        scriptListingReportPage().clickOk();

        //Check workflow was created
        boolean workflowCreatedForWipView = yourWorkflowHasBeenCreatedPage().workflowHasStarted();
        Assertions.assertTrue(workflowCreatedForWipView,"The workflow was not created when it should have");
        yourWorkflowHasBeenCreatedPage().clickClose();

        homePage().switchToPage();
        hierarchyMenu().goToHierarchyReportsScriptListingReport();

        //Select Pub view option and script to create workflow
        scriptListingReportPage().selectPubViewRadioButton();
        scriptListingReportPage().selectScriptWithGivenValue(scriptValue);
        scriptListingReportPage().clickOk();

        //Check workflow was created
        boolean workflowCreatedForPubView = yourWorkflowHasBeenCreatedPage().workflowHasStarted();
        Assertions.assertTrue(workflowCreatedForPubView,"The workflow was not created when it should have");
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
        if(connection != null)
        {
            BaseDatabaseUtils.disconnect(connection);
        }
    }
}