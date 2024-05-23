package com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HierarchyMenuElements extends CommonMenuElements
{
	public static final String HIERARCHY_MENU_OPTIONS_XPATH = "//div[@id='baseNavBarhierarchy']//li/a";
	public static final String HIERARCHY_REPORTS_MENU_OPTIONS_XPATH = "//div[@id='hierarchyhierarchyReports']//li/a";
	public static final String HIERARCHY_MENU_PUB_NAVIGATE_XPATH = "//ul[@class='first-of-type']//a[text()='Pub Navigate']";
	public static final String HIERARCHY_MENU_KEYCITE_DATES_REPORT = "//ul[@class='first-of-type']//a[text()='Keycite Dates Report']";
	public static final String HIERARCHY_MENU_NAVIGATE_TO_VIRTUAL_RULEBOOK = "//ul[@class='first-of-type']//a[text()='Virtual Rule Books']";
	public static final String INSERT_GLOSSARY_LINK = "//a[text()='Insert Glossary Link']";
	private static final String HIERARCHY_DATE_SEARCH_MULTIPLE_DOCUMENTS_REPORT_PAGE = "[text()='Hierarchy Date Search (Multiple Documents)']";

	// Hierarchy Menu
	@FindBy(how = How.XPATH, using = "//*[text()='Hierarchy']")
	public static WebElement hierarchy;

	// Reports
	@FindBy(how = How.XPATH, using = "//*[text()='Reports']")
	public static WebElement reports;

	// Locked Nodes Report
	public static final String HIERARCHY_MENU_LOCKED_NODES_REPORT = "//ul[@class='first-of-type']//a[text()='Locked Nodes Report']";

	@FindBy(how = How.XPATH, using = HIERARCHY_MENU_LOCKED_NODES_REPORT)
	public static WebElement lockedNodesReport;

	// Navigate
	@FindBy(how = How.XPATH, using = "//*[text()='Navigate']")
	public static WebElement navigate;

	// Navigate
	@FindBy(how = How.XPATH, using = "//*[text()='Pub Navigate']")
	public static WebElement pubNavigate;

	@FindBy(how = How.XPATH, using = "//ul[@class='first-of-type']//a[text()='New Nodes Report']")
	public static WebElement newNodesReport;

	public static final String HIERARCHY_MENU_RECOMP_NAVIGATION = "//ul[@class='first-of-type']//a[text()='Recomp Navigation']";

	@FindBy(how = How.XPATH, using = HIERARCHY_MENU_RECOMP_NAVIGATION)
	public static WebElement hierarchyMenuRecompNavigationXpath;

	public static final String HIERARCHY_MENU_TARGET_TAGS_REPORT = "//ul[@class='first-of-type']//a[text()='Target Tags Report']";

	@FindBy(how = How.XPATH, using = HIERARCHY_MENU_TARGET_TAGS_REPORT)
	public static WebElement hierarchyMenuTargetTagsReportXpath;

	public static final String HIERARCHY_MENU_SCRIPT_LISTING_REPORT = "//ul[@class='first-of-type']//a[text()='Script Listing Report']";

	@FindBy(how = How.XPATH, using = HIERARCHY_MENU_SCRIPT_LISTING_REPORT)
	public static WebElement hierarchyMenuScriptListingReportXpath;

	public static final String HIERARCHY_MENU_VIRTUAL_RULE_BOOK_REPORT = "//a[text()='Virtual Rule Book Report']";

	@FindBy(how = How.XPATH, using = HIERARCHY_MENU_VIRTUAL_RULE_BOOK_REPORT)
	public static WebElement hierarchyMenuReportsVirtualRulebookReportXpath;

	@FindBy(how = How.XPATH, using = HIERARCHY_REPORTS_MENU_OPTIONS_XPATH + HIERARCHY_DATE_SEARCH_MULTIPLE_DOCUMENTS_REPORT_PAGE)
	public static WebElement hierarchyDateSearchMultipleDocumentsReportPage;

	public static final String HIERARCHY_MENU_NAVIGATE_TO_RELATED_RULE_BOOK = "//ul[@class='first-of-type']//a[text()='Navigate to Related Rule Book']";

	@FindBy(how = How.XPATH, using = HIERARCHY_MENU_NAVIGATE_TO_RELATED_RULE_BOOK)
	public static WebElement hierarchyMenuNavigateToRelatedRuleBookXpath;

	public static final String HIERARCHY_MENU_SOURCE_DOCUMENTS_IN_HIERARCHY_REPORT = "//ul[@class='first-of-type']//a[text()='Source Documents In Hierarchy Report']";

	@FindBy(how = How.XPATH, using = HIERARCHY_MENU_SOURCE_DOCUMENTS_IN_HIERARCHY_REPORT)
	public static WebElement hierarchyMenuSourceDocumentsXpath;
}
