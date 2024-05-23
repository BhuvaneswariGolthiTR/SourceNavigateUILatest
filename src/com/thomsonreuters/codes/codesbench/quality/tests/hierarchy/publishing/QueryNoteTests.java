package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.publishing;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
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
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class QueryNoteTests extends TestService
{
    Connection connection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    HierarchyDatapodObject datapodObject;

    /**
     * STORY: <br>
     * SUMMARY: Asserts that any change to content, including deleting a Query Note via the Query Note Report, makes the related node "Not Published."
     * Also validates that deleting a Query Note via the Query Note Report functions properly.<br>
     * USER:  LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void queryDeletionInQueryNoteReport()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String contentUuid = datapodObject.getSections().get(0).getContentUUID();
        String todaysDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String queryNoteText = "Test QRDelete " + todaysDate;
        String[] queryFields = {queryNoteText, todaysDate};
        String queryType = "Date";

        connection = CommonDataMocking.connectToDatabase(environmentTag);
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid, contentSetIowa, connection);

        hierarchyNavigatePage().goToHierarchyPage(contentSetIowa);
        loginPage().logIn();

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();

        siblingMetadataPage().selectedSiblingMetadataEditContent();

        // Could add in a step to check if there are already queries in this node and to delete them to be able to not have to get the
        // query text input is the same issue

        editorPage().switchDirectlyToTextFrame();
        editorPage().rightClickOnFirstTextParagraphHeading();

        editorTextPage().rightClick(EditorTextPageElements.TEXT_PARAGRAPH_LABEL);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertQuery();
        queryNotePage().setType(queryType.toUpperCase());
        queryNotePage().setQueryNoteText(queryNoteText);
        queryNotePage().setQueryNoteActionDateToCurrentDate();
        queryNotePage().save();
        editorTextPage().switchToEditorTextArea();

        boolean textParagraphQueryIsSameAsInput = editorTextPage().checkQueryContent(queryType, queryFields, EditorTextPageElements.QUERY_NOTE_TYPE_DATE);
        Assertions.assertTrue(textParagraphQueryIsSameAsInput, "the query text is not the same as what we inputed");

        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectNodes(nodeValue);

        boolean isNodeNotPublishAfterAddingQueryNote = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        siblingMetadataPage().rightClickSelectedNode();
        siblingMetadataContextMenu().setReadyPublishingStatus();

        boolean isNodeSetToReadyStatusAfterSetReady = siblingMetadataPage().isSelectedNodeReadyStatus();

        toolsMenu().goToQueryNoteReport();
        queryNoteReportFiltersPage().setFilterQueryText(queryNoteText);
        queryNoteReportFiltersPage().setFilterType(queryType);
        queryNoteReportFiltersPage().setFilterStatus("ACTIVE");

        queryNotePage().clickRefreshButton();

        queryNoteReportGridPage().rightClickFirstQueryNote();
        boolean deletePageOpened = queryNoteReportContextMenu().deleteQueryNote();
        Assertions.assertTrue(deletePageOpened, "The Query Note Delete Page opened");

        queryNoteDeletePage().clickDeleteButton();

        queryNoteReportFiltersPage().setFilterStatus("DELETED");
        queryNotePage().clickRefreshButton();

        String statusOfQuery = queryNoteReportGridPage().getFirstQueryNoteStatus();

        queryNotePage().switchToHierarchyEditWindow();

        siblingMetadataPage().rightClickSelectedNode();
        siblingMetadataContextMenu().refreshSelectedNode();
        siblingMetadataPage().selectNodes(nodeValue);

        boolean isNodeNotPublishAfterQueryRemove = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(isNodeNotPublishAfterAddingQueryNote, "The node should be not publish after adding the query note"),
              () -> Assertions.assertTrue(isNodeSetToReadyStatusAfterSetReady, "The node should be ready after set ready"),
              () -> Assertions.assertEquals(statusOfQuery, "DELETED", "The status of the query note should be deleted after we delete it"),
              () -> Assertions.assertFalse(isNodeNotPublishAfterQueryRemove, "The status of the node should stay ready after removing query from query tools page")
        );
    }

    /**
     * STORY: <br>
     * SUMMARY: <br>
     * USER:  <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deletionResolutionOfQueryNotesInEditor()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String contentUuid = datapodObject.getSections().get(0).getContentUUID();
        String todaysDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String queryNoteText = "Test " + todaysDate;
        String[] queryFields = {queryNoteText, todaysDate};
        String queryType = "Date";

        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.setPublishingNodeToWestlawLoaded(contentUuid, contentSetIowa, connection);

        hierarchyNavigatePage().goToHierarchyPage(contentSetIowa);
        loginPage().logIn();

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String nodeValue = siblingMetadataPage().getSelectedNodeValue();

        siblingMetadataPage().selectedSiblingMetadataEditContent();

        editorPage().switchDirectlyToTextFrame();
        editorTextPage().rightClick(EditorTextPageElements.TEXT_PARAGRAPH_LABEL);
        editorTextPage().breakOutOfFrame();
        editorTextContextMenu().insertQuery();
        queryNotePage().setType(queryType.toUpperCase());
        queryNotePage().setQueryNoteText(queryNoteText);
        queryNotePage().setQueryNoteActionDateToCurrentDate();
        queryNotePage().save();
        editorTextPage().switchToEditorTextArea();

        boolean textParagraphQueryIsSameAsInput = editorTextPage().checkQueryContent(queryType, queryFields, EditorTextPageElements.QUERY_NOTE_TYPE_DATE);
        Assertions.assertTrue(textParagraphQueryIsSameAsInput);

        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectNodes(nodeValue);

        boolean isNodeNotPublishAfterAddingQueryNote = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        siblingMetadataPage().rightClickSelectedNode();
        siblingMetadataContextMenu().setReadyPublishingStatus();

        boolean isNodeSetToReadyStatusAfterSetReady = siblingMetadataPage().isSelectedNodeReadyStatus();

        siblingMetadataPage().selectedSiblingMetadataEditContent();

        editorPage().switchDirectlyToTextFrame();
        editorTextPage().rightClickQuery();

        editorQueryContextMenu().delete();
        deleteQueryNotePage().clickDeleteButton();

        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();

        hierarchyNavigatePage().switchToHierarchyEditPage();

        siblingMetadataPage().selectNodes(nodeValue);
        boolean isNotPublishAfterDelete = siblingMetadataPage().isSelectedNodeStatusNotPublished();

        Assertions.assertAll
        (
              () -> Assertions.assertTrue(isNodeNotPublishAfterAddingQueryNote, "The node should be not publish after checking in query"),
              () -> Assertions.assertTrue(isNodeSetToReadyStatusAfterSetReady, "The node should be ready status after setting to ready"),
              () -> Assertions.assertTrue(isNotPublishAfterDelete, "The node should be not publish after checking in deletion of query")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }

        BaseDatabaseUtils.disconnect(connection);
    }
}
