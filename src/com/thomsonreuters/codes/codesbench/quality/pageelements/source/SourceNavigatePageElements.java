package com.thomsonreuters.codes.codesbench.quality.pageelements.source;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class SourceNavigatePageElements
{

    @FindBy(how = How.XPATH, using = "div[@id='top_content']/div/span")
    public static List<WebElement> TOP_CONTENT;

    // Window Name
    public static final String SOURCE_NAVIGATE_LINEAGE_TITLE = "Lineage Navigate - WORKSTATION";
    public static final String PAGE_TITLE = "Pending Rendition Navigate";
    public static final String NAVIGATE_PAGE_TITLE = "Navigate";
    public static final String SOURCE_NAVIGATE_SECTION_PAGE_TITLE = "Pending Section Navigate";
    public static final String PAGE_TITLE_DELTA_NAVIGATE = "Delta Navigate";
    public static final String PAGE_TITLE_DELTA_GROUP_NAVIGATE = "Delta Group Navigate";
    public static final String DELTA_NAVIGATE_AFFECTING_SAME_TARGET_PAGE_TITLE = "Delta Navigate (affecting same target)";
    // CM
    public static final String EDIT_CM_SUBMENU = "//li/a[text()='Edit']";
    public static final String EDIT_RENDITION_WITH_DS_CM_ITEM = "//li/a[text()='Rendition Dynamic Scrolling']";
    // Tabs
    public static final String LINEAGE_TAB_XPATH = "//div[@id='tabbedNavigation']/span[contains(@id,'tab') and text() = ' Lineage ']";
    public static final String RENDITION_TAB_XPATH = "//div[@id='tabbedNavigation']/span[contains(@id,'tab') and text() = ' Rendition ']";
    public static final String SECTION_TAB_XPATH = "//div[@id='tabbedNavigation']/span[contains(@id,'tab') and text() = ' Section ']";
    public static final String SECTION_GROUP_TAB_XPATH = "//div[@id='tabbedNavigation']/span[contains(@id,'tab') and text() = ' Section Group ']";
    public static final String DELTA_TAB_XPATH = "//div[@id='tabbedNavigation']/span[contains(@id,'tab') and text() = ' Delta ']";
    public static final String DELTA_GROUP_TAB_XPATH = "//div[@id='tabbedNavigation']/span[contains(@id,'tab') and text() = ' Delta Group ']";
    public static final String REFRESH_BUTTON_TOP_XPATH = "//div[@id='contentMainTabArea']/div[@id='tabbedNavigation']/input";
    // Find
    public static final String FIND_DOCUMENT_BUTTON = "//*[@id='findDocumentButton']";
    public static final String DOCUMENT_UUID_INPUT = "//*[@id='pageForm:docId']";
    public static final String FIND_BY_UUID_BUTTON = "//*[@id='pageForm:docIdSearch']";
    public static final String FIRST_RENDITION = "//tbody[@class='yui-dt-data']/tr/td[3]";
    public static final String SELECTED_RENDITION = "//tr[contains(@class,'yui-dt-selected')]/td[3]";
    public static final String PLEASE_WAIT = "//*[@id='wait']";

    public static final String RENDITION_VERSION_DATE_FILTER_XPATH = "//input[@id='sourceVersionDateFilter']";
    public static final String RENDITION_LOAD_DATE_FILTER_XPATH = "//input[@id='sourceLoadDateFilter']";

    // Grid Renditions
    public static final String RENDITIONS_XPATH = "//tbody[@class='yui-dt-data']/tr";
    public static final String LOCK_IMG_XPATH = "//img[contains(@title, 'Locked')]";
    public static final String DELETED_IMG_XPATH = "//img[@alt='Deleted']";
    public static final String LOCK_IMG_FIRST_REND_XPATH = "//tr[contains(@class, 'first')]//td[contains(@headers,'lockViewable')]//img[contains(@title, 'Locked')]";
   public static final String ORANGE_FLAG_ON_RENDITION = "//tbody[@class='yui-dt-data']/tr[contains(@class, 'yui-dt-')]/td[contains(@class, 'validationFlagViewable')]/div/img[contains(@src, 'flag_type_validation_orange.gif')]";
    public static final String BLUE_FLAG_ON_RENDITION = "//tbody[@class='yui-dt-data']/tr[contains(@class, 'yui-dt-')]/td[contains(@class, 'validationFlagViewable')]/div/img[contains(@src, 'flag_type_validation.gif')]";
    public static final String WESTLAW_LOAD_PREFIX_XPATH = "//div[@id='gridContainer']//div[@class='yui-dt-bd']//tbody[@class='yui-dt-data']//td[contains(@class,'westlawLoad')]//div";
    public static final String SECTION_DELTA_GROUPING_SECTION_GROUPNAME_ON_POSITION_IN_GRID = "//tbody[@class='yui-dt-data']/tr[%s]/td[@headers='yui-dt0-th-groupName ' and ./div/text()='%s']";
    public static final String SECTION_DELTA_GROUPNAME_IN_GRID = "//tbody[@class='yui-dt-data']/tr//td[@headers='yui-dt0-th-groupName ' and ./div/text()='%s']";
    public static final String SECTION_DELTA_GROUPING_SECTION_LOCK = "//tbody[@class='yui-dt-data']/tr[./td[@headers='yui-dt0-th-lockViewable ']//img[@src='/sourceNavigateWeb/resources/images/Personal-Lock.gif'] and ./td[@headers='yui-dt0-th-groupName ']/div/text()='%s']";
    public static final String GROUPING_TAB_EFFECTIVE_DATE_OF_GIVEN_GROUP = "//td[@headers='yui-dt0-th-groupName ']//div[text()='%s']/ancestor::tr//td[contains(@headers,'EffectiveDate')]/div";

    // Grid Columns
    public static final String MULTIPLES_MESSAGE_XPATH = "//span[text()='Displaying all results regardless of filters. Clear all filters to continue.']";
    public static final String CREATE_BOOKMARK_BUTTON = "//img[@id ='bookmarkButton']";
    public static final String VIEW_MANAGEMENT_BUTTON = "//img[@id = 'viewManagementButton']";

    // Bottom Row of Buttons
    public static final String REFRESH_BUTTON_XPATH = "//span[contains(@id,'yui-pg0-1-page-report')]//span[@class='refresh-button-container']//button";
    public static final String EXPORT_TO_EXCEL = "//a/img[@id='exportToExcel']";
    public static final String PUBLIC_VIEW_XPATH = "//span[contains(@id,'yui-pg0-1-page-report')]//span[@class='public-view-container']//button";
    public static final String PUBLIC_VIEW_ALL_COLS = "//span[contains(@id,'yui-pg0-1-page-report')]//span[@class='public-view-container']/div/div/ul/li/a[contains(text(), 'all cols')]";
    public static final String PUBLIC_VIEW_ALL_COLS_WITH_CDP_AUTO_INTEGRATION =
            "//span[contains(@id,'yui-pg0-1-page-report')]//span[@class='public-view-container']/div/div/ul/li/a[contains(text(), 'all cols (with CPD Auto Integration)')]";
    public static final String CLEAR_BUTTON_XPATH = "//button[@id='clear-menu-button-button']";
    public static final String SELECT_BUTTON_XPATH = "//span[@id='select-menu-button']";

    //Current view label
    public static final String VIEW_LABEL_XPATH = "//span[contains(text(),'Current view:')]";

    // Clear button dropdown menu must be open for these to be found.
    public static final String CLEAR_DROPDOWN_CLEAR_FILTERS = "//a[text()='Clear Filters']";

    //select button dropdown items, select menu must be open
    public static final String SELECT_DRODOWN_SELECT_ALL_XPATH = "//a[text()='Select All on Page']";
    
    // Rendition Baselines Window
    public static final String CURRENT_BASELINE_DESCRIPTION_XPATH = "//tr[1]//td[contains(@class, 'description')]//div";
	public static final String BASELINE_ON_A_ROW_WITH_NUMBER_GIVEN = "//tbody[@class='yui-dt-data']/tr[%s]";

    // Rendition Baselines Context Menu
    public static final String RESTORE_BASELINE_CONTEXT_MENU_XPATH = "//li/a[text()='Restore Baseline']";
    public static final String COMPARE_BASELINES = "//a[text()='Compare Baselines']";
    public static final String SELECTED_SECTIONS_DELTA_COUNT = "//tr[contains(@class,'selected')]/td[contains(@class,'deltaCount')]/div";
	
    public static final String RENDITIONS_VALIDATE_IMAGES = "//tbody[@class='yui-dt-data']/tr[%s]/td[contains(@headers,'validationFlagViewable')]//img";

    //GRID
	public static final String ID = "//tbody[@class='yui-dt-data']/tr/td[contains(@headers, 'yui-dt0-th-id ')]/div";
    public static final String DOCUMENT_ID = "//td[@headers='yui-dt0-th-yui-dt-col46 yui-dt0-th-docNumber ']/div[@class='yui-dt-liner']";
	public static final String SELECTED_ID = "//tr[contains(@class, 'selected')]/td[contains(@headers, 'yui-dt0-th-id ')]/div";
	public static final String EFFECTIVE_DATE_COLUMN_TEXT_LIST = "div[@id='gridContainer']//tbody[@class='yui-dt-data']//td[contains(@class,'effectiveDate')]//div";
	public static final String VALIDATE_REPORT_DOC_NUM_IN_GRID = "//tbody[@class='yui-dt-data']/tr/td[contains(@class, 'yui-dt-col-docNumber') and not (contains(@class,'hidden'))]";
	public static final String FINISHED_BOOKMARK_GRID = "//table[@id='pageForm:finishedBookmarkGrid']";
	public static final String LOCATION_INTEGRATION_STATUS_IN_GRID = "//td[contains(@headers, 'yui-dt0-th-intStatus')]/div/a";
	public static final String SOURCE_FILE_SIZE_IN_GRID = "//tbody[@class='yui-dt-data']/tr/td[contains(@headers, 'sourceFileSize ')]"; 
	public static final String ALL_COLUMNS_IN_GRID = "//tr[@class='yui-dt-first' or @class= 'yui-dt-last']/th"; 
	public static final String DELETE_IMG = "//tr[contains(@class, 'yui-dt-first')]/td[contains(@class, 'deleteFlagViewable')]/div/img[contains(@alt, 'Deleted')]";
	public static final String MULTI_FLAG_XPATH = "//div[@id='gridContainer']//tbody[@class='yui-dt-data']//tr//td[contains(@class,'multipleDuplicateViewable')]//img";
	public static final String DELETE_FLAG_XPATH = "//div[@id='gridContainer']//tbody[@class='yui-dt-data']//tr//td[contains(@class,'deleteFlagViewable')]//img";
    public static final String STAGE_CHECK_REPORT_SEARCH_RESULTS_RESULTS_FRAME = "//iframe[@id='resultFrame']";
	public static final String LINEAGE_NAVIGATE_PAGE_TITLE = "Lineage Navigate";
	public static final String DELTA_NAVIGATE_PAGE_TITLE = "Delta Navigate";

    //Progress Bar
	public static final String PROGRESS_BAR = "//*[@id='wait']";
}
