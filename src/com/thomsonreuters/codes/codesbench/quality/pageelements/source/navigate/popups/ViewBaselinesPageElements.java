package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ViewBaselinesPageElements
{
	public static final String PAGE_TITLE = "View Baselines";

	public static final String VERSION_BY_INDEX =  "//tbody[@class='yui-dt-data']/tr[%s]";

	public static final String BASELINE_ON_A_ROW_WITH_NUMBER_GIVEN = "//tbody[@class='yui-dt-data']/tr[%s]";

	public static final String NUMBER_OF_GIVEN_BASELINE = "/td[@headers='yui-dt0-th-baselineNumber ']/div";

	@FindBy(how = How.XPATH, using = "//div[@id='gridContainer']//td[contains(@class, 'baselineNumber')]/div[text()='0'] | //div[@id='gridContainer']//td[contains(@class, 'process')]/div[text()='BaseLine Restore']")
	public static WebElement originalBaseline;

	@FindBy(how = How.XPATH, using = "//div[contains(@id, 'baselineNumber-liner')]/span/a[contains(@title, 'Click to sort')]")
	public static WebElement numberSort;

	@FindBy(how = How.XPATH, using = "//div[@id='gridContainer']/div[@class='yui-dt-bd']/table/tbody[@class='yui-dt-data']/tr[td[contains(@class, 'baselineNumber')] /div[text()='Current']]/td[contains(@class, 'description')]/div")
	public static WebElement currentBaselineDescription;

	@FindBy(how = How.CLASS_NAME, using = "container-close")
	public static WebElement close;

	@FindBy(how = How.XPATH, using = "//a[text()='View Baseline']")
	public static WebElement viewBaselineDynamicScrolling;

	@FindBy(how = How.ID, using = "yui-pg0-1-last-link21")
	public static WebElement lastPageButton;

	@FindBy(how = How.ID, using = "yui-pg0-1-first-link14")
	public static WebElement firstPageButton;
}
