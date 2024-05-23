package com.thomsonreuters.codes.codesbench.quality.tests.smoke.section.contextmenu;

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

public class SectionContextMenuEditLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, it enters the editor using the Context Menu and adds text to the first Text Paragraph.
     *           Checks it in, and then verifies the text was saved.
     *           Finally, it restores it to the original Baseline  <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void editSectionLegalTest()
    {
        //TODO editor doesn't check in

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2012");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().unlockFirstRendition();
        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Click first section and open the editor
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().editSection();
        editorPage().switchToEditorTextFrame();

        //Add text to first text paragraph and check in
        editorTextPage().insertPhraseInTextParagraphWithGivenNumber(" UniqueText ", 1);
        editorPage().closeAndCheckInChangesAndHandleSpellcheckWindowIfAppeared();
        editorPage().waitForEditorToClose();
        sourcePage().switchToSectionNavigatePage();

        //Open first section and editor
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().editSection();
        editorPage().switchToEditorTextFrame();

        //Check text was checked in
        boolean isUniqueTextIsSaved = editorTextPage().doesFirstTextParagraphContainGivenText("UniqueText");
        Assertions.assertTrue(isUniqueTextIsSaved, "Text appears in paragraph");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, it enters the Section Notes editor using the Context Menu and adds a note.
     *           Next the test verifies the note appears in the source grid.
     *           Finally, the test returns to the Section Notes editor and clears the note and verifies it was cleared in the source grid <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void notesLegalTest()
    {
        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Open first section and editor
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionNotesEditor();

        //Verify Instruction Notes header is correct
        boolean instructionNotesHeaderIsCorrect = instructionsNotesPage().getInstructionNoteHeader().equals("Section Instruction Note");

        instructionsNotesPage().addSectionLevelInstruction("Testing");
        instructionsNotesPage().clickSaveButtonOnForm();

        boolean isInstructionNoteSet = sourceNavigateGridPage().getInstructionNoteOfFirstItem().equals("Testing");

        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionNotesEditor();
        instructionsNotesPage().addSectionLevelInstruction("");
        instructionsNotesPage().clickSaveButtonOnForm();

        boolean isInstructionNoteCleared = sourceNavigateGridPage().getInstructionNoteOfFirstItem().equals("");

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(instructionNotesHeaderIsCorrect, "Instruction Note header was incorrect"),
            () -> Assertions.assertTrue(isInstructionNoteSet, "Instruction Note in the grid does not match"),
            () ->  Assertions.assertTrue(isInstructionNoteCleared, "Instruction Note in the grid was not cleared")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, the test clears the current effective date, then enters the effective date page using the Context menu.
     *           Next the test sets the effective date to today and checks the 'Run Cite Locate To Update Integration Status' checkbox.
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

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Open first section and editor
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionEffectiveDateEditor();

        //Switch to Effective Date page, set date, check checkbox, and save
        sectionEffectiveDatePage().setEffectiveDate(currentDate);
        sectionEffectiveDatePage().checkRunCiteLocateToUpdateIntegrationStatusCheckbox();
        sectionEffectiveDatePage().clickSave();
        sourceNavigateGridPage().waitForGridRefresh();

        //Uses a different method of getting current date to match the date formatting seen in the grid
        boolean effectiveDateIsSet = sourceNavigateGridPage().getEffectiveDateOfFirstItem().equals(currentDate);
        Assertions.assertTrue(effectiveDateIsSet, "Effective date does not match set date");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, it enters the Integration Properties pop up using the Context Menu and sets the Effective Date as today's date.
     *           Finally, the test verifies that the effective date is updated in the source grid<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrationPropertiesLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Open first section and editor
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openSectionIntegrationPropertiesEditor();
        integrationPropertiesPage().setEffectiveDate(currentDate);
        integrationPropertiesPage().clickSave();

        boolean effectiveDateWasSet = sourceNavigateGridPage().getEffectiveDateOfFirstItem().equals(currentDate);
        Assertions.assertTrue(effectiveDateWasSet, "Effective date was not set");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - For a Section, it enters the Difficulty Level pop up using the Context Menu and sets the difficulty level.
     *           Next, the test verifies that the difficulty level is updated in the source grid.
     *           Finally, the reopens the Difficulty Level pop up, clears the difficulty level and verifies is was cleared in the source grid<br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void difficultyLevelLegalTest()
    {
        String difficultyLevel = "E1";

        //Login and go to source navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        sourceNavigateGridPage().clickFirstRendition();
        sourceNavigateTabsPage().goToSectionTab();

        //Open first section and editor
        sourceNavigateGridPage().rightClickFirstSection();
        sectionContextMenu().openDifficultyLevelEditor();
        difficultyLevelPage().selectDifficultyLevel(difficultyLevel);
        difficultyLevelPage().clickSave();

        boolean difficultyLevelWasSet = sourceNavigateGridPage().getDifficultyLevelOfFirstItem().equals(difficultyLevel);
        Assertions.assertTrue(difficultyLevelWasSet, "Difficulty level was not set");
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
