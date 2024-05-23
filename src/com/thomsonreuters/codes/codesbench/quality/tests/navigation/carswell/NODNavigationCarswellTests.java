package com.thomsonreuters.codes.codesbench.quality.tests.navigation.carswell;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.NodMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CARSWELL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NODNavigationCarswellTests extends TestService
{
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the NOD menu is present for a Canada user<br>
	 * USER - Carswell <br>
     */
	@Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
	public void navigateToNODCanadaTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean nodMenuIsPresent = homePage().isElementDisplayed(NodMenuElements.NOD_MENU_XPATH);
		Assertions.assertTrue(nodMenuIsPresent, "NOD menu is not present when it should be");
	}
}
