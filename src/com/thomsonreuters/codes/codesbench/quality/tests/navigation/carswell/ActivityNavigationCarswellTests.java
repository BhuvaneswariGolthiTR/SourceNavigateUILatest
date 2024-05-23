package com.thomsonreuters.codes.codesbench.quality.tests.navigation.carswell;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CARSWELL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActivityNavigationCarswellTests extends TestService
{
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Activity Log page opens after selecting the 'User Log' menu option in the Activity menu.<br>
	 * USER - Carswell <br>
     */
	@Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
	public void navigateToActivityUserLogCarswellTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean activityLogPageOpened = activityMenu().goToUserLog();
		Assertions.assertTrue(activityLogPageOpened, "The Activity Log page did not open when it should have");
	}
}
