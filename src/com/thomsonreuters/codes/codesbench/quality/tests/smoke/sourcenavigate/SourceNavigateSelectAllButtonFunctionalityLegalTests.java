package com.thomsonreuters.codes.codesbench.quality.tests.smoke.sourcenavigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.FooterToolsElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SourceNavigateGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class SourceNavigateSelectAllButtonFunctionalityLegalTests extends TestService
{
    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Select All button exists in the lineage tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void lineageSelectAllButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to lineage tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToLineageTab();

        //Verify select all button appears
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        boolean selectAllButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.SELECT_BUTTON);
        Assertions.assertTrue(selectAllButtonExists, "Select all button exists");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Select All button exists in the section tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sectionSelectAllButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to section tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Verify select all button appears
        boolean selectAllButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.SELECT_BUTTON);
        Assertions.assertTrue(selectAllButtonExists, "Select all button exists");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Select All button exists in the delta tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltaSelectAllButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();

        //Verify select all button appears
        boolean selectAllButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.SELECT_BUTTON);
        Assertions.assertTrue(selectAllButtonExists, "Select all button exists");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Select All button selects all the items in the grid. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void renditionSelectAllButtonFunctionalityLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Count total renditions in grid
        int renditionCount = sourceNavigateGridPage().getNumberOfRenditions();

        //Count selected renditions in grid
        sourceNavigateFooterToolsPage().selectAllOnPage();
        int selectedRenditionsCount = sourceNavigateGridPage().getElements(SourceNavigateGridPageElements.ITEM_MARKED_AS_SELECTED).size();

        //Verify all renditions in grid are selected
        boolean allRenditionsWereSelected = selectedRenditionsCount == renditionCount;
        Assertions.assertTrue(allRenditionsWereSelected, "Number of selected items equals the number of items in the grid");
    }
}
