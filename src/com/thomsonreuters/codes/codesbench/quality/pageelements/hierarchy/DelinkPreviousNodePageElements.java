package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DelinkPreviousNodePageElements
{
    public static final String DELINK_PREVIOUS_NODE_PAGE_TITLE = "Delink Previous Node";

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:ok' or @id='pageForm:okButton' or @value='OK'] | //button[@id='bt_ok']")
    public static WebElement okButton;

    @FindBy(how = How.ID, using = "pageForm:setLawTrackingQuick")
    public static WebElement quickLoadButton;

    @FindBy(how = How.XPATH, using = "//tbody[@class='yui-dt-data']/tr[1]")
    public static WebElement firstNode;
}
