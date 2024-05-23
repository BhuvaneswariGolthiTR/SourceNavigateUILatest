package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;

public class ProductTagGRCTests extends TestService
{
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - JETS-15962 <br>
     * SUMMARY - This test verifies that the node's flag status stays as a green checkmark after adding a tag and then removing the tag <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void productTagBugGRCTest()
    {
        HierarchyDatapodConfiguration.getConfig().loadDefaultRiskConfig();
        datapodObject = TargetDataMockingNew.CrownDependencies.Small.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();
        String greenCheckmarkStatus = "check";
        String viewTag = "GG";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();

        //Check flag status is correct
        hierarchySearchPage().searchNodeUuid(uuid);
        String flagStatus = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean isFlagStatusCorrect = flagStatus.equals(greenCheckmarkStatus);
        Assertions.assertTrue(isFlagStatusCorrect,"The flag status should be a green checkmark");

        //Add tag
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().onlineProductAssignments();
        onlineProductAssignmentsPage().rightClickProductTag(viewTag);
        onlineProductAssignmentsContextMenu().manageOnlineProductAssignment();
        manageOnlineProductAssignmentsPage().selectAddTag();
        boolean workflowStarted = manageOnlineProductAssignmentsPage().clickSubmitAndVerifyWorkflowStarted();
        Assertions.assertTrue(workflowStarted,"The workflow started after clicking submit");

        //Verify workflow finish
        yourWorkflowHasBeenCreatedPage().switchToWorkflowConfirmationPage();//Stabilization
        String workflowId = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().closeWorkflowConfirmationPage();
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        boolean onlineProductAssignmentsPageClosed = onlineProductAssignmentsPage().clickCancel();
        Assertions.assertTrue(onlineProductAssignmentsPageClosed,"The Online Product Assignments page closed after clicking the Cancel button");
        hierarchyNavigatePage().switchToWindow(HierarchyPageElements.PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished,String.format("The workflow with id: %s did not finish",workflowId));

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check green flag
        hierarchySearchPage().searchNodeUuid(uuid);
        String flagStatus2 = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean isFlagStatusCorrect2 = flagStatus2.equals(greenCheckmarkStatus);

        //Remove tag
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().onlineProductAssignments();
        onlineProductAssignmentsPage().rightClickProductTag(viewTag);
        onlineProductAssignmentsContextMenu().manageOnlineProductAssignment();
        manageOnlineProductAssignmentsPage().selectRemoveTag();
        boolean workflowStarted2 = manageOnlineProductAssignmentsPage().clickSubmitAndVerifyWorkflowStarted();
        Assertions.assertTrue(workflowStarted2,"The workflow started after clicking submit");

        //Verify workflow finish
        yourWorkflowHasBeenCreatedPage().switchToWorkflowConfirmationPage();//Stabilization
        String workflowId2 = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().closeWorkflowConfirmationPage();
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        boolean onlineProductAssignmentsPageClosed2 = onlineProductAssignmentsPage().clickCancel();
        Assertions.assertTrue(onlineProductAssignmentsPageClosed2,"The Online Product Assignments page closed after clicking the Cancel button");

        hierarchyNavigatePage().switchToWindow(HierarchyPageElements.PAGE_TITLE);
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workflowId2);
        workflowSearchPage().clickFilterButton();
        boolean workflowFinished2 = workflowSearchPage().checkFirstWorkflowFinishedFiveMinutes();
        Assertions.assertTrue(workflowFinished2,String.format("The workflow with id: %s did not finish",workflowId2));

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        //Check green flag
        hierarchySearchPage().searchNodeUuid(uuid);
        hierarchyNavigatePage().switchToHierarchyEditPage();
        String flagStatus3 = siblingMetadataPage().getValidationFlagOfSelectedNode();
        boolean isFlagStatusCorrect3 = flagStatus3.equals(greenCheckmarkStatus);

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(isFlagStatusCorrect2,"The flag status should be a green checkmark after adding the tag"),
                () -> Assertions.assertTrue(isFlagStatusCorrect3,"The flag status should be a green checkmark after removing the tag")
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
