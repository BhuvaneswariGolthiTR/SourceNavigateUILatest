package com.thomsonreuters.codes.codesbench.quality.tests.smoke.sourcenavigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.FooterToolsElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class SourceNavigatePublicViewMenuFunctionalityLegalTests extends TestService
{
    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Public View button exists in the lineage tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void lineagePublicViewButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to lineage tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToLineageTab();

        //Verify public button appears
        boolean publicViewButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.PUBLIC_BUTTON);
        Assertions.assertTrue(publicViewButtonExists, "Public view button exists");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Public View button exists in the section tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sectionPublicViewButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to section tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Verify public button appears
        boolean publicViewButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.PUBLIC_BUTTON);
        Assertions.assertTrue(publicViewButtonExists, "Public view button exists");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Public View button exists in the delta tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltaPublicViewButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();

        //Verify public button appears
        boolean publicViewButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.PUBLIC_BUTTON);
        Assertions.assertTrue(publicViewButtonExists, "Public view button exists");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the view changes from one to another if you switch views in the rendition tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void renditionPublicViewMultipleChangeLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Verify public button appears
        boolean publicViewButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.PUBLIC_BUTTON);
        Assertions.assertTrue(publicViewButtonExists, "Public view button exists");

        //Set 'Regression Test Iowa 1' public view and verify view changes
        sourceNavigateFooterToolsPage().selectPublicView("Regression Test Iowa 1");
        boolean firstPublicView = sourceNavigateFooterToolsPage().compareActualViewToExpectedView("Regression Test Iowa 1");

        //Set 'Regression Test Iowa 2' public view and verify view changes
        sourceNavigateFooterToolsPage().selectPublicView("Regression Test Iowa 2");
        boolean secondPublicView = sourceNavigateFooterToolsPage().compareActualViewToExpectedView("Regression Test Iowa 2");

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(firstPublicView, "Initial view updated correctly"),
            () -> Assertions.assertTrue(secondPublicView, "Second view updated correctly")
        );
    }
}
