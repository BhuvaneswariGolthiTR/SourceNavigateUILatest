package com.thomsonreuters.codes.codesbench.quality.pageelements.tools.scriptmaintenance.view;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ViewPrintScriptRulesPageElements
{
    private static final String PAGE_TITLE = "View/Print Script Rules";
    private static final String DIALOG_CONTAINER_XPATH = "//mat-dialog-container/toolbox-script-print-rules";
    public static final String PAGE_XPATH = String.format("%s/h5[@class='mat-dialog-title' and contains(text(),'%s')]", DIALOG_CONTAINER_XPATH, PAGE_TITLE);

    @FindBy(how = How.XPATH, using = "//table//td/b[contains(text(),'Script Id:')]/../../td[2]")
    public static WebElement scriptId;

    @FindBy(how = How.XPATH, using = "//table//td/b[contains(text(),'Script Name:')]/../../td[2]")
    public static WebElement scriptName;

    @FindBy(how = How.XPATH, using = "//table//td/b[contains(text(),'Version Description:')]/../../td[2]")
    public static WebElement versionDescription;

    @FindBy(how = How.XPATH, using = "//table//td/b[contains(text(),'Pub Tag:')]/../../td[2]")
    public static WebElement pubTag;
    //list of rules???


    public static final String PRINT = "//button/span[contains(text(),'Print')]";

    @FindBy(how = How.XPATH, using = "//button/span[contains(text(),'Close')]")
    public static WebElement close;

    public static final String EFFECTED_CONTENT_SETS = "//mat-dialog-container/toolbox-script-print-rules//div[@class='mat-list-item-content']/span";
//
//    @FindBy(how = How.XPATH, using = "//button/span[text()='OK']")
//    public static WebElement okButton;
}
