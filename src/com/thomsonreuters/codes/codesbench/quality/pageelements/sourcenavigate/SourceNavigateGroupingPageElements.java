package com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigate;

public class SourceNavigateGroupingPageElements 
{
	// common xpaths both for Delta and Section
	public static final String GROUPING_TAB_OF_GIVEN_LEVEL = "//div[@id='tabbedNavigation']/span[contains(@id,'tab') and text() = ' %s Group ']";
	public static final String GROUPING_TAB_EFFECTIVE_DATE_OF_GIVEN_GROUP = "//td[@headers='yui-dt0-th-groupName ']//div[text()='%s']/ancestor::tr//td[contains(@headers,'EffectiveDate')]/div";
	
	// Section/Delta Groups Pane
	public static final String SECTION_DELTA_GROUPING_GROUP_POSITION = "//div[@id='groups']//tbody[@class='yui-dt-data']/tr[%s]/td[contains(@headers, 'groupDisplayName')]/div";	
	public static final String SECTION_DELTA_GROUPING_GROUP_IS_SELECTED = "//div[@id='groups']//tbody[@class='yui-dt-data']/tr[contains(@class, 'yui-dt-selected')]/td[contains(@headers, 'groupDisplayName')]/div";
	public static final String SECTION_DELTA_GROUPING_GROUP_NAME = "//div[@id='groups']//tbody[@class='yui-dt-data']//td[contains(@headers, 'groupDisplayName')]//div[contains(text(), '%s')]";
	public static final String SECTION_DELTA_GROUPING_NEW_GROUPNAME_INPUT = "//div[@id='newGroupDialogContainer']//input[@name='title']";
	public static final String SECTION_DELTA_GROUPING_SECTION_GROUPNAME_ON_POSITION_IN_GRID = "//tbody[@class='yui-dt-data']/tr[%s]/td[@headers='yui-dt0-th-groupName ' and ./div/text()='%s']";
	public static final String SECTION_DELTA_GROUPNAME_IN_GRID = "//tbody[@class='yui-dt-data']/tr//td[@headers='yui-dt0-th-groupName ' and ./div/text()='%s']";
	public static final String SECTION_DELTA_GROUPING_SECTION_LOCK = "//tbody[@class='yui-dt-data']/tr[./td[@headers='yui-dt0-th-lockViewable ']//img[@src='/sourceNavigateWeb/resources/images/Personal-Lock.gif'] and ./td[@headers='yui-dt0-th-groupName ']/div/text()='%s']";
	public static final String SECTION_DELTA_GROUPING_TOOLBAR_BUTTONS = "//div[@id='toolbarContainer']//ul[@class='first-of-type']//li[%s]//a";
	public static final String SECTION_DELTA_GROUPING_BOTTOM_BUTTONS = "//div[@id='bottom']/div//span[%s]/span/button";
	public static final String SECTION_DELTA_GROUPING_SECTION = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr";
	public static final String SECTION_DELTA_GROUPING_SECTION_GROUPNAME_IN_COLUMN = "//td[contains(@headers, 'groupName')]/div";	
	public static final String SECTION_DELTA_GROUPING_NEXT_PAGE_LINK = "//a[@title='Next Page'][1]";
	public static final String SECTION_DELTA_GROUPING_PREVIOUS_PAGE_LINK = "//a[@title='Previous Page'][1]";
	public static final String SECTION_DELTA_GROUPING_SECOND_PAGE_SPAN = "//span[contains(@class, 'current-page') and text() = '2']";	
	public static final String SECTION_DELTA_GROUPING_DELTAS_COUNT_IN_NEW_GROUP = "//div[@id='groups']//tbody[@class='yui-dt-data']//tr[td/div[text()='%s']]//td[contains(@headers, 'deltaCount')]/div[text()='%s']";
	public static final String SECTIONS_DELTAS_FROM_SELECTED_GROUP_TAB = "//em[contains(text(), 'Deltas from selected group') or contains(text(), 'Sections from selected group')]";
	public static final String SECTION_DELTA_ALL_DELTAS_TAB = "//em[contains(text(), 'All deltas') or contains(text(), 'All sections')]";
	public static final String SECTION_DELTA_GROUP_LIST = "//div[@id='groups']//tbody[@class='yui-dt-data']/tr";

	// Section/Delta Pane
	public static final String SECTION_DELTA_GROUPING_SECTION_NUMBER = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr/td[contains(@headers, 'sectionNumber')]";
	public static final String SECTION_DELTA_GROUPING_GROUP_ON_POSITION = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr[%s]/td[contains(@headers, 'groupName')]";
	public static final String SECTION_DELTA_GROUPING_HIGHLIGHTED_GROUP_ON_POSITION = "//div[@id='sectionGrid']//tbody[@class='yui-dt-data']/tr[%s][contains(@class,'selected')]/td[contains(@headers, 'groupName')]";
	
	//Section Grouping
	public static final String SECTION_GROUPING_TAB_XPATH = "//div[@id='tabbedNavigation']/span[contains(@id,'tab') and text() = ' Section Group ']";
	public static final String SECTION_GROUPING_EFFECTIVEDATE_IN_EDITOR = "//source.section[@num='%s']//effective.date.block//date";
	public static final String SECTION_GROUPING_EFFECTIVE_DATE_IN_GRID = "//tbody[@class='yui-dt-data']//td[@headers='yui-dt0-th-groupName ' and ./div/text()='%s']/ancestor::*[1]//td[@headers='yui-dt0-th-yui-dt-col38 yui-dt0-th-EffectiveDate ']";
	public static final String SECTION_GROUPING_SOURCE_NUMBER_IN_EDITOR = "//source.section[@num='%s']//span[contains(text(), 'Source Section Number')]";
	public static final String SECTION_GROUPING_INSERT_IN_EDITOR = "//intro.para[@display-name='Section Introductory Paragraph']//text[1]";
	
	//Delta Grouping
	public static final String DELTA_GROUPING_TAB_XPATH = "//div[@id='tabbedNavigation']/span[contains(@id,'tab') and text() = ' Delta Group ']";
	public static final String DELTA_GROUPING_EFFECTIVE_DATE_IN_GRID = "//tr[descendant::td[contains(@headers,'yui-dt0-th-groupName')]/div[@class='yui-dt-liner' and text()='%s']]//td[contains(@class,'yui-dt0-col-EffectiveDate')]/div[@class='yui-dt-liner']";
	public static final String DELTA_GROUPING_INSERT_IN_EDITOR = "//delta//para//paratext";
	
	//Grouping filter
	public static final String FILTER_SECTION_NUMBER_INPUT = "//div[contains(@id, 'sectionNumberFilter')]//input";
	public static final String FILTER_DELTA_LEVEL_INPUT = "//div[contains(@id, 'deltaLevelFilter')]//input";
	public static final String FILTER_DELTA_COUNT_INPUT = "//div[contains(@id, 'deltaCountFilter')]//input";
	public static final String FILTER_CLEAR_BUTTON = "//button[@id='clearButtons-button']";
	public static final String FILTER_CLEAR_DROPDOWN_CLEAR_FILTERS = "//a[text()='Clear Filters']";
	public static final String FILTER_SORT_SECTION_NUMBER ="//div[contains(@id, 'sectionNumber')]//a[contains(@class, 'sortable')]";
	
	//Grouping table
	public static final String GROUPING_TABLE_SECTION_NUMBER_HEADER = "//div[@id='sectionGrid']//div[@class='yui-dt-hd']//thead//tr[@class='yui-dt-last']/th[@id='yui-dt8-fixedth-sectionNumber']";
	public static final String GROUPING_TABLE_SECTION_NUMBER_CELLS = "//div[@id='sectionGrid']//div[@class='yui-dt-hd']//thead//tr[@class='yui-dt-last']/th[@id='yui-dt8-fixedth-sectionNumber']";
	
	
	//Buttons
	public static final String SECTION_DELTA_GROUPING_SAVE_BUTTON = "//button[contains(text(), 'Save')]"; //TODO
	public static final String SECTION_DELTA_GROUPING_REMOVE_GROUP_BUTTON = "//div[@id='toolbarContainer']//a[contains(text(), 'Remove Group')]";
	public static final String SECTION_DELTA_GROUPING_MOVE_DOWN = "//div[@id='toolbarContainer']//a[contains(text(), 'Move Down')]";
	public static final String SECTION_DELTA_GROUPING_MOVE_UP = "//div[@id='toolbarContainer']//a[contains(text(), 'Move Up')]";
	public static final String SECTION_DELTA_GROUPING_NEW_GROUP = "//div[@id='toolbarContainer']//a[contains(text(), 'New Group')]";
	public static final String SECTION_DELTA_GROUPING_APPLY_AND_CLOSE_BUTTON= "//button[contains(text(), 'Apply and close')]";
	public static final String SECTION_DELTA_GROUPING_APPLY_BUTTON= "//button[text()='Apply']";
	public static final String SECTION_DELTA_GROUPING_CANCEL_BUTTON= "//button[@id='cancelButton-button']"; //TODO
	public static final String YES_BUTTON= "//button[contains(text(),'Yes')]"; 
	
}
