package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ProductTagDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseConstants;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ScriptMaintenance.ScriptMaintenanceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.queries.TargetNodeMockingQueries;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ProductType;

import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.USCA_DEVELOPMENT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ProductType.ALL_PRODUCTS;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ProductType.TAX_TYPE;

public class TaxTypeAssignmentsTests extends TestService
{
    Connection connection;
    HierarchyDatapodObject datapodObject;

    private static  String UUID ;
    private static final String TAGGED_STATUS = "Tagged";
    private static final String ASSIGNED_STATUS = "Assigned";

    private Integer productId = 0;
    private String longName = "";
    private String shortName = "VRB.";
    private String productTypeName = "";
    private String pubTag = "";
    private final ProductType productType = TAX_TYPE;

    /**
     * STORY/BUG - HALCYONST-9557/HALCYONST-9466: <br>
     * SUMMARY - Tax Type Assignments (Tools -> Online Product Maintenance and Hierarchy Navigate) <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void taxTypeAssignmentsTest()
    {
        connection = CommonDataMocking.connectToDatabase(getEnvironment());
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        UUID = datapodObject.getSections().get(0).getNodeUUID();

        String viewTag = productType.getTagView();
        String productName = String.format("%s Test %s", productType.getType(), DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss());
        String shortProductName = viewTag;
        List<String> setList = Arrays.asList(IOWA_DEVELOPMENT.getName(), USCA_DEVELOPMENT.getName());
        String uuid =  datapodObject.getSections().get(0).getNodeUUID();
        String documentDetails = HierarchyDatabaseUtils.getNodeValueByNodeUUID(datapodObject.getSections().get(0).getNodeUUID(), IOWA_DEVELOPMENT.getCode(), connection);
        productTypeName = productType.getType();
        pubTag = productType.getTagView();

        //STEP 01: sign in as the legal user
        homePage().goToHomePage();
        loginPage().logIn();
        //STEP 02: go to Tools -> Online Product Maintenance
        Assertions.assertTrue(toolsMenu().goToOnlineProductMaintenance(),
                "STEP 02: Online Product Maintenance Window DOESN't appear");
        //STEP 03: create a Product Tag, and assign it to Iowa (Development) and USCA(Development)
        onlineProductMaintenanceContextMenu().createNew();
        createNewOnlineProductPage().selectProductType(productType);
        createNewOnlineProductPage().setViewTag(viewTag);
        createNewOnlineProductPage().setProductName(productName);
        createNewOnlineProductPage().setProductShortName(shortProductName);
        createNewOnlineProductPage().addAssignments(setList);
        boolean clickSave = createNewOnlineProductPage().clickSave();
        Assertions.assertTrue(clickSave, "Create Online product page window is closed. ");
        longName = productName;
        shortName = "VRB." + shortProductName;
        //STEP 04: right click the product tag and select View Assignment Status
        onlineProductMaintenancePage().selectProduct(productType);
        onlineProductMaintenancePage().clickRefresh();
        productId = onlineProductMaintenanceGridPage().getProductIdByViewTagAsInt(viewTag);
        onlineProductMaintenanceGridPage().rightClickProductTag(viewTag);
        Assertions.assertTrue(onlineProductMaintenanceContextMenu().viewAssignmentStatus(),
                "STEP 04: View Assignment Status Page DOESN't appear");
        boolean taxTypeAssignedToContentSets = viewAssignmentStatusPage().isOnlineProductAssignedToContentSets(setList);
        boolean nodeAssignmentColumnIsBlank = viewAssignmentStatusPage().isNodeAssignmentColumnBlank(setList);
        //STEP 05: click Cancel
        Assertions.assertTrue(viewAssignmentStatusPage().clickCancel(),
                "STEP 05: View Assignment Status Page DOESN't close");
        //STEP 06: go to Hierarchy Navigate
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        //STEP 07: search for node uuid
        hierarchySearchPage().searchNodeUuid(uuid);
        //STEP 08: right click the selected sibling metadata
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        Assertions.assertTrue(siblingMetadataContextMenu().taxTypeAssignments(),
                "STEP 08: Tax Type Assignments Window DOESN't appear");
        //STEP 09: click Tax Type Assignments
        boolean productViewTagIsInTheList = taxTypeAssignmentsPage().isViewTagInTheGrid(viewTag);
        String assignmentStatusFromAssignmentsGrid = taxTypeAssignmentsPage().getAssignmentStatusByViewTag(viewTag);
        String productTypeFromAssignmentsGrid = taxTypeAssignmentsPage().getProductTypeByViewTag(viewTag);
        String productNameFromAssignmentsGrid = taxTypeAssignmentsPage().getProductNameByViewTag(viewTag);
        //STEP 10: right click the product tag
        taxTypeAssignmentsPage().rightClickProductTag(viewTag);
        //STEP 11: click Manage Tax Type Assignments
        Assertions.assertTrue(taxTypeAssignmentsContextMenu().manageTaxTypeAssignment(),
                "STEP 11: Manage Tax Type Assignments DOESN't appear");
        String selectedProductName = manageTaxTypeAssignmentsPage().getSelectedTaxTypes();
        String selectedDocumentDetails = manageTaxTypeAssignmentsPage().getSelectedDocument();
        //STEP 12: submit assignment
        manageTaxTypeAssignmentsPage().selectAddTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        Assertions.assertTrue(manageTaxTypeAssignmentsPage().clickSubmit(),
                "STEP 12: Manage Tax Type Assignments Page DOESN't close");
        //STEP 13: click Cancel
        Assertions.assertTrue(taxTypeAssignmentsPage().clickCancel(),
                "STEP 13: Tax Type Assignments window DOESN'T close");
        //STEP 14: go to Tools -> Online Product Maintenance
        toolsMenu().goToOnlineProductMaintenance();
        //STEP 15: right click the product tag and select View Assignment Status
        onlineProductMaintenanceGridPage().rightClickProductTag(viewTag);
        Assertions.assertTrue(onlineProductMaintenanceContextMenu().viewAssignmentStatus(),
                "STEP 14: View Assignment Status window DOESN'T appear");
        boolean taxTypeAssignedToContentSet = viewAssignmentStatusPage().isOnlineProductAssignedToContentSets(setList);
        String nodeAssignmentFirstDepartment = viewAssignmentStatusPage().getNodeAssignmentByContentSetName(setList.get(0));
        String nodeAssignmentSecondDepartment = viewAssignmentStatusPage().getNodeAssignmentByContentSetName(setList.get(1));
        //STEP 16: click Cancel
        Assertions.assertTrue(viewAssignmentStatusPage().clickCancel(),
                "STEP 16: View Assignment Status window DOESN'T close");
        //STEP 17: go to Hierarchy Navigate
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        //STEP 18: search for node uuid
        hierarchySearchPage().searchNodeUuid(uuid);
        //STEP 19-20: right click the selected sibling metadata and click Tax Type Assignments
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().taxTypeAssignments();
        //STEP 21: right click the product tag
        taxTypeAssignmentsPage().rightClickProductTag(viewTag);
        //STEP 22: click Manage Tax Type Assignments
        taxTypeAssignmentsContextMenu().manageTaxTypeAssignment();
        //STEP 23: remove assignment
        manageTaxTypeAssignmentsPage().selectRemoveTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        Assertions.assertTrue(manageTaxTypeAssignmentsPage().clickSubmit(),
                "STEP 23: Manage Tax Type Assignments window DOESN'T close");
        String currentAssignmentStatusAfterUnAssignment = taxTypeAssignmentsPage().getAssignmentStatusByViewTag(viewTag);
        //STEP 24: click Cancel
        Assertions.assertTrue(taxTypeAssignmentsPage().clickCancel(),
                "STEP 24: Tax Type Assignments window DOESN'T close");
        //STEP 25: go to Tools -> Online Product Maintenance
        toolsMenu().goToOnlineProductMaintenance();
        //STEP 26: right click the product tag
        onlineProductMaintenanceGridPage().rightClickProductTag(viewTag);
        //STEP 26: select View Assignment Status
        Assertions.assertTrue(onlineProductMaintenanceContextMenu().viewAssignmentStatus(),
                "STEP 26: View Assignment Status window DOESN'T appear");
        boolean onlineProductStillAssignedToContentSet = viewAssignmentStatusPage().isOnlineProductAssignedToContentSets(setList);
        boolean nodeAssignmentColumnIsBlankNow = viewAssignmentStatusPage().isNodeAssignmentColumnBlank(setList);
        //STEP 27: click Cancel
        Assertions.assertTrue(viewAssignmentStatusPage().clickCancel(),
                "View Assignment Status window DOESN'T close");
        //STEP 28: click Delete
        onlineProductMaintenanceGridPage().rightClickProductTag(viewTag);
        Assertions.assertTrue(onlineProductMaintenanceContextMenu().delete(),
                "Delete Online Product window DOESN't appear");
        Assertions.assertTrue(deleteOnlineProductPage().clickDelete(),
                "Delete Online Product window DOESN'T close");
        onlineProductMaintenancePage().clickRefresh();
        boolean viewTagExists = taxTypeAssignmentsPage().isViewTagInTheGrid(viewTag);

        Assertions.assertAll("taxTypeAssignmentsTest",
                () -> Assertions.assertTrue(taxTypeAssignedToContentSets,
                        "STEP 04: Content sets DON'T appear in the Content Set Name column in View Assignment Status window"),
                () -> Assertions.assertTrue(nodeAssignmentColumnIsBlank,
                        "STEP 04: Node Assignment column ISN'T blank"),
                () -> Assertions.assertTrue(productViewTagIsInTheList,
                        String.format("STEP 09: '%s' ISN'T in the grid on Online Product Assignments Page", viewTag)),
                () -> Assertions.assertNotEquals(assignmentStatusFromAssignmentsGrid, ASSIGNED_STATUS,
                        "STEP 09: Assignment Status ISN'T blank"),
                () -> Assertions.assertEquals(productTypeFromAssignmentsGrid, productType.getType(),
                        String.format("STEP 09: Product Type is %s instead of %s", productTypeFromAssignmentsGrid, productType.getType())),
                () -> Assertions.assertEquals(productNameFromAssignmentsGrid, productName,
                        String.format("STEP 09: Product Name is %s instead of %s", productNameFromAssignmentsGrid, productName)),
                () -> Assertions.assertEquals(selectedProductName, shortProductName,
                        String.format("STEP 11: Selected Product Name is %s instead of %s", selectedProductName, shortProductName)),
                () -> Assertions.assertEquals(selectedDocumentDetails, "= "+documentDetails,
                        String.format("STEP 11: Selected Documents is '%s' instead of '%s'", selectedDocumentDetails, "= "+documentDetails)),
                () -> Assertions.assertTrue(taxTypeAssignedToContentSet,
                        "STEP 15: Content sets DON'T appear in the Content Set Name column in View Assignment Status window"),
                () -> Assertions.assertEquals(nodeAssignmentFirstDepartment, ASSIGNED_STATUS,
                        String.format("STEP 15: '%s' DOESN'T have node assignment", setList.get(0))),
                () -> Assertions.assertNotEquals(nodeAssignmentSecondDepartment, ASSIGNED_STATUS,
                        String.format("STEP 15: '%s' HAVE node assignment", setList.get(1))),
                () -> Assertions.assertNotEquals(currentAssignmentStatusAfterUnAssignment, ASSIGNED_STATUS,
                        String.format("STEP 23: Selected Product Current Assignment Status is '%s' instead of empty", currentAssignmentStatusAfterUnAssignment)),
                () -> Assertions.assertTrue(onlineProductStillAssignedToContentSet,
                        "STEP 26: Content sets DON'T appear in the Content Set Name column in View Assignment Status window"),
                () -> Assertions.assertTrue(nodeAssignmentColumnIsBlankNow,
                        "STEP 26: Node Assignment column ISN'T blank"),
                () -> Assertions.assertFalse(viewTagExists,
                        String.format("STEP 29: %s IS in the Grid", viewTag)));
    }

    /**
     * STORY/BUG - HALCYONST-9557/HALCYONST-9466: <br>
     * SUMMARY - Delete of Tax Type Assigned to a Node (Tools -> Online Product Maintenance and Hierarchy Navigate) <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deletionOfTaxTypeProductTagAssignedToNodeTest()
    {

        connection = CommonDataMocking.connectToDatabase(getEnvironment());
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        UUID = datapodObject.getSections().get(0).getNodeUUID();

        String viewTag = productType.getTagView();
        String productName = String.format("%s Test %s", productType.getType(), DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss());
        String shortProductName = viewTag;
        List<String> setList = Arrays.asList(IOWA_DEVELOPMENT.getName());
        String warningMessage = "Please remove assignments before deleting Online Product";
        productTypeName = productType.getType();
        pubTag = productType.getTagView();

        //STEP 01: sign in as the legal user
        homePage().goToHomePage();
        loginPage().logIn();
        //STEP 02: go to Tools -> Online Product Maintenance
        toolsMenu().goToOnlineProductMaintenance();
        //STEP 03: create a Product Tag, and assign it to Iowa (Development)
        onlineProductMaintenanceContextMenu().createNew();
        createNewOnlineProductPage().selectProductType(productType);
        createNewOnlineProductPage().setViewTag(viewTag);
        createNewOnlineProductPage().setProductName(productName);
        createNewOnlineProductPage().setProductShortName(shortProductName);
        createNewOnlineProductPage().addAssignments(setList);
        createNewOnlineProductPage().clickSave();
        longName = productName;
        shortName = "VRB." + shortProductName;
        onlineProductMaintenancePage().selectProduct(productType);
        onlineProductMaintenancePage().clickRefresh();
        productId = onlineProductMaintenanceGridPage().getProductIdByViewTagAsInt(viewTag);
        //STEP 04: go to Hierarchy Navigate
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        //STEP 05: search for node uuid
        hierarchySearchPage().searchNodeUuid(UUID);
        //STEP 06-07: right click the selected sibling metadata and click Online Product Assignments
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().taxTypeAssignments();
        //STEP 08: right click the product tag
        taxTypeAssignmentsPage().rightClickProductTag(viewTag);
        //STEP 09: click Manage Tax Type Assignments
        taxTypeAssignmentsContextMenu().manageTaxTypeAssignment();
        //STEP 10: submit assignment
        manageTaxTypeAssignmentsPage().selectAddTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        Assertions.assertTrue(manageTaxTypeAssignmentsPage().clickSubmit(),
                "STEP 10: Manage Tax Type Assignments Page DOESN't close");
        String selectedProductCurrentAssignmentStatusAfterAssignment = taxTypeAssignmentsPage().getAssignmentStatusByViewTag(viewTag);
        //STEP 11: click Cancel
        Assertions.assertTrue(taxTypeAssignmentsPage().clickCancel(),
                "STEP 11: Tax Type Assignments window DOESN'T close");
        //STEP 12: go to Tools -> Online Product Maintenance
        toolsMenu().goToOnlineProductMaintenance();
        //STEP 13: right click the product tag and select Delete
        onlineProductMaintenanceGridPage().rightClickProductTag(viewTag);
        Assertions.assertTrue(onlineProductMaintenanceContextMenu().delete(),
                "Delete Online Product window DOESN'T appear");
        String warningMessageFromDeleteOnlineProductPage = deleteOnlineProductPage().getMessage();
        List<String> listAssigned = deleteOnlineProductPage().getListAssigned();
        boolean isDeleteButtonDisable = deleteOnlineProductPage().isDeleteButtonEnabled();
        //STEP 14: click Cancel
        Assertions.assertTrue(deleteOnlineProductPage().clickCancel(),
                "Delete Online Product window DOESN'T close");
        onlineProductMaintenancePage().clickRefresh();
        boolean productTagStillExists = onlineProductMaintenanceGridPage().isOnlineProductInGrid(viewTag, productName, shortProductName);
        //STEP 15: go to Hierarchy Navigate
        hierarchyMenu().goToNavigate();
        //STEP 16: search for node uuid
        hierarchySearchPage().searchNodeUuid(UUID);
        //STEP 17-18: right click the selected sibling metadata and click Online Product Assignments
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().taxTypeAssignments();
        //STEP 19: right click the product tag
        taxTypeAssignmentsPage().rightClickProductTag(viewTag);
        //STEP 20: click Manage Tax Type Assignments
        taxTypeAssignmentsContextMenu().manageTaxTypeAssignment();
        //STEP 21: remove assignment
        manageTaxTypeAssignmentsPage().selectRemoveTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        Assertions.assertTrue(manageTaxTypeAssignmentsPage().clickSubmit(),
                "STEP 21: Manage Tax Type Assignments Page DOESN't close");
        String selectedProductCurrentAssignmentStatus = taxTypeAssignmentsPage().getAssignmentStatusByViewTag(viewTag);
        //STEP 22: click Cancel
        Assertions.assertTrue(taxTypeAssignmentsPage().clickCancel(),
                "Tax Type Assignments window DOESN'T close");
        //STEP 23: go to Tools -> Online Product Maintenance
        toolsMenu().goToOnlineProductMaintenance();
        //STEP 24: right click the product tag and select Delete
        onlineProductMaintenanceGridPage().rightClickProductTag(viewTag);
        onlineProductMaintenanceContextMenu().delete();
        //STEP 25: delete the product tag
        deleteOnlineProductPage().clickDelete();
        onlineProductMaintenancePage().selectProduct(ALL_PRODUCTS);
        onlineProductMaintenancePage().selectProduct(productType);
        onlineProductMaintenancePage().clickRefresh();
        boolean productTagNotExists = onlineProductMaintenanceGridPage().isOnlineProductInGrid(viewTag, productName, shortProductName);
        onlineProductMaintenancePage().selectShowDeleted();
        onlineProductMaintenancePage().clickRefresh();
        onlineProductMaintenanceGridPage().rightClickStrikeProductTag(viewTag);
        onlineProductMaintenanceContextMenu().undelete();

        Assertions.assertAll("deletionOfProductTagAssignedToNodeTest",
                () -> Assertions.assertEquals(selectedProductCurrentAssignmentStatusAfterAssignment, TAGGED_STATUS,
                        String.format("STEP 10: Selected Product Current Assignment Status is '%s' instead of '%s'", selectedProductCurrentAssignmentStatusAfterAssignment, TAGGED_STATUS)),
                () -> Assertions.assertEquals(warningMessageFromDeleteOnlineProductPage, warningMessage,
                        String.format("STEP 13: Warning message is '%s' instead of '%s'", warningMessageFromDeleteOnlineProductPage, warningMessage)),
                () -> Assertions.assertTrue(listAssigned.containsAll(setList),
                        "STEP 13: Content set DOESN'T appear in the list of assigned content sets"),
                () -> Assertions.assertFalse(isDeleteButtonDisable,
                        "STEP 13: Delete button IS ENABLE"),
                () -> Assertions.assertTrue(productTagStillExists,
                        "STEP 13: Product tag DOESN'T exist in the list of product tags"),
                () -> Assertions.assertNotEquals(selectedProductCurrentAssignmentStatus, ASSIGNED_STATUS,
                        String.format("STEP 21: Selected Product Current Assignment Status is '%s' instead of empty", selectedProductCurrentAssignmentStatus)),
                () -> Assertions.assertFalse(productTagNotExists,
                        "STEP 25: product tag STILL exists in the list of product tags"));
    }
    @AfterEach
    public void clean()
    {
        // Clean up of data pod
        if(connection!=null)
        {
            if(datapodObject!=null)
            {
                datapodObject.delete();
            }
        }
        //Clean up of online product tags
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ScriptMaintenanceDatabaseUtils.deepDeleteScriptMaintenanceTestingTags(uatConnection, longName, user().getUsername(), pubTag);
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
