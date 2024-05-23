package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SourceFilterPageElements
{
    public static final String SOURCE_FILTER_TITLE = "Search Filter";

    @FindBy(how = How.ID, using = "pageForm:corId")
    public static WebElement corIdTextBox;

    @FindBy(how = How.ID, using = "pageForm:corIdSearch")
    public static WebElement corIdSearchButton;

    @FindBy(how = How.ID, using = "pageForm:billId")
    public static WebElement billIdTextBox;

    @FindBy(how = How.ID, using = "pageForm:billIdSearch")
    public static WebElement billIdSearchButton;

    @FindBy(how = How.ID, using = "pageForm:docId")
    public static WebElement uuidTextBox;

    @FindBy(how = How.ID, using = "pageForm:docIdSearch")
    public static WebElement uuidSearchButton;


    @FindBy(how = How.ID, using = "findDocumentButton")
    public static WebElement findByButton;

    @FindBy(how = How.ID, using = "viewManagementButton")
    public static WebElement viewManagementButton;
}
