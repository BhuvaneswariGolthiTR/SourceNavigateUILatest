package com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.tools;

import com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.StocknoteManagerContextMenuElements;
import com.thomsonreuters.codes.codesbench.quality.menus.contextmenus.ContextMenu;
import com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StocknoteManagerContextMenu extends ContextMenu
{
	
	private WebDriver driver;
	
	@Autowired
	public StocknoteManagerContextMenu(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
	}
	
	@PostConstruct
	public void init()
	{
		PageFactory.initElements(driver, StocknoteManagerContextMenuElements.class);
	}

	public void selectToBeInserted()
	{
		sendEnterToElement(StocknoteManagerContextMenuElements.selectToBeInserted);
	}
	
	/**
	 * Clicks the properties option on the context menu.
	 *
	 * @return
	 */
	public boolean properties()
	{
		click(StocknoteManagerContextMenuElements.properties);
		boolean windowOpened = switchToWindow(StocknotePropertiesPageElements.STOCKNOTE_PROPERTIES_PAGE_TITLE);
		enterTheInnerFrame();
		return windowOpened;
	}
	
	/**
	 * Clicks the delete note option in the Stocknote Manager context menu.
	 * @param stocknoteName 
	 * @return 
	 *
	 */
	public boolean deleteStocknote(String stocknoteName)
	{
		click(StocknoteManagerContextMenuElements.deleteNote);
		boolean alertTextMatches = checkAlertTextMatchesGivenText("Are you sure you want to delete the selected stocknote: " + stocknoteName + "?");
		switchToWindow(StocknoteManagerPageElements.PAGE_TITLE);
		waitForGridRefresh();
		waitForPageLoaded();
		return alertTextMatches;
	}
	
	/**
	 * Clicks the open hotkey setup option in the Stocknote Manager context menu.
	 *
	 * @return True if the window is opened, false if not.
	 */
	public boolean hotKeySetup()
	{
		click(StocknoteManagerContextMenuElements.hotKeySetup);
		boolean windowOpened = switchToWindow(StocknoteHotKeysPageElements.STOCKNOTE_HOT_KEY_SETUP_PAGE_TITLE);
		enterTheInnerFrame();
		return windowOpened;
	}

	/**
	 * Clicks the search and replace option in the context menu.
	 * @return 
	 */
	public boolean searchAndReplace()
	{
		click(StocknoteManagerContextMenuElements.searchAndReplace);
		boolean windowOpened = switchToWindow(StocknoteSearchAndReplacePageElements.STOCKNOTE_SEARCH_AND_REPLACE_PAGE_TITLE);
		enterTheInnerFrame();
		return windowOpened;
	}

	public boolean copyNote()
	{
		click(StocknoteManagerContextMenuElements.copyNote);
		boolean windowOpened = switchToWindow(StocknotePropertiesPageElements.STOCKNOTE_PROPERTIES_PAGE_TITLE);
		enterTheInnerFrame();
		return windowOpened;
	}

	public boolean copyToAnotherContentSet()
	{
		click(StocknoteManagerContextMenuElements.copyToAnotherContentSet);
		boolean windowOpened = switchToWindow(StocknoteTableManagementPageElements.STOCKNOTE_TABLE_MANAGEMENT_PAGE_TITLE);
		enterTheInnerFrame();
		return windowOpened;	
	}
}
