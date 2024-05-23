package com.thomsonreuters.codes.codesbench.quality.pageelements.source.popups;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class TaxTypeAddPageElements
{
    public static final String PAGE_TITLE = "Tax Type Add";

    @FindBy(how = How.XPATH, using = "//select[contains(@id,'taxTypeSwitchList:list1')]/option")
    public static List<WebElement> availableTaxTypeAdds;

    @FindBy(how = How.XPATH, using = "//select[contains(@id,'taxTypeSwitchList:list2')]/option")
    public static List<WebElement> selectedTaxTypeAdds;

    public static final String SELECTED_TAX_TYPE_ADDS = "//select[contains(@id,'taxTypeSwitchList:list2')]/option";

    @FindBy(how = How.XPATH, using = "//span[contains(@id,'taxTypeSwitchList:buttonGroup')]/input[contains(@name,'taxTypeSwitchList:moveAll1to2')]")
    public static WebElement moveAllToSelected;

    @FindBy(how = How.XPATH, using = "//span[contains(@id,'taxTypeSwitchList:buttonGroup')]/input[contains(@name,'taxTypeSwitchList:moveAll2to1')]")
    public static WebElement moveAllToAvailable;

    @FindBy(how = How.XPATH, using = "//span[contains(@id,'taxTypeSwitchList:buttonGroup')]/input[contains(@name,'taxTypeSwitchList:move1to2')]")
    public static WebElement moveToSelected;

    @FindBy(how = How.XPATH, using = "//span[contains(@id,'taxTypeSwitchList:buttonGroup')]/input[contains(@name,'taxTypeSwitchList:move1to2')]")
    public static WebElement moveToAvailable;

    public static final String AVAILABLE_TAX_TYPE_BY_NAME = "//select[contains(@id,'taxTypeSwitchList:list1')]/option[@value='%s']";

    public static final String SELECTED_TAX_TYPE_BY_NAME = "//select[contains(@id,'taxTypeSwitchList:list2')]/option[@value='%s']";

}
