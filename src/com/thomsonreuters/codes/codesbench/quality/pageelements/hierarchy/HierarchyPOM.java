package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

public class HierarchyPOM {

	// Insert in Hierarachy
	public static final String VALUE_TEXTBOX = "//input[@id='pageForm:userEnteredData:0:value']";
	public static final String VALUE_TEXTBOX_BY_NUMBER = "//input[@id='pageForm:userEnteredData:%d:value']";
	public static final String DEPTH_TEXTBOX = "//input[@id='pageForm:userEnteredData:0:depth']";
	public static final String DEPTH_TEXTBOX_BY_NUMBER = "//input[@id='pageForm:userEnteredData:%d:depth']";
	public static final String EFFECTIVE_START_DATE_TEXTBOX = "//input[@name='pageForm:userEnteredData:0:effStartDate']";
	public static final String EFFECTIVE_END_DATE_TEXTBOX = "//input[@id='pageForm:userEnteredData:0:effEndDate']";

	// Volume Information
	public static final String EDIT_BOV_CONTENT = "//*[@id='pageForm:editBovContentDS']";
	public static final String EDIT_EOV_CONTENT = "//*[@id='pageForm:editEovContentDS']";
	public static final String EDIT_BOV_XML_CONTENT = "//*[@id='pageForm:editBovXmlContent']";
	public static final String EDIT_EOV_XML_CONTENT = "//*[@id='pageForm:editEovXmlContent']";
	public static final String BOV_METADATA = "//*[@id='pageForm:bovMetadata']";
	public static final String EOV_METADATA = "//*[@id='pageForm:eovMetadata']";
	public static final String ADD_BOV_SCRIPT = "//*[@id='pageForm:addBovScript']";
	public static final String REMOVE_BOV_SCRIPT = "//*[@id='pageForm:removeBovScript']";
	public static final String JUMP_TO_BEGIN = "//*[@id='pageForm:jumpToBegin']";
	public static final String ADD_EOV_SCRIPT = "//*[@id='pageForm:addEovScript']";
	public static final String REMOVE_EOV_SCRIPT = "//*[@id='pageForm:removeEovScript']";
	public static final String JUMP_TO_END = "//*[@id='pageForm:jumpToEnd']";	

	public static final String SUBMIT_BUTTON = "//input[@id='pageForm:submit']";
}
