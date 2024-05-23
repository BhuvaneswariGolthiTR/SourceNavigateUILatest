package com.thomsonreuters.codes.codesbench.quality.tests.tools;

import java.sql.Connection;

import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.BaseDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.StocknoteDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;

public class StocknoteManagerDatabaseTests extends TestService
{
	private String stockNoteName;
	private String copiedStockNoteName;

	/**
	 * STORY - N/A <br>
	 * SUMMARY - Creates a new note within the Stock Note Manager and verifies that it appears in the
	 * grid. Sets up a hotkey for the new note and verifies it appears in the grid. Travels to hierarchy
	 * navigate and adds the Stock Note to the editor and verifies it was added. Repeats this for shift-alt-z  <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void altZHotKeyTest()
	{
		stockNoteName = "Test " + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
		String stocknoteUserAssignedCategory = "Miscellaneous Feature Notes";
		String quickSearch = "12.10";
		String keyWord = "=";
		String altZHotKeyString = "Alt-Z";
		String shiftAltZHotKeyString = "Shift-Alt-Z";

		homePage().goToHomePage();
		loginPage().logIn();

		toolsMenu().goToStockNoteManager();
		stocknoteManagerGridPage().clickCreateNewNote();
		stocknotePropertiesPage().setName(stockNoteName);
		stocknotePropertiesPage().checkOnContextMenu();
		stocknotePropertiesPage().checkStateRules();
		stocknotePropertiesPage().checkLocalRules();
		stocknotePropertiesPage().setUserAssignedCategory(stocknoteUserAssignedCategory);
		stocknotePropertiesPage().save();
		stocknoteManagerFiltersPage().setFilterStocknoteName(stockNoteName);
		stocknoteManagerPage().refresh();
		boolean stockNoteInGridAfterFilter = stocknoteManagerGridPage().isStocknoteInGrid(stockNoteName);
		Assertions.assertTrue(stockNoteInGridAfterFilter, "The Stock Note was in the grid when filtering for it by name.");

		stocknoteManagerGridPage().rightClickFirstStocknote();
		boolean hotKeySetupWindowOpened = stocknoteManagerContextMenu().hotKeySetup();
		Assertions.assertTrue(hotKeySetupWindowOpened, "Hot Key setup opened.");

		stocknoteHotKeysPage().setAltAndChar("Z");
		boolean hotKeyIsCorrect = stocknoteHotKeysPage().doesHotKeyMatch(altZHotKeyString);
		stocknoteHotKeysPage().saveAndIgnoreAlert();
		Assertions.assertTrue(hotKeyIsCorrect, "Hot key matches desired.");

		stocknoteManagerFiltersPage().setFilterStocknoteName(stockNoteName);
		stocknoteManagerPage().refresh();
		boolean hotKeyIsInGrid = stocknoteManagerGridPage().doesFirstHotKeyMatch(altZHotKeyString);
		Assertions.assertTrue(hotKeyIsInGrid, "Hot key is correct in the grid.");

		stocknoteManagerPage().close();
		hierarchyMenu().goToNavigate();
		hierarchySearchPage().quickSearch(keyWord, quickSearch);
		siblingMetadataPage().selectedSiblingMetadataEditContent();
		editorPage().switchDirectlyToTextFrame();
		editorTextPage().sendHotKeyToCornerpieceText(altZHotKeyString);
		boolean addStocknoteInEditorAltZ = editorTextPage().isPlaceholderDisplayed();
		editorToolbarPage().clickToolbarClose();
		editorClosurePage().clickDiscardChanges();

		editorPage().waitForEditorToClose();

		hierarchyNavigatePage().switchToHierarchyEditPage();
		/*
		 * Start of Shift-Alt-Z hotkey
		 */
		toolsMenu().goToStockNoteManager();
		stocknoteManagerFiltersPage().setFilterStocknoteName(stockNoteName);
		stocknoteManagerPage().refresh();
		stocknoteManagerGridPage().rightClickFirstStocknote();
		stocknoteManagerContextMenu().hotKeySetup();
		stocknoteHotKeysPage().setShiftAltAndChar("Z");
		DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
		hotKeyIsCorrect = stocknoteHotKeysPage().doesHotKeyMatch(shiftAltZHotKeyString);
		stocknoteHotKeysPage().saveAndIgnoreAlert();
		Assertions.assertTrue(hotKeyIsCorrect, "The Hot Key matches desired combination.");

		stocknoteManagerFiltersPage().setFilterStocknoteName(stockNoteName);
		stocknoteManagerPage().refresh();
		hotKeyIsInGrid = stocknoteManagerGridPage().doesFirstHotKeyMatch(shiftAltZHotKeyString);
		Assertions.assertTrue(hotKeyIsInGrid, "The Hot Key is correctly shown in the grid.");

		stocknoteManagerPage().close();
		DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
		hierarchyNavigatePage().switchToHierarchyEditPage();
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
		siblingMetadataPage().selectedSiblingMetadataEditContent();
		editorPage().switchDirectlyToTextFrame();
		editorTextPage().sendHotKeyToCornerpieceText(shiftAltZHotKeyString);
		boolean addStocknoteInEditorShiftAltZ = editorTextPage().isPlaceholderDisplayed();
		DateAndTimeUtils.takeNap(DateAndTimeUtils.FIVE_SECONDS);
		editorToolbarPage().clickToolbarClose();
		editorClosurePage().clickDiscardChanges();
		hierarchyNavigatePage().switchToHierarchyEditPage();
		toolsMenu().goToStockNoteManager();
		stocknoteManagerFiltersPage().setFilterStocknoteName(stockNoteName);
		stocknoteManagerPage().refresh();
		stocknoteManagerGridPage().rightClickFirstStocknote();
		stocknoteManagerContextMenu().deleteStocknote(stockNoteName);
		boolean noteIsDeleted = stocknoteManagerGridPage().doesFirstStocknoteNameMatch(stockNoteName);

		Assertions.assertAll
		(
			() -> Assertions.assertFalse(noteIsDeleted, "The Stock Note was deleted."),
			() -> Assertions.assertTrue(addStocknoteInEditorAltZ, "Stock Note was added to the content when pressing Alt-Z."),
			() -> Assertions.assertTrue(addStocknoteInEditorShiftAltZ, "Stock Note was added to the content when pressing Shift-Alt-Z.")
		);
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Routes to the Stock Note Manager and creates a new Stock Note. Before it saves, the
	 * validate button is tested and the resulting message is verified. The Stock Note is then
	 * saved and the information in the grid is checked. That same Stock Note is then edited. The
	 * information in the Stock Note properties is checked to make sure it matches then it is changed.
	 * Validation is then run again, then the Stock Note is saved. After editing, the information in the
	 * grid is checked again. The Stock Note is then deleted. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void createNewEditPropertiesAndDeleteStocknoteLegalTest()
	{
		stockNoteName = "Test " + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
		String userAssignedCategory = "Miscellaneous Feature Notes";
		String content = "<stocknote><placeholder ID=\"undefined\"><metadata.block owner=\"undefined\"><md.mnem>MNEMONIC</md.mnem><md.pub.tag.info><md.pub.tag>NOPUB</md.pub.tag></md.pub.tag.info><md.source.tag>MV</md.source.tag></metadata.block><placeholder.text>&emsp; TEST</placeholder.text></placeholder></stocknote> ";
		String validateMessage = "Stocknote validates with no errors.";
		String todaysDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
		String modifiedBy = legalUser().getFirstname() + " " + legalUser().getLastname();

		homePage().goToHomePage();
		loginPage().logIn();

		toolsMenu().goToStockNoteManager();
		boolean createNewNoteOpened = stocknoteManagerGridPage().clickCreateNewNote();
		Assertions.assertTrue(createNewNoteOpened, "Create new note opened");

		stocknotePropertiesPage().setName(stockNoteName);
		stocknotePropertiesPage().checkOnContextMenu();
		stocknotePropertiesPage().checkStateRules();
		stocknotePropertiesPage().checkLocalRules();
		stocknotePropertiesPage().checkPinToTop();
		stocknotePropertiesPage().setUserAssignedCategory(userAssignedCategory);
		stocknotePropertiesPage().uncheckTargetDocumentsSelectAll();
		stocknotePropertiesPage().checkSourceDocumentsSelectAll();
		stocknotePropertiesPage().setContent(content);
		stocknotePropertiesPage().validate();
		Assertions.assertTrue(stocknotePropertiesPage().pageSourceContains(validateMessage), "The validation message " + validateMessage + " appeared.");

		stocknotePropertiesPage().save();
		stocknoteManagerFiltersPage().setFilterStocknoteName(stockNoteName);
		stocknoteManagerPage().refresh();

		Assertions.assertAll
		(
			() -> Assertions.assertEquals(stockNoteName, stocknoteManagerGridPage().getFirstStocknoteName(), "The first Stock Note name is correct."),
			() -> Assertions.assertEquals(userAssignedCategory, stocknoteManagerGridPage().getFirstStocknoteCategory(), "The first Stock Note category matches the category inserted."),
			() -> Assertions.assertTrue(stocknoteManagerGridPage().firstStocknoteOnContextMenuY(), "The first Stock Note does have Y for the On Context Menu section."),
			() -> Assertions.assertEquals("", stocknoteManagerGridPage().getFirstStocknoteHotKey(), "The first Stock Note hotkey should be blank."),
			() -> Assertions.assertEquals("None", stocknoteManagerGridPage().getTargetDocType(), "The first Stock Note target doc type is correct."),
			() -> Assertions.assertTrue(stocknoteManagerGridPage().firstStocknoteSourceDocsY(), "The first Stock Note does have Y for the Source Docs section."),
			() -> Assertions.assertTrue(stocknoteManagerGridPage().firstStocknoteStateRulesY(), "The first Stock Note does have Y for the State Rules section."),
			() -> Assertions.assertTrue(stocknoteManagerGridPage().firstStocknoteLocalRulesY(), "The first Stock Note does have Y for the Local Rules section."),
			() -> Assertions.assertEquals(modifiedBy, stocknoteManagerGridPage().getFirstStocknoteModifiedBy(), "The first Stock Note does correct username for the Modified By section."),
			() -> Assertions.assertEquals(todaysDate, stocknoteManagerGridPage().getFirstStocknoteModifiedDate(), "The first Stock Note does have the correct Modified Date."),
			() -> Assertions.assertTrue(stocknoteManagerGridPage().firstStocknotePinnedToTopY(), "The first Stock Note does have Y for the Pinned to Top section.")
		);

		stocknoteManagerGridPage().rightClickFirstStocknote();
		stocknoteManagerContextMenu().properties();

		Assertions.assertAll
		(
			() -> Assertions.assertEquals(stockNoteName, stocknotePropertiesPage().getStocknoteName(), "Stock Note name does match in properties window."),
			() -> Assertions.assertTrue(stocknotePropertiesPage().isOnContextMenuChecked(), "On context menu is checked."),
			() -> Assertions.assertTrue(stocknotePropertiesPage().isStateRulesChecked(), "State rules is checked."),
			() -> Assertions.assertTrue(stocknotePropertiesPage().isLocalRulesChecked(), "On context menu is checked."),
			() -> Assertions.assertTrue(stocknotePropertiesPage().isPinToTopChecked(), "Pin to top is checked."),
			() -> Assertions.assertEquals(userAssignedCategory, stocknotePropertiesPage().getUserAssignedCategory(), "User assigned category is correct."),
			() -> Assertions.assertFalse(stocknotePropertiesPage().isTargetDocumentsSelectAllChecked(), "Target document select all isn't checked."),
			() -> Assertions.assertTrue(stocknotePropertiesPage().isSourceDocumentsSelectAllChecked(), "Source document select all is checked.")
		);

		stocknotePropertiesPage().uncheckStateRules();
		stocknotePropertiesPage().uncheckLocalRules();
		stocknotePropertiesPage().checkTargetDocumentsSelectAll();
		stocknotePropertiesPage().uncheckSourceDocumentsSelectAll();
		stocknotePropertiesPage().validate();
		Assertions.assertTrue(stocknotePropertiesPage().pageSourceContains(validateMessage), "The validation message " + validateMessage + " appeared after editing the stocknote.");

		stocknotePropertiesPage().save();
		stocknoteManagerFiltersPage().setFilterStocknoteName(stockNoteName);
		stocknoteManagerPage().refresh();

		Assertions.assertAll
		(
			() -> Assertions.assertEquals(stockNoteName, stocknoteManagerGridPage().getFirstStocknoteName(), "First Stock Note name is correct after editing the Stock Note."),
			() -> Assertions.assertEquals(userAssignedCategory, stocknoteManagerGridPage().getFirstStocknoteCategory(), "First Stock Note category is correct after editing the Stock Note."),
			() -> Assertions.assertTrue(stocknoteManagerGridPage().firstStocknoteOnContextMenuY(), "First Stock Note does have Y for the On Context Menu section after editing the Stock Note."),
			() -> Assertions.assertEquals("", stocknoteManagerGridPage().getFirstStocknoteHotKey(), "First Stock Note hotkey should be blank after editing the Stock Note."),
			() -> Assertions.assertEquals("All", stocknoteManagerGridPage().getTargetDocType(), "First Stock Note target doc type is correct after editing the Stock Note."),
			() -> Assertions.assertFalse(stocknoteManagerGridPage().firstStocknoteSourceDocsY(), "First Stock Note does have Y for the Source Docs section after editing the Stock Note."),
			() -> Assertions.assertFalse(stocknoteManagerGridPage().firstStocknoteStateRulesY(), "First Stock Note does have Y for the State Rules section after editing the Stock Note."),
			() -> Assertions.assertFalse(stocknoteManagerGridPage().firstStocknoteLocalRulesY(), "First Stock Note does have Y for the Local Rules section after editing the Stock Note."),
			() -> Assertions.assertEquals(modifiedBy, stocknoteManagerGridPage().getFirstStocknoteModifiedBy(), "First Stock Note does correct the username for the Modified By section after editing the stocknote."),
			() -> Assertions.assertEquals(todaysDate, stocknoteManagerGridPage().getFirstStocknoteModifiedDate(), "First Stock Note does have the correct Modified Date after editing the stocknote."),
			() -> Assertions.assertTrue(stocknoteManagerGridPage().firstStocknotePinnedToTopY(), "First Stock Note does have Y for the Pinned to Top section after editing the Stock Note.")
		);

		stocknoteManagerGridPage().rightClickFirstStocknote();
		boolean deleteAlertTextMatches = stocknoteManagerContextMenu().deleteStocknote(stockNoteName);
		Assertions.assertTrue(deleteAlertTextMatches, "The delete text matches.");

		stocknoteManagerFiltersPage().setFilterStocknoteName(stockNoteName);
		stocknoteManagerPage().refresh();
		Assertions.assertTrue(stocknoteManagerGridPage().isStocknoteDeleted(stockNoteName), "Stock Note " + stockNoteName + " was not deleted.");
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Changes content to USCA. Routes to the Stock Note Manager and gets the first hot key in
	 * the grid and the name of that Stock Note. Closes the Stock Note Manager and sets the content to
	 * Iowa. Goes to the Stock Note Manager and creates a Stock Note with the same hot key as the one
	 * from USCA. The Stock Note is then copied to the USCA jurisdiction. The results message is
	 * validated. The created Stock Note is then deleted. Then the Stock Note Manager is closed and the
	 * content is set to USCA. Routes to the Stock Note manager and verifies the original Stock Note still
	 * has it's hot key and the one we copied over does not. The copied Stock Note is deleted. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void copyStocknoteToOtherJurisdictionLegalTest()
	{
		stockNoteName = "Test " + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
		String userAssignedCategory = "Miscellaneous Feature Notes";
		String validateMessage = "Stocknote validates with no errors.";
		String uscaContent = "USCA(Development)";
		String todaysDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
		String modifiedBy = legalUser().getFirstname() + " " + legalUser().getLastname();
		String resultsTextAreaExpectedTest = stockNoteName + " created in " + uscaContent;

		homePage().goToHomePage();
		loginPage().logIn();

		toolsMenu().goToStockNoteManager();
		stocknoteManagerGridPage().clickCreateNewNote();
		stocknotePropertiesPage().setName(stockNoteName);
		stocknotePropertiesPage().setUserAssignedCategory(userAssignedCategory);
		stocknotePropertiesPage().validate();
		Assertions.assertTrue(stocknotePropertiesPage().validateMessageContains(validateMessage), "The validation message \"" + validateMessage + "\" appeared after editing the Stock Note.");

		stocknotePropertiesPage().save();
		stocknoteManagerFiltersPage().setFilterStocknoteName(stockNoteName);
		stocknoteManagerPage().refresh();

		Assertions.assertAll
		(
			() -> Assertions.assertEquals(stockNoteName, stocknoteManagerGridPage().getFirstStocknoteName(), "The first Stock Note should have the name " + stockNoteName),
			() -> Assertions.assertEquals(todaysDate, stocknoteManagerGridPage().getFirstStocknoteModifiedDate(), "The first Stock Note should have the modified date " + todaysDate),
			() -> Assertions.assertEquals(modifiedBy, stocknoteManagerGridPage().getFirstStocknoteModifiedBy(), "The first Stock Note should have been modified by " + modifiedBy)
		);

		stocknoteManagerGridPage().rightClickFirstStocknote();
		boolean stocknoteTableManagementWindowOpened = stocknoteManagerContextMenu().copyToAnotherContentSet();
		Assertions.assertTrue(stocknoteTableManagementWindowOpened, "The Stock Note table management window opened.");

		stocknoteTableManagementPage().selectContentSet(uscaContent);
		stocknoteTableManagementPage().copyToContentSet();
		Assertions.assertTrue(stocknoteTableManagementPage().resultsTextAreaContains(resultsTextAreaExpectedTest), "The results text area does show the correct message after clicking copy.");
		stocknoteTableManagementPage().close();
		homePage().switchToPage();
		homePage().setMyContentSet(uscaContent);
		toolsMenu().goToStockNoteManager();
		stocknoteManagerFiltersPage().setFilterStocknoteName(stockNoteName);
		stocknoteManagerPage().refresh();

		Assertions.assertAll
		(
			() -> Assertions.assertEquals(stockNoteName, stocknoteManagerGridPage().getFirstStocknoteName(), "The first Stock Note should have the name " + stockNoteName),
			() -> Assertions.assertEquals(todaysDate, stocknoteManagerGridPage().getFirstStocknoteModifiedDate(), "The first Stock Note should have the modified date " + todaysDate),
			() -> Assertions.assertEquals(modifiedBy, stocknoteManagerGridPage().getFirstStocknoteModifiedBy(), "The first Stock Note should have been modified by " + modifiedBy)
		);

		stocknoteManagerGridPage().rightClickFirstStocknote();
		stocknoteManagerContextMenu().deleteStocknote(stockNoteName);
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Creates a Stock Note then runs search and replace and verifies the correct changes are
	 * made to the Stock Note. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void searchAndReplaceLegalTest()
	{
		stockNoteName = "Test " + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
		String userAssignedCategory = "Miscellaneous Feature Notes";
		String stocknoteContent = "<stocknote><placeholder ID=\"undefined\"><metadata.block owner=\"undefined\"><md.mnem>MNEMONIC</md.mnem><md.pub.tag.info><md.pub.tag>NOPUB</md.pub.tag></md.pub.tag.info><md.source.tag>MV</md.source.tag></metadata.block><placeholder.text>&emsp;TEST QED</placeholder.text></placeholder></stocknote>";
		String validateMessage = "Stocknote validates with no errors.";
		String todaysDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
		String modifiedBy = legalUser().getFirstname() + " " + legalUser().getLastname();
		String searchValue = "TEST QED";
		String replaceValue = "REST QED PLEASE";

		homePage().goToHomePage();
		loginPage().logIn();

		toolsMenu().goToStockNoteManager();
		stocknoteManagerGridPage().clickCreateNewNote();
		stocknotePropertiesPage().setName(stockNoteName);
		stocknotePropertiesPage().setUserAssignedCategory(userAssignedCategory);
		stocknotePropertiesPage().setContent(stocknoteContent);
		stocknotePropertiesPage().validate();
		Assertions.assertTrue(stocknotePropertiesPage().validateMessageContains(validateMessage), "The validation message " + validateMessage + " appeared after editing the Stock Note.");

		stocknotePropertiesPage().save();
		stocknoteManagerFiltersPage().setFilterStocknoteName(stockNoteName);
		stocknoteManagerPage().refresh();

		Assertions.assertAll
		(
			() -> Assertions.assertEquals(stockNoteName, stocknoteManagerGridPage().getFirstStocknoteName(), "The first Stock Note in the grid should have the same name as the one we created."),
			() -> Assertions.assertEquals(userAssignedCategory, stocknoteManagerGridPage().getFirstStocknoteCategory(), "The first Stock Note in the grid should have the same category as the one we created."),
			() -> Assertions.assertEquals(modifiedBy, stocknoteManagerGridPage().getFirstStocknoteModifiedBy(), "The first Stock Note does correct the username for the Modified By section."),
			() -> Assertions.assertEquals(todaysDate, stocknoteManagerGridPage().getFirstStocknoteModifiedDate(), "The first Stock Note does have the correct Modified Date.")
		);

		stocknoteManagerGridPage().clickStocknoteSearchAndReplace();
		stocknoteSearchAndReplacePage().setSearchValue(searchValue);
		stocknoteSearchAndReplacePage().setReplaceValue(replaceValue);
		stocknoteSearchAndReplacePage().previewChanges();
		Assertions.assertTrue(stocknoteSearchAndReplacePage().beforeChangeContains(searchValue), "The before content is correct.");
		Assertions.assertTrue(stocknoteSearchAndReplacePage().afterChangeContains(replaceValue), "The after content is correct.");

		boolean staysOnSearchAndReplaceAfterApply = stocknoteSearchAndReplacePage().applyChanges();
		Assertions.assertTrue(staysOnSearchAndReplaceAfterApply, "The SAR window stayed after applying changes.");

		stocknoteSearchAndReplacePage().close();
		stocknoteManagerGridPage().rightClickFirstStocknote();
		stocknoteManagerContextMenu().properties();
		Assertions.assertFalse(stocknotePropertiesPage().contentContains(searchValue), "The content in the Stock Note properties is correct.");

		Assertions.assertTrue(stocknotePropertiesPage().contentContains(replaceValue), "The content in the Stock Note properties is correct.");

		stocknotePropertiesPage().cancel();
		stocknoteManagerGridPage().rightClickFirstStocknote();
		boolean deleteAlertTextMatches = stocknoteManagerContextMenu().deleteStocknote(stockNoteName);
		Assertions.assertTrue(deleteAlertTextMatches, "The delete text matches.");

		stocknoteManagerFiltersPage().setFilterStocknoteName(stockNoteName);
		stocknoteManagerPage().refresh();
		Assertions.assertTrue(stocknoteManagerGridPage().isStocknoteDeleted(stockNoteName), "Stock Note " + stockNoteName + " was deleted.");
	}

	/**
	 * STORY/BUG - N/A <br>
	 * SUMMARY - Creates a Stock Note then makes a copy of it. Verifies the information on the properties
	 * pages is correct. Then saves the Stock Note and verifies the information in the grid is correct. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void copyStocknoteLegalTest()
	{
		stockNoteName = "Test " + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
		copiedStockNoteName = "Copy of " + stockNoteName;
		String userAssignedCategory = "Miscellaneous Feature Notes";
		String content = "<stocknote><placeholder ID=\"undefined\"><metadata.block owner=\"undefined\"><md.mnem>MNEMONIC</md.mnem><md.pub.tag.info><md.pub.tag>NOPUB</md.pub.tag></md.pub.tag.info><md.source.tag>MV</md.source.tag></metadata.block><placeholder.text>&emsp; TEST</placeholder.text></placeholder></stocknote> ";
		String todaysDate = DateAndTimeUtils.getCurrentDateMMddyyyy();
		String modifiedBy = legalUser().getFirstname() + " " + legalUser().getLastname();

		homePage().goToHomePage();
		loginPage().logIn();

		toolsMenu().goToStockNoteManager();
		boolean createNewNoteOpened = stocknoteManagerGridPage().clickCreateNewNote();
		Assertions.assertTrue(createNewNoteOpened, "Create new note was opened");

		stocknotePropertiesPage().setName(stockNoteName);
		stocknotePropertiesPage().checkOnContextMenu();
		stocknotePropertiesPage().checkStateRules();
		stocknotePropertiesPage().checkLocalRules();
		stocknotePropertiesPage().checkPinToTop();
		stocknotePropertiesPage().setUserAssignedCategory(userAssignedCategory);
		stocknotePropertiesPage().uncheckTargetDocumentsSelectAll();
		stocknotePropertiesPage().checkSourceDocumentsSelectAll();
		stocknotePropertiesPage().setContent(content);
		stocknotePropertiesPage().save();
		stocknoteManagerFiltersPage().setFilterStocknoteName(stockNoteName);
		stocknoteManagerPage().refresh();
		stocknoteManagerGridPage().rightClickFirstStocknote();
		stocknoteManagerContextMenu().copyNote();

		Assertions.assertAll
		(
			() -> Assertions.assertEquals(copiedStockNoteName, stocknotePropertiesPage().getStocknoteName(), "Stock Note name does match in properties window."),
			() -> Assertions.assertTrue(stocknotePropertiesPage().isOnContextMenuChecked(), "On context menu is checked."),
			() -> Assertions.assertTrue(stocknotePropertiesPage().isStateRulesChecked(), "State rules is checked."),
			() -> Assertions.assertTrue(stocknotePropertiesPage().isLocalRulesChecked(), "On context menu is checked."),
			() -> Assertions.assertTrue(stocknotePropertiesPage().isPinToTopChecked(), "Pin to top is checked."),
			() -> Assertions.assertEquals(userAssignedCategory, stocknotePropertiesPage().getUserAssignedCategory(), "User assigned category is correct."),
			() -> Assertions.assertFalse(stocknotePropertiesPage().isTargetDocumentsSelectAllChecked(), "Target document select all is checked."),
			() -> Assertions.assertTrue(stocknotePropertiesPage().isSourceDocumentsSelectAllChecked(), "Source document select all isn't note checked.")
		);

		stocknotePropertiesPage().save();
		stocknoteManagerFiltersPage().setFilterStocknoteName(copiedStockNoteName);
		stocknoteManagerPage().refresh();

		Assertions.assertAll
		(
			() -> Assertions.assertEquals(copiedStockNoteName, stocknoteManagerGridPage().getFirstStocknoteName(), "First Stock Note name is correct."),
			() -> Assertions.assertEquals(userAssignedCategory, stocknoteManagerGridPage().getFirstStocknoteCategory(), "First Stock Note category is correct."),
			() -> Assertions.assertTrue(stocknoteManagerGridPage().firstStocknoteOnContextMenuY(), "First Stock Note does have Y for the On Context Menu section."),
			() -> Assertions.assertEquals("", stocknoteManagerGridPage().getFirstStocknoteHotKey(), "First Stock Note hotkey should be blank."),
			() -> Assertions.assertEquals("None", stocknoteManagerGridPage().getTargetDocType(), "First Stock Note target doc type is correct."),
			() -> Assertions.assertTrue(stocknoteManagerGridPage().firstStocknoteSourceDocsY(), "First Stock Note does have Y for the Source Docs section."),
			() -> Assertions.assertTrue(stocknoteManagerGridPage().firstStocknoteStateRulesY(), "First Stock Note does have Y for the State Rules section."),
			() -> Assertions.assertTrue(stocknoteManagerGridPage().firstStocknoteLocalRulesY(), "First Stock Note does have Y for the Local Rules section."),
			() -> Assertions.assertEquals(modifiedBy, stocknoteManagerGridPage().getFirstStocknoteModifiedBy(), "First Stock Note does correct the username for the Modified By section."),
			() -> Assertions.assertEquals(todaysDate, stocknoteManagerGridPage().getFirstStocknoteModifiedDate(), "First Stock Note does have the correct Modified Date."),
			() -> Assertions.assertTrue(stocknoteManagerGridPage().firstStocknotePinnedToTopY(), "First Stock Note does have Y for the Pinned to Top section.")
		);

		stocknoteManagerGridPage().rightClickFirstStocknote();

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(stocknoteManagerContextMenu().deleteStocknote(copiedStockNoteName), "The alert appeared when deleting the copied Stock Note."),
			() -> Assertions.assertTrue(stocknoteManagerGridPage().isStocknoteDeleted(copiedStockNoteName), "Stock Note Copy of " + stockNoteName + " was deleted.")
		);

		stocknoteManagerFiltersPage().setFilterStocknoteName(stockNoteName);
		stocknoteManagerPage().refresh();
		stocknoteManagerGridPage().rightClickFirstStocknote();

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(stocknoteManagerContextMenu().deleteStocknote(stockNoteName), "The alert appeared when deleting the Stock Note."),
			() -> Assertions.assertTrue(stocknoteManagerGridPage().isStocknoteDeleted(stockNoteName), "Stock Note " + stockNoteName + " was deleted.")
		);
	}

	@AfterEach
	public void deleteStocknoteFromDB()
	{
		System.out.println(stockNoteName + " (Deleted)");
		System.out.println(legalUser().getUsername());

		Connection uatConnection = BaseDatabaseUtils.connectToDatabaseUAT();
		StocknoteDatabaseUtils.deleteStocknote(uatConnection, stockNoteName + " (Deleted)", legalUser().getUsername());
		System.out.println("Stock Note deleted from database.");
		if(copiedStockNoteName != null)
		{
			StocknoteDatabaseUtils.deleteStocknote(uatConnection, copiedStockNoteName + " (Deleted)", legalUser().getUsername());
			System.out.println("Copy of Stock Note deleted from database.");
		}
		BaseDatabaseUtils.commit(uatConnection);
		BaseDatabaseUtils.disconnect(uatConnection);
	}
}
