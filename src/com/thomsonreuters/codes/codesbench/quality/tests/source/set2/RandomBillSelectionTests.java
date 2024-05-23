package com.thomsonreuters.codes.codesbench.quality.tests.source.set2;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomBillSelectionTests extends TestService
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
    public void randomBillSelectionTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourcePage().waitForGridRefresh();
        clamshellPage().openSideBarReport();
        renditionTabReportClamshellPage().clickRandomBillReports(true, false);

        randomBillReportGridFooterPage().enterTheInnerFrame();
        int initialSize = randomBillReportContentSelectPage().getNonSelectedCount();

        randomBillReportContentSelectPage().selectFirstNonSelectedSet();
        randomBillReportContentSelectPage().clickSelectOneOptionArrow();
        int oneOptionIsMoved = randomBillReportContentSelectPage().getSelectedCount();

        randomBillReportContentSelectPage().clickSelectAllOptionArrow();
        randomBillReportContentSelectPage().waitForGridRefresh();
        int allOptionsSelected = randomBillReportContentSelectPage().getSelectedCount();

        randomBillReportContentSelectPage().selectFirstSelectedSet();
        randomBillReportContentSelectPage().clickDeselectOneArrow();
        int oneOptionDeselected = randomBillReportContentSelectPage().getNonSelectedCount();

        randomBillReportContentSelectPage().clickDeselectAllArrow();
        randomBillReportContentSelectPage().waitForGridRefresh();
        int allOptionsDeselected = randomBillReportContentSelectPage().getNonSelectedCount();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(oneOptionIsMoved == 1, "Only one option is found in the selected list when one option is moved over."),
            () -> Assertions.assertTrue(allOptionsSelected == initialSize, "All options from the unselected list are moved to the selected list when Select all is pressed"),
            () -> Assertions.assertTrue(oneOptionDeselected == 1, "One option was moved to the deselected list after the deselect one button was pressed"),
            () -> Assertions.assertTrue(allOptionsDeselected == initialSize, "All options were deselected when the deslect all button was pressed.")
        );
    }
}
