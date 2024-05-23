package com.thomsonreuters.codes.codesbench.quality.menuelements.clamshellmenuelements.source;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ReportsMenuElements
{
	public static final String REPORTS_CLAMSHELL_DIV = "//div[contains(@id,'reportsClamshellDiv') or contains(@id,'reportClamshellDiv') ]//"; 

	@FindBy(how = How.XPATH, using = "//img[@class='headerImage']")
	public static WebElement sideBarHeaderImage;
  
	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'Integration Results')]")
	public static WebElement integrationResults;

	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'Integration Summary')]")
	public static WebElement integrationSummary;
	
	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'Integration Workflow')]")
	public static WebElement integrationWorkflow;

	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'Lock Report')]")
	public static WebElement lockReport;

	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'PREP Report')]")
	public static WebElement prepReport;

	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'Unused Report')]")
	public static WebElement unusedReport;

	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'Audits')]")
	public static WebElement audits;

	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'Random Bill Report')]")
	public static WebElement randomBillReport;

	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'Stage Check Report')]")
	public static WebElement stageCheckReport;

	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'Mismatched Report')]")
	public static WebElement mismatchedReport;

	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'Bill/Gap Count Report')]")
	public static WebElement billGapCountReport;

	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'Index Report')]")
	public static WebElement indexReport;

	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'Add Index Report')]")
	public static WebElement addIndexReport;
	
	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'Repeal Index Report')]")
	public static WebElement repealIndexReport;

	@FindBy(how = How.XPATH, using = REPORTS_CLAMSHELL_DIV + "div[contains(@onclick, 'Combined Index Report')]")
	public static WebElement combinedIndexReport;
}
