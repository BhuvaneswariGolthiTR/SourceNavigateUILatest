package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AuditCorrectionsPageElements
{
    public static final String AUDIT_CORRECTIONS_PAGE_TITLE = "Audit Corrections";
    public static final String CORRECTIONS_AUDITOR_DROPDOWN = "//select[@id='pageForm:correctionsAuditor']";

    @FindBy(how = How.ID, using = "pageForm:saveButton")
    public static WebElement saveButton;

    @FindBy(how = How.ID, using = "pageForm:cancelButton")
    public static WebElement cancelButton;
}
