package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ScriptMaintenanceMetadataPageElements
{
    public static final String SCRIPT_METADATA_BLOCK = "//toolbox-script-details-form";

    public static final String SELECTED_CONTENT_SETS = SCRIPT_METADATA_BLOCK + "//div[@class='collector-column']/span[contains(text(),'Selected')]/../cdk-virtual-scroll-viewport//mat-list-option//div[@class='mat-list-text']";

    @FindBy(how = How.XPATH, using = SCRIPT_METADATA_BLOCK + "//input[@formControlName='description']")
    public static WebElement versionDescription;

    @FindBy(how = How.XPATH, using = SCRIPT_METADATA_BLOCK + "//input[@formControlName='script']")
    public static WebElement scriptName;

    public static final String SAVE_BUTTON = "//toolbox-script-details//button[span[contains(text(),'Save')]]";
}
