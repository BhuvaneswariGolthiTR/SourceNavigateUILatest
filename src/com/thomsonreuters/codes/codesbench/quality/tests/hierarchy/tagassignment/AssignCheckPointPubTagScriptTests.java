package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.nodes.SectionNode;
import org.junit.jupiter.api.*;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static org.assertj.core.api.Assertions.assertThat;

public class AssignCheckPointPubTagScriptTests extends TestService
{
    private static final String CHECKPOINT_TAG = "CHKPNT";
    private static final String SCRIPT_TAG = "CHKP";
    private static final String DESCENDANTS_INDICATOR = "Descendants";
    private static final String SINGLE_INDICATOR = "Single";
    private static final String DATE = "01/01/2005";
    private static final String PARENT_NODE_MESSAGE = "[parent node]";
    private static final String FIRST_DESCENDANT_NODE_MESSAGE = "[first descendant node]";
    private static final String SECOND_DESCENDANT_NODE_MESSAGE = "[second descendant node]";
    private static final String EMPTY_MESSAGE = "";
    private static final String NEW_VERSION_NODE_MESSAGE = "[new version node]";
    private static final String OLD_VERSION_NODE_MESSAGE = "[old version node]";

    HierarchyDatapodObject datapodObject;

    @BeforeEach
    public void loginAndGoToHierarchyPage()
    {
        //Log on to Iowa (Development) as Legal user.
        //Go to Hierarchy Navigate.
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
    }

    /**
     * STORY/BUG - HALCYONST-12578<br>
     * SUMMARY - Assign with Descendants, partial descendants Checkpoint Product Tagged test<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignWithDescendantsSingleAndDescendantsStatusTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(3);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String parentUuid = datapodObject.getChapters().get(0).getNodeUUID();
        String firstDescendantUuid = datapodObject.getSections().get(0).getNodeUUID();
        String secondDescendantUuid = datapodObject.getSections().get(1).getNodeUUID();

        addCheckpointTagIfProductTagIsNotCheckpoint(firstDescendantUuid);
        removeCheckpointTagIfProductTagIsCheckpoint(secondDescendantUuid);
        removeCheckpointTagIfProductTagIsCheckpoint(parentUuid);

        //Right click on the selected node and select Scripts -> Add Scripts.
        addToScript();

        //Right click on the CHKP pub tag and select Assign With Descendants.
        rightClickOnPubTagAndAssignWithDescendants();

        //VERIFY: Your Workflow Has Been Created page is opened. Workflow Details Page is open. Workflow is finished and close page.
        //Right click on the selected node and select Refresh Selection.
        verifyWorkflowIsFinishedAndRefreshSelection();

        //Right click on the selected node and click Edit Content.
        //VERIFY: there is NO CHKP pub tag on text elements.
        //Close Editor.
        editDocumentAndVerifyChkpTagIsMissing(PARENT_NODE_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there IS the CHKP scriptTag on table and the Indicator for the scriptTag is ‘Descendants’.
        assertThatScriptWithPubTagIsDisplayedWithRightScriptTagIndicator(PARENT_NODE_MESSAGE, DESCENDANTS_INDICATOR);
        removeAssignedScriptsPage().clickClose();

        //Right click on the selected node and click Edit Content.
        //VERIFY: there IS CHKP pub tag on text elements.
        //Close Editor.
        searchForNodeWithUuid(firstDescendantUuid);
        editDocumentAndVerifyChkpTagIsPresent(FIRST_DESCENDANT_NODE_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there IS the CHKP scriptTag on table and the Indicator for the scriptTag is ‘Single’.
        assertThatScriptWithPubTagIsDisplayedWithRightScriptTagIndicator(FIRST_DESCENDANT_NODE_MESSAGE, SINGLE_INDICATOR);
        removeAssignedScriptsPage().clickClose();

        //Right click on the selected node and click Edit Content.
        //VERIFY: there is NO CHKP pub tag on text elements.
        //Close Editor.
        searchForNodeWithUuid(secondDescendantUuid);
        editDocumentAndVerifyChkpTagIsMissing(SECOND_DESCENDANT_NODE_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there is NO the CHKP scriptTag on table.
        assertThatScriptWithPubTagIsNotDisplayed(SECOND_DESCENDANT_NODE_MESSAGE);

        searchForNodeWithUuid(parentUuid);
        viewRemoveAssignedScripts();

        //Right click on the CHKP scriptTag and click on Remove Single.
        removeScriptWithDescendants();
    }

    /**
     * STORY/BUG - HALCYONST-12578<br>
     * SUMMARY - Assign with Descendants, partial descendants Checkpoint Product Tagged test<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignWithDescendantsBothAndSingleStatusesTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(3);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String parentUuid = datapodObject.getChapters().get(0).getNodeUUID();
        String firstDescendantUuid = datapodObject.getSections().get(0).getNodeUUID();
        String secondDescendantUuid = datapodObject.getSections().get(1).getNodeUUID();

        addCheckpointTagIfProductTagIsNotCheckpoint(firstDescendantUuid);
        removeCheckpointTagIfProductTagIsCheckpoint(secondDescendantUuid);
        addCheckpointTagIfProductTagIsNotCheckpoint(parentUuid);

        //Right click on the selected node and select Scripts -> Add Scripts.
        addToScript();

        //Right click on the CHKP pub tag and select Assign With Descendants.
        rightClickOnPubTagAndAssignWithDescendants();

        //VERIFY: Your Workflow Has Been Created page is opened. Workflow Details Page is open. Workflow is finished and close page.
        //Right click on the selected node and select Refresh Selection.
        verifyWorkflowIsFinishedAndRefreshSelection();

        //Right click on the selected node and click Edit Content.
        //VERIFY: there IS CHKP pub tag on text elements.
        //Close Editor.
        editDocumentAndVerifyChkpTagIsPresent(PARENT_NODE_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there IS the CHKP scriptTag on table and the Indicator for the scriptTag is ‘Both’.
        assertThatScriptWithPubTagIsDisplayedWithRightScriptTagIndicator(PARENT_NODE_MESSAGE, "Both");
        removeAssignedScriptsPage().clickClose();

        //Right click on the selected node and click Edit Content.
        //VERIFY: there IS CHKP pub tag on text elements.
        //Close Editor.
        searchForNodeWithUuid(firstDescendantUuid);
        editDocumentAndVerifyChkpTagIsPresent(FIRST_DESCENDANT_NODE_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there IS the CHKP scriptTag on table and the Indicator for the scriptTag is ‘Single’.
        assertThatScriptWithPubTagIsDisplayedWithRightScriptTagIndicator(FIRST_DESCENDANT_NODE_MESSAGE, SINGLE_INDICATOR);
        removeAssignedScriptsPage().clickClose();

        //Right click on the selected node and click Edit Content.
        //VERIFY: there is NO CHKP pub tag on text elements.
        //Close Editor.
        searchForNodeWithUuid(secondDescendantUuid);
        editDocumentAndVerifyChkpTagIsMissing(SECOND_DESCENDANT_NODE_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there is NO the CHKP scriptTag on table.
        assertThatScriptWithPubTagIsNotDisplayed(SECOND_DESCENDANT_NODE_MESSAGE);

        searchForNodeWithUuid(parentUuid);
        viewRemoveAssignedScripts();

        //Right click on the CHKP scriptTag and click on Remove Single.
        removeScriptWithDescendants();
    }

    /**
     * STORY/BUG - HALCYONST-12578<br>
     * SUMMARY - Assign Single, with Checkpoint Product Tagged test<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignSingleWithCheckpointProductTagTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(1);
        HierarchyDatapodConfiguration.getConfig().setChapterCount(0);
        HierarchyDatapodConfiguration.getConfig().setSubtitleCount(0);
        HierarchyDatapodConfiguration.getConfig().setTopNodeTypeWithString("GRADE");
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String uuid = datapodObject.getAllNodes().get(0).getNodeUUID();

        addCheckpointTagIfProductTagIsNotCheckpoint(uuid);

        //Right click on the selected node and select Scripts -> Add Scripts.
        addToScript();

        //Right click on the CHKP pub tag and select Assign Single.
        rightClickOnPubTagAndAssignSingle();

        //VERIFY: Your Workflow Has Been Created page is opened. Workflow Details Page is open. Workflow is finished and close page.
        //Right click on the selected node and select Refresh Selection.
        verifyWorkflowIsFinishedAndRefreshSelection();

        //Right click on the selected node and click Edit Content.
        //VERIFY: there IS CHKP pub tag on text elements.
        //Close Editor.
        editDocumentAndVerifyChkpTagIsPresent(EMPTY_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there IS the CHKP scriptTag on table and the Indicator for the scriptTag is ‘Single’.
        assertThatScriptWithPubTagIsDisplayedWithRightScriptTagIndicator(EMPTY_MESSAGE, SINGLE_INDICATOR);

        //Right click on the CHKP scriptTag and click on Remove Single.
        removeScriptWithDescendants();
    }

    /**
     * STORY/BUG - HALCYONST-12578<br>
     * SUMMARY - Assign Single, without Checkpoint Product Tagged test<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignSingleWithoutCheckpointProductTagTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(1);
        HierarchyDatapodConfiguration.getConfig().setChapterCount(0);
        HierarchyDatapodConfiguration.getConfig().setSubtitleCount(0);
        HierarchyDatapodConfiguration.getConfig().setTopNodeTypeWithString("GRADE");
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String uuid = datapodObject.getAllNodes().get(0).getNodeUUID();

        removeCheckpointTagIfProductTagIsCheckpoint(uuid);

        //Right click on the selected node and select Scripts -> Add Scripts.
        addToScript();

        //Right click on the CHKP pub tag and select Assign Single.
        rightClickOnPubTagAndAssignSingle();

        //VERIFY: Your Workflow Has Been Created page is opened. Workflow Details Page is open. Workflow is finished and close page.
        //Right click on the selected node and select Refresh Selection.
        verifyWorkflowIsFinishedAndRefreshSelection();

        //Right click on the selected node and click Edit Content.
        //VERIFY: there is NO CHKP pub tag on text elements.
        //Close Editor.
        editDocumentAndVerifyChkpTagIsMissing(EMPTY_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there is NO the CHKP scriptTag on table.
        assertThatScriptWithPubTagIsNotDisplayed(EMPTY_MESSAGE);
    }

    /**
     * STORY/BUG - HALCYONST-13422<br>
     * SUMMARY -  CHKP Pub Tag script assignment should be added to prior version nodes with end date in prior year<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignWithDescendantsEndDateTest()
    {
        HierarchyDatapodConfiguration.getConfig().setNumberOfPastChapterVersions(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String parentUuid = datapodObject.getChapters().get(0).getNodeUUID();
        SectionNode nodeOne = datapodObject.getSections().get(0);
        SectionNode nodeTwo = datapodObject.getSections().get(1);
        String oldVersionUuid = nodeOne.isHistoricalVersion() ? nodeOne.getNodeUUID() : nodeTwo.getNodeUUID();
        String newVersionUuid = nodeOne.isHistoricalVersion() ? nodeTwo.getNodeUUID() : nodeOne.getNodeUUID();

        hierarchyTreePage().setDateOfTree(DATE);

        addCheckpointTagIfProductTagIsNotCheckpoint(newVersionUuid);
        addCheckpointTagIfProductTagIsNotCheckpoint(oldVersionUuid);
        removeCheckpointTagIfProductTagIsCheckpoint(parentUuid);

        //Right click on the selected node and select Scripts -> Add Scripts.
        addToScript();

        //Right click on the CHKP pub tag and select Assign With Descendants.
        rightClickOnPubTagAndAssignWithDescendants();

        //VERIFY: Your Workflow Has Been Created page is opened. Workflow Details Page is open. Workflow is finished and close page.
        //Right click on the selected node and select Refresh Selection.
        verifyWorkflowIsFinishedAndRefreshSelection();

        //Right click on the selected node and click Edit Content.
        //VERIFY: there is NO CHKP pub tag on text elements.
        //Close Editor.
        editDocumentAndVerifyChkpTagIsMissing(PARENT_NODE_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there IS the CHKP scriptTag on table and the Indicator for the scriptTag is ‘Descendants’.
        assertThatScriptWithPubTagIsDisplayedWithRightScriptTagIndicator(PARENT_NODE_MESSAGE, DESCENDANTS_INDICATOR);
        removeAssignedScriptsPage().clickClose();

        //Right click on the selected node and click Edit Content Dynamic Scrolling.
        //VERIFY: there IS CHKP pub tag on text elements.
        //Close Editor.
        searchForNodeWithUuid(newVersionUuid);
        editDocumentAndVerifyChkpTagIsPresent(NEW_VERSION_NODE_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there IS the CHKP scriptTag on table and the Indicator for the scriptTag is ‘Single’.
        assertThatScriptWithPubTagIsDisplayedWithRightScriptTagIndicator(NEW_VERSION_NODE_MESSAGE, SINGLE_INDICATOR);
        removeAssignedScriptsPage().clickClose();

        //Right click on the selected node and click Edit Content Dynamic Scrolling.
        //VERIFY: there is NO CHKP pub tag on text elements.
        //Close Editor.
        searchForNodeWithUuid(oldVersionUuid);
        editDocumentAndVerifyChkpTagIsMissing(OLD_VERSION_NODE_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there is NO the CHKP scriptTag on table.
        assertThatScriptWithPubTagIsNotDisplayed(OLD_VERSION_NODE_MESSAGE);
    }

    /**
     * STORY/BUG - HALCYONST-13422<br>
     * SUMMARY -  CHKP Pub Tag script assignment should be added to prior version nodes with end date in prior year<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignHighWithDescendantsEndDateTest()
    {
        HierarchyDatapodConfiguration.getConfig().setNumberOfPastChapterVersions(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String parentUuid = datapodObject.getChapters().get(0).getNodeUUID();
        SectionNode nodeOne = datapodObject.getSections().get(0);
        SectionNode nodeTwo = datapodObject.getSections().get(1);
        String oldVersionUuid = nodeOne.isHistoricalVersion() ? nodeOne.getNodeUUID() : nodeTwo.getNodeUUID();
        String newVersionUuid = nodeOne.isHistoricalVersion() ? nodeTwo.getNodeUUID() : nodeOne.getNodeUUID();

        hierarchyTreePage().setDateOfTree(DATE);

        addCheckpointTagIfProductTagIsNotCheckpoint(newVersionUuid);
        addCheckpointTagIfProductTagIsNotCheckpoint(oldVersionUuid);
        removeCheckpointTagIfProductTagIsCheckpoint(parentUuid);

        //Right click on the selected node and select Scripts -> Add Scripts.
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsAddScriptsHighLevel();

        //Right click on the CHKP pub tag and select Assign With Descendants.
        addHighAssignedScriptsPage().rightClickScriptWithGivenPubTagValue(SCRIPT_TAG);
        addHightAssignedScriptsContextMenu().assignHighWithDescendants();

        //VERIFY: Your Workflow Has Been Created page is opened. Workflow Details Page is open. Workflow is finished and close page.
        //Right click on the selected node and select Refresh Selection.
        verifyWorkflowIsFinishedAndRefreshSelection();

        //Right click on the selected node and click Edit Content.
        //VERIFY: there is NO CHKP pub tag on text elements.
        //Close Editor.
        editDocumentAndVerifyChkpTagIsMissing(PARENT_NODE_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there IS the CHKP scriptTag on table and the Indicator for the scriptTag is ‘Descendants’.
        assertThatScriptWithPubTagIsDisplayedWithRightScriptTagIndicator(PARENT_NODE_MESSAGE, DESCENDANTS_INDICATOR);
        removeAssignedScriptsPage().clickClose();

        //Right click on the selected node and click Edit Content.
        //VERIFY: there IS CHKP pub tag on text elements.
        //Close Editor.
        searchForNodeWithUuid(newVersionUuid);
        editDocumentAndVerifyChkpTagIsPresent(NEW_VERSION_NODE_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there IS the CHKP scriptTag on table and the Indicator for the scriptTag is ‘Single’.
        assertThatScriptWithPubTagIsDisplayedWithRightScriptTagIndicator(NEW_VERSION_NODE_MESSAGE, SINGLE_INDICATOR);
        removeAssignedScriptsPage().clickClose();

        //Right click on the selected node and click Edit Content.
        //VERIFY: there is NO CHKP pub tag on text elements.
        //Close Editor.
        searchForNodeWithUuid(oldVersionUuid);
        editDocumentAndVerifyChkpTagIsMissing(OLD_VERSION_NODE_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there IS NO the CHKP scriptTag on table.
        assertThatScriptWithPubTagIsNotDisplayed(OLD_VERSION_NODE_MESSAGE);

        searchForNodeWithUuid(parentUuid);
        viewRemoveAssignedScripts();

        //Right click on the CHKP scriptTag and click on Remove Single.
        removeScriptWithDescendants();
    }

    /**
     * STORY/BUG - HALCYONST-13422<br>
     * SUMMARY -  CHKP Pub Tag script assignment should be added to prior version nodes with end date in prior year<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignSingleForOldVersionTest()
    {
        HierarchyDatapodConfiguration.getConfig().setNumberOfPastChapterVersions(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        SectionNode nodeOne = datapodObject.getSections().get(0);
        SectionNode nodeTwo = datapodObject.getSections().get(1);
        String oldVersionUuid = nodeOne.isHistoricalVersion() ? nodeOne.getNodeUUID() : nodeTwo.getNodeUUID();

        hierarchyTreePage().setDateOfTree(DATE);

        addCheckpointTagIfProductTagIsNotCheckpoint(oldVersionUuid);

        //Right click on the selected node and select Scripts -> Add Scripts.
        hierarchySearchPage().searchNodeUuid(oldVersionUuid);
        addToScript();

        //Right click on the CHKP pub tag and select Assign Single.
        rightClickOnPubTagAndAssignSingle();

        //VERIFY: Your Workflow Has Been Created page is opened. Workflow Details Page is open. Workflow is finished and close page.
        //Right click on the selected node and select Refresh Selection.
        verifyWorkflowIsFinishedAndRefreshSelection();

        //Right click on the selected node and click Edit Content.
        //VERIFY: there IS CHKP pub tag on text elements.
        //Close Editor.
        editDocumentAndVerifyChkpTagIsPresent(EMPTY_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there IS the CHKP scriptTag on table and the Indicator for the scriptTag is ‘Single’.
        assertThatScriptWithPubTagIsDisplayedWithRightScriptTagIndicator(EMPTY_MESSAGE, SINGLE_INDICATOR);

        //Right click on the CHKP scriptTag and click on Remove Single.
        removeScriptWithDescendants();
    }

    /**
     * STORY/BUG - HALCYONST-13422<br>
     * SUMMARY -  CHKP Pub Tag script assignment should be added to prior version nodes with end date in prior year<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignHighSingleForOldVersionTest()
    {
        HierarchyDatapodConfiguration.getConfig().setNumberOfPastChapterVersions(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        SectionNode nodeOne = datapodObject.getSections().get(0);
        SectionNode nodeTwo = datapodObject.getSections().get(1);
        String oldVersionUuid = nodeOne.isHistoricalVersion() ? nodeOne.getNodeUUID() : nodeTwo.getNodeUUID();

        hierarchyTreePage().setDateOfTree(DATE);

        addCheckpointTagIfProductTagIsNotCheckpoint(oldVersionUuid);

        //Right click on the selected node and select Scripts -> Add Scripts.
        hierarchySearchPage().searchNodeUuid(oldVersionUuid);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsAddScriptsHighLevel();

        //Right click on the CHKP pub tag and select Assign High Single.
        addHighAssignedScriptsPage().rightClickScriptWithGivenPubTagValue(SCRIPT_TAG);
        addHightAssignedScriptsContextMenu().assignHighSingle();

        //VERIFY: Your Workflow Has Been Created page is opened. Workflow Details Page is open. Workflow is finished and close page.
        //Right click on the selected node and select Refresh Selection.
        verifyWorkflowIsFinishedAndRefreshSelection();

        //Right click on the selected node and click Edit Content.
        //VERIFY: there IS CHKP pub tag on text elements.
        //Close Editor.
        editDocumentAndVerifyChkpTagIsPresent(EMPTY_MESSAGE);

        //Right click on the selected node and click on View/Remove Assigned Scripts.
        //VERIFY: there IS the CHKP scriptTag on table and the Indicator for the scriptTag is ‘Single’.
        assertThatScriptWithPubTagIsDisplayedWithRightScriptTagIndicator(EMPTY_MESSAGE, SINGLE_INDICATOR);
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }

    // ---------- Assertions ----------

    private void editDocumentAndVerifyChkpTagIsMissing(String nodeMessage)
    {
        openDocumentInEditor();
        assertThat(editorTextPage().getPubtagsList())
                .as("There IS CHKP pub tag on text elements on %s", nodeMessage)
                .doesNotContain(SCRIPT_TAG);
        closeDocumentAndEditorWindow();
    }

    private void editDocumentAndVerifyChkpTagIsPresent(String nodeMessage)
    {
        openDocumentInEditor();
        editorPage().breakOutOfFrame();
        editorToolbarPage().clickPubtagRefreshButton();
        editorPage().switchToEditor();
        editorPage().switchToEditorTextFrame();
        assertThat(editorTextPage().getPubtagsList())
                .as("There is NO CHKP pub tag on text elements on %s", nodeMessage)
                .contains(SCRIPT_TAG);
        closeDocumentAndEditorWindowWithChanges();
    }

    private void assertThatScriptWithPubTagIsNotDisplayed(String nodeMessage)
    {
        viewRemoveAssignedScripts();
        assertThat(removeAssignedScriptsPage().isScriptWithGivenPubTagDisplayed(SCRIPT_TAG))
                .as("The CHKP scriptTag is on the table on %s", nodeMessage)
                .isFalse();
        removeAssignedScriptsPage().clickClose();
    }

    private void assertThatScriptWithPubTagIsDisplayedWithRightScriptTagIndicator(String nodeMessage,
                                                                                  String indicator)
    {
        viewRemoveAssignedScripts();
        assertThat(removeAssignedScriptsPage().isScriptWithGivenPubTagDisplayed(SCRIPT_TAG))
                .as("The CHKP scriptTag is NOT on the table on %s", nodeMessage)
                .isTrue();
        assertThat(removeAssignedScriptsPage().getIndicatorByValue(SCRIPT_TAG))
                .as("Wrong indicator %s", nodeMessage)
                .isEqualTo(indicator);
    }

    private void assertThatCheckpointTagIsAssignedOnSelectedNode()
    {
        assertThat(siblingMetadataPage().getSelectedNodeProductTag())
                .as("The CHKPNT product tag is NOT assigned on the selected node").isEqualTo(CHECKPOINT_TAG);
    }

    // ---------- Assistive methods ----------

    private void searchForNodeWithUuid(String nodeUuid)
    {
        hierarchyNavigatePage().switchToHierarchyEditPage();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
    }

    private void openDocumentInEditor()
    {
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchToEditor();
        editorPage().switchToEditorTextFrame();
    }

    private void closeDocumentAndEditorWindow()
    {
        editorPage().closeDocumentWithNoChanges();
        editorPage().switchToEditor();
        editorPage().closeCurrentWindowIgnoreDialogue();
    }

    private void closeDocumentAndEditorWindowWithChanges()
    {
        editorPage().closeAndCheckInChanges();
        AutoITUtils.verifyAlertTextAndAccept(true, "Document validation failed, continue with check-in?");
        editorPage().switchToEditor();
        editorPage().closeCurrentWindowIgnoreDialogue();
    }

    private void removeScriptWithDescendants()
    {
        removeAssignedScriptsPage().rightClickScriptWithGivenPubTagValue(SCRIPT_TAG);
        removeAssignedScriptsContextMenu().removeWithDescendants();
    }

    private void viewRemoveAssignedScripts()
    {
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsViewRemoveAssignedScripts();
    }

    private void verifyWorkflowIsFinishedAndRefreshSelection()
    {
        yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();
        String workFlowIdAfterAssignmentTag = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().clickClose();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workFlowIdAfterAssignmentTag);
        workflowSearchPage().clickFilterButton();
        assertThat(workflowSearchPage().waitForFirstWorkflowAppearedAndFinishedTenMinutes())
                .as("Workflow does NOT finish").isTrue();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        refreshSelection();
    }

    private void rightClickOnPubTagAndAssignSingle()
    {
        addAssignedScriptsPage().rightClickScriptWithGivenPubTagValue(SCRIPT_TAG);
        addAssignedScriptsContextMenu().assignSingle();
    }

    private void rightClickOnPubTagAndAssignWithDescendants()
    {
        addAssignedScriptsPage().rightClickScriptWithGivenPubTagValue(SCRIPT_TAG);
        addAssignedScriptsContextMenu().assignWithDescendants();
    }

    private void addToScript()
    {
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsAddToScript();
    }

    private void addCheckpointTagIfProductTagIsNotCheckpoint(String uuid)
    {
        hierarchySearchPage().searchNodeUuid(uuid);
        if (!siblingMetadataPage().getSelectedNodeProductTag().equals(CHECKPOINT_TAG))
        {
            goToManageOnlineProductAssignments();
            //Select Assignment Status – Add Tag.
            manageOnlineProductAssignmentsPage().selectAddTag();
            selectOnlineProductAssignmentsOptionsAndRefreshSelection();
            //VERIFY: the CHKPNT product Tag is assigned on the selected node.
            assertThatCheckpointTagIsAssignedOnSelectedNode();
        }
    }

    private void removeCheckpointTagIfProductTagIsCheckpoint(String uuid)
    {
        hierarchySearchPage().searchNodeUuid(uuid);
        if (siblingMetadataPage().getSelectedNodeProductTag().equals(CHECKPOINT_TAG))
        {
            goToManageOnlineProductAssignments();
            //Select Assignment Status – Remove Tag.
            manageOnlineProductAssignmentsPage().selectRemoveTag();
            selectOnlineProductAssignmentsOptionsAndRefreshSelection();
            //VERIFY: the CHKPNT product Tag is assigned on the selected node.
            assertThatCheckpointTagIsAssignedOnSelectedNode();
        }
    }

    private void goToManageOnlineProductAssignments()
    {
        //Right click on the selected node and select Online Product Assignments.
        //VERIFY: Online Product Assignments page is open.
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        assertThat(siblingMetadataContextMenu().onlineProductAssignments())
                .as("Online Product Assignments Window DOESN't appear").isTrue();

        //Right click on the CHKPNT tag and select ‘Manage Online Product Assignments’.
        //VERIFY: Manage Online Product Assignments page.
        onlineProductAssignmentsPage().rightClickProductTag(CHECKPOINT_TAG);
        assertThat(onlineProductAssignmentsContextMenu().manageOnlineProductAssignment())
                .as("Manage Online Product Assignments Window DOESN't appear").isTrue();
    }

    private void selectOnlineProductAssignmentsOptionsAndRefreshSelection()
    {
        //Select Hierarchy Scope – Single Node Only.
        //Select Version Scope – All Version.
        //Click Submit.
        //Click Cancel on the Online Product Assignments page.
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        onlineProductAssignmentsPage().clickCancel();

        //Right click on the selected node and select Refresh Selection.
        refreshSelection();
    }

    private void refreshSelection()
    {
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelectedNode();
    }
}
