package com.thomsonreuters.codes.codesbench.quality.tests.navigation.legal;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.HomeMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HomeNavigationLegalSharedContentSetTests extends TestService
{
	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that navigating to Home -> My Home Page will refresh/open the Home page<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToHomeMyHomePageLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean homePageLoaded = homeMenu().goToMyHomePage();
		Assertions.assertTrue(homePageLoaded, "The Home page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the Content Preferences page loads when selecting the 'Content Preferences' context menu option from the Home menu <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
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
	 * SUMMARY - This test verifies that the selected content set from the Home page content set dropdown and the content set in the top right corner of the Home page match.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void homePageContentSetMatchingLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		String dropdownContentSet = homePage().getCurrentContentSetFromHomepageDropdown();
		String topRightContentSet = homePage().getCurrentContentSetFromUpperRight();

		boolean topRightContentSetEqualsCorrectContent = topRightContentSet.equals(dropdownContentSet);
		Assertions.assertTrue(topRightContentSetEqualsCorrectContent, "The content set from the upper right is not the same as the one in the dropdown but they should be equal");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the User Properties page loads when selecting User Preferences -> Properties in the Home menu<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToUserPreferencesPropertiesLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean userPropertiesPageLoaded = homeMenu().goToUserPreferencesProperties();
		Assertions.assertTrue(userPropertiesPageLoaded, "The User Properties page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the User Content Set Preferences page loads when selecting User Preferences -> Content Sets from the Home menu <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToUserPreferencesContentSetsLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean userContentSetPreferencesPageLoaded = homeMenu().goToUserPreferencesContentSets();
		Assertions.assertTrue(userContentSetPreferencesPageLoaded, "The User Content Set Preferences page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the User Security Settings page loads when selecting User Preferences -> Security from the Home menu <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void navigateToUserPreferencesSecurityLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean userSecuritySettingsPageLoaded = homeMenu().goToUserPreferencesSecurity();
		Assertions.assertTrue(userSecuritySettingsPageLoaded, "The User Security Settings page did not load when it should have");
	}

	/**
	 * STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that the 'Related Rule Book Preferences' option in the Home menu does not appear for Legal users <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void relatedRuleBooksPreferencesLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		homeMenu().openMenu();

		boolean relatedRuleBookPreferencesMenuOptionIsDisplayed = homeMenu().isElementDisplayed(HomeMenuElements.HOME_MENU_RELATED_RULE_BOOK_PREFERENCES_XPATH);
		Assertions.assertFalse(relatedRuleBookPreferencesMenuOptionIsDisplayed, "The 'Related Rule Book Preferences' option is displayed in the Home menu when it shouldn't be");
	}
}
