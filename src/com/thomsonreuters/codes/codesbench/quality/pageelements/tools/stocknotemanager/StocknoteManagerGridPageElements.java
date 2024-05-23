package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class StocknoteManagerGridPageElements
{
	//public static final String STOCKNOTES_IN_GRID = "//tbody[@class='yui-dt-data']/tr";
	public static final String STOCKNOTE_BY_NAME = "//tbody[@class='yui-dt-data']//div[. = '%s']/..";
	public static final String STOCKNOTE_NAME_BY_HOTKEY = "//div[contains(., '%s')]/../../td[contains(@class, 'StocknoteName')]/div";
	public static final String STOCKNOTE_BY_HOTKEY = "//td[contains(@class, 'Hotkey')]/div[contains(., '%s')]";
	public static final String STOCKNOTE_CATEGORY_BY_HOTKEY = "//div[contains(., '%s')]/../../td[contains(@class, 'Category')]/div";
	public static final String STOCKNOTE_NAMES = "//td[contains(@class, 'StocknoteName')]/div";
	
	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@class, 'StocknoteName')]")
	public static WebElement firstStocknoteName;
	
	@FindBy(how = How.LINK_TEXT, using = "Delete Note")
	public static WebElement deleteButton;

	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@headers, 'Hotkey')]")
	public static WebElement firstStocknoteHotKey;
	
	@FindBy(how = How.XPATH, using = "//td[contains(@class, 'Hotkey')]/div[contains(text(), 'Alt')]")
	public static List<WebElement> stocknotesHotKeys;
	
	@FindBy(how = How.XPATH, using = "//td[contains(@class, 'StocknoteName')]/div")
	public static List<WebElement> stocknoteNames;
	
	@FindBy(how = How.XPATH, using = "//td[contains(@class, 'Hotkey')]/div[contains(., '')]")
	public static WebElement firstStocknoteWithoutHotKey;
	
	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@class, 'Category')]")
	public static WebElement firstStocknoteCategory;
	
	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@class, 'OnContextMenu')]")
	public static WebElement firstStocknoteOnContextMenu;
	
	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@class, 'TargetDocType')]")
	public static WebElement firstStocknoteTargetDocType;
	
	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@class, 'SourceDocs')]")
	public static WebElement firstStocknoteSourceDocs;
	
	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@class, 'StateRules')]")
	public static WebElement firstStocknoteStateRules;
	
	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@class, 'LocalRules')]")
	public static WebElement firstStocknoteLocalRules;
	
	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@class, 'ModifiedDate')]")
	public static WebElement firstStocknoteModifiedDate;
	
	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@class, 'OnPinnedToTop')]")
	public static WebElement firstStocknotePinnedToTop;
	
	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@class, 'ModifiedBy')]")
	public static WebElement firstStocknoteModifiedBy;

	@FindBy(how = How.XPATH, using = "//input[@value='Create New Note']")
	public static WebElement createNewNote;

	@FindBy(how = How.XPATH, using = "//input[@value='Stocknote Search and Replace']")
	public static WebElement stocknoteSearchAndReplace;
}
