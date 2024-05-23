package com.thomsonreuters.codes.codesbench.quality.pageelements.source.bts;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BtsWebUiPageElements 
{
	//Main page 
	public static final String BTS_WEB_UI_PAGE_TITLE = "BTS Web UI";
	public static final String TOOLS_MENU_SORT_BILLS_POPUP_MESSAGE = "sort was successful";  
	public static final String TABLE_CODE_COLUMN_HEADER_XPATH = "//th/div[text()='Table Code']";
	public static final String GENERIC_RECORD_XPATH = "//table[@id='tableman']//tr[not(contains(@class, 'deleted')) and td[contains(text(), '%s')] and td[contains(text(), '%s')] and td[contains(text(), '%s')]]";
	public static final String TASKS_TABLES_GENERATE_POCKET_PART_TABLES_MENU_XPATH = "//a[text()='Generate Pocket Part Tables']"; 
	public static final String TASKS_TABLES_LIST_POCKET_PART_TABLES_MENU_XPATH = "//a[text()='List Pocket Part Tables']";
	public static final String LAST_BUTTON_XPATH = "//a[@class='next fg-button ui-button ui-state-default ui-state-disabled']";
	public static final String SPECIFIC_OPTION_DROPDOWN_XPATH = "//select[@id='selectSpecificOption']";
	public static final String GENERIC_TABLE_ROW = "//table[@id='tableman']/tbody//tr";
	
	@FindBy(how = How.XPATH, using = "//select[@id='selectJurisdiction']")
	public static WebElement jurisdictionDropdown;
	
	@FindBy(how = How.XPATH, using = "//select[@id='selectYear']")
	public static WebElement yearDropdown;
	
	@FindBy(how = How.XPATH, using = "//table[@id='tableman']/tbody//tr" )
	public static WebElement genericTableRow;
	
	@FindBy(how = How.XPATH, using = "//select[@id='selectSpecificOption']")
	public static WebElement specificOptionDropdown;
	
	@FindBy(how = How.XPATH, using = "//a[@id='tableman_last' and not(contains(@class, 'ui-state-disabled'))]")
	public static WebElement lastPageButton;
	
	@FindBy(how = How.XPATH, using = "//span[@title='Delete']")
	public static WebElement deleteAuditRow;
	
	@FindBy(how = How.XPATH, using = "//tbody[@role='alert']/tr/td[4]")
	public static WebElement auditRowName;
	
	@FindBy(how = How.XPATH, using = "//tbody[@role='alert']/tr/td[5]")
	public static WebElement auditRowDate;
	
	@FindBy(how = How.LINK_TEXT, using = "Tasks")
	public static WebElement tasksMenu;
	
	@FindBy(how = How.XPATH, using = "//a[@href='#audit']")
	public static WebElement taskMenuAuditsMenuItem;
	
	@FindBy(how = How.XPATH, using = "\"//a[text()='Generate Audit']\"")
	public static WebElement taskMenuAuditsGenerateAuditsMenuItem;
	
	@FindBy(how = How.XPATH, using = "//a[text()='List Audits']")
	public static WebElement tasksMenuAuditsListAuditsMenuItem ;
	
	@FindBy(how = How.XPATH, using = "//a[@onClick='MainView.openGenerateTable('generate')']")
	public static WebElement tableMenuGenerateLegServices;
	
	@FindBy(how = How.LINK_TEXT, using = "Tables")
	public static WebElement tasksMenuTablesMenu;	
	
	@FindBy(how = How.LINK_TEXT, using =  "Generate Pocket Part Tables")
	public static WebElement tasksMenuTablesMenuPocketPartsTable;
	
	@FindBy(how = How.LINK_TEXT, using  = "List Legislative Service Tables")
	public static WebElement tasksMenuTablesMenuListLegislativeServicesTable;
	
	@FindBy(how = How.XPATH, using = "//a[text()='Generate Legislative Service Tables']")
	public static WebElement tasksTablesMenuGenerateLegislativeServiceTable;
	
	@FindBy(how = How.LINK_TEXT, using = "Tools")
	public static WebElement toolsMenuItem;
	
	@FindBy(how = How.ID, using = "sort")
	public static WebElement sortBillsButton;
	
	@FindBy(how = How.LINK_TEXT, using = "List Pocket Part Tables")
	public static WebElement tasksTablesListPocketPartTablesMenu;
	
	@FindBy(how = How.LINK_TEXT, using = "Generate Pocket Part Tables")
	public static WebElement tasksTablesMenuGeneratePocketPartTables;
	
	@FindBy(how = How.LINK_TEXT, using = "File")
	public static WebElement fileMenu;
	
	@FindBy(how = How.XPATH, using = "//li[a[text()='Add New Record']]")
	public static WebElement fileMenuAddNewRecord;
	
	@FindBy(how = How.LINK_TEXT, using = "Edit")
	public static WebElement editMenu;
	
	@FindBy(how = How.XPATH, using = "//li[a[text()='Find']]")
	public static WebElement editFindMenuFind;
	

	
	
	
}
