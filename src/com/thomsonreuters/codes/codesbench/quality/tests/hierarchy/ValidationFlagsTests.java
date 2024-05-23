package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.ValidationFlagsReportPopupPageElements;
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
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;

import java.sql.Connection;

public class ValidationFlagsTests extends TestService
{

    HierarchyDatapodObject datapodObject;
    Connection connection;

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that the expected window appears after going to
     * Validation Flags -> Check Node Validation Flags in the Sibling metadata context menu <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void validationFlagsCheckNodesLegalTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Right click and go to Validation Flags -> Check Node Validation Flags
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean validationsFlagReportWindowAppeared = siblingMetadataContextMenu().validationFlagsCheckNodeValidationFlags();

        Assertions.assertTrue(validationsFlagReportWindowAppeared,"The window with the header 'Validation Flag Report' did not appear when it should have");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that the expected window appears after going to
     * Validation Flags -> Open Validation Flags Report in the Sibling metadata context menu <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void validationFlagsInitiateAndOpenValidationWorkflowLegalTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Right click and go to Validation Flags -> Open Validation Flags Report
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().validationFlagsOpenValidationFlagsReport();
        boolean validationsFlagReportWindowAppeared = validationFlagsReportPopupPage().isElementDisplayed(ValidationFlagsReportPopupPageElements.VALIDATION_FLAGS_REPORT_POPUP_PAGE_HEADER);

        Assertions.assertTrue(validationsFlagReportWindowAppeared,"The window with the header 'Validation Flag Report' did not appear when it should have");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that editing the node and clearing the warning flag in the Validation Flags Report page <br>
     * will show the node without a warning flag in the sibling metadata pane and no longer appear in the Validation Flags Report page  <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void validationFlagsReverifyFlagsLegalTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String value = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, ContentSets.IOWA_DEVELOPMENT.getCode(), connection);
        String testText = "TEST";
        String warningStatus = "warn";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Change cornerpiece text and check in changes
        hierarchySearchPage().quickSearch("=",value);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().replaceCornerpieceText(testText);
        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();
        editorPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check that validation flag is a warning flag
        String validationValue = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean warningFlagAppears = validationValue.equals(warningStatus);
        Assertions.assertTrue(warningFlagAppears,"The validation flag of the node should be a warning flag");

        //Clear warning flag
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().validationFlagsCheckHierarchyValidationFlags();
        boolean nodeWithFlagAppears = validationFlagsReportPopupPage().isNodeWithGivenValueDisplayed(value);
        Assertions.assertTrue(nodeWithFlagAppears,"The node with the given value does not appear when it should");

        validationFlagsReportPopupPage().rightClickNodeWithGivenValue(value);
        validationFlagsReportPopupPage().clickClearWarningFlag();
        validationFlagsReportPopupPage().clickClose();

        //Check warning flag was cleared
        validationValue = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean warningFlagAppears2 = validationValue.equals(warningStatus);
        Assertions.assertFalse(warningFlagAppears2,"The validation flag of the node should not be a warning flag");

        //Check node does not appear in Validation Flags Report page
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().validationFlagsReverifyFlags();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        boolean validationFlagsPopupOpened = siblingMetadataContextMenu().validationFlagsCheckHierarchyValidationFlags();
        Assertions.assertTrue(validationFlagsPopupOpened, "Validation Flags Report opened");

        boolean nodeWithFlagAppears2 = validationFlagsReportPopupPage().isNodeWithGivenValueDisplayed(value);
        Assertions.assertFalse(nodeWithFlagAppears2,"The node with the given value should not be displayed");
    }

    @AfterEach
    public void cleanUp()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }

        BaseDatabaseUtils.disconnect(connection);
    }
}
