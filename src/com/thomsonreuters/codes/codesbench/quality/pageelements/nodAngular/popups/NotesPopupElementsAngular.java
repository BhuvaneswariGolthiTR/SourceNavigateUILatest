package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups;

public class NotesPopupElementsAngular
{
	public static final String NOTES_POPUP = "//app-draggable-popup";
	public static final String HEADER = NOTES_POPUP + "/div[contains(@class, 'header')]";
	public static final String INPUT_AREA = NOTES_POPUP + "//app-note-input";
	public static final String TEXT_AREA = INPUT_AREA +"//textarea[contains(@class, 'note-text-area')]";
	public static final String SAVE_BUTTON = INPUT_AREA + "//button[text()='Save']";
}
