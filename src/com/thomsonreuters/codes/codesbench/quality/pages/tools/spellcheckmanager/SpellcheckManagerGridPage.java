package com.thomsonreuters.codes.codesbench.quality.pages.tools.spellcheckmanager;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.tools.spellcheckmanager.SpellcheckManagerGridPageElements.NO_RECORDS_FOUND;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.SpellcheckManagerContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.spellcheckmanager.SpellcheckManagerGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.spellcheckmanager.SpellcheckManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;
import com.thomsonreuters.codes.codesbench.quality.utilities.autoIT.AutoITUtils;

@Component
public class SpellcheckManagerGridPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public SpellcheckManagerGridPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SpellcheckManagerGridPageElements.class);
	}
	
	/**
	 * Right clicks the given word.
	 *
	 * @param targetWord
	 */
	public void rightClickWord(String targetWord)
	{
		//click(String.format(SpellcheckManagerGridPageElements.WORD_VALUES_IN_GRID, targetWord));
		waitForElementToBeClickable(String.format(SpellcheckManagerGridPageElements.WORD_VALUES_IN_GRID, targetWord));
		click(String.format(SpellcheckManagerGridPageElements.WORD_VALUES_IN_GRID, targetWord));
		click(String.format(SpellcheckManagerGridPageElements.WORD_VALUES_IN_GRID, targetWord));
		rightClick(getElement(String.format(SpellcheckManagerGridPageElements.WORD_VALUES_IN_GRID, targetWord)));
	}
	
	/**
	 * Removes target word from the spell check dictionary using the minus button. Return true if the
	 * alert appears when deleting the word. Also returns true if the word was not in the dictionary to
	 * begin with.
	 *
	 * @param targetWord
	 * @throws 
	 */
	public boolean removeWordUsingMinusButton(String targetWord)  
	{
		if(doesGivenWordHaveNewStatus(targetWord))
		{
			click(String.format(SpellcheckManagerGridPageElements.MINUS_BUTTON, targetWord));
			spellcheckManagerPage().checkRemoveWordAlertAppeared(targetWord);
		}
		waitForPageLoaded();
		return !doesGivenWordHaveNewStatus(targetWord);
	}
	
	public boolean wordsStatesMatchCondition(String... states)
	{
		List<String> actualStates = getElements(SpellcheckManagerGridPageElements.WORD_STATES_IN_GRID).
				stream().map(WebElement::getText).collect(Collectors.toList());

		switch (states.length)
		{
		case 2:
			return actualStates.stream().allMatch(state -> state.equals(states[0]) || state.equals(states[1]));
		case 1:
			return actualStates.stream().allMatch(state -> state.equals(states[0]));
		default:
			return false;
		}
	}
	
	/**
	 * Checks if a word is already in the spell check dictionary.
	 *
	 * @param word
	 * @return
	 * @throws 
	 */
	public boolean isWordInGrid(String word)
	{
		return doesElementExist(String.format(SpellcheckManagerGridPageElements.WORD_VALUES_IN_GRID, word));
	}
	
	/**
	 * Check if the given words are in order. If upperWord is higher on the screen, true is returned, if
	 * not, false is returned.
	 *
	 * @param words
	 * @return
	 */
	public boolean areWordsInOrder(String... words)
	{
		if (words.length == 1)
		{
			return true;
		}
		
		List<String> wordsInGrid =  SpellcheckManagerGridPageElements.wordElementsInGrid.stream().map(WebElement::getText).collect(Collectors.toList());
		
		for (int i = 0; i < words.length - 1; i++)
		{
			if(wordsInGrid.indexOf(words[i]) > wordsInGrid.indexOf(words[i + 1]))
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Checks if target word has removed status. Only works if the word is currently in the grid.
	 * 
	 * @param targetWord
	 * @return
	 * @throws 
	 */
	public boolean doesGivenWordHaveRemovedStatus(String targetWord) 
	{
		return doesElementExist(String.format(SpellcheckManagerGridPageElements.REMOVED_WORD_STATUS, targetWord));
		//return doesElementExist(String.format(SpellcheckManagerGridPageElements.REMOVED_WORD_STATUS_2, targetWord));
	}
	
	/**
	 * Checks if target word has new status. Only works if the word is currently in the grid.
	 * 
	 *
	 * @param targetWord
	 * @return
	 */
	public boolean doesGivenWordHaveNewStatus(String targetWord)
	{
		return doesElementExist(String.format(SpellcheckManagerGridPageElements.NEW_WORD_STATUS, targetWord));
	}

	public String getWordUserName(String targetWord)
	{
		return getElementsText(String.format(SpellcheckManagerGridPageElements.GIVEN_WORD_USER_XPATH, targetWord));
	}

	public String getWordDate(String targetWord)
	{
		return getElementsText(String.format(SpellcheckManagerGridPageElements.GIVEN_WORD_DATE_XPATH, targetWord));
	}
	
	/**
	 * Checks if target word has the plus button. Only works if the word is currently in the grid.
	 *
	 * @param targetWord
	 * @return
	 */
	public boolean doesGivenWordHavePlusButton(String targetWord)
	{
		return doesElementExist(String.format(SpellcheckManagerGridPageElements.PLUS_BUTTON, targetWord));
	}
	
	/**
	 * Checks if target word has the minus button. Only works if the word is currently in the grid.
	 *
	 * @param targetWord
	 * @return
	 */
	public boolean doesGivenWordHaveMinusButton(String targetWord)
	{
		return doesElementExist(String.format(SpellcheckManagerGridPageElements.MINUS_BUTTON, targetWord));
	}
	
	/**
	 * Checks to see if all of the words currently in the grid match the given word states. Returns true
	 * if they do, false if not. Valid states are "new" and "removed", case sensitive.
	 * 
	 * @param states
	 * @return
	 */
	private boolean wordStatesCurrentlyInGridMatchGiven(String... states)
	{
		boolean stateIsInvalid = Arrays.asList(states).stream().noneMatch(state -> state.equals("new") || state.equals("removed"));
		
		if (stateIsInvalid)
		{
			Assertions.fail("Invalid state used expected: \"new\" or \"removed\"");
		}
		
		List<String> actualStates = getElements(SpellcheckManagerGridPageElements.WORD_STATES_IN_GRID).stream().map(WebElement::getText).collect(Collectors.toList());
		
		switch (states.length)
		{
			case 2:
				return actualStates.stream().allMatch(state -> state.equals(states[0]) || state.equals(states[1]));
				
			case 1:
				return actualStates.stream().allMatch(state -> state.equals(states[0]));
				
			default:
				return false;
		}
	}
	
	/**
	 * Checks to see if all of the words currently in the grid have the new state. Returns true if they
	 * do, false if not.
	 * 
	 * @return
	 */
	public boolean areAllWordStatesInGridNew()
	{
		return wordStatesCurrentlyInGridMatchGiven("new");
	}
	
	/**
	 * Checks to see if all of the words currently in the grid have the new removed. Returns true if
	 * they do, false if not.
	 * 
	 * @return
	 */
	public boolean areAllWordStatesInGridRemoved()
	{
		return wordStatesCurrentlyInGridMatchGiven("removed");
	}
	
	/**
	 * Checks to see if all of the words currently in the grid have the new and removed state. Returns
	 * true if they do, false if not.
	 * 
	 * @return
	 */
	public boolean areAllWordStatesInGridNewAndRemoved()
	{
		return wordStatesCurrentlyInGridMatchGiven("new", "removed");
	}

	public boolean isGridEmpty()
	{
		return isElementDisplayed(NO_RECORDS_FOUND);
	}

	public boolean doesWordExistInTable(String word)
	{
		return doesElementExist(String.format(SpellcheckManagerGridPageElements.WORD_VALUES_IN_GRID, word));
	}

	public void removeWordFromSpellcheckDictionaryWithPlusMinusButton(String targetWord)
	{
		click(String.format(SpellcheckManagerGridPageElements.BUTTON_VALUE, targetWord));
		String alertMessage = "\""+targetWord+"\" will be removed from the jurisdictional Spellcheck list. Click ok to continue or Cancel to back out of request";
		checkAlertTextMatchesGivenText(alertMessage);
		waitForElementGone(String.format(SpellcheckManagerGridPageElements.WORD_VALUES_IN_GRID, targetWord));
	}

	public boolean checkWordStatus(String targetWord, String status)
	{
		return getElementsText(String.format(SpellcheckManagerPageElements.WORD_STATUS, targetWord)).contains(status);
	}

	public boolean checkWordMinusPlusButtonValue(String targetWord, String value)
	{
		return getElementsAttribute((String.format(SpellcheckManagerGridPageElements.BUTTON_VALUE, targetWord)), "value").equals(value);
	}

}
