package com.thomsonreuters.codes.codesbench.quality.tests.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.SourceNavigateGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;

public class RepopulateSourceGridRiskTests extends TestService
{
    /**
     * STORY: N/A <br>
     * SUMMARY: Changes content set settings and checks source grid repopulates with correct options available <br>
     * USER: Risk <br>
     */
    @Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void repopulateSourceGridRiskTest()
    {
        String contentSet = "South Dakota (Development)";

        homePage().goToHomePage();
        loginPage().logIn();

        homeMenu().goToUserPreferencesContentSets();
        userContentSetPreferencesPage().addSourceContentSetByName(contentSet);
        userContentSetPreferencesPage().saveContentSettings();

        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        boolean contentAppearsInitially = sourceNavigateGridPage().verifyContentSetExists(contentSet);

        homeMenu().goToUserPreferencesContentSets();
        userContentSetPreferencesPage().removeSourceContentSetByName(contentSet);
        userContentSetPreferencesPage().saveContentSettings();

        sourceMenu().goToSourceC2012Navigate();
        sourceNavigateFooterToolsPage().repopulateView();

        boolean contentAppearsAfterRemoval = sourceNavigateGridPage().doesElementExist(SourceNavigateGridPageElements.NO_RECORDS_FOUND);

        homeMenu().goToUserPreferencesContentSets();
        userContentSetPreferencesPage().addSourceContentSetByName(contentSet);
        userContentSetPreferencesPage().saveContentSettings();

        sourceMenu().goToSourceC2012Navigate();
        sourceNavigateFooterToolsPage().repopulateView();

        boolean contentAppearsFinal = sourceNavigateGridPage().verifyContentSetExists(contentSet);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(contentAppearsInitially, "Content appears initially"),
            () -> Assertions.assertTrue(contentAppearsAfterRemoval, "Content doesn't appear after content set is removed"),
            () -> Assertions.assertTrue(contentAppearsFinal, "Content appears after the content set is re-added")
        );
    }
}
