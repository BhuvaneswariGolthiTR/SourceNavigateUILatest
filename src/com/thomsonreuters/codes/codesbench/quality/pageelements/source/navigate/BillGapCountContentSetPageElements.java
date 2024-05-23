package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BillGapCountContentSetPageElements
{
    public static final String NOT_SELECTED_CONTENT_SET_DROPDOWN_ID = "pageForm:contentSetList:list1";
    public static final String SELECTED_CONTENT_SET_DROPDOWN_ID = "pageForm:contentSetList:list2";

    @FindBy(how = How.XPATH, using = "//select[contains(@id, 'list1')]")
    public static WebElement notSelectedContentSetDropdown;
    @FindBy(how = How.XPATH, using = "//input[@value='>']")
    public static WebElement moveOneNonSelectedToSelectedButton;
    @FindBy(how = How.XPATH, using = "//select[contains(@id, 'list2')]")
    public static WebElement selectedContentSetDropdown;
    @FindBy(how = How.XPATH, using = "//input[@value='<']")
    public static WebElement moveOneSelectedToNonSelectedButton;
}
