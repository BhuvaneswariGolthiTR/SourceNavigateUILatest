package com.thomsonreuters.codes.codesbench.quality.tests.source.bts;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

public class GeneratingAndListingLegislativeServiceTablesTests extends TestService 
{
	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY -  This test check the Legislative Service Table works properly. checks that a 
	 * table can be added properly. Afterwards it check to make sure it the generate table, 
	 * as well as edit, delete, and download buttons work properly. <br>
	 * USER - Legal <br>
	 * @return - void  <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void generatingAndListingLegislativeServiceTablesTest()
    {
		String pamphletNumber = "7";
		String tableNumber = "3";
		String iowaJurisidcion = "IA";
		String currentYear = DateAndTimeUtils.getCurrentYearyyyy();
		String legislativeServicePamphletID = String.format("IAT0%sP0%sY%s", tableNumber,pamphletNumber,currentYear);
		
		homePage().goToHomePage();
		loginPage().logIn();
		
		boolean btsWindowAppeared = sourceMenu().goToSourceC2012BTS();
		Assertions.assertTrue(btsWindowAppeared);
		// set jurisdiction to IA
		btsWebUiPage().setJurisdictionDropdown(iowaJurisidcion);
		// Navigate to the Generate Legislative Service Tables window
		btsWebUiPage().goToTasksTablesGenerateLegislativeServiceTables();

		// check whether the window was opened and switched to. To be asserted
		generateLegislativeServiceTablesPage().setTableNumberDropdown(tableNumber);
		generateLegislativeServiceTablesPage().setPamphletNumber(pamphletNumber);
		generateLegislativeServiceTablesPage().setLegislativeYear(currentYear);
		generateLegislativeServiceTablesPage().addNewWestNumberForRow(1);
		
		//adding To and From values to the west number
		generateLegislativeServiceTablesPage().setWestNumberFrom("1", "1");
		generateLegislativeServiceTablesPage().setWestNumberTo("1", "2");
		
		//adding To and From values for second west number
		generateLegislativeServiceTablesPage().setWestNumberFrom("2", "30");
		generateLegislativeServiceTablesPage().setWestNumberTo("2", "50");
		
		//Generate PDF  
		boolean succesfullyGeneratedTable = generateLegislativeServiceTablesPage().generateTable();
		Assertions.assertTrue(succesfullyGeneratedTable, "The text 'Successfully generated table' did not appear");
		generateLegislativeServiceTablesPage().clickOnListTab();
		
		boolean downloadSuccessful = listLegislativeServiceTablesPage().downloadLegislativeServiceTable(legislativeServicePamphletID);
		Assertions.assertTrue(downloadSuccessful, "The download should return 200 but something else.");
		
		boolean textBeforeAndAfterEditMatch = listLegislativeServiceTablesPage().editLegislativeServicePamphlet(legislativeServicePamphletID, "test");
		Assertions.assertTrue(textBeforeAndAfterEditMatch, "The text before edit and after edit should match but did not.");
		
		generateLegislativeServiceTablesPage().clickOnListTab();
		
		boolean isPamphletDeleted = listLegislativeServiceTablesPage().deleteLegislativeServicePamphlet(legislativeServicePamphletID);
		Assertions.assertTrue(isPamphletDeleted, "The pamphlet still appears in the table when it shouldn't after delete.");
    }
}
