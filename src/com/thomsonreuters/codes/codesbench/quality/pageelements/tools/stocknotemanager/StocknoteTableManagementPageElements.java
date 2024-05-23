package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class StocknoteTableManagementPageElements
{
	public static final String STOCKNOTE_TABLE_MANAGEMENT_PAGE_TITLE = "Stocknote Table Management";
	
	@FindBy(how = How.ID, using = "pageForm:currentStocknotes")
	public static WebElement selectedStocknotesSelect;

	@FindBy(how = How.ID, using = "pageForm:selectedContentSet")
	public static WebElement copyToContentSetSelect;

	public static final String COPY_TO_CONTENT_SET_SELECT_ID = "pageForm:selectedContentSet";

	@FindBy(how = How.ID, using = "pageForm:copyToContentSetButton")
	public static WebElement copyToContentSetButton;

	@FindBy(how = How.ID, using = "pageForm:errorMessage")
	public static WebElement resultsTextArea;

	@FindBy(how = How.ID, using = "pageForm:closeBtn")
	public static WebElement closeButton;
}
