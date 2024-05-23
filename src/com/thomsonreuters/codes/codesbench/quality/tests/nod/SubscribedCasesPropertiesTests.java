package com.thomsonreuters.codes.codesbench.quality.tests.nod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class SubscribedCasesPropertiesTests extends TestService
{
	/**
	 * STORY/BUGS - N/A &lt;br&gt;
	 * SUMMARY -  Checks basic functionality of Properties page in Subscribed Cases&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
    @Test//(enabled=false)
    @LEGAL
    @LOG
    public void nodSubscribedCasesPropertiesTest()
    {
    	String userInitials = user().getSubscribedCasesInitials();

    	homePage().goToHomePage();
		loginPage().logIn();
		
		nodMenu().goToSubscribedCases();
		boolean subscribedCasesWindowAppeared = subscribedCasesPage().switchToSubscribedCasesPage();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "Subscribed Cases Window did not appear");
		
		//Get the serial numbers of the first and second cases
		String caseOneSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		String caseTwoSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(2);
	
		//Sign out first and second cases
		subscribedCasesPage().rightClickCase(caseOneSerialNumber);
		subscribedCasesContextMenu().signOutCase();
		subscribedCasesPage().rightClickCase(caseTwoSerialNumber);
		subscribedCasesContextMenu().signOutCase();
	
		//Verify that user initials populate "Signed Out By" field of first and second cases
		boolean caseOneInitialsPopulated = subscribedCasesGridPage().verifySignedOutByPopulated(caseOneSerialNumber, userInitials);
		boolean caseTwoInitialsPopulated = subscribedCasesGridPage().verifySignedOutByPopulated(caseTwoSerialNumber, userInitials);
	
		//Clear sign out of first and second cases
		subscribedCasesPage().rightClickCase(caseOneSerialNumber);
		subscribedCasesContextMenu().clearCaseSignOut(userInitials);
		subscribedCasesPage().rightClickCase(caseTwoSerialNumber);
		subscribedCasesContextMenu().clearCaseSignOut(userInitials);
	
		//Check if user initials are cleared from "Signed Out By" field on first and second cases
		boolean caseOneInitialsCleared = subscribedCasesGridPage().verifySignedOutByPopulated(caseOneSerialNumber, "");
		boolean caseTwoInitialsCleared = subscribedCasesGridPage().verifySignedOutByPopulated(caseTwoSerialNumber, "");
	
		//Find a "reported" case
		subscribedCasesPage().filterForReportedOrUnreportedCase("R");
	
		String reportedCaseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		subscribedCasesPage().rightClickCase(reportedCaseSerialNumber);
		boolean reportedCasePropertiesWindowOpened = subscribedCasesContextMenu().openPropertiesOnCaseSerialNumber();
		subscribedCasesPropertiesPage().clickCancel();
		boolean reportedCasePropertiesWindowClosed = !subscribedCasesPage().isPropertiesWindowClosed();
		
		//Find an "unreported" case
		subscribedCasesWindowAppeared = subscribedCasesPage().switchToSubscribedCasesPage();
		Assertions.assertTrue(subscribedCasesWindowAppeared, "The Subscribed Cases page did not appear");
		subscribedCasesPage().filterForReportedOrUnreportedCase("U");
	
		//Unreported cases do not appear for Legal
		String unreportedCaseSerialNumber = subscribedCasesPage().getCaseSerialNumberFromFirstUnreportedSubscribedCase();
		subscribedCasesPage().rightClickCase(unreportedCaseSerialNumber);
		boolean unreportedCasePropertiesWindowOpened = subscribedCasesContextMenu().openPropertiesOnCaseSerialNumber();
		subscribedCasesPropertiesPage().clickCancel();
		boolean unreportedCasePropertiesWindowClosed = !subscribedCasesPage().isPropertiesWindowClosed();
	
		Assertions.assertAll
		(
			() -> Assertions.assertTrue(caseOneInitialsPopulated, "Case One: 'Signed Out By' field failed to populate with user initials upon case sign in."),
			() -> Assertions.assertTrue(caseOneInitialsCleared, "Case One: 'Signed Out By' field failed to clear user initials upon case sign out."),
			() -> Assertions.assertTrue(caseTwoInitialsPopulated, "Case Two: 'Signed Out By' field failed to populate with user initials upon case sign in."),
			() -> Assertions.assertTrue(caseTwoInitialsCleared, "Case Two: 'Signed Out By' field failed to clear user initials upon case sign out."),
			() -> Assertions.assertTrue(reportedCasePropertiesWindowOpened, "Reported Case: The properties window failed to OPEN."),
			() -> Assertions.assertTrue(reportedCasePropertiesWindowClosed, "Reported Case: The properties window failed to close."),
			() -> Assertions.assertTrue(unreportedCasePropertiesWindowOpened, "Unreported Case: The properties window failed to OPEN."),
			() -> Assertions.assertTrue(unreportedCasePropertiesWindowClosed, "Unreported Case: The properties window failed to close.")
		);
    }
}
