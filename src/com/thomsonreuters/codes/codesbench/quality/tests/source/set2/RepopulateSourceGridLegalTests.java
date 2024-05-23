package com.thomsonreuters.codes.codesbench.quality.tests.source.set2;

import com.thomsonreuters.codes.codesbench.quality.pageelements.viewmanagament.SourceNavigateReportsPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.table.TableTestingPage;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RepopulateSourceGridLegalTests extends TestService
{
    /**
     * STORY: NA <br>
     * SUMMARY: checks the source grid to make sure it gets repopulated when you change the active content sets<br>
     * USER:  LEGAL<br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void repopulateSourceGridCodesbenchTest()
    {
        String sourceContentSet ="South Dakota (Development)";

        homePage().goToHomePage();
        loginPage().logIn();

        homeMenu().goToUserPreferencesContentSets();
        if(!contentPreferencesPage().isContentSelected(sourceContentSet))
        {
            contentPreferencesPage().selectHiddenContentOption(sourceContentSet);
            contentPreferencesPage().addSourceTargetContentSet();
            userContentSetPreferencesPage().saveContentSettings();
        }

        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterContentSet(sourceContentSet);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        boolean contentAppearsInitially = tableTestingPage().doesCellWithTextExist(TableTestingPage.RenditionColumns.CONTENT_SET, sourceContentSet);

        homeMenu().goToUserPreferencesContentSets();
        contentPreferencesPage().selectSelectedContentOption(sourceContentSet);
        contentPreferencesPage().removeSourceTargetContentSet();

        userContentSetPreferencesPage().saveContentSettings();

        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFooterToolsPage().repopulateView();

        boolean contentDoesntAppearAfterRemoval = sourceNavigateGridPage().doesElementExist(SourceNavigateReportsPageElements.NO_RECORDS_FOUND);

        homeMenu().goToUserPreferencesContentSets();
        contentPreferencesPage().selectHiddenContentOption(sourceContentSet);
        contentPreferencesPage().addSourceTargetContentSet();

        userContentSetPreferencesPage().saveContentSettings();

        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFooterToolsPage().repopulateView();

        boolean contentAppearsFinal = tableTestingPage().doesCellWithTextExist(TableTestingPage.RenditionColumns.CONTENT_SET, sourceContentSet);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(contentAppearsInitially, "content appears initially"),
            () -> Assertions.assertTrue(contentDoesntAppearAfterRemoval, "Content doesn't appear after content set is removed"),
            () -> Assertions.assertTrue(contentAppearsFinal, "Content appears after the content set is re-added")
        );
    }
}
