package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.publishing;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.editor.EditorTextContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.EditorTextPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.editor.popups.AddPubtagPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class BovAndEovEditsInPublishingEnabledContentSetTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();
    String contentSetCodeOfFedRegs = ContentSets.CODE_OF_FED_REGS_DEVELOPMENT.getCode();
    String contentSetCodeOfFedRegsText = ContentSets.CODE_OF_FED_REGS_DEVELOPMENT.getName();

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - HALCYONST-7433, HALCYONST-7982 <br>
     * SUMMARY - This test verifies that after editing BOV or EOV content and checking it in, a workflow should only be kicked off if the content set has Publishing enabled
     * since publishing should be enabled for this test we are expecting it to kick off a workflow <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void bovEditsInPublishingEnabledContentSetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid1 = datapodObject.getAllNodes().get(0).getNodeUUID();

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        boolean contentSetIsEnabledForPublishing = PublishingDatabaseUtils.isContentSetEnabledForPublishing(contentSetIowa, uatConnection);
        Assertions.assertTrue(contentSetIsEnabledForPublishing, "The current content set is not enabled for publishing when it should be");

        String idSetFromBOV = HierarchyDatabaseUtils.getNodeUuidFromTocNodeTableWithNullValue(uatConnection, 101, contentSetIowa,   5);

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid1);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().viewVolumeInfo();

        //Fake a change in the BOV content, can't use common methods to fake change because BOV content is different
        volumeInformationPage().clickEditContentDynamicScrollingUnderBov();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertAndRemoveSpaceInVolumeNumberText();
        editorPage().closeAndCheckInChangesAndHandleSpellcheckWindowIfAppeared();
        editorPage().waitForEditorToClose();

        volumeInformationPage().switchToVolumeInformationPage();
        volumeInformationPage().clickCloseButton();

        //As long as there are no major errors with this workflow, and the idSetIsCorrect returns as it should we can ignore the status of the workflow
        boolean workflowSearchPageAppears = toolsMenu().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowSearchPageAppears, "The Workflow Search page does not appear when it should");

        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("WestlawNovusUpload", "SOS.IAT", "",  user().getUsername());

        //Check finished workflow has correct idSet
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().openWorkflow(workflowId);
        workflowDetailsPage().expandWorkflowProperties();
        String idSet = workflowPropertiesPage().getWorkflowPropertyValueByName("idSet");
        boolean idSetIsCorrect = idSet.contains(idSetFromBOV);

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(idSetIsCorrect,"The id set of the workflow does not contain the given node uuid when it should"),
                () -> Assertions.assertTrue(workflowFinished,"The workflow did not finish: " + workflowId + ". As long as there are no major errors with this workflow, and the idSetIsCorrect returns as it should we can ignore the status of the workflow")
        );
    }

    /**
     * STORY/BUG - HALCYONST-7433, HALCYONST-7982 <br>
     * SUMMARY - This test verifies that after editing BOV or EOV content and checking it in, a workflow should only be kicked off if the content set has Publishing enabled
     * since publishing should be enabled for this test we are expecting it to kick off a workflow <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void eovEditsInPublishingEnabledContentSetTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid1 = datapodObject.getAllNodes().get(0).getNodeUUID();
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        boolean contentSetIsEnabledForPublishing = PublishingDatabaseUtils.isContentSetEnabledForPublishing(contentSetIowa, uatConnection);
        Assertions.assertTrue(contentSetIsEnabledForPublishing, "The current content set is not enabled for publishing when it should be");

        String idSetFromBOV = HierarchyDatabaseUtils.getNodeUuidFromTocNodeTableWithNullValue(uatConnection, 106, contentSetIowa,   5);

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid1);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().viewVolumeInfo();

        volumeInformationPage().clickEditContentDynamicScrollingUnderEov();
        editorPage().switchDirectlyToTextFrame();

        //we need to make sure there is a WL pubtag on the eov Edit page (without this the workflow throws an error)
        boolean checkForWlPubtag = editorTextPage().getPubtagsList().contains("WL+");
        if(!checkForWlPubtag)
        {
            editorTextPage().rightClick(EditorTextPageElements.pubtagsInIndexReferenceLineBlock);
            editorTextPage().breakOutOfFrame();
            editorTextContextMenu().openContextMenu(EditorTextContextMenuElements.pubTagPlus);
            boolean addPubtagWindowAppeared = addPubtagPage().switchToWindow(AddPubtagPageElements.PAGE_TITLE);
            Assertions.assertTrue(addPubtagWindowAppeared, "Add Pubtag window should appear");

            addPubtagPage().selectAvailablePubtags("WL");
            editorPage().switchToEditor();
            editorPage().switchDirectlyToTextFrame();
        }

        //fake a change and check in chnages
        editorTextPage().insertAndRemoveSpaceInVolumeNumberText();
        editorPage().closeAndCheckInChanges();
        editorPage().closeSpellcheckWindow();
        editorPage().waitForEditorToClose();

        volumeInformationPage().switchToVolumeInformationPage();
        volumeInformationPage().clickCloseButton();

        //As long as there are no major errors with this workflow, and the idSetIsCorrect returns as it should we can ignore the status of the workflow
        boolean workflowSearchPageAppears = toolsMenu().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowSearchPageAppears, "The Workflow Search page does not appear when it should");

        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus("WestlawNovusUpload", "SOS.IAT", "",  user().getUsername());

        //Check finished workflow has correct idSet
        String workflowId = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        workflowSearchPage().openWorkflow(workflowId);
        workflowDetailsPage().expandWorkflowProperties();
        String idSet = workflowPropertiesPage().getWorkflowPropertyValueByName("idSet");
        boolean idSetIsCorrect = idSet.contains(idSetFromBOV);

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(idSetIsCorrect,"The id set of the workflow does not contain the given node uuid when it should"),
                () -> Assertions.assertTrue(workflowFinished,"The workflow did not finish: " + workflowId + ". As long as there are no major errors with this workflow, and the idSetIsCorrect returns as it should we can ignore the status of the workflow")
        );
    }

    /**
     * STORY/BUG - HALCYONST-7433, HALCYONST-7982 <br>
     * SUMMARY - This test verifies that after editing BOV or EOV content and checking it in, a workflow should only be kicked off if the content set has Publishing enabled
     * since publishing should be disabled for this test we are expecting it to NOT kick off a workflow <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void bovEditsInPublishingDisabledContentSetTest()
    {
        //Code of Fed Regs content set info
        String nodeUuid = "I4C1BF8E07C8011D9BF2BB0A94FBB0D8D";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        boolean cfrContentSetIsDisabledForPublishing = PublishingDatabaseUtils.isContentSetEnabledForPublishing(contentSetCodeOfFedRegs, uatConnection);
        Assertions.assertFalse(cfrContentSetIsDisabledForPublishing, "The current content set is enabled for publishing when it should not be");

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().setMyContentSet(contentSetCodeOfFedRegsText);

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().viewVolumeInfo();

        //Fake a change in BOV content
        volumeInformationPage().clickEditContentDynamicScrollingUnderBov();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertAndRemoveSpaceInVolumeNumberText();
        editorPage().closeAndCheckInChanges();
        editorPage().closeSpellcheckWindow();
        editorPage().waitForEditorToClose();

        volumeInformationPage().switchToVolumeInformationPage();
        volumeInformationPage().clickCloseButton();

        boolean workflowSearchPageAppears = toolsMenu().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowSearchPageAppears,"The Workflow Search page does not appear when it should");

        //Check no workflow appears for the faked change in this content set
        boolean noWorkflowAStartedForContentSet = workflowSearchPage().filterWorkflowAndCheckGridStatus("WestlawNovusUpload", "SOS.CFT", "",  user().getUsername());

        Assertions.assertTrue(noWorkflowAStartedForContentSet,"A workflow was not started on content set after faking a change");
    }

    /**
     * STORY/BUG - HALCYONST-7433, HALCYONST-7982 <br>
     * SUMMARY - This test verifies that after editing BOV or EOV content and checking it in, a workflow should only be kicked off if the content set has Publishing enabled
     * since publishing should be disabled for this test we are expecting it to NOT kick off a workflow <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void eovEditsInPublishingDisabledContentSetTest()
    {
        //Code of Fed Regs content set info
        String nodeUuid = "I4C1BF8E07C8011D9BF2BB0A94FBB0D8D";

        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        boolean cfrContentSetIsDisabledForPublishing = PublishingDatabaseUtils.isContentSetEnabledForPublishing(contentSetCodeOfFedRegs, uatConnection);
        Assertions.assertFalse(cfrContentSetIsDisabledForPublishing, "The current content set is enabled for publishing when it should not be");

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().setMyContentSet(contentSetCodeOfFedRegsText);

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().viewVolumeInfo();

        //Fake a change in BOV content
        volumeInformationPage().clickEditContentDynamicScrollingUnderEov();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertAndRemoveSpaceInVolumeNumberText();
        editorPage().closeAndCheckInChanges();
        editorPage().closeSpellcheckWindow();
        editorPage().waitForEditorToClose();

        volumeInformationPage().switchToVolumeInformationPage();
        volumeInformationPage().clickCloseButton();

        boolean workflowSearchPageAppears = toolsMenu().goToWorkflowReportingSystem();
        Assertions.assertTrue(workflowSearchPageAppears,"The Workflow Search page does not appear when it should");

        //Check no workflow appears for the faked change in this content set
        boolean noWorkflowAStartedForContentSet = workflowSearchPage().filterWorkflowAndCheckGridStatus("WestlawNovusUpload", "SOS.CFT", "",  user().getUsername());

        Assertions.assertTrue(noWorkflowAStartedForContentSet,"A workflow was not started on content set after faking a change");
    }

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
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
