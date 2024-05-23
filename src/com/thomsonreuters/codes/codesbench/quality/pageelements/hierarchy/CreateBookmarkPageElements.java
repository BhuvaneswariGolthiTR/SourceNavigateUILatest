package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CreateBookmarkPageElements
{
    public static final String CREATE_BOOKMARK_PAGE_TITLE = "Create Bookmark";
    public static final String VALIDATE_REPORT_DOC_NUM_IN_GRID = "//tbody[@class='yui-dt-data']/tr/td[contains(@class, 'yui-dt-col-docNumber') and not (contains(@class,'hidden'))]";

    @FindBy(how = How.ID, using = "pageForm:bookmarkName")
    public static WebElement createBookmarkNameTextbox;

    @FindBy(how = How.ID, using = "pageForm:finishedBookmarkGrid")
    public static WebElement finishedBookmarkGrid;

    @FindBy(how = How.ID, using = "pageForm:saveButton")
    public static WebElement saveButton;

    @FindBy(how = How.ID, using = "pageForm:closeButton")
    public static WebElement closeButton;

    @FindBy(how = How.ID, using = "pageForm:cancelButton")
    public static WebElement cancelButton;
}