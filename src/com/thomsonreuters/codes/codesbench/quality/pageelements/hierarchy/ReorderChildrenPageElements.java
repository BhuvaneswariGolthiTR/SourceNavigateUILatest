package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ReorderChildrenPageElements
{
    public static final String REORDER_CHILDREN_PAGE_TITLE = "Reorder Children";
    public static final String REORDER_DRAG_LIST = "//ul[@class='draglist']/li";

    @FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
    public static WebElement quickLoadButton;

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:ok' or @id='pageForm:okButton' or @value='OK'] | //button[@id='bt_ok']")
    public static WebElement saveButton;
}
