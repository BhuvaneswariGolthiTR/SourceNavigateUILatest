package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.querynotereport;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class QueryNoteReportGridPageElements
{
	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr")
	public static List<WebElement> queryNotesInGrid;
	
	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@class, 'status')]")
	public static WebElement firstQueryNoteStatus;
	
	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr//td[contains(@class, 'status')]/div")
	public static List<WebElement> queryNoteStatus;
	
	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@class, 'resolvedComment')]")
	public static WebElement firstQueryNoteResolvedComment;

	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@class, 'id')]/div")
	public static WebElement firstQueryNoteUUID;

	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr//td[contains(@class, 'hierarchyBreadcrumb')]/div")
	public static List<WebElement> queryNoteHierarchyBreadcrumb;
	//public static WebElement queryNoteHierarchyBreadcrumb;

	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[contains(@class, 'hierarchyBreadcrumb')]")
	public static WebElement firstQueryNoteHierarchyBreadcrumb;

//	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]/td[18]/div")
//	public static List<WebElement> queryText;

	public static final String QUERY_TEXT = "//tbody[@class='yui-dt-data']/tr[1]/td[18]/div";

	public static final String NO_RECORDS_FOUND = "//div[contains(text(),'No records found.')]";


	
}
