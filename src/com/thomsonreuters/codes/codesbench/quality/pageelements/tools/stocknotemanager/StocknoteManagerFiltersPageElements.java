package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class StocknoteManagerFiltersPageElements
{
	@FindBy(how = How.ID, using = "StocknoteNameFilter")
	public static WebElement stocknoteNameFilterTextbox;
	
	@FindBy(how = How.XPATH, using = "(//div[@id='gridContainer']/div[@class='yui-dt-paginator grid-pg-container']/select[@class='yui-pg-rpp-options'])[2]")
	public static WebElement resultsPerPageSelect;

	public static final String RESULTS_PER_PAGE_SELECT_ID = "yui-pg0-1-rpp";
	
	@FindBy(how = How.XPATH, using="//a[@id='yui-pg0-1-next-link']")
	public static WebElement nextPageButton;
	
	@FindBy(how = How.XPATH, using="//a[@id='yui-pg0-1-prev-link']")
	public static WebElement prevPageButton;
	
	@FindBy(how = How.XPATH, using="//a[@id='yui-pg0-1-last-link']")
	public static WebElement lastPageButton;
	
	@FindBy(how = How.XPATH, using="//a[@id='yui-pg0-1-first-link']")
	public static WebElement firstPageButton;
	
	@FindBy(how = How.XPATH, using = "//select[@class='yui-pg-rpp-options']/option[@value='5000']")
	public static WebElement _5000resultsPerPageOption;

	@FindBy(how = How.LINK_TEXT, using = "Modified Date")
	public static WebElement modifiedDateColumnSort;

	@FindBy(how = How.LINK_TEXT, using = "Clear Sort")
	public static WebElement clearSortButton;

	@FindBy(how = How.LINK_TEXT, using = "Clear All Filters")
	public static WebElement clearAllFiltersButton;
	
	@FindBy(how = How.ID, using = "CategoryFilter")
	public static WebElement stocknoteCategoryFilterTextbox;
}
