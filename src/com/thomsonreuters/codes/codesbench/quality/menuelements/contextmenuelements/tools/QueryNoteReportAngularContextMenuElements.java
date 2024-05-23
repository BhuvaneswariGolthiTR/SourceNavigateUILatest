package com.thomsonreuters.codes.codesbench.quality.menuelements.contextmenuelements.tools;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class QueryNoteReportAngularContextMenuElements
{
    @FindBy(how = How.XPATH, using = "//span[text()='Edit Query Note']")
    public static WebElement editQueryNote;

    @FindBy(how = How.XPATH, using = "//span[text()='Resolve Query Note']")
    public static WebElement resolveQueryNote;

    @FindBy(how = How.XPATH, using = "//span[text()='Delete Query Note']")
    public static WebElement deleteQueryNote;

    @FindBy(how = How.XPATH, using = "//span[text()='Delete Query Note and Italic Para']")
    public static WebElement deleteQueryNoteAndItalicPara;

    @FindBy(how = How.XPATH, using = "//span[text()='View Query Note']")
    public static WebElement viewQueryNote;

    @FindBy(how = How.XPATH, using = "//span[text()='Find Document in Hierarchy']")
    public static WebElement findDocumentInHierarchy;

    @FindBy(how = How.XPATH, using = "//div[contains(@class,'ag-menu-option')]/span[text()='Edit Document']")
    public static WebElement editDocument;

    @FindBy(how = How.XPATH, using = "//span[text()='Export to Excel']")
    public static WebElement exportToExcel;

    @FindBy(how = How.XPATH, using = "//span[text()='Edit Action Dates']")
    public static WebElement editActionDates;
}
