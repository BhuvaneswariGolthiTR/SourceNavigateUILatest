package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.publishing;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;

import java.sql.Connection;
import java.util.ArrayList;

public class RangeRepealPublishingStatusAfterCheckInTests extends TestService
{
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - HALCYONST-7160 <br>
     * SUMMARY - Find a node that is part of a node that contains another node that does not have a publishing status of 'Not Published'.
     * After checking in changes to the node that is a part of a range, through the Common Editor page or the Update Metadata page, and
     * setting the law tracking status to 'Full Vols' or 'Quickload', all nodes in the range should be set to 'Not Published.' <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void rangeRepealPublishingStatusAfterCheckInTest()
    {
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        ArrayList<String> sectionNodes = new ArrayList<>();
        sectionNodes.add(datapodObject.getSections().get(0).getNodeUUID());
        sectionNodes.add(datapodObject.getSections().get(1).getNodeUUID());

        HierarchyDatapodConfiguration.getConfig().setRange(sectionNodes ,connection);

        String val1 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(datapodObject.getSections().get(0).getNodeUUID(),contentSetIowa,connection);
        String val2 = HierarchyDatabaseUtils.getNodeValueByNodeUUID(datapodObject.getSections().get(1).getNodeUUID(),contentSetIowa,connection);

        String contentUuid1 = datapodObject.getSections().get(0).getContentUUID();
        String[] values = {val1,val2};
        String[] valuesAfterValueChange = {values[0]+"test", values[1]};

        homePage().goToHomePage();
        loginPage().logIn();

        //Make sure user is a publish approver list so the user has access to the Set Publish Approved context menu option in hierarchy
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, connection);

        //Set nodes to 'Ready' publishing status, fake a change, and check in changes with the law tracking set to 'Full Vols'
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchContentUuid(contentUuid1);
        boolean nodeIsStartOfRange = siblingMetadataPage().isSelectedNodeTheStartOfARange();
        Assertions.assertTrue(nodeIsStartOfRange,"The selected node should have a rm symbol for the start of a range but in fact does not.");
        boolean node1IsNotSetToNotPublished = !siblingMetadataPage().isSelectedNodeStatusNotPublished();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().expandPublishingStatus();

        //HALCYONST-7128F
        boolean notPublishedIsDisabledOnSingleNode = siblingMetadataContextMenu().isElementDisabled(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_NOT_PUBLISHED);
        boolean setPublishReadyIsDisabledOnSingleNode = siblingMetadataContextMenu().isElementDisabled(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_PUBLISH_READY);
        boolean setPublishApprovedIsDisabledOnSingleNode = siblingMetadataContextMenu().isElementDisabled(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_PUBLISH_APPROVED);

        //We are not setting the publishing status in the DB because these are nodes in a range and that could potentially cause issues. Could still work in DB given this range is only 2 nodes
        if(node1IsNotSetToNotPublished)//Since we can't set them to 'Not Published' at the same time if only one is set to 'Not Published', we have to set them individually
        {
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }
        siblingMetadataPage().selectNodesByValue(values[1]);
        boolean node2IsNotSetToNotPublished = !siblingMetadataPage().isSelectedNodeStatusNotPublished();
        if(node2IsNotSetToNotPublished)
        {
            siblingMetadataPage().rightClickSelectedSiblingMetadata();
            siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        }
        siblingMetadataPage().selectNodes(values);
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();

        siblingMetadataContextMenu().expandPublishingStatus();

        //HALCYONST-7128
        boolean setPublishReadyIsEnabledOnAllNodesInRange = !siblingMetadataContextMenu().isElementDisabled(SiblingMetadataContextMenuElements.PUBLISHING_STATUS_PUBLISH_READY);

        //HALCYONST-9486
        siblingMetadataContextMenu().setReadyPublishingStatus();
        boolean nodesAreNotSetToNotPublished = siblingMetadataPage().areSelectedNodesPublishReady();
        Assertions.assertTrue(nodesAreNotSetToNotPublished,"The selected nodes should not have a publishing status of 'Not Published' but in fact does.");

        siblingMetadataPage().selectNodesByValue(values[0]);

        //Fake a change while setting the law tracking to 'Full Vols'
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertSpaceAndRemoveSpace();
        editorPage().breakOutOfFrame();
        editorToolbarPage().clickToolbarClose();
        editorClosurePage().selectFullVols();
        editorClosurePage().clickCheckInChanges();

        //Check that the publishing status of both nodes in the range are set to 'Not Published'
        siblingMetadataPage().selectNodes(values);
        boolean nodesAreSetToNotPublished = siblingMetadataPage().areSelectedNodesNotPublished();
        Assertions.assertTrue(nodesAreSetToNotPublished,"The selected nodes should have a publishing status of 'Not Published' but in fact does not.");

        //Set the nodes' publishing status back to 'Ready'
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().setReadyPublishingStatus();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectNodes(values);
        boolean nodesAreNotSetToNotPublished2 = siblingMetadataPage().areSelectedNodesPublishReady();
        Assertions.assertTrue(nodesAreNotSetToNotPublished2,"The selected nodes should have a publishing status of ready");

        //Make a change through the 'Update Metadata' page while setting the law tracking to 'Quick Load'
        siblingMetadataPage().selectNodes(values[0]);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().updateMetadata();
        updateMetadataPage().clearAndInsertTextToValueTextBox(values[0] + "test");
        updateMetadataPage().clickQuickLoadOk();

        //Check that the publishing status of both nodes in the range are set to 'Not Published'
        siblingMetadataPage().selectNodes(valuesAfterValueChange);
        boolean nodesAreSetToNotPublished2 = siblingMetadataPage().areSelectedNodesNotPublished();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodesAreSetToNotPublished2,"The selected nodes should have a publishing status of 'Not Published' but in fact does not."),
            () -> Assertions.assertTrue(notPublishedIsDisabledOnSingleNode, "Not Published is enabled when right clicking a single node and should not be"),
            () -> Assertions.assertTrue(setPublishReadyIsDisabledOnSingleNode, "Set Publish Ready is enabled when right clicking a single node and should not be"),
            () -> Assertions.assertTrue(setPublishApprovedIsDisabledOnSingleNode, "Set Publish Approved is enabled when right clicking a single node and should not be"),
            () -> Assertions.assertTrue(setPublishReadyIsEnabledOnAllNodesInRange, "Set Publish Ready is disabled when all nodes in the range are selected and should not be")
        );
    }

    @AfterEach
    public void cleanup()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
