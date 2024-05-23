package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports.AutoMergeReportPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports.DetailReportPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports.NoTeamReportPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.reports.SummaryReportPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class ReportsTests extends TestService {

    /**
     * HALCYONST-11060/HALCYONST-11275 - NOD Codes menu option & cases table layout
     * 1. Log onto the Iowa (development) content set
     * 2. Go to NOD Home Page -> Reports -> Detail
     * 3. VERIFY: Detail report window opened and contains expected elements:
     * input date field, date select button, search button, a table with following headers:
     * Title, Case serial, Westlaw, Court, Synopsis Backgrounds
     * 4. Into the select date field type "testtest12"
     * 5. VERIFY: The error message is shown
     * 6. Type new date into the same field: 11/09/2006 and click "Search"
     * 7. VERIFY: Cases 2010-575775 and 2010-345403 are shown
     * 8. VERIFY: Other table fileds are also populated correctly
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void detailReportLayoutTest() {
        String wrongDate = "testtest12";
        String correctDate = "11092006";
        String expectedMessage = "Please make sure you typed the correct date in format mm/dd/yyyy";
        String expectedTitle = "Gamble v. State";
        String expectedCaseSerial = "2010-575775";
        String expectedWestLaw = "2006 WL 3103035";
        String expectedCourt = "Iowa";
        String row = "0";

        // 1. Log onto the Iowa (development) content set
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD Home Page -> Reports -> Detail
        homePageAngular().clickDetail();
        //3. VERIFY: Detail report window opened and contains expected elements:
        //input date field, date select button, search button, a table with following headers:
        //Title, Case serial, Westlaw, Court, Synopsis Backgrounds
        Assertions.assertTrue(detailReportPopupAngular().doesElementExist(DetailReportPopupElementsAngular.PAGE_TITLE), "Detail Report window DID NOT open");
        boolean isInputDateFieldExist = DetailReportPopupElementsAngular.dateInputField.isDisplayed();
        boolean isDateSelectButtonExist = DetailReportPopupElementsAngular.dateSelectButton.isDisplayed();
        boolean isSearchButtonExist = DetailReportPopupElementsAngular.searchBButton.isDisplayed();
        boolean isTitleHeaderExist = DetailReportPopupElementsAngular.title.isDisplayed();
        boolean isCaseSerialExist = DetailReportPopupElementsAngular.caseSerial.isDisplayed();
        boolean isWestlawExist = DetailReportPopupElementsAngular.westlaw.isDisplayed();
        boolean isCourtExist = DetailReportPopupElementsAngular.court.isDisplayed();
        boolean isSynopsisBackgroundExist = DetailReportPopupElementsAngular.synopsisBackground.isDisplayed();
        //4. Into the select date field type "testtest12"
        detailReportPopupAngular().inputDate(wrongDate);
        String actualMessage = detailReportPopupAngular().getInvalidMessageText();
        //6. Type new date into the same field: 11/09/2006 and click "Search"
        detailReportPopupAngular().inputDate(correctDate);
        detailReportPopupAngular().clickSearch();
        //7. VERIFY: Cases 2010-575775 and 2010-345403 are shown
        //8. VERIFY: Other table fileds are also populated correctly
        String actualTitle = detailReportPopupAngular().getTitleByRowIndex(row);
        String actualCaseSerial = detailReportPopupAngular().getCaseSerialByRowIndex(row);
        String actualWestLaw = detailReportPopupAngular().getWestlawByRowIndex(row);
        String actualCourt = detailReportPopupAngular().getCourtByRowIndex(row);

        Assertions.assertAll(
                () -> Assertions.assertTrue(isInputDateFieldExist, "Input date field is NOT displayed"),
                () -> Assertions.assertTrue(isDateSelectButtonExist, "Date select button is NOT displayed"),
                () -> Assertions.assertTrue(isSearchButtonExist, "Search button is NOT displayed"),
                () -> Assertions.assertTrue(isTitleHeaderExist, "Title header is NOT displayed"),
                () -> Assertions.assertTrue(isCaseSerialExist, "Case serial header is NOT displayed"),
                () -> Assertions.assertTrue(isWestlawExist, "Westlaw header is NOT displayed"),
                () -> Assertions.assertTrue(isCourtExist, "Court header is NOT displayed"),
                () -> Assertions.assertTrue(isSynopsisBackgroundExist, "Synopsis Backgrounds is NOT displayed"),
                () -> Assertions.assertEquals(expectedMessage, actualMessage),
                () -> Assertions.assertEquals(expectedTitle, actualTitle),
                () -> Assertions.assertEquals(expectedCaseSerial, actualCaseSerial),
                () -> Assertions.assertEquals(expectedWestLaw, actualWestLaw),
                () -> Assertions.assertEquals(expectedCourt, actualCourt)
        );

    }

    /**
     * HALCYONST-11060/HALCYONST-11275 - NOD Codes menu option & cases table layout
     * 1. Log onto the Iowa (development) content set
     * 2. Go to NOD Home Page -> Reports -> Detail
     * 3. Click date selector button
     * 4. VERIFY: Date selector pop up is shown
     * 5. Click year to select it
     * 6. VERIFY: year selection dropdown is shown. The current year is the last one in dropdown
     * 7. Select year 2006
     * 8. Click month to select it
     * 9. VERIFY: month selection dropdown is shown.
     * 10. Select Novermber
     * 11. VERIFY: calendar for November 2006 is shown
     * 12. Click the 9th of November in calendar
     * 13. VERIFY: Cases 2010-575775 and 2010-345403 are shown
     * 14. VERIFY: Other table fileds are also populated correctly
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void detailReportCalendarSelectionTest() {
        String expectedTitle = "Gamble v. State";
        String expectedCaseSerial = "2010-575775";
        String expectedWestLaw = "2006 WL 3103035";
        String expectedCourt = "Iowa";
        String year = "2006";
        String month = "Nov";
        String date = "Thursday, November 9, 2006";
        String row = "0";

        // 1. Log onto the Iowa (development) content set
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD Home Page -> Reports -> Detail
        homePageAngular().clickDetail();
        //3. Click date selector button
        Assertions.assertTrue(detailReportPopupAngular().doesElementExist(DetailReportPopupElementsAngular.PAGE_TITLE), "Detail Report window DID NOT open");
        detailReportPopupAngular().clickDateSelector();
        //4. VERIFY: Date selector pop up is shown
        Assertions.assertTrue(dateSelectorPopupAngular().isDateSelectorDisplayed(), "Date selector pop up is NOT shown");
        //5. Click year to select it
        //6. VERIFY: year selection dropdown is shown. The current year is the last one in dropdown
        dateSelectorPopupAngular().selectYear(year);
        //8. Click month to select it
        //9. VERIFY: month selection dropdown is shown.
        //10. Select Novermber
        dateSelectorPopupAngular().selectMonth(month);
        //11. VERIFY: calendar for November 2006 is
        //12. Click the 9th of November in calendar
        dateSelectorPopupAngular().selectDate(date);
        detailReportPopupAngular().clickSearch();
        //13. VERIFY: Cases 2010-575775 and 2010-345403 are shown
        //14. VERIFY: Other table fileds are also populated correctly
        String actualTitle = detailReportPopupAngular().getTitleByRowIndex(row);
        String actualCaseSerial = detailReportPopupAngular().getCaseSerialByRowIndex(row);
        String actualWestLaw = detailReportPopupAngular().getWestlawByRowIndex(row);
        String actualCourt = detailReportPopupAngular().getCourtByRowIndex(row);

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedTitle, actualTitle),
                () -> Assertions.assertEquals(expectedCaseSerial, actualCaseSerial),
                () -> Assertions.assertEquals(expectedWestLaw, actualWestLaw),
                () -> Assertions.assertEquals(expectedCourt, actualCourt)
        );

    }

    /**
     * HALCYONST-11061 / HALCYONST-11034 / HALCYONST-11275
     * No Team report - type into date input field
     * US reports doesn't allow to open reports for December
     * No error messages upon validation fail in Auto-merge report
     * 1. Log onto the Iowa (development) content set
     * 2. Go to NOD Home Page -> Reports -> No Team
     * 3. VERIFY: No Team report window opened and contains expected elements:
     * input date field, date select button, search button, a table with following headers:
     * Title, Case serial, Westlaw, Court, R/U, Reloaded, New, Update
     * 4. Into the select date field type "testtest12"
     * 5. VERIFY: The error message is shown
     * 6. Type new date into the same field: 12/09/2006 and click "Search"
     * 7. VERIFY: Cases XXXXXXXXXXXXX are shown
     * 8. VERIFY: Other table fileds are also populated correctly
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void noTeamReportLayoutTest() {
        String wrongDate = "testtest12";
        String correctDate = "11092006";
        String expectedMessage = "Please make sure you typed the correct date in format mm/dd/yyyy";
        String expectedTitle = "Hubbart v. Hormel Foods Corp.";
        String expectedCaseSerial = "2010-505058";
        String expectedWestLaw = "2006 WL 3007512";
        String expectedCourt = "Neb.App.";
        String expectedRU = "R";
        String expectedReloaded = "N";
        String expectedNew = "9";
        String expectedUpdated = "0";
        String row = "0";

        // 1. Log onto the Iowa (development) content set
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD Home Page -> Reports -> No Team Report
        homePageAngular().clickNoTeam();
        //3. VERIFY: No Team report window opened and contains expected elements:
        //input date field, date select button, search button, a table with following headers:
        //Title, Case serial, Westlaw, Court, R/U, Reloaded, New, Update
        Assertions.assertTrue(noTeamReportPopupAngular().doesElementExist(NoTeamReportPopupElementsAngular.PAGE_TITLE), "No Team Report window DID NOT open");
        boolean isInputDateFieldExist = NoTeamReportPopupElementsAngular.dateInputField.isDisplayed();
        boolean isDateSelectButtonExist = NoTeamReportPopupElementsAngular.dateSelectButton.isDisplayed();
        boolean isSearchButtonExist = NoTeamReportPopupElementsAngular.searchBButton.isDisplayed();
        boolean isTitleHeaderExist = NoTeamReportPopupElementsAngular.title.isDisplayed();
        boolean isCaseSerialExist = NoTeamReportPopupElementsAngular.caseSerial.isDisplayed();
        boolean isWestlawExist = NoTeamReportPopupElementsAngular.westlaw.isDisplayed();
        boolean isCourtExist = NoTeamReportPopupElementsAngular.court.isDisplayed();
        boolean isRuExist = NoTeamReportPopupElementsAngular.ru.isDisplayed();
        boolean isReloadedExist = NoTeamReportPopupElementsAngular.reloaded.isDisplayed();
        boolean isNewExist = NoTeamReportPopupElementsAngular.new_report.isDisplayed();
        boolean isUpdateExist = NoTeamReportPopupElementsAngular.update.isDisplayed();

        //4. Into the select date field type "testtest12"
        noTeamReportPopupAngular().inputDate(wrongDate);
        String actualMessage = noTeamReportPopupAngular().getInvalidMessageText();
        //6. Type new date into the same field: 11/09/2006 and click "Search"
        noTeamReportPopupAngular().inputDate(correctDate);
        noTeamReportPopupAngular().clickSearch();
        //7. VERIFY: Cases 2010-575775 and 2010-345403 are shown
        //8. VERIFY: Other table fileds are also populated correctly
        String actualTitle = noTeamReportPopupAngular().getTitleByRowIndex(row);
        String actualCaseSerial = noTeamReportPopupAngular().getCaseSerialByRowIndex(row);
        String actualWestLaw = noTeamReportPopupAngular().getWestlawByRowIndex(row);
        String actualCourt = noTeamReportPopupAngular().getCourtByRowIndex(row);
        String actualRU = noTeamReportPopupAngular().getRuByRowIndex(row);
        String actualReloaded = noTeamReportPopupAngular().getReloadedByRowIndex(row);
        String actualNew = noTeamReportPopupAngular().getNewByRowIndex(row);
        String actualUpdated = noTeamReportPopupAngular().getUpdatedByRowIndex(row);

        Assertions.assertAll(
                () -> Assertions.assertTrue(isInputDateFieldExist, "Input date field is NOT displayed"),
                () -> Assertions.assertTrue(isDateSelectButtonExist, "Date select button is NOT displayed"),
                () -> Assertions.assertTrue(isSearchButtonExist, "Search button is NOT displayed"),
                () -> Assertions.assertTrue(isTitleHeaderExist, "Title header is NOT displayed"),
                () -> Assertions.assertTrue(isCaseSerialExist, "Case serial header is NOT displayed"),
                () -> Assertions.assertTrue(isWestlawExist, "Westlaw header is NOT displayed"),
                () -> Assertions.assertTrue(isCourtExist, "Court header is NOT displayed"),
                () -> Assertions.assertTrue(isRuExist, "R/U header is NOT displayed"),
                () -> Assertions.assertTrue(isReloadedExist, "Reloaded header is NOT displayed"),
                () -> Assertions.assertTrue(isNewExist, "New header is NOT displayed"),
                () -> Assertions.assertTrue(isUpdateExist, "Update header is NOT displayed"),
                () -> Assertions.assertEquals(expectedMessage, actualMessage),
                () -> Assertions.assertEquals(expectedTitle, actualTitle),
                () -> Assertions.assertEquals(expectedCaseSerial, actualCaseSerial),
                () -> Assertions.assertEquals(expectedWestLaw, actualWestLaw),
                () -> Assertions.assertEquals(expectedCourt, actualCourt),
                () -> Assertions.assertEquals(expectedRU, actualRU),
                () -> Assertions.assertEquals(expectedReloaded, actualReloaded),
                () -> Assertions.assertEquals(expectedNew, actualNew),
                () -> Assertions.assertEquals(expectedUpdated, actualUpdated)
        );

    }

    /**
     * HALCYONST-11060/HALCYONST-11275 - No Team report - calendar selector
     * 1. Log onto the Iowa (development) content set
     * 2. Go to NOD Home Page -> Reports -> No Team
     * 3. Click date selector button
     * 4. VERIFY: Date selector pop up is shown
     * 5. Click year to select it
     * 6. VERIFY: year selection dropdown is shown. The current year is the last one in dropdown
     * 7. Select year 2006
     * 8. Click month to select it
     * 9. VERIFY: month selection dropdown is shown.
     * 10. Select December
     * 11. VERIFY: calendar for December 2006 is shown
     * 12. Click the 9th of December in calendar
     * 13. VERIFY: Cases XXXXXXXXXXXXXXXX are shown
     * 14. VERIFY: Other table fileds are also populated correctly
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void noTimeCalendarSelectionTest() {
        String expectedTitle = "Hubbart v. Hormel Foods Corp.";
        String expectedCaseSerial = "2010-505058";
        String expectedWestLaw = "2006 WL 3007512";
        String expectedCourt = "Neb.App.";
        String expectedRU = "R";
        String expectedReloaded = "N";
        String expectedNew = "9";
        String expectedUpdated = "0";
        String row = "0";
        String year = "2006";
        String month = "Nov";
        String date = "Thursday, November 9, 2006";

        // 1. Log onto the Iowa (development) content set
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD Home Page -> Reports -> No Team
        homePageAngular().clickNoTeam();
        //3. Click date selector button
        Assertions.assertTrue(noTeamReportPopupAngular().doesElementExist(NoTeamReportPopupElementsAngular.PAGE_TITLE), "No team Report window DID NOT open");
        noTeamReportPopupAngular().clickDateSelector();
        //4. VERIFY: Date selector pop up is shown
        Assertions.assertTrue(dateSelectorPopupAngular().isDateSelectorDisplayed(), "Date selector pop up is NOT shown");
        //5. Click year to select it
        //6. VERIFY: year selection dropdown is shown. The current year is the last one in dropdown
        dateSelectorPopupAngular().selectYear(year);
        //8. Click month to select it
        //9. VERIFY: month selection dropdown is shown.
        //10. Select Novermber
        dateSelectorPopupAngular().selectMonth(month);
        //11. VERIFY: calendar for November 2006 is
        //12. Click the 9th of November in calendar
        dateSelectorPopupAngular().selectDate(date);
        detailReportPopupAngular().clickSearch();
        //13. VERIFY: Cases 2010-575775 and 2010-345403 are shown
        //14. VERIFY: Other table fileds are also populated correctly
        String actualTitle = noTeamReportPopupAngular().getTitleByRowIndex(row);
        String actualCaseSerial = noTeamReportPopupAngular().getCaseSerialByRowIndex(row);
        String actualWestLaw = noTeamReportPopupAngular().getWestlawByRowIndex(row);
        String actualCourt = noTeamReportPopupAngular().getCourtByRowIndex(row);
        String actualRU = noTeamReportPopupAngular().getRuByRowIndex(row);
        String actualReloaded = noTeamReportPopupAngular().getReloadedByRowIndex(row);
        String actualNew = noTeamReportPopupAngular().getNewByRowIndex(row);
        String actualUpdated = noTeamReportPopupAngular().getUpdatedByRowIndex(row);

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedTitle, actualTitle),
                () -> Assertions.assertEquals(expectedCaseSerial, actualCaseSerial),
                () -> Assertions.assertEquals(expectedWestLaw, actualWestLaw),
                () -> Assertions.assertEquals(expectedCourt, actualCourt),
                () -> Assertions.assertEquals(expectedRU, actualRU),
                () -> Assertions.assertEquals(expectedReloaded, actualReloaded),
                () -> Assertions.assertEquals(expectedNew, actualNew),
                () -> Assertions.assertEquals(expectedUpdated, actualUpdated)
        );

    }

    /**
     * HALCYONST-11062 / HALCYONST-11275 Summary report
     * 1. Log onto the Iowa (development) content set
     * 2. Go to NOD Home Page -> Reports -> Summary
     * 3. VERIFY: Detail report window opened and contains expected elements:
     * input date field, date select button, search button, a table with following headers:
     * Title, Case serial, Westlaw, Court, R/U, Reloaded, New, Update
     * 4. Into the select date field type "testtest12"
     * 5. VERIFY: The error message is shown
     * 6. Type new date into the same field: 12/09/2006 and click "Search"
     * 7. VERIFY: Cases XXXXXXXXXXXXX are shown
     * 8. VERIFY: Other table fileds are also populated correctly
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void summaryReportLayoutTest() {
        String wrongDate = "testtest12";
        String correctDate = "11092006";
        String expectedMessage = "Please make sure you typed the correct date in format mm/dd/yyyy";
        String expectedTitle = "Gamble v. State";
        String expectedCaseSerial = "2010-575775";
        String expectedWestLaw = "2006 WL 3103035";
        String expectedCourt = "Iowa";
        String expectedRU = "R";
        String expectedReloaded = "N";
        String expectedNew = "5";
        String expectedUpdated = "0";
        String row = "0";

        // 1. Log onto the Iowa (development) content set
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD Home Page -> Reports -> Summary
        homePageAngular().clickSummary();
        //3. VERIFY: No Team report window opened and contains expected elements:
        //input date field, date select button, search button, a table with following headers:
        //Title, Case serial, Westlaw, Court, R/U, Reloaded, New, Update
        Assertions.assertTrue(summaryReportPopupAngular().doesElementExist(SummaryReportPopupElementsAngular.PAGE_TITLE), "Summary Report window DID NOT open");
        boolean isInputDateFieldExist = SummaryReportPopupElementsAngular.dateInputField.isDisplayed();
        boolean isDateSelectButtonExist = SummaryReportPopupElementsAngular.dateSelectButton.isDisplayed();
        boolean isSearchButtonExist = SummaryReportPopupElementsAngular.searchBButton.isDisplayed();
        boolean isTitleHeaderExist = SummaryReportPopupElementsAngular.title.isDisplayed();
        boolean isCaseSerialExist = SummaryReportPopupElementsAngular.caseSerial.isDisplayed();
        boolean isWestlawExist = SummaryReportPopupElementsAngular.westlaw.isDisplayed();
        boolean isCourtExist = SummaryReportPopupElementsAngular.court.isDisplayed();
        boolean isRuExist = SummaryReportPopupElementsAngular.ru.isDisplayed();
        boolean isReloadedExist = SummaryReportPopupElementsAngular.reloaded.isDisplayed();
        boolean isNewExist = SummaryReportPopupElementsAngular.new_report.isDisplayed();
        boolean isUpdateExist = SummaryReportPopupElementsAngular.update.isDisplayed();

        //4. Into the select date field type "testtest12"
        summaryReportPopupAngular().inputDate(wrongDate);
        String actualMessage = summaryReportPopupAngular().getInvalidMessageText();
        //6. Type new date into the same field: 11/09/2006 and click "Search"
        summaryReportPopupAngular().inputDate(correctDate);
        summaryReportPopupAngular().clickSearch();
        //7. VERIFY: Cases 2010-575775 and 2010-345403 are shown
        //8. VERIFY: Other table fileds are also populated correctly
        String actualTitle = summaryReportPopupAngular().getTitleByRowIndex(row);
        String actualCaseSerial = summaryReportPopupAngular().getCaseSerialByRowIndex(row);
        String actualWestLaw = summaryReportPopupAngular().getWestlawByRowIndex(row);
        String actualCourt = summaryReportPopupAngular().getCourtByRowIndex(row);
        String actualRU = summaryReportPopupAngular().getRuByRowIndex(row);
        String actualReloaded = summaryReportPopupAngular().getReloadedByRowIndex(row);
        String actualNew = summaryReportPopupAngular().getNewByRowIndex(row);
        String actualUpdated = summaryReportPopupAngular().getUpdatedByRowIndex(row);

        Assertions.assertAll(
                () -> Assertions.assertTrue(isInputDateFieldExist, "Input date field is NOT displayed"),
                () -> Assertions.assertTrue(isDateSelectButtonExist, "Date select button is NOT displayed"),
                () -> Assertions.assertTrue(isSearchButtonExist, "Search button is NOT displayed"),
                () -> Assertions.assertTrue(isTitleHeaderExist, "Title header is NOT displayed"),
                () -> Assertions.assertTrue(isCaseSerialExist, "Case serial header is NOT displayed"),
                () -> Assertions.assertTrue(isWestlawExist, "Westlaw header is NOT displayed"),
                () -> Assertions.assertTrue(isCourtExist, "Court header is NOT displayed"),
                () -> Assertions.assertTrue(isRuExist, "R/U header is NOT displayed"),
                () -> Assertions.assertTrue(isReloadedExist, "Reloaded header is NOT displayed"),
                () -> Assertions.assertTrue(isNewExist, "New header is NOT displayed"),
                () -> Assertions.assertTrue(isUpdateExist, "Update header is NOT displayed"),
                () -> Assertions.assertEquals(expectedMessage, actualMessage),
                () -> Assertions.assertEquals(expectedTitle, actualTitle),
                () -> Assertions.assertEquals(expectedCaseSerial, actualCaseSerial),
                () -> Assertions.assertEquals(expectedWestLaw, actualWestLaw),
                () -> Assertions.assertEquals(expectedCourt, actualCourt),
                () -> Assertions.assertEquals(expectedRU, actualRU),
                () -> Assertions.assertEquals(expectedReloaded, actualReloaded),
                () -> Assertions.assertEquals(expectedNew, actualNew),
                () -> Assertions.assertEquals(expectedUpdated, actualUpdated)
        );

    }

    /**
     * HALCYONST-11062 / HALCYONST-11275- Summary report - calendar selector
     * 1. Log onto the Iowa (development) content set
     * 2. Go to NOD Home Page -> Reports -> Summary
     * 3. Click date selector button
     * 4. VERIFY: Date selector pop up is shown
     * 5. Click year to select it
     * 6. VERIFY: year selection dropdown is shown. The current year is the last one in dropdown
     * 7. Select year 2006
     * 8. Click month to select it
     * 9. VERIFY: month selection dropdown is shown.
     * 10. Select December
     * 11. VERIFY: calendar for December 2006 is shown
     * 12. Click the 9th of December in calendar
     * 13. VERIFY: Cases XXXXXXXXXXXXXXXX are shown
     * 14. VERIFY: Other table fileds are also populated correctly
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void summaryCalendarSelectionTest() {
        String expectedTitle = "Gamble v. State";
        String expectedCaseSerial = "2010-575775";
        String expectedWestLaw = "2006 WL 3103035";
        String expectedCourt = "Iowa";
        String expectedRU = "R";
        String expectedReloaded = "N";
        String expectedNew = "5";
        String expectedUpdated = "0";
        String row = "0";
        String year = "2006";
        String month = "Nov";
        String date = "Thursday, November 9, 2006";

        // 1. Log onto the Iowa (development) content set
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD Home Page -> Reports -> No Team
        homePageAngular().clickSummary();
        //3. Click date selector button
        Assertions.assertTrue(summaryReportPopupAngular().doesElementExist(SummaryReportPopupElementsAngular.PAGE_TITLE), "Summary Report window DID NOT open");
        summaryReportPopupAngular().clickDateSelector();
        //4. VERIFY: Date selector pop up is shown
        Assertions.assertTrue(dateSelectorPopupAngular().isDateSelectorDisplayed(), "Date selector pop up is NOT shown");
        //5. Click year to select it
        //6. VERIFY: year selection dropdown is shown. The current year is the last one in dropdown
        dateSelectorPopupAngular().selectYear(year);
        //8. Click month to select it
        //9. VERIFY: month selection dropdown is shown.
        //10. Select Novermber
        dateSelectorPopupAngular().selectMonth(month);
        //11. VERIFY: calendar for November 2006 is
        //12. Click the 9th of November in calendar
        dateSelectorPopupAngular().selectDate(date);
        detailReportPopupAngular().clickSearch();
        //13. VERIFY: Cases 2010-575775 and 2010-345403 are shown
        //14. VERIFY: Other table fileds are also populated correctly
        String actualTitle = summaryReportPopupAngular().getTitleByRowIndex(row);
        String actualCaseSerial = summaryReportPopupAngular().getCaseSerialByRowIndex(row);
        String actualWestLaw = summaryReportPopupAngular().getWestlawByRowIndex(row);
        String actualCourt = summaryReportPopupAngular().getCourtByRowIndex(row);
        String actualRU = summaryReportPopupAngular().getRuByRowIndex(row);
        String actualReloaded = summaryReportPopupAngular().getReloadedByRowIndex(row);
        String actualNew = summaryReportPopupAngular().getNewByRowIndex(row);
        String actualUpdated = summaryReportPopupAngular().getUpdatedByRowIndex(row);

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedTitle, actualTitle),
                () -> Assertions.assertEquals(expectedCaseSerial, actualCaseSerial),
                () -> Assertions.assertEquals(expectedWestLaw, actualWestLaw),
                () -> Assertions.assertEquals(expectedCourt, actualCourt),
                () -> Assertions.assertEquals(expectedRU, actualRU),
                () -> Assertions.assertEquals(expectedReloaded, actualReloaded),
                () -> Assertions.assertEquals(expectedNew, actualNew),
                () -> Assertions.assertEquals(expectedUpdated, actualUpdated)
        );

    }

    /**
     * HALCYONST-10585 - Download the latest report
     * 1. Open NOD Home page for Canada Ontario content set
     * 2. Go to Reports -> Auto-merge report
     * 3. Click "Download latest report"
     * 4. VERIFY: the report is downloaded and complains plausible data
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void downloadLatestReportTest() {

        String reportFileName = "autoMergeReport.pdf";
        String reportFilePath = FileUtils.DOWNLOAD_FOLDER_PATH + File.separator + reportFileName;

        // 1. Log onto the Iowa (development) content set
        homePageAngular().openNodHomePage(ContentSets.CANADA_ONTARIO_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD Home Page -> Reports -> Auto-merge report
        homePageAngular().clickAutoMerge();
        Assertions.assertTrue(autoMergeReportPopupAngular().doesElementExist(AutoMergeReportPopupElementsAngular.PAGE_TITLE), "Summary Report window DID NOT open");
        //3. Click "Download latest report"
        boolean isDownloadLatestReportClickable = autoMergeReportPopupAngular().isDownloadLatestReportClickable();
        //4. VERIFY: the report is downloaded and complains plausible data
        autoMergeReportPopupAngular().clickDownloadLatestReport();
        boolean isReportDownloaded = FileUtils.waitForFileToExist(reportFilePath, 10000);

        Assertions.assertAll(
                () -> Assertions.assertTrue(isDownloadLatestReportClickable, "Download the last report link is NOT clickable"),
                () -> Assertions.assertTrue(isReportDownloaded, "Report was NOT downloaded")
        );
    }

    /**
     * HALCYONST-10585 - Subscribe and Unsubscribe to monthly report
     * 1. Open NOD Home page for Canada Ontario content set
     * 2. Go to Reports -> Auto-merge report
     * 3. Click "Subscribe"
     * 4. VERIFY: the message that you are now subscribed to monthly repots for this content set is shown
     * 5. Go to Reports -> Auto-merge report
     * 6. VERIFY: Subscribe button has changed to "Unsubscribe"
     * 7. Click "Unsubscribe"
     * 8. VERIFY: the message that you are now unsubscribed
     * 9. VERIFY: Subscribe button has changed to "Subscribe"
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void subscribeUnsubscribeMonthlyReportTest() {

        String expectedMessage = "Successfully processed subscription to auto merge report email";

        // 1. Log onto the Iowa (development) content set
        homePageAngular().openNodHomePage(ContentSets.CANADA_ONTARIO_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD Home Page -> Reports -> Auto-merge report
        homePageAngular().clickAutoMerge();
        Assertions.assertTrue(autoMergeReportPopupAngular().doesElementExist(AutoMergeReportPopupElementsAngular.PAGE_TITLE), "Summary Report window DID NOT open");
        //3. Click "Subscribe"
        autoMergeReportPopupAngular().clickSubscribe();
        boolean isUnsubscribeDisplayed = autoMergeReportPopupAngular().isUnsubscribeDisplayed();
        autoMergeReportPopupAngular().close();
        //4. VERIFY: the message that you are now subscribed to monthly reports for this content set is shown
        String actualSubscribeMessage = homePageAngular().getTextFromMessageAndCloseIt();
        //5. Go to NOD Home Page -> Reports -> Auto-merge report
        homePageAngular().clickAutoMerge();
        //6. VERIFY: Subscribe button has changed to "Unsubscribe"
        boolean isUnsubscribeDisplayedAfterReopen = autoMergeReportPopupAngular().isUnsubscribeDisplayed();
        //7. Click "Unsubscribe"
        autoMergeReportPopupAngular().clickUnsubscribe();
        //8. VERIFY: Subscribe button has changed to "Subscribe"
        boolean isSubscribeDisplayed = autoMergeReportPopupAngular().isSubscribeDisplayed();
        autoMergeReportPopupAngular().close();
        //9. VERIFY: the message that you are now unsubscribed
        String actualUnsubscribeMessage = homePageAngular().getTextFromMessageAndCloseIt();
        Assertions.assertAll(
                () -> Assertions.assertTrue(isUnsubscribeDisplayed, "Subscribe button has NOT changed to 'Unsubscribe'"),
                () -> Assertions.assertEquals(expectedMessage, actualSubscribeMessage),
                () -> Assertions.assertTrue(isUnsubscribeDisplayedAfterReopen, "Unsubscribe button is NOT saved after reopen"),
                () -> Assertions.assertTrue(isSubscribeDisplayed, "Unsubscribe button has NOT changed to 'Subscribe'"),
                () -> Assertions.assertEquals(expectedMessage, actualUnsubscribeMessage)
        );
    }

}
