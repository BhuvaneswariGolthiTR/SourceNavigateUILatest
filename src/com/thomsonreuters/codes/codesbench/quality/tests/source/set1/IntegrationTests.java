package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.ClamshellPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class IntegrationTests extends TestService
{
    Connection connection;
    String renditionUuid;
    String username;
    /**
     * STORY: HALCYONST-895 <br>
     * SUMMARY: integrates a subsection of a delta and then checks it status <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrateTextOfSubsectionDeltaTest()
    {
        String contentSet = "Iowa (Development)";
        String renditionStatus = "PREP";
        String docNumber = "2247";

        String deltaLevel = "SUBSECTION";

        String[] needsCiteLocate = {"Needs cite locate"};
        String[] inProcess = {"IN PROCESS Node Splitter"};
        String[] warnOrCompleted = {"WARN", "COMPLETED"};

        String workflowType = "CwbSourceIntegrate";
        String workflowContentSet = "106";
        String workflowDescription = "Source Integrate";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();

        sourceNavigateFiltersAndSortsPage().setFilterDeltaLevel(deltaLevel);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToModifyResetIntegrationStatus();

        boolean integrationRest = sourceNavigateGridPage().selectedDeltaHasOneOfTheIntStatuses(needsCiteLocate);
        Assertions.assertTrue(integrationRest, "Integration status is Needs cite locate now");

        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToModifyIntegrate();

        boolean integrationStarted = sourceNavigateGridPage().selectedDeltaHasOneOfTheIntStatuses(inProcess);
        Assertions.assertTrue(integrationStarted, "Integration status is IN PROCESS now");

        toolsMenu().goToWorkflowReportingSystem();
        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, workflowContentSet, workflowDescription);
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowFinished, String.format("Workflow %s has been marked as Finished in workflow Reporting", workflowID));

        String workflowDateTime = workflowSearchPage().getWorkflowModifiedDate();
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToDeltaNavigatePage();

        sourceNavigateFooterToolsPage().refreshTheGrid();
        boolean integrationCompletedOrWarning = sourceNavigateGridPage().selectedDeltaHasOneOfTheIntStatuses(warnOrCompleted);
        Assertions.assertTrue(integrationCompletedOrWarning, "Integration status is WARN or COMPLETED now");

        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToViewTargetInHierarchy();
        String hierarchyDateTime = hierarchyNavigatePage().getSelectedModifiedDate();

        boolean dateOfModifyingIsCorrect = DateAndTimeUtils.isDateTimesEqual(workflowDateTime, "MM/dd/yyyy HH:mm:ss", "GMT",
                hierarchyDateTime, "MM/dd/yyyy HH:mm", "America/Chicago", DateAndTimeUtils.ONE_MINUTE);
        Assertions.assertTrue(dateOfModifyingIsCorrect, "Date of Modifying is Correct in Hierarchy");
    }
    /**
     * STORY: HALCYONST-896 <br>
     * SUMMARY: Integrates a subsection of a delta and then checks it status <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void insertIntoHierarchyWizardTest()
    {
        String contentSet = "Iowa (Development)";
        String renditionStatus = "PREP";
        String docNumber = "2247";

        String deltaLevel = "SECTION";
        String deltaAction = "AMEND";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();

        sourceNavigateFiltersAndSortsPage().setFilterDeltaLevel(deltaLevel);
        sourceNavigateFiltersAndSortsPage().setFilterAction(deltaAction);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        String targetNode = sourceNavigateGridPage().getDeltaTargetNodeText().substring(2);
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean insertDialogAppeared = renditionContextMenu().modifyInsertIntoHierarchyWizard();
        Assertions.assertTrue(insertDialogAppeared, "Insert Into Hierarchy Wizard appeared");

        insertIntoHierarchyWizardPage().clickFindButton();
        boolean targetSectionHasBeenFound = insertIntoHierarchyWizardPage().getSelectedNodeText().contains(targetNode);
        Assertions.assertTrue(targetSectionHasBeenFound, "Target node has been found in node tree in Wizard");

        insertIntoHierarchyWizardPage().addBelowSiblingButton();
        boolean verifyNodeAppearedAfterAdding = insertIntoHierarchyWizardPage().getSelectedNodeText().contains("= " + targetNode);
        Assertions.assertTrue(verifyNodeAppearedAfterAdding, "Hierarchy refreshed after adding the node");

        insertIntoHierarchyWizardPage().clickCloseButton();
        sourcePage().switchToDeltaNavigatePage();
        sourcePage().waitForPageLoaded();
        sourcePage().waitForGridRefresh();
        sourcePage().scrollToElement(ClamshellPageElements.sideContentContainer);
        sourcePage().waitForPageLoaded();
        sourcePage().waitForGridRefresh();

        sourceNavigateGridPage().rightClickFirstRendition();
        deltaContextMenu().goToViewTargetInHierarchy();
        boolean addedNodeAppearedUnderTheCorrectNode = insertIntoHierarchyWizardPage().getSelectedNodeText().contains(targetNode);
        Assertions.assertTrue(addedNodeAppearedUnderTheCorrectNode, "Target node has been added under correct node in Hierarchy");

        siblingMetadataPage().deleteSelectedSiblingMetadata();
    }

    /**
     * STORY: HALCYONST-1011 <br>
     * SUMMARY: Integrates a subsection of a delta and then checks it status <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrateComDocsDeltaTest()
    {
        username = user().getUsername().toUpperCase();
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        String contentSet = "Iowa (Development)";
        String renditionStatus = "PREP";
        String contentType = "COM";
        String docNumber = "XA0380B";

        String testPhrase = "TEST " + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();

        String workflowType = "CwbSourceIntegrateNodeSplitter";
        String workflowDescription = "Source Integrate Node Splitter";

        String[] statuses = { "WARN", "COMPLETE", "ERROR", "IN PROCESS Node Splitter"};

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().unlockFirstRendition();
        renditionUuid = sourceNavigateGridPage().getUUID();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();
        sourceNavigateGridPage().rightClickRowByIndex(2);
        deltaContextMenu().goToModifyResetIntegrationStatus();
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickRowByIndex(2);
        deltaContextMenu().editDelta();
        editorPage().switchToEditor();
        editorPage().switchDirectlyToTextFrame();

        editorTextPage().insertPhraseToTextsResearchReference(testPhrase);
        editorPage().closeAndCheckInChangesAndHandleSpellcheckWindowIfAppeared();
        editorPage().waitForEditorToClose();

        sourcePage().switchToDeltaNavigatePage();
        sourceNavigateGridPage().rightClickRowByIndex(2);
        deltaContextMenu().goToModifyIntegrate();

        toolsMenu().goToWorkflowReportingSystem();
        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, "", workflowDescription, username);
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowFinished, String.format("Workflow %s has been marked Finished in workflow Reporting", workflowID));

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToDeltaNavigatePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().clickRowByIndex(2);
        //Substring the value to get the number after the §
        String deltaTargetNodeValue = sourceNavigateGridPage().getSelectedDeltaTargetNode().substring(2);
        boolean integrationCompleteOrWarningOrError = sourceNavigateGridPage().selectedDeltaHasOneOfTheIntStatuses(statuses);

        sourceNavigateGridPage().rightClickRowByIndex(2);
        deltaContextMenu().editDelta();
        editorPage().switchToEditor();
        editorPage().switchDirectlyToTextFrame();
        boolean editorInfoAboutTextMerge = editorTextPage().checkEditorInfoAboutTextMerge(deltaTargetNodeValue, testPhrase);

        sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
        SourceDatabaseUtils.restoreBaselineToAPreviousBaseline(connection, renditionUuid, username, 2);

        Assertions.assertAll(
            () -> Assertions.assertTrue(integrationCompleteOrWarningOrError, "Integration is Completed or Warning or Error"),
            () -> Assertions.assertTrue(editorInfoAboutTextMerge, "The right date of merge and doc number is displayed")
        );
    }

    /**
     * STORY: HALCYONST-473 <br>
     * SUMMARY: integrates a subsection of a delta and then checks it status <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void textMergePartSubsectionTest()
    {
        username = user().getUsername().toUpperCase();
        connection = BaseDatabaseUtils.connectToDatabaseUAT();
        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";
        String year = "2012";
        String renditionStatus = "PREP";
        String docNumber = "2379";

        String phrase = "(ip.)";

        String workflowType = "CwbSourceIntegrate";
        String workflowContentSet = "106";
        String workflowDescription = "Source Integrate";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        renditionUuid = sourceNavigateGridPage().getUUID();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();

        sourceNavigateFooterToolsPage().clickSelectAllOnPage();
        sourceNavigateGridPage().onlyRightClickFirstItem();
        deltaContextMenu().goToModifyResetIntegrationStatus();
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateTabsPage().goToRenditionTab();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().editRendition();

        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertPhraseToTargetLocationSubsection(phrase);

        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();

        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();

        sourceNavigateFooterToolsPage().clickSelectAllOnPage();
        sourceNavigateGridPage().onlyRightClickFirstItem();
        deltaContextMenu().goToEditEffectiveDate();
        effectiveDatePage().enterTheInnerFrame();
        effectiveDatePage().setEffectiveDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        effectiveDatePage().clickSave();
        sourcePage().switchToDeltaNavigatePage();

        sourceNavigateFooterToolsPage().clickSelectAllOnPage();
        sourceNavigateGridPage().onlyRightClickFirstItem();
        deltaContextMenu().goToModifyCiteLocate();
        sourceNavigateGridPage().onlyRightClickFirstItem();
        deltaContextMenu().goToModifyIntegrate();

        toolsMenu().goToWorkflowReportingSystem();
        boolean workflowFinished = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, workflowContentSet, workflowDescription);
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToDeltaNavigatePage();
        sourceNavigateTabsPage().goToRenditionTab();
        sourcePage().waitForGridRefresh();

        SourceDatabaseUtils.restoreBaselineToOriginal(connection, renditionUuid, username);

        Assertions.assertTrue(workflowFinished, String.format("Workflow %s has been marked Finished in workflow Reporting", workflowID));
    }
}