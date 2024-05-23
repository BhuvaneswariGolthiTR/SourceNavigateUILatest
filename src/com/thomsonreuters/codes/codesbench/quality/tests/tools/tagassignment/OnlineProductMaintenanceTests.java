package com.thomsonreuters.codes.codesbench.quality.tests.tools.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.ProductTagDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.sql.Connection;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ProductType.ALL_PRODUCTS;

public class OnlineProductMaintenanceTests extends TestService
{
    Integer productId = 0;
    String longName = "";
    String shortName = "VRB.";
    String productTypeName = "";
    String pubTag = "";

    /**
     * STORY/BUG - HALCYONST-8602/9763 <br>
     * SUMMARY - Create/Edit/Delete Product Tag (Tools -> Online Product Maintenance) <br>
     * USER - Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @EnumSource
            (
                    names = {"CHECKPOINT", "TAX_TYPE"}
            )
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createEditDeleteProductTagTest(ProductType productType)
    {
        String viewTag = productType.getTagView();
        String productName = String.format("%s Test %s", productType.getType(), DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss());
        String shortProductName = viewTag;
        String editProductName = "Edit " + productName;
        String editShortProductName = "PTEDIT";
        productTypeName = productType.getType();
        pubTag = productType.getTagView();
        //STEP 01: sign in as the legal user
        homePage().goToHomePage();
        loginPage().logIn();
        String defaultContentSetFromHomePage = homePage().getCurrentContentSetFromUpperRight();
        //STEP 02: go to Tools -> Online Product Maintenance
        Assertions.assertTrue(toolsMenu().goToOnlineProductMaintenance(),
                "STEP 02: Online Product Maintenance window DOESN'T appear");
        String defaultProductType = onlineProductMaintenancePage().getSelectedProduct();
        boolean isShowDeletedSelected = onlineProductMaintenancePage().isShowDeletedSelected();
        //STEP 03: right click a Product Tag in the list and select Create New
        Assertions.assertTrue(onlineProductMaintenanceContextMenu().createNew(),
                "STEP 03: Create New Online Product window DOESN'T appear");
        List<String> defaultContentSetFromCreateNewOnlineProductPage =
                createNewOnlineProductPage().getSelectedContentSet();
        //STEP 04: set data
        createNewOnlineProductPage().selectProductType(productType);
        createNewOnlineProductPage().setViewTag(viewTag);
        createNewOnlineProductPage().setProductName(productName);
        createNewOnlineProductPage().setProductShortName(shortProductName);
        //STEP 05: click Save
        Assertions.assertTrue(createNewOnlineProductPage().clickSave(),
                "STEP 05: Create New Online Product window DOESN'T close");
        longName = productName;
        shortName = "VRB."+shortProductName;
//        STEP 06: set Select Product: productType
        onlineProductMaintenancePage().selectProduct(productType);
//        STEP 07: click Refresh
        onlineProductMaintenancePage().clickRefresh();
        boolean isCreatedProductInGrid = onlineProductMaintenanceGridPage()
                .isOnlineProductInGrid(viewTag, productName, shortProductName);
        String createdOnlineProductId = onlineProductMaintenanceGridPage().getProductIdByViewTag(viewTag);
        productId = onlineProductMaintenanceGridPage().getProductIdByViewTagAsInt(viewTag);
//        STEP 08: right click the Product Tag and select Edit Names and Assignments
        onlineProductMaintenanceGridPage().rightClickProductTag(viewTag);
        Assertions.assertTrue(onlineProductMaintenanceContextMenu().editNamesAndAssignments(),
                "STEP 08: Edit Online Product window DOESN'T appear");
        String productTypeFromEditOnlineProductWindow = editNamesAndAssignmentsPage().getProductTypeValue();
        String viewTagFromEditOnlineProductWindow = editNamesAndAssignmentsPage().getViewTagValue();
        String productIdFromEditOnlineProductWindow = editNamesAndAssignmentsPage().getProductId();
        //STEP 09: set data
        editNamesAndAssignmentsPage().setProductName(editProductName);
        editNamesAndAssignmentsPage().setProductShortName(editShortProductName);
        //STEP 10: click Save
        Assertions.assertTrue(editNamesAndAssignmentsPage().clickSave(),
                "Edit Online Product window DOESN'T close");
        longName = editProductName;
        shortName = "VRB."+editShortProductName;
//        STEP 11: click Refresh
        onlineProductMaintenancePage().clickRefresh();
        boolean isEditedProductInGrid = onlineProductMaintenanceGridPage().isOnlineProductInGrid(viewTag, editProductName, editShortProductName);
        String editedOnlineProductID = onlineProductMaintenanceGridPage().getProductIdByViewTag(viewTag);
        //STEP: 12 right click the Product Tag and select Delete
        onlineProductMaintenanceGridPage().rightClickProductTag(viewTag);
        Assertions.assertTrue(onlineProductMaintenanceContextMenu().delete(),
                "STEP 12: Delete Online Product window DOESN'T appear");
        boolean isOnlineProductAssigned = deleteOnlineProductPage().isOnlineProductAssigned();
        //STEP: 13 click Delete
        Assertions.assertTrue(deleteOnlineProductPage().clickDelete(),
                "Delete Online Product window DOESN'T appear");
        onlineProductMaintenancePage().clickRefresh();
        boolean isDeletedProductInGrid = onlineProductMaintenanceGridPage().isOnlineProductInGrid(viewTag, editProductName, editShortProductName);

        Assertions.assertAll("createEditDeleteProductTagTest",
                () -> Assertions.assertEquals(ALL_PRODUCTS.getType(), defaultProductType,
                        String.format("STEP 02: Default Product Type is '%s' instead of '%s'", defaultProductType, ALL_PRODUCTS.getType())),
                () -> Assertions.assertFalse(isShowDeletedSelected,
                        "STEP 02: Show deleted option IS CHOSEN by default"),
                () -> Assertions.assertTrue(defaultContentSetFromCreateNewOnlineProductPage.contains(defaultContentSetFromHomePage),
                        String.format("STEP 03: The content set selected by default is '%s' instead of %s",
                                defaultContentSetFromCreateNewOnlineProductPage, defaultContentSetFromHomePage)),
                () -> Assertions.assertTrue(isCreatedProductInGrid,
                        "STEP 07: There is NOT the created Online Product in the Grid"),
                () -> Assertions.assertEquals(productTypeFromEditOnlineProductWindow, productType.getType(),
                        String.format("STEP 08: Product Type in Edit Online Product Window is NOT %s, but %s",
                                productType.getType(), productTypeFromEditOnlineProductWindow)),
                () -> Assertions.assertEquals(viewTagFromEditOnlineProductWindow, viewTag,
                        String.format("STEP 08: View/Tag in Edit Online Product Window is NOT %s, but %s",
                                viewTag, viewTagFromEditOnlineProductWindow)),
                () -> Assertions.assertTrue(isEditedProductInGrid,
                        "STEP 11: There is NOT the edited Online Product in the Grid"),
                () -> Assertions.assertEquals(createdOnlineProductId, editedOnlineProductID,
                        String.format("STEP 11: The Online Product ID was changed from %s to %s after the Online Product was edited",
                                createdOnlineProductId, editedOnlineProductID)),
                () -> Assertions.assertEquals(createdOnlineProductId, productIdFromEditOnlineProductWindow,
                        String.format("STEP 11: The Online Product ID is %s, but in Edit Online Product Window is %s",
                                createdOnlineProductId, productIdFromEditOnlineProductWindow)),
                () -> Assertions.assertFalse(isOnlineProductAssigned,
                        "STEP 12: Online Product HAS assignments"),
                () -> Assertions.assertFalse(isDeletedProductInGrid,
                        "STEP 13: There IS the deleted Online Product in the Grid"));
    }

//    @AfterEach
//    public void deleteProductTagFromDevDB()
//    {
//        Connection devConnection = BaseDatabaseUtils.connectToDatabaseDEV();
//        ProductTagDatabaseUtils.deleteProductTag(devConnection, productId,longName,shortName,legalUser().getUsername(),productTypeName,pubTag);
//        BaseDatabaseUtils.commit(devConnection);
//        BaseDatabaseUtils.disconnect(devConnection);
//    }

    @AfterEach
    public void deleteProductTagFromUatDB()
    {
        Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        ProductTagDatabaseUtils.deleteProductTag(uatConnection, productId,longName,shortName,legalUser().getUsername(),productTypeName,pubTag);
        BaseDatabaseUtils.commit(uatConnection);
        BaseDatabaseUtils.disconnect(uatConnection);
    }

}
