package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ViewMetadataPageElements
{
    public static final String VIEW_METADATA_PAGE_TITLE = "View Metadata";

    @FindBy(how = How.XPATH, using = "//select[@id='pageForm:nodeType']/option[@selected]")
    public static WebElement selectedNodeTypeOption;

    @FindBy(how = How.ID, using = "pageForm:nodeType")
    public static WebElement nodeTypeDropdown;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:cancel' or @id='cancelButton' or @id='pageForm:cancelButton' or @id='inputAsForm:cancelButton' or @value='Cancel'] | //button[@id='cancelButton-button' or @id='b_cancel']")
    public static WebElement cancelButton;
}
