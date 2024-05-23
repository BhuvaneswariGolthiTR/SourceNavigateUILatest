package com.thomsonreuters.codes.codesbench.quality.pageelements.nodAngular.popups.tools;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ToolsGridPopupElementsAngular
{
    @FindBy(how = How.XPATH, using = "(//span[@class='transferbox-searchbox'])[1]/input")
    public static WebElement nonSelectedFilterList;

    @FindBy(how = How.XPATH, using = "(//span[@class='transferbox-searchbox'])[2]/input")
    public static WebElement selectedFilterList;

    @FindBy(how = How.XPATH, using = "(//bento-transferlist)[1]//bento-checkbox/input")
    public static WebElement nonSelectedTitleCheckbox;

    @FindBy(how = How.XPATH, using = "(//bento-transferlist)[2]//bento-checkbox/input")
    public static WebElement selectedTitleCheckbox;

    @FindBy(how = How.XPATH, using = "//li/button/span[contains(text(),'Add')]")
    public static WebElement add;

    @FindBy(how = How.XPATH, using = "//li/button/span[contains(text(),'Remove')]")
    public static WebElement remove;

    @FindBy(how = How.XPATH, using = "//div[p[contains(text(),'Non-selected Volumes')]]/div")
    public static WebElement numberNonSelectedVolumes;

    @FindBy(how = How.XPATH, using = "//div[p[contains(text(),'Selected Volumes')]]/div")
    public static WebElement numberSelectedVolumes;

    public static final String NON_SELECTED_TABLE = "(//div[@class='bento-table-grid'])[1]";
    public static final String NON_SELECTED_ROWS = NON_SELECTED_TABLE + "//div[@class='bento-table-row']";
    public static final String NON_SELECTED_CHECKBOX_BY_VALUE = NON_SELECTED_ROWS + "[span[contains(text(),'%s')]]/bento-checkbox/input";

    public static final String SELECTED_TABLE = "(//div[@class='bento-table-grid'])[2]";
    public static final String SELECTED_ROWS = SELECTED_TABLE + "//div[@class='bento-table-row']";
    public static final String SELECTED_CHECKBOX_BY_VALUE = SELECTED_ROWS + "[span[contains(text(),'%s')]]/bento-checkbox";

    public static final String NON_SELECTED_FILTER_LIST_X = "(//span[@class='transferbox-searchbox'])[1]/button";
    public static final String SELECTED_FILTER_LIST_X = "(//span[@class='transferbox-searchbox'])[2]/button";
}
