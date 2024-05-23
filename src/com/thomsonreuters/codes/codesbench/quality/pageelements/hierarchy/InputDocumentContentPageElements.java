package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class InputDocumentContentPageElements
{
    public static final String INPUT_DOCUMENT_CONTENT_PAGE_TITLE = "Input Document Content";
    public static final String FILE_MUST_BE_XML_ERROR_MESSAGE = "//span[@id='inputAsForm:filenameMessage' and text()='file must be in .xml format']";
    public static final String FUNCTION_ONLY_AVAILABLE_ON_SECTION_LEVEL_NODE_ERROR_MESSAGE = "//span[@style='color: red;' and text()='This function is only available on a section-level node.']";

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:ok' or @id='pageForm:okButton' or @value='OK'] | //button[@id='bt_ok']")
    public static WebElement okButton;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:cancel' or @id='cancelButton' or @id='pageForm:cancelButton' or @id='inputAsForm:cancelButton' or @value='Cancel' or @text='Cancel'] | //button[@id='cancelButton-button' or @id='b_cancel' or @text='Cancel']")
    public static WebElement cancelButton;

    @FindBy(how = How.XPATH, using = "//input[@name='inputAsForm:selectedFile']")
    public static WebElement selectedFileTextBox;
}