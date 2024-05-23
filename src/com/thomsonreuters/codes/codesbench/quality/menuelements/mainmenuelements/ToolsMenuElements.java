package com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ToolsMenuElements extends CommonMenuElements
{
    private static final String GENERIC_TOOLS_XPATH = MAIN_NAVIGATOR_MENU_XPATH + "//div[@id='baseNavBartools']";
    public static final String TOOLS_MENU_OC_EXTRACT = GENERIC_TOOLS_XPATH + "//a[text()='OC Extract']";
	public static final String TOOLS_MENU_OPTIONS_XPATH = GENERIC_TOOLS_XPATH + "//li/a";

	// Tools
	@FindBy(how = How.XPATH, using = MAIN_NAVIGATOR_MENU_XPATH + "//a[text()='Tools']")
	public static WebElement toolsMenu;

	// Tools -> Spellcheck Manager
	@FindBy(how = How.XPATH, using = GENERIC_TOOLS_XPATH + "//a[text()='Spellcheck Manager']")
	public static WebElement spellcheckManager;

	// Tools -> Stock Note Manager
	public static final String TOOLS_MENU_QUERY_STOCK_NOTE_MANAGER = GENERIC_TOOLS_XPATH + "//a[text()='Stock Note Manager']";

	@FindBy(how = How.XPATH, using = TOOLS_MENU_QUERY_STOCK_NOTE_MANAGER)
	public static WebElement stockNoteManager;

	public static final String TOOLS_MENU_QUERY_NOTE_REPORT = GENERIC_TOOLS_XPATH + "//a[text()='Query Note Report']";

	@FindBy(how = How.XPATH, using = TOOLS_MENU_QUERY_NOTE_REPORT)
	public static WebElement queryNoteReport;

	public static final String TOOLS_MENU_QUERY_NOTE_REPORT_ANGULAR = GENERIC_TOOLS_XPATH + "//a[text()='Query Note Report - New']";

	@FindBy(how = How.XPATH, using = TOOLS_MENU_QUERY_NOTE_REPORT_ANGULAR)
	public static WebElement queryNoteReportAngular;

	public static final String TOOLS_MENU_SCRIPT_MAINTENANCE = GENERIC_TOOLS_XPATH + "//a[text()='Script Maintenance']";

	@FindBy(how = How.XPATH, using = GENERIC_TOOLS_XPATH + "//a[text()='Script Maintenance']")
    public static WebElement scriptMaintenance;

	@FindBy(how = How.XPATH, using = GENERIC_TOOLS_XPATH + "//a[text()='Effective Date Metrics Report']")
	public static WebElement toolsMenuEffectiveDateMetricsReport;

	public static final String TOOLS_MENU_SEARCH_AND_REPLACE = GENERIC_TOOLS_XPATH + "//a[text()='Search and Replace']";

	@FindBy(how = How.XPATH, using = TOOLS_MENU_SEARCH_AND_REPLACE)
	public static WebElement toolsMenuSearchAndReplace;

	@FindBy(how = How.XPATH, using = GENERIC_TOOLS_XPATH + "//a[text()='Workflow Reporting System']")
	public static WebElement workflowReportingSystem;

	@FindBy(how = How.XPATH, using = GENERIC_TOOLS_XPATH + "//div[@id='toolscodesAuthority']" + "//[text()='Codes Authority Search Grid']")
	public static WebElement codesAuthoritySearchGrid;

	public static final String TOOLS_MENU_CODES_AUTHORITY = GENERIC_TOOLS_XPATH + "//a[text()='Codes Authority']";

	@FindBy(how = How.XPATH, using = TOOLS_MENU_CODES_AUTHORITY)
	public static WebElement codesAuthority;

	public static final String TOOLS_MENU_ONLINE_PRODUCT_MAINTENANCE = GENERIC_TOOLS_XPATH + "//a[text()='Online Product Maintenance']";

	@FindBy(how = How.XPATH, using = TOOLS_MENU_ONLINE_PRODUCT_MAINTENANCE)
    public static WebElement onlineProductMaintenance;

	public static final String TOOLS_MENU_UNDO = GENERIC_TOOLS_XPATH + "//a[text()='Undo']";

	@FindBy(how = How.XPATH, using = TOOLS_MENU_UNDO)
    public static WebElement undo;

	public static final String TOOLS_MENU_REDLINING_COMPARE = GENERIC_TOOLS_XPATH + "//a[text()='Redlining Compare']";

	@FindBy(how = How.XPATH, using = TOOLS_MENU_REDLINING_COMPARE)
    public static WebElement redliningCompare;

	@FindBy(how = How.XPATH, using = GENERIC_TOOLS_XPATH + "//a[text()='KeyCite Extract']")
    public static WebElement keyCiteExtract;

	@FindBy(how = How.XPATH, using = TOOLS_MENU_OC_EXTRACT)
	public static WebElement ocExtract;

	@FindBy(how = How.XPATH, using = GENERIC_TOOLS_XPATH + "//a[text()='State Feeds']")
	public static WebElement stateFeeds;
}
