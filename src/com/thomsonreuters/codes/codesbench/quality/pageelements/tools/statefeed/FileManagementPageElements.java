package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.statefeed;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class FileManagementPageElements
{
	public static final String FILE_MANAGEMENT_XPATH = "//div[@class='file-grid']";
	public static final String MAIN_TOOLBAR_XPATH = FILE_MANAGEMENT_XPATH + "/div[@class='main-toolbar toolbar']";
	public static final String CLEAR_FILTERS_AND_SORTS_XPATH = FILE_MANAGEMENT_XPATH + "//button[@aria-label='Clear Filters']";
	public static final String ALL_DATA_SECTION_XPATH = FILE_MANAGEMENT_XPATH + "//span[contains(text(),'All Data')]";
	private static final String EXPAND_TREE_CLOSED = "/..//span[@class='ag-icon ag-icon-tree-closed']";
	private static final String COLLAPSE_TREE_OPEN = "/..//span[@class='ag-icon ag-icon-tree-open']";
	public static final String ALL_DATA_COLLAPSE_XPATH = ALL_DATA_SECTION_XPATH + COLLAPSE_TREE_OPEN;
	public static final String ALL_DATA_EXPAND_XPATH = ALL_DATA_SECTION_XPATH + EXPAND_TREE_CLOSED;
	public static final String TEXT_CREDIT_HISTORICAL_SECTION_XPATH = FILE_MANAGEMENT_XPATH + "//span[contains(text(),'Text Credit Historical')]";
	public static final String TEXT_CREDIT_HISTORICAL_COLLAPSE_XPATH = TEXT_CREDIT_HISTORICAL_SECTION_XPATH + COLLAPSE_TREE_OPEN;
	public static final String TEXT_CREDIT_HISTORICAL_EXPAND_XPATH = TEXT_CREDIT_HISTORICAL_SECTION_XPATH + EXPAND_TREE_CLOSED;
	public static final String TEXT_CREDIT_ONLY_SECTION_XPATH = FILE_MANAGEMENT_XPATH + "//span[contains(text(),'Text Credit Only')]";
	public static final String TEXT_CREDIT_ONLY_COLLAPSE_XPATH = TEXT_CREDIT_ONLY_SECTION_XPATH + COLLAPSE_TREE_OPEN;
	public static final String TEXT_CREDIT_ONLY_EXPAND_XPATH = TEXT_CREDIT_ONLY_SECTION_XPATH + EXPAND_TREE_CLOSED;
	public static final String XML_FILE_XPATH = FILE_MANAGEMENT_XPATH + "//div[contains(@class,'row-level-1')]//span[contains(text(),'%s')]";
	public static final String XML_FILE_EXTRACT_DATE_BY_ROW_ID_XPATH = FILE_MANAGEMENT_XPATH + "//div[contains(@class,'row-level-1')][@row-id='%s']/div[@col-id='createdDate']/span";
	public static final String DOWNLOAD_BUTTON_XPATH = "//button[contains(.,'Download')]";
	public static final String DELETE_BUTTON_XPATH = "//button[contains(.,'Delete')]";
	public static final String SEND_TO_STATE_BUTTON_XPATH = "//button[contains(.,'Send to State')]";
	public static final String XML_FILE_HEADER_XPATH = "//div[contains(@class,'ag-header')]//span[contains(text(),'XML file')]";
	public static final String EXTRACT_DATE_HEADER_XPATH = "//div[contains(@class,'ag-header')]//span[contains(text(),'Extract date')]";
	public static final String SELECT_ALL_HEADER_CHECKBOX_XPATH = "//input[@id='ag-75-input']";
	public static final String HEADER_XPATH = MAIN_TOOLBAR_XPATH + "//h3[contains(text(),'File management')]";
	public static final String FILE_MANAGEMENT_ROW_BY_FILE_NAME_XPATH = FILE_MANAGEMENT_XPATH + "//span[contains(text(), '%s')]/../../..";
	public static final String FILE_CELL_BY_FILE_NAME_XPATH = FILE_MANAGEMENT_XPATH + "//span[contains(text(), '%s')]";
	public static final String DELETE_FILE_CONFIRMATION_POPUP_XPATH = "//toolbox-delete-xml-file";
	public static final String YES_ON_DELETE_POPUP_XPATH = DELETE_FILE_CONFIRMATION_POPUP_XPATH + "//span[text()='Yes']";
	public static final String NO_ON_DELETE_POPUP_XPATH = DELETE_FILE_CONFIRMATION_POPUP_XPATH + "//span[text()='No']";
	public static final String ALL_ROWS_XPATH = FILE_MANAGEMENT_XPATH + "//div[@class='ag-center-cols-container']/div";

	@FindBy(how = How.XPATH, using = HEADER_XPATH)
	public static WebElement header;
	@FindBy(how = How.XPATH, using = CLEAR_FILTERS_AND_SORTS_XPATH)
	public static WebElement clearFiltersAndSorts;
	@FindBy(how = How.ID, using = "refresh-files-btn")
	public static WebElement refreshCurrentGrid;
	@FindBy(how = How.XPATH, using = DOWNLOAD_BUTTON_XPATH)
	public static WebElement downloadButton;
	@FindBy(how = How.XPATH, using = DELETE_BUTTON_XPATH)
	public static WebElement deleteButton;
	@FindBy(how = How.XPATH, using = SEND_TO_STATE_BUTTON_XPATH)
	public static WebElement sendToStateButton;
}
