package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.effectivedatemetricsreport;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EffectiveDateMetricsReportPageElements 
{
	public static final String EFFECTIVE_DATE_METRICS_REPORT_PAGE_TITLE = "//*[contains(.,'Effective Date Metrics Report')]";

	public static final String METRICS_REPORT_FILE_NAME = "\\metrics-report.xlsx";
	
	// Excel info
	public static final String METRICS_REPORT_DASHBOARD_ORDERS_SHEET_NAME_EXCEL = "Dashboard - Orders";
	public static final String METRICS_REPORT_DASHBOARD_LAWS_SHEET_NAME_EXCEL = "Dashboard - Laws";
	public static final String METRICS_REPORT_DASHBOARD_REGULATIONS_SHEET_NAME_EXCEL = "Dashboard - Regulations";
	public static final String METRICS_REPORT_DATA_SHEET_NAME_EXCEL = "Data";

	@FindBy(how = How.XPATH, using = "//input[@type='submit' and @value='Generate report']")
	public static WebElement generateReportButton;
	
	@FindBy(how = How.XPATH, using = "//a[contains(., 'Close')]")
	public static WebElement closeEffectiveDateMetricsModalButton;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Content Set']/preceding-sibling::input")
	public static WebElement contentSetCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Year']/preceding-sibling::input")
	public static WebElement yearCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Rendition Status ID']/preceding-sibling::input")
	public static WebElement renditionStatusIdCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Content Type ID']/preceding-sibling::input")
	public static WebElement contentTypeIdCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Class Number']/preceding-sibling::input")
	public static WebElement classNumberCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Doc Number']/preceding-sibling::input")
	public static WebElement docNumberCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Delta Count']/preceding-sibling::input")
	public static WebElement deltaCountCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Effective Date']/preceding-sibling::input")
	public static WebElement effectiveDateCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Rendition Load Date/Time']/preceding-sibling::input")
	public static WebElement renditionLoadDateTimeCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Prep Started']/preceding-sibling::input")
	public static WebElement prepStartedCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Prep Completed']/preceding-sibling::input")
	public static WebElement prepCompletedCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Audit Started']/preceding-sibling::input")
	public static WebElement auditStartedCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Audit Completed']/preceding-sibling::input")
	public static WebElement auditCompletedCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Integration Started']/preceding-sibling::input")
	public static WebElement integrationStartedCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Integration Completed']/preceding-sibling::input")
	public static WebElement integrationCompletedCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[text()=' Westlaw Load']/preceding-sibling::input")
	public static WebElement westlawLoadCheckbox;
	
	@FindBy(how = How.XPATH, using = "//label[contains(text(),'Year:')]/following-sibling::input[@type='text']")
	public static WebElement yearTextBox;

}
