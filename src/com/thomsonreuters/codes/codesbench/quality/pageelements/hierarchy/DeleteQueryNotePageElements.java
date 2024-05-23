package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static org.openqa.selenium.support.How.*;

public class DeleteQueryNotePageElements
{
    public static final String PAGE_TITLE = "Delete Query Note";

    @FindBy(how = ID, using = "pageForm:cancelBtn")
    public static WebElement cancelButton;

    @FindBy(how = ID, using = "pageForm:deleteBtn")
    public static WebElement deleteButton;
}