package com.thomsonreuters.codes.codesbench.quality.tests.source.bts;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class GenerateListPocketPartTablesDoesntAppearJurisdictionTests extends TestService
{
	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - This Test checks that List and Generate Pocket Part Tables appears 
	 * when it should, and doesn't appear when it should not. <br>
	 * USER - Legal <br>
	 * @return - void  <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void generateListPocketPartTablesDoesntAppearJurisdictionTest()
	{
		String iowaJurisdiction = "IA";
		String marylandJurisdiction = "MD";
		
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean btsWindowAppeared = sourceMenu().goToSourceC2012BTS();
		Assertions.assertTrue(btsWindowAppeared);
		
		btsWebUiPage().setJurisdictionDropdown(iowaJurisdiction);
		btsWebUiPage().openTasksTablesSubmenu(); 
		boolean	iowaListAndGeneratePocketTablesIsDisplayed = btsWebUiPage().listAndGeneratePocketTablesIsDisplayed(); 
		
		btsWebUiPage().setJurisdictionDropdown(marylandJurisdiction);
		btsWebUiPage().openTasksTablesSubmenu();  
		
		boolean marylandListAndGeneratePocketTablesIsDisplayed =  btsWebUiPage().listAndGeneratePocketTablesIsDisplayed();
	    
		Assertions.assertAll
		(
			() -> Assertions.assertTrue(iowaListAndGeneratePocketTablesIsDisplayed,"The list and generate pocket parts options should be shown but is not"),
			() -> Assertions.assertFalse(marylandListAndGeneratePocketTablesIsDisplayed, "list and generate pocket parts options should not be shown but is.")
		);
	}
}
