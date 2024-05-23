package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.shared;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.HomeMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HomeNavigationRiskSharedContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Home Page loads after selecting 'My Home Page' from the Home menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
	public void navigateToMyHomePageRiskSharedTest() 
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);
        
        boolean homePageLoaded = homeMenu().goToMyHomePage();
        Assertions.assertTrue(homePageLoaded, "The Home page did not load when it should have");
    }
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the User Content Set Preferences page loads when selecting the 'Content Sets' context menu option from the Home -> User Preferences submenu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void navigateToUserPreferencesContentSetsRiskSharedTest()
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);
        
        boolean userContentSetPreferencesPageLoaded = homeMenu().goToUserPreferencesContentSets();
		Assertions.assertTrue(userContentSetPreferencesPageLoaded, "The User Content Set Preferences page did not load when it should have");
    }
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the User Properties page loads when selecting User Preferences -> Properties in the Home menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void navigateToUserPreferencesPropertiesRiskSharedTest()
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);
        
		boolean userPropertiesPageLoaded = homeMenu().goToUserPreferencesProperties();
		Assertions.assertTrue(userPropertiesPageLoaded,"The User Properties page did not load when it should have");
    }
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the User Security Settings page loads when selecting User Preferences -> Security from the Home menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void navigateToUserPreferencesSecurityRiskSharedTest() 
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);
        
    	boolean userSecuritySettingsPageLoaded = homeMenu().goToUserPreferencesSecurity();
    	Assertions.assertTrue(userSecuritySettingsPageLoaded, "The User Security Settings page did not load when it should have");
    }
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Content preferences page loads after selecting the 'Content Preferences' menu option from the Home menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void navigateToContentPreferencesRiskSharedTest()
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);
		homeMenu().openMenu();

        boolean contentPreferencesMenuOptionIsDisplayed = homeMenu().isElementDisplayed(HomeMenuElements.HOME_MENU_CONTENT_PREFERENCES_XPATH);
        Assertions.assertFalse(contentPreferencesMenuOptionIsDisplayed, "The Content Preferences menu option is not displayed");
    }
    
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Group Related Rule Books page loads after selecting 'Related Rule Book Preferences' context menu option in the Home menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void navigateToRelatedRuleBooksPreferencesRiskSharedTest()
    {
		String iowaContentSet = "Iowa (Development)";

		homePage().goToHomePage();
		loginPage().logIn();

		homePage().setMyContentSet(iowaContentSet);
        
    	boolean groupRelatedRuleBooksPageLoaded = homeMenu().goToHomeRelatedRuleBookPreferences();
    	Assertions.assertTrue(groupRelatedRuleBooksPageLoaded, "The Group Related Rule Books page did not load when it should have");
    }
}
