package com.thomsonreuters.codes.codesbench.quality.pageelements;

public class QueryNoteManagerPageElements 
{
    
    public static final String QUERY_NOTE_POPUP_URL = "queryNoteWeb/appPages/popups/generic";
    
    // date filter
    public static final String DATE_FILTER_POPUP_WINDOW_TITLE = "Enter Date Criteria for Grid Filter";
    public static final String DATE_TEXTBOX = "//input[@id='singleDate']";
    
    //table
    public static final String REFRESH_BUTTON = "//input[@value='Refresh']";
    public static final String ACTION_DATE_CALENDAR_BUTTON = "//input[@id='actionDateFilter']/following-sibling::button";
    
	//Context Menu
	public static final String CONTEXT_MENU_DELETE_NOTE = "//div[@id='contextMenu']//a[text()='Delete Query Note' or text()='Delete Query Notes']";
	public static final String CONTEXT_MENU_RESOLVE_NOTE = "//div[@id='contextMenu']//a[text()='Resolve Query Note' or text()='Resolve Query Notes']";
	
	public static final String QUERY_NOTE_STATUS_COMBOBOX = "//select[@id='pageForm:queryNoteStatusUserSelected']";
	public static final String QUERY_NOTE_RESOLUTION_COMMENT_FIRST_RADIOBUTTON = "//input[@id='pageForm:queryNoteResolvedCommentRadio0:0']";
	public static final String QUERY_NOTE_RESOLUTION_COMMENT_SECOND_RADIOBUTTON = "//input[@id='pageForm:queryNoteResolvedCommentRadio1:0']";
	public static final String QUERY_NOTE_RESOLUTION_COMMENT_FIRST_COMBOBOX = "//select[@id='pageForm:queryNoteResolvedCommentUserSelectedMenu']";
	public static final String QUERY_NOTE_RESOLUTION_COMMENT_SECOND_COMBOBOX = "//select[@id='pageForm:queryNoteResolvedCommentUserSelectedText']";
	public static final String DELETE_BUTTON = "//input[@id='pageForm:deleteBtn']";
	
	//Create query note window in editor
	public static final String INSERT_QUERY_NOTE_WINDOW_TITLE = "Create Query Note";
	public static final String QUERY_NOTE_TYPE_DROPDOWN = "//select[@id='pageForm:queryNoteType']";
	public static final String ACTION_DATE_FIELD = "//input[@id='pageForm:queryNoteActionDate']";
	public static final String QUERY_NOTE_TEXT_FIELD = "//textarea[@id='pageForm:queryNoteText']";
}
