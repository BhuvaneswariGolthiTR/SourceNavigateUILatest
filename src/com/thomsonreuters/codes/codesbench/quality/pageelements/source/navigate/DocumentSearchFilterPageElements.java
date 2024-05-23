package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DocumentSearchFilterPageElements
{
    public static final String DOCUMENT_SEARCH_FILTER_TITLE = "Search Filter";

    @FindBy(how = How.ID, using = "pageForm:cancel")
    public static WebElement cancelButton;

    @FindBy(how = How.ID, using = "pageForm:corId")
    public static WebElement corIdTextBox;

    @FindBy(how = How.ID, using = "pageForm:corIdSearch")
    public static WebElement corIdSearchButton;

    @FindBy(how = How.ID, using = "pageForm:billId")
    public static WebElement billIdTextBox;

    @FindBy(how = How.ID, using = "pageForm:billIdSearch")
    public static WebElement billIdSearchButton;

    @FindBy(how = How.ID, using = "pageForm:docId")
    public static WebElement docUuidTextBox;

    @FindBy(how = How.ID, using = "pageForm:docIdSearch")
    public static WebElement docUuidSearchButton;

    @FindBy(how = How.ID, using = "pageForm:renditionId")
    public static WebElement renditionUuidTextBox;

    @FindBy(how = How.ID, using = "pageForm:renditionIdSearch")
    public static WebElement renditionUuidSearchButton;

    @FindBy(how = How.ID, using = "pageForm:assignedUserId")
    public static WebElement assignedUserDropdown;

    @FindBy(how = How.ID, using = "pageForm:assignedUserIdSearch")
    public static WebElement assignedUserSearchButton;
}
