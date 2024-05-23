package com.thomsonreuters.codes.codesbench.quality.tests.source.set3;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.SourceNavigatePageElements;

public class VerifyOrangeFlagRemovedDueToEditTests extends TestService
{
    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - Tests if orange flag is removed after editing the rendition and returns after
     *           restoring the baseline and validating and updating the links<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void orangeFlagsRemovedDueToEditTest()
    {
        //Log in and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter for specific rendition
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("AD");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("533");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //Verify the orange link flag exists
        boolean orangeFlagOriginallyExists = sourceMenu().doesElementExist(SourceNavigatePageElements.ORANGE_FLAG_ON_RENDITION);
        Assertions.assertTrue(orangeFlagOriginallyExists, "There is an orange flag present at beginning of test.");

        //Edit the rendition - make a change to the text between the invalid cite tags
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().editRendition();
        editorPage().switchDirectlyToTextFrame();
        editorPage().waitForPageLoaded();
        editorPage().openFindAndReplaceDialog();
        findAndReplacePage().setFindTerm("section 453D.8");
        findAndReplacePage().setReplaceTerm("REGULAR TEST");
        findAndReplacePage().clickReplaceAllButton();
        AutoITUtils.verifyAlertTextContainsAndAccept(true, "Finished searching the document.");
        editorPage().switchDirectlyToTextFrame();
        findAndReplacePage().close();
        editorPage().closeAndCheckInChangesWithBaselineNotes("Test " + DateAndTimeUtils.getCurrentDateMMddyyyy());
        editorPage().waitForEditorToClose();

        //Switch back to the main source navigate window
        editorPage().switchToWindow(SourceNavigatePageElements.PAGE_TITLE);
        editorPage().breakOutOfFrame();

        //Verify that the flag is gone
        boolean orangeFlagRemoved = !sourceMenu().doesElementExist(SourceNavigatePageElements.ORANGE_FLAG_ON_RENDITION);
        boolean blueFlagNowExists = sourceMenu().doesElementExist(SourceNavigatePageElements.BLUE_FLAG_ON_RENDITION);

        //Open View -> Rendition Baselines and restore to row 3
        viewBaselinesNavigatePage().restoreFirstRenditionToBaselineInRowWithGivenNumber("3");
        sourcePage().waitForGridRefresh();

        //Validate and update links, wait for workflow to finish
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().validateValidateAndUpdateLinks();
        sourceNavigateGridPage().waitUntilFirstDocBeUnlocked();

        boolean orangeFlagReturned = sourceMenu().doesElementExist(SourceNavigatePageElements.ORANGE_FLAG_ON_RENDITION);

        Assertions.assertAll
        (
            () ->Assertions.assertTrue(orangeFlagRemoved, "The orange flag did disappear after editing invalid cite."),
            () ->Assertions.assertTrue(blueFlagNowExists, "Flag did change to blue after editing rendition."),
            () ->Assertions.assertTrue(orangeFlagReturned, "Orange flag did re-appear.")
        );
    }
}
