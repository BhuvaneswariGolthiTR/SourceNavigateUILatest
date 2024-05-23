package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.publishing;

import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Connection;

public class PubNavigatePublishingStatusTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    HierarchyDatapodObject datapodObject;

    public static Object[][] publishingStatusesToSetAndCheck()
    {
        return new Object[][]
        {
            {PublishingDatabaseConstants.SET_NODE_TO_CODESBENCH_FAILURE_QUERY, "Status_WIP_PUBLISHED_TO_PUB", "Status_PUB_CODESBENCH_FAILURE"},
            {PublishingDatabaseConstants.SET_NODE_TO_PUBLISH_COMPLETE_QUERY, "Status_WIP_PUBLISHED_TO_PUB", "Status_PUB_HOLD_NODE"},
            {PublishingDatabaseConstants.SET_NODE_TO_WESTLAW_LOADED_QUERY, "Status_WIP_LOADED_TO_WESTLAW", "Status_PUB_LOADED_TO_WESTLAW"},
        };
    }

    /**
     * STORY/BUG - HALCYONST-7650 <br>
     * SUMMARY - This test verifies that after setting a node to a certain publishing status, and faking a change to set the status to 'Not Published' in Hierarchy Navigate,
     * the publishing status of that node should be the same as it was before the change in the Hierarchy Pub Navigate page <br>
     * USER - LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("publishingStatusesToSetAndCheck")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void pubNavigatePublishingStatusWithSameWipVersionTest(String publishingStatusToSetNodeTo,String publishingStatusToCheckInNavigate,String publishingStatusToCheckInPubNavigate)
    {
        HierarchyDatapodConfiguration.getConfig().setInsertInPubNav(true);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String contentUuid = datapodObject.getChapters().get(0).getContentUUID();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.setPublishingNodeStatus(contentUuid, contentSetIowa, publishingStatusToSetNodeTo, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        //Check that the node's publishing statuses are correct in both Hierarchy Navigate and Pub Navigate
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchContentUuid(contentUuid);
        //Stabilization
        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean selectedNodeIsSetCorrectlyInNavigate = siblingMetadataPage().isSelectedNodeSetToGivenPublishingStatus(publishingStatusToCheckInNavigate);
        Assertions.assertTrue(selectedNodeIsSetCorrectlyInNavigate,"The selected node is not set to the correct publishing status when it should be");

        hierarchyMenu().goToPubNavigate();
        boolean selectedNodeIsSetCorrectlyInPubNavigate = siblingMetadataPage().isSelectedNodeSetToGivenPublishingStatus(publishingStatusToCheckInPubNavigate);
        Assertions.assertTrue(selectedNodeIsSetCorrectlyInPubNavigate,"The selected node is not set to the correct publishing status when it should be");

        //Fake a change to set the status to 'Not Published' in Hierarchy Navigate
        hierarchyMenu().goToNavigate();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditorTextFrame();
        editorTextPage().insertSpaceAndRemoveSpaceRunningHeadBlock();
        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();

        //Check that the node's publishing status is set to 'Not Published' in Hierarchy Navigate
        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean selectedNodeIsSetToNotPublishedInSiblingMetadata = siblingMetadataPage().isSelectedNodeStatusNotPublished();
        boolean selectedNodeIsSetToNotPublishedInDispDeriv = dispositionDerivationPage().isSelectedNodeStatusNotPublished();

        //Check that the publishing status of the node is unchanged in Pub Navigate
        hierarchyMenu().goToPubNavigate();
        boolean nodeIsSetToCodesbenchFailureInSiblingMetadata = siblingMetadataPage().isSelectedNodeSetToGivenPublishingStatus(publishingStatusToCheckInPubNavigate);
        boolean nodeIsSetToCodesbenchFailureInDispDeriv = dispositionDerivationPage().isSelectedNodeSetToGivenPublishingStatus(publishingStatusToCheckInPubNavigate);

        //Delete the wip version created by faking a change
        HierarchyDatabaseUtils.deleteLatestWipVersion(contentUuid,uatConnection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUuid,uatConnection);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(selectedNodeIsSetToNotPublishedInSiblingMetadata,"The selected node is not set to 'Not Published' in the sibling metadata pane when it should be"),
            () -> Assertions.assertTrue(selectedNodeIsSetToNotPublishedInDispDeriv,"The selected node is not set to 'Not Published' in the disp/deriv pane when it should be"),
            () -> Assertions.assertTrue(nodeIsSetToCodesbenchFailureInSiblingMetadata,"The selected node is not set to the correct publishing status in the sibling metadata pane when it should be"),
            () -> Assertions.assertTrue(nodeIsSetToCodesbenchFailureInDispDeriv,"The selected node is not set to the correct publishing status in the disp/deriv pane when it should be")
        );
    }

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
