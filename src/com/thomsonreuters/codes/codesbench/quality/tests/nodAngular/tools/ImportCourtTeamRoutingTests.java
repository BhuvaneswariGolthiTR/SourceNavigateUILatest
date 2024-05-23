package com.thomsonreuters.codes.codesbench.quality.tests.nodAngular.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools.ImportCourtTeamRoutingPopupElementsAngular;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.*;
import static com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.*;

public class ImportCourtTeamRoutingTests  extends TestService
{
    /**
     * HALCYONST-10599
     * 1. Log onto the Iowa (development) content set
     * 2. Go to NOD -> Tools -> Import Court/Team Routing
     * 3. VERIFY: Import Court/Team Routing window opened and current user is displayed
     * 4. Click on the Cancel button
     * 5. VERIFY: Imprort Court/Team Routing window is closed
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void importCourtTeamRoutingLayoutTest()
    {
        String expectedCurrentUserName = "Current User: " + user().getNodEditorUsername();
        // 1. Open NodClassifyUI home page
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD -> Tools -> Import Court/Team Routing
        homePageAngular().clickImportCourtTeamRouting();
        //3. VERIFY: Import Court/Team Routing window opened and current user is displayed
        Assertions.assertTrue(importCourtTeamRoutingPopupAngular().doesElementExist(ImportCourtTeamRoutingPopupElementsAngular.PAGE_TITLE), "Import Court Team Routing window DID NOT open");
        String actualCurrentUserName = importCourtTeamRoutingPopupAngular().getCurrentUserName();
        //4. Click on the Cancel button
        toolsFooterPopupAngular().clickCancel();
        //5. VERIFY: Imprort Court/Team Routing window is closed
        homePageAngular().waitForPageLoaded();
        boolean isImportCourtTeamRoutingWindowClosed = homePageAngular().doesElementExist(ImportCourtTeamRoutingPopupElementsAngular.PAGE_TITLE);

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedCurrentUserName, actualCurrentUserName, String.format("Current user name is '%s' instead of '%s'",actualCurrentUserName,expectedCurrentUserName)),
                () -> Assertions.assertFalse(isImportCourtTeamRoutingWindowClosed, "Import Court Team Routing window DID NOT close")
        );

    }

    /**
     * HALCYONST-10599
     * 1. Log onto the Iowa (development) content set
     * 2. Go to NOD -> Tools -> Import Court/Team Routing
     * 3. VERIFY: Import Court Team Routing window opened and current user is displayed
     * 4. Click on the Import button
     * 5. VERIFY: page displays that workflow has been created  and a workflow link
     */

    @Test
    @EDGE
    @LEGAL
    @LOG
    public void importCourtTeamRoutingWorkflowTest() // Test failed due to workflow in old and new NOD can't work on the same time and now it is turned of on NODAngular
    {
        String expectedCurrentUserName = "Current User: " + user().getNodEditorUsername();
        String expectedMessage = "Workflow has been created.";
        // 1. Open NodClassifyUI home page
        homePageAngular().openNodHomePage(ContentSets.IOWA_DEVELOPMENT);
        loginPage().logIn();
        //2. Go to NOD -> Tools -> Import Court/Team Routing
        homePageAngular().clickImportCourtTeamRouting();
        //3. VERIFY: Import Court/Team Routing window opened and current user is displayed
        Assertions.assertTrue(importCourtTeamRoutingPopupAngular().doesElementExist(ImportCourtTeamRoutingPopupElementsAngular.PAGE_TITLE), "Import Court Team Routing window DID NOT open");
        String actualCurrentUserName = importCourtTeamRoutingPopupAngular().getCurrentUserName();
        //4. Click on the Import button
        toolsFooterPopupAngular().createWorkflow();
        //5. VERIFY: page displays that workflow has been created  and a workflow link
        String actualMessage = importCourtTeamRoutingPopupAngular().getMessage();

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedCurrentUserName, actualCurrentUserName, String.format("Current user name is '%s' instead of '%s'",actualCurrentUserName,expectedCurrentUserName)),
                () -> Assertions.assertEquals(expectedMessage, actualMessage, String.format("Message is '%s' instead of '%s'",actualMessage,expectedMessage))
        );

    }
}
