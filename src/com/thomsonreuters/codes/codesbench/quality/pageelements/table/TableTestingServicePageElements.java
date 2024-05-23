package com.thomsonreuters.codes.codesbench.quality.pageelements.table;

public class TableTestingServicePageElements
{
    //General
    static public final String TABLE = "//div[@class='yui-dt yui-dt-scrollable']";
    
    static public final String TABLE_HEAD = /*TABLE + */"//div[@class='yui-dt-hd']//thead";
    
    //static public final String TABLE_HEADER_TEMPLATE = TABLE_HEAD + "//tr[@class='yui-dt-last']/th[contains(@id, '-fixedth-%s')]";
    static public final String TABLE_HEADER_TEMPLATE = /*TABLE_HEAD + */"//tr[contains(@class, 'yui-dt-')]/th[contains(@id, '-fixedth-%s')][.//a]";
    
    static public final String TABLE_BODY = /*TABLE + */"//div[@class='yui-dt-bd']//tbody[@class='yui-dt-data']";
    
    static public final String TABLE_CELL_TEMPLATE = /*TABLE_BODY + */"//td[contains(@headers, '-th-%s') and (not(contains(@class,'hidden')))]/div";
    
    static public final String ANY_ROW_XPATH = TABLE + TABLE_BODY + "/tr[%s]/td[3]";
    
    //Filter
    public static final String FILTER_LOADING_BAR = "//div[ contains(translate(@id, 'WAIT', 'wait'), 'wait')  and contains(@id, '_c') and not(contains(@class, 'hidden')) ]";
    
    //Grouping page
    public static final String FILTER_SECTION_NUMBER_INPUT = "//div[contains(@id, 'sectionNumberFilter')]//input";
    public static final String FILTER_DELTA_LEVEL_INPUT = "//div[contains(@id, 'deltaLevelFilter')]//input";
    public static final String FILTER_DELTA_COUNT_INPUT = "//div[contains(@id, 'deltaCountFilter')]//input";
    
    //Query notes
    public static final String FILTER_QUERY_NOTE_TYPE_INPUT = "//input[@id='typeFilter']";
    public static final String FILTER_QUERY_NOTE_KEYWORD_INPUT = "//input[@id='targetKeywordFilter']";
    public static final String FILTER_QUERY_NOTE_STATUS_INPUT = "//input[@id='statusFilter']";
    public static final String FILTER_QUERY_NOTE_TARGET_VALUE_INPUT = "//input[@id='targetValueFilter']";
    
    //Stage Check Reports
    public static final String FILTER_STAGE_CHECK_REPORTS_CONTENT_SET_INPUT = "//input[@id='contentSetLongNameFilter']";
    public static final String FILTER_STAGE_CHECK_REPORTS_SHORT_NAME_INPUT = "//input[@id='shortNameFilter']";
    public static final String FILTER_STAGE_CHECK_REPORTS_DOC_TYPE_INPUT = "//input[@id='docTypeFilter']";
    public static final String FILTER_STAGE_CHECK_REPORTS_DOC_NUMBER_INPUT = "//input[@id='docNumberFilter']";
    public static final String FILTER_STAGE_CHECK_REPORTS_CONTENT_TYPE_INPUT = "//input[@id='contentTypeShortNameFilter']";
    public static final String FILTER_RENDITION_ORGANIZATION_INPUT = "//input[@id='contentTypeShortNameFilter']";
    
    //Subscribed Cases
    public static final String FILTER_SUBSCRIBED_CASES_CASE_SERIAL_NUMBER_INPUT = "//input[@id='caseSerialNumberFilter']";
}
