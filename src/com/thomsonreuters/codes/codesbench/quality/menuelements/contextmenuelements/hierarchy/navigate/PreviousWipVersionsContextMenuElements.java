package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.hierarchy.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PreviousWipVersionsContextMenuElements
{
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Create Historical Version')]")
    public static WebElement createHistoricalVersion;

    @FindBy(how = How.XPATH, using = "//a[contains(text(),'View Content')]")
    public static WebElement viewContent;

    public static final String RESTORE_WIP_CONTENT = "//a[contains(text(),'Restore WIP Content')]";

}
