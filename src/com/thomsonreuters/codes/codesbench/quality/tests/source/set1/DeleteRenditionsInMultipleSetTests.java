package com.thomsonreuters.codes.codesbench.quality.tests.source.set1;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeleteRenditionsInMultipleSetTests extends TestService
{
    /**
     * STORY: NA  <br>
     * SUMMARY: Tests deleting and undeleting multiple flagged renditions <br>
     * USER: Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void deleteRenditionsInMultipleSetTest()
    {
        String multipleDuplicate = "Multiple";
        String contentSet = "Iowa (Development)";
        String contentType = "BILL";

        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate(multipleDuplicate);
        sourceNavigateFiltersAndSortsPage().setFilterContentSet(contentSet);
        sourceNavigateFiltersAndSortsPage().setFilterContentType(contentType);
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewMultipleAndDuplicateRenditions();

        int documentCount = sourceNavigateGridPage().getNumberOfRenditions();
        boolean multipleFlagsAppearedOnAllRenditions = sourceNavigateGridPage().getNumberOfMultipleFlaggedRenditions() == documentCount;
        sourceNavigateFooterToolsPage().clickSelectAllOnPage();
        sourceNavigateGridPage().onlyRightClickFirstItem();
        renditionContextMenu().modifyDeleteRenditions();
        sourcePage().switchToSourceNavigatePage();

        boolean multipleFlagsDontAppear = sourceNavigateGridPage().getNumberOfMultipleFlaggedRenditions() == 0;
        boolean allRenditionsDeleted = sourceNavigateGridPage().getNumberOfDeletedFlaggedRenditions() == documentCount;

        sourceNavigateFooterToolsPage().clickSelectAllOnPage();
        sourceNavigateGridPage().onlyRightClickFirstItem();
        renditionContextMenu().modifyUndeleteRenditions();
        sourcePage().switchToSourceNavigatePage();
        sourcePage().waitForGridRefresh();
        boolean multipleFlagsReappearedOnAllRenditions = sourceNavigateGridPage().getNumberOfMultipleFlaggedRenditions() == documentCount;
        boolean allRenditionsUndeleted = sourceNavigateGridPage().getNumberOfDeletedFlaggedRenditions() == 0;

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifyDeleteRendition();
        boolean oneLessMultipleFlagAppeared = sourceNavigateGridPage().getNumberOfMultipleFlaggedRenditions() == 0;

        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().modifyUndeleteRendition();
        sourcePage().waitForGridRefresh();
        boolean oneMoreMultipleFlagAppears = sourceNavigateGridPage().getNumberOfMultipleFlaggedRenditions() == documentCount;

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(multipleFlagsAppearedOnAllRenditions, "Multiple flags were present on some renditions"),
            () ->Assertions.assertTrue(multipleFlagsDontAppear, "Multiple flags not present on some renditions"),
            () ->Assertions.assertTrue(allRenditionsDeleted, "Some renditions were deleted"),
            () ->Assertions.assertTrue(multipleFlagsReappearedOnAllRenditions, "The multiple flags did reappear"),
            () -> Assertions.assertTrue(allRenditionsUndeleted, " All renditions were undeleted"),
            () ->Assertions.assertTrue(oneLessMultipleFlagAppeared, "The multiple flags were removed"),
            () -> Assertions.assertTrue(oneMoreMultipleFlagAppears, "The multiple flags were added back")
        );
    }
}
