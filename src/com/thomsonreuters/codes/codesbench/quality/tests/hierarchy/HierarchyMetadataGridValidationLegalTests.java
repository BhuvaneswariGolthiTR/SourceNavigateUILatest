package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HierarchyMetadataGridValidationLegalTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - Bug-229038 <br>
     * SUMMARY - This test verifies that adding text to a text paragraph sets the node's flag status to a warning. <br>
     * Then the test checks that the warning flag is changed back to a green checkmark after removing the added text. <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void validationUpdatesOnPageRefreshLegalTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Medium.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Add text to the text paragraph and check in changes
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorToolbarPage().clickSubsectionGeneration();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertTextIntoFirstTextParagraphWithoutSpaces("1");
        editorPage().closeAndCheckInChanges();
        boolean alertAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Subsection validation failed, continue with check-in?");
        Assertions.assertTrue(alertAppeared,"The expected alert didn't appear or wasn't accepted");

        //Check that flag status of the node has changed to a warning symbol
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean warningFlagAppeared = siblingMetadataPage().doesSelectedNodeHaveWarningValidationFlag();

        //Remove the previously added text from the text paragraph and check in changes
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().deleteFirstCharacterOfFirstTextParagraph();
        editorPage().closeAndCheckInChanges();

        //Check that the flag status of the node is back to a green checkmark
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean greenCheckmarkAppeared = siblingMetadataPage().doesSelectedNodeHaveGreenCheckValidationFlag();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(warningFlagAppeared,"The flag status of the node did not change to a warning as expected"),
                        () -> Assertions.assertTrue(greenCheckmarkAppeared,"The flag status of the node did not change to a green checkmark as expected")
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