package com.thomsonreuters.codes.codesbench.quality.tests.source.set2;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RenditionPropertiesCalendarsAndDatesResponsivenessTests extends TestService
{
    /**
     * STORY: NA <br>
     * SUMMARY: checks the properties calendars by going through the context menu<br>
     * USER: LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void renditionPropertiesCalendarsAndDatesLegalTest()
    {
        int day = 19;

        String validation = "None";
        String multipleDuplicate = "None";
        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";
        String year = "2016";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterValidation(validation);
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate(multipleDuplicate);
        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFooterToolsPage().refreshTheGrid();
        sourceNavigateGridPage().unlockFirstRendition();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().openRenditionProperties();

        boolean showApprovalDateCalendarAppears = renditionPropertiesPage().openApprovalDateCalendar();
        boolean showApprovalDateCalendarDisappears = renditionPropertiesPage().clickCalendarDay(day);

        boolean showAssignedDateCalendarAppears = renditionPropertiesPage().openAssignedToDateCalendar();
        boolean showAssignedDateCalendarDisappears = renditionPropertiesPage().clickCalendarDay(day);

        renditionPropertiesPage().goToProposedApprovedTrackingInformationTab();
        boolean showCompareDateCalendarAppears = renditionPropertiesPage().openCompareDateCalendar();
        boolean showCompareDateCalendarDisappears = renditionPropertiesPage().clickCalendarDay(day);


        boolean showTabularStartedDateCalendarAppears = renditionPropertiesPage().openTabularStartedDateCalendar();
        boolean showTabularStartedDateCalendarDisappears = renditionPropertiesPage().clickCalendarDay(day);

        renditionPropertiesPage().clickCancel();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(showApprovalDateCalendarAppears, "Show approval date calendar appeared in 2 seconds"),
            () ->Assertions.assertTrue(showApprovalDateCalendarDisappears, "How approval date calendar disappeared in 2 seconds"),
            () ->Assertions.assertTrue(showAssignedDateCalendarAppears, "Show assigned to date calendar disappeared in 2 seconds"),
            () ->Assertions.assertTrue(showAssignedDateCalendarDisappears, "Show assigned to date calendar disappeared in 2 seconds"),
            () ->Assertions.assertTrue(showCompareDateCalendarAppears, "Show compare date calendar appeared in 2 seconds"),
            () ->Assertions.assertTrue(showCompareDateCalendarDisappears, "Show compare date calendar disappeared in 2 seconds"),
            () ->Assertions.assertTrue(showTabularStartedDateCalendarAppears, "Show tabular started date calendar appeared in 2 seconds"),
            () ->Assertions.assertTrue(showTabularStartedDateCalendarDisappears, "Show tabular started date calendar disappeared in 2 seconds")
        );
    }
}
