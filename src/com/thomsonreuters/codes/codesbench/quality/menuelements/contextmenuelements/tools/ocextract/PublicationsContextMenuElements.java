package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.ocextract;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PublicationsContextMenuElements
{
    public static final String CONTEXT_MENU = "//div[contains(@class,'ag-menu')]";

    @FindBy(how = How.XPATH, using = "//span[contains(@class,'ag-menu-option-text') and text()='Select Publication']")
    public static WebElement selectPublication;
}