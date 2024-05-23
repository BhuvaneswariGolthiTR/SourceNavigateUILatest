package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ViewSelectedNodesBinPageElements
{
    public static final String VIEW_SELECTED_NODES_BIN_PAGE_TITLE = "View Selected Nodes Bin";
    public static final String NODE_VALUE_XPATH =  "//div[@id='selectedNodesGridDiv']//tbody[@class='yui-dt-data']//td[contains(@class,'Value')]";

    @FindBy(how = How.XPATH, using = "//div[@class='yui-dt-bd']")
    public static WebElement firstItemValue;

    @FindBy(how = How.XPATH, using = "//li[@index='1']")
    public static WebElement findInHierarchy;

    @FindBy(how = How.ID, using = "pageForm:empty")
    public static WebElement emptyButton;
}
