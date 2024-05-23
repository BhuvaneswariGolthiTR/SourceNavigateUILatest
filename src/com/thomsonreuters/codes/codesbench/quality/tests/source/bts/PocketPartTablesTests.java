/**
 * 
 */
package com.thomsonreuters.codes.codesbench.quality.tests.source.bts;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

public class PocketPartTablesTests extends TestService
{
	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - This test checks to make sure that you can set the jurisdiction and
	 * then the the Legislative year then generate the table. Checks that you can
	 * cancel, and that the id matches what should be based on what was entered in
	 * the Legislative year. and jurisdiction<br>
	 * USER - Legal <br>
	 * @return - void <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void pocketPartsTablesTest() 
	{
		String textToSend = "test" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
		String jurisdiction = "IA";
		String currentYearMinusOne = Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy()) - 1 + "";

		homePage().goToHomePage();
		loginPage().logIn();

		boolean btsWindowAppeared = sourceMenu().goToSourceC2012BTS();
		Assertions.assertTrue(btsWindowAppeared, "The bts window did not load");

		btsWebUiPage().setJurisdictionDropdown(jurisdiction);
		btsWebUiPage().goToTasksTablesGeneratePocketPartTables();
		
		generatePocketPartTablesPage().setLegislativeYear(currentYearMinusOne);
		
		boolean generateSuccessfulMessageAppeared = generatePocketPartTablesPage().clickGenerateButton();
		Assertions.assertTrue(generateSuccessfulMessageAppeared, "The pocket part generated for what we selected");
		generatePocketPartTablesPage().clickViewEditButton();
		String textBeforeSend =  generatePocketPartTablesPage().getViewEditText();
		generatePocketPartTablesPage().sendTextToViewEditTextArea(textToSend);
		String afterEdit = generatePocketPartTablesPage().getViewEditText();
		generatePocketPartTablesPage().clickCancelButton();
		generatePocketPartTablesPage().clickListTab();
		
		int index = listPocketPartTablesPage().getIndexOfPocketPart(jurisdiction, currentYearMinusOne);
		Assertions.assertTrue(index != -1, "The index should not return -1. This means that we could not find the pocket part");
		
		String pocketPartID = listPocketPartTablesPage().getPocketPartID(index);
		String pocketPartName = listPocketPartTablesPage().getPocketPartName(index);
		String pocketPartLastModified = listPocketPartTablesPage().getpocketPartLastModified(index);
		
		boolean pocketPartNameIsCorrect = pocketPartName.equals(pocketPartID + ".TXT");
		boolean pocketPartGeneratedToday = pocketPartLastModified.contains(DateAndTimeUtils.getCurrrentDateyyyyMMdd());
		
		Assertions.assertAll
		(
			() -> Assertions.assertTrue(pocketPartNameIsCorrect, "The pocket part name does not match the expected pattern"),
			() -> Assertions.assertTrue(pocketPartGeneratedToday, "The pocket part generated is from today"),
			() -> Assertions.assertFalse(textBeforeSend.contains(textToSend), "The text before sending matches text to send"),
			() -> Assertions.assertTrue(afterEdit.contains(textToSend),"The text after edit does not match the one we sent")
		);
	}
}
