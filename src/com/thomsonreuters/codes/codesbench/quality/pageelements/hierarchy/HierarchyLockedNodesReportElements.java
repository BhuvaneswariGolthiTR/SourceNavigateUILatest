package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HierarchyLockedNodesReportElements
{
	public static final String HIERARCHY_LOCKED_NODES_REPORT_PAGE_TITLE = "Locked Nodes Report";
	public static final String SELECT_NODE_OF_GIVEN_UUID = "//tbody[@class='yui-dt-data']/tr/td[contains(@class, 'NodeUUID')]/div[text()='%s']";
	public static final String NODE_WITHOUT_GIVEN_USERNAME = "//tbody[@class='yui-dt-data']/tr/td[contains(@class, 'UserName')]/div[text()!='%s']";
	public static final String GRID_TABLE = "//div[@id='gridContainer']/div/table/tbody[2]";

	@FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]")
	public static WebElement selectedNode;

	@FindBy(how = How.ID, using = "pageForm:repopulate")
	public static WebElement refreshButton;
}
