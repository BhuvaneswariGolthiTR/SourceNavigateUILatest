package com.thomsonreuters.codes.codesbench.quality.tests.navigation.risk.risk;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.RISK;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HomeNavigationRiskContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Home page loads when selecting 'My Home Page' from the Home menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
	public void navigateToMyHomePageRiskTest()
    {
        homePage().goToHomePage();
        loginPage().logIn();
        
        boolean homePageLoaded = homeMenu().goToMyHomePage();
        Assertions.assertTrue(homePageLoaded, "The Home page did not load when it should have");
    }

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Content Preferences page loads when selecting the 'Content Preferences' context menu option from the Home menu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@RISK
	@LOG
	public void navigateToHomeContentPreferencesLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean contentPreferencesPageLoaded = homeMenu().goToContentPreferences();
		Assertions.assertTrue(contentPreferencesPageLoaded, "The Content Preferences page did not load when it should have");
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
    public void navigateToUserPreferencesPropertiesRiskTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

		boolean userPropertiesPageLoaded = homeMenu().goToUserPreferencesProperties();
		Assertions.assertTrue(userPropertiesPageLoaded, "The User Properties page did not load when it should have");
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
    public void navigateToUserPreferencesSecurityRiskTest() 
    {
		homePage().goToHomePage();
        loginPage().logIn();

		boolean userSecuritySettingsPageLoaded = homeMenu().goToUserPreferencesSecurity();
		Assertions.assertTrue(userSecuritySettingsPageLoaded, "The User Security Settings page did not load when it should have");
    }
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the User Content Set Preferences page loads when selecting User Preferences -> Content Sets from the Home menu<br>
	 * USER - Risk  <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void navigateToUserPreferencesContentSetsRiskTest() 
    {
		homePage().goToHomePage();
        loginPage().logIn();

		boolean userContentSetPreferencesPageLoaded = homeMenu().goToUserPreferencesContentSets();
		Assertions.assertTrue(userContentSetPreferencesPageLoaded, "The User Content Set Preferences page did not load when it should have");
    }
	
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the 'Group Related Rule Books' page opens after selecting 'Related Rule Book Preferences' in the Home menu<br>
	 * USER - Risk <br>
	 */
	@Test
	@IE_EDGE_MODE
    @RISK
    @LOG
    public void navigateToRelatedRuleBooksPreferencesRiskTest()
    {	
		homePage().goToHomePage();
        loginPage().logIn();

		boolean groupRelatedRuleBooksPageOpened = homeMenu().goToHomeRelatedRuleBookPreferences();
		Assertions.assertTrue(groupRelatedRuleBooksPageOpened, "The 'Group Related Rule Books' page did not open when it should have");
    }
}
