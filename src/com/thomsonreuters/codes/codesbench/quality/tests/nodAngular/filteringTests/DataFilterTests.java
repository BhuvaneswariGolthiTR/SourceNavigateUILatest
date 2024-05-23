package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular.filteringTests;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.CalendarWidgetElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.popups.FilterPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.FilterOperator;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.JoinOperator;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;

public class DataFilterTests extends TestService
{
    /**
     * HALCYONST-8958 - Subscribed Cases - Filtering Text Columns
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Access a filter for the Title column
     * 4. VERIFY: default condition is "Contains" and 1 text input field is present
     * 5. Set a filter for Title column as "Contains" "mith v."
     * 6. VERIFY: data is filtered accordingly
     * 7. Access a filter for Loaded date column
     * 8. VERIFY: default condition is "Equals" and 1 text input field is present
     * 9. Select condition "In range"
     * 10. VERIFY: second input field appears
     * 11. Type values in "to" and "from" input fields
     * 12. VERIFY: join operators selector appears with values "AND" and "OR", as well as second condition appears with default value "Contains"
     * 13. VERIFY: page is reloaded an column content is filtered
     * 14. Select join operator
     * 15. Select the second condition and type value in text field
     * 16. VERIFY: page is reloaded an column content is filtered
     */

    private static Stream<Arguments> provideDataForDateFiltersWithInRangeSub()
    {
        return Stream.of(
                Arguments.of("12/01/2008", "12/05/2018", JoinOperator.AND, FilterOperator.LESS_THAN, "06/30/2015"),
                Arguments.of("12/01/2008", "12/05/2018", JoinOperator.OR, FilterOperator.GREATER_THAN, "06/30/2015"),
                Arguments.of("12/01/2008", "12/05/2014", JoinOperator.OR, FilterOperator.EQUALS, "08/25/2015"),
                Arguments.of("12/01/2008", "12/05/2018", JoinOperator.AND, FilterOperator.NOT_EQUAL, "06/05/2009"),
                Arguments.of("12/01/2008", "12/05/2018", JoinOperator.AND, FilterOperator.LESS_THAN_OR_EQUALS, "06/30/2015"),
                Arguments.of("12/01/2008", "12/05/2018", JoinOperator.OR, FilterOperator.GREATER_THAN_OR_EQUALS, "06/30/2015")
        );
    }
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForDateFiltersWithInRangeSub")
    @EDGE
    @LEGAL
    @LOG
    public void subscribedCasesDateFilterInRangeTest(String filterInRangeFromValue, String filterInRangeToValue,
                                                     JoinOperator joinOperator, FilterOperator secondFilterOperator, String secondFilterDateValue)
    {
        String filterContainsValue = "mith v. S";
        TableColumns column = TableColumns.LOADED_DATE;

        // Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");

        // Verify filter dropdown and filter for the Title column
        subscribedCasesTablePage().openFilterOptionsForColumn(TableColumns.TITLE);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, filterContainsValue);
        subscribedCasesTablePage().waitForGridRefresh();

        // Save values from Title column after filtering and verify they are filtered correctly
        List<String> valuesInTitleColumnAfterFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.TITLE);
        List<String> expectedValuesInTitleColumn = subscribedCasesTablePage().applyFilterContainString(valuesInTitleColumnAfterFiltering, filterContainsValue);
        boolean allElementsContainCertainSubstring = valuesInTitleColumnAfterFiltering.equals(expectedValuesInTitleColumn);

        // Expected value of first and second date filter
        List<String> valuesInDateColumnBeforeFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(column);
        List<String> expectedResultOfDateColumnFiltering1 = subscribedCasesTablePage().applyDateFilterInRange(valuesInDateColumnBeforeFiltering, filterInRangeFromValue, filterInRangeToValue);
        List<String> expectedResultOfDateColumnFiltering2 = generateExpectedResultOfSecondDateColumnFiltering(joinOperator, valuesInDateColumnBeforeFiltering, expectedResultOfDateColumnFiltering1, secondFilterOperator, secondFilterDateValue);

        // Filter date column by 'In Range', verify two input fields and results
        subscribedCasesTablePage().openFilterOptionsForColumn(column);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.IN_RANGE);
        boolean secondInputFieldAppeared = !subscribedCasesTablePage().checkIfElementHidden(FilterPopupElementsAngular.DATE_PICKER_TO);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.DATE_PICKER_FROM, filterInRangeFromValue.replaceAll("/", ""));
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.DATE_PICKER_TO, filterInRangeToValue.replaceAll("/", ""));
        subscribedCasesTablePage().waitForGridRefresh();

        //Verify join operators 'AND' and 'OR' appear as well as a second condition input
        boolean joinOperatorsAppeared = !subscribedCasesTablePage().checkIfElementHidden(FilterPopupElementsAngular.JOIN_OPERATORS_PANEL);
        boolean defaultJoinOperatorIsAnd = filterPopUp().checkWhichJoinOperatorIsSelected().equals(JoinOperator.AND);
        boolean secondConditionAppeared = subscribedCasesTablePage().doesElementExist(FilterPopupElementsAngular.SECOND_FILTER_CONDITION);

        //Get the date values of the new filter. Filter again using the second condition and grab the second filter results
        List<String> valuesInDateColumnAfterFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());
        filterPopUp().selectJoinOperator(joinOperator);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.SECOND_FILTER_CONDITION, secondFilterOperator);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.SECOND_INPUT_FIELD_FROM, secondFilterDateValue.replaceAll("/", ""));
        subscribedCasesTablePage().waitForGridRefresh();
        List<String> valuesInDateColumnAfterSecondFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(allElementsContainCertainSubstring, String.format("Not all values in Title column contain" + " \"%s\" substring", filterContainsValue)),
            () -> Assertions.assertTrue(secondInputFieldAppeared, "Second input field didn't appear"),
            () -> Assertions.assertTrue(joinOperatorsAppeared, "Join operators didn't appear after first filter condition is filled"),
            () -> Assertions.assertTrue(defaultJoinOperatorIsAnd, "Join operator AND is not selected by default"),
            () -> Assertions.assertTrue(secondConditionAppeared, "Second filter condition didn't " + "appear after first filter condition is filled"),
            () -> Assertions.assertEquals(expectedResultOfDateColumnFiltering1, valuesInDateColumnAfterFiltering, String.format("The %s column filtering " + "(In range %s - %s) results are not what was expected.", column.getColumnHeaderName(), filterInRangeFromValue, filterInRangeToValue)),
            () -> Assertions.assertEquals(expectedResultOfDateColumnFiltering2, valuesInDateColumnAfterSecondFiltering, String.format("The %s column second " +"filtering (%s %s) results are not what was expected.", column.getColumnHeaderName(), secondFilterOperator.getText(), secondFilterDateValue))
        );
    }



    /**
     * HALCYONST-8958 - Subscribed Cases - Filtering Text Columns
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Access a filter for the Title column
     * 4. VERIFY: default condition is "Contains" and 1 text input field is present
     * 5. Set a filter for Title column as "Contains" "mith v."
     * 6. VERIFY: data is filtered accordingly
     * 7. Access a filter for Loaded date column
     * 8. VERIFY: default condition is "Equals" and 1 text input field is present
     * 9. Select first condition
     * 10. Select a date by calendar picker
     * 11. VERIFY: join operators selector appears with values "AND" and "OR", as well as second condition appears with default value "Contains"
     * 12. VERIFY: page is reloaded an column content is filtered
     * 13. Select join operator
     * 14. Select the second condition and pick a date by calendar picker
     * 15. VERIFY: page is reloaded an column content is filtered
     */

    private static Stream<Arguments> provideDataForDateFiltersWithCalendarPickerSub()
    {
        return Stream.of(
                Arguments.of(FilterOperator.EQUALS, "08/25/2014", JoinOperator.AND, FilterOperator.LESS_THAN, "06/30/2015"),
                Arguments.of(FilterOperator.NOT_EQUAL, "08/25/2014", JoinOperator.OR, FilterOperator.GREATER_THAN, "06/30/2015"),
                Arguments.of(FilterOperator.EQUALS, "08/25/2014", JoinOperator.AND, FilterOperator.LESS_THAN_OR_EQUALS, "06/30/2015"),
                Arguments.of(FilterOperator.NOT_EQUAL, "08/25/2014", JoinOperator.OR, FilterOperator.GREATER_THAN_OR_EQUALS, "06/30/2015")
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForDateFiltersWithCalendarPickerSub")
    @EDGE
    @LEGAL
    @LOG
    public void subscribedCasesDateFilterUsingCalendarPickerTest(FilterOperator firstFilterOperator, String firstFilterValue, JoinOperator joinOperator, FilterOperator secondFilterOperator, String secondFilterDateValue)
    {
        String filterContainsValue = "mith v. S";
        TableColumns column = TableColumns.LOADED_DATE;

        // Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");

        // Verify filter dropdown and filter for the Title column
        subscribedCasesTablePage().openFilterOptionsForColumn(TableColumns.TITLE);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, filterContainsValue);
        subscribedCasesTablePage().waitForGridRefresh();

        // Save values from Title column after filtering and verify they are filtered correctly
        List<String> valuesInTitleColumnAfterFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.TITLE);
        List<String> expectedValuesInTitleColumn = subscribedCasesTablePage().applyFilterContainString(valuesInTitleColumnAfterFiltering, filterContainsValue);
        boolean allElementsContainCertainSubstring = valuesInTitleColumnAfterFiltering.equals(expectedValuesInTitleColumn);

        // Expected value of first and second date filter
        List<String> valuesInDateColumnBeforeFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(column);
        List<String> expectedResultOfDateColumnFiltering1 = subscribedCasesTablePage().applyDateFilter(valuesInDateColumnBeforeFiltering, firstFilterOperator, firstFilterValue);
        List<String> expectedResultOfDateColumnFiltering2 = generateExpectedResultOfSecondDateColumnFiltering(joinOperator, valuesInDateColumnBeforeFiltering, expectedResultOfDateColumnFiltering1, secondFilterOperator, secondFilterDateValue);

        // Filter date column by operator using the calendar
        subscribedCasesTablePage().openFilterOptionsForColumn(column);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, firstFilterOperator);
        filterPopUp().clickOnCalendarButton(CalendarWidgetElementsAngular.PANEL_FROM_1);
        calendarWidgetAngular().selectDateInCalendar(firstFilterValue);
        subscribedCasesTablePage().waitForGridRefresh();

        // Verify join operators 'AND' and 'OR' appear as well as a second condition input
        boolean joinOperatorsAppeared = !subscribedCasesTablePage().checkIfElementHidden(FilterPopupElementsAngular.JOIN_OPERATORS_PANEL);
        boolean defaultJoinOperatorIsAnd = filterPopUp().checkWhichJoinOperatorIsSelected().equals(JoinOperator.AND);
        boolean secondConditionAppeared = subscribedCasesTablePage().doesElementExist(FilterPopupElementsAngular.SECOND_FILTER_CONDITION);

        //Get the date values of the new filter. Filter again using the second condition and grab the second filter results
        List<String> valuesInDateColumnAfterFiltering1 = subscribedCasesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());
        filterPopUp().selectJoinOperator(joinOperator);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.SECOND_FILTER_CONDITION, secondFilterOperator);
        filterPopUp().clickOnCalendarButton(CalendarWidgetElementsAngular.PANEL_FROM_2);
        calendarWidgetAngular().selectDateInCalendar(secondFilterDateValue);
        subscribedCasesTablePage().waitForGridRefresh();
        List<String> valuesInDateColumnAfterFiltering2 = subscribedCasesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(allElementsContainCertainSubstring, String.format("Not all values in Title column contain" + " \"%s\" substring", filterContainsValue)),
            () -> Assertions.assertTrue(joinOperatorsAppeared, "Join operators didn't appear after first filter condition is filled"),
            () -> Assertions.assertTrue(defaultJoinOperatorIsAnd, "Join operator AND is not selected by default"),
            () -> Assertions.assertTrue(secondConditionAppeared, "Second filter condition didn't " + "appear after first filter condition is filled"),
            () -> Assertions.assertEquals(expectedResultOfDateColumnFiltering1, valuesInDateColumnAfterFiltering1, String.format("The %s column filtering " + "%s %s) results are not what was expected.", column.getColumnHeaderName(), firstFilterOperator.getText(), firstFilterValue)),
            () -> Assertions.assertEquals(expectedResultOfDateColumnFiltering2, valuesInDateColumnAfterFiltering2, String.format("The %s column second " + "filtering (%s %s) results are not what was expected.", column.getColumnHeaderName(), secondFilterOperator.getText(), secondFilterDateValue))
        );
    }

    /**
     * HALCYONST-8958 - Cases - Filtering Text Columns
     * 1. Open Cases page for IOWA (Development)
     * 2. VERIFY: Cases page is opened
     * 3. Access a filter for the Title column
     * 4. VERIFY: default condition is "Contains" and 1 text input field is present
     * 5. Set a filter for Title column as "Contains" "mith v."
     * 6. VERIFY: data is filtered accordingly
     * 7. Access a filter for Loaded date column
     * 8. VERIFY: default condition is "Equals" and 1 text input field is present
     * 9. Select condition "In range"
     * 10. VERIFY: second input field appears
     * 11. Type values in "to" and "from" input fields
     * 12. VERIFY: join operators selector appears with values "AND" and "OR", as well as second condition appears with default value "Contains"
     * 13. VERIFY: page is reloaded an column content is filtered
     * 14. Select join operator
     * 15. Select the second condition and type value in text field
     * 16. VERIFY: page is reloaded an column content is filtered
     * 17. Select team content set "Iowa (Development)"
     * 18. VERIFY: all filters are still are retained
     */

    private static Stream<Arguments> provideDataForDateFiltersWithInRange()
    {
        return Stream.of(
                Arguments.of("12/01/2008", "12/05/2018", JoinOperator.AND, FilterOperator.LESS_THAN, "06/30/2015"),
                Arguments.of("12/01/2008", "12/05/2018", JoinOperator.OR, FilterOperator.GREATER_THAN, "06/30/2015"),
                Arguments.of("12/01/2008", "12/05/2011", JoinOperator.OR, FilterOperator.EQUALS, "06/30/2015"),
                Arguments.of("12/01/2008", "12/05/2018", JoinOperator.AND, FilterOperator.NOT_EQUAL, "06/05/2009"),
                Arguments.of("12/01/2008", "12/05/2018", JoinOperator.AND, FilterOperator.LESS_THAN_OR_EQUALS, "06/30/2015"),
                Arguments.of("12/01/2008", "12/05/2018", JoinOperator.OR, FilterOperator.GREATER_THAN_OR_EQUALS, "06/30/2015")
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForDateFiltersWithInRange")
    @EDGE
    @LEGAL
    @LOG
    public void casesDateFilterInRange(String filterInRangeFromValue, String filterInRangeToValue,
                                       JoinOperator joinOperator, FilterOperator secondFilterOperator, String secondFilterDateValue)
    {
        String filterContainsValue = "mith v.";
        TableColumns column = TableColumns.LOADED_DATE;
        String iowaDevelopmentContentSet = "Iowa (Development)";

        // Open Cases page for IOWA (Development)
        casesPageAngular().openNodCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        boolean casesPageIsOpened = casesPageAngular().isPageOpened();
        Assertions.assertTrue(casesPageIsOpened, "NOD Cases page didn't open");

        // Verify filter dropdown and filter for the Title column
        casesTablePage().openFilterOptionsForColumn(TableColumns.TITLE);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, filterContainsValue);
        casesTablePage().waitForGridRefresh();

        // Save values from Title column after filtering and verify they are filtered correctly
        List<String> valuesInTitleColumnAfterFiltering = casesTablePage().getAllValuesInGivenColumn(TableColumns.TITLE);
        List<String> expectedValuesInTitleColumn = casesTablePage().applyFilterContainString(valuesInTitleColumnAfterFiltering, filterContainsValue);
        boolean allElementsContainCertainSubstring = valuesInTitleColumnAfterFiltering.equals(expectedValuesInTitleColumn);

        // Expected value of first and second date filter
        List<String> valuesInDateColumnBeforeFiltering = casesTablePage().getAllValuesInGivenColumn(column);
        List<String> expectedResultOfDateColumnFiltering1 = casesTablePage().applyDateFilterInRange(valuesInDateColumnBeforeFiltering, filterInRangeFromValue, filterInRangeToValue);
        List<String> expectedResultOfDateColumnFiltering2 = generateExpectedResultOfSecondDateColumnFiltering(joinOperator, valuesInDateColumnBeforeFiltering, expectedResultOfDateColumnFiltering1, secondFilterOperator, secondFilterDateValue);

        // Filter date column by 'In Range', verify two input fields and results
        casesTablePage().openFilterOptionsForColumn(column);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.IN_RANGE);
        boolean secondInputFieldAppeared = !casesTablePage().checkIfElementHidden(FilterPopupElementsAngular.DATE_PICKER_TO);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.DATE_PICKER_FROM, filterInRangeFromValue.replaceAll("/", ""));
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.DATE_PICKER_TO, filterInRangeToValue.replaceAll("/", ""));
        casesTablePage().waitForGridRefresh();

        //Verify join operators 'AND' and 'OR' appear as well as a second condition input
        boolean joinOperatorsAppeared = !casesTablePage().checkIfElementHidden(FilterPopupElementsAngular.JOIN_OPERATORS_PANEL);
        boolean defaultJoinOperatorIsAnd = filterPopUp().checkWhichJoinOperatorIsSelected().equals(JoinOperator.AND);
        boolean secondConditionAppeared = casesTablePage().doesElementExist(FilterPopupElementsAngular.SECOND_FILTER_CONDITION);

        //Get the date values of the new filter. Filter again using the second condition and grab the second filter results
        List<String> valuesInDateColumnAfterFiltering1 = casesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());
        filterPopUp().selectJoinOperator(joinOperator);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.SECOND_FILTER_CONDITION, secondFilterOperator);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.SECOND_INPUT_FIELD_FROM, secondFilterDateValue.replaceAll("/", ""));
        casesTablePage().waitForGridRefresh();
        List<String> valuesInDateColumnAfterFiltering2 = casesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());

        //Verify content set can be changed
        casesPageAngular().selectContentSetTeam(iowaDevelopmentContentSet);
        casesTablePage().waitForGridRefresh();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        boolean hasContentSetChanged = casesPageAngular().getContentSetTeamMessage().equals(iowaDevelopmentContentSet);
        boolean isCaseSerialColumnFiltered = casesTablePage().isColumnFiltered(TableColumns.TITLE);
        boolean isOtherColumnFiltered = casesTablePage().isColumnFiltered(column);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(allElementsContainCertainSubstring, String.format("Not all values in Title column contain" + " \"%s\" substring", filterContainsValue)),
            () -> Assertions.assertTrue(secondInputFieldAppeared, "Second input field didn't appear"),
            () -> Assertions.assertTrue(joinOperatorsAppeared, "Join operators didn't appear after first filter condition is filled"),
            () -> Assertions.assertTrue(defaultJoinOperatorIsAnd, "Join operator AND is not selected by default"),
            () -> Assertions.assertTrue(secondConditionAppeared, "Second filter condition didn't " + "appear after first filter condition is filled"),
            () -> Assertions.assertEquals(expectedResultOfDateColumnFiltering1, valuesInDateColumnAfterFiltering1, String.format("The %s column filtering " + "(In range %s - %s) results are not what was expected.", column.getColumnHeaderName(), filterInRangeFromValue, filterInRangeToValue)),
            () -> Assertions.assertEquals(expectedResultOfDateColumnFiltering2, valuesInDateColumnAfterFiltering2, String.format("The %s column second " + "filtering (%s %s) results are not what was expected.", column.getColumnHeaderName(), secondFilterOperator.getText(), secondFilterDateValue)),
            () -> Assertions.assertTrue(hasContentSetChanged, "Content set team didn't changed"),
            () -> Assertions.assertTrue(isCaseSerialColumnFiltered, "Title column is not filtered anymore"),
            () -> Assertions.assertTrue(isOtherColumnFiltered, "Case Serial column is not filtered anymore")
        );
    }

    /**
     * HALCYONST-8958 - Cases - Filtering Text Columns
     * 1. Open Cases page for IOWA (Development)
     * 2. VERIFY: Cases page is opened
     * 3. Access a filter for the Title column
     * 4. VERIFY: default condition is "Contains" and 1 text input field is present
     * 5. Set a filter for Title column as "Contains" "mith v."
     * 6. VERIFY: data is filtered accordingly
     * 7. Access a filter for Loaded date column
     * 8. VERIFY: default condition is "Equals" and 1 text input field is present
     * 9. Select first condition
     * 10. Select a date by calendar picker
     * 11. VERIFY: join operators selector appears with values "AND" and "OR", as well as second condition appears with default value "Contains"
     * 12. VERIFY: page is reloaded an column content is filtered
     * 13. Select join operator
     * 14. Select the second condition and pick a date by calendar picker
     * 15. VERIFY: page is reloaded an column content is filtered
     * 16. Select team content set "Iowa (Development)"
     * 17. VERIFY: all filters are still are retained
     */

    private static Stream<Arguments> provideDataForDateFiltersWithCalendarPicker()
    {
        return Stream.of(
                Arguments.of(FilterOperator.EQUALS, "08/25/2014", JoinOperator.OR, FilterOperator.LESS_THAN, "06/30/2016"),
                Arguments.of(FilterOperator.NOT_EQUAL, "08/25/2014", JoinOperator.OR, FilterOperator.GREATER_THAN, "11/24/2015"),
                Arguments.of(FilterOperator.EQUALS, "08/25/2014", JoinOperator.OR, FilterOperator.LESS_THAN_OR_EQUALS, "11/24/2015"),
                Arguments.of(FilterOperator.NOT_EQUAL, "08/25/2014", JoinOperator.AND, FilterOperator.GREATER_THAN_OR_EQUALS, "06/30/2008")
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForDateFiltersWithCalendarPicker")
    @EDGE
    @LEGAL
    @LOG
    public void casesDateFilterUsingCalendarPicker(FilterOperator firstFilterOperator,
                                                   String firstFilterValue, JoinOperator joinOperator, FilterOperator secondFilterOperator, String secondFilterDateValue)
    {
        String filterContainsValue = "mith v. S";
        TableColumns column = TableColumns.LOADED_DATE;
        String iowaDevelopmentContentSet = "Iowa (Development)";

        // Open Cases page for IOWA (Development)
        casesPageAngular().openNodCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        boolean casesPageIsOpened = casesPageAngular().isPageOpened();
        Assertions.assertTrue(casesPageIsOpened, "NOD Cases page didn't open");

        // Verify filter dropdown and filter for the Title column
        casesTablePage().openFilterOptionsForColumn(TableColumns.TITLE);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, filterContainsValue);
        casesTablePage().waitForGridRefresh();

        // Save values from Title column after filtering and verify they are filtered correctly
        List<String> valuesInTitleColumnAfterFiltering = casesTablePage().getAllValuesInGivenColumn(TableColumns.TITLE);
        List<String> expectedValuesInTitleColumn = casesTablePage().applyFilterContainString(valuesInTitleColumnAfterFiltering, filterContainsValue);
        boolean allElementsContainCertainSubstring = valuesInTitleColumnAfterFiltering.equals(expectedValuesInTitleColumn);

        // Expected value of first and second date filter
        List<String> valuesInDateColumnBeforeFiltering = casesTablePage().getAllValuesInGivenColumn(column);
        List<String> expectedResultOfDateColumnFiltering1 = casesTablePage().applyDateFilter(valuesInDateColumnBeforeFiltering, firstFilterOperator, firstFilterValue);
        List<String> expectedResultOfDateColumnFiltering2 = generateExpectedResultOfSecondDateColumnFiltering(joinOperator, valuesInDateColumnBeforeFiltering, expectedResultOfDateColumnFiltering1, secondFilterOperator, secondFilterDateValue);

        // Filter date column using calendar
        casesTablePage().sortByColumn(column);
        casesTablePage().openFilterOptionsForColumn(column);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, firstFilterOperator);
        filterPopUp().clickOnCalendarButton(CalendarWidgetElementsAngular.PANEL_FROM_1);
        calendarWidgetAngular().selectDateInCalendar(firstFilterValue);
        casesTablePage().waitForGridRefresh();

        // Verify join operators 'AND' and 'OR' appear as well as a second condition input
        boolean joinOperatorsAppeared = !casesTablePage().checkIfElementHidden(FilterPopupElementsAngular.JOIN_OPERATORS_PANEL);
        boolean defaultJoinOperatorIsAnd = filterPopUp().checkWhichJoinOperatorIsSelected().equals(JoinOperator.AND);
        boolean secondConditionAppeared = casesTablePage().doesElementExist(FilterPopupElementsAngular.SECOND_FILTER_CONDITION);

        //Get the date values of the new filter. Filter again using the second condition and grab the second filter results
        List<String> valuesInDateColumnAfterFiltering1 = casesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());
        filterPopUp().selectJoinOperator(joinOperator);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.SECOND_FILTER_CONDITION, secondFilterOperator);
        filterPopUp().clickOnCalendarButton(CalendarWidgetElementsAngular.PANEL_FROM_2);
        calendarWidgetAngular().selectDateInCalendar(secondFilterDateValue);
        casesTablePage().waitForGridRefresh();
        List<String> valuesInDateColumnAfterFiltering2 = casesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());

        //Verify content set can be changed
        casesPageAngular().selectContentSetTeam(iowaDevelopmentContentSet);
        casesTablePage().waitForTableToReload();
        boolean hasContentSetChanged = casesPageAngular().getContentSetTeamMessage().equals(iowaDevelopmentContentSet);
        boolean isCaseSerialColumnFiltered = casesTablePage().isColumnFiltered(TableColumns.TITLE);
        boolean isOtherColumnFiltered = casesTablePage().isColumnFiltered(column);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(allElementsContainCertainSubstring, String.format("Not all values in Title column contain" + " \"%s\" substring", filterContainsValue)),
            () -> Assertions.assertTrue(joinOperatorsAppeared, "Join operators didn't appear after first filter condition is filled"),
            () -> Assertions.assertTrue(defaultJoinOperatorIsAnd, "Join operator AND is not selected by default"),
            () -> Assertions.assertTrue(secondConditionAppeared, "Second filter condition didn't " + "appear after first filter condition is filled"),
            () -> Assertions.assertEquals(expectedResultOfDateColumnFiltering1, valuesInDateColumnAfterFiltering1, String.format("The %s column filtering " + "%s %s) results are not what was expected.", column.getColumnHeaderName(), secondFilterOperator.getText(), firstFilterValue)),
            () -> Assertions.assertEquals(expectedResultOfDateColumnFiltering2, valuesInDateColumnAfterFiltering2, String.format("The %s column second " + "filtering (%s %s) results are not what was expected.", column.getColumnHeaderName(), secondFilterOperator.getText(), secondFilterDateValue)),
            () -> Assertions.assertTrue(hasContentSetChanged, "Content set team didn't changed"),
            () -> Assertions.assertTrue(isCaseSerialColumnFiltered, "Title column is not filtered anymore"),
            () -> Assertions.assertTrue(isOtherColumnFiltered, "Case Serial column is not filtered anymore")
        );
    }

    private List<String> generateExpectedResultOfSecondDateColumnFiltering(JoinOperator joinOperator, List<String> valuesInHNColumnBeforeFiltering, List<String> expectedResultOfHNFirstColumnFiltering, FilterOperator secondFilterOperator, String secondFilterDateValue)
    {
        List<String> expectedResultOfSecondDateColumnFiltering;
        if (joinOperator == JoinOperator.AND)
        {
            expectedResultOfSecondDateColumnFiltering = subscribedCasesTablePage().applyDateFilter(expectedResultOfHNFirstColumnFiltering, secondFilterOperator, secondFilterDateValue).stream().sorted().collect(Collectors.toList());
        }
        else
        {
            List<String> intermediateFilteringResult = subscribedCasesTablePage().applyDateFilter(valuesInHNColumnBeforeFiltering, secondFilterOperator, secondFilterDateValue);
            intermediateFilteringResult.removeIf(expectedResultOfHNFirstColumnFiltering::contains);
            expectedResultOfSecondDateColumnFiltering = Stream.concat(expectedResultOfHNFirstColumnFiltering.stream(), intermediateFilteringResult.stream()).sorted().collect(Collectors.toList());
        }
        return expectedResultOfSecondDateColumnFiltering;
    }
}
