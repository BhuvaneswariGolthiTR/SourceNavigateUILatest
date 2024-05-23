package com.thomsonreuters.codes.codesbench.quality.tests.tools.statefeed;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.statefeeds.StateFeedsDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StateFeedNegativeTests extends TestService
{
    private static final String CONTENT_SET = ContentSets.IOWA_DEVELOPMENT.getName();

    private String groupName = null;
    private boolean deleteGroupNameFlag = false;

    /**
     * STORY/BUG - HALCYONST-17204 <br>
     * SUMMARY - This test verifies if a user declines the delete files warning that node is not deleted from the grid <br>
     * USER - Legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void deleteFilesNegativeTest()
    {
        groupName = StateFeedsDataMocking.insertTestFilesIntoStateFeed();
        deleteGroupNameFlag = true;

        navigateToStateFeedAndSelectGroupName();
        int expectedRows = fileManagementPage().getAllRows().size();

        fileManagementPage().checkSelectAllCheckbox();
        fileManagementPage().clickDeleteButton();
        fileManagementPage().declineDeleteFileAlert();

        Assertions.assertEquals(expectedRows, fileManagementPage().getAllRows().size(), "The file was deleted when it shouldn't have been");
    }

    private void navigateToStateFeedAndSelectGroupName()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        homePage().setMyContentSet(CONTENT_SET);
        toolsMenu().goToStateFeed();

        extractedDataPage().clickGroupFilterMenu();
        extractedDataPage().filterForGroup(groupName);
        extractedDataPage().clickGroupName(groupName);
    }

    @AfterEach
    public void deleteGroupNameFromFtpServer()
    {
        if (deleteGroupNameFlag && groupName != null)
        {
            StateFeedsDataMocking.deleteGroup(groupName);
            deleteGroupNameFlag = false;
        }
    }
}
