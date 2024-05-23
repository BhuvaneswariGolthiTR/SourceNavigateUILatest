package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.publishing;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.DocumentClosurePageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.reflectionUtils.PublishingReflectionUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.sql.Connection;

public class CheckInSetsNotPublishTests extends TestService
{
    Connection connection;
    HierarchyDatapodObject datapodObject;

    /**
     * STORY: HALCYONST-8081, HALCYONST-6957 <br>
     * SUMMARY: This test covers not publishing status for the following functions:
     * Editor Check In  and Restore WIP version <br>
     * USER: LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @ValueSource(strings = {PublishingReflectionUtils.READY_METHOD, PublishingReflectionUtils.APPROVE_METHOD, PublishingReflectionUtils.PUBLISH_COMPLETE_METHOD, PublishingReflectionUtils.WESTLAW_LOADED_METHOD})
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void publishStatusAfterCheckInTest(String method)
    {
        connection = CommonDataMocking.connectToDatabase(getEnvironment());
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();

        String contentUuid = datapodObject.getSections().get(0).getContentUUID();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String parentContentUuuid = datapodObject.getChapters().get(0).getContentUUID();
        String parentNodeUuid= datapodObject.getChapters().get(0).getNodeUUID();

        String todaysDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(parentContentUuuid, contentSetIowa, connection);
        String nodeValue = HierarchyDatabaseUtils.getNodeValue(contentUuid, contentSetIowa, connection);

        hierarchyNavigatePage().goToHierarchyPage(contentSetIowa);
        loginPage().logIn();

        // Editor Check-in
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        hierarchyTreePage().setNavigationTreeToYesterdaysDate();
        siblingMetadataPage().rightClickSiblingMetadata();
        siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        PublishingReflectionUtils.setPublishingStatus(method, contentUuid, contentSetIowa, connection);
        siblingMetadataPage().rightClickSiblingMetadata();
        siblingMetadataContextMenu().refreshSelection();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();

        editorTextPage().insertSpaceAndRemoveSpace();
        editorPage().closeDocumentWithChanges();

        //HALCYONST-8081
        boolean isFullVolsCompareDisplayed = editorClosurePage().isElementDisplayed(DocumentClosurePageElements.FULL_VOLS_COMPARE_RADIO_BUTTON);
        boolean isFullVolsRecompDisplayed = editorClosurePage().isElementDisplayed(DocumentClosurePageElements.FULL_VOLS_RECOMP_RADIO_BUTTON);

        editorClosurePage().setAssignEffectiveDate(todaysDate);
        editorClosurePage().selectQuickLoad();
        editorClosurePage().clickCheckInChanges();
        siblingMetadataPage().selectNodesByValue(nodeValue);

        boolean notPublishStatusAfterCheckIn = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        hierarchySearchPage().searchNodeUuid(parentNodeUuid);
        // Stabilization
        hierarchyNavigatePage().switchToHierarchyEditPage();

        boolean parentNodeRemainsLoadedToWestlaw = siblingMetadataPage().isSelectedNodeWestlawLoadedStatus();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(notPublishStatusAfterCheckIn, "The publishing status should be not published after making changes in the editor"),
            () -> Assertions.assertTrue(parentNodeRemainsLoadedToWestlaw, "The publishing status of the parent node should remain Loaded to Westlaw after its child section node edits a document"),
            () -> Assertions.assertTrue(isFullVolsCompareDisplayed, "The Full Vols compare law tracking radio button should be displayed"),
            () -> Assertions.assertTrue(isFullVolsRecompDisplayed, "The Full Vols Recomp law tracking radio button should be displayed")
        );
    }

    @AfterEach
    public void clean_Up()
    {
        if(datapodObject!=null)
        {
            datapodObject.delete();
        }
        BaseDatabaseUtils.disconnect(connection);
    }
}
