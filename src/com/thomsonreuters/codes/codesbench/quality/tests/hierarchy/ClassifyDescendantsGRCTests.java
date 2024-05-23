package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.ClassifyDescendantsWorkflowPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClassifyDescendantsGRCTests extends TestService
{

    HierarchyDatapodObject datapodObject;
    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This tests the functionality of Classify Descendants for RISK users. The 'value'
     * we are searching is not the lowest level of a tree, and has children so we expect the submit
     * button on the Classify Descendants window to be enabled <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void classifyDescendants_WithChildrenTest()
    {
        HierarchyDatapodConfiguration config = HierarchyDatapodConfiguration.getConfig();
        config.setSectionCount(3);
        config.setChapterCount(0);
        config.setSubtitleCount(0);
        config.setTopNodeTypeWithString("GRADE");
        config.setGradeCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getAllNodes().get(0).getNodeUUID();
        String contentSet = "Iowa (Development)";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSet);
        hierarchyMenu().goToNavigate();

        //Verify submit button is enabled and more than zero nodes affected
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().classifyDescendants();
        boolean submitButtonEnabled = classifyDescendantsWorkflowPage().isElementEnabled(ClassifyDescendantsWorkflowPageElements.submitButton);
        boolean nodesAffectedIsZero = classifyDescendantsWorkflowPage().totalNodesAffectedEqualToZero();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(submitButtonEnabled,"Submit button was not enabled when it should have been"),
                () -> Assertions.assertFalse(nodesAffectedIsZero,"Number of nodes affected equals zero when it should not be")
        );
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This tests the functionality of Classify Descendants for RISK users. The 'value'
     * we are searching is the lowest level of a tree, and has no children so we expect the submit
     * button on the Classify Descendants window to be disabled <br>
     * USER - RISK <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void classifyDescendants_NoChildrenTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String contentSet = "Iowa (Development)";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(contentSet);
        hierarchyMenu().goToNavigate();

        //Verify submit button is disabled and zero nodes are affected
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().classifyDescendants();
        boolean submitButtonEnabled = classifyDescendantsWorkflowPage().isElementEnabled(ClassifyDescendantsWorkflowPageElements.submitButton);
        boolean nodesAffectedEqualsZero = classifyDescendantsWorkflowPage().totalNodesAffectedEqualToZero();

        Assertions.assertAll
        (
                () -> Assertions.assertFalse(submitButtonEnabled,"Submit button was not enabled when it should have been"),
                () -> Assertions.assertTrue(nodesAffectedEqualsZero,"Number of nodes affected equals zero when it should not be")
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
