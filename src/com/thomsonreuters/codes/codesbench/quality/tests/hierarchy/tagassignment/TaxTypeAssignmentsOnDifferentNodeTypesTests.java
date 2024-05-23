package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate.SiblingMetadataContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class TaxTypeAssignmentsOnDifferentNodeTypesTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - HALCYONST-14057<br>
     * SUMMARY - Tax Type Assignment shouldn't be available for NOD type documents in Hierarchy test<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void taxTypeAssignmentsForNodTest()
    {
        HierarchyDatapodConfiguration.getConfig().setBluelineCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String uuid = datapodObject.getBluelines().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();

        Assertions.assertFalse(siblingMetadataPage().doesElementExist(SiblingMetadataContextMenuElements.TAX_TYPE_ASSIGNMENTS), " Tax Type Assignment IS available for such type ");
    }

    /**
     * STORY/BUG - HALCYONST-13419<br>
     * SUMMARY - Tax Type Tags should only be assigned to Section nodes test<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void taxTypeAssignmentsForGradeTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String chapterUUID = datapodObject.getChapters().get(0).getNodeUUID();
        String sectionUUID = datapodObject.getSections().get(0).getNodeUUID();
        String expectedHierarchyScope = "Assign Tax Types to descendant SECTION nodes only";
        String taxtype = "TTRT1";

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        hierarchySearchPage().searchNodeUuid(chapterUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        Assertions.assertTrue(siblingMetadataContextMenu().taxTypeAssignments(), "Tax Type Assignments Window DOESN't appear");

        taxTypeAssignmentsPage().rightClickProductTag(taxtype);
        Assertions.assertTrue(taxTypeAssignmentsContextMenu().manageTaxTypeAssignment(), "Manage Tax Type Assignments DOESN't appear");

        String actualHierarchyScope = manageTaxTypeAssignmentsPage().getSelectedHierarchyScope();
        manageTaxTypeAssignmentsPage().selectAddTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        manageTaxTypeAssignmentsPage().sendEnterToElement(CommonPageElements.SUBMIT_BUTTON);

        //VERIFY:  Your Workflow Has Been Created page is opened.
        yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();
        String workFlowIdAferAssignmentTag = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().clickClose();

        taxTypeAssignmentsPage().switchToTaxTypeAssignmentsPage();
        taxTypeAssignmentsPage().clickCancel();

        //VERIFY:  Workflow Details Page is open.
        //VERIFY:  workflow is finished and close page.
        hierarchyNavigatePage().switchToHierarchyEditPage();
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workFlowIdAferAssignmentTag);
        workflowSearchPage().clickFilterButton();
        Assertions.assertTrue(workflowSearchPage().waitForFirstWorkflowAppearedAndFinishedTenMinutes(), "Workflow does NOT finish");

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelectedNode();

        String gradeTaxType = siblingMetadataPage().getSelectedNodeTaxType();
        hierarchySearchPage().searchNodeUuid(sectionUUID);
        String descendantTaxType = siblingMetadataPage().getSelectedNodeTaxType();
        hierarchySearchPage().searchNodeUuid(chapterUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        Assertions.assertTrue(siblingMetadataContextMenu().taxTypeAssignments(), "Tax Type Assignments Window DOESN't appear");

        taxTypeAssignmentsPage().rightClickProductTag(taxtype);
        Assertions.assertTrue(taxTypeAssignmentsContextMenu().manageTaxTypeAssignment(), "Manage Tax Type Assignments DOESN't appear");

        manageTaxTypeAssignmentsPage().getSelectedHierarchyScope();
        manageTaxTypeAssignmentsPage().selectRemoveTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        manageTaxTypeAssignmentsPage().sendEnterToElement(CommonPageElements.SUBMIT_BUTTON);

        //VERIFY:  Your Workflow Has Been Created page is opened.
        yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();
        yourWorkflowHasBeenCreatedPage().getWorkflowId();
        yourWorkflowHasBeenCreatedPage().clickClose();
        taxTypeAssignmentsPage().switchToTaxTypeAssignmentsPage();
        taxTypeAssignmentsPage().clickCancel();

        Assertions.assertAll
        (
                () -> Assertions.assertEquals(expectedHierarchyScope, actualHierarchyScope),
                () -> Assertions.assertEquals("", gradeTaxType, "Tax type WAS assigned for Grade Node"),
                () -> Assertions.assertEquals(taxtype, descendantTaxType, "Tax Type was NOT assigned for Section Node")
        );
    }

    /**
     * STORY/BUG - HALCYONST-13419<br>
     * SUMMARY - Tax Type Tags should only be assigned to Section nodes test<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void taxTypeAssignmentsForGSectionTest()
    {
        String expectedHierarchyScope = "Assign Tax Types to SECTION nodes";
        String taxtype = "TTRT1";

        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String sectionUuid = datapodObject.getSections().get(0).getNodeUUID();

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(sectionUuid);

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        Assertions.assertTrue(siblingMetadataContextMenu().taxTypeAssignments(), "Tax Type Assignments Window DOESN't appear");

        taxTypeAssignmentsPage().rightClickProductTag(taxtype);
        Assertions.assertTrue(taxTypeAssignmentsContextMenu().manageTaxTypeAssignment(), "Manage Tax Type Assignments DOESN't appear");

        String actualHierarchyScope = manageTaxTypeAssignmentsPage().getSelectedHierarchyScope();
        manageTaxTypeAssignmentsPage().selectAddTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        manageTaxTypeAssignmentsPage().clickSubmit();
        taxTypeAssignmentsPage().switchToTaxTypeAssignmentsPage();
        taxTypeAssignmentsPage().clickCancel();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelectedNode();
        String gradeTaxType = siblingMetadataPage().getSelectedNodeTaxType();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        Assertions.assertTrue(siblingMetadataContextMenu().taxTypeAssignments(), "Tax Type Assignments Window DOESN't appear");

        taxTypeAssignmentsPage().rightClickProductTag(taxtype);
        Assertions.assertTrue(taxTypeAssignmentsContextMenu().manageTaxTypeAssignment(), "Manage Tax Type Assignments DOESN't appear");

        manageTaxTypeAssignmentsPage().getSelectedHierarchyScope();
        manageTaxTypeAssignmentsPage().selectRemoveTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        manageTaxTypeAssignmentsPage().clickSubmit();

        taxTypeAssignmentsPage().switchToTaxTypeAssignmentsPage();
        taxTypeAssignmentsPage().clickCancel();

        Assertions.assertAll
        (
              () -> Assertions.assertEquals(expectedHierarchyScope, actualHierarchyScope),
              () -> Assertions.assertEquals(taxtype, gradeTaxType, "Tax type WAS assigned for Section Node")
        );
    }

    @AfterEach
    public void deleteData()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
