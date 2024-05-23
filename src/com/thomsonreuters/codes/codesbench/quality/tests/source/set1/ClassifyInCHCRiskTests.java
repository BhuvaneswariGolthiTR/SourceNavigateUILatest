package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.source.navigate.RenditionContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;

public class ClassifyInCHCRiskTests extends TestService
{
    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Test to verify that a Risk user can filter for APVRS document and 'Classify in CHC' <br>
     * USER - Risk <br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void classifyInCHCAPVRSRiskTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet("USCA(Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APVRS");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        boolean classifyInCHCEnabled = !renditionContextMenu().isElementDisabled(RenditionContextMenuElements.classifyInCHC);

        boolean classifyInCHCSafeLogInAppeared = renditionContextMenu().classifyInCHC();

        Assertions.assertTrue(classifyInCHCEnabled, "Classify in CHC option enabled");
        Assertions.assertTrue(classifyInCHCSafeLogInAppeared, "Classify in CHC SAFE Log In appears");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Test to verify that a Risk user can filter for RS document and can not 'Classify in CHC' <br>
     * USER - Risk <br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void classifyInCHCNonAPVRSRiskTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet("USCA(Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("RS");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        boolean classifyInCHCEnabled = !renditionContextMenu().isElementDisabled(RenditionContextMenuElements.classifyInCHC);

        Assertions.assertTrue(classifyInCHCEnabled, "Classify in CHC option should be disabled");
    }

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Test to verify that there is not an option to 'Classify in CHC' for multiple APVRS documents <br>
     * USER - Risk <br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void classifyInCHCMultipleAPVRSRiskTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet("USCA(Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APVRS");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstXRenditions(3);
        sourceNavigateGridPage().onlyRightClickFirstItem();
        boolean classifyInCHCDisplayed = renditionContextMenu().isElementDisplayed(RenditionContextMenuElements.CLASSIFY_IN_CHC);
        //Classify in CHC should not be displayed when selecting multiple renditions
        Assertions.assertFalse(classifyInCHCDisplayed, "Classify in CHC option is displayed");
    }
}
