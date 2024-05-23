package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class InputAsChildPageElements
{
    public static final String INPUT_AS_CHILD_PAGE_TITLE = "Input as Child";
    public static final String ERROR_MESSAGE_FOR_SECTION_NODE = "//form[@id='inputAsForm']//span[text()='A section node cannot have a child.']";

    @FindBy(how = How.XPATH, using = "//input[@name='inputAsForm:selectedFile']")
    public static WebElement selectedFileInput;

    @FindBy(how = How.ID, using = "inputAsForm:cancelButton")
    public static WebElement cancelButton;

    @FindBy(how = How.ID, using = "inputAsForm:saveButton")
    public static WebElement okButton;
}
