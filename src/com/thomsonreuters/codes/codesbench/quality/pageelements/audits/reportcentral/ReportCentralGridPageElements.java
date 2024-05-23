package com.thomsonreuters.codes.codesbench.quality.pageelements.audits.reportcentral;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ReportCentralGridPageElements
{
	public static final String REPORT_IDENTIFIER_LINK_WITH_GIVEN_TITLE = "//td[contains(@class, 'reportIdentifier')]/div/a[contains(text(), '%s')]";

	public static final String REPORT_UUID = "//td[contains(@class, 'hidden') and contains(@class, 'col-id')]/div";
	@FindBy(how = How.XPATH, using = "//td[contains(@class, 'workflowStatus')]")
	public static WebElement firstReportWorkflowStatus;

	@FindBy(how = How.XPATH, using = "//td[contains(@class, 'requestName')]")
	public static WebElement firstRequesterName;

	@FindBy(how = How.XPATH, using = "//td[contains(@class, 'reportType')]")
	public static WebElement firstReportType;

	@FindBy(how = How.XPATH, using = "//td[contains(@class, 'reportIdentifier')]")
	public static WebElement firstReportIdentifier;

	@FindBy(how = How.XPATH, using = "//a[contains(text(), 'Effective Date Metrics Report')]")
	public static WebElement firstEffectiveDateMetricsReport;

	@FindBy(how = How.XPATH, using = "//td[contains(@class, 'reportIdentifier')]/div/a")
	public static WebElement firstReportIdentifierLink;
}
