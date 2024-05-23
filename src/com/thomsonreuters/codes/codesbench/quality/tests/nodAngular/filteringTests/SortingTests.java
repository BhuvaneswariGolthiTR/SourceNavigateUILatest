package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular.filteringTests;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.CasesPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.SubscribedCasesPageElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.popups.FilterPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.FilterOperator;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.SortingOrder;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;

public class SortingTests extends TestService
{
    /**
     * HALCYONST-8958 - Subscribed Cases - Sorting Columns
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Check default state is unsorted
     * 4. Sort column
     * 5. Column is shown as sorted
     * 6. Column sorting is correct
     */

    private static Stream<Arguments> provideDataForSortingSub()
    {
        return Stream.of(
                Arguments.of(TableColumns.TITLE, SortingOrder.ASCENDING),
                Arguments.of(TableColumns.TITLE, SortingOrder.DESCENDING),
                Arguments.of(TableColumns.LOADED_DATE, SortingOrder.ASCENDING),
                Arguments.of(TableColumns.LOADED_DATE, SortingOrder.DESCENDING),
                Arguments.of(TableColumns.HN, SortingOrder.ASCENDING),
                Arguments.of(TableColumns.HN, SortingOrder.DESCENDING)
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForSortingSub")
    @EDGE
    @LEGAL
    @LOG
    public void subscribedCasesSortingTest(TableColumns column, SortingOrder sortingOrder)
    {
        String filterContainsValue = "mith v. S";

        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");
        //3. Check default state is unsorted
        Assertions.assertEquals(subscribedCasesTablePage().defineSortingOrder(column), SortingOrder.UNSORTED, String.format("Column is not unsorted by default." +
                "The sorting order is %s", subscribedCasesTablePage().defineSortingOrder(column).getSortingOrderAsString()));

        // Narrow results down
        subscribedCasesTablePage().openFilterOptionsForColumn(TableColumns.TITLE);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, filterContainsValue);
        subscribedCasesTablePage().waitForGridRefresh();
        subscribedCasesTablePage().click(SubscribedCasesPageElementsAngular.GRID);

        //4. Sort column
        if (sortingOrder.equals(SortingOrder.ASCENDING))
        {
            subscribedCasesTablePage().clickToSort(column);
        }
        if (sortingOrder.equals(SortingOrder.DESCENDING))
        {
            subscribedCasesTablePage().clickToSort(column);
            subscribedCasesTablePage().clickToSort(column);
        }
        subscribedCasesTablePage().waitForTableToReload();
        SortingOrder newSortingOrder = subscribedCasesTablePage().defineSortingOrder(column);
        //5. Column is shown as sorted
        //6. Column sorting is correct
        List<String> columnValues = subscribedCasesTablePage().getAllValuesInGivenColumn(column);
        SortingOrder actualColumnValuesSorting = subscribedCasesTablePage().getColumnValuesSorting(column, columnValues);

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(newSortingOrder,sortingOrder, String.format("Column is not shown as sorted. " +
                                "Sorting order is: %s", newSortingOrder)),
                        () -> Assertions.assertEquals(sortingOrder, actualColumnValuesSorting, String.format("Column values are not sorted as expected." +
                                        "Expected order: %s.",
                                sortingOrder.getSortingOrderAsString()))
                );
    }

    /**
     * HALCYONST-8958 - Subscribed Cases - Sorting Columns
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Check default state is unsorted
     * 4. Sort column
     * 5. Column is shown as sorted
     * 6. Column sorting is correct
     */

    private static Stream<Arguments> provideDataForNotesSorting()
    {
        return Stream.of(
                Arguments.of(TableColumns.NOTES, SortingOrder.ASCENDING),
                Arguments.of(TableColumns.NOTES, SortingOrder.DESCENDING)
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForNotesSorting")
    @EDGE
    @LEGAL
    @LOG
    public void subscribedCasesNotesSortingTest(TableColumns column, SortingOrder sortingOrder)
    {
        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");
        //3. Check default state is unsorted
        Assertions.assertEquals(subscribedCasesTablePage().defineSortingOrder(column), SortingOrder.UNSORTED, String.format("Column is not unsorted by default." +
                "The sorting order is %s", subscribedCasesTablePage().defineSortingOrder(column).getSortingOrderAsString()));
        //4. Sort column
        if (sortingOrder.equals(SortingOrder.ASCENDING))
        {
            subscribedCasesTablePage().clickToSort(column);
        }
        if (sortingOrder.equals(SortingOrder.DESCENDING))
        {
            subscribedCasesTablePage().clickToSort(column);
            subscribedCasesTablePage().clickToSort(column);
        }
        subscribedCasesTablePage().waitForTableToReload();
        SortingOrder newSortingOrder = subscribedCasesTablePage().defineSortingOrder(column);
        //5. Column is shown as sorted
        //6. Column sorting is correct
        List<String> columnValues = subscribedCasesTablePage().getAllValuesInGivenColumn(column);
        boolean columnIsSorted = subscribedCasesTablePage().areValuesFromNotesColumnSorted(columnValues);

        Assertions.assertAll
                (
                        () -> Assertions.assertEquals(sortingOrder, newSortingOrder, String.format("Column is not shown as sorted. " +
                                "Sorting order is: %s", newSortingOrder)),
                        () -> Assertions.assertTrue(columnIsSorted, String.format("Column values are not in fact sorted" +
                                "\nActual column values: %s", columnValues))
                );
    }

    /**
     * HALCYONST-8958 - Cases - Sorting Columns
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Check default state is unsorted
     * 4. Sort column
     * 5. Column is shown as sorted
     * 6. Column sorting is correct
     */

    private static Stream<Arguments> provideDataForSorting()
    {
        return Stream.of(
                Arguments.of(TableColumns.TITLE, SortingOrder.ASCENDING),
                Arguments.of(TableColumns.TITLE, SortingOrder.DESCENDING),
                Arguments.of(TableColumns.LOADED_DATE, SortingOrder.ASCENDING),
                Arguments.of(TableColumns.LOADED_DATE, SortingOrder.DESCENDING),
                Arguments.of(TableColumns.HEADNOTES, SortingOrder.ASCENDING),
                Arguments.of(TableColumns.HEADNOTES, SortingOrder.DESCENDING),
                Arguments.of(TableColumns.SUBSCRIPTIONS, SortingOrder.ASCENDING),
                Arguments.of(TableColumns.SUBSCRIPTIONS, SortingOrder.DESCENDING)
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForSorting")
    @EDGE
    @LEGAL
    @LOG
    public void casesSortingTest(TableColumns column, SortingOrder sortingOrder)
    {
        String filterContainsValue = "mith v. S";

        //1. Open Subscribed cases page for IOWA (Development)
        casesPageAngular().openNodCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        Assertions.assertTrue(casesPageAngular().isPageOpened(), "NOD Cases page didn't open");

        //3. Check default state is unsorted
        Assertions.assertEquals(SortingOrder.UNSORTED, casesTablePage().defineSortingOrder(column), "Column is not unsorted by default.");

        // Narrow results down
        casesTablePage().openFilterOptionsForColumn(TableColumns.TITLE);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, filterContainsValue);
        casesTablePage().waitForGridRefresh();
        casesTablePage().click(CasesPageElementsAngular.GRID);

        //4. Sort column
        if (sortingOrder.equals(SortingOrder.ASCENDING))
        {
            casesTablePage().clickToSort(column);
        }
        if (sortingOrder.equals(SortingOrder.DESCENDING))
        {
            casesTablePage().clickToSort(column);
            casesTablePage().clickToSort(column);
        }
        casesTablePage().waitForTableToReload();
        SortingOrder newSortingOrder = casesTablePage().defineSortingOrder(column);
        //5. Column is shown as sorted
        //6. Column sorting is correct
        List<String> columnValues = casesTablePage().getAllValuesInGivenColumn(column);
        SortingOrder actualColumnValuesSorting = casesTablePage().getColumnValuesSorting(column, columnValues);

        Assertions.assertAll
        (
            () -> Assertions.assertEquals(sortingOrder, newSortingOrder, String.format("Column is not shown as sorted. " + "Sorting order is: %s", newSortingOrder)),
            () -> Assertions.assertEquals(sortingOrder, actualColumnValuesSorting, String.format("Column values are not sorted as expected. \n" + "Expected order: %s.", sortingOrder.getSortingOrderAsString()))
        );
    }

    /**
     * HALCYONST-14454
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void clearSortTest()
    {
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");
        List<String> columnValuesBeforeSorting = subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.TITLE).subList(0,10);
        subscribedCasesTablePage().clickToSort(TableColumns.TITLE);
        List<String> columnValuesAfterSorting = subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.TITLE).subList(0,10);
        subscribedCasesPageAngular().clickClearSort();
        List<String> columnValuesAfterClearing= subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.TITLE).subList(0,10);

        Assertions.assertAll
                (
                        () -> Assertions.assertNotEquals(columnValuesBeforeSorting, columnValuesAfterSorting, "After sorting order wasn't changed"),
                        () -> Assertions.assertEquals(columnValuesBeforeSorting,columnValuesAfterClearing, "After clear sort order wasn't clearing")
                );
    }
}
