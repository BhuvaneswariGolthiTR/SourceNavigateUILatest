package com.thomsonreuters.codes.codesbench.quality.tests.smoke.sourcenavigate;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.FooterToolsElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class SourceNavigateJurisdictionViewMenuFunctionalityLegalTest extends TestService 
{
    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Jurisdiction View button exists in the lineage tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void lineageJurisdictionViewButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to lineage tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToLineageTab();

        //Verify jurisdiction button appears
        boolean jurisdictionViewButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.JURISDICTION_BUTTON);
        Assertions.assertTrue(jurisdictionViewButtonExists, "Jurisdiction view button exists");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Jurisdiction View button exists in the section tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sectionJurisdictionViewButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to section tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Verify jurisdiction button appears
        boolean jurisdictionViewButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.JURISDICTION_BUTTON);
        Assertions.assertTrue(jurisdictionViewButtonExists, "Jurisdiction view button exists");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies the Jurisdiction View button exists in the delta tab. <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deltaJurisdictionViewButtonExistsLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToDeltaTab();

        //Verify jurisdiction button appears
        boolean jurisdictionViewButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.JURISDICTION_BUTTON);
        Assertions.assertTrue(jurisdictionViewButtonExists, "Jurisdiction view button exists");
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
    public void renditionMultipleChangeLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Verify jurisdiction button appears
        boolean jurisdictionViewButtonExists = sourceNavigateFooterToolsPage().doesElementExist(FooterToolsElements.JURISDICTION_BUTTON);
        Assertions.assertTrue(jurisdictionViewButtonExists, "Jurisdiction view button exists");

        //Set 'Regression Test Iowa 1' jurisdiction view and verify view changes
        sourceNavigateFooterToolsPage().selectJurisdictionView("Regression Test Iowa 1");
        boolean firstJurisdictionView = sourceNavigateFooterToolsPage().compareActualViewToExpectedView("Regression Test Iowa 1");

        //Set 'Regression Test Iowa 2' jurisdiction view and verify view changes
        sourceNavigateFooterToolsPage().selectJurisdictionView("Regression Test Iowa 2");
        boolean secondJurisdictionView = sourceNavigateFooterToolsPage().compareActualViewToExpectedView("Regression Test Iowa 2");

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(firstJurisdictionView, "Initial view updated correctly"),
            () -> Assertions.assertTrue(secondJurisdictionView, "Second view updated correctly")
        );
    }
}
