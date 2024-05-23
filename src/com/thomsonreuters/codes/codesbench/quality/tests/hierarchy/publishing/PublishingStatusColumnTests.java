package com.thomsonreuters.codes.codesbench.quality.tests.hierarchy.publishing;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.enums.ContentSets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PublishingStatusColumnTests extends TestService
{
    String contentSetIowaText = ContentSets.IOWA_DEVELOPMENT.getName();
    String contentSetCodeOfFedRegsText = ContentSets.CODE_OF_FED_REGS_DEVELOPMENT.getName();

    /**
     * STORY: <br>
     * SUMMARY: Tests whether the publishing column exists for content sets that are enabled for publishing. This test goes from Iowa to Code Of Fed Regs. <br>
     * USER: LEGAL <br>
     */
        @Test
        @IE_EDGE_MODE
        @LEGAL
        @LOG
        public void publishingStatusColumnDisplayedInEnabledContentSetToDisabledContentSetTest()
        {
            homePage().goToHomePage();
            loginPage().logIn();

            homePage().setMyContentSet(contentSetIowaText);
            hierarchyMenu().goToNavigate();

            boolean isPublishingColumnDisplayedForEnabledPublishing = hierarchyNavigatePage().doesPublishingColumnExist();

            homeMenu().goToMyHomePage();
            homePage().setMyContentSet(contentSetCodeOfFedRegsText);
            hierarchyMenu().goToNavigate();

            boolean isPublishingColumnDisplayedForDisabledPublishing = hierarchyNavigatePage().doesPublishingColumnExist();

            Assertions.assertAll
            (
                () -> Assertions.assertTrue(isPublishingColumnDisplayedForEnabledPublishing, "We should see the publishing column in a publishing enabled content set"),
                () -> Assertions.assertFalse(isPublishingColumnDisplayedForDisabledPublishing, "We should not see the publishing column in a publishing disabled content set")
            );
    }

    /**
     * STORY: <br>
     * SUMMARY: Tests whether the publishing column exists for content sets that are enabled for publishing. This test goes from Code of Fed Regs to Iowa.<br>
     * USER: LEGAL <br>
     */
    @Test
    @IE_EDGE_MODE
    @LEGAL
    @LOG
    public void publishingStatusColumnDisplayedInDisabledContentSetToEnabledContentSetTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();

        homePage().setMyContentSet(contentSetCodeOfFedRegsText);
        hierarchyMenu().goToNavigate();

        boolean isPublishingColumnDisplayedForDisabledPublishing = hierarchyNavigatePage().doesPublishingColumnExist();

        homeMenu().goToMyHomePage();
        homePage().setMyContentSet(contentSetIowaText);
        hierarchyMenu().goToNavigate();

        boolean isPublishingColumnDisplayedForEnabledPublishing = hierarchyNavigatePage().doesPublishingColumnExist();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(isPublishingColumnDisplayedForEnabledPublishing, "We should see the publishing column in a publishing enabled content set"),
            () -> Assertions.assertFalse(isPublishingColumnDisplayedForDisabledPublishing, "We should not see the publishing column in a publishing disabled content set")
        );
    }


}