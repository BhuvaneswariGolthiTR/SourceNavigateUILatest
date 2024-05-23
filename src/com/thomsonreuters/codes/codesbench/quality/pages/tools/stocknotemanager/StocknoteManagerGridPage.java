package com.thomsonreuters.codes.codesbench.quality.pages.tools.stocknotemanager;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.StocknoteManagerContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknotePropertiesPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteSearchAndReplacePageElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteManagerGridPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

@Component
public class StocknoteManagerGridPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public StocknoteManagerGridPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, StocknoteManagerGridPageElements.class);
	}
	
	/**
	 * Right clicks the first stocknote.
	 */
	public void rightClickFirstStocknote()
	{
		click(StocknoteManagerGridPageElements.firstStocknoteName);
		rightClick(StocknoteManagerGridPageElements.firstStocknoteName);
	}
	
	/**
	 * Checks if there is a stocknote in the grid.
	 *
	 * @param stocknoteName
	 * @return
	 */
	public boolean isStocknoteInGrid(String stocknoteName)
	{
		return doesElementExist(String.format(StocknoteManagerGridPageElements.STOCKNOTE_BY_NAME, stocknoteName));
	}
	
	
	/**
	 * Checks if the first hot key matches.
	 *
	 * @param hotKey
	 * @return
	 */
	public boolean doesFirstHotKeyMatch(String hotKey)
	{
		return getElementsText(StocknoteManagerGridPageElements.firstStocknoteHotKey).equals(hotKey);
	}
	
	/**
	 * Checks if the first name matches.
	 *
	 * @param stocknoteName
	 * @return
	 */
	public boolean doesFirstStocknoteNameMatch(String stocknoteName)
	{
		return getFirstStocknoteName().equals(stocknoteName);
	}
	
	/**
	 * Returns a list of the hotkeys in alphabetical order on the current page.
	 *
	 * @return List of hotkeys.
	 */
	public List<String> getStocknotesHotKeys()
	{
		List<String> list = StocknoteManagerGridPageElements.stocknotesHotKeys.stream().map(WebElement::getText).collect(Collectors.toList());
		Collections.sort(list);
		return list;
	}
	
	/**
	 * Finds the stocknote name that goes with the given hotkey. If there is no match then an error will
	 * be thrown.
	 *
	 * @param hotkey
	 * @return Stocknote name that goes with the given hotkey.
	 */
	public String getStocknoteNameWithGivenHotKey(String hotkey)
	{
		return getElementsText(String.format(StocknoteManagerGridPageElements.STOCKNOTE_NAME_BY_HOTKEY, hotkey));
	}
	
	/**
	 * This method gets all the hotkey and stocknote name pairs that are on the current page in the
	 * stocknote manager. It puts them in a list of lists and the list is sort alphabetically by the
	 * hotkey(same as the excel file). A general format of the list follows. <br>
	 * <br>
	 * 
	 * List 1: HotKey, Stocknote Name (This list is always the same to match the excel file) <br>
	 * List 2: Alt-A, Stocknote One <br>
	 * List 3: Alt-D, Stocknote Two <br>
	 * List 4: Shift-Alt-R, Stocknote Three <br>
	 * List 5: Shift-Alt-Y, Stocknote Four <br>
	 * <br>
	 * 
	 * All of these lists are then put into a list in their numbered order.
	 *
	 * @return The list of hotkey and stocknote name pairs.
	 */
	public List<List<String>> getAllHotKeysAndStocknoteNames()
	{
		List<List<String>> hotkeysAndNamePairs = new ArrayList<>();
		List<String> stocknotesHotKeys = getStocknotesHotKeys();
		
		hotkeysAndNamePairs.add(Arrays.asList("Hotkey", "Stocknote Name"));
		stocknotesHotKeys.forEach(hotkey -> hotkeysAndNamePairs.add(Arrays.asList(hotkey, getStocknoteNameWithGivenHotKey(hotkey))));
		
		return hotkeysAndNamePairs;
	}
	
	/**
	 * Returns a boolean of whether all stocknotes in the grid match the current filter.
	 *
	 * @return Boolean of if the stocknote names match the filter
	 */
	public boolean allStocknotesMatchNameFilter()
	{
		return StocknoteManagerGridPageElements.stocknoteNames.stream().allMatch(e -> e.getText().contains(stocknoteManagerFiltersPage().getNameFilterValue()));
	}
	
	/**
	 * Gets all the stocknote names currently in the grid.
	 *
	 * @return List of stocknote names
	 */
	public List<String> getStocknotesNames()
	{
		List<String> list = StocknoteManagerGridPageElements.stocknoteNames.stream().map(WebElement::getText).collect(Collectors.toList());
		return list;
	}
	
	/**
	 * Right clicks the first stocknote in the grid that does not have a hot key.
	 */
	public void rightClickFirstStocknoteWithoutHotKey()
	{
		click(StocknoteManagerGridPageElements.firstStocknoteWithoutHotKey);
		rightClick(StocknoteManagerGridPageElements.firstStocknoteWithoutHotKey);
	}
	
	/**
	 * Checks whether a hot key is currently in the grid.
	 *
	 * @param hotKeyString
	 * @return True if it is in the grid, false if not.
	 */
	public Boolean isHotKeyInGrid(String hotKeyString)
	{
		return doesElementExist(String.format(StocknoteManagerGridPageElements.STOCKNOTE_BY_HOTKEY, hotKeyString));
	}
	
	/**
	 * Right clicks the stocknote with the given hot key.
	 *
	 * @param hotKeyString
	 */
	public void rightClickStocknoteWithHotKey(String hotKeyString)
	{
		click(String.format(StocknoteManagerGridPageElements.STOCKNOTE_BY_HOTKEY, hotKeyString));
		rightClick(String.format(StocknoteManagerGridPageElements.STOCKNOTE_BY_HOTKEY, hotKeyString));
	}
	
	/**
	 * Returns the name of the first stocknote in the grid.
	 *
	 * @return
	 */
	public String getFirstStocknoteName()
	{
		return getElementsText(StocknoteManagerGridPageElements.firstStocknoteName).trim();
		
	}
	
	/**
	 * Returns the category of the first stocknote in the grid.
	 *
	 * @return
	 */
	public String getFirstStocknoteCategory()
	{
		return getElementsText(StocknoteManagerGridPageElements.firstStocknoteCategory).trim();
	}
	
	/**
	 * Checks if the first stocknote is on the context menu.
	 *
	 * @return
	 */
	public boolean firstStocknoteOnContextMenuY()
	{
		return isElementTextY(StocknoteManagerGridPageElements.firstStocknoteOnContextMenu);
	}
	
	/**
	 * Returns the hotkey of the first stocknote in the grid.
	 *
	 * @return
	 */
	public String getFirstStocknoteHotKey()
	{
		return getElementsText(StocknoteManagerGridPageElements.firstStocknoteHotKey).trim();
	}
	
	/**
	 * Returns the category of the first stocknote in the grid.
	 *
	 * @return
	 */
	public String getTargetDocType()
	{
		return getElementsText(StocknoteManagerGridPageElements.firstStocknoteTargetDocType).trim();
	}
	
	/**
	 * Checks if the first stocknote is allowed in source docs.
	 *
	 * @return
	 */
	public boolean firstStocknoteSourceDocsY()
	{
		return isElementTextY(StocknoteManagerGridPageElements.firstStocknoteSourceDocs);
	}
	
	/**
	 * Checks if the first stocknote has state rules.
	 *
	 * @return
	 */
	public boolean firstStocknoteStateRulesY()
	{
		return isElementTextY(StocknoteManagerGridPageElements.firstStocknoteStateRules);
	}
	
	/**
	 * Checks if the first stocknote has local rules.
	 *
	 * @return
	 */
	public boolean firstStocknoteLocalRulesY()
	{
		return isElementTextY(StocknoteManagerGridPageElements.firstStocknoteLocalRules);
	}
	
	/**
	 * Returns the text in the modified by column of the first stocknote.
	 *
	 * @return
	 */
	public String getFirstStocknoteModifiedBy()
	{
		return getElementsText(StocknoteManagerGridPageElements.firstStocknoteModifiedBy).trim();
	}
	
	/**
	 * Returns the text in the modified date column of the first stocknote.
	 *
	 * @return
	 */
	public String getFirstStocknoteModifiedDate()
	{
		return getElementsText(StocknoteManagerGridPageElements.firstStocknoteModifiedDate).trim();
	}
	
	/**
	 * Checks if the first stocknote is pinned to the top.
	 *
	 * @return
	 */
	public boolean firstStocknotePinnedToTopY()
	{
		return isElementTextY(StocknoteManagerGridPageElements.firstStocknotePinnedToTop);
	}
	
	/**
	 * Returns whether the given element's inner text is y. This is only for use in this class.
	 *
	 * @param element
	 * @return
	 */
	private boolean isElementTextY(WebElement element)
	{
		return getElementsText(element).trim().equals("Y");
	}
	
	/**
	 * Checks whether the stocknote is deleted. The stocknote must be in the grid for this method to be
	 * accurate. Most reliable if you filter for the given stocknote name first.
	 *
	 * @param stocknoteName
	 * @return
	 */
	public boolean isStocknoteDeleted(String stocknoteName)
	{
		return !getStocknotesNames().contains(stocknoteName + "(Deleted)");
	}

	/**
	 * Gets the first hot key in the grid.
	 *
	 * @return
	 */
	public String getFirstHotKeyInGrid()
	{
		return StocknoteManagerGridPageElements.stocknotesHotKeys.get(0).getText();
	}

	/**
	 * Right clicks stocknote by name.
	 *
	 * @param stocknoteName
	 */
	public void rightClickStocknote(String stocknoteName)
	{
		click(String.format(StocknoteManagerGridPageElements.STOCKNOTE_BY_NAME, stocknoteName));
		rightClick(String.format(StocknoteManagerGridPageElements.STOCKNOTE_BY_NAME, stocknoteName));
	}

	/**
	 * Gets the category of a stocknote with the given name.
	 *
	 * @param hotkey
	 * @return
	 */
	public String getStocknoteCategoryWithGivenHotKey(String hotkey)
	{
		return getElementsText(String.format(StocknoteManagerGridPageElements.STOCKNOTE_CATEGORY_BY_HOTKEY, hotkey));
	}

	public boolean clickCreateNewNote()
	{
		click(StocknoteManagerGridPageElements.createNewNote);
		boolean windowOpened = switchToWindow(StocknotePropertiesPageElements.STOCKNOTE_PROPERTIES_PAGE_TITLE);
		enterTheInnerFrame();
		return windowOpened;
	}

	public boolean clickStocknoteSearchAndReplace()
	{
		click(StocknoteManagerGridPageElements.stocknoteSearchAndReplace);
		boolean windowOpened = switchToWindow(StocknoteSearchAndReplacePageElements.STOCKNOTE_SEARCH_AND_REPLACE_PAGE_TITLE);
		enterTheInnerFrame();
		return windowOpened;
	}
}
