package com.thomsonreuters.codes.codesbench.quality.pageelements;

public class CommonPageElements
{
	public static final String CLOSE_BUTTON = "//input[@id='pageForm:close' or @id='pageForm:closeButton'] | //button[@id='close-button' or @id='b_close' or @id='cancelButton-button']";
	public static final String SUBMIT_BUTTON = "//input[@id='pageForm:submit' or @name='submitButton' or @value='Submit' or @type='submit' or @id='submitButton' or @id='pageForm:submitButton']";
	public static final String OK_BUTTON = "//input[@id='pageForm:ok' or @id='pageForm:okButton' or @value='OK'] | //button[@id='bt_ok']";

	public static final String CANCEL_BUTTON = "//input[@id='pageForm:cancel' or @id='cancelButton' or @id='pageForm:cancelButton' or @value='Cancel' or @id='inputAsForm:cancelButton' or @onclick='cancel();'] | //button[@id='cancelButton-button' or @id='b_cancel']";
	public static final String PROCESSING_PLEASE_WAIT_XPATH = "//div[contains(@id,'wait')]";
	public static final String SAVE_BUTTON = "//input[@id='pageForm:saveButton' or @id='pageForm:performSaveButton' or @id='pageForm:saveBtn' or @value='Save' or @id='inputAsForm:saveButton']";
	public static final String DELETE_BUTTON = "//input[@id='pageForm:deleteBtn']";
    public static final String PUBLISH_BUTTON_XPATH = "//input[@id='pageForm:okButton']";

    public static final String APPLY_ALL_BUTTON = "//button[@id='b_applyAll']";
	
	public static final String PROCESSING_SPINNER = "//img[@id='pageForm:processing']"; // TODO maybe rename ButtonsPOM to e.g. CommonPOM ?
	public static final String VALUE_INPUT = "//input[@id='pageForm:value']";
	public static final String SEARCH_BUTTON = "//input[@value = 'Search' or @name='searchForm:searchButton'] | //button[@id='searchForm:searchButton-button']";
	public static final String WINDOW_CLOSE_CROSS_BUTTON = "//a[@class='container-close']";
	
	public static final String VALID_WORKFLOW_ID_REGEX = "\\d{7}";
}
