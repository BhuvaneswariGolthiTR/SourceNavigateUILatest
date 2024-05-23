package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools.ocextract;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PublicationFilesContextMenuElements
{
    public final static String CONTEXT_MENU = "//div[contains(@class, 'ag-menu')]";

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//span[contains(@class,'ag-menu-option-text') and text()='PDF']")
    public static WebElement pdf;

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//span[contains(@class,'ag-menu-option-text') and text()='Download']")
    public static WebElement pdfDownload;

    @FindBy(how = How.XPATH, using = CONTEXT_MENU + "//span[contains(@class,'ag-menu-option-text') and text()='Upload']")
    public static WebElement pdfUpload;
}