package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.stocknotemanager;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class StocknoteManagerPageElements
{
	public static final String PAGE_TITLE = "Stocknote Manager";
	public static final String PLEASE_WAIT = "//*[contains(text(), 'please wait')]]";
	public static final String STOCKNOTE_MANAGER_DIV_PAGE_TITLE = "//div[contains(.,'Stocknote Manager')]";
	
	public static final String HOTKEY_EXPORT_FILE_NAME_REGEX = "\\Auat-excel-%s-\\d{6}?-hotkeys.xls\\Z"; // for dev: \\Adev-excel-%s-\\d{6}?-hotkeys.xls\\Z
	public static final String HOTKEY_EXPORT_FILE_NAME = "uat-excel-%s-%s-hotkeys.xls"; // for dev: dev-excel-%s-%s-hotkeys.xls
	public static final String HOTKEY_EXCEL_SHEET_NAME = "Hotkeys";
	public static final int HOT_KEY_FILENAME_TIME_INDEX = 3;
	
	@FindBy(how = How.PARTIAL_LINK_TEXT, using = "Export Hotkeys")
	public static WebElement exportHotkeysToExcel;
	
	@FindBy(how = How.XPATH, using = "(//span[@id='yui-pg0-1-page-report']/span[contains(., ' Results')])")
	public static WebElement totalNumberOfStocknotes;
	
	@FindBy(how = How.XPATH, using = "(//span[contains(@class, 'current-page')])[2]")
	public static WebElement currentPageNumber;
	
	@FindBy(how = How.XPATH, using = "//div[@id='yui-dt0-paginator1']//a[text()='Refresh']")
	public static WebElement refreshButton;
}
