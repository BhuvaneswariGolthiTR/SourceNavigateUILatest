package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.SectionGroupContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SectionGroupingPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.sectiongrouping.SectionGroupingToolsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.sectiongrouping.SectionGroupingSectionGridPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupingLegalTests extends TestService
{
    String groupLevel;
    String renditionUuid;
    ArrayList<String> groupNames;
    private static Object[][] renditionFiltering()
    {
        return new Object[][]
        {
            {"2014", "APV", "2324", "Delta"},
            {"2012", "PREP", "2312", "Section"}
        };
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Opens the grouping windows from source navigate <br>
     * USER: LEGAL <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionFiltering")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingWindowsOpensLegalTest(String year, String renditionStatus, String docNumber, String level)
    {
        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";
        groupLevel = level;
        renditionUuid = "";
        groupNames = new ArrayList<>();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        boolean sectionWindowAppeared = renditionContextMenu().modifySectionOrDeltaGrouping(groupLevel);

        Assertions.assertTrue(sectionWindowAppeared, "The section window appeared.");
    }
    private static Object[][] renditionUuid()
    {
        return new Object[][]
                {
                    {"Delta", "IC33F5BB1B09211E3B34DCF0398D7238F"}, // {"2014", "APV", "2324", "Delta"}
                    {"Section", "I62209E6064D611E28B049F1D7A89B350"} // {"2012", "PREP", "2312", "Section"}
                };
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Checks the different values of the toolbars and makes sure they are correct <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionUuid")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingCheckMenuToolbarLegalTest(String level, String uuid)
    {
        groupNames = new ArrayList<>();
        groupLevel = level;
        renditionUuid = uuid;
        List<String> topToolbarButtons = Arrays.asList("New Group", "Remove Group", "Move Up", "Move Down");
        List<String> bottomToolbarButtons = Arrays.asList("Clear", "Select All on Page", "Apply", "Apply and close", "Cancel");

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        boolean topToolbarEquals = topToolbarButtons.equals(sectionGroupingFiltersAndSortsPage().getTopToolBarItems());
        boolean bottomToolbarEquals = bottomToolbarButtons.equals(sectionGroupingFiltersAndSortsPage().getBottomToolBarItems());

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(topToolbarEquals, "Top toolbar matched"),
            () -> Assertions.assertTrue(bottomToolbarEquals, "Bottom toolbar matched")
        );
    }

    /**
     * STORY: N/A  <br>
     * SUMMARY: Creates a new group and makes sure it is created in the correct location <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionUuid")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingInsertDeleteLegalTest(String level,String uuid)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test1"));
        groupLevel = level;
        renditionUuid = uuid;
        String unassigned = "Unassigned";
        String test1 = groupNames.get(0);

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        boolean firstGroupIsUnassigned = sectionGroupingGroupGridPage().checkGroupPosition(unassigned,"1");

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);
        boolean newGroupIsCreated = sectionGroupingGroupGridPage().doesGroupExist(test1);
        boolean newGroupIsOnSecondPlace = sectionGroupingGroupGridPage().checkGroupPosition(test1, "2");
        boolean newGroupIsSelected = sectionGroupingGroupGridPage().checkGroupSelection(test1);

        if(sectionGroupingGroupGridPage().doesGroupExist(test1))
        {
            sectionGroupingGroupGridPage().clickGroup(test1);
            sectionGroupingFooterPage().clickGroupingRemove();
            sectionGroupingFooterPage().clickGroupingYes();
            sectionGroupingFooterPage().clickGroupingApply();
        }
        boolean newGroupIsDeleted = !sectionGroupingGroupGridPage().doesGroupExist(test1);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(firstGroupIsUnassigned, "First group was unassigned"),
            () -> Assertions.assertTrue(newGroupIsCreated, "New group was created"),
            () -> Assertions.assertTrue(newGroupIsOnSecondPlace, "New group is the second group"),
            () -> Assertions.assertTrue(newGroupIsSelected, "The new group is selected"),
            () -> Assertions.assertTrue(newGroupIsDeleted, "The new group is deleted")
        );
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Tries to name a group a name that already exists <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionUuid")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingPreventDuplicateSectionGroupNamesLegalTest(String level, String uuid)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test1", "Test2"));
        groupLevel = level;
        renditionUuid = uuid;
        String unassigned = "Unassigned";
        String test1 = groupNames.get(0);
        String test2 = groupNames.get(1);

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);
        boolean duplicateGroupNameErrorAppeared = sectionGroupingGroupGridPage().acceptedDuplicateGroupNameError();
        sectionGroupingGroupGridPage().addNewGroup(test2);
        boolean secondTestGroupWasCreated = sectionGroupingGroupGridPage().doesGroupExist(test2);

        Assertions.assertAll
        (
            () ->Assertions.assertTrue(duplicateGroupNameErrorAppeared, "Error appeared"),
            () -> Assertions.assertTrue(secondTestGroupWasCreated, "The second test group was created")
        );
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Reorders the groups using keystrokes and context menus <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionUuid")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingReorderGroupsLegalTest(String level, String uuid)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test1", "Test2", "Test3"));
        groupLevel = level;
        renditionUuid = uuid;
        String test1 = groupNames.get(0);
        String test2 = groupNames.get(1);
        String test3 = groupNames.get(2);
        String unassigned = "Unassigned";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test2);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test3);

        sectionGroupingGroupGridPage().moveGroupDown(test1);
        sectionGroupingGroupGridPage().moveGroupDown(test1);
        boolean moveDownCheck = sectionGroupingGroupGridPage().checkGroupPosition(test1, "4");

        sectionGroupingGroupGridPage().moveGroupUp(test1);
        sectionGroupingGroupGridPage().moveGroupUp(test1);
        boolean moveUpCheck = sectionGroupingGroupGridPage().checkGroupPosition(test1, "2");

        sectionGroupingGroupGridPage().rightClickGroupName(test1);
        sectionGroupContextMenu().moveGroupDown();

        sectionGroupingGroupGridPage().rightClickGroupName(test1);
        sectionGroupContextMenu().moveGroupDown();

        boolean moveDownFromContextMenu = sectionGroupingGroupGridPage().checkGroupPosition(test1, "4");

        sectionGroupingGroupGridPage().rightClickGroupName(test1);
        sectionGroupContextMenu().moveGroupUp();

        sectionGroupingGroupGridPage().rightClickGroupName(test1);
        sectionGroupContextMenu().moveGroupUp();

        boolean moveUpFromContextMenu = sectionGroupingGroupGridPage().checkGroupPosition(test1, "2");

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(moveDownCheck, "Group was moved down"),
            () -> Assertions.assertTrue(moveUpCheck, "Group was moved up"),
            () -> Assertions.assertTrue(moveDownFromContextMenu, "Group was moved down from context menu"),
            () -> Assertions.assertTrue(moveUpFromContextMenu, "Group was moved up from context menu")
        );
    }

    /**
     * STORY:  N/A <br>
     * SUMMARY:  Regroups one section/delta and checks its in the new group <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionUuid")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingMoveSectionFromGroupToGroupLegalTest(String level, String uuid)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test1", "Test2"));
        groupLevel = level;
        renditionUuid = uuid;
        String unassigned = "Unassigned";
        String test1 = groupNames.get(0);
        String test2 = groupNames.get(1);

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test2);

        sectionGroupingSectionGridPage().clickXSectionsInARange(2,3);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(2);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(test1);
        sectionGroupingSectionGridPage().clickXSectionsInARange(4,5);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(4);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(test2);

        sectionGroupingSectionGridPage().clickXSectionsInARange(3,3);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(3);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(test2);

        boolean sectionMoveToNewGroup = sectionGroupingSectionGridPage().checkSectionGroupOnPosition(test1, 3);
        boolean sectionDontBelongToOldGroup = sectionGroupingSectionGridPage().checkSectionGroupOnPosition(test2, 3);

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(sectionMoveToNewGroup, "Section was moved to the new group"),
            () -> Assertions.assertTrue(sectionDontBelongToOldGroup, "Section was not in the old group")
        );
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Removes grouping using the context menu <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionUuid")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingRemoveGroupWithContextMenuLegalTest(String level, String uuid)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test1"));
        groupLevel = level;
        renditionUuid = uuid;
        String unassigned = "Unassigned";
        String test1 = groupNames.get(0);

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);

        sectionGroupingGroupGridPage().rightClickGroupName(test1);
        sectionGroupContextMenu().remove();
        sectionGroupingFooterPage().clickGroupingYes();

        boolean groupIsRemoved = !sectionGroupingGroupGridPage().doesGroupExist(test1);

        Assertions.assertTrue(groupIsRemoved, "Group is removed");
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Tests renaming a grouping <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionUuid")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingRenameGroupWithContextMenuLegalTest(String level, String uuid)
    {
        groupNames = new ArrayList<>(Arrays.asList("Rename Group Context Menu", "Renamed"));
        groupLevel = level;
        renditionUuid = uuid;
        String renameOld = groupNames.get(0);
        String renameNew = groupNames.get(1);
        String unassigned = "Unassigned";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(renameOld);

        sectionGroupingGroupGridPage().clickGroup(renameOld);
        sectionGroupingGroupGridPage().rightClickGroupName(renameOld);
        sectionGroupContextMenu().rename();
        sectionGroupingGroupGridPage().groupRename(renameNew);

        boolean oldNameIsPresent = sectionGroupingGroupGridPage().doesGroupExist(renameOld);
        boolean newNameIsPresent = sectionGroupingGroupGridPage().doesGroupExist(renameNew);

        Assertions.assertAll
        (
            () -> Assertions.assertFalse(oldNameIsPresent, "The name changed"),
            () -> Assertions.assertTrue(newNameIsPresent, "The name changed to Renamed")
        );
    }

    /**
     * STORY:  N/A <br>
     * SUMMARY: Checks the from selected group tab to make sure the selected sections/deltas are within that group <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionUuid")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingSectionsOrDeltasAreFromSelectedGroupLegalTest(String level, String uuid)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test1"));
        groupLevel = level;
        renditionUuid = uuid;
        String unassigned = "Unassigned";
        String test1 = groupNames.get(0);

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);

        sectionGroupingSectionGridPage().clickXSectionsInARange(1,3);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(1);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(test1);

        sectionGroupingGroupGridPage().clickGroup(test1);
        sectionGroupingSectionGridPage().clickSectionFromSelectedGroup();

        boolean sectionsFromGroupSelected = sectionGroupingSectionGridPage().checkSelectedGroupSectionDeltaNameList(test1);

        sectionGroupingSectionGridPage().selectAllSections();

        boolean allSectionsSelected = sectionGroupingSectionGridPage().checkAllGroupsSectionsDeltasNameList(test1);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(sectionsFromGroupSelected, "Sections from the group were selected"),
            () -> Assertions.assertTrue(allSectionsSelected, "All sections were selected")
        );
    }

    /**
     * STORY:  N/A <br>
     * SUMMARY: Tests if opening the editor locks the Delta or Section grouping <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionFiltering")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingLockingBehaviorLegalTest(String year, String renditionStatus, String docNumber, String level)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test1"));
        groupLevel = level;
        String unassigned = "Unassigned";
        String test1 = "Test1";
        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";

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
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);

        sectionGroupingSectionGridPage().clickXSectionsInARange(1,3);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(1);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(test1);

        sectionGroupingFooterPage().clickGroupingApply();
        sourcePage().goToSourcePage();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().switchToGroupingTab(groupLevel);
        int expectedUnassignedPosition = 1;
        if(sourceNavigateGridPage().doesGroupNameExistAtPosition(1))
        {
            expectedUnassignedPosition = 2;
        }

        boolean checkUnassignedInGrid = sourceNavigateGridPage().groupExistInGrid(expectedUnassignedPosition,unassigned);
        boolean checkTest1InGrid = sourceNavigateGridPage().groupExistInGrid(++expectedUnassignedPosition,test1);

        sourceNavigateGridPage().rightClickRenditionByGroupName(test1);
        boolean editorWindowIsOpen = renditionContextMenu().openEditorByGrouping(groupLevel);
        editorPage().waitForPageLoaded();

        editorPage().closeCurrentWindowIgnoreDialogue();
        sourcePage().switchToSourceNavigateWindow();
        sourceNavigateFooterToolsPage().refreshTheGrid();

        boolean isDeltaGroupLocked = sourceNavigateGridPage().checkSectionDeltaGroupIsLocked(test1);

        sourceNavigateGridPage().rightClickRenditionByGroupName(test1);
        boolean editorWindowIsOpen2 = renditionContextMenu().openEditorByGrouping(groupLevel);
        editorPage().waitForPageLoaded();

        editorPage().closeDocumentAndDiscardChanges();
        sourcePage().switchToSourceNavigateWindow();
        sourceNavigateFooterToolsPage().refreshTheGrid();

        boolean deltaGroupNoLongerLocked = !sourceNavigateGridPage().checkSectionDeltaGroupIsLocked(test1);

        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(checkUnassignedInGrid, "Unassigned Group is on the first place"),
            () -> Assertions.assertTrue(checkTest1InGrid, "Test1 Group is on the second place"),
            () -> Assertions.assertTrue(editorWindowIsOpen, "Common Editor window appears"),
            () -> Assertions.assertTrue(isDeltaGroupLocked, "Delta Group is locked"),
            () -> Assertions.assertTrue(editorWindowIsOpen2, "Common Editor window appears"),
            () -> Assertions.assertTrue(deltaGroupNoLongerLocked, "Delta Group is not locked")
        );
    }

    /**
     * STORY:  N/A <br>
     * SUMMARY: Opens the properties on a group without any sections/deltas <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionFiltering")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingOpenPropertiesOnEmptyGroupLegalTest(String year, String renditionStatus, String docNumber, String level)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test Empty Group"));
        groupLevel = level;
        String unassigned = "Unassigned";
        String empty = groupNames.get(0);
        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";

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
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(empty);
        sectionGroupingFooterPage().clickGroupingApply();

        sourcePage().goToSourcePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().switchToGroupingTab(groupLevel);

        sourceNavigateGridPage().rightClickRenditionByGroupName(empty);
        boolean propertiesWindowOpened = renditionContextMenu().openPropertiesByGrouping(groupLevel);

        sourcePage().switchToGroupingNavigatePage(groupLevel);
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(propertiesWindowOpened, "Property window opened")
        );
    }

    /**
     * STORY:  N/A <br>
     * SUMMARY: Changes the baseline after assigning sections/deltas to a group <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionFiltering")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingRenditionBaselineGroupsAlertLegalTest(String year, String renditionStatus, String docNumber, String level)
    {
        groupNames = new ArrayList<>(Arrays.asList("Baselines Test"));
        groupLevel = level;
        String unassigned = "Unassigned";
        String baselineGroupName = groupNames.get(0);
        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().unlockFirstRendition();

        renditionUuid = sourceNavigateGridPage().getUUID();
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(baselineGroupName);

        sectionGroupingSectionGridPage().clickXSectionsInARange(1,3);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(1);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(baselineGroupName);
        sectionGroupingFooterPage().clickGroupingApply();

        sourcePage().goToSourcePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewRenditionBaselines();
        String originalBaseline = viewBaselinesNavigatePage().getSecondBaseline();
        viewBaselinesNavigatePage().rightClickRowX("2");
        viewBaselinesNavigatePage().selectRestoreBaseline();
        boolean alertAppeared = viewBaselinesNavigatePage().acceptGroupingAlert();
        viewBaselinesNavigatePage().waitForGridRefresh();
        boolean baselineRestored = viewBaselinesNavigatePage().baselineWasRestoredFromX(originalBaseline);

        viewBaselinesNavigatePage().breakOutOfFrame();
        viewBaselinesNavigatePage().closeCurrentWindowIgnoreDialogue();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(alertAppeared, "Alert appeared"),
            () -> Assertions.assertTrue(baselineRestored, "Baseline was restored to the previous version")
        );
    }

    /**
     * STORY:  N/A <br>
     * SUMMARY: Checks the effective date in several locations <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionFiltering")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingEditEffectiveDatesLegalTest(String year, String renditionStatus, String docNumber, String level)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test1"));
        groupLevel = level;
        String unassigned = "Unassigned";
        String test1 = groupNames.get(0);
        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";

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
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);

        sectionGroupingSectionGridPage().clickXSectionsInARange(1,3);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(1);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(test1);
        sectionGroupingFooterPage().clickGroupingApply();

        sourcePage().goToSourcePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().switchToGroupingTab(groupLevel);
        sourceNavigateGridPage().rightClickRenditionByGroupName(test1);
        boolean effectiveDateOpen = renditionContextMenu().editEffectiveDate();
        deltaEffectiveDatePage().setCurrentDateInEffectiveDateCalender();
        deltaEffectiveDatePage().pressSave();
        boolean checkEffectiveDateInGrid = sourceNavigateGridPage().checkIfTodaysDateIsOnGroupX(test1);

        sourceNavigateGridPage().rightClickRenditionByGroupName(test1);
        renditionContextMenu().openEditorByGrouping(groupLevel);
        boolean checkEffectiveDateInEditor = editorTextPage().checkEffectiveDateInEditor(groupLevel);
        editorPage().closeDocumentWithNoChanges();

        sourcePage().switchToGroupingNavigatePage(groupLevel);
        sourceNavigateGridPage().rightClickRenditionByGroupName(test1);
        renditionContextMenu().openPropertiesByGrouping(groupLevel);
        boolean checkEffectiveDateInProperties = groupingPropertiesPage().checkEffectiveDateIsToday();

        groupingPropertiesPage().closeProperties();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(effectiveDateOpen, "Effective date open"),
            () -> Assertions.assertTrue(checkEffectiveDateInGrid, "Effective date was in grid"),
            () -> Assertions.assertTrue(checkEffectiveDateInEditor, "Effective date was in editor"),
            () -> Assertions.assertTrue(checkEffectiveDateInProperties, "Effective date was in Properties")
        );
    }

    /**
     * STORY:  N/A <br>
     * SUMMARY: Checks to see multiple section/deltas in the editor <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionFiltering")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingViewGroupLegalTest(String year, String renditionStatus, String docNumber, String level)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test1"));
        groupLevel = level;
        String unassigned = "Unassigned";
        String test1 = groupNames.get(0);
        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";

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
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);

        sectionGroupingSectionGridPage().clickXSectionsInARange(1,3);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(1);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(test1);
        sectionGroupingFooterPage().clickGroupingApply();

        sourcePage().goToSourcePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().switchToGroupingTab(groupLevel);
        sourceNavigateGridPage().rightClickRenditionByGroupName(test1);
        boolean editorAppeared = renditionContextMenu().openEditorByGrouping(groupLevel);

        editorPage().waitForPageLoaded();
        editorTextPage().waitForGridRefresh();
        boolean expectedNumberOfSectionsExist = editorTextPage().numberOfSectionByGrouping(groupLevel) == 3;
        editorPage().closeDocumentWithNoChanges();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(editorAppeared, "Editor did appear"),
            () -> Assertions.assertTrue(expectedNumberOfSectionsExist, "There were 3 sections")
        );
    }

    /**
     * STORY:  N/A <br>
     * SUMMARY: Checks to see multiple section/deltas in the editor <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionFiltering")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingModifyAssignUserLegalTest(String year, String renditionStatus, String docNumber, String level)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test1"));
        groupLevel = level;
        String unassigned = "Unassigned";
        String test1 = groupNames.get(0);
        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";
        String user = user().getFirstname() + " " + user().getLastname();

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
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);

        sectionGroupingSectionGridPage().clickXSectionsInARange(1,3);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(1);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(test1);
        sectionGroupingFooterPage().clickGroupingApply();

        sourcePage().goToSourcePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().switchToGroupingTab(groupLevel);
        sourceNavigateGridPage().rightClickRenditionByGroupName(test1);
        renditionContextMenu().openModifyAssignUser();

        assignUserPage().selectAssignedUserDropdown(user);
        assignUserPage().setAssignedDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        assignUserPage().submitAssignedUser();

        sourcePage().switchToGroupingNavigatePage(groupLevel);
        sourceNavigateGridPage().rightClickRenditionByGroupName(test1);
        renditionContextMenu().openPropertiesByGrouping(groupLevel);
        boolean effectiveDateIsInProperties = groupingPropertiesPage().checkAssignedDateIsToday();
        boolean assignedUserIsInProperties = groupingPropertiesPage().checkAssignedUser(user) && effectiveDateIsInProperties;

        groupingPropertiesPage().closeProperties();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(assignedUserIsInProperties, "User was in properties")
        );
    }

    /**
     * STORY:  N/A <br>
     * SUMMARY: Checks to see multiple section/deltas in the editor <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionFiltering")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingEditAndSaveChangesLegalTest(String year, String renditionStatus, String docNumber, String level)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test1"));
        groupLevel = level;
        String unassigned = "Unassigned";
        String test1 = groupNames.get(0);
        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().unlockFirstRendition();

        renditionUuid = sourceNavigateGridPage().getUUID();
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);

        sectionGroupingSectionGridPage().clickXSectionsInARange(1,3);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(1);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(test1);
        sectionGroupingFooterPage().clickGroupingApply();

        sourcePage().goToSourcePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().switchToGroupingTab(groupLevel);
        sourceNavigateGridPage().rightClickRenditionByGroupName(test1);
        boolean editorAppeared = renditionContextMenu().openEditorByGrouping(groupLevel);

        editorPage().waitForPageLoaded();
        editorPage().waitForGridRefresh();
        boolean expectedNumberOfSectionsExist = editorTextPage().numberOfSectionByGrouping(groupLevel) == 3;
        editorPage().switchDirectlyToTextFrame();
        editorTextPage().insertPhraseIntoTextParagraphByNumber("Test" + DateAndTimeUtils.getCurrentDateMMddyyyy(), "1");
        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();
        boolean checkInWindowClosed = sourcePage().checkWindowIsClosed("Common Editor");

        Assertions.assertAll
        (
            ()-> Assertions.assertTrue(editorAppeared, "Editor did appear"),
            () -> Assertions.assertTrue(expectedNumberOfSectionsExist, "There were 3 sections"),
            () -> Assertions.assertTrue(checkInWindowClosed, "Check in window did close")
        );
    }

    /**
     * STORY: N/A <br>
     * SUMMARY: Checks the different values of the toolbars and makes sure they are correct <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionUuid")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingFilteringSortingTest(String level, String uuid)
    {
        groupNames = new ArrayList<>();
        groupLevel = level;
        renditionUuid = uuid;
        String sectionNumber = "5";
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        int numberOfItemsBeforeFilter = sectionGroupingSectionGridPage().getListOfItemsInGrid();
        sectionGroupingFiltersAndSortsPage().setSectionNumberFilter(sectionNumber);

        int numberOfItemsAfterFilter = sectionGroupingSectionGridPage().getListOfItemsInGrid();
        sectionGroupingToolsPage().clearFilters();

        sectionGroupingFiltersAndSortsPage().sortSectionNumberAscending();
        boolean ascSorted = sectionGroupingSectionGridPage().checkSectionNumberSort(SectionGroupingSectionGridPage.SortOrder.ASC);

        sectionGroupingFiltersAndSortsPage().sortSectionNumberDescending();
        boolean desSorted = sectionGroupingSectionGridPage().checkSectionNumberSort(SectionGroupingSectionGridPage.SortOrder.DESC);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(numberOfItemsBeforeFilter > numberOfItemsAfterFilter, "Filter did Filter"),
            () -> Assertions.assertTrue(ascSorted, "Was sorted by ascending"),
            () -> Assertions.assertTrue(desSorted, "Was sorted by Descending")
        );
    }

    /**
     * STORY:  N/A <br>
     * SUMMARY: Checks to see if creating a custom view works on both a section and delta tab <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionFiltering")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingViewCreationSectionGroupsLegalTest(String year, String renditionStatus, String docNumber, String level)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test1", "Test2", "Test3"));
        groupLevel = level;
        String unassigned = "Unassigned";
        String test1 = groupNames.get(0);
        String test2 = groupNames.get(1);
        String test3 = groupNames.get(2);
        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";
        String viewFilterType = "Content Set";
        String viewFilterValue = "Iowa (Development)";
        String viewSortType = level + " Group Name";
        String viewSortValue = "Descending";
        String viewName = "Test Descending Order";
        List<String> columns = Arrays.asList(level+" Group Started",
                level+" Group Started By",
                level+" Group Completed",
                level+" Group Completed By",
                level+" Group Review Started",
                level+" Group Review Started By",
                level+" Group Review Completed",
                level+" Group Review Completed By");

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
        for (String groupName : groupNames)
        {
            sectionGroupingGroupGridPage().deleteGroupThroughDatabase(groupName, groupLevel, renditionUuid);
        }
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);
        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test2);
        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test3);

        sectionGroupingSectionGridPage().clickXSectionsInARange(1,1);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(1);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(test1);

        sectionGroupingSectionGridPage().clickXSectionsInARange(2,2);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(2);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(test2);

        sectionGroupingSectionGridPage().clickXSectionsInARange(3,3);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(3);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(test3);
        sectionGroupingFooterPage().clickGroupingApply();

        sourcePage().goToSourcePage();
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().switchToGroupingTab(groupLevel);

        boolean viewManagementIsOpen = sourceNavigateFooterToolsPage().clickTheViewManager();
        viewManagementPage().openViewCreationWizard();
        viewWizardPage().addFilterToView(viewFilterType, viewFilterValue);
        viewWizardPage().clickNext();
        viewWizardPage().addSortToView(viewSortType, viewSortValue);
        viewWizardPage().clickNext();
        viewWizardPage().clickAddColumnsButton();
        viewWizardPage().clickNext();
        viewWizardPage().setViewname(viewName);
        viewWizardPage().setViewVisibilityToPublic();
        viewWizardPage().saveView();
        viewManagementPage().selectView(viewName);

        sourcePage().switchToGroupingNavigatePage(groupLevel);
        boolean checkFirstGroupPositionInGrid = sourceNavigateGridPage().checkPositionOfGroup(4,test1);
        boolean checkLastGroupPositionInGrid = sourceNavigateGridPage().checkPositionOfGroup(2,test3);

        boolean checkColumns = sourceNavigateGridPage().checkColumnsInGrid(columns);

        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
        SourceDatabaseUtils.deleteView(connection, viewName);
        BaseDatabaseUtils.disconnect(connection);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(viewManagementIsOpen, "View Management opened"),
            () -> Assertions.assertTrue(checkFirstGroupPositionInGrid,"First group was in the right location"),
            () -> Assertions.assertTrue(checkLastGroupPositionInGrid, "Last group was in the right position"),
            () -> Assertions.assertTrue(checkColumns, "Every column was displayed")
        );
    }

    /**
     * STORY:  HALCYONST 1194 <br>
     * SUMMARY: Checks the amount of deltas between the delta and section tabs in source navigate <br>
     * USER: Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void multipleSectionNavigatingToDeltaTabLegalTest()
    {
        groupNames = new ArrayList<>();
        groupLevel = "";
        renditionUuid = "";
        String contentSet = "Iowa (Development)";
        String year = "2015";
        String renditionStatus = "APV";
        String docNumber = "500";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);

        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        sourceNavigateGridPage().clickFirstXRenditions(10);

        int deltaCountSectionTab = sourceNavigateGridPage().numberOfDeltasOfSelectedSections();

        sourceNavigateTabsPage().goToDeltaTab();
        sourceNavigateTabsPage().waitForPageLoaded();

        int deltaCountDeltaTab = sourceNavigateGridPage().getDocumentCount();

        Assertions.assertEquals(deltaCountDeltaTab,deltaCountSectionTab, "Number of deltas is the same for Section and Delta tabs");
    }

    /**
     * STORY:  N/A <br>
     * SUMMARY: Checks the number of deltas in the next page in the delta grouping window <br>
     * USER: Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltaGroupAddDeltasToGroupPaginationLegalTest()
    {
        int deltasCount = 3;
        renditionUuid = "I80A99D218F0711E498059BFCC4D08232";
        groupLevel = "Delta";
        groupNames = new ArrayList<>(Arrays.asList("Test1"));
        String test1 = groupNames.get(0);
        String unassigned = "Unassigned";

        homePage().goToHomePage();
        loginPage().logIn();
        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);

        sectionGroupingSectionGridPage().goToNextPage();

        sectionGroupingSectionGridPage().clickXSectionsInARange(1,deltasCount);
        sectionGroupingSectionGridPage().rightClickHighlightedSection(1);
        sectionGroupingSectionGridPage().openMoveToGroupContextMenu(test1);

        boolean stayedOnTheSamePageAfterApply = !sectionGroupingSectionGridPage().sectionGroupSecondPageIsEmpty();

        boolean deltaGroupContainsAllDeltas = !sectionGroupingSectionGridPage().sectionGroupingDeltaCountInNewGroupNotEmpty(test1,deltasCount);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(stayedOnTheSamePageAfterApply, "The page did not changed after apply"),
            () -> Assertions.assertTrue(deltaGroupContainsAllDeltas,"Not all deltas are in the group window")
        );
    }

    /**
     * STORY:  HALCYONST-1629 <br>
     * SUMMARY: Checks what items are highlighted after clicking and rightClicking <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionUuid")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingRightClickCheckSelectingSectionsDeltasPaneLegalTest(String level, String uuid)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test1"));
        groupLevel = level;
        renditionUuid = uuid;
        String test1 = groupNames.get(0);
        String unassigned = "Unassigned";

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);
        sectionGroupingGroupGridPage().rightClickGroupingOnPosition("1");
        boolean rightClickWithoutClicked = sectionGroupContextMenu().doesElementExist(SectionGroupContextMenuElements.SECTION_GROUPING_MOVE_TO_GROUP) && sectionGroupingGroupGridPage().doesHighlightedGroupOnPositionExist("1");

        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);
        sectionGroupingGroupGridPage().clickGroupOnPosition("2");
        sectionGroupingGroupGridPage().rightClickGroupingOnPosition("1");
        boolean rightClickWithClicked = sectionGroupContextMenu().doesElementExist(SectionGroupContextMenuElements.SECTION_GROUPING_MOVE_TO_GROUP) && sectionGroupingGroupGridPage().doesHighlightedGroupOnPositionExist("1");

        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);
        sectionGroupingGroupGridPage().rightClickGroupingOnPosition("2");
        sectionGroupContextMenu().moveToGroupX(test1);
        boolean rightClickWithRightClicked = sectionGroupingGroupGridPage().checkDeltaSectionGroupOnPosition(test1,2);

        sectionGroupingGroupGridPage().rightClickGroupingOnPosition("1");
        rightClickWithRightClicked &= sectionGroupingGroupGridPage().doesHighlightedGroupOnPositionExist("1");
        sectionGroupContextMenu().moveToGroupX(test1);
        rightClickWithRightClicked &= sectionGroupingGroupGridPage().checkDeltaSectionGroupOnPosition(test1,1);

        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);
        sectionGroupingGroupGridPage().clickGroupOnPosition("2");
        sectionGroupingGroupGridPage().rightClickGroupingOnPosition("2");
        boolean clickWithRightClicked = sectionGroupContextMenu().doesElementExist(SectionGroupContextMenuElements.SECTION_GROUPING_MOVE_TO_GROUP);
        sectionGroupingGroupGridPage().clickGroupOnPosition("1");
        clickWithRightClicked &= sectionGroupingGroupGridPage().doesHighlightedGroupOnPositionExist("1");

        boolean rightClickWithRightClickedResult = rightClickWithRightClicked;
        boolean clickWithRightClickedResult = clickWithRightClicked;
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(rightClickWithoutClicked, "Item was highlighted after right click without an item selected previously"),
            () -> Assertions.assertTrue(rightClickWithClicked, "Item was highlighted after right click with an item selected previously"),
            () -> Assertions.assertTrue(rightClickWithRightClickedResult, "Item was highlighted after right click with an item right clicked previously"),
            () -> Assertions.assertTrue(clickWithRightClickedResult, "Item was highlighted after click with an item right clicked previously")
        );
    }

    /**
     * STORY:  HALCYONST-1629 <br>
     * SUMMARY: Checks what items are highlighted after clicking and rightClicking <br>
     * USER: Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("renditionUuid")
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void groupingRightClickCheckSelectingGroupsPaneLegalTest(String level, String uuid)
    {
        groupNames = new ArrayList<>(Arrays.asList("Test1", "Test2"));
        groupLevel = level;
        renditionUuid = uuid;
        String test1 = groupNames.get(0);
        String unassigned = "Unassigned";
        String test2 = groupNames.get(1);

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);
        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);

        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().rightClickGroupName(test1);
        boolean rightClickWithClicked = sectionGroupContextMenu().doesElementExist(SectionGroupContextMenuElements.SECTION_GROUPING_REMOVE_GROUP) &&
                sectionGroupingGroupGridPage().checkSelectedGroupText(test1);

        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);
        if(sectionGroupingGroupGridPage().doesGroupExist(test1))
        {
            sectionGroupingGroupGridPage().clickGroup(test1);
            sectionGroupingFooterPage().clickGroupingRemove();
            sectionGroupingFooterPage().clickGroupingYes();
            sectionGroupingFooterPage().clickGroupingApply();
        }
        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);
        if(sectionGroupingGroupGridPage().doesGroupExist(test2))
        {
            sectionGroupingGroupGridPage().clickGroup(test2);
            sectionGroupingFooterPage().clickGroupingRemove();
            sectionGroupingFooterPage().clickGroupingYes();
            sectionGroupingFooterPage().clickGroupingApply();
        }
        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test2);

        sectionGroupingGroupGridPage().rightClickGroupName(test2);
        sectionGroupContextMenu().moveGroupUp();
        sectionGroupingGroupGridPage().rightClickGroupName(test1);
        boolean rightClickWithRightClicked = sectionGroupingGroupGridPage().checkSelectedGroupText(test1);
        sectionGroupContextMenu().moveGroupUp();
        rightClickWithRightClicked &= sectionGroupingGroupGridPage().checkGroupPosition(test1, "2");

        homePage().openGroupingForRenditionWithUuid(groupLevel, renditionUuid);
        if(sectionGroupingGroupGridPage().doesGroupExist(test1))
        {
            sectionGroupingGroupGridPage().clickGroup(test1);
            sectionGroupingFooterPage().clickGroupingRemove();
            sectionGroupingFooterPage().clickGroupingYes();
            sectionGroupingFooterPage().clickGroupingApply();
        }
        sectionGroupingGroupGridPage().clickGroup(unassigned);
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(test1);

        sectionGroupingGroupGridPage().rightClickGroupName(test1);
        boolean clickWithRightClicked = sectionGroupContextMenu().doesElementExist(SectionGroupContextMenuElements.SECTION_GROUPING_REMOVE_GROUP);
        sectionGroupingGroupGridPage().clickGroup(unassigned);
        clickWithRightClicked &= !sectionGroupContextMenu().doesElementExist(SectionGroupContextMenuElements.SECTION_GROUPING_REMOVE_GROUP)
                && sectionGroupingGroupGridPage().checkSelectedGroupText(unassigned);

        boolean rightClickWithRightClickedResult = rightClickWithRightClicked;
        boolean clickWithRightClickedResult = clickWithRightClicked;
        Assertions.assertAll
        (
            () -> Assertions.assertTrue(rightClickWithClicked, "Item was highlighted after right click with an item selected previously"),
            () -> Assertions.assertTrue(rightClickWithRightClickedResult, "Item was highlighted after right click with an item right clicked previously"),
            () -> Assertions.assertTrue(clickWithRightClickedResult, "Item was highlighted after click with an item right clicked previously")
        );
    }


    /**
     * STORY: HALCYONST-3646, HALCYONST-3166, HALCYONST-3851<br>
     * SUMMARY: Adds 1000+ sections and deltas to a group and verifies that the section and delta counts for the group are saved successfully <br>
     * USER: Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void add1000PlusSectionsAndDeltasToSectionGroupLegalTest()
    {
        groupLevel = "Section";
        renditionUuid = "I180C5DF0237C11E7A58F64006A5B4ACF";
        groupNames = new ArrayList<>(Arrays.asList("1000+Sections/DeltasTest"));
        int sectionCount = 0;
        int currentDeltaCount = 0;
        int previousDeltaCount;
        int currentPage = 1;

        //Logging in to the home page to start the test
        homePage().goToHomePage();
        loginPage().logIn();

        sourceMenu().goToSourceC2012Navigate();
        boolean didSearchPageAppear = sourceNavigateFooterToolsPage().clickMagnifyingGlass();
        Assertions.assertTrue(didSearchPageAppear, "The search page did not appear when it was expected");
        documentSearchFilterPage().setRenditionUuidFilter(renditionUuid);
        documentSearchFilterPage().clickRenditionUuidFilterButton();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifySectionGrouping();

        //Creating Section group for test
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(groupNames.get(0));

        //Looping through to add 1000+ sections to created section group
        while(sectionCount < 1001)
        {
            sectionGroupingToolsPage().click(SectionGroupingToolsPageElements.selectAllOnPageButton);
            sectionGroupingSectionGridPage().rightClickHighlightedSection(1);
            sectionGroupingSectionGridPage().openMoveToGroupContextMenu(groupNames.get(0));

            //updating delta and section counts to check when we reopen the SectionGroupingSection Page again
            previousDeltaCount = currentDeltaCount;
            currentDeltaCount = sectionGroupingSectionGridPage().getGroupDeltaCountByGroupName(groupNames.get(0));
            sectionCount = sectionGroupingSectionGridPage().getGroupSectionCountByGroupName(groupNames.get(0));

            boolean isSectionCountUpdated = (sectionCount == 250 * (currentPage));
            boolean isDeltaCountUpdated = (currentDeltaCount >= previousDeltaCount);
            Assertions.assertTrue(isSectionCountUpdated, "The section count was not updated after adding sections to the section group.\nExpected: " + 250 * (currentPage++) + "\nActual: " + sectionCount);
            Assertions.assertTrue(isDeltaCountUpdated, "The delta count was not updated after adding sections to the section group.");
            sectionGroupingSectionGridPage().goToNextPage();
        }

        sectionGroupingToolsPage().clickApplyAndClose();
        sectionGroupingSectionGridPage().waitForWindowGoneByTitle(SectionGroupingPageElements.SECTION_GROUPING_PAGE_TITLE);
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifySectionGrouping();
        //Final boolean check once we have broken 1000+ sections added to the group
        boolean isSectionCountPreserved = (sectionCount == sectionGroupingSectionGridPage().getGroupSectionCountByGroupName(groupNames.get(0)));
        boolean isDeltaCountPreserved = (currentDeltaCount == sectionGroupingSectionGridPage().getGroupDeltaCountByGroupName(groupNames.get(0)));

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isSectionCountPreserved, "The section count was not saved after clicking Apply and Close"),
            () -> Assertions.assertTrue(isDeltaCountPreserved, "The delta count was not saved after clicking Apply and Close")
        );
    }

    @AfterEach
    public void deleteGroupingGroup()
    {
        for (String groupName : groupNames)
        {
            sectionGroupingGroupGridPage().deleteGroupThroughDatabase(groupName, groupLevel, renditionUuid);
        }
    }
}