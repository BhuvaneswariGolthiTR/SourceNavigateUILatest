package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.ocextract.popups;

public class UploadFilePopupElements
{
    public static final String UPLOAD_FILE_POPUP = "//div[@class='cdk-overlay-pane custom-dialog-container']";
    public static final String UPLOAD_BUTTON = UPLOAD_FILE_POPUP + "//button[span[contains(text(),'Upload')]]";
    public static final String CANCEL_BUTTON = UPLOAD_FILE_POPUP + "//button[span[contains(text(),'Cancel')]]";
    public static final String SUBMIT_BUTTON = UPLOAD_FILE_POPUP + "//button[span[contains(text(),'Submit')]]";
    public static final String UPLOAD_CHECK_MARK = UPLOAD_FILE_POPUP + "//mat-icon[text()='done']";
    public static final String NO_CHOSEN_FILE_ERROR = UPLOAD_FILE_POPUP + "//mat-error[contains(@style, 'visibility: visible') and contains(text(),'Please select a file to upload')]";
}