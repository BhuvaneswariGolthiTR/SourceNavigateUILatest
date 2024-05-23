package com.thomsonreuters.codes.codesbench.quality.tests.smoke.sourcenavigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.FiltersAndSortsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.FooterToolsElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SourceNavigateGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.table.TableTestingPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.queries.SourceDataMockingQueries;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;


public class SourceNavigateClearMenuFunctionalityLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG - JETS-12345 <br>
     * SUMMARY - Sets a content set filter in the renditions tab and then verifies it can be cleared. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void renditionTabClearFiltersLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Set filter for Iowa (Development)
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Verify filters function
        verifyClearFilters();
    }

    /**
     * STORY/BUG - JETS-12345 <br>
     * SUMMARY - After filtering for certain renditions, the grid is sorted by ascending for the notes column.
     *           Then it is verified that the sort can be cleared. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void renditionTabClearSortLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Set filter for Iowa (Development)
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Sort grid and clear sort
        verifyClearSort();
    }

    /**
     * STORY/BUG - JETS-12345 <br>
     * SUMMARY - After filtering for certain renditions, all items in the grid are selected.
     *           Then it is verified that the selection can be cleared. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void renditionTabClearSelectionLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Set filter for Iowa (Development)
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Select renditions and unselect them
        verifyClearSelection();
    }

    /**
     * STORY/BUG - JETS-12345 <br>
     * SUMMARY - Sets certain filters in the lineage tab and then verifies the filters can be cleared. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void lineageTabClearFiltersLegalTest()
    {
        //TODO create 2 renditions, set one to be deleted

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Validation: None
        //MultipleDuplicate: None
        //Deleted: Not Deleted
        //ContentSet: Iowa(Development)
        //Year: 2015
        //Status: BILL
        filterForLineage();

        //Go to Lineage tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToLineageTab();

        //Filter for Deleted lineages
        DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Deleted");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Verify filters function
        verifyClearFilters();
    }


    /**
     * STORY/BUG - JETS-12345 <br>
     * SUMMARY - After filtering for certain lineages, the grid is sorted by ascending for the notes column.
     *           Then it is verified that the sort can be cleared. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void lineageTabClearSortLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Go to Lineage tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToLineageTab();

        //Sort grid and clear sort
        verifyClearSort();
    }

    /**
     * STORY/BUG - JETS-12345 <br>
     * SUMMARY - After filtering for certain lineages, all items in the grid are selected.
     *           Then it is verified that the selection can be cleared. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void lineageTabClearSelectionLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Go to Lineage tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToLineageTab();

        //Select renditions and unselect them
        verifyClearSelection();
    }

    /**
     * STORY/BUG - JETS-12345 <br>
     * SUMMARY - Sets certain filters in the section tab and then verifies the filters can be cleared. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sectionTabClearFiltersLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Go to Section tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Filter for section number 10
        sourceNavigateFiltersAndSortsPage().setFilterSectionNumber("3");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Verify filters function
        verifyClearFilters();
    }

    /**
     * STORY/BUG - JETS-12345 <br>
     * SUMMARY - After filtering for certain sections, the grid is sorted by ascending for the notes column.
     *           Then it is verified that the sort can be cleared. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sectionTabClearSortLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Go to Section tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Sort grid and clear sort
        verifyClearSort();
    }

    /**
     * STORY/BUG - JETS-12345 <br>
     * SUMMARY - After filtering for certain sections, all items in the grid are selected.
     *           Then it is verified that the selection can be cleared. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sectionTabClearSelectionLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Go to Section tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Select renditions and unselect them
        verifyClearSelection();
    }

    /**
     * STORY/BUG - JETS-12345 <br>
     * SUMMARY - After filtering for certain deltas, the grid is sorted by ascending for the notes column.
     *           Then it is verified that the sort can be cleared. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltaTabClearSortLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Go to Delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();

        //Sort grid and clear sort
        verifyClearSort();
    }

    /**
     * STORY/BUG - JETS-12345 <br>
     * SUMMARY - After filtering for certain deltas, all items in the grid are selected.
     *           Then it is verified that the selection can be cleared. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltaTabClearSelectionLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();
        //Go to Delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();

        //Select renditions and unselect them
        verifyClearSelection();
    }


    /* Helpers */

    private void filterForLineage()
    {
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2015");
        sourceNavigateFiltersAndSortsPage().setFilterContentType("BILL");
        sourceNavigateFooterToolsPage().refreshTheGrid();
    }

    private void verifyClearFilters()
    {
        //Verify clear button exists
        boolean clearButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.CLEAR_BUTTON);
        Assertions.assertTrue(clearButtonExists, "Clear dropdown button couldn't be found");

        //Count the total results before filters are cleared, then clear filters
        int countOfDocumentsBeforeClear = sourceNavigateFooterToolsPage().getResults();
        sourceNavigateFooterToolsPage().clearFilters();

        //Count the number of total results after filters were cleared
        int countOfDocumentsAfterClear = sourceNavigateFooterToolsPage().getResults();

        //Verify filters were cleared and that the amount of results is larger then when filters were on
        boolean contentSetWasCleared = FiltersAndSortsPageElements.contentSetFilter.getText().equals("");
        boolean clearFiltersSuccessful = countOfDocumentsAfterClear > countOfDocumentsBeforeClear;

        Assertions.assertAll
        (
            () ->  Assertions.assertTrue(contentSetWasCleared, "Content set wasn't cleared"),
            () ->  Assertions.assertTrue(clearFiltersSuccessful, "Document count after clear is not more Document count before clear")
        );
    }

    private void verifyClearSort()
    {
        //Verify clear button exists
        boolean clearButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.CLEAR_BUTTON);
        Assertions.assertTrue(clearButtonExists, "Clear dropdown button couldn't be found");

        //Sort Notes column by ascending order and verify they are sorted
        tableTestingPage().sortBy(TableTestingPage.RenditionColumns.NOTES, TableTestingPage.SortOrder.ASC);
        boolean wasSorted = sourceNavigateFiltersAndSortsPage().doesElementExist(FiltersAndSortsPageElements.NOTES_WITH_SORT_ARROW);

        //Clear the sort on NOtes column and verify Notes is no longer sorted
        sourceNavigateFooterToolsPage().clearSort();
        boolean sortCleared = !sourceNavigateFiltersAndSortsPage().doesElementExist(FiltersAndSortsPageElements.NOTES_WITH_SORT_ARROW);

        Assertions.assertAll
        (
            () ->  Assertions.assertTrue(wasSorted, "Sort wasn't applied"),
            () ->  Assertions.assertTrue(sortCleared, "Sort was not cleared")
        );
    }

    private void verifyClearSelection()
    {
        //Verify clear button exists
        boolean clearButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.CLEAR_BUTTON);
        Assertions.assertTrue(clearButtonExists, "Clear dropdown button couldn't be found");

        //Count the number of renditions in the grid
        int documentCount = sourceNavigateGridPage().getDocumentCount();

        //Click all renditions and verify all renditions in the grid were selected
        sourceNavigateFooterToolsPage().selectAllOnPage();
        int selectedRenditions = sourceNavigateGridPage().getElements(SourceNavigateGridPageElements.ITEM_MARKED_AS_SELECTED).size();
        boolean allRenditionsSelected = selectedRenditions == documentCount;

        //Clear selection and verify no more renditions are selected
        sourceNavigateFooterToolsPage().clearSelection();
        boolean noRenditionsSelected = !sourceNavigateGridPage().doesElementExist(SourceNavigateGridPageElements.ITEM_MARKED_AS_SELECTED);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(allRenditionsSelected, "Renditions weren't selected"),
            () -> Assertions.assertTrue(noRenditionsSelected, "Selected renditions weren't cleared")
        );
    }

    @BeforeEach
    public void mockData()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
        renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();

        connection = CommonDataMocking.connectToDatabase(environmentTag);
    }

    @AfterEach
    public void deleteMockedData()
    {
        if(datapodObject != null)
        {
            sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
            datapodObject.delete();
        }
        disconnect(connection);
    }
}
