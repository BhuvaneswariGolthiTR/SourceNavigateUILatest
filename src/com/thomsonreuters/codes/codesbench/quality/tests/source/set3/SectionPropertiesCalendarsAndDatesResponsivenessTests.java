package com.thomsonreuters.codes.codesbench.quality.tests.source.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SectionPropertiesCalendarsAndDatesResponsivenessTests extends TestService
{
    /**
     * STORY: N/A <br>
     * SUMMARY: tests the section properties calendars to make sure they appear and disappear in 2 seconds.<br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void secPropCalendarsAndDatesLegalTests()
    {
        String validation = "None";
        String multipleDuplicate = "None";
        String deleted = "Not Deleted";
        String contentSet = "Iowa (Development)";
        String year = "2016";
        int day = 19;

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterValidation(validation);
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate(multipleDuplicate);
        sourceNavigateFiltersAndSortsPage().setFilterDeleted(deleted);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();
        sourceNavigateGridPage().rightClickFirstRendition();

        sectionContextMenu().openSectionProperties();

        boolean showSectionEffectiveDateCalendarAppears = sectionPropertiesPage().openSectionEffectiveDateCalendar();
        boolean showSectionEffectiveDateCalendarDisappears = sectionPropertiesPage().clickCalendarDay(day);

        boolean showAssignedToDateCalendarAppears = sectionPropertiesPage().openShowAssignedToDateCalendar();
        boolean showAssignedToDateCalendarDisappears = renditionPropertiesPage().clickCalendarDay(day);

        sectionPropertiesPage().goToProposedApprovedTrackingInformationTab();

        boolean showTabularRequestedDateCalendarAppears = sectionPropertiesPage().openTabularRequestedDateCalendar();
        boolean showTabularRequestedDateCalendarDisappears = renditionPropertiesPage().clickCalendarDay(day);


        boolean showTabularStartedDateCalendarAppears = renditionPropertiesPage().openTabularStartedDateCalendar();
        boolean showTabularStartedDateCalendarDisappears = renditionPropertiesPage().clickCalendarDay(day);

        sectionPropertiesPage().clickCancel();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(showSectionEffectiveDateCalendarAppears, "Show section effective date calendar appeared in 2 seconds"),
            () ->Assertions.assertTrue(showSectionEffectiveDateCalendarDisappears, "Show section effective date calendar disappeared in 2 seconds"),
            () ->Assertions.assertTrue(showAssignedToDateCalendarAppears, "Show assigned to date calendar disappeared in 2 seconds"),
            () ->Assertions.assertTrue(showAssignedToDateCalendarDisappears, "Show assigned to date calendar disappeared in 2 seconds"),
            () ->Assertions.assertTrue(showTabularRequestedDateCalendarAppears, "Show tabular requested date calendar appeared in 2 seconds"),
            () ->Assertions.assertTrue(showTabularRequestedDateCalendarDisappears, "Show tabular requested date calendar disappeared in 2 seconds"),
            () ->Assertions.assertTrue(showTabularStartedDateCalendarAppears, "Show tabular started date calendar appeared in 2 seconds"),
            () ->Assertions.assertTrue(showTabularStartedDateCalendarDisappears, "Show tabular started date calendar disappeared in 2 seconds")
        );
    }
}
