package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.NodMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.popups.FilterPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.HomePageAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.FilterOperator;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.SortingOrder;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CARSWELL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.pages.nodAngular.SubscribedCasesReport;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.Keys;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;

import static com.thomsonreuters.codes.codesbench.quality.utilities.files.FileUtils.DOWNLOAD_FOLDER_DIR;

public class SubscribedCasesTests extends TestService
{
    public static final String REPORT_FILE_NAME_REGEX = "subscribed-cases_[01]\\d-[0-3]\\d-\\d{4}_[0-2]\\d-[0-5]\\d-[0-5]\\d\\.xlsx";
    public static final String NOD_SUBSCRIBED_CASES_PAGE_SHOULD_BE_OPENED_MSG = "NOD Subscribed Cases page should be opened";

    /**
     * HALCYONST-9503 + HALCYONST-9087 - NOD Codes menu option & cases table layout
     * 1. Log onto the Iowa (Development) content set
     * 2. VERIFY: Confirm that NOD menu has the following entries:
     * Subscribe Cases
     * Subscribe Cases - Angular
     * Cases
     * Cases - Angular
     * 3. Go to NOD -> Subscribed Cases - angular
     * 4. The columns should be in the following order:  Loaded Date, Notes, Case Serial #, Westlaw #,
     * Reporter Cite, Court, R/U, Reloaded, Headnotes, Title, Signed Out By, Completeed Date, Completed By, Cite Information
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void subscribedCasesUSTableLayoutTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.IOWA_DEVELOPMENT.getName());
        nodMenu().openMenu();
        boolean casesOptionExists = NodMenuElements.cases.isDisplayed();
        boolean subscribedCasesOptionExists = NodMenuElements.subscribedCases.isDisplayed();
        boolean subscribedCasesWindowAppeared = nodMenu().goToSubscribedCases();
        Assertions.assertTrue(subscribedCasesWindowAppeared, NOD_SUBSCRIBED_CASES_PAGE_SHOULD_BE_OPENED_MSG);
        LinkedHashMap<String, Integer> expectedColumnOrder = subscribedCasesPageAngular().getExpectedUSColumnOrder();
        HashMap<Object, Integer> actualColumnOrder = subscribedCasesTablePage().getColumnOrderWithScrolling();
        boolean columnsAsExpected = expectedColumnOrder.equals(actualColumnOrder);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(casesOptionExists, "Cases option is not shown"),
            () -> Assertions.assertTrue(subscribedCasesOptionExists, "Subscribed Cases option is not shown"),
            () -> Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear"),
            () -> Assertions.assertTrue(columnsAsExpected, "Order of columns is not expected")
        );
    }

    /**
     * HALCYONST-10740 + HALCYONST 10239 - Table layout for Canada
     * 1. Log onto Canada Ontario (Development) content set
     * 2. Click the NOD button
     * 3. Select the Subscribed Cases option on the page
     * 4. VERIFY: Subscribed Cases page is opened
     * 5. The columns should be in the following order:  Loaded Date, Notes, CCDB #, Westlaw #, Neutral Cite, Court, R/U, Reloaded, Headnotes, Title, Signed Out By, Completed Date, Completed By, Cite Information
     */
    @Test
    @EDGE
    @CARSWELL
    @LOG
    public void subscribedCasesCanadaTableLayoutTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(ContentSets.CANADA_ONTARIO_DEVELOPMENT.getName());
        boolean nodHomeCasesWindowAppeared = nodMenu().goToCanadianNOD();
        boolean homePageIsOpened = homePageAngular().verifyPageIsOpened();
        homePageAngular().clickHomePageMenuOption(HomePageAngular.NodHomePageListOptions.SUBSCRIBED_CASES);
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), NOD_SUBSCRIBED_CASES_PAGE_SHOULD_BE_OPENED_MSG);
        List<String> expectedColumnOrder = subscribedCasesPageAngular().getExpectedCanadaColumnOrder();
        List<String> actualColumnOrder = subscribedCasesTablePage().getActualColumnOrderListSubscribedCases();

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(nodHomeCasesWindowAppeared, "NOD Window did not appear"),
                        () -> Assertions.assertTrue(homePageIsOpened, "NOD Home page did not appear"),
                        () -> Assertions.assertEquals(expectedColumnOrder, actualColumnOrder, "Order of columns is not expected")
                );
    }

    /**
     * HALCYONST-8958 - Subscribed Cases - Notes
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Click on the "Create Note" icon in the Notes column for any case
     * 4. VERIFY: Note pop up appeared
     * 5. Type in �This is a notes test� in the text input area
     * 6. Click the Save button
     * 7. VERIFY: the "Case Note updated" message appears
     * 8. VERIFY: the icon changed to "Edit note"
     * 9. Click on the "Edit Note" icon in Notes column for this case
     * 10. VERIFY: pop up appears and contains previously entered text
     * 11. Change text to "TEST 2" and click the Save button
     * 12. VERIFY: the "Case Note updated" message appears
     * 13. VERIFY: "Edit Note" icon remains for his case.
     * 14. VERIFY: the text of the note is "TEST 2"
     * 15. Delete all text from the note, leaving the note blank
     * 16.  Click the Save button
     * 17. VERIFY: the "Case Note deleted" message appears
     * 18. VERIFY: the "Edit Note" icon is replaced with the "Create Note" icon appears
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void subscribedCasesNotesTest()
    {
        String notesFirstText = "This is a notes test";
        String notesSecondText = "TEST 2";
        String notificationMessageCaseUpdated = "Case Note updated";
        String notificationMessageCaseDeleted = "Case Note deleted";
        Integer rowNumber = 1;

        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, NOD_SUBSCRIBED_CASES_PAGE_SHOULD_BE_OPENED_MSG);
        //3. Click on the "Create Note" icon in the Notes column for any case
        //parse any row in the table where we have "Create note" icon in Notes column
        //return the cell itself and click it
        subscribedCasesTablePage().clickCellTextByRowAndColumn(rowNumber, TableColumns.NOTES);
        //4. VERIFY: Note pop up appeared
        boolean doesPopupAppear = notesPopupAngular().waitForPopup();
        //5. Type in �This is a notes test� in the text input area
        notesPopupAngular().clearTextArea();
        notesPopupAngular().typeIntoTextArea(notesFirstText);
        //6. Click the Save button
        notesPopupAngular().clickSaveButton();
        //7. VERIFY: the "Case Note updated" message appears
        boolean isNotificationShown = notificationPopupAngular().waitForNotification();
        String actualNotificationText = notificationPopupAngular().getNotificationText();
        boolean doesNotificationContainExpectedText = notificationMessageCaseUpdated.equals(actualNotificationText);
        // close notification
        notificationPopupAngular().closeNotification();
        subscribedCasesTablePage().waitForGridRefresh();
        //8. VERIFY: the icon changed to "Edit note"
        boolean didIconChangedToEditNote = subscribedCasesTablePage().isTextEditNote(rowNumber, TableColumns.NOTES);
        //9. Click on the "Edit Note" icon in Notes column for this case
        subscribedCasesTablePage().clickCellTextByRowAndColumn(rowNumber, TableColumns.NOTES);
        //10. VERIFY: pop up appears and contains previously entered text
        boolean doesPopupAppearAgain = notesPopupAngular().waitForPopup();
        //11. Change text to "TEST 2" and click the Save button
        notesPopupAngular().clearTextArea();
        notesPopupAngular().typeIntoTextArea(notesSecondText);
        notesPopupAngular().clickSaveButton();
        //12. VERIFY: the "Case Note updated" message appears
        boolean isNotificationShownAgain = notificationPopupAngular().waitForNotification();
        String actualNotificationTextAgain = notificationPopupAngular().getNotificationText();
        boolean doesNotificationContainExpectedTextAgain = notificationMessageCaseUpdated.equals(actualNotificationText);
        subscribedCasesTablePage().waitForGridRefresh();
        //13. VERIFY: "Edit Note" icon remains for his case.
        boolean doesIconRemainsEditNote = subscribedCasesTablePage().isTextEditNote(rowNumber, TableColumns.NOTES);
        //14. VERIFY: the text of the note is "TEST 2"
        subscribedCasesTablePage().clickCellTextByRowAndColumn(rowNumber, TableColumns.NOTES);
        String notesText = notesPopupAngular().getNoteText();
        boolean noteTextIsExpected = notesText.equals(notesSecondText);
        //15. Delete all text from the note, leaving tphe note blank
        notesPopupAngular().clearTextArea();
        //16.  Click the Save button
        notesPopupAngular().clickSaveButton();
        //17. VERIFY: the "Case Note deleted" message appears
        boolean isNotificationShownThirdTime = notificationPopupAngular().waitForNotification();
        String actualNotificationTextThirdTime = notificationPopupAngular().getNotificationText();
        boolean doesNotificationContainExpectedTextThirdTime = notificationMessageCaseDeleted.equals(actualNotificationTextThirdTime);
        //18. VERIFY: the "Edit Note" icon is replaced with the "Create Note" icon appears
        boolean doesIconChangedToCreateNote = subscribedCasesTablePage().isTextCreateNote(rowNumber, TableColumns.NOTES);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(doesPopupAppear, "Notes popup didn't appear"),
                        () -> Assertions.assertTrue(isNotificationShown, "Notification didn't appear"),
                        () -> Assertions.assertTrue(doesNotificationContainExpectedText,
                                String.format("Notification contains wrong text. Expected: %s, Actual: %s",
                                        notificationMessageCaseUpdated, actualNotificationText)),
                        () -> Assertions.assertTrue(didIconChangedToEditNote, "Icon didn't changed after Note is inserted"),
                        () -> Assertions.assertTrue(doesPopupAppearAgain, "Notes popup didn't appear"),
                        () -> Assertions.assertTrue(isNotificationShownAgain, "Notification didn't appear after note update"),
                        () -> Assertions.assertTrue(doesNotificationContainExpectedTextAgain,
                                String.format("Notification contains wrong text after note update. Expected: %s, Actual: %s",
                                        notificationMessageCaseUpdated, actualNotificationTextAgain)),
                        () -> Assertions.assertTrue(doesIconRemainsEditNote, "Icon changed after Note is updated"),
                        () -> Assertions.assertTrue(noteTextIsExpected,
                                String.format("Note text is not what expected. Expected: %s, actual: %s", notesSecondText, notesText)),
                        () -> Assertions.assertTrue(isNotificationShownThirdTime, "Notification didn't appear after note text deletion"),
                        () -> Assertions.assertTrue(doesNotificationContainExpectedTextThirdTime,
                                String.format("Notification contains wrong text after note text deletion. Expected: %s, Actual: %s",
                                        notificationMessageCaseDeleted, actualNotificationTextAgain)),
                        () -> Assertions.assertTrue(doesIconChangedToCreateNote, "Icon didn't change after note deletion")
                );
    }

    /**
     * HALCYONST-9080 - Subscribed Cases - All user changes to layout are retained
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Check Title and Westlaw columns are unsorted
     * 4. VERIFY: column order is default
     * 5. Drag and drop column "Title" before column "Westlaw"
     * 6. Apply filter to Title column
     * 7. Sort Westlaw column
     * 8. Reopen subscribed cases page
     * 9. VERIFY: Westlaw column is sorted, Title column is filtered and Title column is before Westlaw column
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void subscribedCasesSavingLayoutTest()
    {
        String filterValue = "mith v.";

        //1. Open Subscribed cases page for IOWA (Development)``
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, NOD_SUBSCRIBED_CASES_PAGE_SHOULD_BE_OPENED_MSG);

        //3. Check Title and Westlaw columns are unsorted
        boolean columnIsUnsortedByDefault = subscribedCasesTablePage().defineSortingOrder(TableColumns.TITLE).equals(SortingOrder.UNSORTED);
        boolean columnIsAlsoUnsortedByDefault = subscribedCasesTablePage().defineSortingOrder(TableColumns.WESTLAW).equals(SortingOrder.UNSORTED);

        //4. Check default columns order
        HashMap<String, Integer> expectedColumnOrder = subscribedCasesPageAngular().getExpectedUSColumnOrder();
        HashMap<Object, Integer> actualColumnOrder = subscribedCasesTablePage().getColumnOrderWithScrolling();
        boolean defaultOrderOfColumnsIsCorrect = expectedColumnOrder.equals(actualColumnOrder);

        //5. Drag and drop column "Title" before column "Westlaw"
        subscribedCasesTablePage().dragAndDropElementToElement(subscribedCasesTablePage().getColumnNameElement(TableColumns.TITLE), subscribedCasesTablePage().getColumnNameElement(TableColumns.WESTLAW));
        DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
        subscribedCasesPageAngular().scrollToRight("-500");
        HashMap<Object, Integer> actualColumnOrderAfterDragAndDrop = subscribedCasesTablePage().getColumnOrderWithScrolling();
        boolean columnsOrderChanged = !expectedColumnOrder.equals(actualColumnOrderAfterDragAndDrop);

        //6. Apply filter to Title column
        boolean columnIsNotFiltered = subscribedCasesTablePage().isFiltered(TableColumns.TITLE);
        subscribedCasesTablePage().openFilterOptionsForColumn(TableColumns.TITLE);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, filterValue);
        subscribedCasesTablePage().closeFilterOptionsForColumn();
        subscribedCasesTablePage().waitForTableToReload();
        boolean columnIsFiltered = subscribedCasesTablePage().isFiltered(TableColumns.TITLE);
        //7. Sort Westlaw column
        subscribedCasesTablePage().clickToSort(TableColumns.WESTLAW);
        subscribedCasesTablePage().waitForTableToReload();
        boolean columnIsNowSorted = subscribedCasesTablePage().defineSortingOrder(TableColumns.WESTLAW).equals(SortingOrder.ASCENDING);

        //8. Reopen subscribed cases page, verify formatting is saved
        subscribedCasesPageAngular().refreshPage();
        boolean subscribedCasesPageIsOpenedAgain = subscribedCasesPageAngular().isPageOpened();
        HashMap<Object, Integer> actualColumnOrderAfterPageReopen = subscribedCasesTablePage().getColumnOrderWithScrolling();
        boolean columnsOrderRetained = actualColumnOrderAfterPageReopen.equals(actualColumnOrderAfterDragAndDrop);
        boolean columnIsFilteredAgain = subscribedCasesTablePage().isFiltered(TableColumns.TITLE);
        boolean columnIsStillSorted = subscribedCasesTablePage().defineSortingOrder(TableColumns.WESTLAW).equals(SortingOrder.ASCENDING);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(columnIsUnsortedByDefault, "Column is not unsorted by default."),
            () -> Assertions.assertTrue(columnIsAlsoUnsortedByDefault, "Column is not unsorted by default."),
            () -> Assertions.assertTrue(defaultOrderOfColumnsIsCorrect, String.format("Column order is not defaut. \nExpected: %s, \nActual: %s", expectedColumnOrder, actualColumnOrder)),
            () -> Assertions.assertTrue(columnsOrderChanged, "Column order didn't change after drag and drop"),
            () -> Assertions.assertFalse(columnIsNotFiltered, "Title column is filtered, than it shouldn't be"),
            () -> Assertions.assertTrue(columnIsFiltered, "Title column is not filtered"),
            () -> Assertions.assertTrue(columnIsNowSorted, "Westlaw column is not sorted"),
            () -> Assertions.assertTrue(subscribedCasesPageIsOpenedAgain, "Subscribed cases page didn't open"),
            () -> Assertions.assertTrue(columnsOrderRetained, String.format("Column order isn't retained. \nExpected: %s, \nActual: %s", actualColumnOrderAfterDragAndDrop, actualColumnOrderAfterPageReopen)),
            () -> Assertions.assertTrue(columnIsFilteredAgain, "Column filtering isn't retained"),
            () -> Assertions.assertTrue(columnIsStillSorted, "Column sorting isn't retained")
        );
    }

    /**
     * HALCYONST-9080 - Subscribed Cases - All user changes to layout are retained
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Right click on the first case
     * 4. Click on Export to Excel button
     * 5. VERIFY: The data is exported in .xlsx format
     * 6. Verify xlsx content is the same as in NOD web interface
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void exportToExcelEverythingTest()
    {
        //1. Open Subscribed cases page for IOWA (Development)``
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, NOD_SUBSCRIBED_CASES_PAGE_SHOULD_BE_OPENED_MSG);
        //we need to delete previous report from downloads folder
        checkAllPreviousReportFilesDeleted();
        //3. Right click on the first case
        subscribedCasesTablePage().rightClickCellTextByRowAndColumn(0, TableColumns.WESTLAW);
        //4. Click on Export to Excel button
        subscribedCasesContextMenuAngular().selectExportToExcel();
        //Don't see a way to avoid it w/o creating new AutoIt script. Doesn't seem to be worth.
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
        AutoITUtils.sendHotKey("s", Keys.ALT);
        //5. VERIFY: The data is exported in .xlsx format
        checkReportFileExported();
        //6. Verify xlsx content is the same as in NOD web interface
        checkXlsxContentInFile();
    }

    /**
     * HALCYONST-9080/13928 - Subscribed Cases - All user changes to layout are retained
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Hold down SHIFT button and select 2-10 cases.
     * 4. Skip 2 rows, hold CTRL and click on 1 more row
     * 5. Right click one of selected cases
     * 6. Click on Export to Excel button
     * 7. VERIFY: The data is exported in .xlsx format
     * 8. Verify xlsx content is the same as in NOD web interface
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @EnumSource
            (
                    names = {"IOWA_DEVELOPMENT", "CANADA_ONTARIO_DEVELOPMENT"}
            )
    @EDGE
    @LEGAL
    @LOG
    public void exportToExcelSelectedRowsTest(ContentSets contentSet)
    {
        //1. Open Subscribed cases page for IOWA (Development)``
        subscribedCasesPageAngular().openNodSubscribedCasesPage(contentSet);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, NOD_SUBSCRIBED_CASES_PAGE_SHOULD_BE_OPENED_MSG);
        //we need to delete previous report from downloads folder
        checkAllPreviousReportFilesDeleted();
        //3. Hold down SHIFT button and select 2-10 cases.
        //4. Skip 2 rows, hold CTRL and click on 1 more row
        subscribedCasesTablePage().selectGivenRowsInTable(TableColumns.WESTLAW, 1,2,3,4,5,6,7,8,9,10,13);
        //5. Right click one of selected cases
        subscribedCasesTablePage().rightClickCellTextByRowAndColumn(5, TableColumns.WESTLAW);
        //6. Click on Export to Excel button
        subscribedCasesContextMenuAngular().selectExportToExcelSelectedRows();
        //Don't see a way to avoid it w/o creating new AutoIt script. Doesn't seem to be worth.
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
        AutoITUtils.sendHotKey("s", Keys.ALT);
        //7. VERIFY: The data is exported in .xlsx format
        checkReportFileExported();
        //8. Verify xlsx content is the same as in NOD web interface
        checkXlsxContentInFile();
    }

    /**
     * HALCYONST-9080 - Subscribed Cases - All user changes to layout are retained
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Hide the Loaded Date, Notes and Cite Information columns
     * 4. Swap R/U and Reloaded columns
     * 5. Right click one on the first case
     * 6. Click on Export to Excel button
     * 7. VERIFY: The data is exported in .xlsx format
     * 8. Verify xlsx content is the same as in NOD web interface
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void exportToExcelWithHiddenAndSwappedColumnsTest()
    {
        //1. Open Subscribed cases page for IOWA (Development)``
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, NOD_SUBSCRIBED_CASES_PAGE_SHOULD_BE_OPENED_MSG);
        //we need to delete previous report from downloads folder
        checkAllPreviousReportFilesDeleted();
        //3. Hide the Loaded Date, Notes and Cite Information columns
        subscribedCasesTablePage().openShowHideOptionsForColumn(TableColumns.WESTLAW);
        filterPopUp().toggleColumnsVisibility(TableColumns.LOADED_DATE, TableColumns.NOTES, TableColumns.WESTLAW);
        //4. Swap R/U and Reloaded columns
        subscribedCasesTablePage().dragColumnToColumn(TableColumns.RELOADED, TableColumns.RU);
        //5. Right click one of selected cases
        subscribedCasesTablePage().rightClickCellTextByRowAndColumn(5, TableColumns.REPORTER_CITE);
        //6. Click on Export to Excel button
        subscribedCasesContextMenuAngular().selectExportToExcel();
        //Don't see a way to avoid it w/o creating new AutoIt script. Doesn't seem to be worth.
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
        AutoITUtils.sendHotKey("s", Keys.ALT);
        //7. VERIFY: The data is exported in .xlsx format
        checkReportFileExported();
        //8. Verify xlsx content is the same as in NOD web interface
        checkXlsxContentInFile();
    }

    /**
     * HALCYONST-9080 - Subscribed Cases - All user changes to layout are retained
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Filter R/U column: "equals" "R"
     * 4. Sort by Title column value in ascending order (lowest first)
     * 5. Right click one on the first case
     * 6. Click on Export to Excel button
     * 7. VERIFY: The data is exported in .xlsx format
     * 8. Verify xlsx content is the same as in NOD web interface
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void exportToExcelFilteredAndSortedColumnsTest()
    {
        //1. Open Subscribed cases page for IOWA (Development)``
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, NOD_SUBSCRIBED_CASES_PAGE_SHOULD_BE_OPENED_MSG);
        //we need to delete previous report from downloads folder
        checkAllPreviousReportFilesDeleted();
        //3. Filter R/U column: "equals" "R"
        subscribedCasesTablePage().openFilterOptionsForColumn(TableColumns.RU);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.EQUALS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, "R");
        //4. Sort by Title column value in ascending order (lowest first)
        subscribedCasesTablePage().sortByColumn(TableColumns.TITLE);
        //5. Right click one of selected cases
        subscribedCasesTablePage().rightClickCellTextByRowAndColumn(5, TableColumns.REPORTER_CITE);
        //6. Click on Export to Excel button
        subscribedCasesContextMenuAngular().selectExportToExcel();
        //Don't see a way to avoid it w/o creating new AutoIt script. Doesn't seem to be worth.
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TEN_SECONDS);
        AutoITUtils.sendHotKey("s", Keys.ALT);
        //7. VERIFY: The data is exported in .xlsx format
        checkReportFileExported();
        //8. Verify xlsx content is the same as in NOD web interface
        checkXlsxContentInFile();
    }

    /**
     * HALCYONST-9080 - Subscribed Cases - All user changes to layout are retained
     * 1. Open Subscribed cases page for USCA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Right click one on the first case
     * 4. Click on Export to Excel button
     * 5. VERIFY: You got an error message stating �The excel report did not generate because it exceeds the 32000 line limit�
     * 6. VERIFY: Nothing was exported
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void exportToExcelTooManyEntriesTest()
    {
        String expectedErrorText = "Error: The excel report did not generate because it exceeds the 32000 line limit";
        //1. Open Subscribed cases page for IOWA (Development)``
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.USCA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, NOD_SUBSCRIBED_CASES_PAGE_SHOULD_BE_OPENED_MSG);
        //we need to delete previous report from downloads folder
        checkAllPreviousReportFilesDeleted();
        //3. Right click one on the first case
        subscribedCasesTablePage().rightClickCellTextByRowAndColumn(0, TableColumns.WESTLAW);
        //4. Click on Export to Excel button
        subscribedCasesContextMenuAngular().selectExportToExcel();
        //5. VERIFY: You got an error message stating �The excel report did not generate because it exceeds the 32000 line limit�
        boolean isErrorShown = notificationPopupAngular().waitForNotification();
        String actualErrorText = notificationPopupAngular().getNotificationText();
        boolean isTextExpected = expectedErrorText.equals(actualErrorText);
        //6. VERIFY: Nothing was exported
        boolean isReportDownloaded = FileUtils.waitForFileToExistByFileNameRegex(
                DOWNLOAD_FOLDER_DIR,
                REPORT_FILE_NAME_REGEX,
                DateAndTimeUtils.TEN_SECONDS);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(isErrorShown , "Error message wasn't displayed"),
                        () -> Assertions.assertTrue(isTextExpected, String.format("Error message is not expected. \n" +
                                "Expected: %s \nActual: %s", expectedErrorText, actualErrorText)),
                        () -> Assertions.assertFalse(isReportDownloaded, "Report unexpectedly exists")
                );
    }

    /**
     * HALCYONST-14118, HALCYONST-14121
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void subscribedSortIfBlankTest()
    {
        String caseSerial = "2016-918354";
        String userName = user().getFirstname()+ " " + user().getLastname();
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        // Open Subscribed cases page for IOWA (Development), open given case by serial number
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), NOD_SUBSCRIBED_CASES_PAGE_SHOULD_BE_OPENED_MSG);
        subscribedCasesTablePage().openHeadnotesPageByCaseSerial(caseSerial);
        Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");

        //Ignore all
        if (!headnotesPageAngular().isUnignoreAllButtonDisabled())
        {
            headnotesPageAngular().clickUnignoreAll();
            headnotesPageAngular().waitForPageLoaded();
        }
        headnotesPageAngular().clickIgnoreAll();
        //Completed By and Date
        headnotesPageAngular().clickCompletedByAndDate();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        //Verify: Subscribed Cases page is opened(HALCYONST-14121)
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open after Selecting Completed by and Date button");
        //Click on the blue hyper link
        subscribedCasesPageAngular().scrollToRight("500");
        String actualCompletedDateAfterCompleted = subscribedCasesTablePage().getCellTextByRowAndColumn(0, TableColumns.COMPLETED_DATE);
        String actualCompletedByAfterCompleted = subscribedCasesTablePage().getCellTextByRowAndColumn(0, TableColumns.COMPLETED_BY);
        subscribedCasesTablePage().openFilterOptionsForColumn(TableColumns.COMPLETED_DATE);
        subscribedCasesTablePage().waitForHiddenOverlay();
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.BLANKS);
        filterPopUp().clickApplyFilterButton();
        subscribedCasesTablePage().waitForHiddenOverlay();
        boolean thereIsNoBlankCompletedDate = subscribedCasesTablePage().isTableEmpty();
        //15. Select the second condition and type value in text field
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.EQUALS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, currentDate);
        filterPopUp().clickApplyFilterButton();
        subscribedCasesTablePage().waitForHiddenOverlay();
        boolean thereIsCompletedDateWithCurrentDate = subscribedCasesTablePage().isTableEmpty();
        subscribedCasesTablePage().closeFilterOptionsForColumn();
        subscribedCasesPageAngular().scrollToRight("500");
        subscribedCasesTablePage().openFilterOptionsForColumn(TableColumns.COMPLETED_BY);
        subscribedCasesTablePage().waitForHiddenOverlay();
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.BLANKS);
        filterPopUp().clickApplyFilterButton();
        subscribedCasesTablePage().waitForHiddenOverlay();
        boolean thereIsNoBlankCompletedBy = subscribedCasesTablePage().isTableEmpty();
        // Select the equals condition for Completed By column and type value in text field
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.EQUALS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, userName);
        filterPopUp().clickApplyFilterButton();
        subscribedCasesTablePage().waitForHiddenOverlay();
        boolean thereIsCompletedByWithUserName = subscribedCasesTablePage().isTableEmpty();
        subscribedCasesTablePage().closeFilterOptionsForColumn();
        subscribedCasesTablePage().refreshPage();
        subscribedCasesTablePage().clickOnHyperLinkOnFirstRow();
        //VERIFY: The Classification/Headnote page is opened
        Assertions.assertTrue(headnotesPageAngular().isPageOpened(), "Headnotes page didn't open");
        //Unignore all
        headnotesPageAngular().clickUnignoreAll();

        Assertions.assertAll
        (
            () -> Assertions.assertEquals(currentDate, actualCompletedDateAfterCompleted),
            () -> Assertions.assertEquals(userName, actualCompletedByAfterCompleted),
            () -> Assertions.assertTrue(thereIsNoBlankCompletedDate),
            () -> Assertions.assertFalse(thereIsCompletedDateWithCurrentDate),
            () -> Assertions.assertTrue(thereIsNoBlankCompletedBy),
            () -> Assertions.assertFalse(thereIsCompletedByWithUserName)
        );
    }

    /**
     * HALCYONST-14457
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void searchByCaseSerialNumberTest()
    {
        String caseSerial = "6-94";
        Predicate<String> caseSerialPredicate = s -> s.contains(caseSerial);

        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //VERIFY: Subscribed Cases page is opened
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), NOD_SUBSCRIBED_CASES_PAGE_SHOULD_BE_OPENED_MSG);
        subscribedCasesTablePage().waitForTableToReload();
        List<String> columnValuesBeforeFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.CASE_SERIAL_SUBSCRIBED_CASES);
        subscribedCasesPageAngular().inputTextInSearchByCaseSerialField(caseSerial);
        List<String> columnValuesAfterFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.CASE_SERIAL_SUBSCRIBED_CASES);

        Assertions.assertAll
                (
                        () -> Assertions.assertFalse(columnValuesBeforeFiltering.stream().allMatch(caseSerialPredicate)),
                        () -> Assertions.assertTrue(columnValuesAfterFiltering.stream().allMatch(caseSerialPredicate))
                );
    }
    /**
     * HALCYONST-14596 <br>
     * CONTENT SET - USCA
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void supremeCourtCasesFilteredToTheTopTest()
    {
        String court = "U.S.";
        String reporterCite = "S.Ct.";
        Predicate<String> courtPredicate = s -> s.contains(court);
        Predicate<String> reporterCitePredicate = s -> s.contains(reporterCite);

        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.USCA_DEVELOPMENT);
        loginPage().logIn();
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), NOD_SUBSCRIBED_CASES_PAGE_SHOULD_BE_OPENED_MSG);
        subscribedCasesTablePage().waitForTableToReload();
        List<String> courtColumnValues = subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.COURT);
        List<String> reporterCiteColumnValues = subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.REPORTER_CITE);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(courtColumnValues.stream().allMatch(courtPredicate)),
                        () -> Assertions.assertTrue(reporterCiteColumnValues.stream().allMatch(reporterCitePredicate))
                );

    }

    private void checkAllPreviousReportFilesDeleted()
    {
        boolean filesSuccessfullyDeleted = FileUtils.deleteFilesByFileNameRegex(DOWNLOAD_FOLDER_DIR, REPORT_FILE_NAME_REGEX);
        Assertions.assertTrue(filesSuccessfullyDeleted, "All matched files should be deleted");
    }

    private void checkReportFileExported()
    {
        boolean isReportDownloaded = FileUtils.waitForFileToExistByFileNameRegex(DOWNLOAD_FOLDER_DIR, REPORT_FILE_NAME_REGEX, DateAndTimeUtils.TEN_SECONDS);
        Assertions.assertTrue(isReportDownloaded, "The report was not downloaded. Something went wrong");
    }

    private void checkXlsxContentInFile()
    {
        File downloadedFile = FileUtils.getFileByFileNameRegex(DOWNLOAD_FOLDER_DIR, REPORT_FILE_NAME_REGEX);
        String reportFileName = downloadedFile.getName();
        int numberOfRowsOnScreen = subscribedCasesTablePage().getRowsNumbers().size() - 1;
        List<List<String>> valuesInReport = SubscribedCasesReport.parseSubscribedCasesReport(DOWNLOAD_FOLDER_DIR + reportFileName,
                numberOfRowsOnScreen + 1);
        List<List<String>> valuesOnPage = subscribedCasesTablePage().getAllValuesForRowsInRange(0, numberOfRowsOnScreen);
        boolean isDataEqual = valuesInReport.equals(valuesOnPage);
        List<String> discrepancyReport = SubscribedCasesReport.findReportAndTableDataDiscrepancy(valuesInReport, valuesOnPage);
        Assertions.assertTrue(isDataEqual,
                "Data in report is not the same as on page. \nDiscrepancy report: " + discrepancyReport);
    }


}
