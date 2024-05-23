package com.thomsonreuters.codes.codesbench.quality.pageelements.publishing.print.wipextracts.custom;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CustomPubExtractTreeViewPageElements
{
    public static final String WIP_EXTRACTS_CUSTOM_PUB_EXTRACT_TREE_VIEW_PAGE_TITLE = "Custom Pub Extract (Tree View)";
    public static final String TREE_VIEW_PANE_VALUE = "//div[@id='navTreeDiv']//td[contains(text(), '%s')]";
    public static final String COLLAPSED_BUTTON_NEXT_TO_VALUE_IN_TREE_VIEW = "//div[@id='navTreeDiv']//table[contains(@class, 'collapsed')]//td[contains(text(), '%s')]/../td//a[@class='ygtvspacer']";
    public static final String EXPANDED_BUTTON_NEXT_TO_VALUE_IN_TREE_VIEW = "//div[@id='navTreeDiv']//table[contains(@class, 'expanded')]//td[contains(text(), '%s')]/../td//a[@class='ygtvspacer']";
    public static final String SELECTED_RECORDS_PANE_VALUE = "//div[@id='modTreeDiv']//td[contains(text(), '%s')]";

    @FindBy(how = How.ID, using = "pageForm:addAllButton")
    public static WebElement rightMultipleArrowButton;

    @FindBy(how = How.ID, using = "pageForm:addOneButton")
    public static WebElement rightSingleArrowButton;

    @FindBy(how = How.ID, using = "pageForm:removeOneButton")
    public static WebElement leftArrowButton;
}
