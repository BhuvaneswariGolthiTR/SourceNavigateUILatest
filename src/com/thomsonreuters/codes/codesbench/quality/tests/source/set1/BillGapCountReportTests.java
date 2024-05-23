package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BillGapCountReportTests extends TestService
{
    /**
     * STORY:  na <br>
     * SUMMARY: Checks the bill gap report populates the grid <br>
     * USER: Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void billGapCountReportTest()
    {
        String contentSet = "Iowa (Development)";

        homePage().goToHomePage();
        loginPage().logIn();
        boolean sourceNavigateWindowAppeared = sourceMenu().goToSourceC2012Navigate();
        Assertions.assertTrue(sourceNavigateWindowAppeared);

        clamshellPage().openSideBarReport();
        renditionTabReportClamshellPage().clickBillGapCountReport(true, false);
        boolean recordsListEmptyAtStart = billGapCountGridPage().recordListIsEmpty();
        billGapCountContentSetPage().setDropdownNotSelected(contentSet);
        billGapCountContentSetPage().pressMoveOneFromNonSelectedToSelected();

        billGapCountGridPage().refreshTheGrid();
        boolean recordsListEmptyAtEnd = billGapCountGridPage().recordListIsEmpty();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(recordsListEmptyAtStart,"Records empty at the start"),
            () -> Assertions.assertFalse(recordsListEmptyAtEnd, "Records empty at the end")
        );
    }
}
