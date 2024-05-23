package com.thomsonreuters.codes.codesbench.quality.pageelements.abstractPageElements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RawXmlEditorPageElements
{
    public static final String RAW_XML_EDITOR_TITLE_PAGE = "Raw XML Editor";
    public static final String BODY_TAG = "//body";

    @FindBy(how = How.ID, using = "pageForm:saveButton")
    public static WebElement saveButton;

    @FindBy(how = How.ID, using = "pageForm:validateButton")
    public static WebElement validateButton;

    @FindBy(how = How.XPATH, using = "//input[(@id='pageForm:discardAndCloseButton' or @id='pageForm:closeButton') and not(contains(@style, 'DISPLAY: none'))]")
    public static WebElement closeButton;

    @FindBy(how = How.ID, using = "pageForm:rawXmlTextArea")
    public static WebElement textArea;

    @FindBy(how = How.ID, using = "messagePane")
    public static WebElement messagePane;
}
