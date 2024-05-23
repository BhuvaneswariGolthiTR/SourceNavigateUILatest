package com.thomsonreuters.codes.codesbench.quality.tests.navigation.carswell;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CARSWELL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContentSetNavigationCarswellTests extends TestService
{
	/**
     *  STORY/BUG - n/a <br>
	 * SUMMARY - Test to verify that a Canada user has access to only Canada Content Sets <br>
	 * USER - Carswell <br>
     */
	@Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
	public void navigateToHomeUserPreferencesContentSetsCarswellTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean userContentSetPreferencesWindowAppeared = homeMenu().goToUserPreferencesContentSets();
		boolean carswellOnlyContentSetsAppeared = userContentSetPreferencesPage().doOnlyCarswellContentSetsAppear();

		Assertions.assertAll
		(
				() -> Assertions.assertTrue(userContentSetPreferencesWindowAppeared, "User Content Set Preferences window did not appear when it should have"),
				() -> Assertions.assertTrue(carswellOnlyContentSetsAppeared, "User Content Set Preferences did not contain Carswell only content sets when it should have")
		);
	}
}
