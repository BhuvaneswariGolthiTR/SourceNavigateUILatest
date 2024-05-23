package com.thomsonreuters.codes.codesbench.quality.tests.navigation.carswell;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.PublishingMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CARSWELL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PublishingNavigationCarswellTests extends TestService
{
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Publishing menu is not present for Canada users<br>
	 * USER - Carswell <br>
     */
	@Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
    public void navigateToPublishingCanadaTest()
    {
    	homePage().goToHomePage();
		loginPage().logIn();
        
        boolean publishingMenuIsPresent = homePage().isElementDisplayed(PublishingMenuElements.PUBLISHING_MENU_XPATH);
        Assertions.assertFalse(publishingMenuIsPresent, "The Publishing menu is present when it should not be");
    }
}
