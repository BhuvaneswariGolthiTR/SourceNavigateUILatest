package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HierarchyInputAsSiblingPageElements
{
    public static final String HIERARCHY_INPUT_AS_SIBLING_PAGE_TITLE = "Input as Sibling";

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:saveButton' or @id='pageForm:performSaveButton' or @id='pageForm:saveBtn' or @value='Save' or @id='inputAsForm:saveButton']")
    public static WebElement saveButton;

    @FindBy(how = How.XPATH, using = "//input[@name='inputAsForm:selectedFile']")
    public static WebElement selectedFileInput;
}
