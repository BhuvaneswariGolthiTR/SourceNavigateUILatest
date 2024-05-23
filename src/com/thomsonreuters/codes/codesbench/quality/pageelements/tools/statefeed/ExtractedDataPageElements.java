package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.statefeed;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ExtractedDataPageElements
{
	public static final String EXTRACTED_DATA_XPATH = "//div[@class='group-grid']";
	public static final String TOOLBOX_STATE_FEED_XPATH = "//toolbox-state-feed";
	public static final String CLEAR_FILTERS_AND_SORTS_XPATH = EXTRACTED_DATA_XPATH + "//button[@aria-label='Clear Filters']";
	public static final String FILTER_MENU_XPATH = TOOLBOX_STATE_FEED_XPATH + "//div[@col-id='group']//span[contains(@class,'ag-icon-menu')]";
	public static final String FILTER_ICON_XPATH = EXTRACTED_DATA_XPATH + "//div[@class='ag-theme-balham ag-popup']//span[@class='ag-icon ag-icon-filter']";
	public static final String FILTER_INPUT_XPATH = EXTRACTED_DATA_XPATH + "//div[@class='ag-theme-balham ag-popup']//input[contains(@class, 'ag-text-field-input')]";
	public static final String GROUP_NAME_ELEMENT_XPATH = "//div[@class='ag-center-cols-container']//div[contains(text(),'%s')]";
	public static final String JURISDICTION_HEADER_XPATH = EXTRACTED_DATA_XPATH + "//div[contains(@class,'ag-header')]//span[contains(text(),'Jurisdiction')]";
	public static final String GROUP_HEADER_XPATH = TOOLBOX_STATE_FEED_XPATH + "//div[contains(@class,'ag-header')]//span[contains(text(),'Group')]";
	public static final String HEADER_XPATH = TOOLBOX_STATE_FEED_XPATH + "//h3[contains(text(),'Extracted data')]";
	public static final String GROUP_NAME_IN_YEAR = "//div[contains(@class,'ag-row-level-1')]//span[contains(text(),'%s')]";
	public static final String JURISDICTION_IN_YEAR = "//div[contains(@class,'ag-row-level-0')]//span[contains(text(),'%s')]";

	@FindBy(how = How.XPATH, using = HEADER_XPATH)
	public static WebElement header;
	@FindBy(how = How.XPATH, using = CLEAR_FILTERS_AND_SORTS_XPATH)
	public static WebElement clearFiltersAndSorts;
	@FindBy(how = How.ID, using = "refresh-groups-btn")
	public static WebElement refreshCurrentGrid;
	@FindBy(how = How.XPATH, using = FILTER_INPUT_XPATH)
	public static WebElement filterInput;
}
