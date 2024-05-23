package com.thomsonreuters.codes.codesbench.quality.tests.source.set2;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomBillReportTests extends TestService
{
    /**
     * STORY: NA <br>
     * SUMMARY: checks if a bill track with a bill text error syncs correctly.<br>
     * USER:  LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void randomBillReportTest()
    {
        String contentSet = "Alabama (Development)";
        String year = (Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy()) - 1) + "";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        clamshellPage().openSideBarReport();
        renditionTabReportClamshellPage().clickRandomBillReports(true, false);
        randomBillReportGridFooterPage().enterTheInnerFrame();

        randomBillReportContentSelectPage().setYear(year);
        randomBillReportContentSelectPage().selectContentSetFromNonSelectedList(contentSet);
        randomBillReportContentSelectPage().clickSelectOneOptionArrow();
        randomBillReportContentSelectPage().clickSearchButton();

        randomBillReportContentSelectPage().breakOutOfFrame();
        randomBillReportContentSelectPage().switchToInnerIFrameByIndex(1);

        int documents = randomBillReportGridFooterPage().getNumberOfResults();

        randomBillReportGridFooterPage().refreshTheGrid();
        randomBillReportGridFooterPage().clearGridSorts();
        randomBillReportGridFooterPage().clearGridFilters();
        randomBillReportGridFooterPage().clearGridSelections();

        int documentsAfterFooterButtons = randomBillReportGridFooterPage().getNumberOfResults();

        randomBillReportGridFooterPage().breakOutOfFrame();
        randomBillReportContentSelectPage().enterTheInnerFrame();
        randomBillReportContentSelectPage().clickSearchButton();
        randomBillReportContentSelectPage().breakOutOfFrame();
        randomBillReportContentSelectPage().switchToInnerIFrameByIndex(1);

        int documentAfterSearch = randomBillReportGridFooterPage().getNumberOfResults();

        Assertions.assertAll
        (
            () -> Assertions.assertEquals(documents, documentsAfterFooterButtons, "Document number shouldn't change after filters change"),
            () -> Assertions.assertNotEquals(documents, documentAfterSearch, "Document number did change after searching")
        );

    }
}
