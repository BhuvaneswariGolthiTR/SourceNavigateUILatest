package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class SourceNavigateGridPageElements
{
	public static final String ROWS = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr";
	public static final String ROW = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[%d]";
	public static final String FIRST_ROW = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[1]/td[3]";
	public static final String TRACK_CLAMSHELL_DIV = "//div[@id='trackClamshellDiv']/div/span";
	public static final String FIRST_ITEM_TAX_TYPE_ADD = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[1]/td[contains(@class,'taxTypeAdd')]/div";
	public static final String FIRST_ITEM_LOCATION_INTEGRATION_STATUS = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[1]/td[contains(@class,'intStatus')]/div";
	public static final String ITEM_LOCATION_INTEGRATION_STATUS = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[%d]/td[contains(@class,'intStatus')]/div";
	public static final String ITEM_BY_LOCATION_INTEGRATION_STATUS = "//tbody[@class='yui-dt-data']/tr[td/div/a[contains(text(),'%s')]]";
	public static final String ITEM_BY_DELTA_GROUP_NAME = "//tbody[@class='yui-dt-data']/tr[td[contains(@class,'groupName')]/div[text()='%s']]";
	public static final String TAX_TYPE_ADD_VALUE_BY_DELTA_GROUP_NAME = ITEM_BY_DELTA_GROUP_NAME + "/td[contains(@class,'taxTypeAdd')]/div";
	public static final String FIRST_ITEM_TARGET_STATUS_MESSAGE = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[1]/td[contains(@class,'targetEvents')]/div";
	public static final String ITEM_TARGET_STATUS_MESSAGE = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[%d]/td[contains(@class,'targetEvents')]/div";
	public static final String RENDITIONS_LIST_XPATH = "//tbody[@class='yui-dt-data']/tr";
	public static final String X_RENDITION = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[%s]/td[3]";
	public static final String RENDITION_STATUS_COLUMN_FIRST_ROW = "//tr[contains(@class, 'first')]/td[contains(@headers, 'renditionStatusViewable')]/div[@class='yui-dt-liner']";
	public static final String NO_RECORDS_FOUND = "//div[@id = 'contentArea']//div[text()='No records found.']";

	public static final String RENDITION_LIST_XPATH = "//div[@id='gridContainer']//tbody[@class='yui-dt-data']//tr";
	public static final String DOCUMENT_NUMBER = "//td[contains(@class, 'yui-dt-col-docNumber') and not (contains(@class,'hidden'))]/div";
	public static final String FILE_NUMBER = "//td[contains(@class, 'yui-dt-col-fileNumber')]/div";
	public static final String SOURCE_FILE_SIZE_IN_GRID = "//tbody[@class='yui-dt-data']/tr/td[contains(@headers, 'sourceFileSize ')]";
	public static final String RENDITION_CONTENT_SET_LIST_XPATH = RENDITION_LIST_XPATH + "/td[contains(@class,'yui-dt-col-contentSetViewable')]//div";
	public static final String RENDITION_YEAR_LIST_XPATH = RENDITION_LIST_XPATH + "//td[contains(@class,'yui-dt-col-year')]//div";
	public static final String RENDITION_SESSION_LIST_XPATH = RENDITION_LIST_XPATH + "//td[contains(@class,'yui-dt-col-session')]//div";
	public static final String RENDITION_DOC_NUMBER_LIST_XPATH  = "//td[contains(@class, 'yui-dt-col-docNumber') and not (contains(@class,'hidden'))]/div";
	public static final String RENDITION_FILE_NUMBER = "//td[contains(@class, 'yui-dt-col-fileNumber')]/div";
	public static final String RENDITION_CONTENT_TYPE_LIST_XPATH = RENDITION_LIST_XPATH + "//td[contains(@class,'yui-dt-col-contentTypeViewable')]//div";
	public static final String RENDITION_STATUS_LIST_XPATH = RENDITION_LIST_XPATH + "//td[contains(@class,'yui-dt-col-renditionStatusViewable')]//div";
	public static final String RENDITION_CORRELATION_ID_LIST_XPATH = RENDITION_LIST_XPATH + "//td[contains(@class,'yui-dt-col-correlationId') and not(contains(@class,'hidden'))]//div";
	public static final String SELECTED_RENDITION_TRACKING_STATUS = "//tbody[@class='yui-dt-data']/tr[contains(@class, 'yui-dt-selected')]/td[contains(@class, 'col-sourceStatus2')]/div";
	public static final String INTERNAL_ENACTMENT_IN_GRID = "//tbody[@class='yui-dt-data']/tr/td[@headers='yui-dt0-th-yui-dt-col31 yui-dt0-th-internalEnactmentViewable ']";
	public static final String CLASS_NUMBER_IN_GRID = "//tbody[@class='yui-dt-data']/tr/td[contains(@headers, '-classNum ')]";
	public static final String RENDITION_RESULT = "//div[@class='yui-dt-paginator grid-pg-container']//span[contains(text(), 'Results')]";

	public static final String DELTA_TAB_TARGET_NODE = "//tr[1]/td[contains(@class,'targetNode')]//*[last()]";
	public static final String SELECTED_DELTA_TARGET_NODE = "//tr[contains(@class, 'selected')]//td[contains(@class, 'targetNode')]//div/a";
	public static final String DELTA_TAB_TARGET_SUBNODE = "//tr[1]/td[contains(@class,'TargetSubNode')]/div";
	public static final String DELTA_TAB_ALL_DELTAS_INT_STATUSES = "//tbody[@class='yui-dt-data']//tr/td[contains(@class,'yui-dt-col-intStatus')]//a";
	public static final String DELTA_TAB_SELECTED_DELTA_INT_STATUS = "//tr[contains(@class,'selected')]/td[contains(@class,'yui-dt-col-intStatus')]//a";
	public static final String ERROR_IMG = "//img[contains(@src, 'flag_type_error.gif')]";
	public static final String DELTA_COLUMN_HEADER = "//td[contains(@headers,'%s')]";
	public static final String SELECTED_DELTA_ROW = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[contains(@class,'selected')]";
	public static final String SELECTED_DELTA_COLUMN = SELECTED_DELTA_ROW + DELTA_COLUMN_HEADER + "/div";
	public static final String TAX_TYPE_ADD_LIST = "//tbody[@class='yui-dt-data']//tr//td[contains(@class,'yui-dt-col-taxTypeAdd')]//div";

	//First Row
	public static final String FIRST_ROW_INSTRUCTION_NOTE = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[1]/td[contains(@class, 'instructionNotes')]/div";
	public static final String FIRST_ROW_EFFECTIVE_DATE = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[1]/td[contains(@class, 'EffectiveDate')]/div";
	public static final String RENDITION_FIRST_ROW_EFFECTIVE_DATE = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[1]/td[contains(@class, 'effectiveDate')]/div";
	public static final String FIRST_ROW_DELTA_COUNT = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[1]/td[contains(@class, 'deltaCount')]/div";
	public static final String FIRST_ROW_EFFECTIVE_DATE_PROVISION = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[1]/td[contains(@class, 'effectiveDateProvision')]/div";
	public static final String FIRST_ROW_EFFECTIVE_COMMENTS = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[1]/td[contains(@class, 'yui-dt0-col-effectiveComments')]/div";
	public static final String FIRST_ROW_QUERY_DATE = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[1]/td[contains(@class, 'queryDate')]/div";
	public static final String FIRST_ROW_ADDED_AS_QD = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[1]/td[contains(@class, 'addAsQD')]/div/img[@src='/sourceNavigateWeb/resources/images/check.gif']";
	public static final String FIRST_ROW_MISCELLANEOUS = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[1]/td[contains(@class, 'miscellaneous')]/div";
	public static final String FIRST_ROW_ONLINE_PRODUCT_TAG = "//div[@id='gridContainer']/div[4]/table/tbody[2]/tr[1]/td[contains(@class, 'OnlineProductTag')]/div/img";
	public static final String FIRST_ROW_DIFFICULTY_LEVEL = "//tbody[@class='yui-dt-data']/tr/td[contains(@headers, 'difficultyLevel')]";
	public static final String DELTA_COUNT_COLUMN_FIRST_ROW = "//tr[contains(@class, 'first')]//td[contains(@class, 'deltaCount')]/div[@class='yui-dt-liner']";
	public static final String WEST_ID_COLUMN_FIRST_ROW = "//tr[contains(@class, 'first')]//td[contains(@headers, 'westId')]//div[@class='yui-dt-liner']";
	public static final String WESTLOAD_QUICK = "//div[@id='gridContainer']//div[@class='yui-dt-bd']//tbody[@class='yui-dt-data']//td[contains(@class,'westlawLoad')]//div[text()='Quick']";

	public static final String FIRST_SECTION_NUMBER_XPATH = "//td[@headers='yui-dt9-th-sectionNumberFilter yui-dt9-th-sectionNumber ']";
	public static final String MOVE_TO_GROUP_WITH_GIVEN_NAME_XPATH = "//div[@id='groupSubmenu']//a[text()='%s']";
	public static final String SECTION_NUMBER_UNDER_NUMBER_XPATH = "(//td[@headers='yui-dt9-th-sectionNumberFilter yui-dt9-th-sectionNumber '])[%s]";
	public static final String SUBSECTION_NUMBER_UNDER_NUMBER_XPATH = "(//td[@headers='yui-dt9-th-subSectionNumberFilter yui-dt9-th-subSectionNumber '])[%s]";
	public static final String ACTION_UNDER_NUMBER_XPATH = "(//td[@headers='yui-dt9-th-actionFilter yui-dt9-th-action '])[%s]";
	public static final String DELTA_LEVEL_UNDER_NUMBER_XPATH = "(//td[@headers='yui-dt9-th-deltaLevelFilter yui-dt9-th-deltaLevel '])[%s]";
	public static final String DELTA_GROUPING_GROUP_WITH_GIVEN_NAME_XPATH = "//div[@id='groups']//tbody[@class='yui-dt-data']//td[contains(@headers, 'groupDisplayName')]//div[contains(text(), '%s')]";
	public static final String FIRST_DELTA_COUNT_XPATH = "//td[@headers='yui-dt9-th-deltaCountFilter yui-dt9-th-deltaCount ']";

	public static final String EFFECTIVE_DATE_PROVISION_IN_GRID = "//tbody[@class='yui-dt-data']/tr/td[contains(headers()='-effectiveDateProvision )']";
	public static final String EFFECTIVE_COMMENTS_IN_GRID = "//tbody[@class='yui-dt-data']/tr/td[@headers='yui-dt0-th-effectiveComments ']";
	public static final String INSTRUCTION_NOTE_IN_GRID = "//tbody[@class='yui-dt-data']/tr/td[@headers='yui-dt0-th-instructionNotes ']";
	public static final String MISCELLANEOUS_IN_GRID = "//tbody[@class='yui-dt-data']/tr/td[@headers='yui-dt0-th-miscellaneous ']";
	public static final String QUERY_DATE_IN_GRID = "//tbody[@class='yui-dt-data']/tr/td[@headers='yui-dt0-th-yui-dt-col70 yui-dt0-th-queryDate ']";
	public static final String EFFECTIVE_DATE_IN_GRID = "//tbody[@class='yui-dt-data']/tr/td[@headers='yui-dt0-th-yui-dt-col60 yui-dt0-th-effectiveDate ']";
	public static final String QUERY_NOTE_IN_GRID = "//tbody[@class='yui-dt-data']/tr/td[@headers='yui-dt0-th-yui-dt-col72 yui-dt0-th-addAsQD ']//img[@src='/sourceNavigateWeb/resources/images/check.gif']";

	public static final String ITEM_BY_SECTION_GROUP_NAME = "//tbody[@class='yui-dt-data']/tr[td[contains(@class,'groupName')]/div[text()='%s']]";
	public static final String TAX_TYPE_ADD_VALUE_BY_SECTION_GROUP_NAME = ITEM_BY_SECTION_GROUP_NAME + "/td[contains(@class,'taxTypeAdd')]/div";
	public static final String SECTION_GROUPING_GROUP_WITH_GIVEN_NAME_XPATH = "//div[@id='groups']//tbody[@class='yui-dt-data']//td[contains(@headers, 'groupDisplayName')]//div[contains(text(), '%s')]";
	public static final String FIRST_SUBSECTION_NUMBER_XPATH = "//td[@headers='yui-dt9-th-subSectionNumberFilter yui-dt9-th-subSectionNumber ']";

	public static final String YEAR_COLUMN_HEADER_SORT_ASCENDING = "//a[contains(@title, 'Click to sort ascending') and text()= 'Year']";
	public static final String YEAR_COLUMN_HEADER_SORT_DESCENDING = "//a[contains(@title, 'Click to sort descending') and text()= 'Year']";

	public static final String SOURCE_NAVIGATE_GRID_PAGE_FILTER_COLUMNS = "//thead//div[contains(@id, '%s')]";



	@FindBy(how = How.XPATH, using ="//tbody[@class='yui-dt-data']/tr/td[contains (@class, 'taxTypeAdd ')]/div")
	public static List<WebElement> taxTypeAddColumnValue;

	@FindBy(how = How.XPATH, using = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr")
	public static List<WebElement> deltaGridRows;

	@FindBy(how = How.XPATH, using = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr[contains(@class,'selected')]/td[contains(@headers, 'sectionNumber')]")
	public static WebElement selectedDelta;

	@FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-sectionNumberFilter yui-dt9-th-sectionNumber ']")
	public static List<WebElement> deltaSectionNumbers;

	@FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-groupNameFilter yui-dt9-th-groupName ']")
	public static List<WebElement> groupNames;

	@FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-deltaLevelFilter yui-dt9-th-deltaLevel ']")
	public static List<WebElement> deltaLevels;

	@FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-actionFilter yui-dt9-th-action ']")
	public static List<WebElement> actions;

	@FindBy(how = How.XPATH, using = "//a[text()='Move to Group']")
	public static WebElement moveToGroup;

	@FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-sectionNumberFilter yui-dt9-th-sectionNumber ']")
	public static List<WebElement> sectionNumbers;

	@FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-subSectionNumberFilter yui-dt9-th-subSectionNumber ']")
	public static List<WebElement> subSectionNumbers;

	@FindBy(how = How.XPATH, using = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr")
	public static List<WebElement> sectionGridRows;

	@FindBy(how = How.XPATH, using = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr[contains(@class,'selected')]/td[contains(@headers, 'groupName')]")
	public static WebElement selectedSection;

	@FindBy(how = How.XPATH, using = "//td[@headers='yui-dt9-th-deltaCountFilter yui-dt9-th-deltaCount ']")
	public static List<WebElement> deltaCounts;

	public static final String ITEM_MARKED_AS_SELECTED = "//div[@id='gridContainer']//tr[contains(@class,'yui-dt-selected')]";

	public static final String MULTIPLES_DUPLICATES_MESSAGE = "//div[@id='messageHeader']//span[text()='Displaying all results regardless of filters. Clear all filters to continue.']";
}
