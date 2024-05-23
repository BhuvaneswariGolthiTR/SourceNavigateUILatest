package com.thomsonreuters.codes.codesbench.quality.tests.navigation.carswell;

import com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements.HomeMenuElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.CARSWELL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HomeNavigationCarswellTests extends TestService
{
	/**
     * STORY/BUG - n/a <br>
	 * SUMMARY - Tests navigation to Home -> My Home Page<br>
	 * USER - Carswell <br>
     */
	@Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
    public void myHomePageNavigationCarswellTest()
    {
		homePage().goToHomePage();
		loginPage().logIn();

        boolean homePageAppeared = homeMenu().goToMyHomePage();
        Assertions.assertTrue(homePageAppeared, "The Home Page did not appear when it should have");
    }
    
	 /**
     * STORY/BUG - n/a <br>
	 * SUMMARY - Tests navigation to Home->Content Preferences <br>
	 * USER - Carswell <br>
     */
    @Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
    public void contentPreferencesNavigationCarswellTest() 
    {
    	homePage().goToHomePage();
		loginPage().logIn();

        boolean contentPreferencesPageAppeared = homeMenu().goToContentPreferences();
        Assertions.assertTrue(contentPreferencesPageAppeared, "The Content Preferences page did not appear when it should have");
    }
    
    /**
     * STORY/BUG - n/a <br>
	 * SUMMARY - Tests that the content sets on the home page match. <br>
	 * USER - Carswell <br>
     */
    @Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
    public void contentSetMatchingNavigationCarswellTest() 
    {
    	homePage().goToHomePage();
		loginPage().logIn();

       boolean topRightConSetContainsDropDownConSet = homePage().isTopRightContentSetEqualToDropDownContentSet();
       Assertions.assertTrue(topRightConSetContainsDropDownConSet, "The content set on the homepage dropdown and the content set in the top right do not match when they should");
    }
    
    /**
     * STORY/BUG - n/a <br>
	 * SUMMARY - Tests navigation to Home->User Preferences->Properties<br>
	 * USER - Carswell <br>
     */
    @Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
    public void userPreferencesPropertiesNavigationCarswellTest()
    {
    	homePage().goToHomePage();
		loginPage().logIn();
    	
    	boolean userPreferencesPropertiesPageAppeared = homeMenu().goToUserPreferencesProperties();
        Assertions.assertTrue(userPreferencesPropertiesPageAppeared, "The User Preferences Properties page did not appear when it should have");
    }
    
    /**
     * STORY/BUG - n/a <br>
	 * SUMMARY - Tests navigation to Home->User Preferences->Content Sets <br>
	 * USER - Carswell <br>
     */
    @Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
    public void userPreferencesContentSetsNavigationCarswellTest()
    {
    	homePage().goToHomePage();
		loginPage().logIn();
    	
    	boolean userPreferencesContentSetsPageAppeared = homeMenu().goToUserPreferencesContentSets();
        Assertions.assertTrue(userPreferencesContentSetsPageAppeared, "The User Preferences Content Sets page did not appear when it should have");
    }
    
    /**
     * STORY/BUG - n/a <br>
	 * SUMMARY - Tests navigation to Home->User Preferences->Security <br>
	 * USER - Carswell <br>
     */
    @Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
    public void userPreferencesSecurityNavigationCarswellTest()
    {
    	homePage().goToHomePage();
		loginPage().logIn();
    	
    	boolean userPreferencesSecurityPageAppeared = homeMenu().goToUserPreferencesSecurity();
        Assertions.assertTrue(userPreferencesSecurityPageAppeared, "The User Preferences Security page did not appear when it should have");
    }
    
    
    /**
     * STORY/BUG - n/a <br>
	 * SUMMARY - Tests that a Canada user doesn't see Home -> Related Rulebook Preferences <br>
	 * USER - Carswell <br>
     */
    @Test
	@IE_EDGE_MODE
	@CARSWELL
	@LOG
    public void navigateToRelatedRulebookPreferencesCarswellTest() 
    {
    	homePage().goToHomePage();
		loginPage().logIn();
		
		homeMenu().openMenu();

    	boolean relatedRuleBooksOptionAvailable = homeMenu().isElementDisplayed(HomeMenuElements.HOME_MENU_RELATED_RULE_BOOK_PREFERENCES_XPATH);
        Assertions.assertFalse(relatedRuleBooksOptionAvailable, "The Related Rule Books Preferences menu option was displayed when it should not be");
    }
}
