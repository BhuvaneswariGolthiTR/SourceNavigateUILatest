package com.thomsonreuters.codes.codesbench.quality.tests.source.bts;

import java.sql.Connection;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.BtsDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
//This passes code review
public class CreateNewBTSRecordTests  extends TestService 
{
	/*
	 * STORY/BUG - n/a <br>
	 * SUMMARY - creating a bts record in a different year and deleting it <br>
	 * USER - Legal <br>
	 * @return - void  <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void createNewSingleBTSRecordTest() 
	{
		String jurisidiction = "IA";
		String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
		String documentType = "SCR";
		String documentTypeValue = "25";
		String userId = user().getUsername().toUpperCase();
		String currentYearMinusOne = Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy()) - 1 + "";
		String currentYearMinusTwo = Integer.parseInt(DateAndTimeUtils.getCurrentYearyyyy()) - 2 + "";

		try
		{
			homePage().goToHomePage();
			loginPage().logIn();

			boolean btsWindowAppeared = sourceMenu().goToSourceC2012BTS();
			Assertions.assertTrue(btsWindowAppeared, "The bts window did not load");

			btsWebUiPage().setJurisdictionDropdown(jurisidiction);
			btsWebUiPage().setLegislativeYear(currentYearMinusOne);

			boolean createNewBTSRecordWindowAppears = btsWebUiPage().goToFileAddNewRecord();

			Assertions.assertTrue(createNewBTSRecordWindowAppears, "The 'Create New BTS' page did not load");
			createNewBtsRecordPage().setDocumentType(documentTypeValue);
			createNewBtsRecordPage().setDocumentNumber(currentDate);
			createNewBtsRecordPage().setLegislativeYear(currentYearMinusTwo);
			createNewBtsRecordPage().clickCreateButton();

			boolean notSameLegislativeYearMessageAppears = createNewBtsRecordPage().notSameLegislativeYearMessageAppears();
			Assertions.assertTrue(notSameLegislativeYearMessageAppears, "The message for the same year did not pop up");
			createNewBtsRecordPage().clickOkButtonOnMessage();

			btsWebUiPage().setLegislativeYear(currentYearMinusTwo);

			boolean findRecordWindowAppears = btsWebUiPage().goToEditFind(); //added wait for page loaded
			Assertions.assertTrue(findRecordWindowAppears, "The 'Find Record' page did not load");
			findRecordPage().setDocumentType(documentTypeValue);
			findRecordPage().setDocumentNumber(currentDate);
			findRecordPage().setLegislativeYear(currentYearMinusTwo);
			findRecordPage().clickFindNow();

			boolean btsRecordIsNotDeleted = btsWebUiPage().doesRecordExist(currentDate, jurisidiction, documentType);
			btsWebUiPage().rightClickRecord(currentYearMinusTwo, jurisidiction, documentType);
			btsWebUiContextMenu().delete();  //added in waitForPageLoaded

			boolean btsDeleteMessageAppears = deleteRecordsPage().deleteMessageAppears();
			Assertions.assertTrue(btsDeleteMessageAppears, "The message 'Are you sure you want to delete record(s)?' did not appear");
			deleteRecordsPage().clickOkButtonOnMessage();
			boolean btsRecordDeleted = btsWebUiPage().doesRecordExist(currentDate, jurisidiction, documentType);

			Assertions.assertAll
			(
				() ->Assertions.assertTrue(btsRecordIsNotDeleted, "The BTS record is not deleted"),
				() ->Assertions.assertFalse(btsRecordDeleted, "The BTS record was not deleted and it should be")
			);
		}
		finally
		{
			//clean up
			Connection uatBtsConnection = BaseDatabaseUtils.connectToDatabaseBtsUAT();
			BtsDatabaseUtils.deleteBtsRecord(uatBtsConnection, userId, jurisidiction, currentDate);
			BaseDatabaseUtils.commit(uatBtsConnection);
			BaseDatabaseUtils.disconnect(uatBtsConnection);
		}
	}
}
