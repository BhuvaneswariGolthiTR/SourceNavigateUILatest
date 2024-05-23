package com.thomsonreuters.codes.codesbench.quality.tests.smoke.rendition.contextmenu;

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

public class RenditionContextMenuEditLegalTests extends TestService
{
    SourceDatapodObject datapodObject;
    Connection connection;
    String renditionUuid;

    private void filterRendition(String year, String docNumber)
    {
        sourceNavigateFiltersAndSortsPage().setFilterValidation("None");
        sourceNavigateFiltersAndSortsPage().setFilterMultipleDuplicate("None");
        sourceNavigateFiltersAndSortsPage().setFilterDeleted("Not Deleted");
        sourceNavigateFiltersAndSortsPage().setFilterContentSet("Iowa (Development)");
        sourceNavigateFiltersAndSortsPage().setFilterYear(year);
        sourceNavigateFiltersAndSortsPage().setFilterRenditionStatus("APV");
        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(docNumber);
        sourceNavigateFooterToolsPage().refreshTheGrid();
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks Rendition Notes can be edited and saved using context menu <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void notesLegalTest()
    {
        String test = "TEST";

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //unlock rendition if needed
        sourceNavigateGridPage().unlockFirstRendition();

        //right click first rendition and select Edit -> Rendition Notes
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean instructionNotesWindowAppears = renditionContextMenu().editRenditionNotes();
        Assertions.assertTrue(instructionNotesWindowAppears, "Instruction Notes window appears");

        //add text to the notes and check they appear in the grid
        instructionsNotesPage().clearRenditionNotes();
        instructionsNotesPage().addRenditionNotes(test);
        instructionsNotesPage().clickSave();
        sourcePage().switchToSourceNavigatePage();
        sourcePage().waitForGridRefresh();
        sourceNavigateGridPage().clickFirstRendition();
        boolean noteIsSaved = sourceNavigateGridPage().getInstructionNoteOfFirstItem().equals(test);

        //clear rendition notes
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().editRenditionNotes();
        instructionsNotesPage().clearRenditionNotes();
        instructionsNotesPage().clickSave();
        sourcePage().switchToSourceNavigatePage();
        sourcePage().waitForGridRefresh();
        sourceNavigateGridPage().clickFirstRendition();
        boolean noteIsDeleted = sourceNavigateGridPage().getInstructionNoteOfFirstItem().isEmpty();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(noteIsSaved, "Note is saved"),
            () -> Assertions.assertTrue(noteIsDeleted, "Note is deleted")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Go to Edit -> Source End using context menu and check editor window appears <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sourceEndLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //unlock rendition if needed
        sourceNavigateGridPage().unlockFirstRendition();

        //right click first rendition and select Edit -> Source End
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean editorWindowAppears = renditionContextMenu().editSourceEnd();
        editorPage().closeDocumentWithNoChanges();
        Assertions.assertTrue(editorWindowAppears, "Common Editor window appears");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Go to Edit -> Integration Properties using context menu and check edits appear in grid <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void integrationPropertiesLegalTest()
    {
        String effectiveDateProvision = "Test1";
        String effectiveComments = "Test2";
        String queryNote = "Test3";
        String instructionNote = "Test4";
        String miscellaneous = "Test5";
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //unlock rendition if needed
        sourceNavigateGridPage().unlockFirstRendition();

        //right click first rendition and select Edit -> Source End
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean integrationPropertiesWindowAppears = renditionContextMenu().editIntegrationProperties();
        Assertions.assertTrue(integrationPropertiesWindowAppears, "Integration Properties window appears");
        integrationPropertiesPage().setEffectiveDate(currentDate);
        integrationPropertiesPage().checkDefaultCheckbox();
        integrationPropertiesPage().setEffectiveDateProvision(effectiveDateProvision);
        integrationPropertiesPage().setEffectiveComments(effectiveComments);
        integrationPropertiesPage().setQueryNote(queryNote);
        integrationPropertiesPage().setQueryDate(currentDate);
        integrationPropertiesPage().checkAddAsDateQueryCheckbox();
        integrationPropertiesPage().setInstructionNote(instructionNote);
        integrationPropertiesPage().setMiscellaneous(miscellaneous);
        integrationPropertiesPage().clickSave();

        //check integration properties are correct in grid
        boolean effectiveDateProvisionInGrid = sourceNavigateGridPage().getEffectiveDateProvisionOfFirstItem().equals(effectiveDateProvision);
        boolean effectiveCommentsInGrid = sourceNavigateGridPage().getEffectiveCommentsOfFirstItem().equals(effectiveComments);
        boolean instructionNoteInGrid = sourceNavigateGridPage().getInstructionNoteOfFirstItem().equals(instructionNote);
        boolean miscellaneousInGrid = sourceNavigateGridPage().getMiscellaneousOfFirstItem().equals(miscellaneous);
        boolean effectiveDate = sourceNavigateGridPage().getEffectiveDateOfFirstRendition().equals(currentDate);
        boolean queryDate = sourceNavigateGridPage().getQueryDateOfFirstItem().equals(currentDate);

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(effectiveDateProvisionInGrid, "Effective Date Provision is " + effectiveDateProvision),
            () -> Assertions.assertTrue(effectiveCommentsInGrid, "Effective Comments is " + effectiveComments),
            () -> Assertions.assertTrue(instructionNoteInGrid, "Instruction Note is " + instructionNote),
            () -> Assertions.assertTrue(miscellaneousInGrid, "Miscellaneous is " + miscellaneous),
            () -> Assertions.assertTrue(effectiveDate, "Effective Date is today's date"),
            () -> Assertions.assertTrue(queryDate, "Query Date is today's date")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Verifies Raw XML Editor validates text properly and text can be saved using context menu <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void xmlLegalTest() {
        String text = "official.status=\"APV\" year=\"2021\"";
        String test = "<!-- This is test abracadabra -->";

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //right click first rendition and select Edit -> Rendition XML
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean rawXmlEditorWindowAppears = renditionContextMenu().editRenditionXml();
        Assertions.assertTrue(rawXmlEditorWindowAppears, "Raw XML Editor window appears");

        //check if given text is in editor. Enter given text into editor and check it is there. Validate text
        boolean textInXmlEditorAppears = sourceRawXmlEditorPage().isGivenTextInEditor(text);
        sourceRawXmlEditorPage().sendTextToXmlEditor(test);
        boolean newTextInEditorAppears = sourceRawXmlEditorPage().isGivenTextInEditor(test);
        boolean xmlEditorValid = sourceRawXmlEditorPage().clickValidate();

        //save and don't check in changes and check editor window is still open and populated
        sourceRawXmlEditorPage().clickSave();
        sourceRawXmlDocumentClosurePage().clickCancel();
        boolean xmlEditorStaysOpen = sourceRawXmlEditorPage().isGivenTextInEditor(text);

        //save and check in changes
        sourceRawXmlEditorPage().clickSave();
        sourceRawXmlDocumentClosurePage().clickCheckIn();

        //re-open XML editor and check changes were saved
        sourcePage().switchToSourceNavigatePage();
        sourcePage().waitForGridRefresh();
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().editRenditionXml();
        boolean textInXmlEditorSaved = sourceRawXmlEditorPage().isGivenTextInEditor(text);
        boolean newTextInEditorSaved = sourceRawXmlEditorPage().isGivenTextInEditor(test);

        //send invalid text to editor, validate, and close
        sourceRawXmlEditorPage().sendTextToXmlEditor("This is invalid test abracadabra -->");
        boolean xmlEditorValidated = sourceRawXmlEditorPage().clickValidate();
        sourceRawXmlEditorPage().clickClose();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(textInXmlEditorAppears, "Raw XML Editor is populated"),
            () -> Assertions.assertTrue(newTextInEditorAppears, "User can enter valid text into XML Editor"),
            () -> Assertions.assertTrue(xmlEditorValid, "Valid text in XML Editor validated"),
            () -> Assertions.assertTrue(xmlEditorStaysOpen, "XML Editor stays open"),
            () -> Assertions.assertTrue(textInXmlEditorSaved, "Raw XML Editor is populated again"),
            () -> Assertions.assertFalse(newTextInEditorSaved, "Not saved new text did not appear in the XML editor"),
            () -> Assertions.assertFalse(xmlEditorValidated, "Invalid text in Rendition XML has not been validated")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks all functionality in the Index Entry tab of Edit -> Topical Heading/Index Entry Features page <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void indexEntryLegalTest()
    {
        String phrase1 = "abandonment";
        String phrase2 = "test";

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //unlock rendition if needed
        sourceNavigateGridPage().unlockFirstRendition();

        //right click first rendition and select Edit -> Topical Heading/Index Entry Features and go to Index Entry tab
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean topicalHeadingHighlightWindowAppears = renditionContextMenu().editTopicalHeadingIndexEntryFeatures();
        Assertions.assertTrue(topicalHeadingHighlightWindowAppears, "Topical Heading/Highlight window appears");
        topicalHeadingHighlightPage().goToIndexEntryTab();

        //move 'abandonment' to selected phrase list and check that it is moved
        topicalHeadingHighlightPage().movePhraseToSelectedPhraseList(phrase1);
        boolean phrase1Moved = topicalHeadingHighlightPage().isPhraseInSelectedList(phrase1);
        Assertions.assertTrue(phrase1Moved, phrase1 + " is added to Selected Phrase list");

        //make user added word and check it is added
        topicalHeadingHighlightPage().addUserPhrase();
        userPhrasePage().enterPhrase(phrase2);
        userPhrasePage().clickSubmit();
        boolean phrase2AddedAndMoved = topicalHeadingHighlightPage().isPhraseInSelectedList(phrase2);
        Assertions.assertTrue(phrase2AddedAndMoved, phrase2 + " is added to Selected Phrase list");

        //move phrase2 up in list
        topicalHeadingHighlightPage().movePhraseUp(phrase2);
        boolean phraseMovedUp = topicalHeadingHighlightPage().getSelectedPhraseIndex(phrase2) == 0;

        //move phrase2 down in list
        topicalHeadingHighlightPage().movePhraseDown(phrase2);
        boolean phraseMovedDown = topicalHeadingHighlightPage().getSelectedPhraseIndex(phrase2) == 1;

        //delete phrase1 and check it is removed from list
        topicalHeadingHighlightPage().deletePhrase(phrase1);
        boolean phrase1AppearsInSelectedList = topicalHeadingHighlightPage().isPhraseInSelectedList(phrase1);
        Assertions.assertFalse(phrase1AppearsInSelectedList, phrase1 + " appears in Selected Phrase list after being deleted");

        //create index entry and check it appears in Index Entry list
        topicalHeadingHighlightPage().createIndexEntry(phrase2);
        boolean phraseIsAddedToIndexEntryList = topicalHeadingHighlightPage().isPhraseInIndexEntryList(phrase2);
        Assertions.assertTrue(phraseIsAddedToIndexEntryList, phrase2 + " is added to Index Entry list");

        //delete index entry and check it is removed from Index Entry list
        topicalHeadingHighlightPage().deleteIndexEntry(phrase2);
        boolean phraseIRemovedFromIndexEntryList = topicalHeadingHighlightPage().isPhraseInIndexEntryList(phrase2);
        Assertions.assertFalse(phraseIRemovedFromIndexEntryList, phrase2 + " appears in Index Entry list after being deleted");

        //delete phrase2 from Selected Phrases list, click Ok, and don't mark entry as completed
        topicalHeadingHighlightPage().deletePhrase(phrase2);
        topicalHeadingHighlightPage().clickOk();
        markIndexEntryPage().clickNo();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(phraseMovedUp, phrase2 + " moved up in Selected Phrases List"),
            () -> Assertions.assertTrue(phraseMovedDown, phrase2 + " moved down in Selected Phrases List")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Checks all functionality in the Topical Heading tab of Edit -> Topical Heading/Index Entry Features page <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void topicalHeadingLegalTest()
    {
        String phrase1 = "abandonment";
        String phrase2 = "test" + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmm();

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //unlock rendition if needed
        sourceNavigateGridPage().unlockFirstRendition();

        //right click first rendition and select Edit -> Topical Heading/Index Entry Features
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean topicalHeadingHighlightWindowAppears = renditionContextMenu().editTopicalHeadingIndexEntryFeatures();
        Assertions.assertTrue(topicalHeadingHighlightWindowAppears, "Topical Heading/Highlight window appears");

        //move phrase1 to selected phrase list and check that it is moved
        topicalHeadingHighlightPage().movePhraseToSelectedPhraseList(phrase1);
        boolean phrase1Moved = topicalHeadingHighlightPage().isPhraseInSelectedList(phrase1);
        Assertions.assertTrue(phrase1Moved, phrase1 + " is added to Selected Phrase list");

        //make user added word and check it is added
        topicalHeadingHighlightPage().addUserPhrase();
        userPhrasePage().enterPhrase(phrase2);
        userPhrasePage().clickSubmit();
        boolean phrase2AddedAndMoved = topicalHeadingHighlightPage().isPhraseInSelectedList(phrase2);
        Assertions.assertTrue(phrase2AddedAndMoved, phrase2 + " is added to Selected Phrase list");

        //move phrase2 up in list
        topicalHeadingHighlightPage().movePhraseUp(phrase2);
        boolean phraseMovedUp = topicalHeadingHighlightPage().getSelectedPhraseIndex(phrase2) == 0;

        //move phrase2 down in list
        topicalHeadingHighlightPage().movePhraseDown(phrase2);
        boolean phraseMovedDown = topicalHeadingHighlightPage().getSelectedPhraseIndex(phrase2) == 1;

        //delete phrase1, check it is removed from list and click cancel
        topicalHeadingHighlightPage().deletePhrase(phrase1);
        boolean phrase1AppearsInSelectedList = topicalHeadingHighlightPage().isPhraseInSelectedList(phrase1);
        Assertions.assertFalse(phrase1AppearsInSelectedList, phrase1 + " appears in Selected Phrase list after being deleted");
        topicalHeadingHighlightPage().clickCancel();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(phraseMovedUp, phrase2 + " moved up in Selected Phrases List"),
            () -> Assertions.assertTrue(phraseMovedDown, phrase2 + " moved down in Selected Phrases List")
        );
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Go to Edit -> Source Front using context menu and check editor window appears <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sourceFrontLegalTest()
    {
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //right click first rendition and select Edit -> Source Front
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean editorWindowAppears = renditionContextMenu().editSourceFront();
        editorPage().closeDocumentWithNoChanges();
        Assertions.assertTrue(editorWindowAppears, "Common Editor window appears");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Go to Edit -> Effective Date using context menu, set effective date to current date, and check value is updated in grid <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void effectiveDateLegalTest()
    {
        String currentDate = DateAndTimeUtils.getCurrentDateMMddyyyy();

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        sourceNavigateFiltersAndSortsPage().setFilterDocNumber(SourceDatabaseUtils.getDocNumberFromRenditionUuid(connection, renditionUuid));
        sourceNavigateFooterToolsPage().refreshTheGrid();

        //right click first rendition and select Edit -> Effective Date
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean effectiveDateWindowAppears = renditionContextMenu().editEffectiveDate();
        Assertions.assertTrue(effectiveDateWindowAppears, "Effective Date window appears");

        //set date to today, check Run Cite Locate checkbox, and click Save
        effectiveDatePage().setEffectiveDate(currentDate);
        effectiveDatePage().checkRunCiteLocate();
        effectiveDatePage().clickSave();

        //check date is correct in grid
        boolean effectiveDateInGridIsToday = sourceNavigateGridPage().getEffectiveDateOfFirstRendition().equals(currentDate);
        Assertions.assertTrue(effectiveDateInGridIsToday, "Effective Date is " + currentDate + " in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Go to Edit -> Class Number Wizard using context menu, set class number, and check value is updated in grid <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void classNumberWizardLegalTest()
    {
        //TODO This test passes with current source front/end insertions, but causes other tests to fail

        String classNum = "123";

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter for the document: Validation: None, Duplicates: None, Deleted: Not Deleted,
        // Content Set: Iowa(Development), Year: 2012, Rendition Status: APV, Doc Number: 0020
        filterRendition("2012", "0020");

        //unlock rendition if needed
        sourceNavigateGridPage().unlockFirstRendition();

        //right click first rendition and select Edit -> Class Number Wizard
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean classNumberWizardWindowAppears = renditionContextMenu().editClassNumberWizard();
        Assertions.assertTrue(classNumberWizardWindowAppears, "Class Number Wizard window appears");

        //set class number, set scp changes to replace SCP Class Number, set all others to no change
        classNumberWizardPage().setClassNumber(classNum);
        classNumberWizardPage().replaceScpClassNumber("SCP Class Number");
        classNumberWizardPage().selectDtypeNoChange();
        classNumberWizardPage().selectHg1NoChange();
        classNumberWizardPage().selectHg2NoChange();
        classNumberWizardPage().clickSubmit();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().waitForPageLoaded();

        //check class number is correct in grid and restore to baseline 0
        boolean classNumberCorrectInGrid = sourceNavigateGridPage().getClassNumber().equals(classNum);
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewRenditionBaselines();
        viewBaselinesNavigatePage().clickNumberSort();
        viewBaselinesNavigatePage().rightClickOriginalBaseline();
        viewBaselinesContextMenu().restoreBaseline();
        Assertions.assertTrue(classNumberCorrectInGrid, "Class Number is updated correctly in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Go to Edit -> Approval Date Wizard using context menu, set approval date, and check value is updated in properties <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void approvalDateWizardLegalTest()
    {
        //TODO This test passes with current source front/end insertions, but causes other tests to fail

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter for the document: Validation: None, Duplicates: None, Deleted: Not Deleted,
        // Content Set: Iowa(Development), Year: 2015, Rendition Status: APV, Doc Number: 166
        filterRendition("2015", "166");

        //unlock rendition if needed
        sourceNavigateGridPage().unlockFirstRendition();

        //right click first rendition and select Edit -> Approval Date Wizard
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean approvalDateWizardWindowAppears = renditionContextMenu().editApprovalDateWizard();
        Assertions.assertTrue(approvalDateWizardWindowAppears, "Approval Date Wizard window appears");

        //set approval date, set apv changes to replace APV Approval Date-Full Date
        approvalDateWizardPage().setApprovalDate(DateAndTimeUtils.getCurrentDateMMddyyyy());
        approvalDateWizardPage().selectApvReplace("APV Approval Date-Full Date");
        approvalDateWizardPage().clickSubmit();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().waitForPageLoaded();

        //check approval date is correct in properties
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().renditionProperties();
        boolean approvalDateIsToday = renditionPropertiesPage().getApprovalDate().equals(DateAndTimeUtils.getCurrentDateMMddyyyy());
        renditionPropertiesPage().clickCancel();
        Assertions.assertTrue(approvalDateIsToday, "Approval Date is today");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Go to Edit -> West ID Wizard using context menu, set west id, and check value is updated in grid <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void westIdWizardLegalTest()
    {
        //TODO This test passes with current source front/end insertions, but causes other tests to fail
        String westId = "123";

        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter for the document: Validation: None, Duplicates: None, Deleted: Not Deleted,
        // Content Set: Iowa(Development), Year: 2012, Rendition Status: APV, Doc Number: 0020
        filterRendition("2012", "0020");

        //unlock rendition if needed
        sourceNavigateGridPage().unlockFirstRendition();

        //right click first rendition and select Edit -> West ID Wizard
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean westIdWizardWindowAppears = renditionContextMenu().editWestIdWizard();
        Assertions.assertTrue(westIdWizardWindowAppears, "West ID Wizard window appears");

        //set west id, set scp changes to replace SCP West ID, set all others to no change
        westIdWizardPage().enterWestId(westId);
        westIdWizardPage().replaceScpClassNumber("SCP West ID");
        westIdWizardPage().selectHg2NoChange();
        westIdWizardPage().clickSubmit();
        sourcePage().switchToSourceNavigatePage();
        sourceNavigateGridPage().waitForGridRefresh();
        sourceNavigateGridPage().waitForPageLoaded();

        //check west id is correct in grid and restore to baseline 0
        boolean westIdCorrectInGrid = sourceNavigateGridPage().getWestId().equals(westId);
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewRenditionBaselines();
        viewBaselinesNavigatePage().clickNumberSort();
        viewBaselinesNavigatePage().rightClickOriginalBaseline();
        viewBaselinesContextMenu().restoreBaseline();
        Assertions.assertTrue(westIdCorrectInGrid, "West ID is updated correctly in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Go to Edit -> Difficulty Level using context menu, set difficulty level, and check value is updated in grid <br>
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

        //right click first rendition and select Edit -> Difficulty Level
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean difficultyLevelWindowAppears = renditionContextMenu().editDifficultyLevel();
        Assertions.assertTrue(difficultyLevelWindowAppears, "Difficulty Level window appears");

        //add difficulty level and check it is updated in the grid
        difficultyLevelPage().selectDifficultyLevel("E1");
        difficultyLevelPage().clickSave();
        boolean difficultyLevelIsUpdated = sourceNavigateGridPage().getDifficultyLevelOfFirstItem().equals("E1");
        Assertions.assertTrue(difficultyLevelIsUpdated,"Difficulty Level is updated in grid");
    }

    /**
     * STORY/BUG -  N/A <br>
     * SUMMARY - Go to Edit -> Source Front using context menu and check source front and end edits do no change file size <br>
     * USER - Legal <br>
     */
    @Test
	@IE_EDGE_MODE
    @LEGAL
    @LOG
    public void sourceFrontEndFileSizeLegalTest()
    {
        //TODO needs data in source front to be able to add text
        String text = "TEST";
        // Log in and go to Source Navigate
        homePage().goToHomePage();
        loginPage().logIn();
        sourceMenu().goToSourceC2012Navigate();

        //Filter for the document: Validation: None, Duplicates: None, Deleted: Not Deleted,
        // Content Set: Iowa(Development), Year: 2016, Rendition Status: APV, Doc Number: 2276
        filterRendition("2016", "2276");

        //unlock rendition if needed
        sourceNavigateGridPage().unlockFirstRendition();

        //get file size in grid
        String fileSize = sourceNavigateGridPage().getFileSize();

        //right click first rendition and select Edit -> Source Front
        sourceNavigateGridPage().rightClickFirstRendition();
        boolean editorWindowAppears = renditionContextMenu().editSourceFront();
        Assertions.assertTrue(editorWindowAppears, "Common Editor window appears");

        //add word to source.front and check file size does not change
        editorPage().switchToEditorTextFrame();
        editorTextPage().insertPhraseUnderGivenLabelWithHome(text, "//source.front/para[1]");
        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();
        sourcePage().switchToSourceNavigatePage();
        boolean sourceFrontFileSize = sourceNavigateGridPage().getFileSize().equals(fileSize);

        //add word to source.end and check file size does not change
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().editSourceEnd();
        editorPage().switchToEditorTextFrame();
        editorTextPage().insertPhraseUnderGivenLabelWithHome(text, "//source.end/para[1]");
        editorPage().closeAndCheckInChanges();
        editorPage().waitForEditorToClose();
        sourcePage().switchToSourceNavigatePage();
        boolean sourceEndFileSize = sourceNavigateGridPage().getFileSize().equals(fileSize);

        //restore rendition to original baseline
        sourceNavigateGridPage().rightClickFirstRendition();
        renditionContextMenu().viewRenditionBaselines();
        viewBaselinesNavigatePage().clickNumberSort();
        viewBaselinesNavigatePage().rightClickOriginalBaseline();
        viewBaselinesContextMenu().restoreBaseline();

        Assertions.assertAll
        (
            () -> Assertions.assertTrue(sourceFrontFileSize, "Source Front Edit does not change file size"),
            () -> Assertions.assertTrue(sourceEndFileSize, "Source End Edit does not change file size")
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
            datapodObject.delete();
        }
        disconnect(connection);
    }
}
