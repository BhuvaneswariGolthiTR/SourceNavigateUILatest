package com.thomsonreuters.codes.codesbench.quality.tests.source.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class InsertIntoHierarchyWizardTests extends TestService
{
    /**
     * STORY/BUG - HALCYONST-10655 <br>
     * SUMMARY - This test verifies that Tax Types are not inherited by newly added nodes based on insert in Hierarchy Wizard <br>
     * USER - LEGAL <br>
     * CONTENT TYPE - LAW <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void InsertInHierarchyWizardTest()
    {
        String nodeUuidHierarchy = "IC8C37D800E1111DC890AFC36AB86B8A8";
        String nodeUuidSource = "ID06AC9E0220011E2A95CC5BEEE5B8325";
        //go to hierarchy
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(nodeUuidHierarchy);
        //assigned Tax Type for all siblings node
        siblingMetadataPage().selectAllNodes();
        siblingMetadataPage().rightClickSiblingMetadata();
        siblingMetadataContextMenu().taxTypeAssignments();
        taxTypeAssignmentsPage().rightClickProductTag("TTRT1");
        taxTypeAssignmentsContextMenu().manageTaxTypeAssignment();
        manageTaxTypeAssignmentsPage().selectAddTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        manageTaxTypeAssignmentsPage().clickSubmit();
        taxTypeAssignmentsPage().clickCancel();
        //go to Source Delta tab and Insert Into Hierarchy Wizard
        sourcePage().goToSourcePageWithRenditionUuids(nodeUuidSource);
        sourceNavigateGridPage().clickFirstItem();
        sourcePage().goToDeltaTab();
        sourcePage().publicViewAllCols();
        sourceNavigateGridPage().rightClickFirstItem();
        deltaContextMenu().goToModifyResetIntegrationStatus();
        sourceNavigateGridPage().rightClickFirstItem();
        deltaContextMenu().goToModifyCiteLocate();
        sourceNavigateGridPage().rightClickFirstItem();
        deltaContextMenu().goToModifyInsertIntoHierarchyWizard();
        sourceNavigateGridPage().enterTheInnerFrame();
        insertIntoHierarchyWizardPage().clickSelectAsTarget();
        insertIntoHierarchyWizardPage().clickAddBelowAsSibling();
        insertIntoHierarchyWizardPage().clickClose();
        sourceNavigateGridPage().acceptAlert();
        //go to hierarchy
        hierarchyMenu().goToNavigate();
        Assertions.assertEquals("", siblingMetadataPage().getSelectedNodeTaxType(), "The inserted node HAS some Tax Type assignment");
        siblingMetadataPage().rightClickMultipleSelectedSiblingMetadata();
        siblingMetadataContextMenu().deleteFunctionsDelete();
        deleteWithPromoteChildrenPage().pressQuickLoad();
        deleteWithPromoteChildrenPage().pressSubmit();
    }
}
