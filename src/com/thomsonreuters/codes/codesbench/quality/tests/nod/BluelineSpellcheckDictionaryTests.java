package com.thomsonreuters.codes.codesbench.quality.tests.nod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thomsonreuters.codes.codesbench.quality.utilities.TestService;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;

public class BluelineSpellcheckDictionaryTests extends TestService
{	
	String newWord = "KERGUDU";

	/**
	 * STORY/BUG - HALCYONST-1478 <br>
	 * SUMMARY - Adds a new misspelled word to the Blueline Spellcheck Dictionary and then creates a blueline from the headnotes page with the new word as
	 * its text, checking to see if a spelling error message appears <br>
	 * USER - Legal <br>
	 */
	@Test
	@LEGAL
	@LOG
	public void spellcheckDictionaryInBluelinesTest() 
	{	
		String newWord = "MispelledPotaetoe";
		String node = "12.2";

		homePage().goToHomePage();
		loginPage().logIn();

		boolean bluelineSpellCheckDictionaryWindowAppeared = nodMenu().goToBluelineSpellcheckDictionary();
		Assertions.assertTrue(bluelineSpellCheckDictionaryWindowAppeared, "Blueline spellcheck dictionary window should appear");

		//Add new word to dictionary
		spellcheckManagerPage().uncheckShowDeleted();
		boolean doesWordAlreadyExist = spellcheckManagerGridPage().doesWordExistInTable(newWord);
		if(doesWordAlreadyExist)
		{
			spellcheckManagerGridPage().removeWordFromSpellcheckDictionaryWithPlusMinusButton(newWord);
		}
		spellcheckManagerPage().addNewWordToSpellcheckDictionary(newWord);
		boolean newWordAdded = spellcheckManagerGridPage().doesWordExistInTable(newWord);
		Assertions.assertTrue(newWordAdded, "New word should be added");
		boolean bluelineSpellCheckDictionaryWindowDisappeared = spellcheckManagerPage().closeSpellcheckManagerWindow();
		Assertions.assertTrue(bluelineSpellCheckDictionaryWindowDisappeared, "Blueline spellcheck dictionary window should disappear");
		homePage().switchToPage();

		// go to subscribed cases
		nodMenu().goToSubscribedCases();

		// open first Case and perform Quick search
		String caseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		boolean headnotesPageAppeared = subscribedCasesGridPage().clickCaseSerialNumberLink(caseSerialNumber);
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes page did not appear as expected.");
		headnotesSearchPage().setQuickFindField(node);
		headnotesSearchPage().clickQuickFindButton();

		//insert first BL and check spelling error message
		headnotesPageAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesPageAppeared, "The headnotes page did not appear");
		headnotesSearchPage().waitForGridRefresh();
		headnotesTreePage().rightClickNodeWithGivenText(node);
		boolean insertBluelinePageAppeared = headnotesContextMenu().insertBlueline();
		Assertions.assertTrue(insertBluelinePageAppeared, "Insert blueline page did not appear as expected");

		insertBluelinePage().setBlueLineNumber("1");
		insertBluelinePage().clickOk();
		insertBluelinePage().selectBlueLineTypeFlush();
		insertBluelinePage().setBlueLineText(newWord);
		insertBluelinePage().clickOk();

		boolean spellCheckAppearedFirstCase = insertBluelinePage().checkForSpellingErrorMessage();
		Assertions.assertFalse(spellCheckAppearedFirstCase, "Error Message appeared");

		headnotesPage().switchToHeadnotesPage();
		boolean treeControlGoesToInsertedBlueLineFirstCase = headnotesTreePage().isNodeHighlightedInTree(newWord);
		Assertions.assertTrue(treeControlGoesToInsertedBlueLineFirstCase, "Tree goes to new BL");

		// delete BL
		headnotesTreePage().rightClickNodeWithGivenText(newWord);
		boolean deleteBluelineWindowAppeared = headnotesContextMenu().deleteBlueline();
		Assertions.assertTrue(deleteBluelineWindowAppeared);

		headnotesPageAppeared = deleteBluelinePage().clickOk();
		Assertions.assertTrue(headnotesPageAppeared, "Headnotes Page did not appear.");

		headnotesPage().closeCurrentWindowIgnoreDialogue();
		homePage().switchToPage();

		nodMenu().goToBluelineSpellcheckDictionary();
		spellcheckManagerPage().uncheckShowDeleted();
		spellcheckManagerGridPage().removeWordFromSpellcheckDictionaryWithPlusMinusButton(newWord);
		spellcheckManagerFiltersPage().filterByWord(newWord);
		boolean wordIsRemoved = !spellcheckManagerGridPage().doesWordExistInTable(newWord);
		Assertions.assertTrue(wordIsRemoved, "New word should be removed");
		spellcheckManagerPage().closeSpellcheckManagerWindow();

		// go to subs-d cases
		homePage().switchToPage();
		nodMenu().goToSubscribedCases();

		// open first Case and perform Quick search
		caseSerialNumber = subscribedCasesGridPage().getCaseSerialNumberFromSubscribedCasesByGivenRow(1);
		subscribedCasesGridPage().clickCaseSerialNumberLink(caseSerialNumber);
		headnotesSearchPage().setQuickFindField(node);
		headnotesSearchPage().clickQuickFindButton();

		// create BL and check for error message
		headnotesPageAppeared = headnotesPage().switchToHeadnotesPage();
		Assertions.assertTrue(headnotesPageAppeared, "The headnotes page did not appear");
		headnotesSearchPage().waitForGridRefresh();
		headnotesTreePage().rightClickNodeWithGivenText(node);
		insertBluelinePageAppeared = headnotesContextMenu().insertBlueline();
		Assertions.assertTrue(insertBluelinePageAppeared);

		insertBluelinePage().setBlueLineNumber("1");
		insertBluelinePage().clickOk();
		insertBluelinePage().selectBlueLineTypeFlush();
		insertBluelinePage().setBlueLineText(newWord);
		insertBluelinePage().clickOk();

		boolean spellCheckAppearedSecondCase = insertBluelinePage().checkForSpellingErrorMessage();
		Assertions.assertTrue(spellCheckAppearedSecondCase, "Error message should appear");
	}

	/**
	 * STORY/BUG - HALCYONST-1478 <br>
	 * SUMMARY - Checks the alerts when an empty word (a word with no text) is added the Blueline Spellcheck Dictionary.<br>
	 * USER - Legal<br>
	 */
	@Test
	@LEGAL
	@LOG
	public void spellCheckEmptyWordTest()
	{
		homePage().goToHomePage();
		loginPage().logIn();

		boolean bluelineSpellcheckDictionaryWindowAppeared = nodMenu().goToBluelineSpellcheckDictionary();
		Assertions.assertTrue(bluelineSpellcheckDictionaryWindowAppeared, "Blueline spellcheck dictionary window should appear");

		// try to add empty word
		boolean doesWordAlreadyExist = spellcheckManagerGridPage().doesWordExistInTable(newWord);
		if(doesWordAlreadyExist)
		{
			spellcheckManagerGridPage().removeWordFromSpellcheckDictionaryWithPlusMinusButton(newWord);
		}
		spellcheckManagerPage().addEmptyWordToSpellcheckDictionary();
		boolean alertAppeared = spellcheckManagerPage().getAcceptAlert("No value has been added, please add a value and try again");
		boolean noEmptyWordAdded = !spellcheckManagerGridPage().doesWordExistInTable("");

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(noEmptyWordAdded, "No empty word is added to the dictionary"),
			() -> Assertions.assertTrue(alertAppeared, "Alert appeared telling the user to add a word before clicking Add")
		);
	}

	/**
	 * STORY/BUG - HALCYONST-1478 <br>
	 * SUMMARY - Checks if the correct alert appears when attempting to add an already existing word to the Blueline Spellcheck Dictionary. <br>
	 * USER - Legal <br>
	 */
	@Test
	@LEGAL
	@LOG
	public void spellCheckDuplicateWordAndRemoveWordsViaContextMenuTest() 
	{
		homePage().goToHomePage();
		loginPage().logIn();

		String anotherWord = "KerGUDU";

		boolean bluelineSpellcheckDictionaryWindowAppeared = nodMenu().goToBluelineSpellcheckDictionary();
		Assertions.assertTrue(bluelineSpellcheckDictionaryWindowAppeared, "Blueline spellcheck dictionary window should appear");
		spellcheckManagerPage().uncheckShowDeleted();

		// add the new word
		boolean doesWordAlreadyExist = spellcheckManagerGridPage().doesWordExistInTable(newWord);
		if(doesWordAlreadyExist)
		{
			spellcheckManagerGridPage().removeWordFromSpellcheckDictionaryWithPlusMinusButton(newWord);
		}
		spellcheckManagerPage().addNewWordToSpellcheckDictionary(newWord);
		boolean newWordAdded = spellcheckManagerGridPage().doesWordExistInTable(newWord);
		Assertions.assertTrue(newWordAdded, "New word has been added to dictionary");

		// try to add the new word again
		spellcheckManagerPage().addNewWordToSpellcheckDictionary(newWord);
		boolean duplicateWordAlertAppeared = spellcheckManagerPage().getAcceptAlert("Added word already exists on spellcheck list");
		Assertions.assertTrue(duplicateWordAlertAppeared, "Alert appeared saying the word exists already");

		// add another word
		doesWordAlreadyExist = spellcheckManagerGridPage().doesWordExistInTable(anotherWord);
		if(doesWordAlreadyExist)
		{
			spellcheckManagerGridPage().removeWordFromSpellcheckDictionaryWithPlusMinusButton(anotherWord);
		}
		spellcheckManagerPage().addNewWordToSpellcheckDictionary(anotherWord);
		boolean anotherWordAdded = spellcheckManagerGridPage().doesWordExistInTable(anotherWord);
		Assertions.assertTrue(anotherWordAdded, "Another word has been added to dictionary");

		// delete words added
		spellcheckManagerFiltersPage().setFilterWord(newWord);
		spellcheckManagerPage().rightClickWord(newWord);
		spellcheckManagerContextMenu().removeWord();
		boolean newWordRemoveAlertAppeared = spellcheckManagerPage().getAcceptAlert("\"" + newWord + "\"" + " will be removed from the jurisdictional Spellcheck list. "
				+ "Click ok to continue or Cancel to back out of request");
		Assertions.assertTrue(newWordRemoveAlertAppeared, "New word has been removed alert did not appear");

		spellcheckManagerFiltersPage().setFilterWord(anotherWord);
		spellcheckManagerPage().rightClickWord(anotherWord);
		spellcheckManagerContextMenu().removeWord();
		boolean anotherWordRemoveAlertAppeared = spellcheckManagerPage().getAcceptAlert("\"" + anotherWord + "\"" + " will be removed from the jurisdictional Spellcheck list. "
				+ "Click ok to continue or Cancel to back out of request");
		Assertions.assertTrue(anotherWordRemoveAlertAppeared, "Another word has been removed alert appeared");

		boolean noNewWordsLeftAfterTest = (!spellcheckManagerGridPage().doesWordExistInTable(newWord)) 
				&& (!spellcheckManagerGridPage().doesWordExistInTable(anotherWord));
		Assertions.assertTrue(noNewWordsLeftAfterTest, "No new words left after the test");
	}

	/**
	 * STORY/BUG - HALCYONST-769 <br>
	 * SUMMARY - Checks the functionality of the removal of a word using the Plus-Minus button in the Blueline SpellcheckDictionary <br>
	 * USER - Legal <br>
	 */
	@Test
	@LEGAL
	@LOG
	public void spellCheckDeleteWordTest() 
	{    	
		homePage().goToHomePage();
		loginPage().logIn();

		boolean bluelineSpellcheckDictionaryWindowAppeared = nodMenu().goToBluelineSpellcheckDictionary();
		Assertions.assertTrue(bluelineSpellcheckDictionaryWindowAppeared, "Blueline spellcheck dictionary window should appear");

		spellcheckManagerPage().uncheckShowDeleted();

		// add the new word
		boolean doesWordAlreadyExist = spellcheckManagerGridPage().doesWordExistInTable(newWord);
		if(doesWordAlreadyExist)
		{
			spellcheckManagerGridPage().removeWordFromSpellcheckDictionaryWithPlusMinusButton(newWord);
		}
		spellcheckManagerPage().addNewWordToSpellcheckDictionary(newWord);
		// remove the new word
		spellcheckManagerGridPage().removeWordFromSpellcheckDictionaryWithPlusMinusButton(newWord);
		boolean wordIsRemoved = !spellcheckManagerGridPage().doesWordExistInTable(newWord);

		// show deleted
		spellcheckManagerPage().checkShowDeleted();
		boolean removedWordIsDisplayed = spellcheckManagerGridPage().doesWordExistInTable(newWord);
		spellcheckManagerFiltersPage().filterByWord(newWord);
		boolean statusIsRemoved = spellcheckManagerGridPage().checkWordStatus(newWord, "removed");
		boolean minusIsNowPlus = spellcheckManagerGridPage().checkWordMinusPlusButtonValue(newWord, "+");

		Assertions.assertAll
		(
			() -> Assertions.assertTrue(wordIsRemoved, "New word has not been removed from the dictionary"),
			() -> Assertions.assertTrue(removedWordIsDisplayed, "Removed word is not displayed"),
			() -> Assertions.assertTrue(statusIsRemoved, "Status of removed word is not \"removed\""),
			() -> Assertions.assertTrue(minusIsNowPlus, "There is not an Add-Plus button instead of Remove-Minus button now")
		);

	}

	/**
	 * STORY/BUG - HALCYONST-770 <br>
	 * SUMMARY - Checks the functionality of the filtering and sorting features of the Blueline Spellcheck Dictionary. <br>
	 * USER - Legal <br>
	 */
	@Test
	@LEGAL
	@LOG 
	public void spellCheckFilteringAndSortingTest()
	{
		String firstWord = "1" + newWord;
		String secondWord = "2" + newWord;

		homePage().goToHomePage();
		loginPage().logIn();

		boolean bluelineSpellcheckDictionaryWindowAppeared = nodMenu().goToBluelineSpellcheckDictionary();
		Assertions.assertTrue(bluelineSpellcheckDictionaryWindowAppeared, "Blueline spellcheck dictionary window should appear");

		spellcheckManagerPage().uncheckShowDeleted();

		// add new words
		boolean doesWordAlreadyExist = spellcheckManagerGridPage().doesWordExistInTable(firstWord);
		if(doesWordAlreadyExist)
		{
			spellcheckManagerGridPage().removeWordFromSpellcheckDictionaryWithPlusMinusButton(firstWord);
		}
		spellcheckManagerPage().addNewWordToSpellcheckDictionary(firstWord);
		
		doesWordAlreadyExist = spellcheckManagerGridPage().doesWordExistInTable(secondWord);
		if(doesWordAlreadyExist)
		{
			spellcheckManagerGridPage().removeWordFromSpellcheckDictionaryWithPlusMinusButton(secondWord);
		}
		spellcheckManagerPage().addNewWordToSpellcheckDictionary(secondWord);

		// sort by ascending word
		spellcheckManagerFiltersPage().clickWordSortAscendingAndDescending();
		boolean ascendingCheck = spellcheckManagerPage().checkWordsOrder(firstWord, secondWord);
		Assertions.assertTrue(ascendingCheck, "The 1KERGUDU is higher than 2KERGUDU with ascending sorting");

		// sort by descending word
		spellcheckManagerFiltersPage().clickWordSortAscendingAndDescending();
		//This is the same issue as the other test, due to how the spellcheck manager doesnt update quick enough
		boolean descendingCheck = spellcheckManagerPage().checkWordsOrder(secondWord, firstWord);
		Assertions.assertTrue(descendingCheck, "The 2TEST is higher than 1TEST with descending sorting");

		// filter
		spellcheckManagerFiltersPage().setFilterWord(firstWord);
		boolean firstWordHasBeenFilteredOut = (spellcheckManagerGridPage().doesWordExistInTable(firstWord)) 
				&& (!spellcheckManagerGridPage().doesWordExistInTable(secondWord));
		Assertions.assertTrue(firstWordHasBeenFilteredOut, "The filtered word appeared in the grid");

		// delete words
		// when some sorting is enabled and you remove some word -- sorting is being disabled
		spellcheckManagerGridPage().removeWordFromSpellcheckDictionaryWithPlusMinusButton(firstWord);
		spellcheckManagerFiltersPage().setFilterWord(secondWord);
		spellcheckManagerGridPage().removeWordFromSpellcheckDictionaryWithPlusMinusButton(secondWord);
		boolean noNewWordsLeftAfterTest = (!spellcheckManagerGridPage().doesWordExistInTable(firstWord)) 
				&& (!spellcheckManagerGridPage().doesWordExistInTable(secondWord));
		Assertions.assertTrue(noNewWordsLeftAfterTest, "No new words left after the test");
	}
}
