package com.thomsonreuters.codes.codesbench.quality.tests.nod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;

public class OpenCasesTests extends TestService 
{
	/**
	 * STORY/BUGS - NA &lt;br&gt;
	 * SUMMARY -  Checks the functionality of switching content sets in the Cases page&lt;br&gt;
	 * USER -  Legal &lt;br&gt;
	 */
	@Test
	@LEGAL
	@LOG
	public void openCasesTest()
	{
		String noTeamCases = "NoTeamCases";
		String maine = "Maine (Development)";

		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean casesWindowOpened = nodMenu().goToCases();
		Assertions.assertTrue(casesWindowOpened, "The cases window never opened.");
		
		boolean defaultContentSetTeamIsNoTeam = listOfCasesPage().getCurrentlyViewingCasesFor().equals(String.format("Currently viewing cases for: %s", noTeamCases));
		listOfCasesPage().setContentSetTeam(maine);
		listOfCasesPage().clickGoButton();
		boolean contentSetTeamChangedToMaine = listOfCasesPage().getCurrentlyViewingCasesFor().equals(String.format("Currently viewing cases for: %s", maine));
		
		Assertions.assertAll
		(
			() -> Assertions.assertTrue(defaultContentSetTeamIsNoTeam, "The default case set is not 'NoTeamCases'."),
			() -> Assertions.assertTrue(contentSetTeamChangedToMaine, "Maine (Developement) cases loaded")
		);
	}
}
