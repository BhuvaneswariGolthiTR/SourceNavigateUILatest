package com.thomsonreuters.codes.codesbench.quality.tests.source.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SourceNavigateGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.popups.TaxTypeAddPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class IntegrateTests extends TestService
{
    private final String uuidTargetNode = "IED9AE21014F311DA8AC5CD53670E6B4E";
    private final String uuidRendition = "I669F185177B311E289DBE4E232D95F35";
    private final String uuidDelta= "I6684B28277B311E289DBE4E232D95F35";
    private final String checkPoint = "CHKPNT";
    private final String taxType = "TTRT1";
    private final String completeStatus = "COMPLETE";
    private final String infoStatus = "INFO";

    /**
     * STORY/BUG - HALCYONST-10014 <br>
     * SUMMARY - Integrate a Delta from Source to Target, when Delta has Delta Feature, Tax Type and a Target Node has a Checkpoint assignment <br>
     * USER - Legal <br>
     * CONTENT TYPE - COM <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltaWithDeltaFeatureAndTaxTypeTargetNodeWithCheckpointTest()
    {
        int index = 4;

        homePage().goToHomePage();
        loginPage().logIn();
        //hierarchy: assign a CheckPoint for a Target Node
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(uuidTargetNode);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().onlineProductAssignments();
        onlineProductAssignmentsPage().rightClickProductTag(checkPoint);
        onlineProductAssignmentsContextMenu().manageOnlineProductAssignment();
        manageOnlineProductAssignmentsPage().selectAddTag();
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        onlineProductAssignmentsPage().clickCancel();
        //hierarchy: remove a Tax Type from a Target Node
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().taxTypeAssignments();
        taxTypeAssignmentsPage().rightClickProductTag(taxType);
        taxTypeAssignmentsContextMenu().manageTaxTypeAssignment();
        manageTaxTypeAssignmentsPage().selectRemoveTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        manageTaxTypeAssignmentsPage().clickSubmit();
        taxTypeAssignmentsPage().clickCancel();

        siblingMetadataPage().rightClickSelectedNode();
        siblingMetadataContextMenu().refreshSelectedNode();
        Assertions.assertEquals(checkPoint, siblingMetadataPage().getSelectedNodeProductTag(), "The Target Node DOES NOT have a Check Point");
        Assertions.assertEquals("",siblingMetadataPage().getSelectedNodeTaxType(), "The target Node HAS a Tax Type");
        //source: assign taxType
        sourcePage().goToSourcePageWithRenditionUuids(uuidRendition);
        sourceNavigateGridPage().clickFirstItem();
        sourcePage().goToDeltaTab();
        sourcePage().publicViewAllCols();
        sourceNavigateGridPage().rightClickRowByIndex(index);
        deltaContextMenu().editTaxTypeAdd();
        if(!taxTypeAddPage().doesElementExist(String.format(TaxTypeAddPageElements.SELECTED_TAX_TYPE_BY_NAME, taxType)))
        {taxTypeAddPage().selectAndMoveAvailableTaxTagAddByName(taxType);}
        taxTypeAddPage().clickSave();
        taxTypeAddPage().waitForGridRefresh();
        Assertions.assertEquals(sourceNavigateGridPage().getTaxTypeAddColumnValuesByIndexRow(3),taxType, "The Delta DOES NOT have a Tax Type");
        //source: integrate
        sourceNavigateGridPage().rightClickRowByIndex(index);
        deltaContextMenu().goToModifyResetIntegrationStatus();
        taxTypeAddPage().waitForGridRefresh();
        sourceNavigateGridPage().rightClickRowByIndex(index);
        deltaContextMenu().goToModifyIntegrate();
        taxTypeAddPage().waitForGridRefresh();
        //source: check the Location Integration Status and Message
        sourceNavigateGridPage().click(String.format(SourceNavigateGridPageElements.ITEM_LOCATION_INTEGRATION_STATUS,3));
        try
        {
            sourceNavigateGridPage().waitUntilElementsTextIsTheFollowing(String.format(SourceNavigateGridPageElements.ITEM_LOCATION_INTEGRATION_STATUS,3),infoStatus);
        }
        catch (Exception e)
        {
            sourceNavigateGridPage().refreshPage();
            String locationIntegrationStatus = sourceNavigateGridPage().getItemLocationIntegrationStatusByIndexRow(4);
            Assertions.assertEquals(completeStatus, locationIntegrationStatus,  String.format("The Location/Integration Status for the Delta (uuid = %s) is '%s'", uuidDelta, locationIntegrationStatus));
        }
        String targetStatusMessage = sourceNavigateGridPage().getItemTargetStatusMessageByIndexRow(4);
        //hierarchy: verify TaxType appears
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(uuidTargetNode);
        String taxTypeHierarchy = siblingMetadataPage().getSelectedNodeTaxType();

        Assertions.assertAll(
                ()-> Assertions.assertEquals(targetStatusMessage, "", String.format("Tharget Status message should be empty, but it IS '%s'",targetStatusMessage)),
                ()-> Assertions.assertEquals(taxTypeHierarchy,taxType, "The Target Node DOES NOT have the Tax Type")
        );
    }

    /**
     * STORY/BUG - HALCYONST-10014 <br>
     * SUMMARY - Integrate a Delta from Source to Target, when Delta has Delta Feature, Tax Type and a Target Node DOES NOT have a Checkpoint assignment <br>
     * USER - Legal <br>
     * CONTENT TYPE - COM <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltaWithDeltaFeatureAndTaxTypeTargetNodeWithoutCheckpointTest()
    {
        String expectedMessage = "No Checkpoint Product Tag on Target Document";
        int index = 4;

        homePage().goToHomePage();
        loginPage().logIn();
        //hierarchy: remove a CheckPoint for a Target Node
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(uuidTargetNode);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().onlineProductAssignments();
        onlineProductAssignmentsPage().rightClickProductTag(checkPoint);
        onlineProductAssignmentsContextMenu().manageOnlineProductAssignment();
        manageOnlineProductAssignmentsPage().selectRemoveTag();
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        onlineProductAssignmentsPage().clickCancel();
        //hierarchy: remove a Tax Type from a Target Node
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().taxTypeAssignments();
        taxTypeAssignmentsPage().rightClickProductTag(taxType);
        taxTypeAssignmentsContextMenu().manageTaxTypeAssignment();
        manageTaxTypeAssignmentsPage().selectRemoveTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        manageTaxTypeAssignmentsPage().clickSubmit();
        taxTypeAssignmentsPage().clickCancel();

        siblingMetadataPage().rightClickSelectedNode();
        siblingMetadataContextMenu().refreshSelectedNode();
        Assertions.assertEquals("", siblingMetadataPage().getSelectedNodeProductTag(), "The Target Node HAS a Check Point");
        Assertions.assertEquals("",siblingMetadataPage().getSelectedNodeTaxType(), "The target Node HAS a Tax Type");
        //source: assign taxType
        sourcePage().goToSourcePageWithRenditionUuids(uuidRendition);
        sourceNavigateGridPage().clickFirstItem();
        sourcePage().goToDeltaTab();
        sourcePage().publicViewAllCols();
        sourceNavigateGridPage().rightClickRowByIndex(index);
        deltaContextMenu().editTaxTypeAdd();
        if(!taxTypeAddPage().doesElementExist(String.format(TaxTypeAddPageElements.SELECTED_TAX_TYPE_BY_NAME, taxType)))
        {taxTypeAddPage().selectAndMoveAvailableTaxTagAddByName(taxType);}
        taxTypeAddPage().clickSave();
        taxTypeAddPage().waitForGridRefresh();
        Assertions.assertEquals(sourceNavigateGridPage().getTaxTypeAddColumnValuesByIndexRow(3),taxType, "The Delta DOES NOT have a Tax Type");
        //source: integrate
        sourceNavigateGridPage().rightClickRowByIndex(index);
        deltaContextMenu().goToModifyResetIntegrationStatus();
        taxTypeAddPage().waitForGridRefresh();
        sourceNavigateGridPage().rightClickRowByIndex(index);
        deltaContextMenu().goToModifyIntegrate();
        taxTypeAddPage().waitForGridRefresh();
        //source: check the Location Integration Status and Message
        sourceNavigateGridPage().click(String.format(SourceNavigateGridPageElements.ITEM_LOCATION_INTEGRATION_STATUS,3));
        try
        {
            sourceNavigateGridPage().waitUntilElementsTextIsTheFollowing(String.format(SourceNavigateGridPageElements.ITEM_LOCATION_INTEGRATION_STATUS,3),infoStatus);
        }
        catch (Exception e)
        {
            sourceNavigateGridPage().refreshPage();
            String locationIntegrationStatus = sourceNavigateGridPage().getItemLocationIntegrationStatusByIndexRow(4);
            Assertions.assertEquals(infoStatus, locationIntegrationStatus,  String.format("The Location/Integration Status for the Delta (uuid = %s) is '%s'", uuidDelta, locationIntegrationStatus));
        }
        String locationIntegrationStatus = sourceNavigateGridPage().getItemLocationIntegrationStatusByIndexRow(4);
        String targetStatusMessage = sourceNavigateGridPage().getItemTargetStatusMessageByIndexRow(4);
        //hierarchy: verify TaxType appears
        hierarchyMenu().goToNavigate();
        hierarchySearchPage().searchNodeUuid(uuidTargetNode);
        String taxTypeHierarchy = siblingMetadataPage().getSelectedNodeTaxType();

        Assertions.assertAll(
                ()->Assertions.assertEquals(infoStatus, locationIntegrationStatus, "The Location Integration Status is NOT INFO"),
                ()-> Assertions.assertEquals(targetStatusMessage, expectedMessage, String.format("The Target Status message should be %s', but it IS '%s'",expectedMessage, targetStatusMessage)),
                ()-> Assertions.assertEquals(taxTypeHierarchy,"", "The Target Node HAS the Tax Type")
        );
    }



}
