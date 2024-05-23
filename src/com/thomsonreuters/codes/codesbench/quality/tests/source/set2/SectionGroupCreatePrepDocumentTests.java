package com.thomsonreuters.codes.codesbench.quality.tests.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.GroupingPropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.table.TableTestingPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;

public class SectionGroupCreatePrepDocumentTests extends TestService
{
    String level = "Section";
    String documentNumber;
    String uuid;
    ArrayList<String> groupNames;
    /**
     * STORY: N/A <br>
     * SUMMARY: creates a preparation document for a section group<br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createPreparationDocumentLegalTest()
    {
        groupNames = new ArrayList<>(Arrays.asList("TEST"));
        String groupName = groupNames.get(0);
        String unassigned = "Unassigned";

        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";
        String year = "2012";
        String docNumber = "2145";
        String renditionStatus = "APV";

        String workflowType = "CwbCreatePrepDocuments";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        uuid = sourceNavigateGridPage().getUUID();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifySectionGrouping();
        
        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(groupName);
        sectionGroupingSectionGridPage().selectSectionsInRange(1,1);
        sectionGroupingSectionGridPage().maximizeCurrentWindow();
        sectionGroupingSectionGridPage().moveSelectedSectionsToGroup(groupName);
        sectionGroupingFooterPage().clickApplyAndClose();
        sourcePage().switchToSourceNavigatePage();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionGroupTab();
        sourceNavigateGridPage().rightClickRenditionByGroupName(groupName);
        sectionGroupContextMenu().createPrepDocument();
        createPreparationDocumentPage().clickSubmit();

        sourceNavigateGridPage().breakOutOfFrame();
        sourceNavigateTabsPage().goToRenditionTab();
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, "", "");
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowCompleted, String.format("The workflow %s did not finish", workflowID));
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("PREP");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        boolean prepDocAppeared = sourceNavigateGridPage().firstRenditionExists();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifyDeleteRendition();

        Assertions.assertTrue(prepDocAppeared, "Prep document appears");
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Checks the Create Prep Document Date, and how it gets removed from a section<br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void removeCPDDateLegalTest()
    {
        groupNames = new ArrayList<>(Arrays.asList("TEST"));
        String groupName = groupNames.get(0);
        String unassigned = "Unassigned";

        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";
        String year = "2012";
        String docNumber = "2159";

        String workflowType = "CwbCreatePrepDocuments";
        String description = "Create Prep Document";
        String user = user().getWorkflowUsername();
        long accuracy = 90000;

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

       sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
       sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
       sourceNavigateFiltersAndSortsPage().setFilterYear(year);
       sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
       sourceNavigateFooterToolsPage().refreshTheGrid();

       uuid = sourceNavigateGridPage().getUUID();
       documentNumber = sourceNavigateGridPage().getDocumentId();
       sourceNavigateGridPage().rightClickFirstRendition();
       renditionContextMenu().modifySectionGrouping();

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(groupName);
        sectionGroupingSectionGridPage().selectSectionsInRange(1,5);
        sectionGroupingSectionGridPage().maximizeCurrentWindow();
        sectionGroupingSectionGridPage().moveSelectedSectionsToGroup(groupName);
        sectionGroupingFooterPage().clickGroupingApply();
        sectionGroupingSectionGridPage().closeCurrentWindowIgnoreDialogue();

        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionGroupTab();
        sourceNavigateGridPage().rightClickRenditionByGroupName(groupName);
        sectionGroupContextMenu().createPrepDocument();
        createPreparationDocumentPage().clickSubmit();

        sourceNavigateGridPage().breakOutOfFrame();
        long currentMs = DateAndTimeUtils.getCurrentDateTimeInMilliseconds();
        sourceNavigateTabsPage().goToRenditionTab();
        toolsMenu().goToWorkflowReportingSystem();

        boolean firstWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, "", description, user.toUpperCase());
        String firstWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(firstWorkflowCompleted, String.format("The workflow %s did not finish", firstWorkflowID));
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();

        sourceNavigateTabsPage().goToSectionGroupTab();
        sourceNavigateFooterToolsPage().clickTheViewManager();
        viewManagementPage().selectView("all cols");
        sourceNavigateGridPage().clickRenditionByGroupName(groupName);
        String cpdDateTime = sourceNavigateGridPage().getCPDDateTime(groupName);
        cpdDateTime = cpdDateTime.replace(".",":");

        long wfMs = DateAndTimeUtils.getDateInMs(cpdDateTime);
        boolean cpdDateInGridIsCorrect = DateAndTimeUtils.isDateTimesInMillisecondsEqual(currentMs, wfMs, accuracy);

        toolsMenu().goToWorkflowReportingSystem();
        boolean secondWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, "", description, user.toUpperCase());
        String secondWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(secondWorkflowCompleted, String.format("The second workflow %s did complete", secondWorkflowID));

        String workflowDate = workflowSearchPage().getStartTime();
        long workflowMS = DateAndTimeUtils.getSpecificDateTimeInMilliseconds(workflowDate, "MM/dd/yyyy HH:mm:ss", "GMT");
        boolean dateInStartTimeISCorrect = DateAndTimeUtils.isDateTimesInMillisecondsEqual(currentMs, workflowMS, DateAndTimeUtils.ONE_MINUTE);

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSectionNavigatePage();

        sourceNavigateGridPage().rightClickRenditionByGroupName(groupName);
        renditionContextMenu().openPropertiesByGrouping("Section");

        String propertiesDate = groupingPropertiesPage().getCPDDate();
        boolean propertiesCPDDateIsCorrect = propertiesDate.equals(DateAndTimeUtils.getCurrentDateMMddyyyy());

        boolean addButtonExists = GroupingPropertiesPageElements.addCpdDateButton.isDisplayed();
        boolean removeButtonExists = GroupingPropertiesPageElements.removeCpdDateButton.isDisplayed();

        boolean removeAlertExists = groupingPropertiesPage().removeCPDDateCancelAlert();

        propertiesDate = groupingPropertiesPage().getCPDDate();
        boolean cpdDateWasNotRemoved = propertiesDate.equals(DateAndTimeUtils.getCurrentDateMMddyyyy());

        groupingPropertiesPage().removeCPDDateAcceptAlert();
        boolean cpdDateWasRemoved = groupingPropertiesPage().getCPDDate().isEmpty();
        groupingPropertiesPage().clickSaveButton();

        sourcePage().waitForPageLoaded();
        sourceNavigateTabsPage().waitForGridRefresh();
        cpdDateTime = sourceNavigateGridPage().getCPDDateTime(groupName);
        boolean cpdDateTimeInGridIsEmpty = cpdDateTime.isEmpty();

        sourceNavigateTabsPage().goToRenditionTab();
        sourceNavigateFooterToolsPage().clearFilters();
        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("PREP");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(documentNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifyDeleteRendition();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(cpdDateInGridIsCorrect, "CPD Date in the grid should be current date"),
            () -> Assertions.assertTrue(dateInStartTimeISCorrect, "Cpd Date in the WorkFlow should be current date"),
            () -> Assertions.assertTrue(propertiesCPDDateIsCorrect, "CPD Date should be current date"),
            () -> Assertions.assertTrue(addButtonExists, "Add button should exist"),
            () -> Assertions.assertTrue(removeButtonExists, "Remove button should exist"),
            () -> Assertions.assertTrue(removeAlertExists, "The alert should appear"),
            () -> Assertions.assertTrue(cpdDateWasNotRemoved, "CPD Date should not be removed"),
            () -> Assertions.assertTrue(cpdDateWasRemoved, "CPD Date should be removed"),
            () -> Assertions.assertTrue(cpdDateTimeInGridIsEmpty, "CPD Date in the grid should be empty")
        );
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Attempts to add a Create Prep Document Date in the section group properties <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addCPDDateLegalTest()
    {
        long accuracy = 60000;
        groupNames = new ArrayList<>(Arrays.asList("TEST"));
        String groupName = "TEST";
        String unassigned = "Unassigned";

        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";
        String year = "2012";
        String docNumber = "2168";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().unlockFirstRendition();

        uuid = sourceNavigateGridPage().getUUID();
        documentNumber = sourceNavigateGridPage().getDocumentId();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifySectionGrouping();

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(groupName);
        sectionGroupingSectionGridPage().selectSectionsInRange(1,3);
        sectionGroupingSectionGridPage().maximizeCurrentWindow();
        sectionGroupingSectionGridPage().moveSelectedSectionsToGroup(groupName);
        sectionGroupingFooterPage().clickGroupingApply();
        sectionGroupingSectionGridPage().closeCurrentWindowIgnoreDialogue();

        sourcePage().switchToSourceNavigatePage();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionGroupTab();
        sourceNavigateFooterToolsPage().clickTheViewManager();
        viewManagementPage().selectView("all cols");

        boolean cpdDateTimeInGridIsEmpty = sourceNavigateGridPage().getCPDDateTime(groupName).isEmpty();

        sourceNavigateGridPage().rightClickRenditionByGroupName(groupName);
        renditionContextMenu().openPropertiesByGrouping("Section");

        boolean cpdDateInPropertiesIsEmpty = groupingPropertiesPage().getCPDDate().isEmpty();

        boolean addButtonExists = GroupingPropertiesPageElements.addCpdDateButton.isDisplayed();
        boolean removeButtonExists = GroupingPropertiesPageElements.removeCpdDateButton.isDisplayed();

        boolean addAlertExists = groupingPropertiesPage().addCPDDateAcceptAlert();
        boolean cpdDateTimeInPropertiesIsStillEmpty = groupingPropertiesPage().getCPDDate().isEmpty();

        groupingPropertiesPage().addCPDDateAcceptAlert();
        groupingPropertiesPage().setCPDDateToTodaysDate();

        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
        String cpdDate = groupingPropertiesPage().getCPDDate();
        boolean propertiesCPDDateIsCorrect = cpdDate.equals(currentDate);
        groupingPropertiesPage().clickSaveButton();

        sourcePage().waitForPageLoaded();
        sourcePage().waitForGridRefresh();
        String dateTime = sourceNavigateGridPage().getCPDDateTime(groupName);
        dateTime = dateTime.replace(".",":");
        long cdpMs = DateAndTimeUtils.getDateInMs(dateTime);
        long currentDateMS = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        boolean cpdDateInGridIsCorrect = DateAndTimeUtils.isDateTimesInMillisecondsEqual(currentDateMS,cdpMs,accuracy);

        sourceNavigateTabsPage().goToRenditionTab();
        sourceNavigateFooterToolsPage().clearFilters();
        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("PREP");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(documentNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifyDeleteRendition();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(cpdDateTimeInGridIsEmpty, "CPD Date/Time in the grid should be Empty"),
            () -> Assertions.assertTrue(cpdDateInPropertiesIsEmpty, "CPD Date should be empty"),
            () -> Assertions.assertTrue(addButtonExists, "Add button should exist"),
            () -> Assertions.assertTrue(removeButtonExists, "Remove button should exist"),
            () -> Assertions.assertTrue(addAlertExists, "The add alert should appear"),
            () -> Assertions.assertTrue(cpdDateTimeInPropertiesIsStillEmpty, "CPD Date should be still empty"),
            () -> Assertions.assertTrue(propertiesCPDDateIsCorrect, "CPD Date should be current date"),
            () -> Assertions.assertTrue(cpdDateInGridIsCorrect, "CPD Date in the grid should be current date")
        );
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Removes the Create Prep Document Date after rebuilding in the editor <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void removeCPDDateAfterRebuildTest()
    {
        long accuracy = 60000;
        groupNames = new ArrayList<>(Arrays.asList("TEST"));
        String groupName = groupNames.get(0);
        String unassigned = "Unassigned";

        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";
        String year = "2018";
        String docNumber = "2236";
        String workflowType = "CwbCreatePrepDocuments";
        String description = "Create Prep Document";
        String user = user().getWorkflowUsername();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().unlockFirstRendition();

        uuid = sourceNavigateGridPage().getUUID();
        documentNumber = sourceNavigateGridPage().getDocumentId();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifySectionGrouping();

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(groupName);
        sectionGroupingSectionGridPage().selectSectionsInRange(1,5);
        sectionGroupingSectionGridPage().maximizeCurrentWindow();
        sectionGroupingSectionGridPage().moveSelectedSectionsToGroup(groupName);
        sectionGroupingFooterPage().clickGroupingApply();
        sectionGroupingSectionGridPage().closeCurrentWindowIgnoreDialogue();

        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().editRendition();
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().clickSourceSectionByNumber(1);
        editorTextPage().sendKeys(Keys.chord(Keys.CONTROL, "c"));
        editorTextPage().sendKeys(Keys.chord(Keys.CONTROL, "v"));

        editorTextPage().clickSourceSectionByNumber(2);
        editorTextPage().sendKeys(Keys.DOWN, Keys.END, Keys.DOWN);
        editorTextPage().sendKeys(groupName);

        editorToolbarPage().clickRebuild();
        boolean warningAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true, "*Warning* This Rendition has existing Section Groups and/or Delta Groups.  Running Rebuild will remove all Section Group and/or Delta Group assignments. *Warning*.");
        boolean checkMessageRebuild = editorMessagePage().checkMessage("Rebuild completed successfully");
        editorPage().breakOutOfFrame();
        editorPage().enterTheInnerFrame();
        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();

        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifySectionGrouping();

        boolean noAssociated = sectionGroupingGroupGridPage().getSectionCountForGroup(groupName).equals("0");
        boolean newSectionNumberExists = sectionGroupingGroupGridPage().getSectionNumberContainsGroupForRow(groupName,1);

        sectionGroupingSectionGridPage().selectSectionsInRange(1,5);
        sectionGroupingSectionGridPage().maximizeCurrentWindow();
        sectionGroupingSectionGridPage().moveSelectedSectionsToGroup(groupName);
        sectionGroupingFooterPage().clickGroupingApply();
        sectionGroupingGroupGridPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionGroupTab();
        sourceNavigateGridPage().rightClickRenditionByGroupName(groupName);
        sectionGroupContextMenu().createPrepDocument();
        createPreparationDocumentPage().clickSubmit();

        sourceNavigateGridPage().breakOutOfFrame();
        long currentMs = DateAndTimeUtils.getCurrentDateTimeInMilliseconds();
        sourceNavigateTabsPage().goToRenditionTab();
        toolsMenu().goToWorkflowReportingSystem();

        boolean firstWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, "", description, user.toUpperCase());
        String firstWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(firstWorkflowCompleted, String.format("The first workflow %s did complete", firstWorkflowID));
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();

        sourceNavigateTabsPage().goToSectionGroupTab();
        sourceNavigateFooterToolsPage().clickTheViewManager();
        viewManagementPage().selectView("all cols");

        String cpdDateTime = sourceNavigateGridPage().getCPDDateTime(groupName);
        cpdDateTime = cpdDateTime.replace(".",":");

        long wfMs = DateAndTimeUtils.getDateInMs(cpdDateTime);
        boolean cpdDateInGridIsCorrect = DateAndTimeUtils.isDateTimesInMillisecondsEqual(currentMs, wfMs, accuracy);

        toolsMenu().goToWorkflowReportingSystem();
        boolean secondWorkflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, "", description, user.toUpperCase());
        String secondWorkflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(secondWorkflowCompleted, String.format("The second workflow %s did complete", secondWorkflowID));
        String workflowDate = workflowSearchPage().getStartTime();
        long workflowMS = DateAndTimeUtils.getSpecificDateTimeInMilliseconds(workflowDate, "MM/dd/yyyy HH:mm:ss", "GMT");
        boolean dateInStartTimeISCorrect = DateAndTimeUtils.isDateTimesInMillisecondsEqual(currentMs, workflowMS, DateAndTimeUtils.ONE_MINUTE);

        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSectionNavigatePage();

        sourceNavigateGridPage().rightClickRenditionByGroupName(groupName);
        renditionContextMenu().openPropertiesByGrouping("Section");

        String propertiesDate = groupingPropertiesPage().getCPDDate();
        boolean propertiesCPDDateIsCorrect = propertiesDate.equals(DateAndTimeUtils.getCurrentDateMMddyyyy());

        boolean addButtonExists = GroupingPropertiesPageElements.addCpdDateButton.isDisplayed();
        boolean removeButtonExists = GroupingPropertiesPageElements.removeCpdDateButton.isDisplayed();

        boolean removeAlertExists = groupingPropertiesPage().removeCPDDateCancelAlert();

        propertiesDate = groupingPropertiesPage().getCPDDate();
        boolean cpdDateWasNotRemoved = propertiesDate.equals(DateAndTimeUtils.getCurrentDateMMddyyyy());

        groupingPropertiesPage().removeCPDDateAcceptAlert();
        boolean cpdDateWasRemoved = groupingPropertiesPage().getCPDDate().isEmpty();
        groupingPropertiesPage().clickSaveButton();

        sourcePage().waitForPageLoaded();
        sourceNavigateTabsPage().waitForGridRefresh();
        cpdDateTime = sourceNavigateGridPage().getCPDDateTime(groupName);
        boolean cpdDateTimeInGridIsEmpty = cpdDateTime.isEmpty();

        sourceNavigateTabsPage().goToRenditionTab();

        sourceNavigateFooterToolsPage().clearFilters();
        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("PREP");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(documentNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifyDeleteRendition();

        sourceNavigateFooterToolsPage().clearFilters();
        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(documentNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewRenditionBaselines();
        viewBaselinesNavigatePage().rightClickRowX("2");
        viewBaselinesNavigatePage().openRestoreBaseLineContextMenu();
        viewBaselinesNavigatePage().closeViewBaselinesPage();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(warningAlertAppeared, "*Warning* alert should appeared after rebuild"),
            () -> Assertions.assertTrue(checkMessageRebuild, "Rebuild should be completed successfully"),
            () -> Assertions.assertTrue(noAssociated, "Group should not have sections and/or deltas associated to it"),
            () -> Assertions.assertTrue(newSectionNumberExists, "New section should appear in the grid of available sections"),
            () -> Assertions.assertTrue(cpdDateInGridIsCorrect, "CPD Date in the grid should be current date"),
            () -> Assertions.assertTrue(dateInStartTimeISCorrect, "CPD Date in the grid should be current date"),
            () -> Assertions.assertTrue(propertiesCPDDateIsCorrect, "CPD Date should be current date"),
            () -> Assertions.assertTrue(addButtonExists, "Add button should exist"),
            () -> Assertions.assertTrue(removeButtonExists, "Remove button should appear"),
            () -> Assertions.assertTrue(removeAlertExists, "The alert should appear"),
            () -> Assertions.assertTrue(cpdDateWasNotRemoved, "CPD Date should not be removed"),
            () -> Assertions.assertTrue(cpdDateWasRemoved, "CPD Date should be removed"),
            () -> Assertions.assertTrue(cpdDateTimeInGridIsEmpty, "CPD Date in the grid should be empty")
        );
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Tests the alert that should appear when attempting to move sections into or out of a Section Group associated with a Prep document<br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void preventChangesToCPDSectionGroupsLegalTest()
    {
        long accuracy = 60000;
        groupNames = new ArrayList<>(Arrays.asList("TEST1", "TEST2", "TEST3"));
        String group1 = groupNames.get(0);
        String group2 = groupNames.get(1);
        String group3 = groupNames.get(2);
        String unassigned = "Unassigned";

        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";
        String year = "2012";
        String docNumber = "2464";
        String workflowType = "CwbCreatePrepDocuments";
        String description = "Create Prep Document";
        String user = user().getWorkflowUsername();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().unlockFirstRendition();

        uuid = sourceNavigateGridPage().getUUID();
        documentNumber = sourceNavigateGridPage().getDocumentId();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifySectionGrouping();

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(group1);
        sectionGroupingSectionGridPage().selectSectionsInRange(1,3);
//        sectionGroupingSectionGridPage().maximizeCurrentWindow();
        sectionGroupingSectionGridPage().moveSelectedSectionsToGroup(group1);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(group2);
        sectionGroupingSectionGridPage().selectSectionsInRange(4,6);
        sectionGroupingSectionGridPage().maximizeCurrentWindow();
        sectionGroupingSectionGridPage().moveSelectedSectionsToGroup(group2);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(group3);
        sectionGroupingSectionGridPage().selectSectionsInRange(7,9);
        sectionGroupingSectionGridPage().maximizeCurrentWindow();
        sectionGroupingSectionGridPage().moveSelectedSectionsToGroup(group3);

        sectionGroupingToolsPage().clickApplyAndClose();

        sourcePage().switchToSourceNavigatePage();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionGroupTab();
        sourceNavigateGridPage().rightClickRenditionByGroupName(group1);
        sectionGroupContextMenu().createPrepDocument();
        createPreparationDocumentPage().clickSubmit();

        sourceNavigateGridPage().breakOutOfFrame();
        long currentMs = DateAndTimeUtils.getCurrentDateTimeInMilliseconds();
        sourceNavigateTabsPage().goToRenditionTab();
        toolsMenu().goToWorkflowReportingSystem();

        boolean workflowCompleted = workflowSearchPage().filterWorkflowAndVerifyStatus(workflowType, "", description, user.toUpperCase());
        String workflowID = workflowSearchPage().getWorkflowIdOfFirstWorkflow();
        Assertions.assertTrue(workflowCompleted, String.format("The workflow %s did complete", workflowID));
        workflowSearchPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigatePage();

        sourceNavigateTabsPage().goToSectionGroupTab();
        sourceNavigateFooterToolsPage().clickTheViewManager();
        viewManagementPage().selectView("all cols");
        sourceNavigateGridPage().clickRenditionByGroupName(group1);
        String cpdDateTime = sourceNavigateGridPage().getCPDDateTime(group1);
        cpdDateTime = cpdDateTime.replace(".",":");

        long wfMs = DateAndTimeUtils.getDateInMs(cpdDateTime);
        boolean cpdDateInGridIsCorrect = DateAndTimeUtils.isDateTimesInMillisecondsEqual(currentMs, wfMs, accuracy);

        sourceNavigateTabsPage().goToRenditionTab();
        sourceNavigateFooterToolsPage().clearFilters();
        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("PREP");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(documentNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        boolean prepDocAppeared =  sourceNavigateGridPage().firstRenditionExists();

        homePage().openGroupingForRenditionWithUuid("Section",uuid);

        int[] groupNameRows = tableTestingPage().getRowsByText(TableTestingPage.GroupingColumns.GROUP_NAME, group1);
        int[] cpdDateRows = tableTestingPage().getRowsByText(TableTestingPage.GroupingColumns.CPD_DATE, "true");
        boolean cpdDateSectionValue = Arrays.equals(groupNameRows,cpdDateRows);

        sectionGroupingSectionGridPage().selectSectionsInRange(2,2);
        sectionGroupingSectionGridPage().maximizeCurrentWindow();
        sectionGroupingSectionGridPage().moveSelectedSectionsToGroup(group2);
        boolean alertAppeared1 = AutoITUtils.verifyAlertTextContainsAndAccept(true,"At least some selected Section(s) can not have Section Group changed because CPD by Section Group has already been run");
        boolean sectionsDidNotMove1 = tableTestingPage().checkCellValue(TableTestingPage.GroupingColumns.GROUP_NAME, 2,group1);

        sectionGroupingSectionGridPage().selectSectionsInRange(5,5);
        sectionGroupingSectionGridPage().maximizeCurrentWindow();
        sectionGroupingSectionGridPage().moveSelectedSectionsToGroup(group1);
        boolean alertAppeared2 = AutoITUtils.verifyAlertTextContainsAndAccept(true, "At least some selected Section(s) can not have Section Group changed because CPD by Section Group has already been run");
        boolean sectionsDidNotMove2 = tableTestingPage().checkCellValue(TableTestingPage.GroupingColumns.GROUP_NAME, 5,group2);

        sectionGroupingSectionGridPage().selectSectionsInRange(3,4);
        sectionGroupingSectionGridPage().maximizeCurrentWindow();
        sectionGroupingSectionGridPage().moveSelectedSectionsToGroup(group3);
        boolean alertAppeared3 = AutoITUtils.verifyAlertTextContainsAndAccept(true, "At least some selected Section(s) can not have Section Group changed because CPD by Section Group has already been run");
        boolean sectionsDidNotMove3 = tableTestingPage().checkCellValue(TableTestingPage.GroupingColumns.GROUP_NAME, 3,group1);
        boolean sectionDidMove = tableTestingPage().checkCellValue(TableTestingPage.GroupingColumns.GROUP_NAME,4,group3);

        boolean sectionsCountInGroup2IsCorrect = sectionGroupingGroupGridPage().getSectionCountForGroup(group2).equals("2");
        boolean sectionsCountInGroup3IsCorrect = sectionGroupingGroupGridPage().getSectionCountForGroup(group3).equals("4");

        sourcePage().goToSourcePage();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifyDeleteRendition();
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(cpdDateInGridIsCorrect, "CPD Date in the grid should be current date"),
            () -> Assertions.assertTrue(prepDocAppeared, "Prep document should appear"),
            () -> Assertions.assertTrue(cpdDateSectionValue, "The sections in the 1st section group should be ?true? for the CPD Date column"),
            () -> Assertions.assertTrue(alertAppeared1, "The first alert should appear"),
            () -> Assertions.assertTrue(sectionsDidNotMove1, "Section from the 1st group should not be moved to the 2nd gorup"),
            () -> Assertions.assertTrue(alertAppeared2, "The second alert should appear"),
            () -> Assertions.assertTrue(sectionsDidNotMove2, "Sections from the 2nd group should not be moved to the 2nd group"),
            () -> Assertions.assertTrue(alertAppeared3, "The Third alert should appear"),
            () -> Assertions.assertTrue(sectionsDidNotMove3, "Sections from the 1st group should not be moved to the 3rd group"),
            () -> Assertions.assertTrue(sectionDidMove, "Sections from the 2nd group should be moved to the 3rd group"),
            () -> Assertions.assertTrue(sectionsCountInGroup2IsCorrect, "Sections count in the 2nd group should be updated"),
            () -> Assertions.assertTrue(sectionsCountInGroup3IsCorrect, "Sections count in the 3rd group should be updated")
         );
    }

    @AfterEach
    public void deleteGroups()
    {
        for (String groupName : groupNames)
        {
            sectionGroupingGroupGridPage().deleteGroupThroughDatabase(groupName, level, uuid);
        }
    }
}
