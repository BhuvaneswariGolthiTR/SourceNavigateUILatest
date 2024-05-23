package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.util.ArrayList;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class InsertInHierarchyTests extends TestService
{

    HierarchyDatapodObject datapodObject;

    @BeforeEach
    public void loginAndGoToHierarchyPage()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());
    }

    /**
     * STORY/BUG - HALCYONST-10655 <br>
     * SUMMARY - This test verifies that Tax Types are not inherited by newly added nodes based on sibling or parent assignments <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertInHierarchyTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String value = "Test";

        hierarchySearchPage().searchNodeUuid(nodeUUID);

        assignedTaxTypeForAllNodes();

        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedNode();
        siblingMetadataContextMenu().hierarchyFunctionsInsertInHierarchy();
        insertNewNodesPage().setValueByRowNumber(value, 1);
        insertNewNodesPage().setEffectiveStartDateOfGivenRow("01/05/2017", 1);
        insertNewNodesPage().clickQuickLoad();
        insertNewNodesPage().clickOk();

        Assertions.assertTrue(siblingMetadataPage().isNodeDisplayedWithValue(value));
        siblingMetadataPage().selectNodesByValue(value);
        String taxType = siblingMetadataPage().getSelectedNodeTaxType();

        Assertions.assertAll
        (
            () -> Assertions.assertEquals(taxType,"", "New node has some Tax Type.")
        );
    }

    /**
     * STORY/BUG - HALCYONST-10655/HALCYONST-11920 <br>
     * SUMMARY - This test verifies that Tax Types are not inherited by newly added nodes based on sibling or parent assignments (if all siblings has Tax Type assignment) <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void InsertInHierarchyWithTwoLevelsTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String value1 = "Test1";
        String value2 = "Test2";
        String onlineProduct = "CHKPNT";

        hierarchySearchPage().searchNodeUuid(nodeUUID);

        assignedTaxTypeForAllNodes();
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        assignedOnlineProductForAllNodes(onlineProduct);

        siblingMetadataPage().refreshPage();
        siblingMetadataPage().rightClickSelectedNode();
        siblingMetadataContextMenu().hierarchyFunctionsInsertInHierarchy();
        insertNewNodesPage().setValueByRowNumber(value1, 1);
        insertNewNodesPage().setEffectiveStartDateOfGivenRow("01/05/2017", 1);
        insertNewNodesPage().sendKeys(Keys.ENTER);
        insertNewNodesPage().setNewNodeDepthByRowNumber("1",2);
        insertNewNodesPage().setValueByRowNumber(value2, 2);
        insertNewNodesPage().clickQuickLoad();
        insertNewNodesPage().clickOk();

        Assertions.assertTrue(siblingMetadataPage().isNodeDisplayedWithValue(value1));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        hierarchySearchPage().quickSearch("=", value1);
        String taxType1 = siblingMetadataPage().getSelectedNodeTaxType();
        String onlineProduct1 = siblingMetadataPage().getSelectedNodeProductTag();

        hierarchySearchPage().quickSearch("=", value2);
        String taxType2 = siblingMetadataPage().getSelectedNodeTaxType();
        String onlineProduct2 = siblingMetadataPage().getSelectedNodeProductTag();

        Assertions.assertAll(
                () -> Assertions.assertEquals(taxType1,"", "New node has some Tax Type."),
                () -> Assertions.assertEquals(onlineProduct1,onlineProduct, String.format("The sibling node has '%s' product tag instead of '%s'",onlineProduct1,onlineProduct)),
                () -> Assertions.assertEquals(taxType2,"", "New node has some Tax Type."),
                () -> Assertions.assertEquals(onlineProduct2,onlineProduct, String.format("The sibling node has '%s' product tag instead of '%s'",onlineProduct2,onlineProduct))
        );
    }

    /**
     * STORY/BUG - HALCYONST-12939<br>
     * SUMMARY - New node does not inherit Checkpoint Product Tag, but gets Product Tag query. No CHKP pubtag script or query (when node would ordinarily get script and query with traditional pubtag script rules)<br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertInHierarchyWithPartialCheckpointTagAndScriptTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(0);
        HierarchyDatapodConfiguration.getConfig().setGradeCount(1);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getGrades().get(0).getNodeUUID();
        String value = "TestPartial";
        String scriptTag = "CHKP";
        String onlineProduct = "CHKPNT";

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        assignedOnlineProductForAllNodes(onlineProduct);
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        assignedScriptsForAllNodes(scriptTag);
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        removeOnlineProduct(onlineProduct);
        removeScript(scriptTag);
        siblingMetadataPage().rightClickSelectedNode();

        siblingMetadataContextMenu().hierarchyFunctionsInsertInHierarchy();
        insertNewNodesPage().setValueByRowNumber(value, 1);
        insertNewNodesPage().setNodeTypeofGivenRow("SECTION", 0);
        insertNewNodesPage().setEffectiveStartDateOfGivenRow("01/05/2017", 1);
        insertNewNodesPage().clickQuickLoad();
        insertNewNodesPage().clickOk();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        Assertions.assertTrue(siblingMetadataPage().isNodeDisplayedWithValue(value));
        String productTag = siblingMetadataPage().getSelectedNodeProductTag();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsViewRemoveAssignedScripts();
        boolean isScriptAssigned = removeAssignedScriptsPage().isScriptWithGivenPubTagDisplayed(scriptTag);
        removeAssignedScriptsPage().clickClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        toolsMenu().goToQueryNoteReport();
        queryNoteReportFiltersPage().setFilterTargetValue(value);
        queryNoteReportFiltersPage().setFilterStatus("ACTIVE");
        queryNotePage().clickRefreshButton();
        int numberOfQueryNotes =  queryNoteReportGridPage().getNumberOfQueryNotesInGrid();
        queryNotePage().switchToHierarchyEditWindow();

        Assertions.assertAll(
                () -> Assertions.assertEquals("",productTag),
                () -> Assertions.assertFalse(isScriptAssigned),
                () -> Assertions.assertEquals(2,numberOfQueryNotes)
        );

    }

    /**
     * STORY/BUG - HALCYONST-12939<br>
     * SUMMARY -  New node inherits Checkpoint Product Tag because all siblings, also gets Script <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertInHierarchyWithAllCheckpointTagAndScriptTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(0);
        HierarchyDatapodConfiguration.getConfig().setGradeCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getGrades().get(0).getNodeUUID();
        String value = "TestAll";
        String scriptTag = "CHKP";
        String expectedProductTag = "CHKPNT";

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        assignedOnlineProductForAllNodes(expectedProductTag);
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        assignedScriptsForAllNodes(scriptTag);
        siblingMetadataPage().rightClickSelectedNode();

        siblingMetadataContextMenu().hierarchyFunctionsInsertInHierarchy();
        insertNewNodesPage().setValueByRowNumber(value, 1);
        insertNewNodesPage().setNodeTypeofGivenRow("SECTION", 0);
        insertNewNodesPage().setEffectiveStartDateOfGivenRow("01/05/2017", 1);
        insertNewNodesPage().clickQuickLoad();
        insertNewNodesPage().clickOk();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        Assertions.assertTrue(siblingMetadataPage().isNodeDisplayedWithValue(value));
        String actualProductTag = siblingMetadataPage().getSelectedNodeProductTag();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsViewRemoveAssignedScripts();
        boolean isScriptAssigned = removeAssignedScriptsPage().isScriptWithGivenPubTagDisplayed(scriptTag);
        removeAssignedScriptsPage().clickClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        toolsMenu().goToQueryNoteReport();
        queryNoteReportFiltersPage().setFilterTargetValue(value);
        queryNoteReportFiltersPage().setFilterStatus("ACTIVE");
        queryNotePage().clickRefreshButton();
        int numberOfQueryNotes =  queryNoteReportGridPage().getNumberOfQueryNotesInGrid();
        queryNotePage().switchToHierarchyEditWindow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedProductTag,actualProductTag),
                () -> Assertions.assertTrue(isScriptAssigned),
                () -> Assertions.assertEquals(0,numberOfQueryNotes)
        );

    }

    /**
     * STORY/BUG - HALCYONST-12939<br>
     * SUMMARY -  New node inherits Checkpoint Product Tag because all siblings, also gets Script <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertInHierarchyFirstChildTest()
    {
        HierarchyDatapodConfiguration.getConfig().setSectionCount(0);
        HierarchyDatapodConfiguration.getConfig().setGradeCount(2);
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUuid = datapodObject.getGrades().get(0).getNodeUUID();
        String parentNodeUuids = datapodObject.getChapters().get(0).getNodeUUID();
        String value = "TestFirst";
        String scriptTag = "CHKP";
        String expectedProductTag = "CHKPNT";

        hierarchySearchPage().searchNodeUuid(nodeUuid);
        assignedOnlineProductForAllNodes(expectedProductTag);
        hierarchySearchPage().searchNodeUuid(nodeUuid);
        assignedScriptsForAllNodes(scriptTag);
        hierarchySearchPage().searchNodeUuid(parentNodeUuids);
        siblingMetadataPage().rightClickSelectedNode();

        siblingMetadataContextMenu().hierarchyFunctionsInsertInHierarchy();
        insertNewNodesPage().setNewNodeDepthByRowNumber("1", 1);
        insertNewNodesPage().setValueByRowNumber(value, 1);
        insertNewNodesPage().setNodeTypeofGivenRow("SECTION", 0);
        insertNewNodesPage().setEffectiveStartDateOfGivenRow("01/05/2017", 1);
        insertNewNodesPage().clickQuickLoad();
        insertNewNodesPage().clickOk();

        hierarchyNavigatePage().switchToHierarchyEditPage();
        Assertions.assertTrue(siblingMetadataPage().isNodeDisplayedWithValue(value));
        String actualProductTag = siblingMetadataPage().getSelectedNodeProductTag();
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().scriptsViewRemoveAssignedScripts();
        boolean isScriptAssigned = removeAssignedScriptsPage().isScriptWithGivenPubTagDisplayed(scriptTag);
        removeAssignedScriptsPage().clickClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();

        toolsMenu().goToQueryNoteReport();
        queryNoteReportFiltersPage().setFilterTargetValue(value);
        queryNoteReportFiltersPage().setFilterStatus("ACTIVE");
        queryNotePage().clickRefreshButton();
        int numberOfQueryNotes =  queryNoteReportGridPage().getNumberOfQueryNotesInGrid();
        queryNotePage().switchToHierarchyEditWindow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedProductTag,actualProductTag),
                () -> Assertions.assertTrue(isScriptAssigned),
                () -> Assertions.assertEquals(0,numberOfQueryNotes)
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

    private void assignedTaxTypeForAllNodes()
    {
        siblingMetadataPage().selectAllNodes();
        siblingMetadataPage().rightClickSiblingMetadata();
        siblingMetadataContextMenu().taxTypeAssignments();
        taxTypeAssignmentsPage().rightClickProductTag("TTRT1");
        taxTypeAssignmentsContextMenu().manageTaxTypeAssignment();
        manageTaxTypeAssignmentsPage().selectAddTag();
        manageTaxTypeAssignmentsPage().selectAllVersions();
        manageTaxTypeAssignmentsPage().clickSubmit();
        taxTypeAssignmentsPage().switchToTaxTypeAssignmentsPage();
        taxTypeAssignmentsPage().clickCancel();
    }

    private void assignedOnlineProductForAllNodes(String onlineProduct)
    {
        siblingMetadataPage().selectAllNodes();
        siblingMetadataPage().rightClickSiblingMetadata();
        siblingMetadataContextMenu().onlineProductAssignments();
        onlineProductAssignmentsPage().rightClickProductTag(onlineProduct);
        onlineProductAssignmentsContextMenu().manageOnlineProductAssignment();
        manageOnlineProductAssignmentsPage().selectAddTag();
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        onlineProductAssignmentsPage().clickCancel();
    }

    private void assignedScriptsForAllNodes(String scripts)
    {
        siblingMetadataPage().selectAllNodes();
        siblingMetadataPage().rightClickSiblingMetadata();
        siblingMetadataContextMenu().scriptsAddToScript();
        addAssignedScriptsPage().rightClickScriptWithGivenPubTagValue(scripts);
        addAssignedScriptsContextMenu().assignSingle();
        boolean workflowStarted = yourWorkflowHasBeenCreatedPage().workflowHasStarted();
        Assertions.assertTrue(workflowStarted, "The workflow didn't started when it should have");
        yourWorkflowHasBeenCreatedPage().clickClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();
    }

    private void removeScript(String scripts)
    {
        siblingMetadataPage().rightClickSiblingMetadata();
        siblingMetadataContextMenu().scriptsViewRemoveAssignedScripts();
        removeAssignedScriptsPage().rightClickScriptWithGivenPubTagValue(scripts);
        removeAssignedScriptsContextMenu().removeSingle();
        boolean workflowStarted = yourWorkflowHasBeenCreatedPage().workflowHasStarted();
        Assertions.assertTrue(workflowStarted, "The workflow didn't started when it should have");
        yourWorkflowHasBeenCreatedPage().clickClose();
        hierarchyNavigatePage().switchToHierarchyEditPage();
    }

    private void removeOnlineProduct(String onlineProduct)
    {
        siblingMetadataPage().rightClickSiblingMetadata();
        siblingMetadataContextMenu().onlineProductAssignments();
        onlineProductAssignmentsPage().rightClickProductTag(onlineProduct);
        onlineProductAssignmentsContextMenu().manageOnlineProductAssignment();
        manageOnlineProductAssignmentsPage().selectRemoveTag();
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        onlineProductAssignmentsPage().clickCancel();
    }
}
