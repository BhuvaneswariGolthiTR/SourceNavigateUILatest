package com.thomsonreuters.codes.codesbench.quality.tests.smoke.delta.contextmenu;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
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
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

import java.sql.Connection;

import static com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils.disconnect;

public class DeltaContextMenuEditLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks that edits made to deltas through the context menu are saved <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void editLegalTest()
    {
        String text = " test ";
        //TODO mocked data doesn't have a subsection text paragraph

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter for the document: Validation: None, Duplicates: None, Deleted: Not Deleted, Year: 2012,
        // Content Set: Iowa(Development), Rendition Status: APV, Doc Number: 0020
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear("2020");
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber("310");
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //unlock rendition if needed
        sourceNavigateGridPage().unlockFirstRendition();

        //click first rendition and go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();

        //click first rendition and edit delta using context menu
        sourceNavigateGridPage().firstDeltaEditContent();
        editorPage().switchToEditorTextFrame();

        //write in word and check in
        editorTextPage().insertPhraseInSubsectionTextParagraphWithGivenNumber(text, 1);
        editorPage().closeAndCheckInChangesAndHandleSpellcheckWindowIfAppeared();
        editorPage().waitForEditorToClose();

        //reopen document and test is word was saved
        sourcePage().switchToDeltaNavigatePage();
        sourceNavigateGridPage().firstDeltaEditContent();
        editorPage().switchToEditorTextFrame();
        boolean newWordIsSaved = editorTextPage().doesFirstSubsectionParagraphWithParaTextContainGivenText(text, 1);
        editorPage().closeDocumentWithNoChanges();
        Assertions.assertTrue(newWordIsSaved, "New word was saved");

        //view rendition baseline and restore
        sourcePage().switchToDeltaNavigatePage();
        sourcePage().goToRenditionTab();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewRenditionBaselines();
        viewBaselinesNavigatePage().clickNumberSort();
        viewBaselinesNavigatePage().rightClickOriginalBaseline();
        viewBaselinesContextMenu().restoreBaseline();
        sourcePage().switchToSourceNavigatePage();
        viewBaselinesNavigatePage().switchToViewBaselinesPage();
        viewBaselinesNavigatePage().clickNumberSort();
        boolean restored = viewBaselinesNavigatePage().getCurrentBaselineDescription().equals("Restored from 0");
        Assertions.assertTrue(restored, "Baseline was restored");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies Instruction Note appears after adding to delta notes using context menu <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void editNotesLegalTest()
    {
        String note = "Testing" + (DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss());

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //click first rendition and go to delta tab
        sourceNavigateGridPage().clickFirstRendition();
        sourcePage().goToDeltaTab();

        //click first delta and edit notes using context menu
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToEditDeltaNotes();

        //add note and verify it is added
        instructionsNotesPage().addDeltaNotes(note);
        instructionsNotesPage().clickSave();
        sourcePage().switchToDeltaNavigatePage();
        sourcePage().waitForGridRefresh();
        boolean instructionNoteExists = sourceNavigateGridPage().getInstructionNoteOfFirstItem().equals(note);
        Assertions.assertTrue(instructionNoteExists, "Instruction Note appears on grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies Effective date of delta is edited using context menu <br>
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

        //click first delta and edit notes using context menu
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToEditEffectiveDate();

        //Set effective date an run cite locate
        effectiveDatePage().setCurrentDateInEffectiveDateCalender();
        effectiveDatePage().checkRunCiteLocate();
        effectiveDatePage().clickSave();

        //check date is correct in grid
        boolean effectiveDateAppears = sourceNavigateGridPage().getEffectiveDateOfFirstItem().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        Assertions.assertTrue(effectiveDateAppears, "Effective Date appears in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies Effective Date in Integration Properties is able to be edited using context menu <br>
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

        //click first delta and edit notes using context menu
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToEditIntegrationProperties();

        //Set effective date an run cite locate
        integrationPropertiesPage().setEffectiveDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        integrationPropertiesPage().clickSave();

        //check date is correct in grid
        boolean effectiveDateAppears = sourceNavigateGridPage().getEffectiveDateOfFirstItem().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        Assertions.assertTrue(effectiveDateAppears, "Effective Date appears in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies Difficulty Level is able to be changed using context menu <br>
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

        //edit difficulty level
        sourceNavigateGridPage().rightClickFirstDelta();
        deltaContextMenu().goToEditDifficultyLevel();
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
