package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page;

public class ScriptMaintenanceErrorPageElements
{
    private static final String PAGE_TITLE = "ERROR";
    private static final String DIALOG_CONTAINER_XPATH = "//mat-dialog-container/toolbox-message-dialog";
    public static final String PAGE_XPATH = String.format("%s/h1[contains(@class,'mat-dialog-title') and contains(text(),'%s')]", DIALOG_CONTAINER_XPATH, PAGE_TITLE);

    private static final String MESSAGE_ELEMENT_XPATH = "//mat-dialog-content/div[contains(text(),'%s')]";
    public static final String EXISTING_PUBTAG_MESSAGE = String.format(MESSAGE_ELEMENT_XPATH, "There is a existing pubtag and related script already assigned. Action will not be performed");
    public static final String EXISTING_998_MESSAGE = String.format(MESSAGE_ELEMENT_XPATH, "There is already an existing 998 script assigned");

    public static final String OK_BUTTON = "//div[@class='mat-dialog-actions']//button[span[contains(text(),'OK')]]";
}
