package com.thomsonreuters.codes.codesbench.quality.pages.tools.stocknotemanager;

import javax.annotation.PostConstruct;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteManagerFiltersPageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class StocknoteManagerFiltersPage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public StocknoteManagerFiltersPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, StocknoteManagerFiltersPageElements.class);
	}
	
	/**
	 * Filters stocknote manager by the given word.
	 *
	 * @param wordToFilter
	 */
	public void setFilterStocknoteName(String wordToFilter)
	{
		clearAndSendTextToTextbox(StocknoteManagerFiltersPageElements.stocknoteNameFilterTextbox, wordToFilter);
	}
	
	/**
	 * Sets the results per page to the desired number.
	 *
	 * @param resultsPerPage
	 */
	private void setResultsPerPage(String resultsPerPage)
	{
		waitForElement(StocknoteManagerFiltersPageElements.resultsPerPageSelect);
		selectDropdownOptionUsingJavascript(StocknoteManagerFiltersPageElements.RESULTS_PER_PAGE_SELECT_ID, resultsPerPage);
		waitForGridRefresh();
		waitForPageLoaded();
		if("5000".equals(resultsPerPage))
		{
			StocknoteManagerFiltersPageElements.resultsPerPageSelect.sendKeys(Keys.UP);
			waitForGridRefresh();
			waitForPageLoaded();
			StocknoteManagerFiltersPageElements.resultsPerPageSelect.sendKeys(Keys.DOWN);
			waitForGridRefresh();
			waitForPageLoaded();
		}
		else
		{
			StocknoteManagerFiltersPageElements.resultsPerPageSelect.sendKeys(Keys.DOWN);
			waitForGridRefresh();
			waitForPageLoaded();
			StocknoteManagerFiltersPageElements.resultsPerPageSelect.sendKeys(Keys.UP);
			waitForGridRefresh();
			waitForPageLoaded();
		}
		stocknoteManagerPage().refresh();
	}
	
	/**
	 * Sets the stocknote manager results per page to be 500.
	 */
	public void setResultsPerPageTo5000()
	{
		setResultsPerPage("5000");
	}
	
	/**
	 * Sets the stocknote manager results per page to be 50.
	 */
	public void setResultsPerPageTo50()
	{
		setResultsPerPage("50");
	}

	/**
	 * Sets the stocknote manager results per page to be 500.
	 */
	public void setResultsPerPageTo500()
	{
		setResultsPerPage("500");
	}

	/**
	 * Sets the stocknote manager results per page to be 25.
	 */
	public void setResultsPerPageTo25()
	{
		setResultsPerPage("25");
	}
	
	/**
	 * Gets the number of stocknotes per page.
	 *
	 * @return Number of stocknotes per page
	 */
	public int getNumberOfResultsPerPage()
	{
		return Integer.parseInt(getElementsAttribute(StocknoteManagerFiltersPageElements.resultsPerPageSelect, "value"));
	}
	
	/**
	 * Clicks the next page arrow in the stocknote manager.
	 */
	public void goToNextPage()
	{
		click(StocknoteManagerFiltersPageElements.nextPageButton);
		waitForGridRefresh();
		waitForPageLoaded();
	}
	
	/**
	 * Click the last page arrow in the stocknote manager.
	 */
	public void goToLastPage()
	{
		click(StocknoteManagerFiltersPageElements.lastPageButton);
		waitForGridRefresh();
		waitForPageLoaded();
	}
	
	/**
	 * Clicks the previous page arrow in the stocknote manager.
	 */
	public void goToPreviousPage()
	{
		click(StocknoteManagerFiltersPageElements.prevPageButton);
		waitForGridRefresh();
		waitForPageLoaded();
	}
	
	/**
	 * Click the first page arrow in the stocknote manager.
	 */
	public void goToFirstPage()
	{
		click(StocknoteManagerFiltersPageElements.firstPageButton);
		waitForGridRefresh();
		waitForPageLoaded();
	}
	
	/**
	 * Gets the value that is currently int he name filter box.
	 *
	 * @return
	 */
	public String getNameFilterValue()
	{
		return getElementsText(StocknoteManagerFiltersPageElements.stocknoteNameFilterTextbox).trim();
	}
	
	/**
	 * Returns whether the date modified column is sorted ascending.
	 *
	 * @return Whether date modified column is sorted ascending
	 */
	public boolean stocknotesSortedByDateModifiedAscending()
	{
		return getElementsAttribute(StocknoteManagerFiltersPageElements.modifiedDateColumnSort, "title").contains("descending");
	}
	
	/**
	 * Returns whether the date modified column is sorted descending.
	 *
	 * @return Whether date modified column is sorted ascending
	 */
	public boolean stocknotesSortedByDateModifiedDescending()
	{
		return getElementsAttribute(StocknoteManagerFiltersPageElements.modifiedDateColumnSort,"title").contains("ascending");
	}
	
	/**
	 * Sorts the date modified column ascending.
	 */
	public void sortDateModifiedAscending()
	{
		if(stocknotesSortedByDateModifiedDescending())
		{
			click(StocknoteManagerFiltersPageElements.modifiedDateColumnSort);
			waitForGridRefresh();
		}
	}
	
	/**
	 * Clicks the clear sorts button.
	 */
	public void clearSort()
	{
		sendEnterToElement(StocknoteManagerFiltersPageElements.clearSortButton);
		waitForGridRefresh();
	}
	
	/**
	 * Clear all filters.
	 */
	public void clearAllFilters()
	{
		sendEnterToElement(StocknoteManagerFiltersPageElements.clearAllFiltersButton);
		waitForGridRefresh();
		waitForPageLoaded();
	}

	/**
	 * Filters by category
	 *
	 * @param category
	 */
	public void filterByCategory(String category)
	{
		clearAndSendKeysToElement(StocknoteManagerFiltersPageElements.stocknoteCategoryFilterTextbox, category);
		sendEnterToElement(StocknoteManagerFiltersPageElements.stocknoteCategoryFilterTextbox);
		waitForGridRefresh();
	}
}
