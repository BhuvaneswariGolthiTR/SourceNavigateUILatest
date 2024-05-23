package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.searchandreplace;



public class CopyTableToAnotherContentSetPageElements 
{
	public static final String COPY_TABLE_TO_ANOTHER_CONTENT_SET_PAGE_TITLE = "Copy Table To Another Content Set";
	public static final String COPY_TABLE_TO_ANOTHER_CONTENT_SET_IFRAME_NAME = "modalPopup";

	//This xpath will need to be tested, it wants to be able to select any given content set
	public static final String CONTENT_SET_TO_SELECT = "//select[@id='pageForm:selectedContentSet']/option[text()='%s']";
	public static final String TABLE_NAME_TEXTBOX = "//input[@id='pageForm:tableName']";
	public static final String TABLE_DESCRIPTION_TEXTBOX = "//input[@name='pageForm:j_idt21']";
}
