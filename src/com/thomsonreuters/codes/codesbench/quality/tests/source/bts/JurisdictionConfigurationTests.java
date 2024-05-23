package com.thomsonreuters.codes.codesbench.quality.tests.source.bts;

import com.thomsonreuters.codes.codesbench.quality.pageelements.source.BTSPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts.BtsWebUiPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

public class JurisdictionConfigurationTests extends TestService 
{
	/**
	 * STORY/BUG -  N/A <br>
	 * SUMMARY - This test checks that the jurisdiction drop-down works, as well as showing the 
	 * specific columns that go with that jurisdiction. Checks that column does not show up with 
	 * jurisdictions that should not have them. Checks that the last page loads correctly.  <br>
	 * USER - Legal <br>
	 * @return - void <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void jurisdictionConfigurationTest()
	{
		String marylandJurisdiction = "MD";
		String usJurisdiction = "US";
		String year2018 = "2018";
		//Old Code
		//String proclamationsAndExecutiveOrders = "U4: Proclamations and Executive Orders";
		//new Code
		String proclamationsAndExecutiveOrders = "U4";
		
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean btsWindowAppeared = sourceMenu().goToSourceC2012BTS();
		Assertions.assertTrue(btsWindowAppeared, "The Bts Window did not appear.");

		btsWebUiPage().setLegislativeYear(year2018);
		btsWebUiPage().setJurisdictionDropdown(marylandJurisdiction);
		boolean mdHasNoAnotherDropDown = btsWebUiPage().isElementDisplayed(BtsWebUiPageElements.SPECIFIC_OPTION_DROPDOWN_XPATH);
		
		btsWebUiPage().setJurisdictionDropdown(usJurisdiction);
		boolean usHasAnotherDropDown = btsWebUiPage().isElementDisplayed(BtsWebUiPageElements.SPECIFIC_OPTION_DROPDOWN_XPATH);
		
		btsWebUiPage().selectSpecificTable(proclamationsAndExecutiveOrders);
		boolean dataLoadsWithoutErrors = btsWebUiPage().dataLoadedWithoutErrors();
		
		Assertions.assertAll
		(
			() ->Assertions.assertFalse(mdHasNoAnotherDropDown, "Error: MD DOES have a Table Code header."),
			() ->Assertions.assertTrue(usHasAnotherDropDown, "Error: US DOES NOT have another dropdown."),
			() ->Assertions.assertTrue(dataLoadsWithoutErrors, "Error: Data does not load correctly after changing dropdown option.")
		);
	}
}
