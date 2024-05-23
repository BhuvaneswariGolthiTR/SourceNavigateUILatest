package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular.filteringTests;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.popups.FilterPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.Table;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.FilterOperator;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.JoinOperator;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;

public class NumericalFilterTests extends TestService
{
    /**
     * HALCYONST-8958 - Subscribed Cases - Filtering Numerical Columns
     * 1. Open Subscribed cases page for IOWA (Development)
     * 2. VERIFY: Subscribed Cases page is opened
     * 3. Access a filter for the Title column
     * 4. VERIFY: default condition is "Contains" and 1 text input field is present
     * 5. Set a filter for Title column as "Contains" "mith v."
     * 6. VERIFY: data is filtered accordingl
     * 7. Access a filter for the Headnotes (HN) colum
     * 8. VERIFY: default condition is "Equals" and 1 text input field is present
     * 9. Select condition "In range"
     * 10. VERIFY: second input field appears
     * 11. Type values in "to" and "from" input fields
     * 12. VERIFY: join operators selector appears with values "AND" and "OR", as well as second condition appears with default value "Equals
     * 13. VERIFY: page is reloaded an column content is filtered
     * 14. Select join operator
     * 15. Select the second condition and type value in text field
     * 16. VERIFY: page is reloaded an column content is filtered
     */

    private static Stream<Arguments> provideDataForNumericalFiltersWithInRangeSub()
    {
        return Stream.of(
                Arguments.of("1", "3", JoinOperator.AND, FilterOperator.LESS_THAN, "2"),
                Arguments.of("4", "20", JoinOperator.AND, FilterOperator.GREATER_THAN, "7"),
                Arguments.of("1", "12", JoinOperator.AND, FilterOperator.EQUALS, "10"),
                Arguments.of("1", "3", JoinOperator.OR, FilterOperator.LESS_THAN_OR_EQUALS, "7"),
                Arguments.of("1", "3", JoinOperator.OR, FilterOperator.GREATER_THAN_OR_EQUALS, "15"),
                Arguments.of("1", "3", JoinOperator.OR, FilterOperator.NOT_EQUAL, "7")
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForNumericalFiltersWithInRangeSub")
    @EDGE
    @LEGAL
    @LOG
    public void subscribedCasesNumericalFilterWithInRangeFilterTest(String filterInRangeFromValue,
                                                                    String filterInRangeToValue, JoinOperator joinOperator,
                                                                    FilterOperator secondFilterOperator, String secondFilterNumericalValue)
    {
        String filterContainsValue = "mith v. S";
        TableColumns column = TableColumns.HN;

        // Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");

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
        List<String> valuesInHNColumnBeforeFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(column);
        List<String> expectedResultOfHNColumnFiltering1 = subscribedCasesTablePage().applyNumericalFilterInRange(valuesInHNColumnBeforeFiltering, filterInRangeFromValue, filterInRangeToValue);
        List<String> expectedResultOfHNColumnFiltering2 = generateExpectedResultOfSecondHNColumnFiltering(joinOperator, valuesInHNColumnBeforeFiltering, expectedResultOfHNColumnFiltering1, secondFilterOperator, secondFilterNumericalValue);

        // Filter date column by 'In Range', verify two input fields and results
        subscribedCasesTablePage().openFilterOptionsForColumn(column);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.IN_RANGE);
        boolean secondInputFieldAppeared = !subscribedCasesTablePage().checkIfElementHidden(FilterPopupElementsAngular.FIRST_INPUT_FIELD_TO);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, filterInRangeFromValue);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_TO, filterInRangeToValue);
        subscribedCasesTablePage().waitForGridRefresh();

        //12. VERIFY: join operators selector appears with values "AND" and "OR", as well as second condition appears with default value "Equals"
        boolean joinOperatorsAppeared = !subscribedCasesTablePage().checkIfElementHidden(FilterPopupElementsAngular.JOIN_OPERATORS_PANEL);
        boolean defaultJoinOperatorIsAnd = filterPopUp().checkWhichJoinOperatorIsSelected().equals(JoinOperator.AND);
        boolean secondConditionAppeared = subscribedCasesTablePage().doesElementExist(FilterPopupElementsAngular.SECOND_FILTER_CONDITION);

        //Get the date values of the new filter. Filter again using the second condition and grab the second filter results
        List<String> valuesInHNColumnAfterFiltering1 = subscribedCasesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());
        filterPopUp().selectJoinOperator(joinOperator);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.SECOND_FILTER_CONDITION, secondFilterOperator);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.SECOND_INPUT_FIELD_FROM, secondFilterNumericalValue);
        subscribedCasesTablePage().waitForGridRefresh();
        List<String> valuesInHNColumnAfterFiltering2 = subscribedCasesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(allElementsContainCertainSubstring, String.format("Not all values in Title column contain" + " \"%s\" substring", filterContainsValue)),
            () -> Assertions.assertTrue(secondInputFieldAppeared, "Second input field didn't appear"),
            () -> Assertions.assertTrue(joinOperatorsAppeared, "Join operators didn't appear after first filter condition is filled"),
            () -> Assertions.assertTrue(defaultJoinOperatorIsAnd, "Join operator AND is not selected by default"),
            () -> Assertions.assertTrue(secondConditionAppeared, "Second filter condition didn't " + "appear after first filter condition is filled"),
            () -> Assertions.assertEquals(expectedResultOfHNColumnFiltering1, valuesInHNColumnAfterFiltering1, String.format("The %s column filtering " + "(In range %s - %s) results are not what was expected.", column.getColumnHeaderName(), filterInRangeFromValue, filterInRangeToValue)),
            () -> Assertions.assertEquals(expectedResultOfHNColumnFiltering2, valuesInHNColumnAfterFiltering2, String.format("The %s column second " +"filtering (%s %s) results are not what was expected.", column.getColumnHeaderName(), secondFilterOperator.getText(), secondFilterNumericalValue))
        );
    }

    private static Stream<Arguments> provideDataForNumericalFiltersSub()
    {
        return Stream.of(
                Arguments.of(FilterOperator.NOT_EQUAL, "3", JoinOperator.AND, FilterOperator.GREATER_THAN, "1"),
                Arguments.of(FilterOperator.EQUALS, "10", JoinOperator.OR, FilterOperator.LESS_THAN_OR_EQUALS, "2"),
                Arguments.of(FilterOperator.GREATER_THAN_OR_EQUALS, "2", JoinOperator.AND, FilterOperator.LESS_THAN, "12"),
                Arguments.of(FilterOperator.GREATER_THAN, "2", JoinOperator.AND, FilterOperator.NOT_EQUAL, "12"),
                Arguments.of(FilterOperator.LESS_THAN_OR_EQUALS, "10", JoinOperator.OR, FilterOperator.EQUALS, "12"),
                Arguments.of(FilterOperator.LESS_THAN, "12", JoinOperator.AND, FilterOperator.NOT_EQUAL, "10")
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForNumericalFiltersSub")
    @EDGE
    @LEGAL
    @LOG
    public void subscribedCasesNumericalFilterTest(FilterOperator firstFilterOperator,
                                                   String firstFilterNumericalValue, JoinOperator joinOperator,
                                                   FilterOperator secondFilterOperator, String secondFilterNumericalValue)
    {
        String filterContainsValue = "mith v. S";
        TableColumns column = TableColumns.HN;

        // Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");

        // Verify filter dropdown and filter for the Title column
        subscribedCasesTablePage().openFilterOptionsForColumn(TableColumns.TITLE);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, filterContainsValue);
        subscribedCasesTablePage().waitForGridRefresh();

        // Save values from Title column after filtering and verify they are filtered correctly
        List<String> valuesInTitleColumnAfterFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.TITLE);
        List<String> expectedValuesInTitleColumn = subscribedCasesTablePage().applyFilterContainString(valuesInTitleColumnAfterFiltering, filterContainsValue);
        boolean allElementsContainCertainSubstring = valuesInTitleColumnAfterFiltering.equals(expectedValuesInTitleColumn);

        // Expected value of first and second HN filter
        List<String> valuesInHNColumnBeforeFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(column);
        List<String> expectedResultOfHNColumnFiltering1 = subscribedCasesTablePage().applyNumericalFilter(valuesInHNColumnBeforeFiltering, firstFilterOperator, firstFilterNumericalValue);
        List<String> expectedResultOfHNColumnFiltering2 = generateExpectedResultOfSecondHNColumnFiltering(joinOperator, valuesInHNColumnBeforeFiltering, expectedResultOfHNColumnFiltering1, secondFilterOperator, secondFilterNumericalValue);

        // Filter HN column by operator
        subscribedCasesTablePage().openFilterOptionsForColumn(column);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, firstFilterOperator);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, firstFilterNumericalValue);
        subscribedCasesTablePage().waitForGridRefresh();

        //12. VERIFY: join operators selector appears with values "AND" and "OR", as well as second condition appears with default value "Equals"
        boolean joinOperatorsAppeared = !subscribedCasesTablePage().checkIfElementHidden(FilterPopupElementsAngular.JOIN_OPERATORS_PANEL);
        boolean defaultJoinOperatorIsAnd = filterPopUp().checkWhichJoinOperatorIsSelected().equals(JoinOperator.AND);
        boolean secondConditionAppeared = subscribedCasesTablePage().doesElementExist(FilterPopupElementsAngular.SECOND_FILTER_CONDITION);

        //Get the date values of the new filter. Filter again using the second condition and grab the second filter results
        List<String> valuesInHNColumnAfterFiltering1 = subscribedCasesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());
        filterPopUp().selectJoinOperator(joinOperator);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.SECOND_FILTER_CONDITION, secondFilterOperator);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.SECOND_INPUT_FIELD_FROM, secondFilterNumericalValue);
        subscribedCasesTablePage().waitForGridRefresh();
        List<String> valuesInHNColumnAfterFiltering2 = subscribedCasesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(allElementsContainCertainSubstring, String.format("Not all values in Title column contain" + " \"%s\" substring", filterContainsValue)),
            () -> Assertions.assertTrue(joinOperatorsAppeared, "Join operators didn't appear after first filter condition is filled"),
            () -> Assertions.assertTrue(defaultJoinOperatorIsAnd, "Join operator AND is not selected by default"),
            () -> Assertions.assertTrue(secondConditionAppeared, "Second filter condition didn't " + "appear after first filter condition is filled"),
            () -> Assertions.assertEquals(expectedResultOfHNColumnFiltering1, valuesInHNColumnAfterFiltering1, String.format("The %s column filtering " + "(In range %s - %s) results are not what was expected.", column.getColumnHeaderName(), firstFilterOperator.getText(), firstFilterNumericalValue)),
            () -> Assertions.assertEquals(expectedResultOfHNColumnFiltering2, valuesInHNColumnAfterFiltering2, String.format("The %s column second " +"filtering (%s %s) results are not what was expected.", column.getColumnHeaderName(), secondFilterOperator.getText(), secondFilterNumericalValue))
        );
    }

    /**
     * HALCYONST-8958 - Cases - Filtering Numerical Columns
     * 1. Open Cases page for IOWA (Development)
     * 2. VERIFY: SCases page is opened
     * 3. Access a filter for the Title column
     * 4. VERIFY: default condition is "Contains" and 1 text input field is present
     * 5. Set a filter for Title column as "Contains" "mith v."
     * 6. VERIFY: data is filtered accordingl
     * 7. Access a filter for the Headnotes column
     * 8. VERIFY: default condition is "Equals" and 1 text input field is present
     * 9. Select condition "In range"
     * 10. VERIFY: second input field appears
     * 11. Type values in "to" and "from" input fields
     * 12. VERIFY: join operators selector appears with values "AND" and "OR", as well as second condition appears with default value "Equals
     * 13. VERIFY: page is reloaded an column content is filtered
     * 14. Select join operator
     * 15. Select the second condition and type value in text field
     * 16. VERIFY: page is reloaded an column content is filtered
     */

    private static Stream<Arguments> provideDataForNumericalFilters()
    {
        return Stream.of(
                Arguments.of(FilterOperator.NOT_EQUAL, "3", JoinOperator.AND, FilterOperator.GREATER_THAN, "1"),
                Arguments.of(FilterOperator.EQUALS, "7", JoinOperator.OR, FilterOperator.LESS_THAN_OR_EQUALS, "3"),
                Arguments.of(FilterOperator.GREATER_THAN_OR_EQUALS, "2", JoinOperator.AND, FilterOperator.LESS_THAN, "7"),
                Arguments.of(FilterOperator.GREATER_THAN, "2", JoinOperator.AND, FilterOperator.NOT_EQUAL, "2"),
                Arguments.of(FilterOperator.LESS_THAN_OR_EQUALS, "6", JoinOperator.OR, FilterOperator.EQUALS, "15"),
                Arguments.of(FilterOperator.LESS_THAN, "3", JoinOperator.AND, FilterOperator.NOT_EQUAL, "2")
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForNumericalFilters")
    @EDGE
    @LEGAL
    @LOG
    public void casesNumericalFilter(FilterOperator firstFilterOperator,
                                     String firstFilterNumericalValue, JoinOperator joinOperator,
                                     FilterOperator secondFilterOperator, String secondFilterNumericalValue)
    {
        String filterContainsValue = "mith v. S";
        TableColumns column = TableColumns.HEADNOTES;

        // Open Cases page for IOWA (Development)
        casesPageAngular().openNodCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        Assertions.assertTrue(casesPageAngular().isPageOpened(), "NOD Cases page didn't open");

        // Verify filter dropdown and filter for the Title column
        casesTablePage().openFilterOptionsForColumn(TableColumns.TITLE);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, filterContainsValue);
        casesTablePage().waitForGridRefresh();

        // Save values from Title column after filtering and verify they are filtered correctly
        List<String> valuesInTitleColumnAfterFiltering = casesTablePage().getAllValuesInGivenColumn(TableColumns.TITLE);
        List<String> expectedValuesInTitleColumn = casesTablePage().applyFilterContainString(valuesInTitleColumnAfterFiltering, filterContainsValue);
        boolean allElementsContainCertainSubstring = valuesInTitleColumnAfterFiltering.equals(expectedValuesInTitleColumn);

        // Expected value of first and second HN filter
        List<String> valuesInHNColumnBeforeFiltering = casesTablePage().getAllValuesInGivenColumn(column);
        List<String> expectedResultOfHNColumnFiltering1 = casesTablePage().applyNumericalFilter(valuesInHNColumnBeforeFiltering, firstFilterOperator, firstFilterNumericalValue);
        List<String> expectedResultOfHNColumnFiltering2 = generateExpectedResultOfSecondHNColumnFiltering(joinOperator, valuesInHNColumnBeforeFiltering, expectedResultOfHNColumnFiltering1, secondFilterOperator, secondFilterNumericalValue);

        // Filter HN column by operator
        casesTablePage().openFilterOptionsForColumn(column);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, firstFilterOperator);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, firstFilterNumericalValue);
        casesTablePage().waitForGridRefresh();

        //12. VERIFY: join operators selector appears with values "AND" and "OR", as well as second condition appears with default value "Equals"
        boolean joinOperatorsAppeared = !casesTablePage().checkIfElementHidden(FilterPopupElementsAngular.JOIN_OPERATORS_PANEL);
        boolean defaultJoinOperatorIsAnd = filterPopUp().checkWhichJoinOperatorIsSelected().equals(JoinOperator.AND);
        boolean secondConditionAppeared = casesTablePage().doesElementExist(FilterPopupElementsAngular.SECOND_FILTER_CONDITION);

        //Get the date values of the new filter. Filter again using the second condition and grab the second filter results
        List<String> valuesInHNColumnAfterFiltering1 = casesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());
        filterPopUp().selectJoinOperator(joinOperator);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.SECOND_FILTER_CONDITION, secondFilterOperator);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.SECOND_INPUT_FIELD_FROM, secondFilterNumericalValue);
        subscribedCasesTablePage().waitForGridRefresh();
        List<String> valuesInHNColumnAfterFiltering2 = casesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(allElementsContainCertainSubstring, String.format("Not all values in Title column contain" + " \"%s\" substring", filterContainsValue)),
            () -> Assertions.assertTrue(joinOperatorsAppeared, "Join operators didn't appear after first filter condition is filled"),
            () -> Assertions.assertTrue(defaultJoinOperatorIsAnd, "Join operator AND is not selected by default"),
            () -> Assertions.assertTrue(secondConditionAppeared, "Second filter condition didn't " + "appear after first filter condition is filled"),
            () -> Assertions.assertEquals(expectedResultOfHNColumnFiltering1, valuesInHNColumnAfterFiltering1, String.format("The %s column filtering " + "(In range %s - %s) results are not what was expected.", column.getColumnHeaderName(), firstFilterOperator.getText(), firstFilterNumericalValue)),
            () -> Assertions.assertEquals(expectedResultOfHNColumnFiltering2, valuesInHNColumnAfterFiltering2, String.format("The %s column second " +"filtering (%s %s) results are not what was expected.", column.getColumnHeaderName(), secondFilterOperator.getText(), secondFilterNumericalValue))
        );
    }

    private static Stream<Arguments> provideDataForNumericalFiltersWithInRange()
    {
        return Stream.of(
                Arguments.of("1", "3", JoinOperator.AND, FilterOperator.LESS_THAN, "2"),
                Arguments.of("4", "20", JoinOperator.AND, FilterOperator.GREATER_THAN, "7"),
                Arguments.of("1", "3", JoinOperator.AND, FilterOperator.EQUALS, "2"),
                Arguments.of("1", "3", JoinOperator.OR, FilterOperator.LESS_THAN_OR_EQUALS, "7"),
                Arguments.of("1", "3", JoinOperator.OR, FilterOperator.GREATER_THAN_OR_EQUALS, "15"),
                Arguments.of("1", "3", JoinOperator.OR, FilterOperator.NOT_EQUAL, "7")
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForNumericalFiltersWithInRange")
    @EDGE
    @LEGAL
    @LOG
    public void casesNumericalFilterWithInRangeFilter(String filterInRangeFromValue, String filterInRangeToValue,
                                                      JoinOperator joinOperator, FilterOperator secondFilterOperator, String secondFilterNumericalValue)
    {
        String filterContainsValue = "mith v. S";
        TableColumns column = TableColumns.HEADNOTES;

        // Open Subscribed cases page for IOWA (Development)
        casesPageAngular().openNodCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        Assertions.assertTrue(casesPageAngular().isPageOpened(), "NOD Cases page didn't open");

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
        List<String> valuesInHNColumnBeforeFiltering = casesTablePage().getAllValuesInGivenColumn(column);
        List<String> expectedResultOfHNColumnFiltering1 = casesTablePage().applyNumericalFilterInRange(valuesInHNColumnBeforeFiltering, filterInRangeFromValue, filterInRangeToValue);
        List<String> expectedResultOfHNColumnFiltering2 = generateExpectedResultOfSecondHNColumnFiltering(joinOperator, valuesInHNColumnBeforeFiltering, expectedResultOfHNColumnFiltering1, secondFilterOperator, secondFilterNumericalValue);

        // Filter date column by 'In Range', verify two input fields and results
        casesTablePage().openFilterOptionsForColumn(column);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.IN_RANGE);
        boolean secondInputFieldAppeared = !casesTablePage().checkIfElementHidden(FilterPopupElementsAngular.FIRST_INPUT_FIELD_TO);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, filterInRangeFromValue);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_TO, filterInRangeToValue);
        casesTablePage().waitForGridRefresh();

        //12. VERIFY: join operators selector appears with values "AND" and "OR", as well as second condition appears with default value "Equals"
        boolean joinOperatorsAppeared = !casesTablePage().checkIfElementHidden(FilterPopupElementsAngular.JOIN_OPERATORS_PANEL);
        boolean defaultJoinOperatorIsAnd = filterPopUp().checkWhichJoinOperatorIsSelected().equals(JoinOperator.AND);
        boolean secondConditionAppeared = casesTablePage().doesElementExist(FilterPopupElementsAngular.SECOND_FILTER_CONDITION);

        //Get the date values of the new filter. Filter again using the second condition and grab the second filter results
        List<String> valuesInHNColumnAfterFiltering1 = casesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());
        filterPopUp().selectJoinOperator(joinOperator);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.SECOND_FILTER_CONDITION, secondFilterOperator);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.SECOND_INPUT_FIELD_FROM, secondFilterNumericalValue);
        casesTablePage().waitForGridRefresh();
        List<String> valuesInHNColumnAfterFiltering2 = casesTablePage().getAllValuesInGivenColumn(column).stream().sorted().collect(Collectors.toList());

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(allElementsContainCertainSubstring, String.format("Not all values in Title column contain" + " \"%s\" substring", filterContainsValue)),
            () -> Assertions.assertTrue(secondInputFieldAppeared, "Second input field didn't appear"),
            () -> Assertions.assertTrue(joinOperatorsAppeared, "Join operators didn't appear after first filter condition is filled"),
            () -> Assertions.assertTrue(defaultJoinOperatorIsAnd, "Join operator AND is not selected by default"),
            () -> Assertions.assertTrue(secondConditionAppeared, "Second filter condition didn't " + "appear after first filter condition is filled"),
            () -> Assertions.assertEquals(expectedResultOfHNColumnFiltering1, valuesInHNColumnAfterFiltering1, String.format("The %s column filtering " + "(In range %s - %s) results are not what was expected.", column.getColumnHeaderName(), filterInRangeFromValue, filterInRangeToValue)),
            () -> Assertions.assertEquals(expectedResultOfHNColumnFiltering2, valuesInHNColumnAfterFiltering2, String.format("The %s column second " +"filtering (%s %s) results are not what was expected.", column.getColumnHeaderName(), secondFilterOperator.getText(), secondFilterNumericalValue))
        );
    }

    private List<String> generateExpectedResultOfSecondHNColumnFiltering(JoinOperator joinOperator, List<String> valuesInHNColumnBeforeFiltering, List<String> expectedResultOfHNFirstColumnFiltering, FilterOperator secondFilterOperator, String secondFilterNumericalValue)
    {
        List<String> expectedResultOfSecondHNColumnFiltering;
        if (joinOperator == JoinOperator.AND)
        {
            expectedResultOfSecondHNColumnFiltering = subscribedCasesTablePage().applyNumericalFilter(expectedResultOfHNFirstColumnFiltering, secondFilterOperator, secondFilterNumericalValue).stream().sorted().collect(Collectors.toList());
        }
        else
        {
            List<String> intermediateFilteringResult = subscribedCasesTablePage().applyNumericalFilter(valuesInHNColumnBeforeFiltering, secondFilterOperator, secondFilterNumericalValue);
            intermediateFilteringResult.removeIf(expectedResultOfHNFirstColumnFiltering::contains);
            expectedResultOfSecondHNColumnFiltering = Stream.concat(expectedResultOfHNFirstColumnFiltering.stream(), intermediateFilteringResult.stream()).sorted().collect(Collectors.toList());
        }
        return expectedResultOfSecondHNColumnFiltering;
    }
}
