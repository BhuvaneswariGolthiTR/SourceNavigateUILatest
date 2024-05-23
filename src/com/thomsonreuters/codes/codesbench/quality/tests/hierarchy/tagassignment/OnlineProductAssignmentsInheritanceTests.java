package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.tagassignment;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.hierarchy.HierarchyDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodConfiguration;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets.IOWA_DEVELOPMENT;

public class OnlineProductAssignmentsInheritanceTests extends TestService
{
    private static final String EMPTY_STATUS = "";
    HierarchyDatapodObject datapodObject;
    Connection connection;

    public  String ancestorsUUID;
    public  String pastUUID;
    public  String currentUUID;
    public  String futureUUID;
    public  String descendentsUUID;
    public  String vols = "9999";
    public  String targetValue;
    public  String tag = "CHKPNT";

    /**
     * STORY/BUG - HALCYONST-9537 <br>
     * SUMMARY -  Assign Product Tags With Ancestors And Descendents and All Version <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignProductTagsWithAncestorsAndDescendentsAllVersionsTest()
    {
        //STEP 01: Find node: INSERT UUID. Create: A past effective node; A currently effective node; A future effective node
        //STEP 02: Assign a checkpoint tag on the currently effective node
        goToManageOnlineProductAssignment(currentUUID, tag);
        //STEP 03: Add tag, With Ancestors and Descendants, All Versions
        manageOnlineProductAssignmentsPage().selectAddTag();
        manageOnlineProductAssignmentsPage().selectWithAncestorsAndDescendants();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();
        //STEP 04: Verify: Workflow finishes
        String workFlowIdAferAssignmentTag = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        Assertions.assertTrue(yourWorkflowHasBeenCreatedPage().clickClose(),
                "STEP 04: Your Workflow Has Been Created window DOESN'T close");
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        Assertions.assertTrue(onlineProductAssignmentsPage().clickCancel(),
                "STEP 04: Online Product Assignments window DOESN'T close");
        boolean isWorkflowCompletedAfterAssignmentTag = verifyWorkflowFinishes(workFlowIdAferAssignmentTag);
        //STEP 05: Past, Currently, and future effective nodes are tagged
        String ancestorsUuidStatusAfterAssignmentTag = getNodeProductTag(ancestorsUUID);
        String pastUuidStatusAfterAssignmentTag = getNodeProductTag(pastUUID);
        String currentUuidStatusAfterAssignmentTag = getNodeProductTag(currentUUID);
        String futureUuidStatusAfterAssignmentTag = getNodeProductTag(futureUUID);
        String descendentsUuidStatusAfterAssignmentTag = getNodeProductTag(descendentsUUID);
        //STEP 06: Go to Tools -> Query Note Report
        toolsMenu().goToQueryNoteReport();
        //STEP 07: VERIFY: We don’t have a query note for the node value
        queryNoteReportFiltersPage().setFilterVols(vols);
        queryNoteReportFiltersPage().setFilterTargetValue(targetValue);
        queryNoteReportPage().refresh();
        int numberOfQueryNotes = queryNoteReportGridPage().getNumberOfQueryNotesInGrid();
        queryNoteReportPage().closeCurrentWindowIgnoreDialogue();

        Assertions.assertAll("assignProductTagsWithAncestorsAndDescendentsAllVersionsTest",
                () -> Assertions.assertTrue(isWorkflowCompletedAfterAssignmentTag,
                        "STEP 04: Workflow DOES NOT finish"),
                () -> Assertions.assertEquals(tag, ancestorsUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Ancestor Node Product Tag Status is '%s' instead of '%s'", ancestorsUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(tag, pastUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Past Node Product Tag Status is '%s' instead of '%s'", pastUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(tag, currentUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Current Node Product Tag Status is '%s' instead of '%s'", currentUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(tag, futureUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Future Node Product Tag Status is '%s' instead of '%s'", futureUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(tag, descendentsUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Descendent Node Product Tag Status is '%s' instead of '%s'", descendentsUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(0, numberOfQueryNotes,
                        "STEP 07: We HAVE a query note for the node value")
        );

    }

    /**
     * STORY/BUG - HALCYONST-9537 <br>
     * SUMMARY -  Assign Product Tags With Ancestors And Descendents and Current And Prospective Versions Only <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignProductTagsWithAncestorsAndDescendentsCurrentAndProspectiveVersionsOnlyTest()
    {
        //STEP 01: Find node: INSERT UUID. Create: A past effective node; A currently effective node; A future effective node
        //STEP 02: Assign a checkpoint tag on the currently effective node
        goToManageOnlineProductAssignment(currentUUID, tag);
        //STEP 03: Add tag, With Ancestors and Descendants, Current and Prospective Versions Only
        manageOnlineProductAssignmentsPage().selectAddTag();
        manageOnlineProductAssignmentsPage().selectWithAncestorsAndDescendants();
        manageOnlineProductAssignmentsPage().selectCurrentAndProspectiveVersionsOnly();
        manageOnlineProductAssignmentsPage().clickSubmit();
        yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();
        //STEP 04: Verify: Workflow finishes
        String workFlowIdAferAssignmentTag = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        Assertions.assertTrue(yourWorkflowHasBeenCreatedPage().clickClose(),
                "STEP 04: Your Workflow Has Been Created window DOESN'T close");
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        Assertions.assertTrue(onlineProductAssignmentsPage().clickCancel(),
                "STEP 04: Online Product Assignments window DOESN'T close");
        boolean isWorkflowCompletedAfterAssignmentTag = verifyWorkflowFinishes(workFlowIdAferAssignmentTag);
        //STEP 05: Past, Currently, and future effective nodes are tagged
        String ancestorsUuidStatusAfterAssignmentTag = getNodeProductTag(ancestorsUUID);
        String pastUuidStatusAfterAssignmentTag = getNodeProductTag(pastUUID);
        String currentUuidStatusAfterAssignmentTag = getNodeProductTag(currentUUID);
        String futureUuidStatusAfterAssignmentTag = getNodeProductTag(futureUUID);
        String descendentsUuidStatusAfterAssignmentTag = getNodeProductTag(descendentsUUID);
        //STEP 06: Go to Tools -> Query Note Report
        toolsMenu().goToQueryNoteReport();
        //STEP 07: VERIFY: We don’t have a query note for the node value
        queryNoteReportFiltersPage().setFilterVols(vols);
        queryNoteReportFiltersPage().setFilterTargetValue(targetValue);
        queryNoteReportPage().refresh();
        int numberOfQueryNotes = queryNoteReportGridPage().getNumberOfQueryNotesInGrid();
        queryNoteReportPage().closeCurrentWindowIgnoreDialogue();

        Assertions.assertAll("assignProductTagsWithAncestorsAndDescendentsCurrentAndProspectiveVersionsOnlyTest",
                () -> Assertions.assertTrue(isWorkflowCompletedAfterAssignmentTag,
                        "STEP 04: Workflow DOES NOT finish"),
                () -> Assertions.assertEquals(tag, ancestorsUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Ancestor Node Product Tag Status is '%s' instead of '%s'", ancestorsUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(EMPTY_STATUS, pastUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Past Node Product Tag Status is '%s' instead of '%s'", pastUuidStatusAfterAssignmentTag, EMPTY_STATUS)),
                () -> Assertions.assertEquals(tag, currentUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Current Node Product Tag Status is '%s' instead of '%s'", currentUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(tag, futureUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Future Node Product Tag Status is '%s' instead of '%s'", futureUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(tag, descendentsUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Descendent Node Product Tag Status is '%s' instead of '%s'", descendentsUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(0, numberOfQueryNotes,
                        "STEP 07: We HAVE a query note for the node value")
        );

    }

    /**
     * STORY/BUG - HALCYONST-9537 <br>
     * SUMMARY -  Assign Product Tags With Descendents and All Version <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignProductTagsWithDescendentsOnlyAllVersionsTest()
    {
        //STEP 01: Find node: INSERT UUID. Create: A past effective node; A currently effective node; A future effective node
        //STEP 02: Assign a checkpoint tag on the currently effective node
        goToManageOnlineProductAssignment(currentUUID, tag);
        //STEP 03: Add tag, With Descendents Only, All Versions
        manageOnlineProductAssignmentsPage().selectAddTag();
        manageOnlineProductAssignmentsPage().selectWithDescendantsOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();
        //STEP 04: Verify: Workflow finishes
        String workFlowIdAferAssignmentTag = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        Assertions.assertTrue(yourWorkflowHasBeenCreatedPage().clickClose(),
                "STEP 04: Your Workflow Has Been Created window DOESN'T close");
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        Assertions.assertTrue(onlineProductAssignmentsPage().clickCancel(),
                "STEP 04: Online Product Assignments window DOESN'T close");
        boolean isWorkflowCompletedAfterAssignmentTag = verifyWorkflowFinishes(workFlowIdAferAssignmentTag);
        //STEP 05: Past, Currently, and future effective nodes are tagged
        String ancestorsUuidStatusAfterAssignmentTag = getNodeProductTag(ancestorsUUID);
        String pastUuidStatusAfterAssignmentTag = getNodeProductTag(pastUUID);
        String currentUuidStatusAfterAssignmentTag = getNodeProductTag(currentUUID);
        String futureUuidStatusAfterAssignmentTag = getNodeProductTag(futureUUID);
        String descendentsUuidStatusAfterAssignmentTag = getNodeProductTag(descendentsUUID);
        //STEP 06: Go to Tools -> Query Note Report
        toolsMenu().goToQueryNoteReport();
        //STEP 07: VERIFY: We don’t have a query note for the node value
        queryNoteReportFiltersPage().setFilterVols(vols);
        queryNoteReportFiltersPage().setFilterTargetValue(targetValue);
        queryNoteReportPage().refresh();
        int numberOfQueryNotes = queryNoteReportGridPage().getNumberOfQueryNotesInGrid();
        queryNoteReportPage().closeCurrentWindowIgnoreDialogue();

        Assertions.assertAll("assignProductTagsWithDescendentsOnlyAllVersionsTest",
                () -> Assertions.assertTrue(isWorkflowCompletedAfterAssignmentTag,
                        "STEP 04: Workflow DOES NOT finish"),
                () -> Assertions.assertEquals(EMPTY_STATUS, ancestorsUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Ancestor Node Product Tag Status is '%s' instead of '%s'", ancestorsUuidStatusAfterAssignmentTag, EMPTY_STATUS)),
                () -> Assertions.assertEquals(tag, pastUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Past Node Product Tag Status is '%s' instead of '%s'", pastUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(tag, currentUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Current Node Product Tag Status is '%s' instead of '%s'", currentUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(tag, futureUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Future Node Product Tag Status is '%s' instead of '%s'", futureUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(tag, descendentsUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Descendent Node Product Tag Status is '%s' instead of '%s'", descendentsUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(0, numberOfQueryNotes,
                        "STEP 07: We HAVE a query note for the node value")
        );

    }

    /**
     * STORY/BUG - HALCYONST-9537 <br>
     * SUMMARY -  Assign Product Tags With Descendents and Current And Prospective Versions Only <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignProductTagsWithDescendentsOnlyCurrentAndProspectiveVersionsOnlyTest()
    {
        //STEP 01: Find node: INSERT UUID. Create: A past effective node; A currently effective node; A future effective node
        //STEP 02: Assign a checkpoint tag on the currently effective node
        goToManageOnlineProductAssignment(currentUUID, tag);
        //STEP 03: Add tag, With Descendents Only, Current and Prospective Versions Only
        manageOnlineProductAssignmentsPage().selectAddTag();
        manageOnlineProductAssignmentsPage().selectWithDescendantsOnly();
        manageOnlineProductAssignmentsPage().selectCurrentAndProspectiveVersionsOnly();
        manageOnlineProductAssignmentsPage().clickSubmit();
        yourWorkflowHasBeenCreatedPage().switchToYourWorkflowHasBeenCreatedPage();
        //STEP 04: Verify: Workflow finishes
        String workFlowIdAferAssignmentTag = yourWorkflowHasBeenCreatedPage().getWorkflowId();
        Assertions.assertTrue(yourWorkflowHasBeenCreatedPage().clickClose(),
                "STEP 04: Your Workflow Has Been Created window DOESN'T close");
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        Assertions.assertTrue(onlineProductAssignmentsPage().clickCancel(),
                "STEP 04: Online Product Assignments window DOESN'T close");
        boolean isWorkflowCompletedAfterAssignmentTag = verifyWorkflowFinishes(workFlowIdAferAssignmentTag);
        //STEP 05: Past, Currently, and future effective nodes are tagged
        String ancestorsUuidStatusAfterAssignmentTag = getNodeProductTag(ancestorsUUID);
        String pastUuidStatusAfterAssignmentTag = getNodeProductTag(pastUUID);
        String currentUuidStatusAfterAssignmentTag = getNodeProductTag(currentUUID);
        String futureUuidStatusAfterAssignmentTag = getNodeProductTag(futureUUID);
        String descendentsUuidStatusAfterAssignmentTag = getNodeProductTag(descendentsUUID);
        //STEP 06: Go to Tools -> Query Note Report
        toolsMenu().goToQueryNoteReport();
        //STEP 07: VERIFY: We don’t have a query note for the node value
        queryNoteReportFiltersPage().setFilterVols(vols);
        queryNoteReportFiltersPage().setFilterTargetValue(targetValue);
        queryNoteReportPage().refresh();
        int numberOfQueryNotes = queryNoteReportGridPage().getNumberOfQueryNotesInGrid();
        queryNoteReportPage().closeCurrentWindowIgnoreDialogue();

        Assertions.assertAll("assignProductTagsWithDescendentsOnlyCurrentAndProspectiveVersionsOnlyTest",
                () -> Assertions.assertTrue(isWorkflowCompletedAfterAssignmentTag,
                        "STEP 04: Workflow DOES NOT finish"),
                () -> Assertions.assertEquals(EMPTY_STATUS, ancestorsUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Ancestor Node Product Tag Status is '%s' instead of '%s'", ancestorsUuidStatusAfterAssignmentTag, EMPTY_STATUS)),
                () -> Assertions.assertEquals(EMPTY_STATUS, pastUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Past Node Product Tag Status is '%s' instead of '%s'", pastUuidStatusAfterAssignmentTag, EMPTY_STATUS)),
                () -> Assertions.assertEquals(tag, currentUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Current Node Product Tag Status is '%s' instead of '%s'", currentUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(tag, futureUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Future Node Product Tag Status is '%s' instead of '%s'", futureUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(tag, descendentsUuidStatusAfterAssignmentTag,
                        String.format("STEP 05: Descendent Node Product Tag Status is '%s' instead of '%s'", descendentsUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(0, numberOfQueryNotes,
                        "STEP 07: We HAVE a query note for the node value")
        );

    }

    /**
     * STORY/BUG - HALCYONST-9537 <br>
     * SUMMARY -  Assign Product Tags With Single Node Only and All Version <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignProductTagsWithSingleNodeOnlyAndAllVersionsTest()
    {
        //STEP 01: Find node: INSERT UUID. Create: A past effective node; A currently effective node; A future effective node
        //STEP 02: Assign a checkpoint tag on the currently effective node
        goToManageOnlineProductAssignment(currentUUID, tag);
        //STEP 03: Add tag, Single Node Only, All Versions
        manageOnlineProductAssignmentsPage().selectAddTag();
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        Assertions.assertTrue(onlineProductAssignmentsPage().clickCancel(),
                "STEP 03: Online Product Assignments window DOESN'T close");
        //STEP 04: Past, Currently, and future effective nodes are tagged
        String ancestorsUuidStatusAfterAssignmentTag = getNodeProductTag(ancestorsUUID);
        String pastUuidStatusAfterAssignmentTag = getNodeProductTag(pastUUID);
        String currentUuidStatusAfterAssignmentTag = getNodeProductTag(currentUUID);
        String futureUuidStatusAfterAssignmentTag = getNodeProductTag(futureUUID);
        String descendentsUuidStatusAfterAssignmentTag = getNodeProductTag(descendentsUUID);
        //STEP 05: Go to Tools -> Query Note Report
        toolsMenu().goToQueryNoteReport();
        //STEP 06: VERIFY: We don’t have a query note for the node value
        queryNoteReportFiltersPage().setFilterVols(vols);
        queryNoteReportFiltersPage().setFilterTargetValue(targetValue);
        queryNoteReportPage().refresh();
        int numberOfQueryNotes = queryNoteReportGridPage().getNumberOfQueryNotesInGrid();
        queryNoteReportPage().closeCurrentWindowIgnoreDialogue();

        Assertions.assertAll("assignProductTagsWithSingleNodeOnlyAndAllVersionsTest",
                () -> Assertions.assertEquals(EMPTY_STATUS, ancestorsUuidStatusAfterAssignmentTag,
                        String.format("STEP 04: Ancestor Node Product Tag Status is '%s' instead of '%s'", ancestorsUuidStatusAfterAssignmentTag, EMPTY_STATUS)),
                () -> Assertions.assertEquals(EMPTY_STATUS, pastUuidStatusAfterAssignmentTag,
                        String.format("STEP 04: Past Node Product Tag Status is '%s' instead of '%s'", pastUuidStatusAfterAssignmentTag, EMPTY_STATUS)),
                () -> Assertions.assertEquals(tag, currentUuidStatusAfterAssignmentTag,
                        String.format("STEP 04: Current Node Product Tag Status is '%s' instead of '%s'", currentUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(tag, futureUuidStatusAfterAssignmentTag,
                        String.format("STEP 04: Future Node Product Tag Status is '%s' instead of '%s'", futureUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(EMPTY_STATUS, descendentsUuidStatusAfterAssignmentTag,
                        String.format("STEP 04: Descendent Node Product Tag Status is '%s' instead of '%s'", descendentsUuidStatusAfterAssignmentTag, EMPTY_STATUS)),
                () -> Assertions.assertEquals(0, numberOfQueryNotes,
                        "STEP 06: We HAVE a query note for the node value")
        );

    }

    /**
     * STORY/BUG - HALCYONST-9537 <br>
     * SUMMARY -  Assign Product Tags With Single Node Only and Current And Prospective Versions Only  <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void assignProductTagsWithSingleNodeOnlyAndCurrentAndProspectiveVersionsOnlyTest()
    {
        //STEP 01: Find node: INSERT UUID. Create: A past effective node; A currently effective node; A future effective node
        //STEP 02: Assign a checkpoint tag on the currently effective node
        goToManageOnlineProductAssignment(currentUUID, tag);
        //STEP 03: Add tag, Single Node Only, Current and Prospective Versions Only
        manageOnlineProductAssignmentsPage().selectAddTag();
        manageOnlineProductAssignmentsPage().selectSingleNodeOnly();
        manageOnlineProductAssignmentsPage().selectCurrentAndProspectiveVersionsOnly();
        manageOnlineProductAssignmentsPage().clickSubmit();
        onlineProductAssignmentsPage().switchToOnlineProductAssignmentsPage();
        Assertions.assertTrue(onlineProductAssignmentsPage().clickCancel(),
                "STEP 03: Online Product Assignments window DOESN'T close");
        //STEP 04: Past, Currently, and future effective nodes are tagged
        String ancestorsUuidStatusAfterAssignmentTag = getNodeProductTag(ancestorsUUID);
        String pastUuidStatusAfterAssignmentTag = getNodeProductTag(pastUUID);
        String currentUuidStatusAfterAssignmentTag = getNodeProductTag(currentUUID);
        String futureUuidStatusAfterAssignmentTag = getNodeProductTag(futureUUID);
        String descendentsUuidStatusAfterAssignmentTag = getNodeProductTag(descendentsUUID);
        //STEP 05: Go to Tools -> Query Note Report
        toolsMenu().goToQueryNoteReport();
        //STEP 06: VERIFY: We don’t have a query note for the node value
        queryNoteReportFiltersPage().setFilterVols(vols);
        queryNoteReportFiltersPage().setFilterTargetValue(targetValue);
        queryNoteReportPage().refresh();
        int numberOfQueryNotes = queryNoteReportGridPage().getNumberOfQueryNotesInGrid();
        queryNoteReportPage().closeCurrentWindowIgnoreDialogue();

        Assertions.assertAll("assignProductTagsWithSingleNodeOnlyAndCurrentAndProspectiveVersionsOnlyTest",
                () -> Assertions.assertEquals(EMPTY_STATUS, ancestorsUuidStatusAfterAssignmentTag,
                        String.format("STEP 04: Ancestor Node Product Tag Status is '%s' instead of '%s'", ancestorsUuidStatusAfterAssignmentTag, EMPTY_STATUS)),
                () -> Assertions.assertEquals(EMPTY_STATUS, pastUuidStatusAfterAssignmentTag,
                        String.format("STEP 04: Past Node Product Tag Status is '%s' instead of '%s'", pastUuidStatusAfterAssignmentTag, EMPTY_STATUS)),
                () -> Assertions.assertEquals(tag, currentUuidStatusAfterAssignmentTag,
                        String.format("STEP 04: Current Node Product Tag Status is '%s' instead of '%s'", currentUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(tag, futureUuidStatusAfterAssignmentTag,
                        String.format("STEP 04: Future Node Product Tag Status is '%s' instead of '%s'", futureUuidStatusAfterAssignmentTag, tag)),
                () -> Assertions.assertEquals(EMPTY_STATUS, descendentsUuidStatusAfterAssignmentTag,
                        String.format("STEP 04: Descendent Node Product Tag Status is '%s' instead of '%s'", descendentsUuidStatusAfterAssignmentTag, EMPTY_STATUS)),
                () -> Assertions.assertEquals(0, numberOfQueryNotes,
                        "STEP 06: We HAVE a query note for the node value")
        );

    }

    @BeforeEach
    public void beforeEach()
    {
        HierarchyDatapodConfiguration.getConfig().setNumberOfPastChapterVersions(3);
        HierarchyDatapodConfiguration.getConfig().setNumberOfFutureChapterVersions(3);

        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        connection = HierarchyDatabaseUtils.connectToDatabaseUAT();

        ancestorsUUID = datapodObject.getSubtitles().get(0).getNodeUUID();
        pastUUID = datapodObject.getChapters().get(2).getNodeUUID();
        currentUUID = datapodObject.getChapters().get(3).getNodeUUID();
        futureUUID = datapodObject.getChapters().get(4).getNodeUUID();
        descendentsUUID = datapodObject.getSections().get(4).getNodeUUID();
        targetValue = HierarchyDatabaseUtils.getNodeValue(datapodObject.getSections().get(1).getContentUUID(),IOWA_DEVELOPMENT.getCode(),connection);


        homePage().goToHomePage();
        loginPage().logIn();
        hierarchyNavigatePage().goToHierarchyPage(IOWA_DEVELOPMENT.getCode());

        Assertions.assertEquals(EMPTY_STATUS, getNodeProductTag(ancestorsUUID),"Ancestor UUID Product Tag is not Empty");
        Assertions.assertEquals(EMPTY_STATUS, getNodeProductTag(pastUUID),"Past UUID Product Tag is not Empty ");
        Assertions.assertEquals(EMPTY_STATUS, getNodeProductTag(currentUUID), "Current UUID Product Tag is not Empty");
        Assertions.assertEquals(EMPTY_STATUS, getNodeProductTag(futureUUID),"Future UUID Product Tag is not Empty");
        Assertions.assertEquals(EMPTY_STATUS, getNodeProductTag(descendentsUUID),"Descendents UUID Product Tag is not Empty");
    }

    private void updateCurrentAndFutureNodeDates(String UUID)
    {
        hierarchySearchPage().searchNodeUuid(UUID);
        dispositionDerivationPage().rightClickSelectNodeByNumber("2");
        dispositionDerivationContextMenu().updateMetadata();
        updateMetadataPage().enterEffectiveEndDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        updateMetadataPage().clickQuickLoadOk();
        dispositionDerivationPage().rightClickSelectNodeByNumber("3");
        dispositionDerivationContextMenu().updateMetadata();
        updateMetadataPage().enterEffectiveStartDate(DateAndTimeUtils.getTomorrowDateMMddyyyy());
        updateMetadataPage().clickQuickLoadOk();
        dispositionDerivationPage().clickCommit();
        setLawTrackingPage().switchToSetLawTrackingPage();
        setLawTrackingPage().clickQuickLoadTrackingButton();
        setLawTrackingPage().clickOkButton();
        hierarchyNavigatePage().switchToHierarchyEditPage();
    }

    private void goToManageOnlineProductAssignment(String UUID, String tag)
    {
        hierarchySearchPage().searchNodeUuid(UUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().onlineProductAssignments();
        onlineProductAssignmentsPage().rightClickProductTag(tag);
        onlineProductAssignmentsContextMenu().manageOnlineProductAssignment();
    }

    private boolean verifyWorkflowFinishes(String workFlowId)
    {
        toolsMenu().goToWorkflowReportingSystem();
        workflowSearchPage().setWorkflowID(workFlowId);
        workflowSearchPage().clickFilterButton();
        boolean isWorkflowCompleted = workflowSearchPage().checkFirstWorkflowFinishedTenMinutes();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        hierarchyNavigatePage().switchToHierarchyEditPage();
        return isWorkflowCompleted;
    }

    private String getNodeProductTag(String UUID)
    {
        hierarchySearchPage().searchNodeUuid(UUID);
        return siblingMetadataPage().getSelectedNodeProductTag();
    }

    private void removeTag()
    {
        manageOnlineProductAssignmentsPage().selectRemoveTag();
        manageOnlineProductAssignmentsPage().selectWithAncestorsAndDescendants();
        manageOnlineProductAssignmentsPage().selectAllVersions();
        manageOnlineProductAssignmentsPage().clickSubmit();
    }

    @AfterEach
    public void cleanup()
    {
        if(datapodObject !=null)
        {
            datapodObject.delete();
        }
    }

}
