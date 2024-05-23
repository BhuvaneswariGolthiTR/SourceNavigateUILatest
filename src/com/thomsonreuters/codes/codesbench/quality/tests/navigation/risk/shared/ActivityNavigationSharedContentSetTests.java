package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.shared;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.ActivityMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActivityNavigationSharedContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Activity menu is not display for Risk users under a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
    public void navigateToActivityRiskSharedTest() 
    {
    	String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();
		
		homePage().setMyContentSet(iowaContentSet);
        boolean activityMenuOptionIsDisplayed = homePage().isElementDisplayed(ActivityMenuElements.ACTIVITY_MENU_XPATH);

        Assertions.assertFalse(activityMenuOptionIsDisplayed, "The Activity menu appeared when it should not have");
    }
}
