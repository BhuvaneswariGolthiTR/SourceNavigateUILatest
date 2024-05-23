package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditShellPageElements
{
    public static final String DELETE_FILE_BUTTON = "//input[contains(@id, 'pageForm:dtFileNumbers:1') and contains(@value,'Delete')]";
    public static final String NEW_FILE = "//input[@id='pageForm:dtFileNumbers:0:inputText']";

    @FindBy(how = How.ID, using = "pageForm:selectedFile")
    public static WebElement selectedFileTextBox;

    @FindBy(how = How.ID, using = "pageForm:performSaveButton")
    public static WebElement saveButton;

    @FindBy(how = How.XPATH, using = "//input[@name='pageForm:documentNumber']")
    public static WebElement documentNumber;

    @FindBy(how = How.ID, using = "pageForm:dtFileNumbers:0:j_idt93")
    public static WebElement addFileNumber;
}
