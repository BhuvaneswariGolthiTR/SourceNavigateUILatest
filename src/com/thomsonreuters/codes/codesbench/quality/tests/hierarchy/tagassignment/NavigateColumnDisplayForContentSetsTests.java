package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import org.junit.jupiter.api.AfterEach;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.CROWN_DEPENDENCIES;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class NavigateColumnDisplayForContentSetsTests extends TestService
{
    Connection connection;
    HierarchyDatapodObject datapodObject;

    //    private static final String UUID = "IE591E800158111DA8AC5CD53670E6B4E";
    private static final  String TAX_TYPE_COLUMN_VALUE_WHEN_FOUR_ASSIGNMENTS = "TTRT1;TTRT2;TTRT3;...";
    private static final String TAX_TYPE_COLUMN_VALUE_WHEN_THREE_ASSIGNMENTS = "TTRT1;TTRT3;TTRT4";
    private final List<String> viewTagList = Arrays.asList("TTRT1", "TTRT2", "TTRT3", "TTRT4");

    @BeforeEach
    public void loginAngGoToHierarchyPage()
    {
        homePage().goToHomePage();
        loginPage().logIn();
    }

    /**
     * STORY/BUG - HALCYONST-8602/9763 <br>
     * SUMMARY -  Hierarchy Navigate column display (Shared and Legal content sets) <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void hierarchyNavigateColumnDisplayForSharedAndLegalContentSetsTest()
    {
        connection = CommonDataMocking.connectToDatabase(getEnvironment());
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String UUID = datapodObject.getSections().get(0).getNodeUUID();

        //STEP 01: Sign in as the legal user. The homemage content set shows 'Iowa (Development)' is selected.  This is a shared content set
        String defaultContentSetFromHomePage = homePage().getCurrentContentSetFromUpperRight();
        //STEP 02: Go to Hierarchy -> Navigate. We see a Tax Type column
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        boolean isTaxTypeColumnDisplayedForSharedContentSet = siblingMetadataPage().isTaxTypeColumnDisplayed();
        //STEP 03: Search for node UUID: I34E314B0014511DB8AD6C31C3DF13255. This node has a value of 901.5 in case the uuid ever changes
        hierarchySearchPage().searchNodeUuid(UUID);
        //STEP 04: Assign the already created Tax Type product tags to this node: TTRT1, Tax Type Regression Test 1, VRB.TTRT1; TTRT2, Tax Type Regression Test 2, VRB.TTRT2; TTRT3, Tax Type Regression Test 3, VRB.TTRT3; TTRT4, Tax Type Regression Test 4, VRB.TTRT4; In DEV, TTRT4 is actually TTRT5
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().taxTypeAssignments();
        for (String viewTag : viewTagList)
        {
            taxTypeAssignmentsPage().rightClickProductTag(viewTag);
            taxTypeAssignmentsContextMenu().manageTaxTypeAssignment();
            manageTaxTypeAssignmentsPage().selectAddTag();
            manageTaxTypeAssignmentsPage().selectAllVersions();
            manageTaxTypeAssignmentsPage().clickSubmit();
        }
        taxTypeAssignmentsPage().clickCancel();
        //STEP 05: Perform Refresh Selection on the hierarchy navigate page
        siblingMetadataPage().refreshPage();
        String actualTaxTypeColumnValueWhenFourAssignments = siblingMetadataPage().getSelectedNodeTaxType();
        //STEP 06: Remove the assignment for TTRT2
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().taxTypeAssignments();
        taxTypeAssignmentsPage().rightClickProductTag(viewTagList.get(1));
        taxTypeAssignmentsContextMenu().manageTaxTypeAssignment();
        manageTaxTypeAssignmentsPage().selectRemoveTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        manageTaxTypeAssignmentsPage().clickSubmit();
        taxTypeAssignmentsPage().clickCancel();
        //STEP 07: Perform Refresh Selection on the hierarchy navigate page
        siblingMetadataPage().refreshPage();
        String actualTaxTypeColumnValueWhenThreeAssignments = siblingMetadataPage().getSelectedNodeTaxType();
        //STEP 08: Remove all Tax Type assignments from this node
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().taxTypeAssignments();
        for (String viewTag : viewTagList)
        {
            taxTypeAssignmentsPage().rightClickProductTag(viewTag);
            taxTypeAssignmentsContextMenu().manageTaxTypeAssignment();
            manageTaxTypeAssignmentsPage().selectRemoveTag();
            manageTaxTypeAssignmentsPage().selectAllVersions();
            manageTaxTypeAssignmentsPage().clickSubmit();
        }
        taxTypeAssignmentsPage().clickCancel();
        //STEP 09: Go to Home -> My Home Page
        homePage().goToHomePage();
        //STEP 10: Change the homepage content set to PopName (UPT).  This is a legal specific content set
        homePage().setMyContentSet("PopName (UPT)");
        //STEP 11: Go to Hierarchy -> Navigate
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
        boolean isTaxTypeColumnDisplayedForLegalSpecificContentSet = siblingMetadataPage().isTaxTypeColumnDisplayed();


        Assertions.assertAll("hierarchyNavigateColumnDisplayForSharedAndLegalContentSetsTest",
            () -> Assertions.assertEquals(IOWA_DEVELOPMENT.getName(), defaultContentSetFromHomePage,
                    String.format("STEP 01: Default  is '%s' instead of '%s'", defaultContentSetFromHomePage, IOWA_DEVELOPMENT.getName())),
            () -> Assertions.assertTrue(isTaxTypeColumnDisplayedForSharedContentSet,
                    "STEP 02: Tax Type column IS NOT displayed for shared content set"),
            () -> Assertions.assertEquals(TAX_TYPE_COLUMN_VALUE_WHEN_FOUR_ASSIGNMENTS, actualTaxTypeColumnValueWhenFourAssignments,
                    String.format("STEP 05: There is '%s' instead of 'TTRT1;TTRT2;TTRT3;...' in the Tax Type column", actualTaxTypeColumnValueWhenFourAssignments)),
            () -> Assertions.assertEquals(TAX_TYPE_COLUMN_VALUE_WHEN_THREE_ASSIGNMENTS, actualTaxTypeColumnValueWhenThreeAssignments,
                    String.format("STEP 07: There is '%s' instead of 'TTRT1;TTRT3;TTRT4' in the Tax Type column", actualTaxTypeColumnValueWhenThreeAssignments)),
            () -> Assertions.assertTrue(isTaxTypeColumnDisplayedForLegalSpecificContentSet,
                    "STEP 11: Tax Type column IS NOT displayed for legal specific content set")
        );

    }

    /**
     * STORY/BUG - HALCYONST-9557 <br>
     * SUMMARY -  Hierarchy Navigate column display (Risk content sets) <br>
     * USER - Risk <br>
     */
    @Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void hierarchyNavigateColumnDisplayForRiskContentSetsTest()
    {
        //STEP 01: Sign in as the risk user. The homemage content set shows 'Crown Dependencies' is selected.  This is a risk specific content set
        String defaultContentSetFromHomePage = homePage().getCurrentContentSetFromUpperRight();
        //STEP 02: Go to Hierarchy -> Navigate. VERIFY: We DO NOT see a Tax Type column
        hierarchyNavigatePage().goToHierarchyPage(CROWN_DEPENDENCIES.getCode());
        boolean isTaxTypeColumnDisplayedForSharedContentSet = siblingMetadataPage().isTaxTypeColumnDisplayed();

        Assertions.assertAll("hierarchyNavigateColumnDisplayForRiskContentSetsTest",
            () -> Assertions.assertEquals("Crown Dependencies", defaultContentSetFromHomePage,
                    String.format("STEP 01: Default  is '%s' instead of 'Crown Dependencies'", defaultContentSetFromHomePage)),
            () -> Assertions.assertFalse(isTaxTypeColumnDisplayedForSharedContentSet,
                    "STEP 02: Tax Type column IS NOT displayed for shared content set"));

    }
    @AfterEach
    public void cleanup()
    {
        if(datapodObject!=null)
        {
            datapodObject.delete();
        }
        BaseDatabaseUtils.disconnect(connection);
    }
}
