package com.thomsonreuters.codes.codesbench.quality.tests.smoke.delta.clamshellmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.source.SourceDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.CommonDataMocking;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.SourceDataMockingNew;
import com.thomsonreuters.codes.codesbench.quality.utilities.datamocking.source.datapod.SourceDatapodObject;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class DeltaClamshellMenuEditLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies correct Instruction Note appears in grid after adding to delta notes using clamshell menu <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void editNotesLegalTest()
    {
        String note = "Testing" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();

        //click first delta and go to Edit -> Notes using clamshell menu
        sourceNavigateGridPage().clickFirstDelta();
        clamshellPage().openSideBarEdit();
        deltaTabEditClamshellPage().clickNotes(true,false);

        //add note and verify it is added
        instructionsNotesPage().addDeltaNotes(note);
        instructionsNotesPage().clickSave();
        sourcePage().switchToDeltaNavigatePage();
        sourcePage().waitForGridRefresh();
        boolean instructionNoteExists = sourceNavigateGridPage().getInstructionNoteOfFirstItem().equals(note);
        Assertions.assertTrue(instructionNoteExists, "Instruction Note appears in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Sets delta's effective date to current date and checks it is updated correctly in the grid <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void effectiveDateLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();

        //click first delta and edit effective date using clamshell menu
        sourceNavigateGridPage().clickFirstDelta();
        clamshellPage().openSideBarEdit();
        deltaTabEditClamshellPage().clickEffectiveDate(true,false);

        //Set effective date and run cite locate
        effectiveDatePage().setCurrentDateInEffectiveDateCalender();
        effectiveDatePage().checkRunCiteLocate();
        effectiveDatePage().clickSave();

        //check date is correct in grid
        boolean effectiveDateIsTodayInGrid = sourceNavigateGridPage().getEffectiveDateOfFirstItem().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        Assertions.assertTrue(effectiveDateIsTodayInGrid, "Effective Date is today in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Sets Effective Date in Integration Properties and checks date is updated in the grid <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrationPropertiesLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();

        //click first delta and edit integration properties using clamshell menu
        sourceNavigateGridPage().clickFirstDelta();
        clamshellPage().openSideBarEdit();
        deltaTabEditClamshellPage().clickIntegrationProperties(true,false);

        //Set effective date an run cite locate
        integrationPropertiesPage().setEffectiveDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        integrationPropertiesPage().clickSave();

        //check date is correct in grid
        boolean effectiveDateIsTodayInGrid = sourceNavigateGridPage().getEffectiveDateOfFirstItem().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        Assertions.assertTrue(effectiveDateIsTodayInGrid, "Effective Date is today in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Changes difficulty level and checks it is updated correctly in grid <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void difficultyLevelLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();

        //set difficulty level to E1
        sourceNavigateGridPage().clickFirstDelta();
        deltaTabEditClamshellPage().clickDifficultyLevel(true,false);
        difficultyLevelPage().selectDifficultyLevel("E1");
        difficultyLevelPage().clickSave();
        boolean difficultyLevelAdded = sourceNavigateGridPage().getDifficultyLevelOfFirstItem().equals("E1");
        Assertions.assertTrue(difficultyLevelAdded,"Difficulty Level is added");
    }

    @BeforeEach
    public void mockData()
    {
        datapodObject = SourceDataMockingNew.Iowa.Small.APV.insert();
        renditionUuid = datapodObject.getRenditions().get(0).getRenditionUUID();
        connection = CommonDataMocking.connectToDatabase(environmentTag);
    }

    @AfterEach
    public void deleteMockedData()
    {
        if(datapodObject != null)
        {
            sourceNavigateGridPage().unlockRenditionWithUuid(renditionUuid);
            datapodObject.delete();
        }
        disconnect(connection);
    }
}
