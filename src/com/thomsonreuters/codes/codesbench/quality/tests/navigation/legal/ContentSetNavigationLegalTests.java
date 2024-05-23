package com.thomsonreuters.codes.codesbench.quality.tests.navigation.legal;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.TestSetupEdge;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.home.HomeDatabaseUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class ContentSetNavigationLegalTests extends TestService
{
	private final String USCA_CONTENT_SET = "USCA(Development)";

	/**
     *  STORY/BUG - n/a <br>
	 * SUMMARY -  Test to verify that the 'Change Default Content Set' functionality of the 'Content Set Preferences' page is working correctly.<br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void changeDefaultContentSetLegalTest() 
    {
		homePage().goToHomePage();
		loginPage().logIn();
		
		String initialContentSet = homePage().getCurrentContentSetFromHomepageDropdown();
        
		homeMenu().goToUserPreferencesContentSets();
        userContentSetPreferencesPage().setDefaultTargetContentSet(USCA_CONTENT_SET);

		TestSetupEdge.closeBrowser();
		TestSetupEdge.openBrowser();
		homePage().goToHomePage();
		loginPage().logIn();

        String finalContentSet = homePage().getCurrentContentSetFromHomepageDropdown();
        boolean contentSetChanged = !initialContentSet.equalsIgnoreCase(finalContentSet);
        Assertions.assertTrue(contentSetChanged, "The default content set was not changed when it should have been");
    }
	
	/**
     *  STORY/BUG - n/a <br>
	 * SUMMARY - This test verifies that changing the content set in the dropdown menu also changes the content set shown in the top right corner of the home page<br>
	 * USER - Legal <br>
     */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void changeContentSetHomepageDropdownLegalTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();
		
		homePage().setMyContentSet(USCA_CONTENT_SET);

		String contentSetInTopRightCornerOfHomePage =  homePage().getCurrentContentSetFromUpperRight();
		String contentSetInHomePageDropdown =  homePage().getCurrentContentSetFromHomepageDropdown();

		boolean updatedContentSetAppearsInUpperRight = contentSetInTopRightCornerOfHomePage.equals(USCA_CONTENT_SET);
		boolean updatedContentSetAppearsInHomePageDropdown = contentSetInHomePageDropdown.equals(USCA_CONTENT_SET);

		Assertions.assertAll
		(
				() -> Assertions.assertTrue(updatedContentSetAppearsInUpperRight, "The content set change was not noted in the upper-right corner of the Home Page when it should have been"),
				() -> Assertions.assertTrue(updatedContentSetAppearsInHomePageDropdown, "The content set change was not noted in the content set dropdown of the Home Page when it should have been")
		);
	}
	   
	/**
	*  STORY/BUG - n/a <br>
	* SUMMARY - Test to verify that a legal user doesn't see risk only content sets in Home -> Content Set Preferences<br>
	* USER - Legal <br>
    */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void riskHierarchyContentSetsNotPresentedLegalTest()  
	{
		List<String> riskOnlyContentSets = Arrays.asList("BOX Options Exchange (BOX)", "CBOE Futures Exchange (CFE)",
			"Chicago Board Options Exchange (CBOE)", "Crown Dependencies",
			"ICE Swap Trade", "International Securities Exchange",
			"NYSE Arca", "NYSE MKT Rule Book (Development)");
	    
	    homePage().goToHomePage();
		loginPage().logIn();
	    	
	    homeMenu().goToUserPreferencesContentSets();
	    boolean riskContentSetsDoNotAppear = userContentSetPreferencesPage().doesHierarchyContentSetsNotContainGivenContentSetList(riskOnlyContentSets);
	    Assertions.assertTrue(riskContentSetsDoNotAppear, "Risk Content Sets appeared in the list");
	}

	@AfterEach
	public void setDefaultContentSet()
	{
		Connection connection = BaseDatabaseUtils.connectToDatabaseUAT();
		HomeDatabaseUtils.setDefaultTargetContentSet(connection, "SOS.IAT", user().getUsername());
		BaseDatabaseUtils.disconnect(connection);
	}
}
