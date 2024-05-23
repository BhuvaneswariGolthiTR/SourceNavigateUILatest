package com.thomsonreuters.codes.codesbench.quality.pageelements.editor;

public class EditorMessagePageElements
{

	// log message area
	public static final String LOG_MESSAGE_AREA = "//div[contains(@id, 'message2')]";
	public static final String MESSAGE_AREA = "(//div[@id='message2']|//div[@id='message3'])";
	public static final String IDS = MESSAGE_AREA + "/a";
	public static final String SAVE_MESSAGE = "//div[@id='message2' and contains(.,'Document save initiated...save completed in ')]";
	public static final String WARN_SPAN = MESSAGE_AREA + "/span[@class='log_warn' and text()='warn']";
	public static final String ERROR_SPAN = MESSAGE_AREA + "/span[@class='log_error' and text()='error']";
	public static final String INFO_SPAN = MESSAGE_AREA + "/span[@class='log_info' and text()='info']";
	public static final String INITIAL_LOAD_MESSAGE = "//div[@id='message2' and contains(.,'Initial load completed in')]";
}
