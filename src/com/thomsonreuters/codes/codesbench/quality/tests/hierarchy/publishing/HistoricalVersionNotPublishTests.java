package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.publishing;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;

import java.sql.Connection;
import java.util.List;

public class HistoricalVersionNotPublishTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    HierarchyDatapodObject datapodObject ;
    /**
     * STORY/BUG - HALCYONST-8519 <br>
     * SUMMARY - This test verifies that in Publishing enabled content sets, after checking in a node with either a prior version created or not,
     * should result in the publishing status being set to 'Not Published' <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void notPublishedStatusOnPriorWipVersionNodesTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getSections().get(0).getNodeUUID();
        String contentUuid = datapodObject.getSections().get(0).getContentUUID();
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();

        //Check that the content set has publishing enabled, and enable it if not
        PublishingDatabaseUtils.checkAndSetContentSetToBeEnabledForPublishing(contentSetIowa,uatConnection);

        //Login
        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSiblingMetadata();
        siblingMetadataContextMenu().setNotPublishedPublishingStatus();
        //Set Publishing Status to 'Ready' in Database
        PublishingDatabaseUtils.setPublishingNodeToReady(contentUuid, contentSetIowa, uatConnection);
        siblingMetadataPage().refreshPage();
        boolean checkReadyStatusOfSelectedNodeIsTrue = siblingMetadataPage().isSelectedNodeReadyStatus();
        Assertions.assertTrue(checkReadyStatusOfSelectedNodeIsTrue, "Node should be set to true but is not.");

        String nodeValue = siblingMetadataPage().getSelectedNodeValue();
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //Fake a change to create new version with new effective start date
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertSpaceAndRemoveSpace();
        editorPage().closeAndCheckInChangesWithGivenDate(currentDate);
        boolean expectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true,
                String.format("THIS IS NOT AN ERROR, just a notice that the selected node [%s] ended before your navigation filter date. Your navigation date has been reset to %s.", nodeUuid,DateAndTimeUtils.getYesterdaysDateWithoutLeadingZeros()));
        Assertions.assertTrue(expectedAlertAppeared, "The expected alert did not appear.");

        editorPage().switchToEditor();
        editorPage().closeCurrentWindowIgnoreDialogue();

        //Check that a new node version was created and that both node versions are set to 'Not Published'
        hierarchyNavigatePage().switchToHierarchyEditPage();
        List<WebElement> nodeList = siblingMetadataPage().getListOfNodesWithGivenValue(nodeValue);
        boolean nodeVersionCreated = nodeList.size()==2;
        Assertions.assertTrue(nodeVersionCreated,"2 nodes with the same value should be displayed");

        siblingMetadataPage().selectNodesByValue(nodeValue);
        boolean nodesAreSetToNotPublished = siblingMetadataPage().areSelectedNodesNotPublished();
        Assertions.assertTrue(nodesAreSetToNotPublished,"The 2 selected nodes with the same value should have a publishing status of 'Not Published' but do not");

        //Set newly created nde version to 'Ready'
        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue,currentDate);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().setReadyPublishingStatus();
        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue,currentDate);
        boolean checkReadyStatusOfSelectedNodeIsTrue2 = siblingMetadataPage().isSelectedNodeReadyStatus();
        Assertions.assertTrue(checkReadyStatusOfSelectedNodeIsTrue2, "Node should be set to true but is not.");

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectNodeByValueAndStartDate(nodeValue,currentDate);
        siblingMetadataPage().selectedSiblingMetadataEditContent();

        //Fake a change to create new version but with same effective date
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertSpaceAndRemoveSpace();
        editorPage().closeAndCheckInChangesWithGivenDate(currentDate);
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that a new node version was created and that both node versions are set to 'Not Published'
        List<WebElement> nodeList2 = siblingMetadataPage().getListOfNodesWithGivenValue(nodeValue);
        boolean nodeVersionCreated2 = nodeList2.size()==2;

        siblingMetadataPage().selectNodesByValue(nodeValue);
        boolean nodesAreSetToNotPublished2 = siblingMetadataPage().areSelectedNodesNotPublished();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(nodeVersionCreated2,"2 nodes with the same value should be displayed"),
            () -> Assertions.assertTrue(nodesAreSetToNotPublished2,"The 2 selected nodes with the same value should have a publishing status of 'Not Published' but do not")
        );
    }

    @AfterEach
    public void cleanup()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
            BaseDatabaseUtils.disconnect(uatConnection);
        }
    }
}