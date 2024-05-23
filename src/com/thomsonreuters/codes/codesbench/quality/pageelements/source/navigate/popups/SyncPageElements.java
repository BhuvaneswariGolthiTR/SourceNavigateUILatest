package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SyncPageElements
{
    public static final String SYNC_PAGE_TITLE = "Sync";

    @FindBy(how = How.ID, using = "pageForm:submitButton")
    public static WebElement okButton;
}
