package com.thomsonreuters.codes.codesbench.quality.tests.source.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourcePageElements;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.sql.Connection;

public class ViewManagementTests extends TestService
{
    String view;

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - create a view using the view management window and filter by effective date and then delete the view <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void checkViewWithDateFilteringCreatedFromViewManagementLegalTest()
    {
        view = "TEST_VIEW";
        String dateToFilter = "01/01/2012";

        //Navigate to source
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Open view management and create a view
        boolean viewManagementIsOpened = sourceNavigateFooterToolsPage().clickTheViewManager();
        Assertions.assertTrue(viewManagementIsOpened, "The view management window did open.");

        viewManagementPage().openViewCreationWizard();
        viewWizardPage().addDateFilterWithGivenValue("Effective Date", dateToFilter);
        viewWizardPage().clickNext();
        AutoITUtils.verifyAlertTextAndAccept(true, "The year column wasn't selected, the current and previous years will be set as a default to this view.");
        viewWizardPage().setYearFilter("2011");
        viewWizardPage().addSortToView("Year", "Descending");
        viewWizardPage().clickNext();
        viewWizardPage().clickAddColumnsButton();
        viewWizardPage().clickNext();
        viewWizardPage().setViewname(view);
        viewWizardPage().setViewVisibilityToPublic();
        viewWizardPage().saveView();
        viewManagementPage().selectView(view);

        //Check the view in grid
        sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        boolean isCurrentViewExpectedView = sourceNavigateFooterToolsPage().compareExpectedView(view);
        Assertions.assertTrue(isCurrentViewExpectedView, "The view does not match expected view.");

        //Get the list of records with the given effective date
        boolean effectiveDateInGrid = sourceNavigateGridPage().checkIfAllValuesInEffectiveDateColumnAreEqualTo(dateToFilter);

        //Delete the view
        sourceNavigateFooterToolsPage().clickTheViewManager();
        viewManagementPage().deleteViews(view);

        //Close view management window
        viewManagementPage().closeViewManagementWindow();

        sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        sourcePage().waitForGridRefresh();
        boolean isCurrentViewNone = sourceNavigateFooterToolsPage().compareExpectedView("(none)");

        Assertions.assertAll
            (
                () ->Assertions.assertTrue(effectiveDateInGrid, "Effective Date is in grid"),
                () ->Assertions.assertTrue(isCurrentViewNone, "View of (none) does appear in grid")
            );
    }

    /**
     * STORY/BUG - HALCYONST-12727 <br>
     * SUMMARY - This test creates a view without selecting a year filter and verifies that a year filter of the current year, previous year, and null are inserted into the view <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addViewNoYearFilterLegalTest()
    {
        view = "TEST_VIEW_NO_YEAR_FILTER" + DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Navigate to source
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        //Open view management and create a view
        boolean viewManagementIsOpened = sourceNavigateFooterToolsPage().clickTheViewManager();
        Assertions.assertTrue(viewManagementIsOpened, "The view management window didn't open.");

        boolean viewWizardIsOpened = viewManagementPage().openViewCreationWizard();
        Assertions.assertTrue(viewWizardIsOpened, "The view wizard window didn't open");
        viewWizardPage().addFilterToView("Content Set", "Iowa (Development)");
        viewWizardPage().clickNext();
        AutoITUtils.verifyAlertTextAndAccept(true, "The year column wasn't selected, the current and previous years will be set as a default to this view.");

        boolean didTheSortsPaneAppear = viewWizardPage().verifyViewWizardPane("Sorts");
        Assertions.assertTrue(didTheSortsPaneAppear, "The Sorts Pane did not appear");
        viewWizardPage().clickNext();

        boolean didTheColumnsPaneAppear = viewWizardPage().verifyViewWizardPane("Columns");
        Assertions.assertTrue(didTheColumnsPaneAppear, "The Columns Pane did not appear");
        viewWizardPage().addColumnsToView("Content Set", "Year");
        viewWizardPage().clickNext();

        boolean didTheSavePaneAppear = viewWizardPage().verifyViewWizardPane("Save");
        Assertions.assertTrue(didTheSavePaneAppear, "The Save Pane did not appear");
        viewWizardPage().setViewname(view);
        viewWizardPage().setViewVisibilityToPublic();
        boolean didViewWizardPageDisappear = viewWizardPage().saveView();
        Assertions.assertTrue(didViewWizardPageDisappear, "The view wizard page did not disappear");

        boolean doesViewExist = viewManagementPage().doesViewExist(view);
        Assertions.assertTrue(doesViewExist, "The created view does not exist");
        viewManagementPage().selectView(view);

        //Check the view in grid
        boolean didViewManagementPageDisappear = sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        Assertions.assertTrue(didViewManagementPageDisappear, "The View Management Page Disappear");

        sourcePage().waitForGridRefresh();
        boolean isCurrentViewExpectedView = sourceNavigateFooterToolsPage().compareExpectedView(view);
        Assertions.assertTrue(isCurrentViewExpectedView, "The view does not match expected view.");

        //Sorting ascending and descending to verify that the range of data in the grid fits the criteria of current or previous year
        sourceNavigateGridPage().sortSourceNavigateGridByYearAscending();
        for (String date : sourceNavigateGridPage().getAllYears())
        {
            Assertions.assertTrue((date.equals(DateAndTimeUtils.getCurrentYearyyyy())) ||
                            (date.equals(String.valueOf(Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy()) - 1))),
                    "A date in the grid was outside of the filter values");
        }
        sourceNavigateGridPage().sortSourceNavigateGridByYearDescending();
        for (String date : sourceNavigateGridPage().getAllYears())
        {
            Assertions.assertTrue((date.equals(DateAndTimeUtils.getCurrentYearyyyy())) ||
                            (date.equals(String.valueOf(Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy()) - 1))),
                    "A date in the grid was outside of the filter values");
        }
    }

    /**
     * STORY/BUG - HALCYONST-12727 <br>
     * SUMMARY - This test creates a view with the year filter and verifies that no alert appears <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addViewWithYearFilterLegalTest()
    {
        view = "TEST_VIEW_YEAR_FILTER" + DateAndTimeUtils.getCurrentDateMMddyyyy();

        String[] years = {"2023", "2022"};

        //Navigate to source
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        //Open view management and create a view
        boolean viewManagementIsOpened = sourceNavigateFooterToolsPage().clickTheViewManager();
        Assertions.assertTrue(viewManagementIsOpened, "The view management window didn't open.");

        boolean viewWizardIsOpened = viewManagementPage().openViewCreationWizard();
        Assertions.assertTrue(viewWizardIsOpened, "The view wizard window didn't open");
        viewWizardPage().addFilterToView("Year", years);
        viewWizardPage().clickNext();

        boolean didTheSortsPaneAppear = viewWizardPage().verifyViewWizardPane("Sorts");
        Assertions.assertTrue(didTheSortsPaneAppear, "The Sorts Pane did not appear");
        viewWizardPage().clickNext();

        boolean didTheColumnsPaneAppear = viewWizardPage().verifyViewWizardPane("Columns");
        Assertions.assertTrue(didTheColumnsPaneAppear, "The Columns Pane did not appear");
        viewWizardPage().addColumnsToView("Year");
        viewWizardPage().clickNext();

        boolean didTheSavePaneAppear = viewWizardPage().verifyViewWizardPane("Save");
        Assertions.assertTrue(didTheSavePaneAppear, "The Save Pane did not appear");
        viewWizardPage().setViewname(view);
        viewWizardPage().setViewVisibilityToPublic();
        boolean didViewWizardPageDisappear = viewWizardPage().saveView();
        Assertions.assertTrue(didViewWizardPageDisappear, "The view wizard page did not disappear");

        boolean doesViewExist = viewManagementPage().doesViewExist(view);
        Assertions.assertTrue(doesViewExist, "The created view does not exist");
        viewManagementPage().selectView(view);

        //Check the view in grid
        boolean didViewManagementPageDisappear = sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        Assertions.assertTrue(didViewManagementPageDisappear, "The View Management Page Disappear");

        sourcePage().waitForGridRefresh();
        boolean isCurrentViewExpectedView = sourceNavigateFooterToolsPage().compareExpectedView(view);
        Assertions.assertTrue(isCurrentViewExpectedView, "The view does not match expected view.");

        //Verifying only the year column is present
        boolean areColumnsPresent = sourceNavigateGridPage().isColumnPresent("year");
        Assertions.assertTrue(areColumnsPresent, "The expected columns are not present when they should be");

        //Sorting ascending and descending to verify that the range of data in the grid fits the criteria of current or previous year
        sourceNavigateGridPage().sortSourceNavigateGridByYearAscending();
        for (String date : sourceNavigateGridPage().getAllYears())
        {
            Assertions.assertTrue((date.equals(DateAndTimeUtils.getCurrentYearyyyy())) ||
                            (date.equals(String.valueOf(Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy()) - 1))),
                    "A date in the grid was outside of the filter values");
        }
        sourceNavigateGridPage().sortSourceNavigateGridByYearDescending();
        for (String date : sourceNavigateGridPage().getAllYears())
        {
            Assertions.assertTrue((date.equals(DateAndTimeUtils.getCurrentYearyyyy())) ||
                            (date.equals(String.valueOf(Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy()) - 1))),
                    "A date in the grid was outside of the filter values");
        }
    }

    /**
     * STORY/BUG - HALCYONST-12727 <br>
     * SUMMARY - This test creates a view without selecting ANY filter and verifies that a year filter of the current year, previous year, and null are inserted into the view <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addViewWithNoFiltersSetLegalTest()
    {
        view = "TEST_VIEW_NO_FILTER" + DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Navigate to source
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        //Open view management and create a view
        boolean viewManagementIsOpened = sourceNavigateFooterToolsPage().clickTheViewManager();
        Assertions.assertTrue(viewManagementIsOpened, "The view management window didn't open.");

        boolean viewWizardIsOpened = viewManagementPage().openViewCreationWizard();
        Assertions.assertTrue(viewWizardIsOpened, "The view wizard window didn't open");
        viewWizardPage().clickNext();
        AutoITUtils.verifyAlertTextAndAccept(true, "The year column wasn't selected, the current and previous years will be set as a default to this view.");

        boolean didTheSortsPaneAppear = viewWizardPage().verifyViewWizardPane("Sorts");
        Assertions.assertTrue(didTheSortsPaneAppear, "The Sorts Pane did not appear");
        viewWizardPage().clickNext();

        boolean didTheColumnsPaneAppear = viewWizardPage().verifyViewWizardPane("Columns");
        Assertions.assertTrue(didTheColumnsPaneAppear, "The Columns Pane did not appear");
        viewWizardPage().addColumnsToView("Year");
        viewWizardPage().clickNext();

        boolean didTheSavePaneAppear = viewWizardPage().verifyViewWizardPane("Save");
        Assertions.assertTrue(didTheSavePaneAppear, "The Save Pane did not appear");
        viewWizardPage().setViewname(view);
        viewWizardPage().setViewVisibilityToPublic();
        boolean didViewWizardPageDisappear = viewWizardPage().saveView();
        Assertions.assertTrue(didViewWizardPageDisappear, "The view wizard page did not disappear");

        boolean doesViewExist = viewManagementPage().doesViewExist(view);
        Assertions.assertTrue(doesViewExist, "The created view does not exist");
        viewManagementPage().selectView(view);

        //Check the view in grid
        sourcePage().waitForGridRefresh();
        boolean didViewManagementPageDisappear = sourcePage().switchToWindow(SourcePageElements.SOURCE_PAGE_TITLE);
        Assertions.assertTrue(didViewManagementPageDisappear, "The View Management Page Disappear");

        sourcePage().waitForGridRefresh();
        boolean isCurrentViewExpectedView = sourceNavigateFooterToolsPage().compareExpectedView(view);
        Assertions.assertTrue(isCurrentViewExpectedView, "The view does not match expected view.");

        //Verifying only the year column is present
        boolean areColumnsPresent = sourceNavigateGridPage().isColumnPresent("year");
        Assertions.assertTrue(areColumnsPresent, "The expected columns are not present when they should be");

        //Sorting ascending and descending to verify that the range of data in the grid fits the criteria of current or previous year
        sourceNavigateGridPage().sortSourceNavigateGridByYearAscending();
        for (String date : sourceNavigateGridPage().getAllYears())
        {
            Assertions.assertTrue((date.equals(DateAndTimeUtils.getCurrentYearyyyy())) ||
                            (date.equals(String.valueOf(Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy()) - 1))),
                    "A date in the grid was outside of the filter values");
        }
        sourceNavigateGridPage().sortSourceNavigateGridByYearDescending();
        for (String date : sourceNavigateGridPage().getAllYears())
        {
            Assertions.assertTrue((date.equals(DateAndTimeUtils.getCurrentYearyyyy())) ||
                            (date.equals(String.valueOf(Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy()) - 1))),
                    "A date in the grid was outside of the filter values");
        }
    }

    /**
     * STORY/BUG - HALCYONST-12727 <br>
     * SUMMARY - This test creates a view without selecting a year filter and verifies that for all other non-Rendition tabs, the view is NOT auto-populated with a year <br>
     * USER - Legal <br>
     */
    @ParameterizedTest(name = "Test: {displayName}; Index: {index}; Arguments: {arguments}")
    @CsvSource
    (
        {
            "Lineage, Lineage Navigate",
            "Section, Pending Section Navigate",
            "Section Group, Pending Section Navigate",
            "Delta, Delta Navigate",
            "Delta Group, Delta Group Navigate"
        }
    )
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void addViewWithNoYearFilterAlternateTabsLegalTest(String sourceNavigateTab, String sourceNavigateTabPageTitle)
    {
        view = "TEST_VIEW_NO_YEAR_FILTER_ALTERNATE_TABS" + DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Navigate to source
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSourceNavigateLevelTab(sourceNavigateTab);

        //Open view management and create a view
        boolean viewManagementIsOpened = sourceNavigateFooterToolsPage().clickTheViewManager();
        Assertions.assertTrue(viewManagementIsOpened, "The view management window didn't open.");

        boolean viewWizardIsOpened = viewManagementPage().openViewCreationWizard();
        Assertions.assertTrue(viewWizardIsOpened, "The view wizard window didn't open");
        viewWizardPage().addFilterToView("Content Set", "Iowa (Development)");
        viewWizardPage().clickNext();

        boolean didTheSortsPaneAppear = viewWizardPage().verifyViewWizardPane("Sorts");
        Assertions.assertTrue(didTheSortsPaneAppear, "The Sorts Pane did not appear");
        viewWizardPage().clickNext();

        boolean didTheColumnsPaneAppear = viewWizardPage().verifyViewWizardPane("Columns");
        Assertions.assertTrue(didTheColumnsPaneAppear, "The Columns Pane did not appear");
        viewWizardPage().addColumnsToView("Content Set");
        viewWizardPage().clickNext();

        boolean didTheSavePaneAppear = viewWizardPage().verifyViewWizardPane("Save");
        Assertions.assertTrue(didTheSavePaneAppear, "The Save Pane did not appear");
        viewWizardPage().setViewname(view);
        viewWizardPage().setViewVisibilityToPublic();
        boolean didViewWizardPageDisappear = viewWizardPage().saveView();
        Assertions.assertTrue(didViewWizardPageDisappear, "The view wizard page did not disappear");

        boolean doesViewExist = viewManagementPage().doesViewExist(view);
        Assertions.assertTrue(doesViewExist, "The created view does not exist");
        viewManagementPage().selectView(view);

        //Check the view in grid
        boolean didViewManagementPageDisappear = sourcePage().switchToWindow(sourceNavigateTabPageTitle);
        Assertions.assertTrue(didViewManagementPageDisappear, "The View Management Page Disappear");

        sourcePage().waitForGridRefresh();
        boolean isCurrentViewExpectedView = sourceNavigateFooterToolsPage().compareExpectedView(view);
        Assertions.assertTrue(isCurrentViewExpectedView, "The view does not match expected view.");

        //Verifying only the year column is present
        //TODO: add check for un-existence of other columns?
        boolean areColumnsPresent = sourceNavigateGridPage().isColumnPresent("contentSet");
        Assertions.assertTrue(areColumnsPresent, "The expected columns are not present when they should be");
    }

    @AfterEach
    public void deleteView()
    {
        Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
        SourceDatabaseUtils.deleteView(connection, view);
        BaseDatabaseUtils.disconnect(connection);
    }
}