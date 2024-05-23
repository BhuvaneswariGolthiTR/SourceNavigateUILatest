package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class UpdateYearOrSessionPageElements
{
    public static final String UPDATE_YEAR_OR_SESSION_PAGE_TITLE  = "Update Year/Session";
    public static final String WORKFLOW_LINK = "//a[contains(@href,'workflowId')]";

    @FindBy(how = How.ID, using = "saveButton-button")
    public static WebElement saveButton;

    @FindBy(how = How.ID, using = "pageForm:year")
    public static WebElement yearTextBox;

    @FindBy(how = How.ID, using = "pageForm:session")
    public static WebElement sessionDropdown;

    @FindBy(how = How.ID, using = "cancelButton-button")
    public static WebElement cancelButton;
}