package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodContentType;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

public class HierarchyMetadataGridValidationGRCTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - Bug-229038 <br>
     * SUMMARY - This test verifies that changing the text of a cite reference sets the node's flag status to a warning. <br>
     * Then the test checks that the warning flag is changed back to a green checkmark after removing the added text. <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void validationUpdatesOnPageRefreshGRCTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        HierarchyDatapodConfiguration.getConfig().setContentType(HierarchyDatapodContentType.RISK_SUBSECTIONS_REG_GUIDANCE_US);
        datapodObject = TargetDataMockingNew.RegGuidanceSummaryUS.Small.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();
        String citeRefText = "16 CFR s 310.1";
        String testPhrase = "TEST";
        String citeRefTextWithPhrase = "16 CFR "+ testPhrase +"s 310.1";
        String contentSet = "Reg Guidance Summary US";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSet);
        hierarchyMenu().goToNavigate();

        //Add text to the cite reference and check in changes
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorToolbarPage().clickSubsectionGeneration();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().addTextToGivenCiteReference(citeRefText,testPhrase);
        editorPage().closeAndCheckInChanges();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        boolean alertAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "Subsection validation failed, continue with check-in?");
        Assertions.assertTrue(alertAppeared,"The expected alert didn't appear or wasn't accepted");

        //Check that flag status of the node has changed to a warning symbol
        editorPage().waitForEditorToClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        boolean warningFlagAppeared = siblingMetadataPage().doesSelectedNodeHaveWarningValidationFlag();

        //Remove the previously added text from the cite reference and check in changes
        siblingMetadataPage().selectedSiblingMetadataEditContent();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().removeTextFromGivenCiteReference(citeRefTextWithPhrase,testPhrase);
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
