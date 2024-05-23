package com.thomsonreuters.codes.codesbench.quality.pageelements.viewmanagament;

public class SourceNavigateReportsPageElements
{
	public static final String MISMATCHED_REPORTS_PAGE_TITLE = "Mismatched Report";
	public static final String YEAR_FILTER_XPATH = "//div[@id='yearAutoComplete']/input[@id='yearFilter']";
	public static final String CONTENT_SET_FILTER_XPATH = "//div[@id='jurisdictionShortNameAutoComplete']/input[@id='jurisdictionShortNameFilter']";
	public static final String DOC_TYPE_FILTER_XPATH = "//div[@id='docTypeAutoComplete']/input[@id='docTypeFilter']";
	public static final String DOC_NUMBER_FILTER_XPATH = "//input[@id='docNumberFilter']";
	public static final String TOP_CLAMSHELL_MENU_XPATH = "//div[@id='clamshell_toggle_1']//span";
	public static final String CLAMSHELL_SIDEBAR_XPATH = "//div[@id='sidebar']";
	public static final String SOURCE_LOAD_DATE_CALENDAR_XPATH = "//button[@title='Show Calendar']//img[@alt='Show Calendar']";
	public static final String SEARCH_FILTER_WINDOW_TITLE = "Search Filter";
	public static final String SEARCH_FILTER_CALENDAR_XPATH = "//button[@id='showSingleDateCal']";
	
	//reports
	public static final String REPORT_CLEAR_ALL_FILTERS = "//div[@id='yui-dt0-paginator1']//a[text()='Clear All Filters']";
    public static final String REPORT_REFRESH = "//div[@id='yui-dt0-paginator1']//a[text()='Refresh']";
    public static final String REPORT_CLEAR_SORT = "//div[@id='yui-dt0-paginator1']//a[text()='Clear Sort']";
    public static final String REPORT_CLEAR_SELECTION = "//div[@id='yui-dt0-paginator1']//a[text()='Clear Selection']";
	//Bill Gap Count Window
	public static final String NO_RECORDS_FOUND = "//div[@id = 'contentArea']//div[text()='No records found.']";
	public static final String NONSELECTED_CONTENT_SET_LIST = "//select[contains(@id, 'list1')]";
	public static final String CONTENT_SET_MOVE_TO_SELECTEDLIST = "//input[@id = 'pageForm:contentSetList:move1to2']";
	public static final String SELECTED_CONTENT_SET_LIST= "//select[contains(@id, 'list2')]";
	public static final String SELECT_ONE_OPTION_ARROW = "//input[@value='>']";
	public static final String SELECT_ALL_OPTIONS_ARROW = "//input[@value='>>']";
	public static final String DESELECT_ONE_OPTION_ARROW = "//input[@value='<']";
	public static final String DESELECT_ALL_OPTIONS_ARROW = "//input[@value='<<']";
    public static final String NONSELECTED_CONTENT_SET_OPTION = "//select[contains(@id, 'list1')]/option[text()='%s']";
    
    //Mismatched
    public static final String DATE_TEXTBOX = "//input[@id='singleDate']";
    public static final String MISMATCHED_REPORT_NO_RECORDS_FOUND = "//div[@id='contentMainGridArea']//div[@id='gridContainer']//tr//td//div[text() = 'No records found.']";
    public static final String MISMATCHED_REPORTS_CALENDAR = "//div[@class='calheader']";
}
