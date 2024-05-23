package com.thomsonreuters.codes.codesbench.quality.pages.tools.spellcheckmanager;

import javax.annotation.PostConstruct;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.spellcheckmanager.SpellcheckManagerFiltersPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.spellcheckmanager.SpellcheckManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class SpellcheckManagerFiltersPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public SpellcheckManagerFiltersPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, SpellcheckManagerFiltersPageElements.class);
	}
	
	/**
	 * Sets Add/Removed filter to Removed. Returns true if the filter is changed, false if not.
	 * 
	 * @return
	 */
	public boolean setAddRemovedFilterToRemoved()
	{
		selectDropdownOptionUsingJavascript(SpellcheckManagerFiltersPageElements.ADD_REMOVED_FILTER_DROPDOWN_ID,"removed");
		SpellcheckManagerFiltersPageElements.addRemovedFilterDropdown.sendKeys(Keys.UP);
		SpellcheckManagerFiltersPageElements.addRemovedFilterDropdown.sendKeys(Keys.DOWN);
		waitForPageLoaded();
		return isAddRemovedFilterRemoved();
	}
	
	/**
	 * Sets Add/Removed filter to blank. Returns true if the filter is changed, false if not.
	 * 
	 * @return
	 */
	public boolean setAddRemovedFilterToBlank()
	{
		selectDropdownOptionUsingJavascript(SpellcheckManagerFiltersPageElements.ADD_REMOVED_FILTER_DROPDOWN_ID,"");
		SpellcheckManagerFiltersPageElements.addRemovedFilterDropdown.sendKeys(Keys.DOWN);
		SpellcheckManagerFiltersPageElements.addRemovedFilterDropdown.sendKeys(Keys.UP);
		waitForPageLoaded();
		return isAddRemovedFilterBlank();
	}
	
	/**
	 * Returns true if add/removed filter is blank and false if not.
	 * 
	 * @return
	 */
	public boolean isAddRemovedFilterBlank()
	{
		return getElementsAttribute(SpellcheckManagerFiltersPageElements.addRemovedFilterDropdown, "value").equals("");
	}
	
	/**
	 * Returns true if add/removed filter is new and false if not.
	 * 
	 * @return
	 */
	public boolean isAddRemovedFilterNew()
	{
		return getElementsAttribute(SpellcheckManagerFiltersPageElements.addRemovedFilterDropdown, "value").equals("new");
	}
	
	/**
	 * Returns true if add/removed filter is removed and false if not.
	 * 
	 * @return
	 */
	public boolean isAddRemovedFilterRemoved()
	{
		return getElementsAttribute(SpellcheckManagerFiltersPageElements.addRemovedFilterDropdown, "value").equals("removed");
	}
	
	/**
	 * Sorts grid by word ascending.
	 */
	public void sortByWordAscending()
	{
		if(getElementsAttribute(SpellcheckManagerFiltersPageElements.wordColumnButton, "title").equals("Click to sort ascending"))
		{
			click(SpellcheckManagerFiltersPageElements.wordColumnButton);
		}
		waitForPageLoaded();
	}
	
	/**
	 * Sorts grid by word descending.
	 */
	public void sortByWordDescending()
	{
		if(getElementsAttribute(SpellcheckManagerFiltersPageElements.wordColumnButton, "title").equals("Click to sort descending"))
		{
			click(SpellcheckManagerFiltersPageElements.wordColumnButton);
		}
		waitForPageLoaded();
	}
	
	/**
	 * Filters by the given word.
	 * 
	 * @param word
	 */
	public void filterByWord(String word)
	{
		clearAndSendKeysToElement(SpellcheckManagerFiltersPageElements.wordFilterTextbox, word);
		waitForPageLoaded();
	}
	
	/**
	 * Clears the word filtering box.
	 */
	public void clearWordFilter()
	{
		clear(SpellcheckManagerFiltersPageElements.wordFilterTextbox);
		waitForPageLoaded();
	}
	
	public void clickWordSortAscendingAndDescending()
	{
		click(SpellcheckManagerFiltersPageElements.wordSortAscendingDescending);
		waitForGridRefresh();
		waitForPageLoaded();
	}
	
	public void setFilterWord(String word)
	{
		clearAndSendTextToTextbox(SpellcheckManagerFiltersPageElements.wordFilterTextbox, word);
		waitForPageLoaded();
	}
}
