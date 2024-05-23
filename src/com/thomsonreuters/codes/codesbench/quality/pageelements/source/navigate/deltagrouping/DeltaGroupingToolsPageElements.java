package com.thomsonreuters.codes.codesbench.quality.pageelements.source.navigate.deltagrouping;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeltaGroupingToolsPageElements
{
    @FindBy(how = How.XPATH, using = "//div[@id='toolbarContainer']//a[contains(text(), 'New Group')]")
    public static WebElement newGroup;

    @FindBy(how = How.XPATH, using = "//div[@id='toolbarContainer']//a[contains(text(), 'Remove Group')]")
    public static WebElement removeGroup;

    @FindBy(how = How.XPATH, using = "//a[text()='Clear Filters']")
    public static WebElement clearFilters;

    @FindBy(how = How.ID, using = "clearButtons-button")
    public static WebElement clearButton;

    @FindBy(how = How.XPATH, using = "//a[text()='Clear Sort']")
    public static WebElement clearSort;

    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Apply and close')]")
    public static WebElement applyAndCloseButton;

    @FindBy(how = How.XPATH, using = "//button[text()='Apply']")
    public static WebElement applyButton;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Yes')]")
    public static WebElement yesButton;
}
