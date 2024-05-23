package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPOM;
import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ViewMetadataPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Connection;

public class ViewVolumeInformationTests extends TestService
{
    private HierarchyDatapodObject datapodObject;

    public static Object[][] editContentByVolumePart()
    {
        return new Object[][]
                {
                        {HierarchyPOM.EDIT_BOV_CONTENT, EditorTextPageElements.BOV_TAG_IN_EDITOR_TEXT_BODY},
                        {HierarchyPOM.EDIT_EOV_CONTENT, EditorTextPageElements.EOV_TAG_IN_EDITOR_TEXT_BODY},
                };
    }



    /**
     * STORY/BUG - HALCYONST-726 AND HALCYONST-1368 <br>
     * SUMMARY - This test verifies that the 'Edit Content' function works within the Volume Information page for both the beginning and the end of the volume <br>
     * USER - LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("editContentByVolumePart")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void editContentFromViewVolumeLegalTest(String editContentButtonToClickForPartOfVolume, String tagToEdit)
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String oldVolumeNumber = "9999";
        String oldVolumeTitle = "QED Testing";
        String newVolumeNumber = "0040";
        String newVolumeTitle = "19.1 TO 38D. END SOVEREIGNTY AND MANAGEMENT 2001";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check that the Volume Information window has appeared
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean volumeInformationWindowAppeared = siblingMetadataContextMenu().viewVolumeInfo();
        Assertions.assertTrue(volumeInformationWindowAppeared,"The Volume Information window did not appear when it should have");

        //Check that the volume title is updated correctly after changing the volume number
        volumeInformationPage().setVolumeNumberDropdown(newVolumeNumber);
        boolean volumeTitleIsCorrect = volumeInformationPage().isVolumeTitleEqualTo(newVolumeTitle);
        Assertions.assertTrue(volumeTitleIsCorrect,"The Volume Title was not updated correctly");

        //Make fake changes
        volumeInformationPage().click(editContentButtonToClickForPartOfVolume);
        editorPage().switchToEditor();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertAndRemoveSpaceInVolumeNumberText();

        //Check that correct tag appears in the text and is not read only
        boolean correctTagIsDisplayed = editorTextPage().isElementDisplayed(tagToEdit);
        editorPage().switchToEditor();
        boolean editorIsReadOnly = editorPage().checkEditorIsReadOnly();
        Assertions.assertFalse(editorIsReadOnly,"The editor page is read only when it should not be");
        Assertions.assertTrue(correctTagIsDisplayed,"The editor page does contain the correct body tag");

        editorPage().closeAndCheckInChanges();
        if(tagToEdit.equals(EditorTextPageElements.EOV_TAG_IN_EDITOR_TEXT_BODY))
        {
            editorPage().closeSpellcheckWindow();
        }
        editorPage().waitForEditorToClose();

        //Check that the the volume title is updated correctly after changing the volume number back to the original
        volumeInformationPage().switchToVolumeInformationPage();
        volumeInformationPage().setVolumeNumberDropdown(oldVolumeNumber);
        boolean volumeTitleIsCorrect2 = volumeInformationPage().isVolumeTitleEqualTo(oldVolumeTitle);
        Assertions.assertTrue(volumeTitleIsCorrect2,"The volume title did not change back correctly");
    }

    public static Object[][] rawXmlByVolumePart()
    {
        return new Object[][]
                {
                        {HierarchyPOM.EDIT_BOV_XML_CONTENT, "<bov ", "</bov>"},
                        {HierarchyPOM.EDIT_EOV_XML_CONTENT, "<eov ", "</eov>"},
                };
    }

    /**
     * STORY/BUG - HALCYONST-727,728 <br>
     * SUMMARY - This test verifies after opening the Raw Xml Editor page within the Volume Information page for both the beginning and the end of the volume, <br>
     * that the text is not read only and the associated open and close tags appear within the text <br>
     * USER - LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("rawXmlByVolumePart")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void editRawXmlFromVolumeLegalTest(String editRawXmlButtonToClickForPartOfVolume, String openTag,String closeTag)
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Open Volume Information window
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean volumeInformationWindowAppeared = siblingMetadataContextMenu().viewVolumeInfo();
        Assertions.assertTrue(volumeInformationWindowAppeared,"The Volume Information window did not appear when it should have");

        //Check that the Raw Xml Editor window appears
        volumeInformationPage().click(editRawXmlButtonToClickForPartOfVolume);
        boolean rawXmlEditorPageAppeared = hierarchyRawXmlEditorPage().switchToRawXmlEditorPage();
        boolean textIsReadOnly = hierarchyRawXmlEditorPage().isEditorTextReadOnly();
        boolean openTagIsShown = hierarchyRawXmlEditorPage().isGivenTextInEditor(openTag);
        boolean closeTagIsShown = hierarchyRawXmlEditorPage().isGivenTextInEditor(closeTag);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(rawXmlEditorPageAppeared,"The Raw Xml Editor page did not appear when it should have"),
                        () -> Assertions.assertFalse(textIsReadOnly,"The displayed text is read only when it should not be"),
                        () -> Assertions.assertTrue(openTagIsShown,"The associated open tag is not shown in the editor"),
                        () -> Assertions.assertTrue(closeTagIsShown,"The associated close tag is not shown in the editor")
                );
    }

    public static Object[][] addAndRemoveScriptByVolumePart()
    {
        return new Object[][]
                {
                        {HierarchyPOM.ADD_BOV_SCRIPT, HierarchyPOM.REMOVE_BOV_SCRIPT},
                        {HierarchyPOM.ADD_EOV_SCRIPT, HierarchyPOM.REMOVE_EOV_SCRIPT},
                };
    }

    /**
     * STORY/BUG - HALCYONST-725 <br>
     * SUMMARY - This test verifies that adding and removing a script of a node can be successfully done from the View Volume Info page <br>
     * USER - LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("addAndRemoveScriptByVolumePart")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addAndRemoveScriptBeginningOfVolumeLegalTest(String addScriptButtonForPartOfVolume, String removeScriptButtonForPartOfVolume)
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String pubTagValue = "CIV";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check that the Volume Information window appeared
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean volumeInformationWindowAppeared = siblingMetadataContextMenu().viewVolumeInfo();
        Assertions.assertTrue(volumeInformationWindowAppeared,"The Volume Information window did not appear when it should have");

        //Add assigned script
        volumeInformationPage().click(addScriptButtonForPartOfVolume);
        addAssignedScriptsPage().switchToAddAssignedScriptsPage();
        addAssignedScriptsPage().rightClickScriptWithGivenPubTagValue(pubTagValue);
        addAssignedScriptsContextMenu().assignSingle();

        //Check that workflow finished
        String workflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().closeWorkflowConfirmationPage();

        volumeInformationPage().switchToVolumeInformationPage();
        volumeInformationPage().clickCloseButton();

        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished,String.format("The workflow with id: %s did not finish",workflowId));

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that the Volume Information window appeared
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean volumeInformationWindowAppeared2 = siblingMetadataContextMenu().viewVolumeInfo();
        Assertions.assertTrue(volumeInformationWindowAppeared2,"The Volume Information window did not appear when it should have");

        //Remove assigned script
        volumeInformationPage().click(removeScriptButtonForPartOfVolume);
        removeAssignedScriptsPage().switchToRemoveAssignedScriptsPage();
        removeAssignedScriptsPage().rightClickScriptWithGivenPubTagValue(pubTagValue);
        removeAssignedScriptsContextMenu().removeSingle();

        //Check that the workflow finished
        String workflowId2 = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().closeWorkflowConfirmationPage();

        volumeInformationPage().switchToVolumeInformationPage();
        volumeInformationPage().clickCloseButton();

        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId2);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished2 = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished2,String.format("The workflow with id: %s did not finish",workflowId2));

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
    }

    public static Object[][] viewMetadataByPartOfVolume()
    {
        return new Object[][]
                {
                        {HierarchyPOM.BOV_METADATA, "BOV"},
                        {HierarchyPOM.EOV_METADATA, "EOV"},
                };
    }

    /**
     * STORY/BUG - HALCYONST-1096 <br>
     * SUMMARY - This test verifies that the node type is correct and the node type dropdown is disabled on the
     * View Metadata page when accessing it through the Volume Information page <br>
     * USER - LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("viewMetadataByPartOfVolume")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void viewMetadataFromViewVolumeLegalTest(String viewMetadataButtonForPartOfVolume, String nodeType)
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //CHeck that the Volume Information page appeared
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean volumeInfoWindowAppeared = siblingMetadataContextMenu().viewVolumeInfo();
        Assertions.assertTrue(volumeInfoWindowAppeared,"The Volume Information window did not appear when it should have");

        //Check that the View Metadata page appeared
        volumeInformationPage().click(viewMetadataButtonForPartOfVolume);
        boolean viewMetadataPageAppeared = viewMetadataPage().switchToViewMetadataPage();
        Assertions.assertTrue(viewMetadataPageAppeared,"The View Metadata page did not appear when it should have");

        //Check that the node type is correct and the node type dropdown is disabled
        boolean nodeTypeIsCorrect = viewMetadataPage().isNodeTypeEqualToGivenValue(nodeType);
        boolean nodeTypeIsEnabled = viewMetadataPage().isElementEnabled(ViewMetadataPageElements.nodeTypeDropdown);
        viewMetadataPage().clickCancel();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(nodeTypeIsCorrect,"The node type of the given node is incorrect"),
                        () -> Assertions.assertFalse(nodeTypeIsEnabled,"The node type dropdown option is enabled when it should be disabled")
                );
    }

    public static Object[][] navigateToPartOfVolume()
    {
        return new Object[][]
                {
                        {HierarchyPOM.JUMP_TO_BEGIN, "TITLE"},
                        {HierarchyPOM.JUMP_TO_END, "="},
                };
    }

    /**
     * STORY/BUG - HALCYONST-1097/1098 <br>
     * SUMMARY - This test verifies that the selected node is correct and has the correct metadata when accessing it through the Volume Information page <br>
     * USER - LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("navigateToPartOfVolume")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void viewFirstOrLastNodeFromViewVolumeLegalTest(String navigateToButtonForPartOfVolume, String expectedKeyword)
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getChapters().get(0).getNodeUUID();
        String sectionNodeUUID = datapodObject.getSections().get(0).getNodeUUID();

        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String expectedValue = expectedKeyword.equals("TITLE") ? "XCIX" : HierarchyDatabaseUtils.getNodeValueByNodeUUID(sectionNodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check that the Volume Information page appeared
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean volumeInfoWindowAppeared = siblingMetadataContextMenu().viewVolumeInfo();
        Assertions.assertTrue(volumeInfoWindowAppeared,"The Volume Information window did not appear when it should have");

        //Check that selected node has the correct metadata
        volumeInformationPage().click(navigateToButtonForPartOfVolume);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        String selectedNodeValue = siblingMetadataPage().getSelectedNodeValue();
        boolean valueOfNodeIsCorrect = selectedNodeValue.equals(expectedValue);

        String actualKeyword = siblingMetadataPage().getSelectedGridRowKeyword();
        boolean keywordOfNodeIsCorrect = actualKeyword.equals(expectedKeyword);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(valueOfNodeIsCorrect,"The value of the selected node is not as expected"),
                        () -> Assertions.assertTrue(keywordOfNodeIsCorrect,"The keyword of the selected node is not as expected")
                );
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
