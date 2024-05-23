package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishingstatusreport.nodonlypubnavigateevaluation;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;

import java.sql.Connection;

public class NodOnlyPubNavigateEvaluationSwatTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    /**
     * STORY: HALCYONST-7850 <br>
     * SUMMARY: Verifies that users without SWAT access do not have the ability to select the Next button on the
     * Pub Navigate Evaluation - Issues after Pub Complete UI. <br>
     * USER: Legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void isNextButtonNotDisplayedForNonSwatUserTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndRemovePublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        publishingMenu().goToPublishingStatusReportsNodOnlyPubNavigateEvaluation();
        boolean nextButtonIsDisplayedForLegalUser = toolbarPage().isElementDisplayed(ToolbarElements.NEXT);
        Assertions.assertFalse(nextButtonIsDisplayedForLegalUser,"The next button was displayed");
    }

    /**
     * STORY: HALCYONST-7850 <br>
     * SUMMARY: Verifies that users with SWAT access have the ability to select the Next button on the
     * Pub Navigate Evaluation - Issues after Pub Complete UI. <br>
     * USER: Citeline <br>
     */
    @Test
    @EDGE
    @CITELINE
    @LOG
    public void isNextButtonDisplayedForSwatUserTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndRemovePublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        publishingMenu().goToPublishingStatusReportsNodOnlyPubNavigateEvaluation();
        boolean nextButtonIsDisplayedForLegalUser = toolbarPage().isElementDisplayed(ToolbarElements.NEXT);
        Assertions.assertTrue(nextButtonIsDisplayedForLegalUser, "The next button was displayed");
    }

    @AfterEach
    public void closeConnection()
    {
        PublishingDatabaseUtils.checkAndAddPublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
