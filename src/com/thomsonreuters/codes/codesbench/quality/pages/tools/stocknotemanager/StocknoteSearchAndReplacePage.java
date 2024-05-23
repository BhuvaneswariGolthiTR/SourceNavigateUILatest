package com.thomsonreuters.codes.codesbench.quality.pages.tools.stocknotemanager;

import javax.annotation.PostConstruct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteManagerPageElements;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.StocknoteSearchAndReplacePageElements;
import com.thomsonreuters.codes.codesbench.quality.pages.BasePage;

@Component
public class StocknoteSearchAndReplacePage extends BasePage
{
	private WebDriver driver;
	
	@Autowired
	public StocknoteSearchAndReplacePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, StocknoteSearchAndReplacePageElements.class);
	}

	/**
	 * Sets the search value.
	 *
	 * @param searchValue
	 */
	public void setSearchValue(String searchValue)
	{
		clearAndSendKeysToElement(StocknoteSearchAndReplacePageElements.searchValueTextBox, searchValue);
	}

	/**
	 * Sets the replace value.
	 *
	 * @param replaceValue
	 */
	public void setReplaceValue(String replaceValue)
	{
		clearAndSendKeysToElement(StocknoteSearchAndReplacePageElements.replaceValueTextBox, replaceValue);
	}

	/**
	 * Clicks preview changes.
	 */
	public void previewChanges()
	{
		click(StocknoteSearchAndReplacePageElements.previewChangesButton);
		waitForPageLoaded();
	}

	/**
	 * Clicks apply changes.
	 *
	 * @return
	 */
	public boolean applyChanges()
	{
		click(StocknoteSearchAndReplacePageElements.applyChangesButton);
		return switchToWindow(StocknoteSearchAndReplacePageElements.STOCKNOTE_SEARCH_AND_REPLACE_PAGE_TITLE);
	}

	/**
	 * Closes the search and replace window.
	 *
	 * @return
	 */
	public boolean close()
	{
		driver.close();
		boolean switchToStocknoteManager = switchToWindow(StocknoteManagerPageElements.PAGE_TITLE);
		waitForGridRefresh();
		return switchToStocknoteManager;
	}

	/**
	 * Verifies the before change area contains the given text.
	 *
	 * @param value
	 * @return
	 */
	public boolean beforeChangeContains(String value)
	{
		return getElementsText(StocknoteSearchAndReplacePageElements.beforeChangeText).contains(value);
	}
	
	/**
	 * Verifies the after change area contains the given text.
	 *
	 * @param value
	 * @return
	 */
	public boolean afterChangeContains(String value)
	{
		return getElementsText(StocknoteSearchAndReplacePageElements.afterChangeText).contains(value);
	}
}
