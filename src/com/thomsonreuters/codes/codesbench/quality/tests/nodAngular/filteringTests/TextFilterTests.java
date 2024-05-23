package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular.filteringTests;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tableAngular.popups.FilterPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.FilterOperator;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.JoinOperator;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;

public class TextFilterTests extends TestService
{
    /**
     * HALCYONST-8958 - Cases - Filtering Text Columns
     * 1. Open Cases page for IOWA (Development)
     * 2. VERIFY: Cases page is opened
     * 3. Access a filter for the Case Serial column
     * 4. VERIFY: default condition is "Contains" and 1 text input field is present
     * 5. Set a filter for Title column as "Contains" "2050-9"
     * 6. VERIFY: data is filtered accordingly
     * 7. Access a filter for Title column
     * 8. VERIFY: default condition is "Contains" and 1 text input field is present
     * 9. Select the first filter condition
     * 11. Type value in the first condition input field
     * 12. VERIFY: join operators selector appears with values "AND" and "OR", as well as second condition appears with default value "Contains"
     * 13. VERIFY: page is reloaded an column content is filtered
     * 14. Select join operator
     * 15. Select the second condition and type value in text field
     * 16. VERIFY: page is reloaded an column content is filtered
     */

    private static Stream<Arguments> provideDataForTextFiltersSub()
    {
        return Stream.of(
                Arguments.of(TableColumns.TITLE, FilterOperator.EQUALS, "State v. Beres", JoinOperator.AND, FilterOperator.ENDS_WITH, "Beres"),
                Arguments.of(TableColumns.TITLE, FilterOperator.EQUALS, "State v. Beres", JoinOperator.OR, FilterOperator.NOT_CONTAINS, "United States"),
                Arguments.of(TableColumns.TITLE, FilterOperator.CONTAINS, "United", JoinOperator.OR, FilterOperator.ENDS_WITH, "Company"),
                Arguments.of(TableColumns.TITLE, FilterOperator.CONTAINS, "United", JoinOperator.AND, FilterOperator.NOT_EQUAL, "United States v. Hunt"),
                Arguments.of(TableColumns.TITLE, FilterOperator.CONTAINS, "United", JoinOperator.AND, FilterOperator.NOT_CONTAINS, "Hunt"),
                Arguments.of(TableColumns.TITLE, FilterOperator.STARTS_WITH, "United", JoinOperator.AND, FilterOperator.NOT_CONTAINS, "Hunt"),
                Arguments.of(TableColumns.TITLE, FilterOperator.STARTS_WITH, "United", JoinOperator.AND, FilterOperator.ENDS_WITH, "Hunt"),
                Arguments.of(TableColumns.TITLE, FilterOperator.STARTS_WITH, "United", JoinOperator.OR, FilterOperator.ENDS_WITH, "Company")
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForTextFiltersSub")
    @EDGE
    @LEGAL
    @LOG
    public void subscribedCasesTextFilterTest(TableColumns column, FilterOperator firstFilterOperator,
                                              String firstFilterValue, JoinOperator joinOperator, FilterOperator secondFilterOperator, String secondFilterValue)
    {
        String filterContainsValue = "2050-9";

        //1. Open Subscribed cases page for IOWA (Development)
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Subscribed Cases page is opened
        boolean subscribedCasesPageIsOpened = subscribedCasesPageAngular().isPageOpened();
        Assertions.assertTrue(subscribedCasesPageIsOpened, "NOD Subscribed Cases page didn't open");
        //3. Access a filter for the Case Serial column
        subscribedCasesTablePage().openFilterOptionsForColumn(TableColumns.CASE_SERIAL_SUBSCRIBED_CASES);
        //4. VERIFY: default condition is "Contains" and 1 text input field is present
        String defaultFilterValueTitle = filterPopUp().getSelectedDropdownOptionText(FilterPopupElementsAngular.FIRST_FILTER_CONDITION);
        boolean defaultValueInTitleIsCorrect = defaultFilterValueTitle.equals(FilterOperator.CONTAINS.getText());
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        //5. Set a filter for Title column as "Contains" "2050-9"
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, filterContainsValue);
        //get cell to check if table is reloaded
        subscribedCasesTablePage().waitForTableToReload();
        //Save values from Title column before filtering
        List<String> valuesInHNColumnBeforeFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(column);
        //generate expected value based on filter selected
        List<String> expectedResultOfHNFirstColumnFiltering = subscribedCasesTablePage().applyTextFilter(
                valuesInHNColumnBeforeFiltering, firstFilterOperator, firstFilterValue).stream().sorted().collect(Collectors.toList());
        //generate second expected value based on filter selected
        List<String> expectedResultOfSecondHNColumnFiltering = generateExpectedResultOfSecondHNColumnFiltering(joinOperator, valuesInHNColumnBeforeFiltering,
                expectedResultOfHNFirstColumnFiltering, secondFilterOperator, secondFilterValue);
        //7. Access a filter for Title column
        subscribedCasesTablePage().openFilterOptionsForColumn(column);
        //8. VERIFY: default condition is "Contains" and 1 text input field is present
        String defaultValueHN = filterPopUp().getSelectedDropdownOptionText(FilterPopupElementsAngular.FIRST_FILTER_CONDITION);
        boolean defaultValueInHNIsCorrect = defaultValueHN.equals(FilterOperator.CONTAINS.getText());
        //9. Select the first filter condition
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, firstFilterOperator);
        //11. Type value in the first condition input field
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, firstFilterValue);
        //get cell to check if table is reloaded
        subscribedCasesTablePage().waitForTableToReload();
        //12. VERIFY: join operators selector appears with values "AND" and "OR", as well as second condition appears with default value "Contains"
        boolean joinOperatorsAppeared = !subscribedCasesTablePage().checkIfElementHidden(FilterPopupElementsAngular.JOIN_OPERATORS_PANEL);
        boolean defaultJoinOperatorIsAnd = filterPopUp().checkWhichJoinOperatorIsSelected().equals(JoinOperator.AND);
        boolean secondConditionAppeared = subscribedCasesTablePage().doesElementExist(FilterPopupElementsAngular.SECOND_FILTER_CONDITION);
        //13. VERIFY: page is reloaded an column content is filtered
        List<String> valuesInHNColumnAfterFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(column)
                .stream().sorted().collect(Collectors.toList());
        //14. Select join operator
        filterPopUp().selectJoinOperator(joinOperator);
        //15. Select the second condition and type value in text field
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.SECOND_FILTER_CONDITION, secondFilterOperator);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.SECOND_INPUT_FIELD_FROM, secondFilterValue);
        //16. VERIFY: page is reloaded an column content is filtered
        //get cell to check if table is reloaded
        subscribedCasesTablePage().waitForTableToReload();

        List<String> valuesInHNColumnAfterSecondFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(column)
                .stream().sorted().collect(Collectors.toList());

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(defaultValueInTitleIsCorrect, String.format("Unexpected default value of the first " +
                                "filter operator for Case Serial column. Expected: %s, Actual: %s", FilterOperator.CONTAINS.getText(), defaultValueHN)),
                        () -> Assertions.assertTrue(defaultValueInHNIsCorrect, String.format("Unexpected default value of the second " +
                                        "filter operator for %s column. Expected: %s, Actual: %s", column.getColumnHeaderName(),
                                FilterOperator.EQUALS.getText(), defaultValueHN)),
                        () -> Assertions.assertTrue(joinOperatorsAppeared, "Join operators didn't appear after first filter condition is filled"),
                        () -> Assertions.assertTrue(defaultJoinOperatorIsAnd, "Join operator AND is not selected by default"),
                        () -> Assertions.assertTrue(secondConditionAppeared, "Second filter condition didn't " +
                                "appear after first filter condition is filled"),
                        () -> Assertions.assertEquals(expectedResultOfHNFirstColumnFiltering, valuesInHNColumnAfterFiltering, String.format("The %s column filtering " +
                                        "%s %s) results are not what was expected.",
                                column.getColumnHeaderName(), secondFilterOperator.getText(), firstFilterValue)),
                        () -> Assertions.assertEquals(expectedResultOfSecondHNColumnFiltering, valuesInHNColumnAfterSecondFiltering, String.format("The %s column second " +
                                        "filtering (%s %s) results are not what was expected.",
                                column.getColumnHeaderName(), secondFilterOperator.getText(), secondFilterValue))
                );
    }

    /**
     * HALCYONST-8958 - Cases - Filtering Text Columns
     * 1. Open Cases page for IOWA (Development)
     * 2. VERIFY: Cases page is opened
     * 3. Access a filter for the Case Serial column
     * 4. VERIFY: default condition is "Contains" and 1 text input field is present
     * 5. Set a filter for Title column as "Contains" "2050-9"
     * 6. VERIFY: data is filtered accordingly
     * 7. Access a filter for Title column
     * 8. VERIFY: default condition is "Contains" and 1 text input field is present
     * 9. Select the first filter condition
     * 11. Type value in the first condition input field
     * 12. VERIFY: join operators selector appears with values "AND" and "OR", as well as second condition appears with default value "Contains"
     * 13. VERIFY: page is reloaded an column content is filtered
     * 14. Select join operator
     * 15. Select the second condition and type value in text field
     * 16. VERIFY: page is reloaded an column content is filtered
     * 17. Select team content set "Iowa (Development)"
     * 18. VERIFY: all filters are still are retained
     */

    private static Stream<Arguments> provideDataForTextFilters()
    {
        return Stream.of(
                Arguments.of(TableColumns.TITLE, FilterOperator.EQUALS, "In re Herrera",
                        JoinOperator.OR, FilterOperator.ENDS_WITH, "Bush"),
                Arguments.of(TableColumns.TITLE, FilterOperator.NOT_EQUAL, "In re Herrera",
                        JoinOperator.OR, FilterOperator.CONTAINS, "Herrera"),
                Arguments.of(TableColumns.TITLE, FilterOperator.CONTAINS, "In re", JoinOperator.OR, FilterOperator.ENDS_WITH, "Connecticut"),
                Arguments.of(TableColumns.TITLE, FilterOperator.CONTAINS, "In re", JoinOperator.AND, FilterOperator.NOT_EQUAL, "In re Herrera"),
                Arguments.of(TableColumns.TITLE, FilterOperator.CONTAINS, "In re", JoinOperator.AND, FilterOperator.NOT_CONTAINS, "Bush"),
                Arguments.of(TableColumns.TITLE, FilterOperator.STARTS_WITH, "In re", JoinOperator.AND, FilterOperator.NOT_CONTAINS, "Bush"),
                Arguments.of(TableColumns.TITLE, FilterOperator.STARTS_WITH, "In re", JoinOperator.AND, FilterOperator.ENDS_WITH, "Herrera"),
                Arguments.of(TableColumns.TITLE, FilterOperator.STARTS_WITH, "In re", JoinOperator.OR, FilterOperator.ENDS_WITH, "Misconduct")
        );
    }

    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @MethodSource("provideDataForTextFilters")
    @EDGE
    @LEGAL
    @LOG
    public void casesTextFilter(TableColumns column, FilterOperator firstFilterOperator,
                                String firstFilterValue, JoinOperator joinOperator, FilterOperator secondFilterOperator, String secondFilterValue)
    {
        String filterContainsValue = "2045";
        String iowaDevelopmentContentSet = "Iowa (Development)";

        //1. Open Cases page for IOWA (Development)
        casesPageAngular().openNodCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. VERIFY: Cases page is opened
        boolean casesPageIsOpened = casesPageAngular().isPageOpened();
        Assertions.assertTrue(casesPageIsOpened, "NOD Cases page didn't open");
        //3. Access a filter for the Case Serial column
        casesTablePage().openFilterOptionsForColumn(TableColumns.CASE_SERIAL_CASES);
        //4. VERIFY: default condition is "Contains" and 1 text input field is present
        String defaultFilterValueTitle = filterPopUp().getSelectedDropdownOptionText(FilterPopupElementsAngular.FIRST_FILTER_CONDITION);
        boolean defaultValueInTitleIsCorrect = defaultFilterValueTitle.equals(FilterOperator.CONTAINS.getText());
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        //5. Set a filter for Title column as "Contains" "2045"
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, filterContainsValue);
        casesTablePage().waitForTableToReload();
        //Save values from Title column before filtering
        List<String> valuesInHNColumnBeforeFiltering = casesTablePage().getAllValuesInGivenColumn(column);
        //generate expected value based on filter selected
        List<String> expectedResultOfHNFirstColumnFiltering = subscribedCasesTablePage().applyTextFilter(
                valuesInHNColumnBeforeFiltering, firstFilterOperator, firstFilterValue).stream().sorted().collect(Collectors.toList());
        //generate second expected value based on filter selected
        List<String> expectedResultOfSecondHNColumnFiltering = generateExpectedResultOfSecondHNColumnFiltering(joinOperator, valuesInHNColumnBeforeFiltering,
                expectedResultOfHNFirstColumnFiltering, secondFilterOperator, secondFilterValue);
        //7. Access a filter for Title column
        casesTablePage().clickToSort(column);
        casesTablePage().openFilterOptionsForColumn(column);
        //8. VERIFY: default condition is "Contains" and 1 text input field is present
        String defaultValueHN = filterPopUp().getSelectedDropdownOptionText(FilterPopupElementsAngular.FIRST_FILTER_CONDITION);
        boolean defaultValueInHNIsCorrect = defaultValueHN.equals(FilterOperator.CONTAINS.getText());
        //9. Select the first filter condition
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, firstFilterOperator);
        //11. Type value in the first condition input field
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, firstFilterValue);

        //get cell to check if table is reloaded
        casesTablePage().waitForTableToReload();

        //12. VERIFY: join operators selector appears with values "AND" and "OR", as well as second condition appears with default value "Contains"
        boolean joinOperatorsAppeared = !casesTablePage().checkIfElementHidden(FilterPopupElementsAngular.JOIN_OPERATORS_PANEL);
        boolean defaultJoinOperatorIsAnd = filterPopUp().checkWhichJoinOperatorIsSelected().equals(JoinOperator.AND);
        boolean secondConditionAppeared = casesTablePage().doesElementExist(FilterPopupElementsAngular.SECOND_FILTER_CONDITION);
        //13. VERIFY: page is reloaded an column content is filtered
        List<String> valuesInHNColumnAfterFiltering = casesTablePage().getAllValuesInGivenColumn(column);
        //14. Select join operator
        filterPopUp().selectJoinOperator(joinOperator);
        //15. Select the second condition and type value in text field
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.SECOND_FILTER_CONDITION, secondFilterOperator);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.SECOND_INPUT_FIELD_FROM, secondFilterValue);
        //16. VERIFY: page is reloaded an column content is filtered
        //get cell to check if table is reloaded
        casesTablePage().waitForTableToReload();
        List<String> valuesInHNColumnAfterSecondFiltering = casesTablePage().getAllValuesInGivenColumn(column);
        //17. Select team content set "Iowa (Development)"
        casesPageAngular().selectContentSetTeam(iowaDevelopmentContentSet);
        casesTablePage().waitForTableToReload();
        //18. VERIFY: all filters are still are retained
        boolean hasContentSetChanged = casesPageAngular().getContentSetTeamMessage().equals(iowaDevelopmentContentSet);
        boolean isCaseSerialColumnFiltered = casesTablePage().isColumnFiltered(TableColumns.CASE_SERIAL_CASES);
        boolean isOtherColumnFiltered = casesTablePage().isColumnFiltered(column);

        Assertions.assertAll
                (
                        () -> Assertions.assertTrue(defaultValueInTitleIsCorrect, String.format("Unexpected default value of the first " +
                                "filter operator for Case Serial column. Expected: %s, Actual: %s", FilterOperator.CONTAINS.getText(), defaultValueHN)),
                        () -> Assertions.assertTrue(defaultValueInHNIsCorrect, String.format("Unexpected default value of the second " +
                                        "filter operator for %s column. Expected: %s, Actual: %s", column.getColumnHeaderName(),
                                FilterOperator.EQUALS.getText(), defaultValueHN)),
                        () -> Assertions.assertTrue(joinOperatorsAppeared, "Join operators didn't appear after first filter condition is filled"),
                        () -> Assertions.assertTrue(defaultJoinOperatorIsAnd, "Join operator AND is not selected by default"),
                        () -> Assertions.assertTrue(secondConditionAppeared, "Second filter condition didn't " +
                                "appear after first filter condition is filled"),
                        () -> Assertions.assertEquals(expectedResultOfHNFirstColumnFiltering, valuesInHNColumnAfterFiltering, String.format("The %s column filtering " +
                                        "%s %s) results are not what was expected.",
                                column.getColumnHeaderName(),
                                secondFilterOperator.getText(),
                                firstFilterValue)),
                        () -> Assertions.assertEquals(expectedResultOfSecondHNColumnFiltering,valuesInHNColumnAfterSecondFiltering , String.format("The %s column second " +
                                        "filtering (%s %s) results are not what was expected.",
                                column.getColumnHeaderName(), secondFilterOperator.getText(), secondFilterValue)),
                        () -> Assertions.assertTrue(hasContentSetChanged, "Content set team didn't changed"),
                        () -> Assertions.assertTrue(isCaseSerialColumnFiltered, "Case Serial column is not filtered anymore"),
                        () -> Assertions.assertTrue(isOtherColumnFiltered, "Case Serial column is not filtered anymore")
                );
    }

    private List<String> generateExpectedResultOfSecondHNColumnFiltering(JoinOperator joinOperator,
                                                                         List<String> valuesInHNColumnBeforeFiltering,
                                                                         List<String> expectedResultOfHNFirstColumnFiltering,
                                                                         FilterOperator secondFilterOperator,
                                                                         String secondFilterValue)
    {
        List<String> expectedResultOfSecondHNColumnFiltering;
        if (joinOperator == JoinOperator.AND)
        {
            expectedResultOfSecondHNColumnFiltering = casesTablePage().applyTextFilter(
                    expectedResultOfHNFirstColumnFiltering, secondFilterOperator, secondFilterValue)
                    .stream().sorted().collect(Collectors.toList());
        } else
        {
            List<String> intermediateFilteringResult = casesTablePage().applyTextFilter(
                    valuesInHNColumnBeforeFiltering, secondFilterOperator, secondFilterValue);
            intermediateFilteringResult.removeIf(expectedResultOfHNFirstColumnFiltering::contains);
            expectedResultOfSecondHNColumnFiltering = Stream.concat(expectedResultOfHNFirstColumnFiltering.stream(),
                    intermediateFilteringResult.stream()).sorted().collect(Collectors.toList());
        }
        return expectedResultOfSecondHNColumnFiltering;
    }

    /**
     * HALCYONST-14454
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void clearFilterTest()
    {
        subscribedCasesPageAngular().openNodSubscribedCasesPage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        Assertions.assertTrue(subscribedCasesPageAngular().isPageOpened(), "NOD Subscribed Cases page didn't open");
        List<String> columnValuesBeforeFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.TITLE).subList(0,10);

        subscribedCasesTablePage().openFilterOptionsForColumn(TableColumns.TITLE);
        filterPopUp().selectFilterFromDropdown(FilterPopupElementsAngular.FIRST_FILTER_CONDITION, FilterOperator.CONTAINS);
        filterPopUp().typeTextToFilterField(FilterPopupElementsAngular.FIRST_INPUT_FIELD_FROM, "ty");
        casesTablePage().waitForTableToReload();
        List<String> columnValuesAfterFiltering = subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.TITLE).subList(0,10);

        subscribedCasesPageAngular().clickClearFilters();
        List<String> columnValuesAfterClearing= subscribedCasesTablePage().getAllValuesInGivenColumn(TableColumns.TITLE).subList(0,10);

        Assertions.assertAll
                (
                        () -> Assertions.assertNotEquals(columnValuesBeforeFiltering, columnValuesAfterFiltering, "After filtering order wasn't changed"),
                        () -> Assertions.assertEquals(columnValuesBeforeFiltering,columnValuesAfterClearing, "After clear filtering order wasn't clearing")
                );
    }
}
