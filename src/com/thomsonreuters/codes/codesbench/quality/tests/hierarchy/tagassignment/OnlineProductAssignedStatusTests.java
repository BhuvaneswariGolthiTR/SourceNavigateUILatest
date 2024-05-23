package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;

public class OnlineProductAssignedStatusTests extends TestService
{
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - HALCYONST-11949: <br>
     * SUMMARY - Tax Type Assignments (Tools -> Hierarchy Navigate) Some Targged<br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void taxTypeSomeAssignedStatusTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(2);
        datapodObject= TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        final String SOME_TAGGED_STATUS = "Some Tagged";
        String onlineProduct = "CHKPNT";

        //go to Hierarchy Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        //Assign CHECKPOINT on node
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        Assertions.assertTrue(siblingMetadataContextMenu().onlineProductAssignments(),
                "Online Product Assignments Window DOESN't appear");
        onlineProductAssignmentsPage().rightClickProductTag(onlineProduct);
        Assertions.assertTrue(onlineProductAssignmentsContextMenu().manageOnlineProductAssignment(),
                "Manage Online Product Assignments DOESN't appear");
        manageOnlineProductAssignmentsPage().selectAddTag();
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        Assertions.assertTrue(manageOnlineProductAssignmentsPage().doesManageOnlineProductAssignmentsPageClose(),
                "Manage Online Product Assignments Page DOESN't close");
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        Assertions.assertTrue(onlineProductAssignmentsPage().clickCancel(),
                "Online Product Assignments window DOESN'T close");
        //Select first two nodes
        siblingMetadataPage().selectFirstFewNodes(2);
        //Get Online Product status
        siblingMetadataPage().rightClickSiblingMetadata();
        Assertions.assertTrue(siblingMetadataContextMenu().onlineProductAssignments(),
                "Online Product Assignments Window DOESN't appear");
        String selectedProductAssignmentStatus = onlineProductAssignmentsPage().getAssignmentStatusByViewTag(onlineProduct);
        onlineProductAssignmentsPage().clickCancel();
        Assertions.assertEquals(SOME_TAGGED_STATUS, selectedProductAssignmentStatus,
                String.format("Selected Product Assignment Status is '%s' instead of '%s'", selectedProductAssignmentStatus, SOME_TAGGED_STATUS));
    }

    @AfterEach
    public void cleanup()
    {
        if(datapodObject!=null)
        {
            datapodObject.delete();
        }
        BaseDatabaseUtils.disconnect();
    }

}
