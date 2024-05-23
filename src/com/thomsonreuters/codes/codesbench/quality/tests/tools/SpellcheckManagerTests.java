package com.thomsonreuters.codes.codesbench.quality.tests.tools;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.spellcheckmanager.SpellcheckManagerFiltersPageElements;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.IE_EDGE_MODE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.database.tools.SpellcheckManagerDatabaseUtils;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class SpellcheckManagerTests extends TestService
{
	/**
	 * STORY/BUGS - HALCYONST-1476 <br>
	 * SUMMARY - This test checks the add/removed filter column as well as the show deleted checkbox.
	 * Then checks if the add/removed filter column changes correctly when show deleted checkbox is changed
	 * as well as the other way around. Also verifies the words in the grid change depending on the
	 * filter. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void spellCheckAddRemoveFiltersLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean spellcheckMangerWindowAppeared = toolsMenu().goToSpellcheckManager();
		Assertions.assertTrue(spellcheckMangerWindowAppeared, "Spellcheck Manager window should have appeared.");

		boolean showDeletedCheckedByDefault = spellcheckManagerPage().isShowDeletedChecked();
		boolean addRemovedDropdownIsBlank = spellcheckMangerFiltersPage().isAddRemovedFilterBlank();
		boolean stateColumnShowsBothNewAndRemovedWords = spellcheckManagerGridPage().areAllWordStatesInGridNewAndRemoved();
		spellcheckManagerPage().uncheckShowDeleted();
		boolean addRemovedDropdownShowsNew = spellcheckMangerFiltersPage().isAddRemovedFilterNew();
		boolean stateColumnShowsOnlyFirstWords = spellcheckManagerGridPage().areAllWordStatesInGridNew();
		boolean addRemovedDropdownIsRemoved = spellcheckMangerFiltersPage().setAddRemovedFilterToRemoved();
		boolean showDeletedIsChecked = spellcheckManagerPage().isShowDeletedChecked();
		boolean stateColumnShowsOnlyRemovedWords = spellcheckManagerGridPage().areAllWordStatesInGridRemoved();
		boolean addRemovedDropdownIsBlankAgain = spellcheckMangerFiltersPage().setAddRemovedFilterToBlank();
		boolean showDeletedIsStillChecked = spellcheckManagerPage().isShowDeletedChecked();
		boolean stateColumnShowsBothNewAndRemovedWordsAgain = spellcheckManagerGridPage().areAllWordStatesInGridNewAndRemoved();

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(showDeletedCheckedByDefault, "Show deleted checkbox should be checked by default."),
			() -> Assertions.assertTrue(addRemovedDropdownIsBlank, "Add-Removed dropdown should be blank."),
			() -> Assertions.assertTrue(stateColumnShowsBothNewAndRemovedWords, "State column should show both new and removed words."),
			() -> Assertions.assertTrue(addRemovedDropdownShowsNew, "Add-Removed dropdown should show new."),
			() -> Assertions.assertTrue(stateColumnShowsOnlyFirstWords, "State column should show only new words."),
			() -> Assertions.assertTrue(showDeletedIsChecked, "Show deleted should be checked."),
			() -> Assertions.assertTrue(addRemovedDropdownIsRemoved, "Add-Removed dropdown should be set to removed."),
			() -> Assertions.assertTrue(stateColumnShowsOnlyRemovedWords, "State column should only show removed words."),
			() -> Assertions.assertTrue(showDeletedIsStillChecked, "Show deleted should still be checked."),
			() -> Assertions.assertTrue(addRemovedDropdownIsBlankAgain, "Add-Removed dropdown should be blank again."),
			() -> Assertions.assertTrue(stateColumnShowsBothNewAndRemovedWordsAgain, "State column should show both new and removed words again")
		);
	}

	/**
	 * STORY/BUGS - HALCYONST-767 <br>
	 * SUMMARY - Checks if an empty word can be added. This should not be allowed. It then verifies the
	 * correct alert came up and the word is not in the grid. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void spellCheckEmptyWordLegalTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean spellcheckMangerWindowAppeared = toolsMenu().goToSpellcheckManager();
		Assertions.assertTrue(spellcheckMangerWindowAppeared, "Spellcheck Manager window appeared.");

		spellcheckManagerPage().addWord("");
		boolean alertAppeared = spellcheckManagerPage().getAcceptAlert("No value has been added, please add a value and try again");
		boolean emptyWordAdded = spellcheckManagerGridPage().isWordInGrid("");

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(alertAppeared, "Alert appeared to tell the user to add a word before clicking Add"),
			() -> Assertions.assertFalse(emptyWordAdded, "Empty word was added to the dictionary.")
		);
	}

	/**
	 * STORY/BUGS - HALCYONST-768 <br>
	 * SUMMARY - Checks that a word can be added to the Spellcheck Manager successfully. It then tries
	 * to add the same word again. Checks to make sure the correct alert pops up. Adds a second word and
	 * checks that it has been added. Then removes both using the context menu and verifies they correct
	 * alerts pop up and the words are no longer in the Spellcheck Manager. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void spellCheckDuplicateWordAndRemoveWordsViaContextMenuLegalTest()
	{
		String firstWord = "TEST";
		String secondWord = "TeST";

		homePage().goToHomePage();
		loginPage().logIn();

		boolean spellcheckMangerWindowAppeared = toolsMenu().goToSpellcheckManager();
		Assertions.assertTrue(spellcheckMangerWindowAppeared, "Spellcheck Manager window appeared.");

		spellcheckManagerPage().uncheckShowDeleted();
		spellcheckManagerPage().addWordDeleteIfExists(firstWord);
		boolean firstWordAdded = spellcheckManagerGridPage().isWordInGrid(firstWord);
		Assertions.assertTrue(firstWordAdded, "First word was added to the spellcheck manager.");

		spellcheckManagerPage().addWord(firstWord);
		boolean duplicateWordAlertAppeared = spellcheckManagerPage().getAcceptAlert("Added word already exists on spellcheck list");
		spellcheckManagerPage().switchToPage();
		spellcheckManagerPage().addWordDeleteIfExists(secondWord);
		boolean secondWordAdded = spellcheckManagerGridPage().isWordInGrid(secondWord);
		Assertions.assertTrue(secondWordAdded, "Second word was added to the spellcheck manager.");

		spellcheckManagerPage().closeSpellcheckManagerWindow();
		homePage().switchToPage();
		boolean spellcheckMangerWindowAppeared2 = toolsMenu().goToSpellcheckManager();
		Assertions.assertTrue(spellcheckMangerWindowAppeared2, "Spellcheck Manager window appeared.");

		spellcheckManagerPage().uncheckShowDeleted();
		spellcheckManagerGridPage().rightClickWord(firstWord);
		spellcheckManagerContextMenu().removeWord();
		boolean firstWordRemoveAlertAppeared = spellcheckManagerPage().checkRemoveWordAlertAppeared(firstWord);
		spellcheckManagerGridPage().rightClickWord(secondWord);
		spellcheckManagerContextMenu().removeWord();
		boolean secondWordRemoveAlertAppeared = spellcheckManagerPage().checkRemoveWordAlertAppeared(secondWord);
		boolean firstWordStillInDictionary = spellcheckManagerGridPage().isWordInGrid(firstWord);
		boolean secondWordStillInDictionary = spellcheckManagerGridPage().isWordInGrid(secondWord);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(duplicateWordAlertAppeared, "Alert appeared when trying to add the first word again."),
			() -> Assertions.assertTrue(firstWordRemoveAlertAppeared, "Alert appeared when removing first word."),
			() -> Assertions.assertTrue(secondWordRemoveAlertAppeared, "Alert appeared when removing second word."),
			() -> Assertions.assertFalse(firstWordStillInDictionary, "First word is still in Spellcheck Manager."),
			() -> Assertions.assertFalse(secondWordStillInDictionary, "Second word is still in Spellcheck Manager.")
		);
	}

	/**
	 * STORY/BUGS - HALCYONST-769 <br>
	 * SUMMARY - Routes to the Spellcheck Manager to test deleting words. First adds a word and deletes
	 * it if it exists. It then tests the functionality of the minus button. Verifies the word is not
	 * in the grid when show deleted is not checked. Verifies the word is in the
	 * grid when show deleted is check and it has the removed status and plus button <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void spellCheckDeleteWordLegalTest()
	{
		String newWord = "TEST";

		homePage().goToHomePage();
		loginPage().logIn();

		boolean spellcheckMangerWindowAppeared = toolsMenu().goToSpellcheckManager();
		Assertions.assertTrue(spellcheckMangerWindowAppeared, "Spellcheck Manager window appeared.");

		spellcheckManagerPage().uncheckShowDeleted();
		spellcheckManagerPage().addWordDeleteIfExists(newWord);
		spellcheckManagerGridPage().removeWordUsingMinusButton(newWord);
		boolean removedWordIsNotShownInGrid = spellcheckManagerGridPage().isWordInGrid(newWord);
		Assertions.assertFalse(removedWordIsNotShownInGrid, "New word should have been removed.");

		spellcheckManagerPage().checkShowDeleted();
		boolean removedWordIsShownInGrid = spellcheckManagerGridPage().isWordInGrid(newWord);
		Assertions.assertTrue(removedWordIsShownInGrid, "Removed word should be displayed.");


		boolean statusIsRemoved = spellcheckManagerGridPage().doesGivenWordHaveRemovedStatus(newWord);
		boolean minusIsNowPlus = spellcheckManagerGridPage().doesGivenWordHavePlusButton(newWord);
		boolean spellcheckManagerWindowClosed = spellcheckManagerPage().closeSpellcheckManagerWindow();

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(statusIsRemoved, "Status of removed word should be \"removed\"."),
			() -> Assertions.assertTrue(minusIsNowPlus, "There should be an Add-Plus button instead of Remove-Minus button."),
			() -> Assertions.assertTrue(spellcheckManagerWindowClosed, "Spellcheck Manager window should have closed.")
		);
	}

	/**
	 * STORY/BUGS - HALCYONST-771 <br>
	 * SUMMARY - Routes to the Spellcheck Manager then adds two words. It executes sort before
	 * ascending and descending and verifies it works correctly. Then filters by word and verifies it
	 * works correctly. The words are then deleted. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void spellCheckFilteringAndSortingLegalTest()
	{
		String firstWord = "1TEST";
		String secondWord = "2TEST";

		homePage().goToHomePage();
		loginPage().logIn();

		boolean spellcheckMangerWindowAppeared = toolsMenu().goToSpellcheckManager();
		Assertions.assertTrue(spellcheckMangerWindowAppeared, "Spellcheck Manager window appeared.");

		spellcheckManagerPage().uncheckShowDeleted();
		spellcheckManagerPage().addWordDeleteIfExists(firstWord);
		spellcheckManagerPage().addWordDeleteIfExists(secondWord);
		spellcheckMangerFiltersPage().sortByWordAscending();
		boolean ascendingCheck = spellcheckManagerGridPage().areWordsInOrder(firstWord, secondWord);
		spellcheckMangerFiltersPage().sortByWordDescending();
		boolean descendingCheck = spellcheckManagerGridPage().areWordsInOrder(secondWord, firstWord);
		spellcheckMangerFiltersPage().filterByWord(firstWord);
		boolean firstWordPresentAfterFilter = spellcheckManagerGridPage().isWordInGrid(firstWord);
		boolean secondWordNotPresentAfterFilter = spellcheckManagerGridPage().isWordInGrid(secondWord);
		spellcheckManagerFiltersPage().setTextOfElement("", SpellcheckManagerFiltersPageElements.wordFilterTextbox);
//		spellcheckMangerFiltersPage().clearWordFilter();
		spellcheckManagerGridPage().removeWordUsingMinusButton(firstWord);
		spellcheckManagerGridPage().removeWordUsingMinusButton(secondWord);
		spellcheckManagerPage().uncheckShowDeleted();
		boolean firstWordNotPresentAfterDelete = spellcheckManagerGridPage().isWordInGrid(firstWord);
		boolean secondWordNotPresentAfterDelete = spellcheckManagerGridPage().isWordInGrid(secondWord);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(ascendingCheck, "The 1TEST is higher than 2TEST with ascending sorting"),
			() -> Assertions.assertTrue(descendingCheck, "The 2TEST is higher than 1TEST with descending sorting"),
			() -> Assertions.assertTrue(firstWordPresentAfterFilter, "The first word is not in the grid when filtering for it."),
			() -> Assertions.assertFalse(secondWordNotPresentAfterFilter, "The second word is in the grid when filtering for the first word."),
			() -> Assertions.assertFalse(firstWordNotPresentAfterDelete, "The first word was deleted and doesn't show in the Spellcheck table."),
			() -> Assertions.assertFalse(secondWordNotPresentAfterDelete, "The second word was deleted and doesn't show in the Spellcheck table.")
		);
	}

	/**
	 * STORY/BUGS - HALCYONST-771 <br>
	 * SUMMARY - Adds words in Spellcheck Manager then goes to edit content of a document. The newly
	 * added words are added to the document then the Spellcheck validation is ran. The test then
	 * validates the console has the correct output. It does this one at a time for each word. Then
	 * discards and closes changes and removes the words from Spellcheck Manager.<br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void spellCheckRunningSpellcheckInEditorLegalTest()
	{
		String firstPhrase = "MispelledAvocahdo";
		String secondPhrase = "MispelledBannana";
		String value = "12.43";

		homePage().goToHomePage();
		loginPage().logIn();

		boolean spellcheckMangerWindowAppeared = toolsMenu().goToSpellcheckManager();
		Assertions.assertTrue(spellcheckMangerWindowAppeared, "Spellcheck Manager window appeared.");

		spellcheckManagerPage().uncheckShowDeleted();
		spellcheckManagerPage().addWordIfNew(firstPhrase);
		spellcheckManagerPage().addWordIfNew(secondPhrase);
		spellcheckManagerPage().closeSpellcheckManagerWindow();
		hierarchyMenu().goToNavigate();
		hierarchySearchPage().quickSearch("=", value);
		siblingMetadataPage().selectedSiblingMetadataEditContent();
		editorPage().switchDirectlyToTextFrame();
//		editorTextPage().insertPhraseIntoFirstTextParagraph();
		editorTextPage().insertPhraseIntoFirstTextParagraphWithSpace(firstPhrase);
		editorToolbarPage().breakOutOfFrame();
		editorToolbarPage().clickToolbarCheckSpelling();
		boolean firstPhraseCheckPassed = editorMessagePage().checkLastInfoMessageSpellcheckOk();
		editorPage().switchDirectlyToTextFrame();
		editorTextPage().insertPhraseIntoFirstTextParagraphWithSpace(secondPhrase);
		editorToolbarPage().breakOutOfFrame();
		editorToolbarPage().clickToolbarCheckSpelling();
		boolean secondPhraseCheckPassed = editorMessagePage().checkLastInfoMessageSpellcheckOk();
		editorToolbarPage().clickToolbarClose();
		editorClosurePage().clickDiscardChanges();
		hierarchyNavigatePage().switchToHierarchyEditPage();
		toolsMenu().goToSpellcheckManager();
		boolean firstPhraseRemoved = spellcheckManagerGridPage().removeWordUsingMinusButton(firstPhrase);
		boolean secondPhraseRemoved = spellcheckManagerGridPage().removeWordUsingMinusButton(secondPhrase);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(firstPhraseCheckPassed, "The first phrase check passed."),
			() -> Assertions.assertTrue(secondPhraseCheckPassed, "The second phrase check passed."),
			() -> Assertions.assertTrue(firstPhraseRemoved, "First phrase was removed."),
			() -> Assertions.assertTrue(secondPhraseRemoved, "Second phrase was removed.")
		);
	}

	/**
	 * STORY/BUGS - HALCYONST-771 <br>
	 * SUMMARY - Adds new word in Spellcheck Manager and make sure it appears in the grid with
	 * the correct filters. Then goes to the blueline Spellcheck dictionary and tries adding the same
	 * word. Both words are then removed and we assure that the two dictionaries are talking to one another. <br>
	 * USER - Legal <br>
	 */
	@Test
	@IE_EDGE_MODE
	@LEGAL
	@LOG
	public void spellcheckDictionariesTalkingToEachOtherTest()
	{
		String newWord = "TEST " + DateAndTimeUtils.getCurrentDateAndTimeyyyyMMddHHmmss();
		String userName = "TLE TCBA-BOT";
		String currentDate = DateAndTimeUtils.getCurrentDateMMMddyyyy();
		homePage().goToHomePage();
		loginPage().logIn();

		String currentContentSet = homePage().getCurrentContentSetFromUpperRight();
		boolean spellcheckMangerWindowAppeared = toolsMenu().goToSpellcheckManager();
		Assertions.assertTrue(spellcheckMangerWindowAppeared, "Spellcheck Manager window appeared.");

		spellcheckManagerPage().addWord(newWord);
		spellcheckManagerFiltersPage().setFilterWord(newWord);
		boolean toolsSpellcheckManagerWordAppears = spellcheckManagerGridPage().isWordInGrid(newWord);
		boolean toolsSpellcheckManagerWordHasNewStatus = spellcheckManagerGridPage().doesGivenWordHaveNewStatus(newWord);
		String userNameInGridTools = spellcheckManagerGridPage().getWordUserName(newWord);
		String dateInGridTools = spellcheckManagerGridPage().getWordDate(newWord);
		String wordsGroupTools = SpellcheckManagerDatabaseUtils.getWordsGroupForWord(newWord);
		spellcheckManagerPage().closeSpellcheckManagerWindow();
		homePage().switchToPage();
		boolean bluelineSpellcheckDictionaryWindowAppeared = nodMenu().goToBluelineSpellcheckDictionary();
		Assertions.assertTrue(bluelineSpellcheckDictionaryWindowAppeared, "Blueline Spellcheck Dictionary window appeared.");

		spellcheckManagerPage().addWord(newWord);
		spellcheckManagerFiltersPage().setFilterWord(newWord);
		boolean bluelineDictionaryWordAppears =  spellcheckManagerGridPage().isWordInGrid(newWord);
		boolean bluelineDictionaryWordHasNewStatus = spellcheckManagerGridPage().doesGivenWordHaveNewStatus(newWord);
		String userNameInGridNod = spellcheckManagerGridPage().getWordUserName(newWord);
		String dateInGridNod = spellcheckManagerGridPage().getWordDate(newWord);
		String wordsGroupNod = SpellcheckManagerDatabaseUtils.getWordsGroupForWord(newWord);
		spellcheckManagerGridPage().removeWordFromSpellcheckDictionaryWithPlusMinusButton(newWord);
		boolean bluelineDictionaryWordIsRemoved = spellcheckManagerGridPage().doesGivenWordHaveRemovedStatus(newWord);
		spellcheckManagerPage().closeSpellcheckManagerWindow();
		DateAndTimeUtils.takeNap(DateAndTimeUtils.TWO_SECONDS);
		hierarchyMenu().goToNavigate();
		toolsMenu().goToSpellcheckManager();
		spellcheckManagerFiltersPage().setFilterWord(newWord);
		boolean wordIsStillInToolsSpellcheckManager = spellcheckManagerGridPage().isWordInGrid(newWord);
		boolean wordStatusIsStillSetToNewInToolsSpellcheckManager = spellcheckManagerGridPage().doesGivenWordHaveNewStatus(newWord);
		String userNameInGridToolsSecondTime = spellcheckManagerGridPage().getWordUserName(newWord);
		String dateInGridToolsSecondTime = spellcheckManagerGridPage().getWordDate(newWord);
		spellcheckManagerGridPage().removeWordFromSpellcheckDictionaryWithPlusMinusButton(newWord);
		boolean toolsSpellcheckManagerWordIsRemoved = spellcheckManagerGridPage().doesGivenWordHaveRemovedStatus(newWord);
		SpellcheckManagerDatabaseUtils.deleteWordFromDatabase(newWord, currentContentSet);

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(toolsSpellcheckManagerWordAppears, "Our word appeared after adding it in Tools->Spellcheck Manager"),
			() -> Assertions.assertTrue(toolsSpellcheckManagerWordHasNewStatus, "Our word has a status of 'new' after adding it in Tools->Spellcheck Manager"),
			() -> Assertions.assertEquals(userName, userNameInGridTools, "Our word has the right user name in the 'User' column after adding it in Tools->Spellcheck Manager"),
			() -> Assertions.assertEquals(currentDate, dateInGridTools, "Our word has the right date in the 'Date' column after adding it in Tools->Spellcheck Manager"),
			() -> Assertions.assertEquals("editor", wordsGroupTools, "Our word has the right WORDS_GROUP value in the database after adding it in Tools->Spellcheck Manager"),
			() -> Assertions.assertTrue(bluelineDictionaryWordAppears, "Our word appeared after adding it in NOD->Blueline Spellcheck Dictionary"),
			() -> Assertions.assertTrue(bluelineDictionaryWordHasNewStatus, "Our word has a status of 'new' after adding it in NOD->Blueline Spellcheck Dictionary"),
			() -> Assertions.assertEquals(userName, userNameInGridNod, "Our word has the right user name in the 'User' column after adding it in NOD->Blueline Spellcheck Dictionary"),
			() -> Assertions.assertEquals(currentDate, dateInGridNod, "Our word has the right date in the 'Date' column after adding it in NOD->Blueline Spellcheck Dictionary"),
			() -> Assertions.assertEquals("blueline", wordsGroupNod, "Our word has the right WORDS_GROUP value in the database after adding it in NOD->Blueline Spellcheck Dictionary"),
			() -> Assertions.assertTrue(bluelineDictionaryWordIsRemoved, "Our word has a status of 'removed' after removing it in NOD->Blueline Spellcheck Dictionary"),
			() -> Assertions.assertTrue(wordIsStillInToolsSpellcheckManager, "Our word appears after switching back to Tools->Spellcheck Manager"),
			() -> Assertions.assertTrue(wordStatusIsStillSetToNewInToolsSpellcheckManager, "Our word has a status of 'new' after switching back to Tools->Spellcheck Manager"),
			() -> Assertions.assertEquals(userName, userNameInGridToolsSecondTime, "Our word has the right user name in the 'User' column after switching back to Tools->Spellcheck Manager"),
			() -> Assertions.assertEquals(currentDate, dateInGridToolsSecondTime, "Our word has the right date in the 'Date' column after switching back to Tools->Spellcheck Manager"),
			() -> Assertions.assertTrue(bluelineDictionaryWordIsRemoved, "Our word has a status of 'removed' after removing it in NOD->Blueline Spellcheck Dictionary"),
			() -> Assertions.assertTrue(toolsSpellcheckManagerWordIsRemoved, "Our word has a status of 'removed' after removing it in Tools->Spellcheck Manager")
		);
	}
}
