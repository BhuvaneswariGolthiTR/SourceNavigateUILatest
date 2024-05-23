package com.thomsonreuters.codes.codesbench.quality.tests.smoke.sourcenavigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.FooterToolsElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class SourceNavigatePrivateViewMenuFunctionalityLegalTests extends TestService
{
    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Private View button exists in the lineage tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void lineagePrivateViewButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to lineage tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToLineageTab();

        //Verify private button appears
        boolean privateViewButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.PRIVATE_BUTTON);
        Assertions.assertTrue(privateViewButtonExists, "Private view button exists");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Private View button exists in the section tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sectionPrivateViewButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to section tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Verify private button appears
        boolean privateViewButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.PRIVATE_BUTTON);
        Assertions.assertTrue(privateViewButtonExists, "Private view button exists");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Private View button exists in the delta tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltaPrivateViewButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();

        //Verify private button appears
        boolean privateViewButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.PRIVATE_BUTTON);
        Assertions.assertTrue(privateViewButtonExists, "Private view button exists");
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
    public void renditionPrivateViewMultipleChangeLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Verify private button appears
        boolean privateViewButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.PRIVATE_BUTTON);
        Assertions.assertTrue(privateViewButtonExists, "Private view button exists");

        //Set 'Regression Test Iowa 1' private view and verify view changes
        sourceNavigateFooterToolsPage().selectPrivateView("Regression Test Iowa 1");
        boolean firstPrivateView = sourceNavigateFooterToolsPage().compareActualViewToExpectedView("Regression Test Iowa 1");

        //Set 'Regression Test Iowa 2' private view and verify view changes
        sourceNavigateFooterToolsPage().selectPrivateView("Regression Test Iowa 2");
        boolean secondPrivateView = sourceNavigateFooterToolsPage().compareActualViewToExpectedView("Regression Test Iowa 2");

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(firstPrivateView, "Initial view updated correctly"),
            () -> Assertions.assertTrue(secondPrivateView, "Second view updated correctly")
        );
    }
}
