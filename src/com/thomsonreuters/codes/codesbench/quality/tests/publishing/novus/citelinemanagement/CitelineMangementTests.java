package com.thomsonreuters.codes.codesbench.quality.tests.publishing.novus.citelinemanagement;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.PublishingMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.PRODLEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class CitelineMangementTests extends TestService
{
    Connection uatConnection;

    @AfterEach
    public void closeConnection()
    { BaseDatabaseUtils.disconnect(uatConnection); }

    /**
     * STORY: Halyconst-8921 <br>
     * SUMMARY: Checks weither the citline managmnet option is not displayed for publising user that does not have swat, partech or publisher access <br>
     * USER: legal <br>
     */
    @Test
    @IE_EDGE_MODE
    @PRODLEGAL
    @LOG
    public void jurisdictionSupportPublishingDropdownMenuOptionsForApproves()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        String contentSetId = ContentSets.IOWA_DEVELOPMENT.getCode();
        PublishingDatabaseUtils.checkAndRemovePublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetId, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        //navigate to home -> user preferences -> security
        homeMenu().goToUserPreferencesSecurity();

        //verify it does not have swat, paratech, or publishier
        boolean doesUserHaveSwatAccess =  userSecuritySettingsPage().doesUserHaveSwatAccess();
        Assertions.assertFalse(doesUserHaveSwatAccess, "User is not supposed to sees Swat Access");

        boolean doesUserHaveParatechAccess = userSecuritySettingsPage().doesUserHaveParatechAccess();
        Assertions.assertFalse(doesUserHaveParatechAccess, "User is not supposed to see Paratech Access ");

        boolean doesUserHaveWestlawPublishingAccess = userSecuritySettingsPage().doesUserHaveWestlawPublisherAccess();
        Assertions.assertFalse(doesUserHaveWestlawPublishingAccess, "User is not supposed to see Westlaw Publisher Access");

        // hover over publishing -> novous: verify options do not display
        boolean citlineMangagmentIsNotDisplayed = publishingMenu().isPublishingNovusSubmenuItemAvailable(PublishingMenuElements.NOVUS_CITLINE_MANAGAMENT);

        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetId, uatConnection);
        homePage().refreshPage();

        // go to publishing novous: verify options displays
        boolean citlineMangagmentIsDisplayed = publishingMenu().isPublishingNovusSubmenuItemAvailable(PublishingMenuElements.NOVUS_CITLINE_MANAGAMENT);

        Assertions.assertAll
        (
            () ->Assertions.assertFalse(citlineMangagmentIsNotDisplayed, "Citline Mangament is not displayed"),
            () ->Assertions.assertTrue(citlineMangagmentIsDisplayed, "Citline Mangament is Displayed")
        );
    }
}
