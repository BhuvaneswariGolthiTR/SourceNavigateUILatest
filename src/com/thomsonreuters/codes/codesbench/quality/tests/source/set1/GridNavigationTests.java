package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GridNavigationTests extends TestService
{
    /**
     * STORY: N/A <br>
     * SUMMARY:Verifies that the Lineage, Section, and Delta tabs are not enabled when a user goes to source navigate without selecting a rendition  <br>
     * USER: LEGAL <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void gridNavigationTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        boolean lineageTabDisabled = sourceNavigateTabsPage().lineageTabDisabled();
        boolean sectionTabDisabled = sourceNavigateTabsPage().sectionTabDisabled();
        boolean deltaTabDisabled = sourceNavigateTabsPage().deltaTabDisabled();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(lineageTabDisabled, "Lineage tab was disabled."),
            () -> Assertions.assertTrue(sectionTabDisabled, "Section tab was disabled."),
            () -> Assertions.assertTrue(deltaTabDisabled, "Delta tab was disabled.")
        );
    }
}
