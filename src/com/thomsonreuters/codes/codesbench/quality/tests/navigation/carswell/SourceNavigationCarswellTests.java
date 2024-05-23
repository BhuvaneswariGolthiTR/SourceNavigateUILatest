package com.thomsonreuters.codes.codesbench.quality.tests.navigation.carswell;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.SourceMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CARSWELL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SourceNavigationCarswellTests extends TestService
{
	
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Source menu is not present for a Canada User <br>
	 * USER - Carswell <br>
     */
	@Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
    public void navigateToSourceCanadaTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();
		
        boolean sourceMenuIsPresent = homePage().isElementDisplayed(SourceMenuElements.SOURCE_MENU_XPATH);
        Assertions.assertFalse(sourceMenuIsPresent, "The Source menu is present when it should not be");
    }
}
