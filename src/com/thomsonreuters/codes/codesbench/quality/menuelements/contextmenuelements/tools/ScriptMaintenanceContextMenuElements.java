package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ScriptMaintenanceContextMenuElements
{
    //the div wrapping the span in these xpaths has an attribute for disabled
    @FindBy(how = How.XPATH, using = "//div[contains(@class,'ag-menu')]//div[contains(@class,'ag-menu-option') and span[contains(text(),'Copy')]]")
    public static WebElement copy;

    @FindBy(how = How.XPATH, using = "//div[contains(@class,'ag-menu')]//div[contains(@class,'ag-menu-option') and span[contains(text(),'Copy with Headers')]]")
    public static WebElement copyWithHeaders;

    @FindBy(how = How.XPATH, using = "//div[contains(@class,'ag-menu')]//div[contains(@class,'ag-menu-option') and span[contains(text(),'Paste')]]")
    public static WebElement paste;

    @FindBy(how = How.XPATH, using = "//div[contains(@class,'ag-menu')]//div[contains(@class,'ag-menu-option')]//span[contains(text(),'Export')]")
    public static WebElement export;

    @FindBy(how = How.XPATH, using = "//div[contains(@class,'ag-menu')]//div[contains(@class,'ag-menu-option') and span[contains(text(),'CSV Export')]]")
    public static WebElement exportCsvExport;

    @FindBy(how = How.XPATH, using = "//div[contains(@class,'ag-menu')]//div[contains(@class,'ag-menu-option') and span[contains(text(),'Excel Export')]]")
    public static WebElement exportExcelXlsxExport;
}
