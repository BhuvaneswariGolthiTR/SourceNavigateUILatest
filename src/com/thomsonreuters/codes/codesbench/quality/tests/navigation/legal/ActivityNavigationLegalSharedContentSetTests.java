package com.thomsonreuters.codes.codesbench.quality.tests.navigation.legal;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.ActivityMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActivityNavigationLegalSharedContentSetTests extends TestService
{
	/**
     *  STORY/BUG - n/a <br>
	 * SUMMARY -  Verifies the user doesn't see Activity  <br>
	 * USER - Legal  <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToActivityMenuLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean activityMenuOptionAvailable = homePage().isElementDisplayed(ActivityMenuElements.ACTIVITY_MENU_XPATH);
        Assertions.assertFalse(activityMenuOptionAvailable, "Activity menu appeared when it should not have");
	}
}