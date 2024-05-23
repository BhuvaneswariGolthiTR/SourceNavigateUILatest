package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy;

import com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.HierarchyPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.TargetDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.hierarchy.datapod.HierarchyDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateBookmarkTests extends TestService
{
    HierarchyDatapodObject datapodObject;

    /**
     * STORY/BUG - N/A <br>
     * SUMMARY - This test verifies that the user can create a bookmark and
     * see it on the home page and successfully delete the bookmark from there <br>
     * USER - LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void createBookmarkTest()
    {
        datapodObject = TargetDataMockingNew.Iowa.Small.insert();
        String nodeUUID = datapodObject.getSections().get(0).getNodeUUID();
        String bookmarkName = "TEST " + DateAndTimeUtils.getCurrentDateAndTimeddHHmmss();

        homePage().goToHomePage();
        loginPage().logIn();

        hierarchyMenu().goToNavigate();

        //Open Create Bookmark page
        hierarchySearchPage().searchNodeUuid(nodeUUID);
        siblingMetadataPage().rightClickSelectedSiblingMetadata();
        siblingMetadataContextMenu().createBookmark();

        //Create Bookmark with given name
        createBookmarkPage().setBookmarkName(bookmarkName);
        createBookmarkPage().clickSave();
        boolean bookmarkCreated = createBookmarkPage().checkBookmarkWasCreated();
        Assertions.assertTrue(bookmarkCreated, "Bookmark wasn't created when it should have");

        createBookmarkPage().clickClose();
        hierarchyNavigatePage().switchToWindow(HierarchyPageElements.PAGE_TITLE);

        //Verify Bookmark was created
        homeMenu().goToMyHomePage();
        boolean isBookmarkDisplayed = homePage().isBookmarkDisplayed(bookmarkName);
        Assertions.assertTrue(isBookmarkDisplayed, "Bookmark is not displayed when it should be");

        //Verify Bookmark was deleted
        homePage().deleteBookmark(bookmarkName);
        boolean expectedAlertAppeared = AutoITUtils.verifyAlertTextAndAccept(true, String.format("Do you want to delete the bookmark named: %s?", bookmarkName));
        Assertions.assertTrue(expectedAlertAppeared, "The expected alert didn't appear or was not accepted");

        homePage().waitForPageLoaded();
        boolean bookmarkIsStillDisplayed = homePage().isBookmarkDisplayed(bookmarkName);
        Assertions.assertFalse(bookmarkIsStillDisplayed, "The bookmark was not deleted when it should have");
    }

    @AfterEach
    public void cleanUp()
    {
        if (datapodObject != null)
        {
            datapodObject.delete();
        }
    }
}