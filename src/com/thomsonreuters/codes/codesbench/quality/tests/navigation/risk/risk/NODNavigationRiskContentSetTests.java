package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.risk;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.NodMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NODNavigationRiskContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the NOD menu is not present for a Risk User<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
	public void navigateToNodRiskTest()
	{
		homePage().goToHomePage();
        loginPage().logIn();

		boolean nodMenuIsPresent = homePage().isElementDisplayed(NodMenuElements.NOD_MENU_XPATH);
		Assertions.assertFalse(nodMenuIsPresent, "The NOD menu is present when it should not be");
	}
}
