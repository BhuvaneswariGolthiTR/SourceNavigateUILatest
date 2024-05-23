package com.thomsonreuters.codes.codesbench.quality.tests.source.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class SourceVersionAndLoadDateFiltersTests extends TestService
{

    SourceDatapodObject datapodObject;

    /**
     * STORY/BUG - n/a <br>
     * SUMMARY - Tests source version and source load date search filter window to take date before filter and clear filter <br>
     * USER - Legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sourceVersionAndLoadDateFiltersTest()
    {
        String dateBefore = "11/01/2011";
        datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();

        //Go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter renditions
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(datapodObject.getRenditions().get(0).getDocNumber() + "");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Click rendition load date calendar button and set date before filter
        sourceNavigateFiltersAndSortsPage().clickRenditionLoadDateAndTimeCalendarButton();
        dateSearchFilterPage().clickDateBeforeRadioButton();
        dateSearchFilterPage().setDateBeforeFilter(dateBefore);
        dateSearchFilterPage().clickSubmitButton();
        boolean loadDateWorked = dateSearchFilterPage().validateLoadDateFilterTitle("*** BEF(" + dateBefore + ")");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        boolean noRenditionsAppearWithLoadDateFilter = sourceNavigateFooterToolsPage().getResults() == 0;

        //Click rendition load date calendar button again and clear filter
        sourceNavigateFiltersAndSortsPage().clickRenditionLoadDateAndTimeCalendarButton();
        boolean rendLoadDateHasSameValue = dateSearchFilterPage().checkFilterDateBeforeInputIsExpected(dateBefore);
        dateSearchFilterPage().clickClearFilterRadioButton();
        dateSearchFilterPage().clickSubmitButton();
        boolean rendLoadDateCleared = dateSearchFilterPage().validateLoadDateFilterTitle("");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        boolean oneRenditionAppearedWithLoadDateCleared = sourceNavigateFooterToolsPage().getResults() == 1;

        //Click rendition version date calendar button and set date before filter
        sourceNavigateFiltersAndSortsPage().clickRenditionVersionDateAndTimeCalendarButton();
        dateSearchFilterPage().clickDateBeforeRadioButton();
        dateSearchFilterPage().setDateBeforeFilter(dateBefore);
        dateSearchFilterPage().clickSubmitButton();
        boolean rendVersionDateWorked = dateSearchFilterPage().validateVersionDateFilterTitle("*** BEF(" + dateBefore + ")");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        boolean noRenditionsAppearWithVersionDateFilter = sourceNavigateFooterToolsPage().getResults() == 0;

        //Click rendition version date calendar button again and clear filter
        sourceNavigateFiltersAndSortsPage().clickRenditionVersionDateAndTimeCalendarButton();
        boolean rendVersionDateHasSameValue = dateSearchFilterPage().checkFilterDateBeforeInputIsExpected(dateBefore);
        dateSearchFilterPage().clickClearFilterRadioButton();
        dateSearchFilterPage().clickSubmitButton();
        boolean rendVersionCleared = dateSearchFilterPage().validateVersionDateFilterTitle("");
        sourceNavigateFooterToolsPage().refreshTheGrid();
        boolean oneRenditionAppearedWithVersionDateCleared = sourceNavigateFooterToolsPage().getResults() == 1;

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(loadDateWorked, "Submitting a rendition load date did filter the results."),
            () -> Assertions.assertTrue(rendLoadDateHasSameValue, "The date previously entered for rendition load date is present."),
            () -> Assertions.assertTrue(rendLoadDateCleared, "The rendition load date has been cleared from the filter field."),
            () -> Assertions.assertTrue(rendVersionDateWorked, "Submitting a rendition version date did filter the results."),
            () -> Assertions.assertTrue(rendVersionDateHasSameValue, "The date previously entered for rendition version date is present."),
            () -> Assertions.assertTrue(rendVersionCleared, "The rendition version date has been cleared from the filter field."), () -> Assertions.assertTrue(noRenditionsAppearWithLoadDateFilter, "No renditions appeared with the load date filter"),
            () -> Assertions.assertTrue(noRenditionsAppearWithVersionDateFilter, "No renditions appeared with the version date filter"),
            () -> Assertions.assertTrue(oneRenditionAppearedWithVersionDateCleared, "One rendition appeared after the version date filter was cleared"),
            () -> Assertions.assertTrue(oneRenditionAppearedWithLoadDateCleared, "One rendition appeared after the load date filter was cleared")
        );
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}
