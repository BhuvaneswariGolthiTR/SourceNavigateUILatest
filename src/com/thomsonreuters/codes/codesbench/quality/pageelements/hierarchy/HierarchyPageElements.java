package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HierarchyPageElements
{		
	public static final String HIERARCHY_PUB_NAVIGATE_PAGE_TITLE = "Hierarchy Edit (PUB)";
	
	@FindBy(how = How.ID, using = "topPageForm:nodeUuidInput")
	public static WebElement nodeUuidInput;

	// Title Page
	public static final String PAGE_TITLE = "Hierarchy Edit";
	
	// Top search tabs
	@FindBy(how = How.ID, using = "findNodeLabel")
	public static WebElement findNodeTabXpath;

	@FindBy(how = How.XPATH, using = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]")
	public static WebElement selectedNodesInSiblingMetadataGrid;

	public static final String SIBLING_METADATA_SELECTED_GRID_ROW_XPATH = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]";

	@FindBy(how = How.XPATH, using = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]/td[@headers='yui-dt0-th-Keyword ']/div")
	public static WebElement siblingMetadataSelectedGridRowKeyword;

	@FindBy(how = How.XPATH, using = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]/td[@headers='yui-dt0-th-Value ']/div")
	public static WebElement siblingMetadataSelectedGridRowValueXpath;

	@FindBy(how = How.XPATH, using = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]/td[@headers='yui-dt0-th-Vols ']/div")
	public static WebElement siblingMetadataSelectedGridRowVols;

	public static final String SIBLING_METADATA_SELECTED_GRID_ROW_STAT = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]//td[contains(@class,'Stat')]//div";

	@FindBy(how = How.XPATH, using = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]//td[contains(@class,'HID')]//div")
	public static WebElement siblingMetadataSelectedGridRowHid;

	@FindBy(how = How.XPATH, using = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]/td[@headers='yui-dt0-th-ModifiedDate ']/div")
	public static WebElement siblingMetadataSelectedModifiedDate;

	public static final String REPUBLISH_BY_HIERARCHY_XPATH = "//a[text()='Republish by Hierarchy']";

	// Update metadata popup
	public static final String METADATA_STATUS = "//select[@id='pageForm:status']";
	
	public static final String SUBMIT_BUTTON = "//input[@id='pageForm:submit']";
}
