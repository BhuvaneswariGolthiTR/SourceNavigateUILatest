package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.shared;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.NodMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NODNavigationRiskSharedContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the NOD menu is not present for a Risk user in a shared content set<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
	public void navigateToNODRiskSharedTest()
	{
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);
        
        boolean nodMenuIsDisplayed = homePage().isElementDisplayed(NodMenuElements.NOD_MENU_XPATH);
        Assertions.assertFalse(nodMenuIsDisplayed, "The NOD Menu is present when it shouldn't be");
	}
}
