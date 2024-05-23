package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class ScriptMaintenanceGridPageElements
{
    //pubtag, description -> script name - version description, status
    public static final String GENERIC_SCRIPT_ROW = "//div[@role='grid']//div[@role='row' and div[@col-id='pubtag' and contains(text(),'%s')] and div[@col-id='0' and contains(text(),'%s')] and div[@col-id='status.status' and contains(text(),'%s')]]";
    public static final String GENERIC_SELECTED_SCRIPT_ROW = "//div[@role='grid']//div[@role='row' and contains(@class,'ag-row-selected') and div[@col-id='pubtag' and contains(text(),'%s')] and div[@col-id='0' and contains(text(),'%s')] and div[@col-id='status.status' and contains(text(),'%s')]]";

    public static final String SCRIPT_ID_XPATH = GENERIC_SCRIPT_ROW + "/div[@col-id='1']";

    public static final String PROGRESS_BAR = "//mat-progress-bar";

    public static final String HIDE_DELETED_CHECKBOX = "//mat-checkbox[@formControlName='hideDeleted']";

    public static final String CONTENT_SET_IN_TOP_LEFT_CORNER = "//toolbox-script-content-set-name";

    public static final String SAVE_BUTTON = "//button[span[contains(text(),'Save')]]";

    @FindBy(how = How.XPATH, using =  "//input[@formControlName='filterText']")
    public static WebElement contentSetFilter;

}
