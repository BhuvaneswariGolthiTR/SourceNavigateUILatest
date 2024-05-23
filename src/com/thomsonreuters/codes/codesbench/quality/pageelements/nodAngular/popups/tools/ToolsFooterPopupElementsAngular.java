package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ToolsFooterPopupElementsAngular
{

    @FindBy(how = How.XPATH, using = "//button[text()='Cancel']")
    public static WebElement cancel;

    @FindBy(how = How.XPATH, using = "//button[text()='Create Workflow']")
    public static WebElement createWorkflow;

}
