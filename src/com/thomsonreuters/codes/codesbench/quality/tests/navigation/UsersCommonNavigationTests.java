package com.thomsonreuters.codes.codesbench.quality.tests.navigation;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import org.junit.jupiter.api.Assertions;

public class UsersCommonNavigationTests extends TestService
{
    // Test method body for different users navigation tests
    public void navigateToToolsOCExtractTest()
    {
        // 1	Log onto the Iowa (Development) content set
        homePage().goToHomePage();
        loginPage().logIn();

        // 2	Open Tools menu
        // 3	VERIFY: List of Tools menu items is shown and there is 'OC Extract' option after 'State Feeds'
        boolean ocExtractDisplayedAfterStateFeeds = toolsMenu().isOCExtractDisplayedAfterStateFeeds();
        Assertions.assertTrue(ocExtractDisplayedAfterStateFeeds, "OC Extract tools menu option is displayed after State Feeds");

        // 4	Click 'OC Extract'
        boolean ocExtractPageOpened = toolsMenu().goToOCExtract();
        Assertions.assertTrue(ocExtractPageOpened, "The OC Extract page opened");

        // 5	VERIFY: O'Connors Processing UI window is opened
        boolean publicationsPageOpened = publicationsPage().isPageOpened();
        Assertions.assertTrue(publicationsPageOpened, "Publications page opened");
    }
}
