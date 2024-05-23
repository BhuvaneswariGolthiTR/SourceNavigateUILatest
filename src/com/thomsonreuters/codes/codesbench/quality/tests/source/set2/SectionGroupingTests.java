package com.thomsonreuters.codes.codesbench.quality.tests.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pages.source.navigate.SourceNavigateGridPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class SectionGroupingTests extends TestService
{
    String level = "Section";
    String uuid;
    ArrayList<String> groupNames;
    /**
     * STORY/BUG - HALCYONST-5343 <br>
     * SUMMARY - A test to verify that we can apply a filter and sort on each column in the Section/Delta Grouping UIs,
     * and that filtering and sorting works correctly. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sectionGroupingFilterSortTest()
    {
        uuid = "";
        groupNames = new ArrayList<>();
        String deletedNotDeleted = "Not Deleted";
        String contentSet = "USCA(Development)";
        String year = "2013";
        String renditionStatus = "APV";
        String contentType = "LAW";
        String docNumber = "3370";

        String sectionNumberFilter = "11";
        String subSectionNumberFilter = "(10)";

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

        // Right click our document, then modify -> section grouping
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifySectionGrouping();

        // Set filter for section number and verify only values with filter appear
        sectionGroupingFiltersAndSortsPage().setSectionNumberFilter(sectionNumberFilter);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        boolean sectionNumberColumnOnlyContainsFilter = sourceNavigateGridPage().checkSectionGroupingSectionNumberFilter(sectionNumberFilter);

        // Sort the section number column to be descending and verify
        sectionGroupingFiltersAndSortsPage().sortSectionNumberDescending();
        boolean sectionNumberSortedDescending = sourceNavigateGridPage().checkSectionGroupingSectionNumberSort(SourceNavigateGridPage.SortOrder.DESC);

        // Clear all filters and sorts
        sectionGroupingToolsPage().clearFilters();
        sectionGroupingToolsPage().clearSort();

        // Set filter for sub section number and verify only values with filter appear
        sectionGroupingFiltersAndSortsPage().setSubSectionNumberFilter(subSectionNumberFilter);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        boolean subSectionNumberColumnOnlyContainsFilter = sourceNavigateGridPage().checkSectionGroupingSectionSubSectionNumberFilter(subSectionNumberFilter);

        // Sort the sub section number column to be descending and verify
        sectionGroupingFiltersAndSortsPage().sortSubSectionNumberDescending();
        boolean subSectionNumberSortedDescending = sourceNavigateGridPage().checkSectionGroupingSectionSubSectionNumberSort(SourceNavigateGridPage.SortOrder.DESC);


        Assertions.assertAll
        (
            () -> Assertions.assertTrue(sectionNumberColumnOnlyContainsFilter, "All values under section number should contain our filter"),
            () -> Assertions.assertTrue(subSectionNumberColumnOnlyContainsFilter, "All values under sub section number should contain our filter"),
            () -> Assertions.assertTrue(sectionNumberSortedDescending, "section number column should be sorted descending"),
            () -> Assertions.assertTrue(subSectionNumberSortedDescending, "sub section number column should be sorted descending")
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
    public void sectionGroupingApplySavesChangesInUiTest()
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

        // Right click our document, then modify -> section grouping
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifySectionGrouping();

        // Create our groups
        sectionGroupingGroupGridPage().clickGroup("Unassigned");
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(group1);
        sectionGroupingGroupGridPage().clickGroup("Unassigned");
        sectionGroupingGroupGridPage().sendEnterToGroup();
        sectionGroupingGroupGridPage().addNewGroup(group2);

        // Select the first three sections and move them to one of our groups
        sourceNavigateGridPage().selectSectionsInRange(1,3);
        sourceNavigateGridPage().moveSelectedSectionsToGroup(group1);

        // Set our group name filter
        sectionGroupingFiltersAndSortsPage().setGroupNameFilter(group1);

        // Sort the section number column to be descending
        sectionGroupingFiltersAndSortsPage().sortSectionNumberDescending();

        // Pull the section number values of the first row and pull the number of rows that appear before we hit apply
        boolean groupNameColumnOnlyContainsFilter = sourceNavigateGridPage().checkSectionGroupingSectionGroupNameFilter(group1);
        boolean sectionNumberSortedDescending = sourceNavigateGridPage().checkSectionGroupingSectionNumberSort(SourceNavigateGridPage.SortOrder.DESC);

        // Click apply
        sectionGroupingToolsPage().clickApply();

        // Pull the section number values of the first row and pull the number of rows that appear after we hit apply
        boolean groupNameColumnOnlyContainsFilterAfterApply = sourceNavigateGridPage().checkSectionGroupingSectionGroupNameFilter(group1);
        boolean sectionNumberSortedDescendingAfterApply = sourceNavigateGridPage().checkSectionGroupingSectionNumberSort(SourceNavigateGridPage.SortOrder.DESC);

        boolean doesGroup1ExistAfterApply = sourceNavigateGridPage().doesSectionGroupingGroupExist("group1");
        boolean doesGroup2ExistAfterApply = sourceNavigateGridPage().doesSectionGroupingGroupExist("group2");

        // Click apply and close
        sectionGroupingToolsPage().clickApplyAndClose();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(groupNameColumnOnlyContainsFilter, "We did see the right group names after filter."),
            () -> Assertions.assertTrue(sectionNumberSortedDescending, "We did see the the section number column sorted correctly."),
            () -> Assertions.assertTrue(groupNameColumnOnlyContainsFilterAfterApply, "We did see the right group names after hitting apply."),
            () -> Assertions.assertTrue(sectionNumberSortedDescendingAfterApply, "We did see the the section number column sorted correctly after apply."),
            () -> Assertions.assertTrue(doesGroup1ExistAfterApply, "We did see group1 after apply."),
            () -> Assertions.assertTrue(doesGroup2ExistAfterApply, "We did see group2 after apply.")
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