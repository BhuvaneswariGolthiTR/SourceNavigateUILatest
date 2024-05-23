package com.thomsonreuters.codes.codesbench.quality.tests.smoke.sourcenavigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.FooterToolsElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class SourceNavigateRefreshButtonFunctionalityLegalTests extends TestService
{
    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Refresh button exists in the lineage tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void lineageRefreshButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to lineage tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToLineageTab();

        //Verify refresh button appears
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        boolean refreshButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.REFRESH_BUTTON_BOTTOM);
        Assertions.assertTrue(refreshButtonExists, "Refresh button exists");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Refresh button exists in the section tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sectionRefreshButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to section tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Verify refresh button appears
        DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
        boolean refreshButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.REFRESH_BUTTON_BOTTOM);
        Assertions.assertTrue(refreshButtonExists, "Refresh button exists");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Refresh button exists in the delta tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltaRefreshButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();

        //Verify refresh button appears
        boolean refreshButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.REFRESH_BUTTON_BOTTOM);
        Assertions.assertTrue(refreshButtonExists, "Refresh button exists");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the progress bar appears after clicking the Refresh button. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void renditionRefreshButtonFunctionalityLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Click refresh button
        sourceNavigateFooterToolsPage().click(FooterToolsElements.REFRESH_BUTTON_BOTTOM);
        sourceNavigateGridPage().waitForElement(SourceNavigatePageElements.PROGRESS_BAR);

        //Verify progress bar appears
        boolean checkForRefresh = sourceNavigateGridPage().doesElementExist(SourceNavigatePageElements.PROGRESS_BAR);
        sourceNavigateGridPage().waitForGridRefresh();

        //Verify refresh button appears
        boolean refreshButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.REFRESH_BUTTON_BOTTOM);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(refreshButtonExists, "Refresh button still exists after refresh"),
            () -> Assertions.assertTrue(checkForRefresh, "Progress bar exists during refresh")
        );
    }
}
