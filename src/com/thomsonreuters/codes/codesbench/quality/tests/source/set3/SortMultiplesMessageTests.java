package com.thomsonreuters.codes.codesbench.quality.tests.source.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SortMultiplesMessageTests extends TestService
{
    /**
     * STORY: NA <br>
     * SUMMARY: Checks the message you get when you view multiple or duplicate renditions.<br>
     * USER:  LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void multipleRenditionsMessageTest()
    {
        String multipleDuplicate = "Multiple";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();
        sourcePage().waitForGridRefresh();

        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate(multipleDuplicate);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewMultipleAndDuplicateRenditions();

        boolean messageAppeared = sourcePage().multipleMessageExists();

        TestSetupEdge.closeBrowser();
        TestSetupEdge.openBrowser();

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        boolean messageNoLongerAppears = !sourcePage().multipleMessageExists();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(messageAppeared, "Multiples and Duplicate Message appeared"),
            () -> Assertions.assertTrue(messageNoLongerAppears, "The message no longer appears when you reopen the page")
        );
    }
}
