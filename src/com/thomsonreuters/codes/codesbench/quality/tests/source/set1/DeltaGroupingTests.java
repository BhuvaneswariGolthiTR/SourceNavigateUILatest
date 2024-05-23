package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.SourceNavigateGridPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class DeltaGroupingTests extends TestService
{
    String level = "Delta";
    String uuid;
    ArrayList<String> groupNames;
    /**
     * STORY/BUG - HALCYONST-5343 <br>
     * SUMMARY - A test to verify that we can filter and sort on each column in the Section/Delta Grouping UIs,
     * and that filtering and sorting works correctly. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltaGroupingFilterSortTest()
    {
        groupNames = new ArrayList<>();
        uuid = "";
        String deletedNotDeleted = "Not Deleted";
        String contentSet = "USCA(Development)";
        String year = "2013";
        String renditionStatus = "APV";
        String contentType = "LAW";
        String docNumber = "3370";

        String sectionNumberFilter = "8";
        String deltaLevelFilter = "A";
        String actionFilter = "T";

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        // Filter for our document
        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deletedNotDeleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        // Right click our document, then modify -> delta grouping
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifyDeltaGrouping();

        // Set filter for section number and record how many rows appear after
        deltaGroupingFiltersAndSortsPage().setSectionNumberFilter(sectionNumberFilter);
        boolean sectionNumberColumnOnlyContainsFilter = sourceNavigateGridPage().checkDeltaGroupingDeltaSectionNumberFilter(sectionNumberFilter);

        // Sort the section number column to be descending and pull the first row section number
        deltaGroupingFiltersAndSortsPage().sortSectionNumberDescending();
        boolean sectionNumberSortedDescending = sourceNavigateGridPage().checkDeltaGroupingDeltaSectionNumberSort(SourceNavigateGridPage.SortOrder.DESC);

        // Clear all filters and sorts
        deltaGroupingToolsPage().clearFilters();
        deltaGroupingToolsPage().clearSort();

        // Set filter for delta level and record how many rows appear after
        deltaGroupingFiltersAndSortsPage().setDeltaLevelFilter(deltaLevelFilter);
        boolean deltaLevelColumnOnlyContainsFilter = sourceNavigateGridPage().checkDeltaGroupingDeltaLevelFilter(deltaLevelFilter);

        // Sort the delta level column to be ascending and pull the first row delta level
        deltaGroupingFiltersAndSortsPage().sortDeltaLevelAscending();
        boolean deltaLevelSortedAscending = sourceNavigateGridPage().checkDeltaGroupingDeltaLevelSort(SourceNavigateGridPage.SortOrder.ASC);

        // Clear all filters and sorts
        deltaGroupingToolsPage().clearFilters();
        deltaGroupingToolsPage().clearSort();

        // Set filter for action level and record how many rows appear after
        deltaGroupingFiltersAndSortsPage().setActionFilter(actionFilter);
        boolean actionColumnOnlyContainsFilter = sourceNavigateGridPage().checkDeltaGroupingDeltaActionFilter(actionFilter);

        // Sort the action column to be descending and pull the first row action
        deltaGroupingFiltersAndSortsPage().sortActionDescending();
        boolean actionSortedDescending = sourceNavigateGridPage().checkDeltaGroupingDeltaActionSort(SourceNavigateGridPage.SortOrder.DESC);

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(sectionNumberColumnOnlyContainsFilter, "All values under section number contain our filter"),
                () -> Assertions.assertTrue(deltaLevelColumnOnlyContainsFilter, "All values under delta level contain our filter"),
                () -> Assertions.assertTrue(actionColumnOnlyContainsFilter, "All values under action contain our filter"),
                () -> Assertions.assertTrue(sectionNumberSortedDescending, "section number column is sorted descending"),
                () -> Assertions.assertTrue(deltaLevelSortedAscending, "delta level column is sorted ascending"),
                () -> Assertions.assertTrue(actionSortedDescending, "action column is sorted descending")
        );
    }

    /**
     * STORY/BUG - HALCYONST-5348 <br>
     * SUMMARY - A test to verify that we can filter and sort on each column in the Section/Delta Grouping UIs,
     * and that filtering and sorting stays after hitting apply. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltaGroupingApplySavesChangesInUiTest()
    {
        groupNames = new ArrayList<>(Arrays.asList("group1", "group2"));
        String deletedNotDeleted = "Not Deleted";
        String contentSet = "USCA(Development)";
        String year = "2013";
        String renditionStatus = "APV";
        String contentType = "LAW";
        String docNumber = "3370";

        String group1 = groupNames.get(0);
        String group2 = groupNames.get(1);

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        // Filter for our document
        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deletedNotDeleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus(renditionStatus);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        uuid = sourceNavigateGridPage().getUUID();

        // Right click our document, then modify -> delta grouping
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifyDeltaGrouping();

        // Remove groups if they exist
        deltaGroupingToolsPage().removeGroup(group1);
        deltaGroupingToolsPage().removeGroup(group2);

        // Create our groups
        sectionGroupingGroupGridPage().clickGroup("Unassigned");
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(group1);
        sectionGroupingGroupGridPage().clickGroup("Unassigned");
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(group2);

        // Select the first three deltas and move them to one of our groups
        sourceNavigateGridPage().selectDeltasInRange(1, 3);
        sourceNavigateGridPage().moveSelectedDeltasToGroup(group1);

        // Set our group name filter
        deltaGroupingFiltersAndSortsPage().setGroupNameFilter(group1);

        // Sort the section number column to be descending
        deltaGroupingFiltersAndSortsPage().sortSectionNumberDescending();

        boolean groupNameColumnOnlyContainsFilter = sourceNavigateGridPage().checkDeltaGroupingDeltaGroupNameFilter(group1);
        boolean sectionNumberSortedDescending = sourceNavigateGridPage().checkDeltaGroupingDeltaSectionNumberSort(SourceNavigateGridPage.SortOrder.DESC);

        // Click apply
        sectionGroupingToolsPage().clickApply();

        // Pull the section number values of the first row and pull the number of rows that appear after we hit apply
        boolean groupNameColumnOnlyContainsFilterAfterApply = sourceNavigateGridPage().checkDeltaGroupingDeltaGroupNameFilter(group1);
        boolean sectionNumberSortedDescendingAfterApply = sourceNavigateGridPage().checkDeltaGroupingDeltaSectionNumberSort(SourceNavigateGridPage.SortOrder.DESC);

        boolean doesGroup1ExistAfterApply = sourceNavigateGridPage().doesDeltaGroupingGroupExist("group1");
        boolean doesGroup2ExistAfterApply = sourceNavigateGridPage().doesDeltaGroupingGroupExist("group2");

        // Click apply and close
        sectionGroupingToolsPage().clickApplyAndClose();

        Assertions.assertAll
        (
                () -> Assertions.assertTrue(groupNameColumnOnlyContainsFilter, "Group name column only contains filtered data."),
                () -> Assertions.assertTrue(sectionNumberSortedDescending, "Section Number sorted descending"),
                () -> Assertions.assertTrue(groupNameColumnOnlyContainsFilterAfterApply, "Group name column only contains filter after apply."),
                () -> Assertions.assertTrue(sectionNumberSortedDescendingAfterApply, "Section number sorted descending after apply"),
                () -> Assertions.assertTrue(doesGroup1ExistAfterApply, "group1 appears after apply."),
                () -> Assertions.assertTrue(doesGroup2ExistAfterApply, "group2 appears after apply.")
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
