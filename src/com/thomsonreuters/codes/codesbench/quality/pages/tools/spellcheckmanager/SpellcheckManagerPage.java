package com.thomsonreuters.codes.codesbench.quality.pages.tools.spellcheckmanager;

import java.util.List;

import javax.annotation.PostConstruct;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.spellcheckmanager.SpellcheckManagerGridPageElements;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.CommonPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.spellcheckmanager.SpellcheckManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;

@Component
public class SpellcheckManagerPage extends BasePage
{
	private final WebDriver driver;
	
	@Autowired
	public SpellcheckManagerPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SpellcheckManagerPageElements.class);
	}
	
	/**
	 * Adds the given word to the spell check dictionary. Does not check if the word already exists
	 * 
	 * @param newWord
	 * @throws 
	 */
	public void addWord(String newWord)
	{
		clearAndSendKeysToElement(SpellcheckManagerPageElements.newWordField, newWord);
		click(SpellcheckManagerPageElements.addButton);
		if(newWord != "")
		{
			waitForElement(String.format(SpellcheckManagerGridPageElements.WORD_VALUES_IN_GRID, newWord));
		}
	}
	
	/**
	 * Adds the given word to the spell check dictionary. Checks to see if the word is already there, if
	 * it is the word is removed using the minus button. The alert is automatically accepted. Returns
	 * true if the word is successfully added to the spell check manager.
	 *
	 * @param newWord
	 * @throws 
	 * @return
	 */
	public boolean addWordDeleteIfExists(String newWord)  
	{
		if(spellcheckManagerGridPage().doesGivenWordHaveNewStatus(newWord))
		{
			
			boolean wordIsNotInDictionary;
			final boolean correctAlertPopsUp = spellcheckManagerGridPage().removeWordUsingMinusButton(newWord);
			
			waitForElementGone(CommonPageElements.PROCESSING_PLEASE_WAIT_XPATH);
			
			if(isShowDeletedChecked())
			{
				wordIsNotInDictionary = spellcheckManagerGridPage().doesGivenWordHaveRemovedStatus(newWord);
			}
			else
			{
				wordIsNotInDictionary = !spellcheckManagerGridPage().isWordInGrid(newWord);
			}
			
			if(!correctAlertPopsUp)
			{
				Assertions.fail("The correct alert did not pop up when adding " + newWord);
			}
			else if (!wordIsNotInDictionary)
			{
				Assertions.fail("The word was not deleted: " + newWord);
			}
		}
		
		addWord(newWord);
		
		return spellcheckManagerGridPage().doesGivenWordHaveNewStatus(newWord);
	}
	
	/**
	 * Adds a given word if it is not already in the dictionary.
	 * 
	 * @param newWord
	 * @return
	 * @throws 
	 */
	public boolean addWordIfNew(String newWord)  
	{
		if(!spellcheckManagerGridPage().doesGivenWordHaveNewStatus(newWord))
		{
			addWord(newWord);
		}
		
		return spellcheckManagerGridPage().doesGivenWordHaveNewStatus(newWord);
	}
	
	/**
	 * Closes the spell check page and waits for it to close. Returns true if it gets closed, false if
	 * not.
	 *
	 * @return
	 * @throws 
	 */
	public boolean closeSpellcheckManagerWindow()  
	{
		click(SpellcheckManagerPageElements.closeButton);
		AutoITUtils.verifyAlertTextAndAccept(true, "Are you sure you want to close?");
		waitForWindowGoneByTitle(SpellcheckManagerPageElements.SPELLCHECK_MANAGER_PAGE_TITLE);
		return !checkWindowIsPresented(SpellcheckManagerPageElements.SPELLCHECK_MANAGER_PAGE_TITLE);
	}
	
	public boolean switchToPage()
	{
		return switchToWindow(SpellcheckManagerPageElements.SPELLCHECK_MANAGER_PAGE_TITLE);
	}
	
	public void addNewWordToSpellcheckDictionary(String newWord)  
	{	
		clearAndSendKeysToElement(SpellcheckManagerPageElements.newWordField, newWord);
		click(SpellcheckManagerPageElements.addButton);
		waitForElement(String.format(SpellcheckManagerGridPageElements.WORD_VALUES_IN_GRID, newWord));
	}
	
	public void addEmptyWordToSpellcheckDictionary()  
	{	
		clearAndSendKeysToElement(SpellcheckManagerPageElements.newWordField, "");
		click(SpellcheckManagerPageElements.addButton);
	}
	
	public void rightClickWord(String targetWord)
	{
		click(String.format(SpellcheckManagerGridPageElements.WORD_VALUES_IN_GRID, targetWord));
		rightClick(String.format(SpellcheckManagerGridPageElements.WORD_VALUES_IN_GRID, targetWord));
	}

	public boolean checkWordsOrder(String upperWord, String lowerWord) 
	{
		List<String> words = getElementsTextList(SpellcheckManagerGridPageElements.ALL_WORDS_IN_DICTIONARY);
		int indexOfUpperWord = words.indexOf(upperWord);
		int indexOfLowerWord = words.indexOf(lowerWord);
		return (indexOfUpperWord < indexOfLowerWord);
	}

	/**
	 * Hides the deleted words.
	 */
	public void uncheckShowDeleted()
	{
		uncheckCheckbox(SpellcheckManagerPageElements.showDeletedCheckbox);
		waitForPageLoaded();
	}
	
	/**
	 * Shows the deleted words.
	 */
	public void checkShowDeleted()
	{
		checkCheckbox(SpellcheckManagerPageElements.showDeletedCheckbox);
		waitForPageLoaded();
	}
	
	/**
	 * Returns true if show deleted is checked and false if not.
	 * 
	 * @return
	 */
	public boolean isShowDeletedChecked()
	{
		return isElementSelected(SpellcheckManagerPageElements.showDeletedCheckbox);
	}
	
	/**
	 * Checks if the correct alert appeared after a word was removed.
	 *
	 * @param targetWord
	 * @return
	 * @throws 
	 */
	public boolean checkRemoveWordAlertAppeared(String targetWord)  
	{
		boolean alertTextIsCorrect = AutoITUtils.verifyAlertTextContainsAndAccept(true, "will be removed from the jurisdictional Spellcheck list. Click ok to continue or Cancel to back out of request");
		spellcheckManagerPage().switchToPage();
		waitForElementGone(String.format(SpellcheckManagerGridPageElements.WORD_VALUES_IN_GRID, targetWord));
		return alertTextIsCorrect;
	}
}
