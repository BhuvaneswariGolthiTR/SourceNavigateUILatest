package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.shared;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.PublishingMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PublishingNavigationRiskSharedContentSetTests extends TestService
{
    /**
     * STORY/BUG - n/a <br>
     * SUMMARY - This test verifies that the Publishing menu is not present for a Risk User in a shared content set<br>
     * USER - Risk <br>
    */
	@Test
    @IE_EDGE_MODE
    @RISK
    @LOG
    public void navigateToPublishingRiskSharedTest()
    {
        String iowaContentSet = "Iowa (Development)";

        homePage().goToHomePage();
        loginPage().logIn();

        homePage().setMyContentSet(iowaContentSet);
    	
        boolean publishingMenuIsDisplayed = homePage().isElementDisplayed(PublishingMenuElements.PUBLISHING_MENU_XPATH);
        Assertions.assertFalse(publishingMenuIsDisplayed, "The Publishing menu is present when it should not be");
    }
}
