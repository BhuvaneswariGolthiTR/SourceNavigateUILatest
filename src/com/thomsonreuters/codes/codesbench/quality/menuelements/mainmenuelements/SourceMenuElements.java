package com.thomsonreuters.codes.codesbench.quality.menuelements.mainmenuelements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SourceMenuElements extends CommonMenuElements
{
	public static final String contextMenu = "//div[@id='contextMenu']";
    public static final String LEGO_REPORTS_XPATH = "//ul[@class='first-of-type']//a[text()='Lego Reports']";
	public static final String LEGO_ALL_DELTAS_WITH_SHARED_TARGET_XPATH = "//ul[@class='first-of-type']//a[text()='Lego All Deltas with Shared Target']";
    private static final String GENERIC_SOURCE_XPATH = MAIN_NAVIGATOR_MENU_XPATH + "//div[@id='baseNavBarsource']";
    private static final String GENERIC_NOVUS_EXTRACTS_XPATH = GENERIC_SOURCE_XPATH + "//div[@id='sourcenovusExtractsMenu']";

	public static final String SOURCE_MENU_XPATH = MAIN_NAVIGATOR_MENU_XPATH + "//a[text()='Source']";

	@FindBy(how = How.XPATH, using = SOURCE_MENU_XPATH)
	public static WebElement source;

	@FindBy(how = How.XPATH, using = GENERIC_SOURCE_XPATH + "//a[text()='C2012 Navigate']")
	public static WebElement navigate;

	public static final String C2012_PRINT_NAVIGATE_XPATH = GENERIC_SOURCE_XPATH  + "//a[text()='C2012 Print Navigate']";

	@FindBy(how = How.XPATH, using = C2012_PRINT_NAVIGATE_XPATH)
	public static WebElement printNavigate;

	public static final String C2012_BTS_XPATH = GENERIC_SOURCE_XPATH + "//a[text()='C2012 BTS']";

	@FindBy(how = How.XPATH, using = C2012_BTS_XPATH)
	public static WebElement bts;

	@FindBy(how = How.XPATH, using = GENERIC_SOURCE_XPATH + "//a[text()='C2012 Reports']")
	public static WebElement reports;

	@FindBy(how = How.XPATH, using = GENERIC_SOURCE_XPATH + "//a[text()='Lock Report']")
	public static WebElement lockReport;

	public static final String SHARED_DELTA_REPORT_XPATH = GENERIC_SOURCE_XPATH + "//a[text()='Shared Delta Report']";

	@FindBy(how = How.XPATH, using = SHARED_DELTA_REPORT_XPATH)
	public static WebElement sharedDeltaReport;
	
	@FindBy(how = How.XPATH, using = GENERIC_SOURCE_XPATH + "//div[@id='sourcesourceReports']" + "//a[text()='Lock Report']")
	public static WebElement legoReportsLockReport;

	public static final String NOVUS_EXTRACTS_XPATH = GENERIC_SOURCE_XPATH + "//a[text()='Novus Extracts']";
	
	@FindBy(how = How.XPATH, using = NOVUS_EXTRACTS_XPATH)
	public static WebElement novusExtracts;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOVUS_EXTRACTS_XPATH + "//a[text()='Lexis Extract']")
	public static WebElement novusLexisExtract;
	
	@FindBy(how = How.XPATH, using = GENERIC_NOVUS_EXTRACTS_XPATH + "//a[text()='Historical Extract']")
	public static WebElement novusHistoricalExtract;
}
	
	
