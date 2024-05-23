package com.thomsonreuters.codes.codesbench.quality.tests.publishing.publishingstatusreport.pubnavigateevaluation;

import com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.toolbox.ToolbarElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CITELINE;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.publishing.PublishingDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class PubNavigateEvaluationSwatAccessTests extends TestService
{
    Connection uatConnection;
    String contentSetIowa = ContentSets.IOWA_DEVELOPMENT.getCode();

    /**
     * STORY: HALCYONST-7850 <br>
     * SUMMARY: Verifies that users with out SWAT access do not have the ability to select the Next button on the
     * Pub Navigate Evaluation - Issues after Pub Complete UI. <br>
     * USER: Legal <br>
     */
    @Test
    @EDGE
    @LEGAL
    @LOG
    public void nextButtonAccessForNonSwatUserTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndRemovePublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        //verify next button is not display for a user who does not have SWAT access
        publishingMenu().goToPublishingStatusReportsPubNavigateEvaluation();
        boolean nextButtonIsDisplayedForLegalUser = toolbarPage().isElementDisplayed(ToolbarElements.NEXT);
        Assertions.assertFalse(nextButtonIsDisplayedForLegalUser);
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
    public void nextButtonAccessForSwatUserTest()
    {
        uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
        PublishingDatabaseUtils.checkAndRemovePublishApproverForContentSet(user().getUsername(), user().getFirstname(), user().getLastname(), contentSetIowa, uatConnection);

        homePage().goToHomePage();
        loginPage().logIn();

        publishingMenu().goToPublishingStatusReportsPubNavigateEvaluation();

        //HALCYONST-7394
        boolean nextButtonIsDisplayedForLegalUser = toolbarPage().isElementDisplayed(ToolbarElements.NEXT);
        Assertions.assertTrue(nextButtonIsDisplayedForLegalUser, "Next button is displayed");
    }

    @AfterEach
    public void closeConnection()
    {
        BaseDatabaseUtils.disconnect(uatConnection);
    }
}
