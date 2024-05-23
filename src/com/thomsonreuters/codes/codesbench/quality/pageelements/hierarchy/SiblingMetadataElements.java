package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class SiblingMetadataElements
{
	public static final String SELECTED_NODE_ROW = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]";
	public static final String SIBLING_METADATA_GRID = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//td[contains(@class, 'Value')]/div";
	public static final String SIBLING_METADATA_GRID_ROWS = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']/tr";
	public static final String SIBLING_METADATA_GRID_ROW = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']/tr[%d]";
	public static final String SIBLING_METADATA_GRID_ROW_BY_STRING = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']/tr[%s]";

	@FindBy(how = How.XPATH, using = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]")
	public static WebElement selectedNodeRow;

	@FindBy(how = How.XPATH, using = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]//td[contains(@class,'Flag')]//img")
	public static WebElement selectedNodeRowFlag;

	public static final String KEYWORD = "/td[contains(@class,'Keyword')]/div";
	public static final String VALUE = "/td[contains(@class,'Value')]/div";
	public static final String VOLS = "/td[contains(@class,'Vols')]/div";
	public static final String START_DATE = "/td[contains(@class,'StartDate')]/div";
	public static final String TAX_TYPE = "/td[contains(@class,'TaxType')]/div";
	public static final String PRODUCT_TAG = "/td[contains(@class,'ProductTag')]/div";
	public static final String END_DATE = "/td[contains(@class,'EndDate')]/div";
	public static final String HID = "/td[contains(@class,'HID')]/div";
	public static final String PUBLISHING_STATUS = "/td[contains(@class,'PublishStatus')]/div/img";

	public static final String SELECTED_NODE_KEYWORD = SELECTED_NODE_ROW + KEYWORD;
	public static final String SELECTED_NODE_VALUE = SELECTED_NODE_ROW + VALUE;
	public static final String SELECTED_NODE_VOLS = SELECTED_NODE_ROW + VOLS;
	public static final String SELECTED_NODE_START_DATE = SELECTED_NODE_ROW + START_DATE;
	public static final String SELECTED_NODE_TAX_TYPE = SELECTED_NODE_ROW + TAX_TYPE;
	public static final String SELECTED_NODE_PRODUCT_TAG = SELECTED_NODE_ROW + PRODUCT_TAG;
	public static final String SELECTED_NODE_END_DATE = SELECTED_NODE_ROW + END_DATE;
	public static final String SELECTED_NODE_HID = SELECTED_NODE_ROW + HID;
	public static final String SELECTED_NODE_PUBLISHING_STATUS = SELECTED_NODE_ROW + PUBLISHING_STATUS;

	public static final String SIBLING = "/following-sibling::tr/";

	public static final String NODE_BELOW_SELECTED_NODE_ROW_KEYWORD = SELECTED_NODE_ROW + SIBLING + KEYWORD;
	public static final String NODE_BELOW_SELECTED_NODE_ROW_VALUE = SELECTED_NODE_ROW + SIBLING + VALUE;
	public static final String NODE_BELOW_SELECTED_NODE_ROW_VOLS = SELECTED_NODE_ROW + SIBLING + VOLS;
	public static final String NODE_BELOW_SELECTED_NODE_ROW_HID = SELECTED_NODE_ROW + SIBLING + HID;
	public static final String NODE_BELOW_SELECTED_NODE_START_DATE = SELECTED_NODE_ROW + SIBLING + START_DATE;
	public static final String NODE_BELOW_SELECTED_NODE_END_DATE = SELECTED_NODE_ROW + SIBLING + END_DATE;
	public static final String NODE_BELOW_SELECTED_NODE_ROW_PUBLISHING_STATUS = SELECTED_NODE_ROW + SIBLING + PUBLISHING_STATUS;
	public static final String NODE_BELOW_SELECTED_NODE_ROW_PRODUCT_TAG = SELECTED_NODE_ROW + SIBLING + PRODUCT_TAG;
	public static final String NODE_BELOW_SELECTED_NODE_ROW_TAX_TYPE = SELECTED_NODE_ROW + SIBLING + TAX_TYPE;

	public static final String RM_SYMBOL_OF_SELECTED_NODE = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]//td[contains(@class,'RM')]//div//img[contains(@src,'%s')]";
	public static final String SELECTED_NODE_VOLS_NUMBER_XPATH = SELECTED_NODE_ROW + "/td[12]";

	public static final String SELECTED_NODE_MODIFIED_DATE_XPATH = SELECTED_NODE_ROW + "/td[contains(@class,'ModifiedDate')]/div";
	public static final String SIBLING_METADATA_GRID_END_DATE_XPATH = "//div[@id='sibMetadata']//tbody[@class='yui-dt-data']//td[contains(@class,'EndDate')]/div[text()='%s']";
	public static final String SELECTED_NODE_ROW_PUBLISH_STATUS = SELECTED_NODE_ROW + "//td[contains(@class,'yui-dt-col-PublishStatus')]/div/img";
	public static final String SIBLING_METADATA_GRID_VALUECOLUMN_VALUE_XPATH = "//div[@id='sibMetadata']//tbody[@class='yui-dt-data']//td[contains(@class,'Value')]/div[text()='%s']";
	public static final String DELETED_NODE_BY_VALUE_XPATH = "//div[@id='sibMetadata']//tbody[@class='yui-dt-data']//td[contains(@class,'Value')]/div/strike[text()='%s']";
	public static final String DELETED_NODE_BY_VALUE_AND_MODIFIED_DATE_XPATH = "//div[@id='sibMetadata']//tbody[@class='yui-dt-data']//tr[td[contains(@class,'Value')]/div/strike[text()='%s'] and td[contains(@class,'ModifiedDate')]/div/strike[contains(text(), '%s')]]";
	public static final String NODE_BY_VALUE_AND_START_DATE = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[td[contains(@class, 'Value')]/div[contains(text(), '%s')] and td[contains(@class, 'StartDate')]/div[contains(text(), '%s')]]";
	public static final String PUBLISHING_STATUS_COLUMN_XPATH = "//div[@id='siblingMetadataGrid']//tr[contains(@class,'yui-dt-first')]/th[@id='yui-dt0-th-PublishStatus']";
	public static final String NODE_BY_HID = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[td[contains(@class,'HID')]/div[contains(text(),'%s')]]";
	public static final String TAX_TYPE_COLUMN_XPATH = "//div[@id='siblingMetadataGrid']//tr[contains(@class,'yui-dt-first')]/th[@id='yui-dt0-th-TaxType']";
	public static final String SIBLING_METADATA_GRID_VALUE_PUBLISHING_STATUS = SIBLING_METADATA_GRID_VALUECOLUMN_VALUE_XPATH + "/../../td[contains(@class,'yui-dt-col-PublishStatus')]/div/img";
	public static final String NODE_UUID = "//tr[contains(@class, 'selected')]/td[contains(@headers, 'yui-dt0-th-id ')]/div";
	public static final String PRODUCT_TAG_COLUMN_XPATH = "//div[@id='siblingMetadataGrid']//tr[contains(@class,'yui-dt-first')]/th[@id='yui-dt0-th-ProductTag']";

	@FindBy(how = How.XPATH, using = SELECTED_NODE_ROW + "/td[contains(@class,'yui-dt-col-PublishStatus')]/div/img")
	public static List<WebElement> selectedNodesPublishingStatuses;

	@FindBy(how = How.XPATH, using = SELECTED_NODE_ROW + "//td[contains(@class,'Flag')]//div//img[contains(@src,'%s')]")
	public static List<WebElement> selectedNodesValidationFlags;

	public static final String VALIDATION_FLAG_OF_SELECTED_NODE = "//div[@id='siblingMetadataGrid']//tbody[@class='yui-dt-data']//tr[contains(@class,'selected')]//td[contains(@class,'Flag')]//div//img[contains(@src,'%s')]";

	@FindBy(how = How.XPATH, using = SELECTED_NODE_ROW + "/td[contains(@class,'HID')]/div")
	public static WebElement selectedNodeHID;
}
