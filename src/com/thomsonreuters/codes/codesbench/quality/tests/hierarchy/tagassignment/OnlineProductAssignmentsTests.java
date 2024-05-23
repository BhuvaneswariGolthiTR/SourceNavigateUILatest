package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.SiblingMetadataElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ProductTagDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.USCA_DEVELOPMENT;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ProductType.ALL_PRODUCTS;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ProductType.CHECKPOINT;

public class OnlineProductAssignmentsTests extends TestService
{
    private static final String TAGGED_STATUS = "Tagged";
    private static final String NOT_TAGGED_STATUS = "Not Tagged";
    private static final String ASSIGNED_STATUS = "Assigned";

    private Integer productId = 0;
    private String longName = "";
    private String shortName = "VRB.";
    private String productTypeName = "";
    private String pubTag = "";

    private HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - HALCYONST-8602/9763 <br>
     * SUMMARY - Product Tag Assignments (Tools -> Online Product Maintenance and Hierarchy Navigate) <br>
     * USER - Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @EnumSource
            (
                    names = {"CHECKPOINT"}
            )
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void onlineProductAssignmentsTest(ProductType productType)
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String viewTag = productType.getTagView();
        String productName = String.format("%s Test %s", productType.getType(), DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss());
        String shortProductName = viewTag;
        List<String> setList = Arrays.asList(IOWA_DEVELOPMENT.getName(), USCA_DEVELOPMENT.getName());
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        String nodeValue = HierarchyDatabaseUtils.getNodeValueByNodeUUID(nodeUUID, IOWA_DEVELOPMENT.getCode(), connection);
        BaseDatabaseUtils.disconnect(connection);
        String documentDetails = "= ".concat(nodeValue);
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
        createNewOnlineProductPage().clickSave();
        longName = productName;
        shortName = "VRB." + shortProductName;
        //STEP 04: right click the product tag and select View Assignment Status
        onlineProductMaintenancePage().selectProduct(productType);
        onlineProductMaintenancePage().clickRefresh();
        productId = onlineProductMaintenanceGridPage().getProductIdByViewTagAsInt(viewTag);
        onlineProductMaintenanceGridPage().rightClickProductTag(viewTag);
        Assertions.assertTrue(onlineProductMaintenanceContextMenu().viewAssignmentStatus(),
                "STEP 04: View Assignment Status Page DOESN't appear");
        boolean onlineProductAssignedToContentSets = viewAssignmentStatusPage().isOnlineProductAssignedToContentSets(setList);
        boolean nodeAssignmentColumnIsBlank = viewAssignmentStatusPage().isNodeAssignmentColumnBlank(setList);
        //STEP 05: click Cancel
        Assertions.assertTrue(viewAssignmentStatusPage().clickCancel(),
                "STEP 05: View Assignment Status Page DOESN't close");
        //STEP 06: go to Hierarchy Navigate
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        //STEP 07: search for node uuid
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        //STEP 08: right click the selected sibling metadata
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        Assertions.assertTrue(siblingMetadataContextMenu().onlineProductAssignments(),
                "STEP 08: Online Product Assignments Window DOESN't appear");
        //STEP 09: click Online Product Assignments
        boolean productViewTagIsInTheList = onlineProductAssignmentsPage().isViewTagInTheGrid(viewTag);
        String assignmentStatusFromAssignmentsGrid = onlineProductAssignmentsPage().getAssignmentStatusByViewTag(viewTag);
        String productTypeFromAssignmentsGrid = onlineProductAssignmentsPage().getProductTypeByViewTag(viewTag);
        String productNameFromAssignmentsGrid = onlineProductAssignmentsPage().getProductNameByViewTag(viewTag);
        //STEP 10: right click the product tag
        onlineProductAssignmentsPage().rightClickProductTag(viewTag);
        //STEP 11: click Manage Online Product Assignments
        Assertions.assertTrue(onlineProductAssignmentsContextMenu().manageOnlineProductAssignment(),
                "STEP 11: Manage Online Product Assignments DOESN't appear");
        String selectedProductViewTag = manageOnlineProductAssignmentsPage().getSelectedProductViewTag();
        String selectedProductType = manageOnlineProductAssignmentsPage().getSelectedProductType();
        String selectedProductName = manageOnlineProductAssignmentsPage().getSelectedProductName();
        String selectedProductCurrentAssignmentStatusBeforeAssignment = manageOnlineProductAssignmentsPage().getSelectedProductCurrentAssignmentStatus();
        String selectedDocumentDetails = manageOnlineProductAssignmentsPage().getSelectedDocument();
        //STEP 12: submit assignment
        manageOnlineProductAssignmentsPage().selectAddTag();
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        if(yourWorkflowHasBeenCreatedPage().verifyYourWorkflowHasBeenCreatedPageOpened())
        {
            yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();
            String workFlowIdAferAssignmentTag = yourWorkflowHasBeenCreatedPage().getWorkflowId();
            yourWorkflowHasBeenCreatedPage().clickClose();
            onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
            onlineProductAssignmentsPage().clickCancel();
            verifyWorkflowFinishes(workFlowIdAferAssignmentTag);
            siblingMetadataContextMenu().onlineProductAssignments();
        }
        Assertions.assertTrue(manageOnlineProductAssignmentsPage().doesManageOnlineProductAssignmentsPageClose(),
                "STEP 12: Manage Online Product Assignments Page DOESN't close");
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        String selectedProductCurrentAssignmentStatusAfterAssignment = onlineProductAssignmentsPage().getAssignmentStatusByViewTag(viewTag);
        //STEP 13: click Cancel
        Assertions.assertTrue(onlineProductAssignmentsPage().clickCancel(),
                "STEP 13: Online Product Assignments window DOESN'T close");
        //STEP 14: go to Tools -> Online Product Maintenance
        toolsMenu().goToOnlineProductMaintenance();
        //STEP 15: right click the product tag and select View Assignment Status
        onlineProductMaintenanceGridPage().rightClickProductTag(viewTag);
        Assertions.assertTrue(onlineProductMaintenanceContextMenu().viewAssignmentStatus(),
                "STEP 14: View Assignment Status window DOESN'T appear");
        boolean onlineProductAssignedToContentSet = viewAssignmentStatusPage().isOnlineProductAssignedToContentSets(setList);
        String nodeAssignmentFirstDepartment = viewAssignmentStatusPage().getNodeAssignmentByContentSetName(setList.get(0));
        String nodeAssignmentSecondDepartment = viewAssignmentStatusPage().getNodeAssignmentByContentSetName(setList.get(1));
        //STEP 16: click Cancel
        Assertions.assertTrue(viewAssignmentStatusPage().clickCancel(),
                "STEP 16: View Assignment Status window DOESN'T close");
        //STEP 17: go to Hierarchy Navigate
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        //STEP 18: search for node uuid
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        //STEP 19-20: right click the selected sibling metadata and click Online Product Assignments
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().onlineProductAssignments();
        //STEP 21: right click the product tag
        onlineProductAssignmentsPage().rightClickProductTag(viewTag);
        //STEP 22: click Manage Online Product Assignments
        onlineProductAssignmentsContextMenu().manageOnlineProductAssignment();
        String currentAssignmentStatus = manageOnlineProductAssignmentsPage().getSelectedProductCurrentAssignmentStatus();
        //STEP 23: remove assignment
        manageOnlineProductAssignmentsPage().selectRemoveTag();
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        if(yourWorkflowHasBeenCreatedPage().verifyYourWorkflowHasBeenCreatedPageOpened())
        {
            yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();
            String workFlowIdAferAssignmentTag = yourWorkflowHasBeenCreatedPage().getWorkflowId();
            yourWorkflowHasBeenCreatedPage().clickClose();
            onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
            onlineProductAssignmentsPage().clickCancel();
            verifyWorkflowFinishes(workFlowIdAferAssignmentTag);
            siblingMetadataContextMenu().onlineProductAssignments();
        }
        Assertions.assertTrue(manageOnlineProductAssignmentsPage().doesManageOnlineProductAssignmentsPageClose(),
                "STEP 23: Manage Online Product Assignments window DOESN'T close");
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        String currentAssignmentStatusAfterUnAssignment = onlineProductAssignmentsPage().getAssignmentStatusByViewTag(viewTag);
        //STEP 24: click Cancel
        Assertions.assertTrue(onlineProductAssignmentsPage().clickCancel(),
                "STEP 24: Online Product Assignments window DOESN'T close");
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
        boolean viewTagExists = onlineProductAssignmentsPage().isViewTagInTheGrid(viewTag);

        Assertions.assertAll("onlineProductAssignmentsTest",
                () -> Assertions.assertTrue(onlineProductAssignedToContentSets,
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
                () -> Assertions.assertEquals(selectedProductViewTag, viewTag,
                        String.format("STEP 11: Selected Product View/Tag is %s instead of %s", selectedProductViewTag, viewTag)),
                () -> Assertions.assertEquals(selectedProductType, productType.getType(),
                        String.format("STEP 11: Selected Product Type is %s instead of %s", selectedProductType, productType.getType())),
                () -> Assertions.assertEquals(selectedProductName, productName,
                        String.format("STEP 11: Selected Product Name is %s instead of %s", selectedProductName, productName)),
                () -> Assertions.assertEquals(selectedProductCurrentAssignmentStatusBeforeAssignment, NOT_TAGGED_STATUS,
                        String.format("STEP 11: Selected Product Current Assignment Status is %s instead of %s", selectedProductCurrentAssignmentStatusBeforeAssignment, NOT_TAGGED_STATUS)),
                () -> Assertions.assertEquals(selectedDocumentDetails, documentDetails,
                        String.format("STEP 11: Selected Documents is '%s' instead of '%s'", selectedDocumentDetails, documentDetails)),
                () -> Assertions.assertEquals(selectedProductCurrentAssignmentStatusAfterAssignment, TAGGED_STATUS,
                        String.format("STEP 12: Selected Product Current Assignment Status is '%s' instead of '%s'", selectedProductCurrentAssignmentStatusAfterAssignment, TAGGED_STATUS)),
                () -> Assertions.assertTrue(onlineProductAssignedToContentSet,
                        "STEP 15: Content sets DON'T appear in the Content Set Name column in View Assignment Status window"),
                () -> Assertions.assertEquals(nodeAssignmentFirstDepartment, ASSIGNED_STATUS,
                        String.format("STEP 15: '%s' DOESN'T have node assignment", setList.get(0))),
                () -> Assertions.assertNotEquals(nodeAssignmentSecondDepartment, ASSIGNED_STATUS,
                        String.format("STEP 15: '%s' HAVE node assignment", setList.get(1))),
                () -> Assertions.assertEquals(currentAssignmentStatus, TAGGED_STATUS,
                        String.format("STEP 22: Selected Product Current Assignment Status is %s instead of %s", currentAssignmentStatus, TAGGED_STATUS)),
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
     * STORY/BUG - HALCYONST-8602/9763 <br>
     * SUMMARY - Delete of Product Tag Assigned to a Node (Tools -> Online Product Maintenance and Hierarchy Navigate) <br>
     * USER - Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @EnumSource
            (
                    names = {"CHECKPOINT"}
            )
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deletionOfProductTagAssignedToNodeTest(ProductType productType)
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
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
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        //STEP 06-07: right click the selected sibling metadata and click Online Product Assignments
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().onlineProductAssignments();
        //STEP 08: right click the product tag
        onlineProductAssignmentsPage().rightClickProductTag(viewTag);
        //STEP 09: click Manage Online Product Assignments
        onlineProductAssignmentsContextMenu().manageOnlineProductAssignment();
        //STEP 10: submit assignment
        manageOnlineProductAssignmentsPage().selectAddTag();
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        if(yourWorkflowHasBeenCreatedPage().verifyYourWorkflowHasBeenCreatedPageOpened())
        {
            yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();
            String workFlowIdAferAssignmentTag = yourWorkflowHasBeenCreatedPage().getWorkflowId();
            yourWorkflowHasBeenCreatedPage().clickClose();
            onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
            onlineProductAssignmentsPage().clickCancel();
            verifyWorkflowFinishes(workFlowIdAferAssignmentTag);
            siblingMetadataContextMenu().onlineProductAssignments();
        }
        Assertions.assertTrue(manageOnlineProductAssignmentsPage().doesManageOnlineProductAssignmentsPageClose(),
                "STEP 10: Manage Online Product Assignments Page DOESN't close");
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        String selectedProductCurrentAssignmentStatusAfterAssignment = onlineProductAssignmentsPage().getAssignmentStatusByViewTag(viewTag);
        //STEP 11: click Cancel
        Assertions.assertTrue(onlineProductAssignmentsPage().clickCancel(),
                "STEP 11: Online Product Assignments window DOESN'T close");
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
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        //STEP 16: search for node uuid
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        //STEP 17-18: right click the selected sibling metadata and click Online Product Assignments
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().onlineProductAssignments();
        //STEP 19: right click the product tag
        onlineProductAssignmentsPage().rightClickProductTag(viewTag);
        //STEP 20: click Manage Online Product Assignments
        onlineProductAssignmentsContextMenu().manageOnlineProductAssignment();
        //STEP 21: remove assignment
        manageOnlineProductAssignmentsPage().selectRemoveTag();
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        if(yourWorkflowHasBeenCreatedPage().verifyYourWorkflowHasBeenCreatedPageOpened())
        {
            yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();
            String workFlowIdAferAssignmentTag = yourWorkflowHasBeenCreatedPage().getWorkflowId();
            yourWorkflowHasBeenCreatedPage().clickClose();
            onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
            onlineProductAssignmentsPage().clickCancel();
            verifyWorkflowFinishes(workFlowIdAferAssignmentTag);
            siblingMetadataContextMenu().onlineProductAssignments();
        }
        Assertions.assertTrue(manageOnlineProductAssignmentsPage().doesManageOnlineProductAssignmentsPageClose(),
                "STEP 21: Manage Online Product Assignments Page DOESN't close");
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        String selectedProductCurrentAssignmentStatus = onlineProductAssignmentsPage().getAssignmentStatusByViewTag(viewTag);
        //STEP 22: click Cancel
        Assertions.assertTrue(onlineProductAssignmentsPage().clickCancel(),
                "Online Product Assignments window DOESN'T close");
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

    /**
     * STORY/BUG - HALCYONST-8602/9763 <br>
     * SUMMARY - Product Tags Online Product Maintenance Context Menu Enabled Options and Run Product Tag Report <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void productTagsOnlineProductMaintenanceContextMenuEnabledOptionsRunProductTagReportTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String viewTag = CHECKPOINT.getTagView();
        ProductType productType = CHECKPOINT;
        String productName = String.format("%s Test %s", productType.getType(), DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss());
        String shortProductName = viewTag;
        List<String> setList = Arrays.asList(IOWA_DEVELOPMENT.getName());
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        productTypeName = productType.getType();
        pubTag = productType.getTagView();

        //STEP 01: sign in as the legal user
        homePage().goToHomePage();
        loginPage().logIn();
        //STEP 02: go to Tools -> Online Product Maintenance
        toolsMenu().goToOnlineProductMaintenance();
        //STEP 03: create a Checkpoint Product Tag, and assign it to Iowa (Development)
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
        String createdOnlineProductId = onlineProductMaintenanceGridPage().getProductIdByViewTag(viewTag);
        //STEP 04: go to Hierarchy Navigate
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        //STEP 05: search for node uuid
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        //STEP 06-07: right click the selected sibling metadata and click Online Product Assignments
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().onlineProductAssignments();
        //STEP 08: right click the product tag
        onlineProductAssignmentsPage().rightClickProductTag(viewTag);
        //STEP 09: click Manage Online Product Assignments
        onlineProductAssignmentsContextMenu().manageOnlineProductAssignment();
        //STEP 10: submit assignment
        manageOnlineProductAssignmentsPage().selectAddTag();
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        if(yourWorkflowHasBeenCreatedPage().verifyYourWorkflowHasBeenCreatedPageOpened())
        {
            yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();
            String workFlowIdAferAssignmentTag = yourWorkflowHasBeenCreatedPage().getWorkflowId();
            yourWorkflowHasBeenCreatedPage().clickClose();
            onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
            onlineProductAssignmentsPage().clickCancel();
            verifyWorkflowFinishes(workFlowIdAferAssignmentTag);
            siblingMetadataContextMenu().onlineProductAssignments();
        }
        Assertions.assertTrue(manageOnlineProductAssignmentsPage().doesManageOnlineProductAssignmentsPageClose(),
                "STEP 10: Manage Online Product Assignments Page DOESN't close");
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        String selectedProductCurrentAssignmentStatusAfterAssignment = onlineProductAssignmentsPage().getAssignmentStatusByViewTag(viewTag);
        //STEP 11: click Cancel
        Assertions.assertTrue(onlineProductAssignmentsPage().clickCancel(),
                "STEP 11: Online Product Assignments window DOESN'T close");
        //STEP 12: go to Tools -> Online Product Maintenance
        toolsMenu().goToOnlineProductMaintenance();
        //STEP 13: set Iowa (Development) Content Set radio button
        onlineProductMaintenancePage().selectContentSetByName(setList.get(0));
        //STEP 14: click Refresh
        onlineProductMaintenancePage().clickRefresh();
        //STEP 15: right click the product tag
        onlineProductMaintenanceGridPage().rightClickProductTag(viewTag);
        //STEP 16: verify enable/disable context menu item
        boolean isCreateNewDisable = onlineProductMaintenanceContextMenu().isCreateNewDisable();
        boolean isEditNamesAndAssignmentsDisable = onlineProductMaintenanceContextMenu().isEditNamesAndAssignmentsDisable();
        boolean isDeleteOnlineProductDisable = onlineProductMaintenanceContextMenu().isDeleteOnlineProductDisable();
        boolean isRunProductTagReportDisable = onlineProductMaintenanceContextMenu().isRunProductTagReportDisable();
        boolean isRemoveAssignmentDisable = onlineProductMaintenanceContextMenu().isRemoveAssignmentDisable();
        boolean isViewAssignmentStatusDisable = onlineProductMaintenanceContextMenu().isViewAssignmentStatusDisable();
        boolean isUpdateAttributeValuesDisable = onlineProductMaintenanceContextMenu().isUpdateAttributeValuesDisable();
        //STEP 17: click Run Product Tag Report
        Assertions.assertTrue(onlineProductMaintenanceContextMenu().runProductTagReport(),
                "Online Product Assignment Report window DOESN'T appear");
        //STEP 18: verify id
        String selectedProduct = onlineProductAssignmentReportPage().getSelectedProduct();
        //STEP 19: click Wip View radio button
        onlineProductAssignmentReportPage().selectWipView();
        //STEP 20: set Effective Date to the current date
        onlineProductAssignmentReportPage().setDate(currentDate);
        //STEP 21: click Ok
        Assertions.assertTrue(onlineProductAssignmentReportPage().clickOk(),
                "STEP 21: Your Workflow Has Been Created window DOESN'T appear");
        yourWorkflowHasBeenCreatedPage().clickLink();
        Assertions.assertTrue(workflowDetailsPage().verifyWorkflowFinished(),
                "STEP 21: workflow DOESN'T finish");
        //STEP 22: remove assignment
        workflowDetailsPage().closeAndSwitchToYourWorkflowHasBeenCreatedPage();
        Assertions.assertTrue(yourWorkflowHasBeenCreatedPage().clickClose(),
                "STEP 22: Your Workflow Has Been Created window DOESN'T close");
        onlineProductMaintenanceGridPage().rightClickProductTag(viewTag);
        String alertMessage = onlineProductMaintenanceContextMenu().removeAssignments(setList.get(0), productName);
        //TODO: reduce select product steps after closing http://ent.jira.int.thomsonreuters.com/browse/HALCYONST-9627
        onlineProductMaintenancePage().selectProduct(ALL_PRODUCTS);
        onlineProductMaintenancePage().clickRefresh();
        String nodeAssignmentStatus = onlineProductMaintenanceGridPage().getProductNodeAssignmentByViewTag(viewTag);
        //STEP 23: verify enable/disable context menu item
        onlineProductMaintenanceGridPage().rightClickProductTag(viewTag);
        boolean isCreateNewDisableWithoutAssignment = onlineProductMaintenanceContextMenu().isCreateNewDisable();
        boolean isEditNamesAndAssignmentsDisableWithoutAssignment = onlineProductMaintenanceContextMenu().isEditNamesAndAssignmentsDisable();
        boolean isDeleteOnlineProductDisableWithoutAssignment = onlineProductMaintenanceContextMenu().isDeleteOnlineProductDisable();
        boolean isRunProductTagReportDisableWithoutAssignment = onlineProductMaintenanceContextMenu().isRunProductTagReportDisable();
        boolean isRemoveAssignmentDisableWithoutAssignment = onlineProductMaintenanceContextMenu().isRemoveAssignmentDisable();
        boolean isViewAssignmentStatusDisableWithoutAssignment = onlineProductMaintenanceContextMenu().isViewAssignmentStatusDisable();
        boolean isUpdateAttributeValuesDisableWithoutAssignment = onlineProductMaintenanceContextMenu().isUpdateAttributeValuesDisable();

        Assertions.assertAll("productTagsOnlineProductMaintenanceContextMenuEnabledOptionsRunProductTagReportTest",
                () -> Assertions.assertEquals(selectedProductCurrentAssignmentStatusAfterAssignment, TAGGED_STATUS,
                        String.format("STEP 10: Selected Product Current Assignment Status is '%s' instead of '%s'", selectedProductCurrentAssignmentStatusAfterAssignment, TAGGED_STATUS)),
                () -> Assertions.assertFalse(isCreateNewDisable, "STEP 16: Create New is disabled"),
                () -> Assertions.assertFalse(isEditNamesAndAssignmentsDisable, "STEP 16: Edit Names and Assignments is disabled"),
                () -> Assertions.assertFalse(isDeleteOnlineProductDisable, "STEP 16: Delete is disabled"),
                () -> Assertions.assertFalse(isRunProductTagReportDisable, "STEP 16: Run Product Tag Report is disabled"),
                () -> Assertions.assertFalse(isRemoveAssignmentDisable, "STEP 16: Remove Assignments is disabled"),
                () -> Assertions.assertFalse(isViewAssignmentStatusDisable, "STEP 16: View Assignment Status is disabled"),
                () -> Assertions.assertFalse(isUpdateAttributeValuesDisable, "STEP 16: Update Attribute Values is disabled"),
                () -> Assertions.assertTrue(selectedProduct.contains(createdOnlineProductId),
                        String.format("STEP 18: '%s' instead of  '%s'", selectedProduct, createdOnlineProductId)),
                () -> Assertions.assertEquals(alertMessage.replace("\n", ""), String.format("Are you sure you want to remove  %s documents from %s Online Product?", setList.get(0), productName),
                        String.format("STEP 22: An alert message ISN'T 'Are you sure you want to remove %s documents from %s Online Product?'", setList.get(0), productName)),
                () -> Assertions.assertNotEquals(nodeAssignmentStatus, ASSIGNED_STATUS,
                        String.format("STEP 22: Selected Product Current Assignment Status is '%s' instead of empty", selectedProductCurrentAssignmentStatusAfterAssignment)),
                () -> Assertions.assertFalse(isCreateNewDisableWithoutAssignment, "STEP 23: Create New is disabled"),
                () -> Assertions.assertFalse(isEditNamesAndAssignmentsDisableWithoutAssignment, "STEP 23: Edit Names and Assignments is disabled"),
                () -> Assertions.assertFalse(isDeleteOnlineProductDisableWithoutAssignment, "STEP 23: Delete is disabled"),
                () -> Assertions.assertTrue(isRunProductTagReportDisableWithoutAssignment, "STEP 23: Run Product Tag Report is enabled"),
                () -> Assertions.assertTrue(isRemoveAssignmentDisableWithoutAssignment, "STEP 23: Remove Assignments is enabled"),
                () -> Assertions.assertFalse(isViewAssignmentStatusDisableWithoutAssignment, "STEP 23: View Assignment Status is disabled"),
                () -> Assertions.assertFalse(isUpdateAttributeValuesDisableWithoutAssignment, "STEP 23: Update Attribute Values is disabled")
        );
    }

    /**
     * STORY/BUG - JETS-22588 <br>
     * SUMMARY - Verifies the product tag column in hierarchy navigate is updated correctly when product tags are added to a node. <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void displayProductTagsInHierarchyNavigateTest()
    {
        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();

        String productTag1 = "11373998";   //ALAA
        String productTag2 = "11053998";   //BNKG
        String productTag3 = "11331998";   //CHKPNT
        String productTag4 = "11073998";   //ESTEST

        String expectedTags1 = "ALAA";
        String expectedTags2 = "ALAA;BNKG";
        String expectedTags3 = "ALAA;BNKG;CHKPNT";
        String expectedTags4 = "ALAA;BNKG;CHKPNT;...";

        //Sign in and go to hierarchy navigate
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());

        hierarchySearchPage().searchNodeUuid(nodeUUID);

        boolean productTagColumnAppears = hierarchyNavigatePage().doesElementExist(SiblingMetadataElements.PRODUCT_TAG_COLUMN_XPATH);
        Assertions.assertTrue(productTagColumnAppears, "Product tag column should appear");

        //Add 1st product tag, verify product tag updated correctly in ui
        ProductTagDatabaseUtils.addProductTagToNode(connection, productTag1, nodeUUID, IOWA_DEVELOPMENT.getCode());
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelectedNode();
        boolean isProductTagColumnUpdated1 = siblingMetadataPage().getSelectedNodeProductTag().equals(expectedTags1);
        Assertions.assertTrue(isProductTagColumnUpdated1, "Product tag column should show" + expectedTags1);

        //Add 2nd product tag, verify product tag updated correctly in ui
        ProductTagDatabaseUtils.addProductTagToNode(connection, productTag2, nodeUUID, IOWA_DEVELOPMENT.getCode());
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelectedNode();
        boolean isProductTagColumnUpdated2 = siblingMetadataPage().getSelectedNodeProductTag().equals(expectedTags2);
        Assertions.assertTrue(isProductTagColumnUpdated2, "Product tag column should show" + expectedTags2);

        //Add 3rd product tag, verify product tag updated correctly in ui
        ProductTagDatabaseUtils.addProductTagToNode(connection, productTag3, nodeUUID, IOWA_DEVELOPMENT.getCode());
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelectedNode();
        boolean isProductTagColumnUpdated3 = siblingMetadataPage().getSelectedNodeProductTag().equals(expectedTags3);
        Assertions.assertTrue(isProductTagColumnUpdated3, "Product tag column should show" + expectedTags3);

        //Add 4th product tag, verify product tag updated correctly in ui
        ProductTagDatabaseUtils.addProductTagToNode(connection, productTag4, nodeUUID, IOWA_DEVELOPMENT.getCode());
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelectedNode();
        boolean isProductTagColumnUpdated4 = siblingMetadataPage().getSelectedNodeProductTag().equals(expectedTags4);
        Assertions.assertTrue(isProductTagColumnUpdated4, "Product tag column should show" + expectedTags4);

        //Remove 4th product tag, verify product tag updated correctly in ui
        ProductTagDatabaseUtils.removeProductTagFromNode(connection, productTag4, nodeUUID, IOWA_DEVELOPMENT.getCode());
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelectedNode();
        boolean isProductTagColumnUpdated5 = siblingMetadataPage().getSelectedNodeProductTag().equals(expectedTags3);
        Assertions.assertTrue(isProductTagColumnUpdated5, "Product tag column should show" + expectedTags3);

        //Remove 3rd product tag, verify product tag updated correctly in ui
        ProductTagDatabaseUtils.removeProductTagFromNode(connection, productTag3, nodeUUID, IOWA_DEVELOPMENT.getCode());
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelectedNode();
        boolean isProductTagColumnUpdated6 = siblingMetadataPage().getSelectedNodeProductTag().equals(expectedTags2);
        Assertions.assertTrue(isProductTagColumnUpdated6, "Product tag column should show" + expectedTags2);

        //Remove 2nd product tag, verify product tag updated correctly in ui
        ProductTagDatabaseUtils.removeProductTagFromNode(connection, productTag2, nodeUUID, IOWA_DEVELOPMENT.getCode());
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelectedNode();
        boolean isProductTagColumnUpdated7 = siblingMetadataPage().getSelectedNodeProductTag().equals(expectedTags1);
        Assertions.assertTrue(isProductTagColumnUpdated7, "Product tag column should show" + expectedTags1);

        //Remove 1st product tag, verify product tag updated correctly in ui
        ProductTagDatabaseUtils.removeProductTagFromNode(connection, productTag1, nodeUUID, IOWA_DEVELOPMENT.getCode());
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().refreshSelectedNode();
        boolean isProductTagColumnUpdated8 = siblingMetadataPage().getSelectedNodeProductTag().equals("");
        Assertions.assertTrue(isProductTagColumnUpdated8, "Product tag column should be empty");
    }

    public void verifyWorkflowFinishes(String workFlowId)
    {
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workFlowId);
        workflowSearchPage().clickFilterButton();
        workflowSearchPage().checkFirstWorkflowFinishedTenMinutes();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();
    }

    @AfterEach
    public void cleanUp()
    {
        Connection connection = CommonDataMocking.connectToDatabase(environmentTag);
        ProductTagDatabaseUtils.deleteProductTag(connection, productId, longName, shortName, legalUser().getUsername(), productTypeName, pubTag);
        BaseDatabaseUtils.commit(connection);
        BaseDatabaseUtils.disconnect(connection);
    }

    @AfterEach
    public void deleteDatapod()
    {
        if(datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
