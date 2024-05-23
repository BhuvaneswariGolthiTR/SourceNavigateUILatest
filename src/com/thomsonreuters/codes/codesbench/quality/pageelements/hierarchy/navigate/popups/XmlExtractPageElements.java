package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy.navigate.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class XmlExtractPageElements
{
    public static final String XML_EXTRACT_PAGE_TITLE = "XML Extract";
    public static final String FILE_NAME_INPUT = "//form[@id='pageForm']//input[@id='pageForm:filename']";
    public static final String FILE_NAME_VALUE = "//form[@id='pageForm']//input[@id='pageForm:filename' and @value='%s']";

    @FindBy(how = How.ID, using = "pageForm:filename")
    public static WebElement fileNameInput;

    @FindBy(how = How.ID, using = "submitButton-button")
    public static WebElement submitButton;

    @FindBy(how = How.LINK_TEXT, using = "Close")
    public static WebElement close;
}
