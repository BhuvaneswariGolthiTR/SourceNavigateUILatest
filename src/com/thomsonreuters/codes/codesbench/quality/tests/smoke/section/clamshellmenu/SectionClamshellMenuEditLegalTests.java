package com.thomsonreuters.codes.codesbench.quality.tests.smoke.section.clamshellmenu;

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

public class SectionClamshellMenuEditLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, it enters the Integration Properties pop up using the Clamshell Menu.
     *           Sets the Effective Date, Effective Date Provision, Effective Comments, Instruction Notes, Miscellaneous Text, and adds it as a Query Date.
     *           Finally, the test verifies that the values appear in the source grid<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrationPropertiesLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and open Integration Properties page
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarEdit();
        sectionTabEditClamshellPage().clickIntegrationProperties(true, false);

        //Test + current date and time to diversify each test
        String effectiveDateProvisionText = "dateProvision" + currentDate;
        String effectiveCommentsText = "effectiveComments" + currentDate;
        String instructionNoteText = "instructionNote" + currentDate;
        String miscellaneousText = "miscellaneous" + currentDate;

        //Set Integration Properties fields and save
        integrationPropertiesPage().setEffectiveDate(currentDate);
        integrationPropertiesPage().setEffectiveDateProvision(effectiveDateProvisionText);
        integrationPropertiesPage().setEffectiveComments(effectiveCommentsText);
        integrationPropertiesPage().setQueryDate(currentDate);
        integrationPropertiesPage().checkAddAsDateQueryCheckbox();
        integrationPropertiesPage().setInstructionNote(instructionNoteText);
        integrationPropertiesPage().setMiscellaneous(miscellaneousText);
        integrationPropertiesPage().clickSave();

        //Comparing set values to what we see in the source navigate grid
        boolean instructionNoteIsSet = sourceNavigateGridPage().getInstructionNoteOfFirstItem().equals(instructionNoteText);
        boolean effectiveDateIsSet = sourceNavigateGridPage().getEffectiveDateOfFirstItem().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        boolean effectiveDateProvisionIsSet = sourceNavigateGridPage().getEffectiveDateProvisionOfFirstItem().equals(effectiveDateProvisionText);
        boolean effectiveCommentsIsSet = sourceNavigateGridPage().getEffectiveCommentsOfFirstItem().equals(effectiveCommentsText);
        boolean queryDateIsSet = sourceNavigateGridPage().getQueryDateOfFirstItem().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        boolean wasAddedAsQueryDate = sourceNavigateGridPage().wasAddedAsQueryDate();
        boolean miscellaneousIsSet = sourceNavigateGridPage().getMiscellaneousOfFirstItem().equals(miscellaneousText);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(instructionNoteIsSet, "Instruction text matches set text"),
            () -> Assertions.assertTrue(effectiveDateIsSet, "Effective date matches set date"),
            () -> Assertions.assertTrue(effectiveDateProvisionIsSet, "Provision text matches set text"),
            () -> Assertions.assertTrue(effectiveCommentsIsSet, "Comments match set text"),
            () -> Assertions.assertTrue(queryDateIsSet, "Query date matches set date"),
            () -> Assertions.assertTrue(wasAddedAsQueryDate, "Query date matches set date"),
            () -> Assertions.assertTrue(miscellaneousIsSet, "Miscellaneous text matches set text")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test clears the current effective date, then enters the effective date page using the Clamshell menu.
     *           Sets the effective date to today and checks the 'Run Cite Locate To Update Integration Status checkbox.
     *           Finally, the test verifies the new effective date appears in the source grid<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void effectiveDateLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and open Section Properties page
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionProperties();

        //Pre-Condition
        //Clear Effective Date textbox and save
        boolean textboxWasEmptied = sectionPropertiesPage().clearSectionEffectiveDate();
        Assertions.assertTrue(textboxWasEmptied, "Textbox was cleared before test starts");
        sectionPropertiesPage().clickSave();

        //Open Effective Date popup using the clamshell menu
        sourceNavigateGridPage().clickFirstSection();
        clamshellPage().openSideBarEdit();
        sectionTabEditClamshellPage().clickEffectiveDate(true, false);

        //Switch to Effective Date page, set date, check checkbox, and save
        sectionEffectiveDatePage().setEffectiveDate(currentDate);
        sectionEffectiveDatePage().checkRunCiteLocateToUpdateIntegrationStatusCheckbox();
        sectionEffectiveDatePage().clickSave();

        boolean effectiveDateIsSet = sourceNavigateGridPage().getEffectiveDateOfFirstItem().equals(currentDate);
        Assertions.assertTrue(effectiveDateIsSet, "Effective date matches date set in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test clears the current difficulty level, then enters the difficulty level page using the Clamshell menu.
     *           Sets the difficulty level, after setting the difficulty level it is then cleared
     *           Finally, the test verifies the difficulty level was both set and cleared correctly <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void difficultyLevelLegalTest()
    {
        String difficultyLevel = "E1";

        //Login and go to Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();
        sourceNavigateGridPage().clickFirstSection();

        //Verify difficulty was cleared
        boolean difficultyLevelIsEmptyToStart = sourceNavigateGridPage().getDifficultyLevelOfFirstItem().equals("");
        Assertions.assertTrue(difficultyLevelIsEmptyToStart, "Difficulty level was empty to start test");

        //Sets the difficulty level to E1
        clamshellPage().openSideBarEdit();
        sectionTabEditClamshellPage().clickDifficultyLevel(true, false);
        difficultyLevelPage().selectDifficultyLevel(difficultyLevel);
        difficultyLevelPage().clickSave();
        boolean difficultyLevelWasSet = sourceNavigateGridPage().getDifficultyLevelOfFirstItem().equals(difficultyLevel);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(difficultyLevelWasSet, "Difficulty level was set in grid")
        );
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
