package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups.AssignmentsPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;


public class MultipleTaxTypeAssignmentsTests extends TestService
{
    Connection connection;
    HierarchyDatapodObject datapodObject;
    /**
     * STORY/BUG - HALCYONST-10756: <br>
     * SUMMARY - Multiply Tax Type Assignments (Tools -> Online Product Maintenance and Hierarchy Navigate) <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void multipleTaxTypeAssignmentsTest()
    {
        connection = CommonDataMocking.connectToDatabase(getEnvironment());
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String uuid = datapodObject.getSections().get(0).getNodeUUID();
        List<String> taxTypes = Arrays.asList("TTRT1","TTRT2","TTRT3");
        String selectedTaxTypesExpected = "TTRT1TTRT2TTRT3";
        String selectedNodTaxTypesExpected = "TTRT1;TTRT2;TTRT3";

        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().taxTypeAssignments();
        taxTypeAssignmentsPage().selectMultipleTaxTypes(taxTypes);
        taxTypeAssignmentsPage().rightClick(AssignmentsPageElements.GRID);

        taxTypeAssignmentsContextMenu().manageTaxTypeAssignment();
        String selectedProductType = manageTaxTypeAssignmentsPage().getSelectedTaxTypes();

        manageTaxTypeAssignmentsPage().selectAddTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        manageTaxTypeAssignmentsPage().clickSubmit();

        taxTypeAssignmentsPage().clickCancel();

        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        String nodeTaxType = siblingMetadataPage().getSelectedNodeTaxType();

        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().taxTypeAssignments();
        taxTypeAssignmentsPage().selectMultipleTaxTypes(taxTypes);
        taxTypeAssignmentsPage().rightClick(AssignmentsPageElements.GRID);

        taxTypeAssignmentsContextMenu().manageTaxTypeAssignment();

        manageTaxTypeAssignmentsPage().selectRemoveTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        manageTaxTypeAssignmentsPage().clickSubmit();

        taxTypeAssignmentsPage().clickCancel();

        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        hierarchySearchPage().searchNodeUuid(uuid);
        String nodeTaxTypeAfterRemoving = siblingMetadataPage().getSelectedNodeTaxType();

        Assertions.assertAll("multipleTaxTypeAssignmentsTest",
            () -> Assertions.assertEquals(selectedTaxTypesExpected, selectedProductType,
                    "Selected Tax Type(s) list is WRONG"),
            () -> Assertions.assertEquals(selectedNodTaxTypesExpected, nodeTaxType,
                    "Tax Type column has WRONG value"),
            () -> Assertions.assertEquals("",nodeTaxTypeAfterRemoving,
                    "Tax Type column ISN'T empty"));
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
