package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class NodeCheckInLegalTests extends TestService
{
    Connection connection;
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - HALCYONST-1363 <br>
     * SUMMARY - This test verifies after adding text to the Value label in the Editor Page and checking in the changes,
     * that the node value is updated and the node flag status is unchanged for a grade level node <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void gradeLevelContentCheckInLegalTest()
    {
        connection = CommonDataMocking.connectToDatabase(getEnvironment());
        HierarchyDatapodConfiguration.getConfig().setGradeCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getGrades().get(0).getNodeUUID();
        String contentUuid = datapodObject.getGrades().get(0).getContentUUID();
        String greenCheckmarkStatus = "check";
        String testText = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check that node has a green checkmark as a flag value
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String validationValue = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean greenCheckmarkAppeared = validationValue.equals(greenCheckmarkStatus);
        Assertions.assertTrue(greenCheckmarkAppeared, "The selected node flag is not a green checkmark when it should be");

        //Add text to the value tag in the editor page
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().addTextInsideHeadingTextLabel(testText);
        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that the flag is still a green checkmark and the node value was updated
        String validationValue2 = siblingMetadataPage().getValidationFlagOfSelectedNode();
        String newNodeValue = siblingMetadataPage().getSelectedNodeValue();
        boolean greenCheckmarkAppeared2 = validationValue2.equals(greenCheckmarkStatus);
        boolean nodeValueUpdated = newNodeValue.contains(testText);
        Assertions.assertAll
        (
                () -> Assertions.assertTrue(greenCheckmarkAppeared2, "The selected node flag is not a green checkmark when it should be"),
                () -> Assertions.assertTrue(nodeValueUpdated, "The node's value didn't update as it should have")
        );

        //Restore Wip version
        HierarchyDatabaseUtils.deleteLatestWipVersion(contentUuid, connection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUuid, connection);

        //Check the added text is no longer in the document
        hierarchyNavigatePage().switchToHierarchyEditPage();

        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        boolean testTextAppears = editorTextPage().isGivenTextInsideHeadingTextLabel(testText);
        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();

        Assertions.assertFalse(testTextAppears, "The text under the Value label is correct");
    }

    /**
     * STORY/BUG - HALCYONST-1364 <br>
     * SUMMARY - This test verifies after adding text to the Value label in the Editor Page and checking in the changes, <br>
     * that the node value is updated and the node flag status is unchanged for a rule level node <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void ruleLevelContentCheckInLegalTest()
    {
        connection = CommonDataMocking.connectToDatabase(getEnvironment());
        HierarchyDatapodConfiguration.getConfig().setRuleCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String testText = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String nodeUuid = datapodObject.getRules().get(0).getNodeUUID();
        String contentUuid = datapodObject.getRules().get(0).getContentUUID();
        String greenCheckmarkStatus = "check";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check that node has a flag status of a green checkmark
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String validationValue = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean greenCheckmarkAppeared = validationValue.equals(greenCheckmarkStatus);
        Assertions.assertTrue(greenCheckmarkAppeared, "The selected node flag is not a green checkmark when it should be");

        //Add text to the value tag in the editor page
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().addTextInsideValueLabel(testText);
        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that the flag is still a green checkmark and the node value was updated
        String validationValue2 = siblingMetadataPage().getValidationFlagOfSelectedNode();
        String newNodeValue = siblingMetadataPage().getSelectedNodeValue();
        boolean greenCheckmarkAppeared2 = validationValue2.equals(greenCheckmarkStatus);
        boolean nodeValueUpdated = newNodeValue.contains(testText);
        Assertions.assertAll
        (
                () -> Assertions.assertTrue(greenCheckmarkAppeared2, "The selected node flag is not a green checkmark when it should be"),
                () -> Assertions.assertTrue(nodeValueUpdated, "The node's value didn't update as it should have")
        );

        //Restore Wip version
        HierarchyDatabaseUtils.deleteLatestWipVersion(contentUuid, connection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUuid, connection);

        //Check the added text is no longer in the document
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check the added text is no longer in the document
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        boolean testTextAppeared = editorTextPage().isGivenTextInsideValueLabel(testText);
        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();

        Assertions.assertFalse(testTextAppeared, "The text under the Value label is incorrect");
    }

    /**
     * STORY/BUG - HALCYONST-1365 <br>
     * SUMMARY - This test verifies after adding text to the Value label in the Editor Page and checking in the changes, <br>
     * that the node text is updated and the node flag status is unchanged for a specific level node <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void nodXndContentCheckInLegalTest()
    {
        connection = CommonDataMocking.connectToDatabase(getEnvironment());
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String testText = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String nodeUuid = datapodObject.getXNDNodes().get(0).getNodeUUID();
        String contentUuid = datapodObject.getXNDNodes().get(0).getContentUUID();
        String greenCheckmarkStatus = "check";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check that node has a flag status of a green checkmark
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String validationValue = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean greenCheckmarkAppeared = validationValue.equals(greenCheckmarkStatus);
        Assertions.assertTrue(greenCheckmarkAppeared, "the selected node flag is not a green checkmark when it should be");

        //Add text to the heading text tag in the editor page
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertTextInsideHeadingTextLabel(testText);
        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that the flag is still a green checkmark and the node text was updated
        String validationValue2 = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean greenCheckmarkAppeared2 = validationValue2.equals(greenCheckmarkStatus);
        Assertions.assertTrue(greenCheckmarkAppeared2, "the selected node flag is not a green checkmark when it should be");

        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        boolean nodeTextUpdated = editorTextPage().doesHeadingTextContainGivenText(testText);
        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();
        Assertions.assertTrue(nodeTextUpdated, "The node's value didn't update as expected");
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Restore Wip version
        HierarchyDatabaseUtils.deleteLatestWipVersion(contentUuid, connection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUuid, connection);

        //Check the added text is no longer in the document
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check the added text is no longer in the document
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        boolean testTextAppeared = editorTextPage().doesHeadingTextContainGivenText(testText);
        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();

        Assertions.assertFalse(testTextAppeared, "The text under the Value label is incorrect");
    }

    /**
     * STORY/BUG - HALCYONST-1366 <br>
     * SUMMARY - This test verifies after adding text to the Value label in the Editor Page and checking in the changes, <br>
     * that the node value is updated and the node flag status is unchanged for a specific level node <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void nodBlContentCheckInLegalTest()
    {
        connection = CommonDataMocking.connectToDatabase(getEnvironment());
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String testText = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss() + " ";
        String nodeUuid = datapodObject.getBLAnalyses().get(0).getNodeUUID();
        String contentUuid = datapodObject.getBLAnalyses().get(0).getContentUUID();
        String greenCheckmarkStatus = "check";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check that node has a flag status of a green checkmark
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String validationValue = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean greenCheckmarkAppeared = validationValue.equals(greenCheckmarkStatus);
        Assertions.assertTrue(greenCheckmarkAppeared, "the selected node flag is not a green checkmark when it should be");

        //Add text to the value tag in the editor page
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertTextToAnalysisParagraph(testText);
        editorPage().closeAndCheckInChanges();
        editorPage().closeSpellcheckWindow();
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that the flag is still a green checkmark and the node value was updated
        String validationValue2 = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean greenCheckmarkAppeared2 = validationValue2.equals(greenCheckmarkStatus);
        Assertions.assertTrue(greenCheckmarkAppeared2, "The selected node flag is not a green checkmark when it should be");

        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        boolean nodeTextUpdated = editorTextPage().doesFirstParagraphWithAnalysisTextContainGivenText(testText);
        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();
        Assertions.assertTrue(nodeTextUpdated, "The node's value didn't update as expected");

        //Restore Wip version
        HierarchyDatabaseUtils.deleteLatestWipVersion(contentUuid, connection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUuid, connection);

        //Check the added text is no longer in the document
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check the added text is no longer in the document
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        boolean testTextAppeared = editorTextPage().doesFirstParagraphWithAnalysisTextContainGivenText(testText);
        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();

        Assertions.assertFalse(testTextAppeared, "The text under the Value label is incorrect");
    }

    /**
     * STORY/BUG - HALCYONST-1367 <br>
     * SUMMARY - This test verifies after adding text to the Value label in the Editor Page and checking in the changes, <br>
     * that the node value is updated and the node flag status is unchanged for a specific level node <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void nodNodContentCheckInLegalTest()
    {
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String testText = "TEST" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
        String nodeUuid = datapodObject.getBluelines().get(0).getNodeUUID();
        String contentUuid = datapodObject.getBluelines().get(0).getContentUUID();
        String greenCheckmarkStatus = "check";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check that node has a flag status of a green checkmark
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        String validationValue = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean greenCheckmarkAppeared = validationValue.equals(greenCheckmarkStatus);
        Assertions.assertTrue(greenCheckmarkAppeared, "the selected node flag is not a green checkmark when it should be");

        //Add text to the value tag in the editor page
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertPhraseAtEndOfNODParagraph(testText);
        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();
        boolean editorPageClosed = hierarchyNavigatePage().switchToHierarchyEditPage();
        Assertions.assertTrue(editorPageClosed, "The editor page did not close when it should have");

        //Check that the flag is still a green checkmark and the node value was updated
        String validationValue2 = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean greenCheckmarkAppeared2 = validationValue2.equals(greenCheckmarkStatus);
        Assertions.assertTrue(greenCheckmarkAppeared2, "The selected node flag is not a green checkmark when it should be");

        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        boolean nodeTextUpdated = editorTextPage().doesFirstParagraphWithParaTextContainGivenText(testText);
        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();
        Assertions.assertTrue(nodeTextUpdated, "The node's value didn't update as expected");

        //Restore Wip version
        HierarchyDatabaseUtils.deleteLatestWipVersion(contentUuid, connection);
        HierarchyDatabaseUtils.updateWipVersionToLatest(contentUuid, connection);

        //Check the added text is no longer in the document
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check the added text is no longer in the document
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        boolean testTextAppeared = editorTextPage().doesFirstParagraphWithParaTextContainGivenText(testText);
        editorPage().closeDocumentWithNoChanges();
        editorPage().waitForEditorToClose();

        Assertions.assertFalse(testTextAppeared, "The text under the first paragraph is incorrect");
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
        if (connection != null)
        {
            disconnect(connection);
        }
    }
}
