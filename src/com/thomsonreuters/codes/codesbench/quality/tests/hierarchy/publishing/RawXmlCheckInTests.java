package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.publishing;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;


import java.sql.Connection;

public class RawXmlCheckInTests extends TestService
{
    HierarchyDatapodObject datapodObject;
    /**
     * NOTE: Test has strange activity with pop up window. As of now, keeping this test manual.
     * STORY:  <br>
     * SUMMARY: Any edits to the RawXML Editor and check-in should change the Node publishing status to Not Published <br>
     * USER:  Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void rawXmlCheckInTest()
    {
        String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String contentUuid = datapodObject.getSections().get(0).getContentUUID();

        Connection uatConnection = CommonDataMocking.connectToDatabase(environmentTag);
        PublishingDatabaseUtils.setPublishingNodeToReady(contentUuid, contentSetIowa, uatConnection);
        BaseDatabaseUtils.disconnect(uatConnection);

        hierarchyNavigatePage().goToHierarchyPage(contentSetIowa);
        loginPage().logIn();

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        // set mocked data to ready and check it
        siblingMetadataPage().rightClickSelectedNode();
        siblingMetadataContextMenu().setReadyPublishingStatus();
        boolean isNodeReady = siblingMetadataPage().isSelectedNodeReadyStatus();
        Assertions.assertTrue(isNodeReady, "The node should be in the ready status before we start the test");

        siblingMetadataPage().rightClickSelectedNode();
        siblingMetadataContextMenu().editRawXml();

        hierarchyRawXmlEditorPage().copyPasteXmlEditorText();
        hierarchyRawXmlEditorPage().clickSave();

        hierarchyRawXmlDocumentClosurePage().clickQuickLoadRadioButton();
        hierarchyRawXmlDocumentClosurePage().clickCheckInButton();

        siblingMetadataPage().rightClickSelectedNode();
        siblingMetadataContextMenu().refreshSelectedNode();

        boolean isNodeNotPublished = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        Assertions.assertTrue(isNodeNotPublished, "The node should be in the not publish status after checking in with raw xml editor");
    }

    @AfterEach
    public void cleanup()
    {
        if(datapodObject!=null)
        {
            datapodObject.delete();
        }
        BaseDatabaseUtils.disconnect();
    }
}
