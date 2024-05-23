package com.thomsonreuters.codes.codesbench.quality.pageelements.source.reports;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LockReportPageElements
{
	public static final String LOCK_REPORT_PAGE_TITLE = "Lock Report";

	public static final String LOCKED_DOCUMENT = "//tbody[@class='yui-dt-data']//tr["
			+ "td[contains(@class,'contentSet')]/div[contains(text(),'%s')] and "
			+ "td[contains(@class,'document')]/div[contains(text(),'%s')] and "
			+ "td[contains(@class,'userName')]/div[contains(text(),'%s')]]";
	public static final String DOC_NAME_OF_DOCUMENT_GIVEN = "td[contains(@class,'document')]/div";
	public static final String USER_NAME_OF_DOCUMENT_GIVEN = "td[contains(@class,'userName')]/div";
	public static final String LOCK_DATE_OF_DOCUMENT_GIVEN = "td[contains(@class,'lockDate')]/div";

	public static final String DOCUMNET_LOCKED_NOT_BY_USER = "//tbody[@class='yui-dt-data']//tr["
			+ "td[contains(@class,'userName')]/div[not(contains(text(),'%s'))]]";

	public static final String LOCK_DATE_IN_GRID = "//a[contains(@href,'lockDate')]";


	@FindBy(how = How.XPATH, using = "//div[@id='gridContainer']/div[contains(@id, 'paginator')]/span/a[contains(@onclick, 'refreshGrid')]")
	public static WebElement refreshButton;

	@FindBy(how = How.ID, using = "contentSetFilter")
	public static WebElement contentSetFilter;

	@FindBy(how = How.XPATH, using = "(//select[@class='yui-pg-rpp-options'])[2]")
	public static WebElement lockReportPaginationDropDown;
}
