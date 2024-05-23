package com.thomsonreuters.codes.codesbench.quality.tests.tools.querynotereportangular;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.lists.ListUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.tableAngular.table.enums.TableColumns;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


public class QueryNoteReportAngularFilterTests extends TestService
{
    /**
     *  Story: Ado 289354
     *  Summary: Check that the filter clearing button works correctly in Query Note (Angular) page
     *  User: Legal
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void queryNoteClearFiltersTest()
    {
        TableColumns typeColumn = TableColumns.QUERY_NOTE_REPORT_TYPE;
        TableColumns statusColumn = TableColumns.QUERY_NOTE_REPORT_STATUS;
        String[] typeFilterValues = {"CRITICAL ISSUE", "INDEX"};
        String[] statusFilterValues = {"ACTIVE", "DELETED"};

        homePage().goToHomePage();
        loginPage().logIn();
        toolsMenu().goToQueryNoteReportAngular();

        //Grab column values before filtering to compare with values after clearing the sorts
        List<String> valuesInTypeColumnBefore = queryNoteReportAngularTablePage().getAllValuesInGivenColumn(typeColumn);

        //Filter for the type and status values
        queryNoteReportAngularTablePage().openFilterOptionsForColumn(typeColumn);
        gridHeaderFiltersPage().setMultipleFilterValues(typeFilterValues);
        queryNoteReportAngularTablePage().openFilterOptionsForColumn(statusColumn);
        gridHeaderFiltersPage().setMultipleFilterValues(statusFilterValues);

        //Verify filters were applied correctly
        List<String> valuesInTypeColumn = queryNoteReportAngularTablePage().getAllValuesInGivenColumn(typeColumn);
        boolean typeFilteredCorrectly = ListUtils.areListEqualIgnoreSize(valuesInTypeColumn, Arrays.asList(typeFilterValues));
        Assertions.assertTrue(typeFilteredCorrectly, "Type column did not filter correctly");
        List<String> valuesInStatusColumn = queryNoteReportAngularTablePage().getAllValuesInGivenColumn(statusColumn);
        boolean statusFilteredCorrectly = ListUtils.areListEqualIgnoreSize(valuesInStatusColumn, Arrays.asList(statusFilterValues));
        Assertions.assertTrue(statusFilteredCorrectly, "Status column did not filter correctly");

        //Clear filter, verify the table goes back to how it was before filtering
        queryNoteReportAngularTablePage().clickClearFilters();
        List<String> valuesInTypeColumnAfter = queryNoteReportAngularTablePage().getAllValuesInGivenColumn(typeColumn);
        boolean verifyClearFilterWorks = valuesInTypeColumnBefore.equals(valuesInTypeColumnAfter);
        Assertions.assertTrue(verifyClearFilterWorks, "The clear filter button did not clear the filters properly");
    }
}