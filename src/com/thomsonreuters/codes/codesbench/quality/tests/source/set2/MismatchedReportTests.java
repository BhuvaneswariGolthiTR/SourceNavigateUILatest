package com.thomsonreuters.codes.codesbench.quality.tests.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.MismatchedReportGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MismatchedReportTests extends TestService
{
    /**
     * STORY: NA <br>
     * SUMMARY: checks the mismatch report loads data according to a date<br>
     * USER:  LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void mismatchedReportTest()
    {
        String contentSet = "Iowa (Development)";
        String year = "2011";
        String contentType = "BILLTRACK";
        String docNumber = "2459";
        String docType = "HF";

        String misMatchContentSet = "IA";

        String dayNumber = "16";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFiltersAndSortsPage().setFilterDocType(docType);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        clamshellPage().openSideBarReport();
        renditionTabReportClamshellPage().clickMismatchedReport(true,false);
        boolean gridEmpty = mismatchedReportGridPage().doesElementExist(MismatchedReportGridPageElements.MISMATCHED_REPORT_NO_RECORDS_FOUND);
        Assertions.assertFalse(gridEmpty, "Verifying that No records found message not appeared in the grid");

        mismatchedReportFiltersPage().setFilterYear(year);
        mismatchedReportFiltersPage().setFilterContentSet(misMatchContentSet);
        mismatchedReportFiltersPage().setFilterDocType(docType);
        mismatchedReportFiltersPage().setFilterDocNumber(docNumber);
        mismatchedReportFooterPage().refreshTheGrid();//Check to see if there is a hidden refreshButton

        boolean filteredDocumentAppears = mismatchedReportGridPage().getDocumentCount() == 1;
        Assertions.assertTrue(filteredDocumentAppears, "Verifying that the document appeared in the mismatched report");

        mismatchedReportFiltersPage().clickSourceLoadDateCalendar();
        dateSearchFilterPage().clickSearchFilterCalendar();

        //Returns String "Previous Month (<Previous Month> <Year>) <Current Month> <Year> Next Month (<Next Month> <Year>)"
        String monthAndYearLong = dateSearchFilterPage().getCalendarText();
        //Pulls SubString "<Current Month> <Year>"
        String monthAndYearShort = monthAndYearLong.substring(monthAndYearLong.indexOf(")") + 2, monthAndYearLong.indexOf("Next") - 1);
        //Splits the SubString into another substring "<Current Month>"
        String monthCal = monthAndYearShort.substring(0, monthAndYearShort.indexOf(" "));
        //Turns the month name into its number, i.e. "November" -> "11"
        String monthNumber = DateAndTimeUtils.monthNameToNumber(monthCal);
        //Splits the monthAndYearShort string into another substring for just the year "<Year>"
        String yearCalNumber = monthAndYearShort.substring(monthAndYearShort.indexOf(" ") + 1);

        dateSearchFilterPage().selectDayNumber(dayNumber);
        dateSearchFilterPage().clickSubmitButtonMismatchReportPage();

        boolean sourceLoadDateFilterFilled = mismatchedReportGridPage().getLoadDateTitle().contains(monthNumber + "/" + dayNumber + "/" + yearCalNumber);
        mismatchedReportFiltersPage().clickSourceLoadDateCalendar();

        boolean dateFieldIsStatic = dateSearchFilterPage().getDateText().contains(monthNumber + "/" + dayNumber + "/" + yearCalNumber);
        dateSearchFilterPage().clickCancelButtonMismatchReportPage();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(sourceLoadDateFilterFilled, "Verifying that Source load date Filter filled with date we selected"),
            () -> Assertions.assertTrue(dateFieldIsStatic, "Verifying that Date field filled with date we selected")
        );
    }
}
