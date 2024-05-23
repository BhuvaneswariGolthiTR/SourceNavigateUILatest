package com.thomsonreuters.codes.codesbench.quality.pageelements.hierarchy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RemoveAssignedScriptsPageElements
{
    public static final String REMOVE_ASSIGNED_SCRIPTS_PAGE_TITLE = "Remove Assigned Scripts";
    public static final String NUMBER_OF_ROWS_IN_SCRIPTS_GRID = "//tbody[@class='yui-dt-data']/tr";
    public static final String SCRIPT_WITH_GIVEN_PUBTAG_VALUE = "//tbody[@class='yui-dt-data']//td[contains(@class,'Pubtag')]/div[text()='%s']";
    public static final String CELL_VALUE_IN_GRID_ROW_OF_GIVEN_INDEX = "//tbody[@class='yui-dt-data']/tr[%d]/td[not(contains(@class,'hidden'))]/div";
    public static final String INDICATOR_BY_GIVEN_PUBTAG_VALUE = "//tr[td/div[text()='%s']]/td[contains(@class, 'Indicator')]/div";

    @FindBy(how = How.XPATH, using = "//input[@id='pageForm:close' or @id='pageForm:closeButton'] | //button[@id='close-button' or @id='b_close']")
    public static WebElement closeButton;
}
