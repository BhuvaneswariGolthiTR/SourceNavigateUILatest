package com.thomsonreuters.codes.codesbench.quality.tests.source.bts;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class SortBillsTests extends TestService 
{
	/**
	 * STORY/BUG -  N/A <br>
	 * SUMMARY - This checks that the sort correct message appears after sorting the bills <br>
	 * USER - Legal <br>
	 * @return - void <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void sortBillsTest() 
    {	
		homePage().goToHomePage();
		loginPage().logIn();
		boolean btsWindowAppeared = sourceMenu().goToSourceC2012BTS(); 
		boolean correctMsgAlert = btsWebUiPage().clickSortBills();
		
		Assertions.assertAll
		(
			() -> Assertions.assertTrue(btsWindowAppeared, "bts window did not appear"),
			() -> Assertions.assertTrue(correctMsgAlert, "The correct message did not appear")
		);
    }
}
