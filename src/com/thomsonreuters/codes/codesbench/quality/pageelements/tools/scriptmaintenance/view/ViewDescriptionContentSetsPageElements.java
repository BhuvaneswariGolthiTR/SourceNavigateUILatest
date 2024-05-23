package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.view;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ViewDescriptionContentSetsPageElements
{
    private static final String PAGE_TITLE = "Description and Content Sets";
    private static final String DIALOG_CONTAINER_XPATH = "//mat-dialog-container/toolbox-script-description";
    public static final String PAGE_XPATH = String.format("%s/h5[@class='mat-dialog-title' and contains(text(),'%s')]", DIALOG_CONTAINER_XPATH, PAGE_TITLE);

    @FindBy(how = How.XPATH, using = "//table//td/b[contains(text(),'Script Id')]/../../td[2]")
    public static WebElement scriptId;

    @FindBy(how = How.XPATH, using = "//table//td/b[contains(text(),'Script Name')]/../../td[2]")
    public static WebElement scriptName;

    @FindBy(how = How.XPATH, using = "//table//td/b[contains(text(),'Version Description')]/../../td[2]")
    public static WebElement versionDescription;

    @FindBy(how = How.XPATH, using = "//table//td/b[contains(text(),'Pub Tag')]/../../td[2]")
    public static WebElement pubTag;

    public static final String VALID_CONTENT_SETS_FOR_SCRIPT = DIALOG_CONTAINER_XPATH + "//div[@class='cdk-virtual-scroll-content-wrapper']//mat-list-item";

    // public static final String LIST_OF_SCRIPTS = DIALOG_CONTAINER_XPATH + "//table[@role='grid']//tbody[@role='rowgroup']//tr";

    public static final String PRINT = "//button/span[contains(text(),'Print')]";

    public static final String CLOSE = "//button/span[contains(text(),'Close')]";
}
