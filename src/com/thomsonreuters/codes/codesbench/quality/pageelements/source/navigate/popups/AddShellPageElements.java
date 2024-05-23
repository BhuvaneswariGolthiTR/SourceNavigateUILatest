package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AddShellPageElements
{
    public static final String ALL_CONTENT_TYPE_OPTIONS = "//select[@id='pageForm:contentType']//option";
    public static final String ALL_ORGANISATION_OPTIONS = "//select[@onchange='organizationOnChange(this)']//option";
    public static final String NEW_FILE = "//input[@id='pageForm:dtFileNumbers:0:inputText']";
    public static final String ADD_FILENAME_MESSAGE = "//span[@id='pageForm:filenameMessage']";
    public static final String DOCUMENT_TYPE_DROPDOWN = "//select[@id='pageForm:documentType']";

    @FindBy(how = How.XPATH, using = "//span[@class='ui-combobox']//a[contains(@class,'ui-combobox-toggle')]")
    public static WebElement organisationDropdown;

    @FindBy(how = How.ID, using = "pageForm:contentSet")
    public static WebElement contentSetDropdown;

    @FindBy(how = How.ID, using = "pageForm:documentType")
    public static WebElement documentTypeDropdown;

    @FindBy(how = How.ID, using = "CombifyInput-mySelect")
    public static WebElement organizationTextBox;

    @FindBy(how = How.ID, using = "pageForm:documentNumber")
    public static WebElement documentNumberTextBox;

    @FindBy(how = How.ID, using = "pageForm:contentType")
    public static WebElement contentTypeTextBox;

    @FindBy(how = How.ID, using = "pageForm:year")
    public static WebElement yearTextBox;

    @FindBy(how = How.ID, using = "pageForm:title")
    public static WebElement documentTitleTextBox;

    @FindBy(how = How.ID, using = "pageForm:selectedFile")
    public static WebElement selectedFileBrowserButton;

    @FindBy(how = How.ID, using = "pageForm:addContentButton")
    public static WebElement submitButton;

    @FindBy(how = How.ID, using = "pageForm:dtFileNumbers:0:inputText")
    public static WebElement newFileNumber;

    @FindBy(how = How.ID, using = "pageForm:dtFileNumbers:0:j_idt93")
    public static WebElement addFileNumber;

    @FindBy(how = How.ID, using = "pageForm:cancelButton")
    public static WebElement cancelButton;
}
