package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.view;

public class ViewPrintListOfScriptPageElements
{
    private static final String PAGE_TITLE = "View Print List of Script";
    private static final String DIALOG_CONTAINER_XPATH = "//mat-dialog-container/toolbox-script-print-list-dialog";
    public static final String PAGE_XPATH = String.format("%s/h5[@class='mat-dialog-title' and contains(text(),'%s')]", DIALOG_CONTAINER_XPATH, PAGE_TITLE);

    public static final String LIST_OF_SCRIPTS = DIALOG_CONTAINER_XPATH + "//table[@role='grid']//tbody[@role='rowgroup']//tr";
    public static final String LIST_OF_SCRIPTS_IDS = DIALOG_CONTAINER_XPATH + "//table[@role='grid']//tbody[@role='rowgroup']//tr/td[contains(@class, 'cdk-column-script')]";
}
